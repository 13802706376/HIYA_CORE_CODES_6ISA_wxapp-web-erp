package com.yunnex.ops.erp.modules.workflow.flow.task;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.TaskService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.utils.DateUtils;
import com.yunnex.ops.erp.modules.act.dao.ActDao;
import com.yunnex.ops.erp.modules.act.entity.Act;
import com.yunnex.ops.erp.modules.act.entity.TaskExt;
import com.yunnex.ops.erp.modules.act.service.TaskExtService;
import com.yunnex.ops.erp.modules.holiday.service.ErpHolidaysService;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.workflow.flow.common.JykFlowConstants;
import com.yunnex.ops.erp.modules.workflow.flow.common.WorkFlowConstants;
import com.yunnex.ops.erp.modules.workflow.flow.from.FlowForm;
import com.yunnex.ops.erp.modules.workflow.flow.from.WorkFlowQueryForm;

/**
 * 定时任务：计算任务超时状态和任务待生产状态
 */
public class WorkFlowTaskScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowTaskScheduler.class);
    private static final String YES = "Y";
    private static final String NO = "N";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final Integer NORMAL = 80;
    private static final Integer OVER_TIME = 100;
    private static final String JYK_FLOW = "jyk_flow";
    private static final String JYK_FLOW_NEW = "jyk_flow_new";
    // 是否是自动移出待生产库，false为定时任务移出
    private static final String IS_AUTO_OUT_PENDING = "isAutoOutPending";
    // 进入待生产库原因 key
    private static final String IN_PENDING_REASON = "inPendingReason";
    // 进入待生产库原因：超过20工作日
    private static final String IN_PENDING_REASON_PT = "PT";
    // 进入待生产库原因：下次确定营业执照时间
    private static final String IN_PENDING_REASON_NLT = "NLT";
    // 进入待生产库原因：下次确定资质齐全时间
    private static final String IN_PENDING_REASON_NQT = "NQT";
    // 进入待生产库原因：下次确定投放上线预期时间
    private static final String IN_PENDING_REASON_NCT = "NCT";
    
    // 所有聚引客订单
    private Set<String> jykOrders;
    // 有超时风险的聚引客订单
    private Set<String> jykRiskOrders;

    @Autowired
    private ActDao actDao;
    @Autowired
    private ErpHolidaysService erpHolidaysService;
    @Autowired
    private TaskExtService taskExtService;
    @Autowired
    private ErpOrderSplitInfoService erpOrderSplitInfoService;
    @Autowired
    private TaskService taskService;
    
    /**
     * 修改流程的待生产状态和任务超时状态
     *
     * @param workFlowQueryForm
     * @date 2018年1月11日
     */
    public void changeState() {
        LOGGER.info("流程定时任务开始：修改流程的待生产状态和任务超时状态");
        // 查询全部
        List<FlowForm> tasks = actDao.findTasks(new WorkFlowQueryForm());
        if (CollectionUtils.isEmpty(tasks)) {
            LOGGER.info("任务数量为 0，结束！");
            return;
        }
        
        init();

        for (FlowForm form : tasks) {
            try {
                process(form);
            } catch (RuntimeException e) {
                LOGGER.info("流程定时任务发现异常！", e);
            }
        }

        updateRiskOrders();

        LOGGER.info("流程定时任务结束：修改流程的待生产状态和任务超时状态，数量：{}", tasks.size());
    }

    private void init() {
        jykOrders = new HashSet<String>();
        jykRiskOrders = new HashSet<String>();
    }

    /** 待生产库和超时计算 */
    private void process(FlowForm form) throws RuntimeException {
        if (form == null || form.getAct() == null) {
            return;
        }

        if (JYK_FLOW.equals(form.getAct().getProcDefKey()) || JYK_FLOW_NEW.equals(form.getAct().getProcDefKey())) {
            removePendingProduction(form);
            calcRiskOrders(form);
        }

        // 计算任务超时状态
        calcTaskTime(form);
    }

    /** 更新聚引客订单的超时风险状态 */
    private void updateRiskOrders() {
        // 有超时风险的订单
        if (CollectionUtils.isNotEmpty(jykRiskOrders)) {
            erpOrderSplitInfoService.updateRiskStatus(YES, jykRiskOrders);
        }

        // 没有超时风险的订单
        if (CollectionUtils.isNotEmpty(jykOrders)) {
            jykOrders.removeAll(jykRiskOrders);
            erpOrderSplitInfoService.updateRiskStatus(NO, jykOrders);
        }
    }

    /**
     * 计算任务是否有超时风险
     * 
     * @throws ParseException
     */
    private void calcRiskOrders(FlowForm form) {
        if (form == null || form.getAct() == null || form.getTaskExt() == null || form.getOrderSplitInfo() == null) {
            return;
        }

        // 记下所有的分单ID，如果某个分单始终没有关键任务点处于超时风险状态，要将其状态置为N
        jykOrders.add(form.getOrderSplitInfo().getId());

        // 不是关键任务点，退出
        if (!YES.equals(form.getAct().getIsKeyPoint())) {
            return;
        }

        // 待生产库订单不计算超时风险
        if (YES.equals(form.getTaskExt().getPendingProdFlag())) {
            return;
        }

        riskOrders(form);
    }

    private void riskOrders(FlowForm form) {
        if (form == null || form.getAct() == null || form.getOrderSplitInfo() == null || form.getOrderOriginalInfo() == null || form
                        .getTaskExt() == null) {
            return;
        }

        Act act = form.getAct();
        ErpOrderSplitInfo splitInfo = form.getOrderSplitInfo();

        // 是否是加急分单
        boolean hurry = ONE.equals(splitInfo.getHurryFlag().toString());
        // 这2个任务特殊处理
        if ((YES.equals(splitInfo.getPendingProduced()) && null != splitInfo.getActivationTime()) && ("contact_shop_zhixiao"
                        .equals(act.getTaskDefKey()) || "contact_shop_service".equals(act.getTaskDefKey()) || "contact_shop_service_latest"
                                        .equals(act.getTaskDefKey()) || "contact_shop_zhixiao_latest".equals(act.getTaskDefKey()))) {
            if (hurry) {
                act.setUrgentTaskDateHours(2);
            } else {
                act.setTaskDateHours(4);
            }
        }
        // 任务工时
        Integer taskDateHours = hurry ? act.getUrgentTaskDateHours() : act.getTaskDateHours();

        // 未进入过待生产库
        // 超时风险开始计算时间
        Date riskStartDate = form.getOrderOriginalInfo().getBuyDate(); // 订单购买时间
        // 既定消耗工时
        Integer taskHours = hurry ? act.getUrgentTaskHours() : act.getNormalTaskHours();

        // 任务开始时间取自于计算待生产库的结果
        Date taskStarterDate = act.getTaskStarterDate();
        // 从待生产库激活的分单，激活时间不为空表示进入过一次待生产库并且出来了
        if (YES.equals(splitInfo.getPendingProduced()) && null != splitInfo.getActivationTime()) {
            // 超时风险开始计算时间为分单从待生产库激活的时间
            riskStartDate = taskStarterDate;
            // 激活后的既定消耗工时
            taskHours = hurry ? act.getActivationUrgentTaskHours() : act.getActivationNormalTaskHours();
        }

        try {
            // 任务到期时间
            Date taskEndDate = erpHolidaysService.enddate(taskStarterDate, taskDateHours);
            long now = System.currentTimeMillis();
            // 如果任务到期时间小于现在的时间，以现在时间为准
            if (now > taskEndDate.getTime()) {
                taskEndDate = new Date(now);
            }

            // 既定时间
            Date fixedDate = erpHolidaysService.enddate(riskStartDate, taskHours);

            // 如果任务结束时间小于既定时间，则没有超时风险，否则有
            if (taskEndDate.getTime() > fixedDate.getTime()) {
                jykRiskOrders.add(splitInfo.getId());
            }
        } catch (ParseException e) {
            LOGGER.debug("日期解析出错！", e);
        }
    }

    /** 计算任务超时状态 */
    private void calcTaskTime(FlowForm form) {
        if (form == null || form.getAct() == null || form.getTaskExt() == null) {
            return;
        }

        Act act = form.getAct();
        TaskExt taskExt = form.getTaskExt();

        int taskHours = act.getTaskDateHours();
        boolean pendingProdFlag = false;
        if (JYK_FLOW.equals(act.getProcDefKey()) || JYK_FLOW_NEW.equals(act.getProcDefKey())) {
            ErpOrderSplitInfo splitRow = form.getOrderSplitInfo();
            taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
            pendingProdFlag = YES.equals(taskExt.getPendingProdFlag());
        }
        Integer taskConsumTime = computingTaskSchedule(act.getTaskStarterDate(), taskHours);
        act.setTaskConsumTime(taskConsumTime);
        // 待生产库的任务状态都为正常
        if (pendingProdFlag) {
            act.setTaskConsumTime(0);
        }

        if (act.getTaskConsumTime() < NORMAL && !WorkFlowConstants.NORMAL.equals(taskExt.getStatus())) {
            // 正常
            taskExtService.updateTaskState(new TaskExt(act.getTaskId(), null, WorkFlowConstants.NORMAL));
        } else if (act.getTaskConsumTime() >= NORMAL && act.getTaskConsumTime() < OVER_TIME && !WorkFlowConstants.ONLY_WILL_OVER_TIME
                        .equals(taskExt.getStatus())) {
            // 既将到期
            taskExtService.updateTaskState(new TaskExt(act.getTaskId(), null, WorkFlowConstants.ONLY_WILL_OVER_TIME));
        } else if (act.getTaskConsumTime() >= OVER_TIME && !WorkFlowConstants.OVER_TIME.equals(taskExt.getStatus())) {
            // 超时
            taskExtService.updateTaskState(new TaskExt(act.getTaskId(), null, WorkFlowConstants.OVER_TIME));
        }
    }
    
    // 将符合条件的任务从待生产库移到待办任务
    private void removePendingProduction(FlowForm form) {
        // 根据暂停标识移除待生产库
        removeSuspendedTask(form);
        removePromotionTimeDetermination(form);
        removeChannelConfirmBusinessLicense(form);
        removeChannelConfirmBusinessQualification(form);
        removeContactShop(form);
        // removeFinallyConfirmExtensionTime(form);

    }

    private void removeSuspendedTask(FlowForm form) {
        ErpOrderSplitInfo orderSplitInfo = form.getOrderSplitInfo();
        if(orderSplitInfo!=null) {
            String suspendFlag = orderSplitInfo.getSuspendFlag();
            if (CommonConstants.Sign.YES.equals(suspendFlag)) {
                checkAndRemove(form, form.getOrderSplitInfo().getNextContactTime());
            }
        }
    }

    /** 移出待生产库：与商户沟通推广时间 */
    private void removePromotionTimeDetermination(FlowForm form) {
        if (!isTaskKey(form, "promotion_time_determination")) {
            return;
        }

        Map<String, Object> variables = taskService.getVariables(form.getAct().getTaskId());
        if (variables == null) {
            return;
        }
        Object iprObj = variables.get(IN_PENDING_REASON);
        String inPendingReason = String.valueOf(iprObj != null ? iprObj : "");

        // 设置了沟通时间，但未确定投放时间的订单
        if (IN_PENDING_REASON_NCT.equals(inPendingReason)) {
            checkAndRemove(form, form.getOrderSplitInfo().getNextContactTime());
        }

        // 移出：投放日期距离现在时间小于20个工作日的订单任务
        if (IN_PENDING_REASON_PT.equals(inPendingReason)) {
            check20Workdays(form);
        }
    }

    /** 移出待生产库：商户没有营业执照下次沟通时间 */
    private void removeChannelConfirmBusinessLicense(FlowForm form) {
        if (!isTaskKey(form, "channel_confirm_business_license")) {
            return;
        }

        checkAndRemove(form, form.getOrderSplitInfo().getNextLicenseTime());
    }

    /** 移出待生产库：商户资质是否齐全下次沟通时间 */
    private void removeChannelConfirmBusinessQualification(FlowForm form) {
        if (!isTaskKey(form, "channel_confirm_business_qualification")) {
            return;
        }

        checkAndRemove(form, form.getOrderSplitInfo().getNextQualificationTime());
    }

    /** 移出待生产库：最终上线时间确认 */
    public void removeFinallyConfirmExtensionTime(FlowForm form) {
        if (!isTaskKey(form, "finally_confirm_extension_time")) {
            return;
        }

        checkAndRemove(form, form.getOrderSplitInfo().getPromotionTime());
    }

    /** 移出待生产库：直销和服务商 -> 商户对接/确认推广门店/资质/推广时间。 */
    private void removeContactShop(FlowForm form) {
        if (!isTaskKey(form, null)) {
            return;
        }

        Act act = form.getAct();
        if (!"contact_shop_zhixiao".equals(act.getTaskDefKey()) && !"contact_shop_service"
                        .equals(act.getTaskDefKey()) && !"contact_shop_service_latest"
                                        .equals(act.getTaskDefKey()) && !"contact_shop_zhixiao_latest"
                                        .equals(act.getTaskDefKey())) {
            return;
        }

        Map<String, Object> variables = taskService.getVariables(act.getTaskId());
        if (variables == null) {
            return;
        }

        boolean isAutoOutPending = true;
        Object object = variables.get(IS_AUTO_OUT_PENDING);
        if (object != null && !Boolean.valueOf(object.toString())) {
            isAutoOutPending = false;
        }
        Object iprObj = variables.get(IN_PENDING_REASON);
        String inPendingReason = String.valueOf(iprObj != null ? iprObj : "");
        // 如果不是自动从待生产库激活，则任务开始时间取手动激活的时间
        Date activationTime = form.getOrderSplitInfo().getActivationTime();
        if (IN_PENDING_REASON_PT.equals(inPendingReason)) {
            if (!isAutoOutPending) {
                form.getOrderSplitInfo().setPromotionTime(activationTime);
            }
            check20Workdays(form);
        } else if (IN_PENDING_REASON_NLT.equals(inPendingReason)) {
            checkAndRemove(form, !isAutoOutPending ? activationTime : form.getOrderSplitInfo().getNextLicenseTime());
        } else if (IN_PENDING_REASON_NQT.equals(inPendingReason)) {
            checkAndRemove(form, !isAutoOutPending ? activationTime : form.getOrderSplitInfo().getNextQualificationTime());
        } else if (IN_PENDING_REASON_NCT.equals(inPendingReason)) {
            checkAndRemove(form, !isAutoOutPending ? activationTime : form.getOrderSplitInfo().getNextContactTime());
        }
    }

    private void check20Workdays(FlowForm form) {
        if (!checkParams(form)) {
            return;
        }

        try {
            Act act = form.getAct();
            ErpOrderSplitInfo splitRow = form.getOrderSplitInfo();
            // 现在开始20个工作日后的日期，跟投放日期比较，<=0 表示在20个工作日内，移出待生产库
            Double distanceDays = DateUtils.getDistanceOfTwoDate(
                            this.erpHolidaysService.enddate(new Date(), JykFlowConstants.PLANNING_DATE_DISTINCT * 8), splitRow.getPromotionTime());
            if (distanceDays.intValue() <= 0 && YES.equals(form.getTaskExt().getPendingProdFlag())) {
                removeFromPendingProd(act, splitRow.getId(), splitRow.getPromotionTime());
                splitRow.setActivationTime(splitRow.getPromotionTime());
            }
            act.setTaskStarterDate(splitRow.getPromotionTime());
        } catch (ParseException e) {
            LOGGER.debug("日期解析出错！", e);
        }
    }

    /**
     * 如果是待生产库任务并且时间到了, 出库. 并将开始时间设置为指定的时间.
     */
    private void checkAndRemove(FlowForm form, Date datetime) {
        if (!checkParams(form)) {
            return;
        }

        Act act = form.getAct();
        ErpOrderSplitInfo splitRow = form.getOrderSplitInfo();
        if (datetime != null) {
            if (System.currentTimeMillis() >= datetime.getTime() && YES.equals(form.getTaskExt().getPendingProdFlag())) {
                removeFromPendingProd(act, splitRow.getId(), datetime);
                splitRow.setActivationTime(datetime);
            }
            act.setTaskStarterDate(datetime);
        }
    }

    private boolean checkParams(FlowForm form) {
        if (form == null || form.getAct() == null || form.getOrderSplitInfo() == null || form.getTaskExt() == null) {
            return false;
        }

        return true;
    }

    /** 查看当前任务是不是指定的任务 */
    private boolean isTaskKey(FlowForm form, String taskKey) {
        if (form == null || form.getAct() == null || form.getOrderSplitInfo() == null) {
            return false;
        }

        // 非指定的任务不用检查
        if (StringUtils.isNotBlank(taskKey) && !taskKey.equals(form.getAct().getTaskDefKey())) {
            return false;
        }

        return true;
    }

    /** 标识为非待生产库，并更新激活时间 */
    private void removeFromPendingProd(Act act, String splitId, Date date) {
        taskExtService.updateTaskState(new TaskExt(act.getTaskId(), NO, null));
        erpOrderSplitInfoService.updatePendingStatus(splitId, date, NO);
    }

    private Integer computingTaskSchedule(Date staterDate, Integer taskHours) {
        float startDateLong = (erpHolidaysService.calculateHours(staterDate, new Date()))*60*60*1000;
        float taskHoursLong = (taskHours * 60 * 60 * 1000);
        return (int) ((startDateLong / taskHoursLong) * 100);
    }
    
}
