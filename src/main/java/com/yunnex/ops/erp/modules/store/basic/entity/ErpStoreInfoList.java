package com.yunnex.ops.erp.modules.store.basic.entity;

import com.yunnex.ops.erp.common.persistence.DataEntity;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayUnionpay;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayWeixin;

public class ErpStoreInfoList extends DataEntity<ErpStoreInfoList> {

    private static final long serialVersionUID = 1L;
    private ErpStoreInfo storeInfo;
    private ErpStorePayWeixin weixin;
    private ErpStorePayUnionpay unionpay;
    Integer businessType;
    Boolean openUnionpay;
    Boolean different;
    Integer unionpayAccountType;
    Integer weixinAccountType;

    public ErpStoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(ErpStoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public ErpStorePayWeixin getWeixin() {
        return weixin;
    }

    public void setWeixin(ErpStorePayWeixin weixin) {
        this.weixin = weixin;
    }

    public ErpStorePayUnionpay getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(ErpStorePayUnionpay unionpay) {
        this.unionpay = unionpay;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Boolean getOpenUnionpay() {
        return openUnionpay;
    }

    public void setOpenUnionpay(Boolean openUnionpay) {
        this.openUnionpay = openUnionpay;
    }

    public Boolean getDifferent() {
        return different;
    }

    public void setDifferent(Boolean different) {
        this.different = different;
    }

    public Integer getUnionpayAccountType() {
        return unionpayAccountType;
    }

    public void setUnionpayAccountType(Integer unionpayAccountType) {
        this.unionpayAccountType = unionpayAccountType;
    }

    public Integer getWeixinAccountType() {
        return weixinAccountType;
    }

    public void setWeixinAccountType(Integer weixinAccountType) {
        this.weixinAccountType = weixinAccountType;
    }

    @Override
    public String toString() {
        return "ErpStoreInfoList [storeInfo=" + storeInfo + ", weixin=" + weixin + ", unionpay=" + unionpay + ", businessType=" + businessType + ", openUnionpay=" + openUnionpay + ", different=" + different + ", unionpayAccountType=" + unionpayAccountType + ", weixinAccountType=" + weixinAccountType + "]";
    }

}
