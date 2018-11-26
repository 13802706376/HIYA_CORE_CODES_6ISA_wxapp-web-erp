package com.yunnex.ops.erp.modules.message.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.service.BaseService;
import com.yunnex.ops.erp.common.utils.DateUtils;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.act.service.ActTaskService;
import com.yunnex.ops.erp.modules.material.constant.MaterialCreationConstant;
import com.yunnex.ops.erp.modules.material.entity.ErpOrderMaterialCreation;
import com.yunnex.ops.erp.modules.material.service.ErpOrderMaterialCreationService;
import com.yunnex.ops.erp.modules.message.constant.ServiceProgressTemplateConstants;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitGoodService;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.shop.constant.ShopConstant;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.basic.service.ErpStoreInfoService;
import com.yunnex.ops.erp.modules.store.constant.StoreConstant;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayUnionpay;
import com.yunnex.ops.erp.modules.store.pay.service.ErpStorePayUnionpayService;
import com.yunnex.ops.erp.modules.sys.constants.SysConstantsConstants;
import com.yunnex.ops.erp.modules.sys.entity.JobNumberInfo;
import com.yunnex.ops.erp.modules.sys.entity.SysConstants;
import com.yunnex.ops.erp.modules.sys.service.JobNumberInfoService;
import com.yunnex.ops.erp.modules.sys.service.SysConstantsService;
import com.yunnex.ops.erp.modules.visit.constant.ErpVisitServiceConstants;
import com.yunnex.ops.erp.modules.visit.constant.VisitServiceItemConstants;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceInfo;
import com.yunnex.ops.erp.modules.visit.service.ErpVisitServiceInfoService;
import com.yunnex.ops.erp.modules.workflow.acceptance.entity.ErpServiceAcceptance;
import com.yunnex.ops.erp.modules.workflow.acceptance.service.ErpServiceAcceptanceService;
import com.yunnex.ops.erp.modules.workflow.channel.entity.JykOrderPromotionChannel;
import com.yunnex.ops.erp.modules.workflow.channel.enums.PromotionChannelType;
import com.yunnex.ops.erp.modules.workflow.channel.service.JykOrderPromotionChannelService;
import com.yunnex.ops.erp.modules.workflow.delivery.entity.ErpDeliveryService;
import com.yunnex.ops.erp.modules.workflow.delivery.service.ErpDeliveryServiceService;
import com.yunnex.ops.erp.modules.workflow.enums.FlowServiceType;
import com.yunnex.ops.erp.modules.workflow.flow.constant.FlowConstant;
import com.yunnex.ops.erp.modules.workflow.store.entity.JykOrderChoiceStore;
import com.yunnex.ops.erp.modules.workflow.store.service.JykOrderChoiceStoreService;
import com.yunnex.ops.erp.modules.workflow.user.constant.OrderFlowUserConstants;
import com.yunnex.ops.erp.modules.workflow.user.entity.ErpOrderFlowUser;
import com.yunnex.ops.erp.modules.workflow.user.service.ErpOrderFlowUserService;

@Service
@Transactional(readOnly = true)
public class ServiceProgressVariableService extends BaseService {

    @Autowired
    private ErpOrderFlowUserService orderFlowUserService;
    @Autowired
    private JobNumberInfoService jobNumberInfoService;
    @Autowired
    private JykOrderChoiceStoreService orderChoiceStoreService;
    @Autowired
    private ErpOrderSplitInfoService splitInfoService;
    @Autowired
    private ErpOrderSplitGoodService splitGoodService;
    @Autowired
    private ErpStoreInfoService erpStoreInfoService;
    @Autowired
    private JykOrderPromotionChannelService orderPromotionChannelService;
    @Autowired
    private ErpShopInfoService erpShopInfoService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private ErpVisitServiceInfoService visitServiceInfoService;
    @Autowired
    private ErpServiceAcceptanceService acceptanceService;
    @Autowired
    private ErpOrderMaterialCreationService materialCreationService;
    @Autowired
    private ErpDeliveryServiceService deliveryServiceService;
    @Autowired
    private ErpStorePayUnionpayService erpStorePayUnionpayService;
    @Autowired
    private SysConstantsService sysConstantsService;

