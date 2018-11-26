package com.yunnex.ops.erp.modules.order.entity;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 分单商品 Entry
 * 
 * @author zjq
 * @date 2018年3月29日
 */
public class ErpOrderSplitGood extends DataEntity<ErpOrderSplitGood> {

    private static final long serialVersionUID = 1L;

    private String originalSplitId; // 分单id
    private String originalGoodId; // 订单商品id
    private String goodName; // 商品名称
    private Long goodTypeId; // 商品类型id
    private String goodTypeName; // 商品类型名称
    private Integer num; // num
    private Long price; // 价格(单位：分)
    private Integer buyExposure; // 购买曝光量
    private Integer donateExposure; // 赠送曝光量

    private Boolean checked; // 是否选中
    private String orderVersion; // 订单/套餐版本

    public String getOriginalSplitId() {
        return originalSplitId;
    }

    public void setOriginalSplitId(String originalSplitId) {
        this.originalSplitId = originalSplitId;
    }

    public String getOriginalGoodId() {
        return originalGoodId;
    }

    public void setOriginalGoodId(String originalGoodId) {
        this.originalGoodId = originalGoodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Long getGoodTypeId() {
        return goodTypeId;
    }

    public void setGoodTypeId(Long goodTypeId) {
        this.goodTypeId = goodTypeId;
    }

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getChecked() {
        return checked;
    }

    public Integer getBuyExposure() {
        return buyExposure;
    }

    public void setBuyExposure(Integer buyExposure) {
        this.buyExposure = buyExposure;
    }

    public Integer getDonateExposure() {
        return donateExposure;
    }

    public void setDonateExposure(Integer donateExposure) {
        this.donateExposure = donateExposure;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getOrderVersion() {
        return orderVersion;
    }

    public void setOrderVersion(String orderVersion) {
        this.orderVersion = orderVersion;
    }

}
