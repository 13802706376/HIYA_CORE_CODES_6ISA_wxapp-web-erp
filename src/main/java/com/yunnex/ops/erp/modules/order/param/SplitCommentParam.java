package com.yunnex.ops.erp.modules.order.param;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.BaseParam;

/**
 * 分单评价
 * 
 * @author linqunzhi
 * @date 2018年4月4日
 */
public class SplitCommentParam extends BaseParam{

    private static final long serialVersionUID = -465262721440807356L;

    /** 订单ID */
    private String splitId;

    /** 服务满意度评分 */
    private double serviceScore;

    /** 效果推广评分 */
    private double promotionScore;

    /** 回答列表 */
    private List<SplitCommentAnswerParam> answerList;

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }

    public double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public double getPromotionScore() {
        return promotionScore;
    }

    public void setPromotionScore(double promotionScore) {
        this.promotionScore = promotionScore;
    }

    public List<SplitCommentAnswerParam> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<SplitCommentAnswerParam> answerList) {
        this.answerList = answerList;
    }

}
