package com.yunnex.ops.erp.modules.workflow.delivery.constant;

/**
 * 生产服务常量
 * 
 * @author linqunzhi
 * @date 2018年5月31日
 */
public interface ErpDeliveryServiceConstants {

    /** 交付流程中相关工作日（系统常量表中的key） */
    String DELIVERY_SERVICE_WORK_DAYS = "delivery_service_work_days";
    /** 物料（系统常量表中的key） */
    String DELIVERY_SERVICE_MATERIAL_DAYS = "delivery_service_material_days";
    /** 售后（系统常量表中的key） */
    String DELIVERY_SERVICE_VC_DAYS = "delivery_service_vc_days";

    /** 首次营销服务(First marketing plan service) */
    String TYPE_FMPS = "FMPS";
    /** 物料跟新 (material update) */
    String TYPE_MU = "MU";
    /** 上门收费 (visit charge) */
    String TYPE_VC = "VC";
    /** 聚引客 */
    String TYPE_JYK = "JYK";

}
