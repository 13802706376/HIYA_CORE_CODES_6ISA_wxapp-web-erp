package com.yunnex.ops.erp.modules.order.view;

import com.yunnex.ops.erp.common.persistence.BaseView;

/**
 * 分单信息交互视图
 * 
 * @author linqunzhi
 * @date 2018年4月2日
 */
public class OrderSplitInfoView extends BaseView {

    private static final long serialVersionUID = -7490195781931846492L;

    /** 商品类型名称 */
    private String goodTypeName;

    /** 商品备注（商品类型×数量） */
    private String goodRemark;

    /** 分单id */
    private String splitId;
    
    /** 商品类型图片 */
    private String goodPhotoUrl;

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    public String getGoodRemark() {
        return goodRemark;
    }

    public void setGoodRemark(String goodRemark) {
        this.goodRemark = goodRemark;
    }

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }

    public String getGoodPhotoUrl() {
        return goodPhotoUrl;
    }

    public void setGoodPhotoUrl(String goodPhotoUrl) {
        this.goodPhotoUrl = goodPhotoUrl;
    }

}
