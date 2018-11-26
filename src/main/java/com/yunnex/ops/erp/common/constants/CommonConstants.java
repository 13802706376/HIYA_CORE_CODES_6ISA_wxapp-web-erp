package com.yunnex.ops.erp.common.constants;

/**
 * 公共常量类
 * 
 * @author linqunzhi
 * @date 2018年4月2日
 */
public interface CommonConstants {

    /**
     * 错误信息
     * 
     * @author linqunzhi
     * @date 2018年4月2日
     */
    public interface FailMsg {

        String PARAM = "参数异常";

        String DATA = "数据异常";
        
        String SYSTEM = "系统异常";
    }

    /**
     * 标志、符号
     * 
     * @author linqunzhi
     * @date 2018年4月2日
     */
    interface Sign {

        String YES = "Y";

        String NO = "N";

        String DASH = "-";

        String COMMA = ",";

        String COMMA_FULL = "，";
        
        String SEMICOLON = ";";
        
        String ASTERISK = "*";
        
        String POINT = ".";

        String FORWARD_SLASH = "/";

        String SPACE = " ";

        String EMPTY_STRING = "";

        /** 时间默认值 */
        String DATE_TIME_DEFAULT = "0000-00-00 00:00:00";

        String DUN_HAO = "、";


    }

}
