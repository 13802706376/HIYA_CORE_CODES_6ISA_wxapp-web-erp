package com.yunnex.ops.erp.modules.order.constans;

/**
 * 分单评论常量
 * 
 * @author linqunzhi
 * @date 2018年4月4日
 */
public interface SplitCommentConstants {

    /**
     * 问题常量
     * 
     * @author linqunzhi
     * @date 2018年4月4日
     */
    interface Question {
        // 多选题
        String TYPE_SELECT_MULTIPLE = "SelectMultiple";
        // 单选题
        String TYPE_SELECT_SINGLE = "SelectSingle";
        // 文本题
        String TYPE_TEXT = "Text";
    }

    interface Answer {
        // 已勾选
        String CHECK_YES = "Y";
        // 未勾选
        String CHECK_NO = "N";
    }

}
