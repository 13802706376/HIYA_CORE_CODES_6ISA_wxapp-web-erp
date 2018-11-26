package com.yunnex.ops.erp.modules.store.basic.entity;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 门店联系人信息Entity
 * 
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStoreLinkman extends DataEntity<ErpStoreLinkman> {

    private static final long serialVersionUID = 1L;
    private String name; // 姓名
    private String phone; // 手机号
    private String email; // 邮箱
    private String address; // 地址
    private String erpStoreInfoId; // 门店ID
    private String finishStatus;// 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成

    public ErpStoreLinkman() {
        super();
    }

    public ErpStoreLinkman(String id) {
        super(id);
    }

    @Length(min = 1, max = 64, message = "姓名长度必须介于 1 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 20, message = "手机号长度必须介于 1 和 20 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 1, max = 64, message = "邮箱长度必须介于 1 和 64 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 1, max = 64, message = "地址长度必须介于 1 和 64 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min = 0, max = 64, message = "门店ID长度必须介于 0 和 64 之间")
    public String getErpStoreInfoId() {
        return erpStoreInfoId;
    }

    public void setErpStoreInfoId(String erpStoreInfoId) {
        this.erpStoreInfoId = erpStoreInfoId;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    @Override
    public String toString() {
        return "ErpStoreLinkman [name=" + name + ", phone=" + phone + ", email=" + email + ", address=" + address + ", erpStoreInfoId=" + erpStoreInfoId + ",finishStatus=" + finishStatus + "]";
    }

}