    /**
     * 根据 服务类型 获取变量map
     *
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     * @param zhangbeiId
     */
    public Map<String, String> getVariableMap(String zhangbeiId, String procInsId, String serviceType) {
        Map<String, String> result = new HashMap<>();
        if (FlowServiceType.SPLIT_JU_YIN_KE.getType().equals(serviceType)) {
            result = getSplitJuYinKeVariableMap(zhangbeiId, procInsId);
        } else if (FlowServiceType.DELIVERY_FMPS.getType().equals(serviceType)) {
            result = getDeliveryFMPS(zhangbeiId, procInsId);
        } else if (FlowServiceType.DELIVERY_FMPS_BASIC.getType().equals(serviceType)) {
            result = getDeliveryFMPSBASIC(zhangbeiId, procInsId);
        } else if (FlowServiceType.DELIVERY_JYK.getType().equals(serviceType)) {
            result = getDeliveryJYK(zhangbeiId, procInsId);
        } else if (FlowServiceType.DELIVERY_INTO_PIECES.getType().equals(serviceType)) {
            result = getDeliveryINTOPIECES(zhangbeiId, procInsId);
        } else if (FlowServiceType.ZHI_HUI_CAN_TING.getType().equals(serviceType)) {
            result = getZhiHuiCanTingVariableMap(zhangbeiId, procInsId);
        }
        result.put("@ZhangbeiId@", zhangbeiId);
        return result;
    }

