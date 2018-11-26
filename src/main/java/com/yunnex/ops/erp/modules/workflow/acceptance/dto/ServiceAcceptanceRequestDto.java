package com.yunnex.ops.erp.modules.workflow.acceptance.dto;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.RequestDto;

public class ServiceAcceptanceRequestDto extends RequestDto<ServiceAcceptanceRequestDto> {

    private static final long serialVersionUID = -6970060641504469859L;
    
    /** id */
    private String id;

    /** 上门服务详情id */
    private String visitServiceInfoId;

    /** 本次服务(上门目的) */
    private String serviceGoal;

    /** 评分 */
    private Double score;

    /** id */
    private List<AcceptanceQuestionRequestDto> questionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitServiceInfoId() {
        return visitServiceInfoId;
    }

    public void setVisitServiceInfoId(String visitServiceInfoId) {
        this.visitServiceInfoId = visitServiceInfoId;
    }

    public String getServiceGoal() {
        return serviceGoal;
    }

    public void setServiceGoal(String serviceGoal) {
        this.serviceGoal = serviceGoal;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<AcceptanceQuestionRequestDto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<AcceptanceQuestionRequestDto> questionList) {
        this.questionList = questionList;
    }
}
