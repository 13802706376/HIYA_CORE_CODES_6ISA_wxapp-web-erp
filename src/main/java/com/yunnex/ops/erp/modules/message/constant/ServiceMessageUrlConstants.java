package com.yunnex.ops.erp.modules.message.constant;

import com.yunnex.ops.erp.common.config.Global;

/**
 * 服务通知相关 请求url
 * 
 * @author linqunzhi
 * @date 2018年7月27日
 */
public interface ServiceMessageUrlConstants {

    /** 服务验收提价后，触发 erp 接口调用 */
    String ACCEPTANCE_COMMIT = Global.getDomainErpApi() + "/api/message/erpServiceMessage/acceptanceCommit";

}