    private Map<String, String> getZhiHuiCanTingVariableMap(String zhangbeiId, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 商户信息
        ErpShopInfo shop = erpShopInfoService.getByZhangbeiId(zhangbeiId);
        String operationAdviserId = null;
        if (shop != null) {
            operationAdviserId = shop.getOperationAdviserId();
        }
        // 获取运营顾问
        ErpOrderFlowUser operationFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER);
        String operationUserId = null;// 运营顾问id
        if (operationFlowUser != null) {
            if (operationFlowUser.getUser() != null) {
                operationUserId = operationFlowUser.getUser().getId();
            }
        }
        if (StringUtils.isBlank(operationUserId)) {
            // 如果正在跑的流程中没指派运营顾问，取商户绑定运营顾问
            operationUserId = operationAdviserId;
        }
        String operationName = null;
        JobNumberInfo operationJob = jobNumberInfoService.getByUserId(operationUserId);
        if (operationJob != null) {
            operationName = operationJob.getJobNumber();
        }
        // ### 生成变量值 ###
        result.put("@OperationAdviserName@", StringUtils.toString(operationName, ""));
        return result;
    }

    private Map<String, String> getDeliveryINTOPIECES(String zhangbeiId, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 获取开户顾问
        ErpOrderFlowUser accountFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER);
        JobNumberInfo accountJob = null;
        String accountName = null;
        if (accountFlowUser != null) {
            if (accountFlowUser.getUser() != null) {
                accountJob = jobNumberInfoService.getByUserId(accountFlowUser.getUser().getId());
                if (accountJob != null) {
                    accountName = accountJob.getJobNumber();
                }
            }
        }
        // 获取商户信息
        ErpShopInfo shopInfo = erpShopInfoService.getByZhangbeiId(zhangbeiId);
        int storeCount = 0; // 门店总数

        if (shopInfo != null) {
            storeCount = shopInfo.getStoreCount();
        }
        // 获取主门店信息
        ErpStoreInfo storeInfo = erpStoreInfoService.getMainByZhangbeiId(zhangbeiId);
        ErpStorePayUnionpay unionPay = null;
        if (storeInfo != null) {
            unionPay = erpStorePayUnionpayService.get(storeInfo.getUnionpayId());
        }
        // 获取是否开通微信公众号
        boolean weixinOpen = actTaskService.taskIsFinish(procInsId, FlowConstant.PUBLIC_NUMBER_OPEN);
        boolean zhifubaoOpen = ShopConstant.ALIPA_STATE_OPEN.equals(shopInfo.getAlipaState());
        boolean yinlianOpen = unionPay != null && StoreConstant.PayAuditStatus.PASS.getStatus() == unionPay.getAuditStatus();
        String openContentStr = "您的掌贝平台、@weixin@微信支付、@zhifubao@@yinlian@已开通";
        // 开通文案
        String openServiceContent = getOpenServiceContent(openContentStr, weixinOpen, zhifubaoOpen, yinlianOpen);

        // ### 生成变量值 ###
        result.put("@AccountAdviserName@", StringUtils.toString(accountName, ""));
        result.put("@zhangbeiId@", StringUtils.toString(zhangbeiId, ""));
        result.put("@StoreCount@", StringUtils.toString(storeCount, "0"));
        result.put("@OpenServiceContent@", StringUtils.toString(openServiceContent, ""));
        return result;
    }

    private Map<String, String> getDeliveryJYK(String zhangbeiId, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 获取运营顾问
        ErpOrderFlowUser operationFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER);
        JobNumberInfo operationJob = null;
        String operationName = null;
        if (operationFlowUser != null) {
            if (operationFlowUser.getUser() != null) {
                operationJob = jobNumberInfoService.getByUserId(operationFlowUser.getUser().getId());
                if (operationJob != null) {
                    operationName = operationJob.getJobNumber();
                }
            }
        }
        // 获取开户顾问
        ErpOrderFlowUser accountFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER);
        JobNumberInfo accountJob = null;
        String accountName = null;
        if (accountFlowUser != null) {
            if (accountFlowUser.getUser() != null) {
                accountJob = jobNumberInfoService.getByUserId(accountFlowUser.getUser().getId());
                if (accountJob != null) {
                    accountName = accountJob.getJobNumber();
                }
            }
        }
        // 获取商户信息
        ErpShopInfo shopInfo = erpShopInfoService.getByZhangbeiId(zhangbeiId);
        int storeCount = 0; // 门店总数

        if (shopInfo != null) {
            storeCount = shopInfo.getStoreCount();
        }
        // 获取是否开通微信公众号
        boolean weixinOpen = actTaskService.taskIsFinish(procInsId, FlowConstant.PUBLIC_NUMBER_OPEN);
        boolean zhifubaoOpen = ShopConstant.ALIPA_STATE_OPEN.equals(shopInfo.getAlipaState());
        String openContentStr = "您的掌贝平台、@weixin@微信支付及广告主已开通";
        // 开通文案
        String openServiceContent = getOpenServiceContent(openContentStr, weixinOpen, zhifubaoOpen, false);

        // ### 生成变量值 ###
        result.put("@OperationAdviserName@", StringUtils.toString(operationName, ""));
        result.put("@AccountAdviserName@", StringUtils.toString(accountName, ""));
        result.put("@zhangbeiId@", StringUtils.toString(zhangbeiId, ""));
        result.put("@StoreCount@", StringUtils.toString(storeCount, "0"));
        result.put("@OpenServiceContent@", StringUtils.toString(openServiceContent, ""));
        return result;
    }

    private Map<String, String> getDeliveryFMPSBASIC(String zhangbeiId, String procInsId) {
        Map<String, String> result = new HashMap<>();
        result = getDeliveryFMPS(zhangbeiId, procInsId);
        return result;
    }

    /**
     * 获取 智能客流运营全套落地服务 变量集合
     *
     * @param procInsId
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     * @param
     */
    private Map<String, String> getDeliveryFMPS(String zhangbeiId, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 获取运营顾问
        ErpOrderFlowUser operationFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER);
        JobNumberInfo operationJob = null;
        String operationName = null;
        if (operationFlowUser != null) {
            if (operationFlowUser.getUser() != null) {
                operationJob = jobNumberInfoService.getByUserId(operationFlowUser.getUser().getId());
                if (operationJob != null) {
                    operationName = operationJob.getJobNumber();
                }
            }
        }
        // 获取开户顾问
        ErpOrderFlowUser accountFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER);
        JobNumberInfo accountJob = null;
        String accountName = null;
        if (accountFlowUser != null) {
            if (accountFlowUser.getUser() != null) {
                accountJob = jobNumberInfoService.getByUserId(accountFlowUser.getUser().getId());
                if (accountJob != null) {
                    accountName = accountJob.getJobNumber();
                }
            }
        }
        // 获取商户信息
        ErpShopInfo shopInfo = erpShopInfoService.getByZhangbeiId(zhangbeiId);
        int storeCount = 0; // 门店总数
        if (shopInfo != null) {
            storeCount = shopInfo.getStoreCount();
        }
        // 获取主门店信息
        ErpStoreInfo storeInfo = erpStoreInfoService.getMainByZhangbeiId(zhangbeiId);
        ErpStorePayUnionpay unionPay = null;
        if (storeInfo != null) {
            unionPay = erpStorePayUnionpayService.get(storeInfo.getUnionpayId());
        }
        // 获取是否开通微信公众号
        boolean weixinOpen = actTaskService.taskIsFinish(procInsId, FlowConstant.PUBLIC_NUMBER_OPEN);
        boolean zhifubaoOpen = ShopConstant.ALIPA_STATE_OPEN.equals(shopInfo.getAlipaState());
        boolean yinlianOpen = unionPay != null && StoreConstant.PayAuditStatus.PASS.getStatus() == unionPay.getAuditStatus();
        String openContentStr = "您的掌贝平台、@weixin@微信支付、@zhifubao@@yinlian@已开通";
        // 开通文案
        String openServiceContent = getOpenServiceContent(openContentStr, weixinOpen, zhifubaoOpen, yinlianOpen);
        String materialStatus = null;// 物料状态
        ErpOrderMaterialCreation materialCreation = materialCreationService.getByProcInsId(procInsId);
        if (materialCreation != null) {
            materialStatus = materialCreation.getStatus();
        }
        ErpDeliveryService deliveryService = deliveryServiceService.getDeliveryInfoByProsIncId(procInsId);
        Date startTime = null;
        if (deliveryService != null) {
            startTime = deliveryService.getStartTime();
        }
        // 物料文案
        String materialContent = getMaterialContent(startTime, materialStatus);
        // ### 生成变量值 ###
        result.put("@OperationAdviserName@", StringUtils.toString(operationName, ""));
        result.put("@AccountAdviserName@", StringUtils.toString(accountName, ""));
        result.put("@zhangbeiId@", StringUtils.toString(zhangbeiId, ""));
        result.put("@StoreCount@", StringUtils.toString(storeCount, "0"));
        result.put("@OpenServiceContent@", StringUtils.toString(openServiceContent, ""));
        result.put("@MaterialContent@", StringUtils.toString(materialContent, ""));
        return result;
    }

    /**
     * 获取物料内容
     *
     * @param startTime
     * @param materialStatus
     * @return
     * @date 2018年7月24日
     * @author linqunzhi
     */
    private String getMaterialContent(Date startTime, String materialStatus) {
        if (MaterialCreationConstant.ARRIVED.equals(materialStatus)) {
            return "您的物料已经到店，请您签收前，务必同物料清单核对无误";
        }
        boolean materialDelay = materialDelay(startTime);
        if (materialDelay) {
            return "因物流原因，导致您的物料未能及时到店，请您耐心等候，我们会及时帮您处理";
        }
        return "/";
    }

    private boolean materialDelay(Date startTime) {
        if (startTime == null) {
            return false;
        }
        SysConstants sysConstants = sysConstantsService.getByKey(SysConstantsConstants.MESSAGE_MATERIAL_DELAY_DAYS);
        if (sysConstants == null) {
            return false;
        }
        // 间隔小时
        int value = NumberUtils.toInt(sysConstants.getValue(), -1);
        if (value < 0) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.HOUR_OF_DAY, value);
        if (new Date().after(cal.getTime())) {
            return true;
        }
        return false;
    }

    /**
     * 获取开通文案
     *
     * @param openContentStr
     * @param weixinOpen
     * @param zhifubaoOpen
     * @param yinlianOpen
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    private String getOpenServiceContent(String openContentStr, boolean weixinOpen, boolean zhifubaoOpen, boolean yinlianOpen) {
        if (openContentStr == null) {
            return null;
        }
        String weixinStr = weixinOpen ? "微信公众号、" : "";
        String zhifubaoStr = zhifubaoOpen ? "支付宝口碑、" : "";
        String yinlianStr = yinlianOpen ? "银联支付" : "";
        String result = openContentStr.replace("@weixin@", weixinStr).replace("@zhifubao@", zhifubaoStr).replace("@yinlian@", yinlianStr);
        // 去除最后一个顿号
        result = result.replace("、已开通", "已开通");
        return result;
    }

    /**
     * 获取引流服务 变量集合
     *
     * @param procInsId
     * @return
     * @date 2018年7月18日
     * @author linqunzhi
     * @param procInsId2
     */
    private Map<String, String> getSplitJuYinKeVariableMap(String zhangbeiId, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 获取分单信息
        ErpOrderSplitInfo splitInfo = splitInfoService.getByProsIncId(procInsId);
        String splitId = null; // 分单id
        Date promotionTime = null;// 推广上线时间
        String lookEffectFlag = null;// 是否查看过效果报告
        String commentFlag = CommonConstants.Sign.NO;// 是否评价过
        if (splitInfo != null) {
            splitId = splitInfo.getId();
            promotionTime = splitInfo.getPromotionTime();
            lookEffectFlag = splitInfo.getLookEffectFlag();
            commentFlag = splitInfo.getCommentCount() > 0 ? CommonConstants.Sign.YES : CommonConstants.Sign.NO;
        }
        // 商户信息
        ErpShopInfo shop = erpShopInfoService.getByZhangbeiId(zhangbeiId);
        String operationAdviserId = null;
        if (shop != null) {
            operationAdviserId = shop.getOperationAdviserId();
        }
        // 获取运营顾问
        ErpOrderFlowUser operationFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER);
        String operationUserId = null;// 运营顾问id
        if (operationFlowUser != null) {
            if (operationFlowUser.getUser() != null) {
                operationUserId = operationFlowUser.getUser().getId();
            }
        }
        if (StringUtils.isBlank(operationUserId)) {
            // 如果正在跑的流程中没指派运营顾问，取商户绑定运营顾问
            operationUserId = operationAdviserId;
        }
        String operationName = null;
        JobNumberInfo operationJob = jobNumberInfoService.getByUserId(operationUserId);
        if (operationJob != null) {
            operationName = operationJob.getJobNumber();
        }
        // 获取开户顾问
        ErpOrderFlowUser accountFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER);
        JobNumberInfo accountJob = null;
        String accountName = null;
        if (accountFlowUser != null) {
            if (accountFlowUser.getUser() != null) {
                accountJob = jobNumberInfoService.getByUserId(accountFlowUser.getUser().getId());
                if (accountJob != null) {
                    accountName = accountJob.getJobNumber();
                }
            }
        }
        // 获取决策专家
        ErpOrderFlowUser planningFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_PLANNING_EXPERT);
        JobNumberInfo planningJob = null;
        String planningName = null;
        if (planningFlowUser != null) {
            if (planningFlowUser.getUser() != null) {
                planningJob = jobNumberInfoService.getByUserId(planningFlowUser.getUser().getId());
                if (planningJob != null) {
                    planningName = planningJob.getJobNumber();
                }
            }
        }

        // 获取分单商品信息
        List<ErpOrderSplitGood> splitGoodList = splitGoodService.findListBySplitId(splitId);
        // 预计推广周期
        String promotionCycle = getPromotionCycle(splitGoodList);
        // 获取门店推广信息
        JykOrderChoiceStore choiceStore = orderChoiceStoreService.getBySplitId(splitId);
        String storeId = null;
        if (choiceStore != null) {
            storeId = choiceStore.getChoiceStore();
        }
        // 获取门店详情
        ErpStoreInfo storeInfo = erpStoreInfoService.get(storeId);
        String storeName = null;
        if (storeInfo != null) {
            storeName = storeInfo.getShortName();
        }
        // 获取推广通道
        List<JykOrderPromotionChannel> channelList = orderPromotionChannelService.findListBySplitId(splitId);
        String promotionChannels = null;// 推广渠道
        if (channelList != null) {
            StringBuilder channels = new StringBuilder();
            for (JykOrderPromotionChannel channel : channelList) {
                String channelName = PromotionChannelType.getNameByCode(channel.getPromotionChannel());
                channels.append(CommonConstants.Sign.DUN_HAO).append(channelName);
            }
            if (channels.length() > 0) {
                promotionChannels = channels.substring(1);
            }
        }
        // ### 生成变量值 ###
        result.put("@SplitId@", StringUtils.toString(splitId, ""));
        result.put("@OperationAdviserName@", StringUtils.toString(operationName, ""));
        result.put("@AccountAdviserName@", StringUtils.toString(accountName, ""));
        result.put("@PlanningExpertName@", StringUtils.toString(planningName, ""));
        result.put("@PromotionStoreNames@", StringUtils.toString(storeName, ""));
        result.put("@PromotionOnlineDate@", StringUtils.toString(DateUtils.formatDateTime(promotionTime), ""));
        result.put("@PromotionChannels@", StringUtils.toString(promotionChannels, ""));
        result.put("@PromotionCycle@", StringUtils.toString(promotionCycle, ""));
        result.put("@LookEffectFlag@", StringUtils.toString(lookEffectFlag, CommonConstants.Sign.NO));
        result.put("@CommentFlag@", StringUtils.toString(commentFlag, CommonConstants.Sign.NO));
        return result;
    }

    /**
     * 获取推广周期
     *
     * @param splitGoodList
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    private String getPromotionCycle(List<ErpOrderSplitGood> splitGoodList) {
        String result = null;
        if (CollectionUtils.isNotEmpty(splitGoodList)) {
            List<Map<String, String>> cycleList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<>();
            map.put("聚套餐", "7~9");
            cycleList.add(map);
            map = new HashMap<>();
            map.put("引套餐", "5~7");
            cycleList.add(map);
            map = new HashMap<>();
            map.put("招套餐", "3~5");
            cycleList.add(map);
            map = new HashMap<>();
            map.put("入门版", "3~4");
            cycleList.add(map);
            for (Map<String, String> mapObj : cycleList) {
                for (ErpOrderSplitGood good : splitGoodList) {
                    result = mapObj.get(good.getGoodName());
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return "3~9";
    }

    /**
     * 获取单个进度 的变量集合
     *
     * @param procInsId
     * @param serviceType
     * @param type
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    public Map<String, String> getNodeTypeMap(String procInsId, String serviceType, String type) {
        Map<String, String> result = new HashMap<>();
        if (FlowServiceType.SPLIT_JU_YIN_KE.getType().equals(serviceType)) {
            result = getNodeTypeSplitJuYinKeVariableMap(type, procInsId);
        } else if (FlowServiceType.DELIVERY_FMPS.getType().equals(serviceType)) {
            result = getNodeTypeDeliveryFMPS(type, procInsId);
        } else if (FlowServiceType.DELIVERY_FMPS_BASIC.getType().equals(serviceType)) {
            result = getNodeTypeDeliveryFMPSBASIC(type, procInsId);
        } else if (FlowServiceType.DELIVERY_JYK.getType().equals(serviceType)) {
            result = getNodeTypeDeliveryJYK(type, procInsId);
        } else if (FlowServiceType.DELIVERY_INTO_PIECES.getType().equals(serviceType)) {
            result = getNodeTypeDeliveryINTOPIECES(type, procInsId);
        } else if (FlowServiceType.ZHI_HUI_CAN_TING.getType().equals(serviceType)) {
            result = getNodeTypeZhiHuiCanTing(type, procInsId);
        }
        return result;
    }

    /**
     * 智慧餐厅进度变量
     *
     * @param type
     * @param procInsId
     * @return
     * @date 2018年8月28日
     * @author linqunzhi
     */
    private Map<String, String> getNodeTypeZhiHuiCanTing(String type, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 上门相关变量
        if (ServiceProgressTemplateConstants.Type.VISIT_SERVICE.equals(type)) {
            assignmentVisitVariable(result, VisitServiceItemConstants.Goal.ZHCT_CODE, procInsId);
        }
        return result;
    }

    /**
     * 聚引客交付服务 进度变量
     *
     * @param type
     * @param procInsId
     * @return
     * @date 2018年8月28日
     * @author linqunzhi
     */
    private Map<String, String> getNodeTypeDeliveryJYK(String type, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 上门相关变量
        if (ServiceProgressTemplateConstants.Type.VISIT_SERVICE.equals(type)) {
            assignmentVisitVariable(result, VisitServiceItemConstants.Goal.JYK_CODE, procInsId);
        }
        return result;
    }

    private Map<String, String> getNodeTypeDeliveryINTOPIECES(String type, String procInsId) {
        Map<String, String> result = new HashMap<>();
        return result;
    }

    /**
     * 首次营销基础版进度 变量
     *
     * @param type
     * @param procInsId
     * @return
     * @date 2018年8月28日
     * @author linqunzhi
     */
    private Map<String, String> getNodeTypeDeliveryFMPSBASIC(String type, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 上门相关变量
        if (ServiceProgressTemplateConstants.Type.VISIT_SERVICE.equals(type)) {
            assignmentVisitVariable(result, VisitServiceItemConstants.Goal.FMPS_BASIC_CODE, procInsId);
        }
        return result;
    }

    /**
     * 智能客流运营全套落地服务 进度 变量
     *
     * @param type
     * @param procInsId
     * @return
     * @date 2018年8月28日
     * @author linqunzhi
     */
    private Map<String, String> getNodeTypeDeliveryFMPS(String type, String procInsId) {
        Map<String, String> result = new HashMap<>();
        // 上门相关变量
        if (ServiceProgressTemplateConstants.Type.FLOOR_SERVICE.equals(type) || ServiceProgressTemplateConstants.Type.MATERIAL_DEPLOYMENT
                        .equals(type)) {
            String serviceGoalCode = VisitServiceItemConstants.Goal.FMPS_CODE;
            if (ServiceProgressTemplateConstants.Type.MATERIAL_DEPLOYMENT.equals(type)) {
                serviceGoalCode = VisitServiceItemConstants.Goal.FMPS_VISIT_CODE;
            }
            assignmentVisitVariable(result, serviceGoalCode, procInsId);
        }
        return result;
    }

    /**
     * 聚引客生产流程 进度变量
     *
     * @param type
     * @param procInsId
     * @return
     * @date 2018年8月28日
     * @author linqunzhi
     */
    private Map<String, String> getNodeTypeSplitJuYinKeVariableMap(String type, String procInsId) {
        Map<String, String> result = new HashMap<>();
        return result;
    }

    /**
     * 与上门相关进度的变量 赋值
     *
     * @param result
     * @param serviceGoalCode
     * @param procInsId
     * @date 2018年8月28日
     * @author linqunzhi
     */
    private void assignmentVisitVariable(Map<String, String> result, String serviceGoalCode, String procInsId) {
        // 获取运营顾问
        ErpOrderFlowUser operationFlowUser = orderFlowUserService.findByProcInsIdAndRoleName(procInsId,
                        OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER);
        String operationUserId = null;// 运营顾问id
        if (operationFlowUser != null) {
            if (operationFlowUser.getUser() != null) {
                operationUserId = operationFlowUser.getUser().getId();
            }
        }
        String operationName = null;
        JobNumberInfo operationJob = jobNumberInfoService.getByUserId(operationUserId);
        if (operationJob != null) {
            operationName = operationJob.getJobNumber();
        }
        // 上门信息id
        String visitServiceInfoId = null;
        ErpVisitServiceInfo visitInfo = visitServiceInfoService.getByGoalCode(procInsId, serviceGoalCode);
        // 上门时间
        Date visitTime = null;
        // 上门标识
        String visitFlag = null;
        String shopPreparationinfo = null;
        String visitAuditStatus = null;
        if (visitInfo != null) {
            visitServiceInfoId = visitInfo.getId();
            visitTime = visitInfo.getAppointedStartTime();
            shopPreparationinfo = visitInfo.getShopPreparationInfo();
            visitFlag = visitInfo.getRemindFlag();
            visitAuditStatus = visitInfo.getAuditStatus();
        }
        String acceptanceId = null;
        if (StringUtils.isNotBlank(visitServiceInfoId)) {
            ErpServiceAcceptance acceptance = acceptanceService.getByVisitId(visitServiceInfoId);
            if (acceptance != null) {
                acceptanceId = acceptance.getId();
            }
        }
        // 评价标识
        String commentFlag = StringUtils.isNotBlank(acceptanceId) ? CommonConstants.Sign.YES : CommonConstants.Sign.NO;
        // 上门文案
        String visitContent = getVisitContent(visitTime, shopPreparationinfo, operationName, visitFlag, commentFlag, visitAuditStatus);

        // 赋值变量
        result.put("@AcceptanceId@", StringUtils.toString(acceptanceId, ""));
        result.put("@VisitServiceInfoId@", StringUtils.toString(visitServiceInfoId, ""));
        result.put("@VisitContent@", StringUtils.toString(visitContent, ""));
        result.put("@VisitFlag@", StringUtils.toString(visitFlag, CommonConstants.Sign.NO));
    }

    /**
     * 商户准备材料文案
     *
     * @param visitTime
     * @param shopPreparationinfo
     * @return
     * @date 2018年7月24日
     * @author linqunzhi
     * @param accountName
     * @param visitAuditStatus
     */
    private String getVisitContent(Date visitTime, String shopPreparationinfo, String operationName, String visitFlag, String commentFlag,
                    String visitAuditStatus) {
        String yes = CommonConstants.Sign.YES;
        // 已上门
        if (yes.equals(visitFlag)) {
            // 已评价
            if (yes.equals(commentFlag)) {
                return CommonConstants.Sign.FORWARD_SLASH;
            } else {
                return "请核实本次服务内容并对本次服务进行评分";
            }
        }
        // 已预约上门
        if (ErpVisitServiceConstants.AUDIT_STATUS_AUDITED.equals(visitAuditStatus)) {
            return "您已经预约" + DateUtils.formatDate(visitTime,
                            DateUtils.YYYY_MM_DD_HH_MM) + "日上门服务，请您提前准备" + replaceRN(shopPreparationinfo) + "等资料，并保持电话通畅";
        } else {
            return "运营顾问_" + operationName + "将和您电话预约上门服务时间，请保持电话畅通";
        }
    }

    /**
     * 去除换行
     *
     * @param str
     * @return
     * @date 2018年8月1日
     * @author linqunzhi
     */
    private static String replaceRN(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.replaceAll("\\r\\n|\\r|\\n|\\n\\r", "");
    }

}
