package com.yunnex.ops.erp.common.constants;

public interface LoginConstants {

    /** session过期时间：30分钟 */
    public static final int SESSION_TIMEOUT = 60 * 30;
    /** 记住我过期时间：两周 */
    public static final int REMEMBER_ME_TIMEOUT = 60 * 60 * 24 * 365;
    /** session key的前缀 */
    public static final String SESSION_PREFIX = "ERP:WXAPP:SESSION:";
    /** token key */
    public static final String SHOP_TOKEN = "SHOPTOKEN";
    /** remember me key */
    public static final String REMEMBER_ME = "REMEMBERME";
    /** 小程序登录页面地址 */
    public static final String WXAPP_LOGIN_URL = "REMEMBERME";
    /** 退出成功 */
    public static final String LOGOUT_SUCCESS = "退出成功！";
    /** 退出失败 */
    public static final String LOGOUT_FAIL = "退出失败！";
    
}
