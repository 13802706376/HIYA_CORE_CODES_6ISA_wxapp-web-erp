package com.yunnex.ops.erp.modules.shop.constant;

public interface ShopConstant {

    /**
     * 判断值
     */
    public interface whether {
        
        /**
         * 是
         */
        String YES = "Y";
        
        /**
         * 否
         */
        String NO = "N";
    }
    
    /**
     * 返回状态
     */
    public interface returnStatus {
        
        /**
         * 成功
         */
        String SUCCESS = "0";
        
        /**
         * 失败
         */
        String FAIL = "1";
    }
    
    /**
     * 同步类型
     */
    public interface syncType {
        
        /**
         * 直接覆盖
         */
        String COVER = "Cover";
        
        /**
         * 保留两者
         */
        String RETAIN = "Retain";
    }

    /** 支付宝开通 */
    String ALIPA_STATE_OPEN = "open";

    /** 支付宝未开通 */
    String ALIPA_STATE_NOT_OPEN = "notOpen";
}