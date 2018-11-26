package com.yunnex.ops.erp.modules.message.constant;

/**
 * 交互入口 常量
 * 
 * @author linqunzhi
 * @date 2018年7月30日
 */
public interface ServiceLinkConstants {

    /**
     * 服务验收及评价页
     * 
     * @author linqunzhi
     * @date 2018年7月30日
     */
    interface ServiceAcceptance {
        /** 服务验收及评价页 */
        String type = "ServiceAcceptance";

        /**
         * 参数
         * 
         * @author linqunzhi
         * @date 2018年7月30日
         */
        interface Param {

            /** 已上门标识 */
            String VISIT_FLAG = "visitFlag";
        }
    }



}
