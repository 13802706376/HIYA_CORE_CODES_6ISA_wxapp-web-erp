package com.yunnex.ops.erp.modules.store.basic.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 法人信息Entity
 * 
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStoreLegalPerson extends DataEntity<ErpStoreLegalPerson> {

    private static final long serialVersionUID = 1L;
    private String name; // 姓名
    private String idCardNo; // 身份证号码
    private Date idCardStartDate; // 身份证有效期起始日期
    private Date idCardEndDate; // 身份证有效期结束日期
    private String idCardFrontPhoto; // 法人身份证正面照
    private String idCardReversePhoto; // 法人身份证反面照
    private String idCardInHandPhoto; // 法人手持身份证照
    private String erpStoreInfoId;// 门店基本信息ID
    private String finishStatus;// 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成

    public ErpStoreLegalPerson() {
        super();
    }

    public ErpStoreLegalPerson(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "姓名长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 18, message = "身份证号码长度必须介于 0 和 18 之间")
    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getIdCardStartDate() {
        return idCardStartDate;
    }

    public void setIdCardStartDate(Date idCardStartDate) {
        this.idCardStartDate = idCardStartDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getIdCardEndDate() {
        return idCardEndDate;
    }

    public void setIdCardEndDate(Date idCardEndDate) {
        this.idCardEndDate = idCardEndDate;
    }

    @Length(min = 0, max = 255, message = "法人身份证正面照长度必须介于 0 和 255 之间")
    public String getIdCardFrontPhoto() {
        return idCardFrontPhoto;
    }

    public void setIdCardFrontPhoto(String idCardFrontPhoto) {
        this.idCardFrontPhoto = idCardFrontPhoto;
    }

    @Length(min = 0, max = 255, message = "法人身份证反面照长度必须介于 0 和 255 之间")
    public String getIdCardReversePhoto() {
        return idCardReversePhoto;
    }

    public void setIdCardReversePhoto(String idCardReversePhoto) {
        this.idCardReversePhoto = idCardReversePhoto;
    }

    @Length(min = 0, max = 255, message = "法人手持身份证照长度必须介于 0 和 255 之间")
    public String getIdCardInHandPhoto() {
        return idCardInHandPhoto;
    }

    public void setIdCardInHandPhoto(String idCardInHandPhoto) {
        this.idCardInHandPhoto = idCardInHandPhoto;
    }

    public String getErpStoreInfoId() {
        return erpStoreInfoId;
    }

    public void setErpStoreInfoId(String storeInfoId) {
        this.erpStoreInfoId = storeInfoId;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    @Override
    public String toString() {
        return "ErpStoreLegalPerson [name=" + name + ", idCardNo=" + idCardNo + ", idCardStartDate=" + idCardStartDate + ", idCardEndDate=" + idCardEndDate + ", idCardFrontPhoto=" + idCardFrontPhoto + ", idCardReversePhoto=" + idCardReversePhoto + ", idCardInHandPhoto=" + idCardInHandPhoto + ",erpStoreInfoId=" + erpStoreInfoId + ",finishStatus=" + finishStatus + "]";
    }

}
