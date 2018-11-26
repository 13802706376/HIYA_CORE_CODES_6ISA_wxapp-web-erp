package com.yunnex.ops.erp.modules.order.view;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.BaseView;

/**
 * 分单评价问题配置视图
 * 
 * @author linqunzhi
 * @date 2018年4月3日
 */
public class SplitCommentQuetionConfView extends BaseView {

    private static final long serialVersionUID = -7228130806955323321L;

    /** 问题id */
    private String questionId;

    /** 问题内容 */
    private String quetionContent;

    /** 问题类型 （SelectMultiple 多选题，SelectSingle 单选题, Text 文本题） */
    private String quetionType;

    /** 问题是否必答( Y 是，N 否) */
    private String questionMustFlag;

    /** 回答内容集合 */
    private List<SplitCommentAnswerConfView> answerList;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuetionContent() {
        return quetionContent;
    }

    public void setQuetionContent(String quetionContent) {
        this.quetionContent = quetionContent;
    }

    public String getQuetionType() {
        return quetionType;
    }

    public void setQuetionType(String quetionType) {
        this.quetionType = quetionType;
    }

    public List<SplitCommentAnswerConfView> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<SplitCommentAnswerConfView> answerList) {
        this.answerList = answerList;
    }

    public String getQuestionMustFlag() {
        return questionMustFlag;
    }

    public void setQuestionMustFlag(String questionMustFlag) {
        this.questionMustFlag = questionMustFlag;
    }
    
    

}
