package com.yunnex.ops.erp.modules.material.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 订单物料内容Entity
 * 
 * @author yunnex
 * @version 2018-07-13
 */
public class ErpOrderMaterialContent extends DataEntity<ErpOrderMaterialContent> {

    private static final long serialVersionUID = 1L;
    private String orderNumber; // 订单号
    private String materialQuality; // 材质
    /**
     * 物料类型当场景是收银台/买单排队/店内海报/店外易拉宝KT板块： <br/>
     * 1 会员 2 储值当场景是桌台：1.桌贴-会员，2.桌贴-储值，3. 立牌 会员 4. 立牌 储值 5. 立牌 储值+会员
     */
    private Integer materialType;
    private String size; // 尺寸大小
    private Integer materialAmount; // 物料数量
    private String frontImage; // 正面
    private String reverseImage; // 反面
    private String materialImage; // 物料图片

    public ErpOrderMaterialContent() {
        super();
    }

    public ErpOrderMaterialContent(String id) {
        super(id);
    }

    @Length(min = 1, max = 50, message = "订单号长度必须介于 1 和 50 之间")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Length(min = 1, max = 30, message = "材质长度必须介于 1 和 30 之间")
    public String getMaterialQuality() {
        return materialQuality;
    }

    public void setMaterialQuality(String materialQuality) {
        this.materialQuality = materialQuality;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    @Length(min = 1, max = 30, message = "尺寸大小长度必须介于 1 和 30 之间")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @NotNull(message = "物料数量不能为空")
    public Integer getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(Integer materialAmount) {
        this.materialAmount = materialAmount;
    }

    @Length(min = 1, max = 100, message = "正面长度必须介于 1 和 100 之间")
    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    @Length(min = 1, max = 100, message = "反面长度必须介于 1 和 100 之间")
    public String getReverseImage() {
        return reverseImage;
    }

    public void setReverseImage(String reverseImage) {
        this.reverseImage = reverseImage;
    }

    @Length(min = 1, max = 100, message = "物料图片长度必须介于 1 和 100 之间")
    public String getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }

}
