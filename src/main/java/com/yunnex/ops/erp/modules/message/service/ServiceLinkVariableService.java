package com.yunnex.ops.erp.modules.message.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.service.BaseService;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;
import com.yunnex.ops.erp.modules.visit.constant.VisitServiceItemConstants;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceInfo;
import com.yunnex.ops.erp.modules.visit.service.ErpVisitServiceInfoService;
import com.yunnex.ops.erp.modules.workflow.acceptance.entity.ErpServiceAcceptance;
import com.yunnex.ops.erp.modules.workflow.acceptance.service.ErpServiceAcceptanceService;
import com.yunnex.ops.erp.modules.workflow.delivery.entity.ErpDeliveryService;
import com.yunnex.ops.erp.modules.workflow.delivery.service.ErpDeliveryServiceService;
import com.yunnex.ops.erp.modules.workflow.enums.FlowServiceType;

/**
 * 交互入口 变量
 * 
 * @author linqunzhi
 * @date 2018年8月6日
 */
@Service
public class ServiceLinkVariableService extends BaseService {

    @Autowired
    @Lazy
    private ErpOrderSplitInfoService splitInfoService;
    @Autowired
    @Lazy
    private ErpVisitServiceInfoService visitServiceInfoService;
    @Autowired
    @Lazy
    private ErpServiceAcceptanceService acceptanceService;
    @Autowired
    @Lazy
    private ErpDeliveryServiceService deliveryServiceService;
    @Autowired
    @Lazy
    private ErpShopInfoService erpShopInfoService;

    /**
     * 获取连接变量
     *
     * @param procInsId
     * @param serviceType
     * @param nodeType
     * @return
     * @date 2018年8月6日
     * @author linqunzhi
     */
    public Map<String, String> getVariableMap(String procInsId, String serviceType, String nodeType) {
        Map<String, String> result = new HashMap<>();
        if (FlowServiceType.SPLIT_JU_YIN_KE.getType().equals(serviceType)) {
            result = getSplitJuYinKeVariableMap(procInsId);
        } else {
            result = getDeliveryVariableMap(procInsId, nodeType, serviceType);
        }
        return result;
    }

    private Map<String, String> getDeliveryVariableMap(String procInsId, String nodeType, String serviceType) {
        ErpDeliveryService deliveryService = deliveryServiceService.getDeliveryInfoByProsIncId(procInsId);
        String zhangbeiId = null;
        if (deliveryService != null) {
            zhangbeiId = deliveryService.getShopId();
        }
        // 获取商户信息
        ErpShopInfo shopInfo = erpShopInfoService.getByZhangbeiId(zhangbeiId);
        int storeCount = 0; // 门店总数

        if (shopInfo != null) {
            storeCount = shopInfo.getStoreCount();
        }
        // 获取上门目的
        String serviceGoalCode = calculateServiceGoalCode(serviceType, nodeType);
        ErpVisitServiceInfo visitInfo = null;
        if (StringUtils.isNotBlank(serviceGoalCode)) {
            visitInfo = visitServiceInfoService.getByGoalCode(procInsId, serviceGoalCode);
        }
        String visitServiceInfoId = null;
        // 上门提醒标识
        String visitFlag = null;
        if (visitInfo != null) {
            visitServiceInfoId = visitInfo.getId();
            visitFlag = visitInfo.getRemindFlag();
        }
        String acceptanceId = null;
        if (StringUtils.isNotBlank(visitServiceInfoId)) {
            ErpServiceAcceptance acceptance = acceptanceService.getByVisitId(visitServiceInfoId);
            if (acceptance != null) {
                acceptanceId = acceptance.getId();
            }
        }
        // ### 生成变量值 ###
        Map<String, String> result = new HashMap<>();
        result.put("@ZhangbeiId@", StringUtils.toString(zhangbeiId, ""));
        result.put("@StoreCount@", StringUtils.toString(storeCount, "0"));
        result.put("@AcceptanceId@", StringUtils.toString(acceptanceId, ""));
        result.put("@VisitServiceInfoId@", StringUtils.toString(visitServiceInfoId, ""));
        result.put("@VisitFlag@", StringUtils.toString(visitFlag, CommonConstants.Sign.NO));
        return result;
    }

    private Map<String, String> getSplitJuYinKeVariableMap(String procInsId) {
        // 获取分单信息
        ErpOrderSplitInfo splitInfo = splitInfoService.getByProsIncId(procInsId);
        String splitId = null; // 分单id
        String lookEffectFlag = null;// 是否查看过效果报告
        String commentFlag = CommonConstants.Sign.NO;// 是否评价过
        String zhangbeiId = null;
        if (splitInfo != null) {
            splitId = splitInfo.getId();
            lookEffectFlag = splitInfo.getLookEffectFlag();
            commentFlag = splitInfo.getCommentCount() > 0 ? CommonConstants.Sign.YES : CommonConstants.Sign.NO;
            zhangbeiId = splitInfo.getShopId();
        }
        Map<String, String> result = new HashMap<>();
        result.put("@ZhangbeiId@", StringUtils.toString(zhangbeiId, ""));
        result.put("@SplitId@", StringUtils.toString(splitId, ""));
        result.put("@LookEffectFlag@", StringUtils.toString(lookEffectFlag, CommonConstants.Sign.NO));
        result.put("@CommentFlag@", StringUtils.toString(commentFlag, CommonConstants.Sign.NO));
        return result;
    }

    /**
     * 获取上门目的
     *
     * @param serviceType
     * @param nodeType
     * @return
     * @date 2018年7月24日
     * @author linqunzhi
     */
    private String calculateServiceGoalCode(String serviceType, String nodeType) {
        String result = "";
        if (FlowServiceType.DELIVERY_FMPS.getType().equals(serviceType)) {
            if ("FloorService".equals(nodeType) || "FloorServiceVisit".equals(nodeType) || "FloorServiceComment".equals(nodeType)) {
                result = "1";
            } else {
                result = "2";
            }
        } else if (FlowServiceType.DELIVERY_FMPS_BASIC.getType().equals(serviceType)) {
            result = "7";
        } else if (FlowServiceType.DELIVERY_JYK.getType().equals(serviceType)) {
            result = "3";
        } else if (FlowServiceType.ZHI_HUI_CAN_TING.getType().equals(serviceType)) {
            result = VisitServiceItemConstants.Goal.ZHCT_CODE;
        }
        return result;
    }

    /**
     * 替换服务通知中交互入口变量
     *
     * @param procInsId
     * @param linkParam
     * @return
     * @date 2018年8月6日
     * @author linqunzhi
     */
    public String replaceLinkParam(String procInsId, String serviceType, String nodeType, String linkParam) {
        if (StringUtils.isBlank(linkParam)) {
            return linkParam;
        }
        Map<String, String> map = this.getVariableMap(procInsId, serviceType, nodeType);
        for (Entry<String, String> entry : map.entrySet()) {
            linkParam = linkParam.replace(entry.getKey(), entry.getValue());
        }
        return linkParam;
    }
}
