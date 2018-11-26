package com.yunnex.ops.erp.modules.workflow.acceptance.dto;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.RequestDto;

/**
 * 服务验收及评分问题 RequestDto
 * 
 * @author linqunzhi
 * @date 2018年7月13日
 */
public class AcceptanceQuestionRequestDto extends RequestDto<AcceptanceQuestionRequestDto> {

    private static final long serialVersionUID = -6047622238668439965L;

    /** 问题内容 */
    private String quetionContent;

    /** 问题是否必答 */
    private String questionMustFlag;

    /** 问题类型 */
    private String quetionType;

    /** 显示顺序 */
    private Integer rank;

    /** 问题是否必答 */
    private List<AcceptanceAnswerRequestDto> answerList;

    public String getQuetionContent() {
        return quetionContent;
    }

    public void setQuetionContent(String quetionContent) {
        this.quetionContent = quetionContent;
    }

    public String getQuestionMustFlag() {
        return questionMustFlag;
    }

    public void setQuestionMustFlag(String questionMustFlag) {
        this.questionMustFlag = questionMustFlag;
    }

    public String getQuetionType() {
        return quetionType;
    }

    public void setQuetionType(String quetionType) {
        this.quetionType = quetionType;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<AcceptanceAnswerRequestDto> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AcceptanceAnswerRequestDto> answerList) {
        this.answerList = answerList;
    }
}
