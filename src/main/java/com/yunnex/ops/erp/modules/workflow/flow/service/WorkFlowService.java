package com.yunnex.ops.erp.modules.workflow.flow.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yunnex.ops.erp.common.freemarker.TemplateServiceFreeMarkerImpl;
import com.yunnex.ops.erp.common.utils.DateUtils;
import com.yunnex.ops.erp.common.utils.HttpUtil;
import com.yunnex.ops.erp.modules.act.entity.Act;
import com.yunnex.ops.erp.modules.act.entity.ActDefExt;
import com.yunnex.ops.erp.modules.act.service.ActDefExtService;
import com.yunnex.ops.erp.modules.act.service.ActTaskService;
import com.yunnex.ops.erp.modules.act.utils.ActUtils;
import com.yunnex.ops.erp.modules.holiday.service.ErpHolidaysService;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.sys.entity.JobNumberInfo;
import com.yunnex.ops.erp.modules.sys.entity.User;
import com.yunnex.ops.erp.modules.sys.service.JobNumberInfoService;
import com.yunnex.ops.erp.modules.sys.utils.UserUtils;
import com.yunnex.ops.erp.modules.workflow.flow.common.JykFlowConstants;
import com.yunnex.ops.erp.modules.workflow.flow.common.WorkFlowConstants;
import com.yunnex.ops.erp.modules.workflow.flow.dao.JykFlowDao;
import com.yunnex.ops.erp.modules.workflow.flow.entity.ErpOrderSubTask;
import com.yunnex.ops.erp.modules.workflow.flow.entity.FlowHistory;
import com.yunnex.ops.erp.modules.workflow.flow.entity.JykFlow;
import com.yunnex.ops.erp.modules.workflow.flow.entity.SubTaskHistory;
import com.yunnex.ops.erp.modules.workflow.flow.entity.TaskHistory;
import com.yunnex.ops.erp.modules.workflow.flow.from.WorkFlowQueryForm;
import com.yunnex.ops.erp.modules.workflow.user.constant.OrderFlowUserConstants;
import com.yunnex.ops.erp.modules.workflow.user.entity.ErpOrderFlowUser;
import com.yunnex.ops.erp.modules.workflow.user.service.ErpOrderFlowUserService;

/**
 * 任务流处理信息
 * 
 * @author yunnex
 * @date 2017年10月31日
 */
