package com.yunnex.ops.erp.modules.store.basic.entity;

import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserFriends;
import com.yunnex.ops.erp.modules.store.advertiser.entity.ErpStoreAdvertiserWeibo;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStoreBank;
import com.yunnex.ops.erp.modules.store.pay.entity.ErpStorePayWeixin;

public class ErpStoreInfoParam {

    private ErpStoreInfo erpStoreInfo;
    private ErpStoreLinkman erpStoreLinkman;
    private ErpStoreLegalPerson erpStoreLegalPerson;
    private ErpStoreCredentials erpStoreCredentials;
    private ErpStoreBank erpStoreBank;
    private ErpStoreAdvertiserFriends erpStoreAdvertiserFriends;
    private ErpStoreAdvertiserWeibo erpStoreAdvertiserWeibo;
    private ErpStorePayWeixin erpStorePayWeixin;

    public ErpStoreInfo getErpStoreInfo() {
        return erpStoreInfo;
    }

    public void setErpStoreInfo(ErpStoreInfo erpStoreInfo) {
        this.erpStoreInfo = erpStoreInfo;
    }

    public ErpStoreLinkman getErpStoreLinkman() {
        return erpStoreLinkman;
    }

    public void setErpStoreLinkman(ErpStoreLinkman erpStoreLinkman) {
        this.erpStoreLinkman = erpStoreLinkman;
    }

    public ErpStoreLegalPerson getErpStoreLegalPerson() {
        return erpStoreLegalPerson;
    }

    public void setErpStoreLegalPerson(ErpStoreLegalPerson erpStoreLegalPerson) {
        this.erpStoreLegalPerson = erpStoreLegalPerson;
    }

    public ErpStoreCredentials getErpStoreCredentials() {
        return erpStoreCredentials;
    }

    public void setErpStoreCredentials(ErpStoreCredentials erpStoreCredentials) {
        this.erpStoreCredentials = erpStoreCredentials;
    }

    public ErpStoreBank getErpStoreBank() {
        return erpStoreBank;
    }

    public void setErpStoreBank(ErpStoreBank erpStoreBank) {
        this.erpStoreBank = erpStoreBank;
    }

    public ErpStoreAdvertiserFriends getErpStoreAdvertiserFriends() {
        return erpStoreAdvertiserFriends;
    }

    public void setErpStoreAdvertiserFriends(ErpStoreAdvertiserFriends erpStoreAdvertiserFriends) {
        this.erpStoreAdvertiserFriends = erpStoreAdvertiserFriends;
    }

    public ErpStoreAdvertiserWeibo getErpStoreAdvertiserWeibo() {
        return erpStoreAdvertiserWeibo;
    }

    public void setErpStoreAdvertiserWeibo(ErpStoreAdvertiserWeibo erpStoreAdvertiserWeibo) {
        this.erpStoreAdvertiserWeibo = erpStoreAdvertiserWeibo;
    }

    public ErpStorePayWeixin getErpStorePayWeixin() {
        return erpStorePayWeixin;
    }

    public void setErpStorePayWeixin(ErpStorePayWeixin erpStorePayWeixin) {
        this.erpStorePayWeixin = erpStorePayWeixin;
    }

    @Override
    public String toString() {
        return "ErpStoreInfoParam [erpStoreInfo=" + erpStoreInfo + ", erpStoreLinkman=" + erpStoreLinkman + ", erpStoreLegalPerson=" + erpStoreLegalPerson + ", erpStoreCredentials=" + erpStoreCredentials + ",erpStoreBank=" + erpStoreBank + ",erpStoreAdvertiserWeibo=" + erpStoreAdvertiserWeibo + ",erpStoreAdvertiserWeibo=" + erpStoreAdvertiserWeibo + ",erpStorePayWeixin=" + erpStorePayWeixin + "]";
    }

}
