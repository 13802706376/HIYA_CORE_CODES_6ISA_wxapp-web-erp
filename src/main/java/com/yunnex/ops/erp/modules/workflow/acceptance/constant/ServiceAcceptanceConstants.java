package com.yunnex.ops.erp.modules.workflow.acceptance.constant;

import com.yunnex.ops.erp.common.config.Global;

/**
 * 服务验收及评分 常量
 * 
 * @author linqunzhi
 * @date 2018年7月17日
 */
public interface ServiceAcceptanceConstants {

    /** 服务验收提价后，触发 erp 接口调用 */
    String ERP_FLOW_URL = Global.getDomainErpApi() + "/api/visit/acceptance";

}