@Service
public class WorkFlowService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowService.class);
    
    @Value("${api_erp_diagnosis_summary}")
    private String API_ERP_DIAGNOSIS_SUMMARY;
    /** Activiti工作流处理服务 */
    @Autowired
    private ActTaskService actTaskService;
    /*** 聚引客业务处理服务 */
    @Autowired
    private JykFlowService jykFlowService;
    /** 订单分单信息表 */
    @Autowired
    private ErpOrderSplitInfoService erpOrderSplitInfoService;
    /** 表单服务 */
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ErpOrderFlowUserService erpOrderFlowUserService;
    @Autowired
    private TemplateServiceFreeMarkerImpl serviceFreeMarker;
    @Autowired
    private ErpOrderSubTaskService erpOrderSubTaskService;
    @Autowired
    private ErpHolidaysService erpHolidaysService;
    @Autowired
    private WorkFlowMonitorService workFlowMonitorService;
    @Autowired
    private JykFlowDao jykFlowDao;
    @Autowired
    private ActDefExtService actDefExtService;
    @Autowired
    private JobNumberInfoService jobNumberService;
    @Value("${domain.wxapp.res}")
    private String RES_DOMAIN;
    
    /**
     * 启动聚引客工作流
     *
     * @param paranningExpertInterface 策划专家接口
     * @param orderId 订单编号
     * @param splitId 分单编号
     * @param procInsId 流程编号
     * @date 2017年10月31日
     * @author yunnex
     */
    public boolean startJykWorkFlow(String paranningExpertInterface, String orderId, String splitId) {
        Map<String, Object> vars = Maps.newHashMap();
        // 设置任务处理人
        vars.put(JykFlowConstants.PLANNING_EXPERT_INTERFACE_MAN, paranningExpertInterface);
        // 启动流程
        String procInsId = actTaskService.startProcess(ActUtils.JYK_OPEN_ACCOUNT_FLOW[0], ActUtils.JYK_OPEN_ACCOUNT_FLOW[1], splitId,
                        "分单并指定策划专家接口人成功.", vars);
        LOGGER.info("聚引客工作流程启动成功|流程编号:{}", procInsId);
        JykFlow flow = new JykFlow();
        flow.setOrderId(orderId);
        flow.setSplitId(splitId);
        flow.setPlanningExpertInterface(paranningExpertInterface);
        flow.setProcInsId(procInsId);
        jykFlowService.save(flow);
        LOGGER.info("聚引客流程信息表保存成功|对象:{}", flow);

        Task task = actTaskService.getCurrentTaskInfo(this.actTaskService.getProcIns(procInsId));
        // 插入下一步所需要的
        insertSubTask(task.getId(), procInsId, paranningExpertInterface);

        return true;
    }

    /**
     * 
     * 根据条件获取我的待办列表
     * 
     * @param act
     * @param orderId 订单编号（支持模糊 查询）
     * @param taskStateList 任务状态列表
     * @param hurryFlag 订单加急标记
     * @param shopName 商户名称（支持模糊查询）
     * @return
     * @date 2017年10月31日
     * @author yunnex
     * @throws ParseException 
     */
    public List<Act> todoList(WorkFlowQueryForm form) throws ParseException {
        List<String> taskStateList = StringUtils.isNotBlank(form.getTaskStateList()) ? Arrays.asList(form.getTaskStateList().split(",")) : null;
        List<String> goodsType = StringUtils.isNotBlank(form.getGoodsType()) ? Arrays.asList(form.getGoodsType().split(",")) : null;
        Integer hurryFlag = StringUtils.isNotBlank(form.getHurryFlag()) ? Integer.parseInt(form.getHurryFlag()) : null;
        // 过滤分单信息表编号
        List<ErpOrderSplitInfo> orderSplitList = erpOrderSplitInfoService.findListByParams(form.getShopName(), form.getOrderNumber(), hurryFlag,
                        goodsType);
        // 获取流程待办信息列表
        List<Act> taskTodoList = actTaskService.todoList(new Act());
        //设置节点信息
        Map<String,String> a=new HashMap<String, String>();
        a.put("promotion_time_determination","promotion_time_determination");
        a.put("channel_confirm_business_license", "channel_confirm_business_license");
        a.put("channel_confirm_business_qualification", "channel_confirm_business_qualification");
        List<Act> taskTodoListFilter = new ArrayList<Act>();
        for (Act act : taskTodoList) {
            ErpOrderSplitInfo splitRow = null;
            // 查看列表中是否存在任务
            for (ErpOrderSplitInfo split : orderSplitList) {
                if (split.getAct().getProcInsId() == null) {
                    continue;
                }
                if (split.getAct().getProcInsId().equals(act.getBusinessId())) {
                    splitRow = split;
                }
            }
            // 任务列表中，不包含的分单编号
            if (splitRow == null) {
                LOGGER.debug("不符合条件过滤的任务Id为|ID:{}", act.getBusinessId());
                continue;
            }
            //与商户沟通推广时间
        	if("promotion_time_determination".equals(act.getTaskDefKey())){
                // 过滤设置了沟通时间，但未确定投放时间的订单
                if (splitRow.getNextContactTime() != null && splitRow.getPromotionTime() == null) {
                	Date now=new Date();
                	if(now.getTime()<splitRow.getNextContactTime().getTime()){
                        LOGGER.debug("待生产库订单|splitOrderInfo:{}", splitRow);
                        continue;
                	}
                	act.setTaskStarterDate(splitRow.getNextContactTime());
                }
                // 过滤投放日期距离现在时间超于20个工作日的订单任务
                if (splitRow.getPromotionTime() != null) {
                    Double distanceDays = DateUtils.getDistanceOfTwoDate(this.erpHolidaysService.enddate(new Date(),JykFlowConstants.PLANNING_DATE_DISTINCT*8), splitRow.getPromotionTime());
                    if (distanceDays.intValue() > 0) {
                        LOGGER.debug("待生产库订单|splitOrderInfo:{}", splitRow);
                        continue;
                    }
                }
        	}
        	//商户没有营业执照下次沟通时间
        	if("channel_confirm_business_license".equals(act.getTaskDefKey())){
                // 过滤设置了沟通时间，但未确定投放时间的订单
                if (splitRow.getNextLicenseTime() != null) {
                	Date now=new Date();
                	if(now.getTime()<splitRow.getNextLicenseTime().getTime()){
                        LOGGER.debug("待生产库订单|splitOrderInfo:{}", splitRow);
                        continue;
                	}
                	act.setTaskStarterDate(splitRow.getNextLicenseTime());
                }
        	}
        	 //商户资质是否齐全下次沟通时间
        	if("channel_confirm_business_qualification".equals(act.getTaskDefKey())){
                // 过滤设置了沟通时间，但未确定投放时间的订单
                if (splitRow.getNextQualificationTime() != null) {
                	Date now=new Date();
                	if(now.getTime()<splitRow.getNextQualificationTime().getTime()){
                        LOGGER.debug("待生产库订单|splitOrderInfo:{}", splitRow);
                        continue;
                	}
                	act.setTaskStarterDate(splitRow.getNextQualificationTime());
                }
        	}
            setFormProperty(act);
            int taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
            act.setTaskConsumTime(computingTaskSchedule(act.getTaskStarterDate(), taskHours));
            boolean flag = filterTaskState(taskStateList, act, splitRow);
            try {
                act.setTaskBusinessEndDate(erpHolidaysService.enddate(act.getTaskStarterDate(), taskHours));
            } catch (ParseException e) {
                act.setTaskBusinessEndDate(null);
            }
            if (flag) {
                taskTodoListFilter.add(act);
            }
            
        }
        LOGGER.info("条件过滤后剩余的任务列表为|actList:{}", taskTodoList);
        return taskTodoListFilter;
    }

    private boolean filterTaskState(List<String> taskStateList, Act act, ErpOrderSplitInfo splitRow) {
        // 设置表单属性
        setFormProperty(act);
        // 正常
        if (act.getTaskConsumTime() < 80) {
            act.setTaskState(WorkFlowConstants.NORMAL);
            if (taskStateList != null && !taskStateList.isEmpty() && !taskStateList.contains(WorkFlowConstants.NORMAL)) {
                return false;
            }
            // 既将到期
        } else if (act.getTaskConsumTime() >= 80 && act.getTaskConsumTime() < 100) {
            act.setTaskState(WorkFlowConstants.ONLY_WILL_OVER_TIME);
            if (taskStateList != null && !taskStateList.isEmpty() && !taskStateList.contains(WorkFlowConstants.ONLY_WILL_OVER_TIME)) {
                return false;
            }
            // 超时
        } else if (act.getTaskConsumTime() >= 100) {
            act.setTaskState(WorkFlowConstants.OVER_TIME);
            if (taskStateList != null && !taskStateList.isEmpty() && !taskStateList.contains(WorkFlowConstants.OVER_TIME)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 根据条件获取我的关注列表
     * 
     * @param act
     * @param orderId 订单编号（支持模糊 查询）
     * @param taskStateList 任务状态列表
     * @param hurryFlag 订单加急标记
     * @param shopName 商户名称（支持模糊查询）
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    public List<Act> getFollowTaskList(WorkFlowQueryForm form) {
        List<String> taskStateList = StringUtils.isNotBlank(form.getTaskStateList()) ? Arrays.asList(form.getTaskStateList().split(",")) : null;
        List<String> goodsType = StringUtils.isNotBlank(form.getGoodsType()) ? Arrays.asList(form.getGoodsType().split(",")) : null;
        Integer hurryFlag = StringUtils.isNotBlank(form.getHurryFlag()) ? Integer.parseInt(form.getHurryFlag()) : null;
        // 过滤分单信息表编号
        List<String> orderSplitList = erpOrderSplitInfoService.findFollowOrderByParams(form.getShopName(), form.getOrderNumber(), hurryFlag,
                        goodsType);
        List<Act> actList = new ArrayList<Act>();
        // 流程编号
        for (String procInsId : orderSplitList) {
            Act act;
            ProcessInstance processInstance = this.actTaskService.getProcIns(procInsId);
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
            for (Task task : tasks) {
                act = new Act();
                act.setTask(task);
                // 如果当前任务的处理人为登录用户，则不显示在我的关注列表中
                if (task.getAssignee().equals(UserUtils.getUser().getId())) {
                    continue;
                }
                act.setBusinessId(procInsId);
                act.setTaskId(task.getId());
                act.setVars(task.getProcessVariables());
                // 设置表单属性
                setFormProperty(act);
                ErpOrderSplitInfo splitRow = erpOrderSplitInfoService.getByProsIncId(procInsId);
                int taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
                HistoricTaskInstanceEntity historicTask = (HistoricTaskInstanceEntity) historyService.createHistoricTaskInstanceQuery()
                                .taskId(task.getId()).singleResult();
                act.setTaskStarterDate(historicTask.getStartTime());
                act.setTaskConsumTime(computingTaskSchedule(act.getTaskStarterDate(), taskHours));
                boolean flag = filterTaskState(taskStateList, act, splitRow);
                try {
                    act.setTaskBusinessEndDate(erpHolidaysService.enddate(act.getTaskStarterDate(), taskHours));
                } catch (ParseException e) {
                    act.setTaskBusinessEndDate(null);
                }
                if (flag) {
                    actList.add(act);
                }
            }
        }
        return actList;
    }


    /**
     * 
     * 根据条件获取待生产库订单
     * 
     * @param orderId 订单编号（支持模糊 查询）
     * @param taskStateList 任务状态列表
     * @param hurryFlag 订单加急标记
     * @param shopName 商户名称（支持模糊查询）
     * @return
     * @date 2017年10月31日
     * @author yunnex
     * @throws ParseException 
     */
    public List<Act> getPendingProductionTaskList(WorkFlowQueryForm form) throws ParseException {
        List<String> taskStateList = StringUtils.isNotBlank(form.getTaskStateList()) ? Arrays.asList(form.getTaskStateList().split(",")) : null;
        List<String> goodsType = StringUtils.isNotBlank(form.getGoodsType()) ? Arrays.asList(form.getGoodsType().split(",")) : null;
        Integer hurryFlag = StringUtils.isNotBlank(form.getHurryFlag()) ? Integer.parseInt(form.getHurryFlag()) : null;
        // 过滤分单信息表编号
        List<ErpOrderSplitInfo> orderSplitList = erpOrderSplitInfoService.findListByParams(form.getShopName(), form.getOrderNumber(), hurryFlag,
                        goodsType);
        // 获取流程待办信息列表
        List<Act> taskTodoList = actTaskService.todoList(new Act());
        //设置节点信息
        Map<String,String> a=new HashMap<String, String>();
        a.put("promotion_time_determination","promotion_time_determination");
        a.put("channel_confirm_business_license", "channel_confirm_business_license");
        a.put("channel_confirm_business_qualification", "channel_confirm_business_qualification");
        List<Act> taskTodoListFilter = new ArrayList<Act>();
        for (Act act : taskTodoList) {
            ErpOrderSplitInfo splitRow = null;
            // 查看列表中是否存在任务
            for (ErpOrderSplitInfo split : orderSplitList) {
                if (split.getAct().getProcInsId() == null) {
                    continue;
                }
                if (split.getAct().getProcInsId().equals(act.getBusinessId())) {
                    splitRow = split;
                }
            }
            for(Map.Entry<String, String> entry : a.entrySet()){
	            if (!entry.getValue().toString().equals(act.getTaskDefKey())) {
	                continue;
	            }
	            // 任务列表中，不包含的分单编号
	            if (splitRow == null) {
                    LOGGER.debug("不符合条件过滤的任务Id为|ID:{}", act.getBusinessId());
	                continue;
	            }
	            boolean isAddFlag = false;
                if ("promotion_time_determination".equals(entry.getValue())) {
		            // 过滤设置了沟通时间，但未确定投放时间的订单
		            if (splitRow.getNextContactTime() != null && splitRow.getPromotionTime() == null) {
		            	Date now=new Date();
		            	if(now.getTime()<splitRow.getNextContactTime().getTime()){
		            		act.setTaskStarterDate(splitRow.getNextContactTime());
		            		isAddFlag = true;
		            	}
		                
		            }
		            // 过滤投放日期距离现在时间超于20个工作日的订单任务
		            if (splitRow.getPromotionTime() != null) {
		                Double distanceDays = DateUtils.getDistanceOfTwoDate(this.erpHolidaysService.enddate(new Date(),JykFlowConstants.PLANNING_DATE_DISTINCT*8), splitRow.getPromotionTime());
		                if (distanceDays.intValue() > 0) {
		                	act.setTaskStarterDate(splitRow.getNextContactTime());
		                    isAddFlag = true;
		                }
		            }
	            }
	            
	          //商户没有营业执照下次沟通时间
                if ("channel_confirm_business_license".equals(entry.getValue())) {
		            if (splitRow.getNextLicenseTime()!= null) {
		            	Date now=new Date();
		            	if(now.getTime()<splitRow.getNextLicenseTime().getTime()){
		            		act.setTaskStarterDate(splitRow.getNextLicenseTime());
		            		isAddFlag = true;
		            	}
		                
		            }
	            }
	            //商户资质是否齐全下次沟通时间
                if ("channel_confirm_business_qualification".equals(entry.getValue())) {
		            if (splitRow.getNextQualificationTime()!= null) {
		            	Date now=new Date();
		            	if(now.getTime()<splitRow.getNextQualificationTime().getTime()){
		            		act.setTaskStarterDate(splitRow.getNextQualificationTime());
		            		isAddFlag = true;
		            	}
		                
		            }
	            }
	            if (isAddFlag) {
	                setFormProperty(act);
	                int taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
	                act.setTaskConsumTime(0);
	                boolean flag = filterTaskState(taskStateList, act, splitRow);
	                try {
	                    act.setTaskBusinessEndDate(erpHolidaysService.enddate(act.getTaskStarterDate(), taskHours));
	                } catch (ParseException e) {
	                    act.setTaskBusinessEndDate(null);
	                }
	                if (flag) {
	                    taskTodoListFilter.add(act);
	                }
	            }
            }
        }
        
        LOGGER.info("待生产库的订单列表为|actList:{}", taskTodoList);
        return taskTodoListFilter;
    }

    /**
     * 
     * 根据条件获取我的关注列表
     * 
     * @param act
     * @param orderId 订单编号（支持模糊 查询）
     * @param taskStateList 任务状态列表
     * @param hurryFlag 订单加急标记
     * @param shopName 商户名称（支持模糊查询）
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    public Act getTaskDetailList(String taskId) {
        Act act = new Act();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        act.setTask(task);
        act.setBusinessId(task.getProcessInstanceId());
        act.setTaskId(task.getId());
        act.setVars(this.actTaskService.getProcessEngine().getRuntimeService().getVariables(task.getExecutionId()));
        // 设置表单属性
        setFormProperty(act);
        ErpOrderSplitInfo splitRow = erpOrderSplitInfoService.getByProsIncId(task.getProcessInstanceId());
        int taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
        HistoricTaskInstanceEntity historicTask = (HistoricTaskInstanceEntity) historyService.createHistoricTaskInstanceQuery().taskId(task.getId())
                        .singleResult();
        act.setTaskStarterDate(historicTask.getStartTime());
        act.setTaskConsumTime(computingTaskSchedule(act.getTaskStarterDate(), taskHours));
        try {
            act.setTaskBusinessEndDate(erpHolidaysService.enddate(act.getTaskStarterDate(), taskHours));
        } catch (ParseException e) {
            act.setTaskBusinessEndDate(null);
        }
        return act;
    }

    public Task getTaskById(String taskId) {
        return taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
    }

    /**
     * 完成服务，自动进入下一节点
     *
     * @param taskId
     * @param procInsId
     * @param comment
     * @param vars
     * @date 2017年11月2日
     * @author yunnex
     */
    public void completeFlow(String folwUserType, String taskId, String procInsId, String comment, Map<String, Object> vars) {
        if (vars == null) {
            vars = Maps.newHashMap();
        }
        String userId = this.erpOrderFlowUserService.findListByFlowId(procInsId, folwUserType).getUser().getId();
        vars.put("TaskUser", userId);
        this.actTaskService.complete(taskId, procInsId, comment, vars);
        // 插入下一步所需要的
        insertSubTask(taskId, procInsId, userId);
    }

    private void insertSubTask(String taskId, String procInsId, String userId) {
        ProcessInstance processInstance = this.actTaskService.getProcIns(procInsId);
        if (processInstance == null) {
            return;
        }
        List<Task> tasks = taskService.createTaskQuery().includeProcessVariables().processInstanceId(processInstance.getId()).list();

        for (Task task : tasks) {
            // 获取子任务显示
            Map<String, Object> freemarkerMap = new HashMap<String, Object>();
            freemarkerMap.put("vars", task.getProcessVariables());
            String taskStr = serviceFreeMarker.getContent(task.getTaskDefinitionKey() + ".ftl", freemarkerMap);
            Document doc = Jsoup.parseBodyFragment(taskStr);
            // 获取当前任务的子任务
            Elements elements = doc.getElementsByClass("subTask");
            for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
                Element e = (Element) it.next();
                if ((e.text() != null && !e.text().equals("")) && (e.attr("value") != null && !e.attr("value").equals(""))) {
                    ErpOrderSubTask subTask = new ErpOrderSubTask();
                    subTask.setTaskId(task.getId());
                    subTask.setSplitId(procInsId);
                    subTask.setSubTaskPerson(userId);
                    subTask.setState("0");
                    subTask.setSubTaskDetail(e.text());
                    subTask.setSubTaskId(e.attr("value"));
                    // 如果当前子任务已经存在，则不插入子任务信息表
                    if (this.erpOrderSubTaskService.getSubTask(task.getId(), e.attr("value")) == null) {
                        erpOrderSubTaskService.save(subTask);
                    }
                }
            }
        }
    }

    /**
     * 设置表单属性
     *
     * @param task
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    private Act setFormProperty(Act task) {
        // 获取工作流表单属性
        TaskFormData st = formService.getTaskFormData(task.getTaskId());
        List<FormProperty> fromProperty = st.getFormProperties();
        for (FormProperty formProperty : fromProperty) {
            if (WorkFlowConstants.TASK_DATE_HOURS.equals(formProperty.getName())) {
                task.setTaskDateHours(Integer.valueOf(formProperty.getId()));
            }
            if (WorkFlowConstants.URGENT_TASK_DATEHOURS.equals(formProperty.getName())) {
                task.setUrgentTaskDateHours(Integer.valueOf(formProperty.getId()));
            }
            // 如果表单属性中存在指派用户分组
            if (WorkFlowConstants.TASK_USER_ROLE.equals(formProperty.getName())) {
                task.setTaskUserRole(formProperty.getId());
            }
            if (WorkFlowConstants.TASK_USER_ROLE2.equals(formProperty.getName())) {
                task.setTaskUserRole2(formProperty.getId());
            }
        }
        if (task.getTaskDateHours() == null) {
            task.setTaskDateHours(0);
        }
        if (task.getUrgentTaskDateHours() == null) {
            task.setUrgentTaskDateHours(0);
        }
        return task;
    }


    /***
     * 计算任务进度
     *
     * @param staterDate
     * @param taskHours
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    private static Integer computingTaskSchedule(Date staterDate, Integer taskHours) {
        float startDateLong = (float) new Date().getTime() - staterDate.getTime();
        float taskHoursLong = (taskHours * 60 * 60 * 1000);
        return (int) ((startDateLong / taskHoursLong) * 100);
    }

    /**
     * 确认子任务完成
     *
     * @param procInsId
     * @param channelList
     * @param taskId
     * @date 2017年11月5日
     * @author yunnex
     */
    @Transactional(readOnly = false)
    public void submitSubTask(String procInsId, String channelList, String taskId) {
        if (StringUtils.isBlank(channelList)) {
            return;
        }
        String[] channel = channelList.split(",");
        // 先将任务所有状态复原，再更新
        this.erpOrderSubTaskService.updateTaskState(taskId, "0");
        for (int i = 0; i < channel.length; i++) {
            // 修改子任务完成状态
            this.erpOrderSubTaskService.updateState(taskId, channel[i]);
        }
    }


    /**
     * 任务进度跟踪
     *
     * @param taskId
     * @return
     * @date 2017年11月5日
     * @author yunnex
     */
    public FlowHistory getTaskHistoicFlow(String procInsId) {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
                        .orderByHistoricActivityInstanceEndTime().desc().list();
        FlowHistory history = new FlowHistory();
        List<TaskHistory> taskHistoryList = new ArrayList<TaskHistory>();
        List<SubTaskHistory> subTaskHistoryList;
        TaskHistory taskHistory;
        SubTaskHistory subTaskHistory;
        for (int i = 0; i < list.size(); i++) {
            HistoricActivityInstance histIns = list.get(i);
            if (StringUtils.isBlank(histIns.getAssignee())) {
                continue;
            }
            subTaskHistoryList = new ArrayList<SubTaskHistory>();
            taskHistory = new TaskHistory();
            taskHistory.setSort(histIns.getEndTime() != null ? Integer.MAX_VALUE-i : Integer.MAX_VALUE);
            taskHistory.setIsFinished(histIns.getEndTime() != null ? 1 : 0);
            taskHistory.setTaskTime(histIns.getEndTime());
            taskHistory.setStartTaskTime(histIns.getStartTime());
            taskHistory.setTaskId(histIns.getTaskId());
            taskHistory.setTaskPerson(UserUtils.get(histIns.getAssignee()).getName());
            taskHistory.setTaskName(histIns.getActivityName());
            List<ErpOrderSubTask> subTaskList = this.erpOrderSubTaskService.getSubTaskList(histIns.getTaskId());
            for (ErpOrderSubTask erpOrderSubTask : subTaskList) {
                subTaskHistory = new SubTaskHistory();
                subTaskHistory.setIsFinished(erpOrderSubTask.getState());
                subTaskHistory.setStartTaskTime(histIns.getStartTime());
                subTaskHistory.setTaskName(erpOrderSubTask.getSubTaskDetail());
                subTaskHistory.setTaskPerson(UserUtils.get(histIns.getAssignee()).getName());
                subTaskHistory.setTaskTime(erpOrderSubTask.getUpdateDate());
                subTaskHistoryList.add(subTaskHistory);
            }
            taskHistory.setSubTaskHistroy(subTaskHistoryList);
            taskHistoryList.add(taskHistory);
        }

        Collections.sort(taskHistoryList, new Comparator<TaskHistory>() {
            
            // 重写排序规则
            public int compare(TaskHistory o1, TaskHistory o2) {
                if(o1.getIsFinished()==0){
                    return -1;
                }
                return o2.getSort()-o1.getSort();
            }
        });

        history.setTaskHistory(taskHistoryList);

        return history; 
    }

    /**
     * 
     * 根据条件获取我的待办列表
     * 
     * @param act
     * @param orderId 订单编号（支持模糊 查询）
     * @param taskStateList 任务状态列表
     * @param hurryFlag 订单加急标记
     * @param shopName 商户名称（支持模糊查询）
     * @return
     * @date 2017年10月31日
     * @author yunnex
     * @throws ParseException 
     */
    public List<Act> todoTeamList(WorkFlowQueryForm form, List<String> userIds) throws ParseException {
        List<String> taskStateList = StringUtils.isNotBlank(form.getTaskStateList()) ? Arrays.asList(form.getTaskStateList().split(",")) : null;
        List<String> goodsType = StringUtils.isNotBlank(form.getGoodsType()) ? Arrays.asList(form.getGoodsType().split(",")) : null;
        Integer hurryFlag = StringUtils.isNotBlank(form.getHurryFlag()) ? Integer.parseInt(form.getHurryFlag()) : null;
        // 过滤分单信息表编号
        List<ErpOrderSplitInfo> orderSplitList = erpOrderSplitInfoService.findListByParams(form.getShopName(), form.getOrderNumber(), hurryFlag,
                        goodsType);
        // 获取流程待办信息列表
        List<Act> taskTodoList = actTaskService.todoTeamList(userIds);
        List<Act> taskTodoListFilter = new ArrayList<Act>();
        for (Act act : taskTodoList) {
            ErpOrderSplitInfo splitRow = null;
            // 查看列表中是否存在任务
            for (ErpOrderSplitInfo split : orderSplitList) {
                if (split.getAct().getProcInsId() == null) {
                    continue;
                }
                if (split.getAct().getProcInsId().equals(act.getBusinessId())) {
                    splitRow = split;
                }
            }
            // 任务列表中，不包含的分单编号
            if (splitRow == null) {
                LOGGER.debug("不符合条件过滤的任务Id为|ID:{}", act.getBusinessId());
                continue;
            }
            if ("promotion_time_determination".equals(act.getTaskDefKey())) {
                // 过滤设置了沟通时间，但未确定投放时间的订单
                if (splitRow.getNextContactTime() != null && splitRow.getPromotionTime() == null) {
                    LOGGER.debug("待生产库订单|splitOrderInfo:{}", splitRow);
                    continue;
                }
                // 过滤投放日期距离现在时间超于20个工作日的订单任务
                if (splitRow.getPromotionTime() != null) {
                    Double distanceDays = DateUtils.getDistanceOfTwoDate(this.erpHolidaysService.enddate(new Date(),JykFlowConstants.PLANNING_DATE_DISTINCT*8), splitRow.getPromotionTime());
                    if (distanceDays.intValue() > 0) {
                        LOGGER.debug("待生产库订单|splitOrderInfo:{}", splitRow);
                        continue;
                    }
                }
            }
            setFormProperty(act);
            int taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
            act.setTaskConsumTime(computingTaskSchedule(act.getTaskStarterDate(), taskHours));
            boolean flag = filterTaskState(taskStateList, act, splitRow);
            try {
                act.setTaskBusinessEndDate(erpHolidaysService.enddate(act.getTaskStarterDate(), taskHours));
            } catch (ParseException e) {
                act.setTaskBusinessEndDate(null);
            }
            if (flag) {
                taskTodoListFilter.add(act);
            }
        }
        LOGGER.info("条件过滤后剩余的任务列表为|actList:{}", taskTodoList);
        return taskTodoListFilter;
    }

    /**
     * 
     * 根据条件获取待生产库订单
     * 
     * @param orderId 订单编号（支持模糊 查询）
     * @param taskStateList 任务状态列表
     * @param hurryFlag 订单加急标记
     * @param shopName 商户名称（支持模糊查询）
     * @return
     * @date 2017年10月31日
     * @author yunnex
     * @throws ParseException 
     */
    public List<Act> getPendingProductionTeamTaskList(WorkFlowQueryForm form, List<String> userIds) throws ParseException {
        List<String> taskStateList = StringUtils.isNotBlank(form.getTaskStateList()) ? Arrays.asList(form.getTaskStateList().split(",")) : null;
        List<String> goodsType = StringUtils.isNotBlank(form.getGoodsType()) ? Arrays.asList(form.getGoodsType().split(",")) : null;
        Integer hurryFlag = StringUtils.isNotBlank(form.getHurryFlag()) ? Integer.parseInt(form.getHurryFlag()) : null;
        // 过滤分单信息表编号
        List<ErpOrderSplitInfo> orderSplitList = erpOrderSplitInfoService.findListByParams(form.getShopName(), form.getOrderNumber(), hurryFlag,
                        goodsType);
        // 获取流程待办信息列表
        List<Act> taskTodoList = actTaskService.todoTeamList(userIds);
        List<Act> taskTodoListFilter = new ArrayList<Act>();
        for (Act act : taskTodoList) {
            ErpOrderSplitInfo splitRow = null;
            // 查看列表中是否存在任务
            for (ErpOrderSplitInfo split : orderSplitList) {
                if (split.getAct().getProcInsId() == null) {
                    continue;
                }
                if (split.getAct().getProcInsId().equals(act.getBusinessId())) {
                    splitRow = split;
                }
            }
            // 任务列表中，不包含的分单编号
            if (splitRow == null) {
                LOGGER.debug("不符合条件过滤的任务Id为|ID:{}", act.getBusinessId());
                continue;
            }

            if (!"promotion_time_determination".equals(act.getTaskDefKey())) {
                continue;
            }
            boolean isAddFlag = false;
            // 过滤设置了沟通时间，但未确定投放时间的订单
            if (splitRow.getNextContactTime() != null && splitRow.getPromotionTime() == null) {
                isAddFlag = true;
            }
            // 过滤投放日期距离现在时间超于20个工作日的订单任务
            if (splitRow.getPromotionTime() != null) {
                Double distanceDays = DateUtils.getDistanceOfTwoDate(this.erpHolidaysService.enddate(new Date(),JykFlowConstants.PLANNING_DATE_DISTINCT*8), splitRow.getPromotionTime());
                if (distanceDays.intValue() > 0) {
                    isAddFlag = true;
                }
            }
            if (isAddFlag) {
                setFormProperty(act);
                int taskHours = splitRow.getHurryFlag() == ErpOrderSplitInfo.HURRY_FLAG_YES ? act.getUrgentTaskDateHours() : act.getTaskDateHours();
                act.setTaskConsumTime(computingTaskSchedule(act.getTaskStarterDate(), taskHours));
                boolean flag = filterTaskState(taskStateList, act, splitRow);
                try {
                    act.setTaskBusinessEndDate(erpHolidaysService.enddate(act.getTaskStarterDate(), taskHours));
                } catch (ParseException e) {
                    act.setTaskBusinessEndDate(null);
                }


                if (flag) {
                    taskTodoListFilter.add(act);
                }
            }
        }
        LOGGER.info("待生产库的订单列表为|actList:{}", taskTodoList);
        getFollowTaskList(form);
        return taskTodoListFilter;
    }

    public void setVariable(String taskId, String variableName, String value) {
        this.actTaskService.getProcessEngine().getTaskService().setVariable(taskId, variableName, value);

    }

    public ProcessEngine getProcessEngine() {
        return this.actTaskService.getProcessEngine();
    }

    /**
     * 跳转（包括回退和向前）至指定活动节点
     */
    public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
        this.actTaskService.jumpTask(procInsId, currentTaskId, targetTaskDefinitionKey, variables);
        // 插入下一步所需要的
        insertSubTask(currentTaskId, procInsId, UserUtils.getUser().getId());
    }
    
    /**
     * 获取某个流程下的所有用户任务，不包括子流程的任务
     *
     * @param procInsId
     * @return
     * @date 2017年11月28日
     * @author hsr
     */
    public List<Map<String, Object>> getProcInsTasks(String procInsId) {
        // 所有任务
        List<ActivityImpl> activities = workFlowMonitorService.getProcessNodes(procInsId);
        // 当前任务
        List<Task> currTasks = workFlowMonitorService.getCurrentTasks(procInsId);
        // 任务的处理人
        List<Map<String, Object>> assignees = jykFlowDao.findTaskAssignee(procInsId);
        // 封装跳转任务列表数据
        List<Map<String, Object>> jumpTasks = new ArrayList<Map<String,Object>>();
        if (!CollectionUtils.isEmpty(assignees)) {
            for (ActivityImpl activity : activities) {
                // 排除分支为“不是新门店”的终点
                if ("sid-E3A66D76-38E1-42F0-85CC-8A95DF374C3A".equals(activity.getId())) {
                    continue;
                }
                Map<String, Object> jumpTask = Maps.newHashMap();
                jumpTask.put("id", activity.getId());   // 任务ID
                jumpTask.put("taskName", activity.getProperties().get("name")); // 任务名称
                for (Map<String, Object> map : assignees) {
                    if (map.get("act_id_").equals(activity.getId())) {
                        // 任务负责人
                        jumpTask.put("userId", map.get("userId"));
                        jumpTask.put("assignee", map.get("username"));
                    }
                }
                // 给当前任务添加标识
                for (Task task : currTasks) {
                    if (activity.getId().equals(task.getTaskDefinitionKey()))
                        jumpTask.put("current", 1);
                }
                jumpTasks.add(jumpTask);
            }
        }
        return jumpTasks;
    }
    
    /**
     * 跳转任务，并将原来旧的任务ID改为跳转后的新任务ID，以获取旧任务数据进行回显和修改
     *
     * @param procInsId
     * @param taskId
     * @throws Exception
     * @date 2017年12月1日
     * @author hsr
     */
    @Transactional(readOnly = false)
    public void jumpTask(String procInsId, String taskId, String userId) throws Exception {
        // 设置处理/负责人
        Map<String, Object> vars = Maps.newHashMap();
        ActDefExt actDefExt = actDefExtService.getByActId(taskId);  // 获取任务负责人变量名
        vars.put(actDefExt.getAssignee(), userId);
        
        // 将指定任务变为当前任务
        workFlowMonitorService.jump(procInsId, vars, taskId);
        
        // 获取旧任务
        HistoricTaskInstance lastHistoryTask = actTaskService.getLastHistoryTask(procInsId, taskId);
        // 以前没有处理过该任务,不需要修改旧任务ID
        if (lastHistoryTask == null)
            return;

        // 获取新（当前）任务
        List<Task> currentTasks = workFlowMonitorService.getCurrentTasks(procInsId);
        String currTaskId = null;
        for (Task task : currentTasks) {
            if (task.getTaskDefinitionKey().equals(taskId)) {
                currTaskId = task.getId();
            }
        }
        if (currTaskId != null) {
            // 修改旧任务ID为新任务ID(子任务表)
            erpOrderSubTaskService.updateTaskId(currTaskId, lastHistoryTask.getId());
        }
    }
    
    /**
     * 获取经营诊断相关数据
     *
     * @param splitId
     * @return
     * @date 2018年1月26日
     */
    public Map<String, Object> getDiagnosisInfo(String splitId, String roleId) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("errorCode", "0");
        result.put("errorMsg", "成功!");
        ErpOrderSplitInfo erpOrderSplitInfo = erpOrderSplitInfoService.get(splitId);
        if (erpOrderSplitInfo == null) {
            result.put("errorCode", "1");
            result.put("errorMsg", "不存在此分单: " + splitId);
            return result;
        }
        if (erpOrderSplitInfo.getPublishToWxapp() != 1) {
            result.put("errorCode", "2");
            result.put("errorMsg", "未发布到小程序!");
            return result;
        }
        
        // 获取处理人相关信息
        JobNumberInfo jobNumberInfo = null;
        // 获取决策专家
        ErpOrderFlowUser planningFlowUser = erpOrderFlowUserService.findByProcInsIdAndRoleName(erpOrderSplitInfo.getProcInsId(),
                        OrderFlowUserConstants.FLOW_USER_PLANNING_EXPERT);;
        if (planningFlowUser != null) {
            User user = planningFlowUser.getUser();
            String userId = user == null ? null : user.getId();
            if (StringUtils.isNotBlank(userId)) {
                // 策划专家信息
                jobNumberInfo = jobNumberService.getByUserId(userId);
            }
        }
        
        if (jobNumberInfo != null) {
            jobNumberInfo.setIconImg(RES_DOMAIN + "/" + jobNumberInfo.getIconImg());
        }
        
        Object diagnosisInfo = null;
        try {
            // 从ERP获取经营诊断数据
            String url = String.format(API_ERP_DIAGNOSIS_SUMMARY, splitId, StringUtils.isNotBlank(roleId) ? roleId : "3");
            String response = HttpUtil.sendHttpGetReqToServer(url);
            JSONObject jsonObject = JSONObject.parseObject(response);
            diagnosisInfo = jsonObject.get("attach");
        } catch (Exception e) {
            String msg = "获取ERP商户版经营诊断数据出错! ";
            result.put("errorCode", "-1");
            result.put("errorMsg", msg);
            LOGGER.error(msg, e);
        }
        
        result.put("jobNumberInfo", jobNumberInfo);
        result.put("diagnosisInfo", diagnosisInfo);
        return result;
    }

}
