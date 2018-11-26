package com.yunnex.ops.erp.modules.order.view;

import com.yunnex.ops.erp.common.persistence.BaseView;

/**
 * 订单信息交互视图
 * 
 * @author linqunzhi
 * @date 2018年4月2日
 */
public class OrderInfoView extends BaseView {

    private static final long serialVersionUID = 2762366451075062892L;

    /** 门店id */
    private String storeId;

    /** 门店名称 */
    private String storeName;

    /** 门店图片地址 */
    private String storePhotoUrl;

    /** 商品类型名称 */
    private String goodTypeName;

    /** 商品备注（商品类型×数量） */
    private String goodRemark;

    /** 订单id */
    private String orderId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhotoUrl() {
        return storePhotoUrl;
    }

    public void setStorePhotoUrl(String storePhotoUrl) {
        this.storePhotoUrl = storePhotoUrl;
    }

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
