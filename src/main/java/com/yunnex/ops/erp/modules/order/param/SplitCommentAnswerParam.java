package com.yunnex.ops.erp.modules.order.param;

import com.yunnex.ops.erp.common.persistence.BaseParam;

/**
 * 分单评价回答
 * @author linqunzhi
 * @date 2018年4月4日
 */
public class SplitCommentAnswerParam extends BaseParam{
    
    private static final long serialVersionUID = -7351431782351982073L;

    /** 回答id */
    private String id;
    
    /** 问题id */
    private String questionId;
    
    /** 回答内容 */
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    
}
