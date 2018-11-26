package com.yunnex.ops.erp.modules.workflow.acceptance.dto;

import com.yunnex.ops.erp.common.persistence.ResponseDto;

public class AcceptanceAnswerResponseDto extends ResponseDto<AcceptanceAnswerResponseDto> {

    private static final long serialVersionUID = -607667290322342293L;

    /** 回答内容 【如果是多选表格题】数组字符串，第一个数组为title（[["产品","型号","数量"],["POS机","Z5P","6"]],[...]） */
    private String answerContent;

    /** 是否勾选（Y 是，N 否） */
    private String checkFlag;

    /** 显示顺序 */
    private Integer rank;

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
