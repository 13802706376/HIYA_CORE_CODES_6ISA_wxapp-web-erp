package com.yunnex.ops.erp.modules.order.view;

import com.yunnex.ops.erp.common.persistence.BaseView;

/**
 * 分单评价答案视图
 * @author linqunzhi
 * @date 2018年4月3日
 */
public class SplitCommentAnswerConfView extends BaseView{

    private static final long serialVersionUID = -939680928647422838L;
    
    /** 回答id */
    private String answerId;
    
    /** 问题id */
    private String questionId;
    
    /** 回答内容 */
    private String answerContent;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
    
}
