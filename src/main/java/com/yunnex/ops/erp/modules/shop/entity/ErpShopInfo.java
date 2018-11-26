package com.yunnex.ops.erp.modules.shop.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 商户管理Entity
 * @author huanghaidong
 * @version 2017-10-24
 */
public class ErpShopInfo extends DataEntity<ErpShopInfo> {

    private static final long serialVersionUID = 1L;
    private String number;		// 商户编号
    private String name;		// 商户名称
    private String abbreviation;		// 商户简称
    private String industryType;		// 行业类型
    private String address;		// 商户地址
    private String contactEmail;		// 联系邮箱
    private String contactName;		// 商户联系人
    private String contactPhone;		// 联系电话
    private String serviceProvider;		// 服务商
    private String serviceProviderPhone;		// 服务商联系方式
    private String zhangbeiId;		// 掌贝id
    private String zhangbeiPassword;   // 掌贝密码
    private Integer zhangbeiState; // 掌贝进件状态
    private Integer storeCount;    // 门店总数
    private String remark;		// 备注
    private Long sort;		// 排序字段
    private String intoPieces;
    private String fwrole; //展示用，服务角色
    private String fwname; //展示用服务人员名
    private String fwtype; //展示用服务项目
    private Integer storenum;//门店数量
    private String loginName;   // 登录名
    private String operationAdviserId; // 运营顾问id
    private String alipaState;// 支付宝开通状态


    public Integer getStorenum() {
        return storenum;
    }

    public void setStorenum(Integer storenum) {
        this.storenum = storenum;
    }

    public String getFwrole() {
        return fwrole;
    }

    public void setFwrole(String fwrole) {
        this.fwrole = fwrole;
    }

    public String getFwname() {
        return fwname;
    }

    public void setFwname(String fwname) {
        this.fwname = fwname;
    }

    public String getFwtype() {
        return fwtype;
    }

    public void setFwtype(String fwtype) {
        this.fwtype = fwtype;
    }

    public String getIntoPieces() {
        return intoPieces;
    }

    public void setIntoPieces(String intoPieces) {
        this.intoPieces = intoPieces;
    }

    public ErpShopInfo() {
        super();
    }

    public ErpShopInfo(String id){
        super(id);
    }

    @Length(min=1, max=50, message="商户编号长度必须介于 1 和 50 之间")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Length(min=1, max=50, message="商户名称长度必须介于 1 和 50 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=1, max=30, message="商户简称长度必须介于 1 和 30 之间")
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Length(min=1, max=50, message="行业类型长度必须介于 1 和 50 之间")
    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    @Length(min=1, max=50, message="商户地址长度必须介于 1 和 50 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min=1, max=50, message="联系邮箱长度必须介于 1 和 50 之间")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Length(min=1, max=50, message="商户联系人长度必须介于 1 和 50 之间")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Length(min=1, max=20, message="联系电话长度必须介于 1 和 20 之间")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Length(min=1, max=30, message="服务商长度必须介于 1 和 30 之间")
    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Length(min=1, max=20, message="服务商联系方式长度必须介于 1 和 20 之间")
    public String getServiceProviderPhone() {
        return serviceProviderPhone;
    }

    public void setServiceProviderPhone(String serviceProviderPhone) {
        this.serviceProviderPhone = serviceProviderPhone;
    }

    @Length(min=1, max=20, message="掌贝id长度必须介于 1 和 20 之间")
    public String getZhangbeiId() {
        return zhangbeiId;
    }

    public void setZhangbeiId(String zhangbeiId) {
        this.zhangbeiId = zhangbeiId;
    }

    public String getZhangbeiPassword() {
        return zhangbeiPassword;
    }

    public void setZhangbeiPassword(String zhangbeiPassword) {
        this.zhangbeiPassword = zhangbeiPassword;
    }

    public Integer getZhangbeiState() {
        return zhangbeiState;
    }

    public void setZhangbeiState(Integer zhangbeiState) {
        this.zhangbeiState = zhangbeiState;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    @Length(min=1, max=256, message="备注长度必须介于 1 和 256 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @NotNull(message="排序字段不能为空")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOperationAdviserId() {
        return operationAdviserId;
    }

    public void setOperationAdviserId(String operationAdviserId) {
        this.operationAdviserId = operationAdviserId;
    }

    public String getAlipaState() {
        return alipaState;
    }

    public void setAlipaState(String alipaState) {
        this.alipaState = alipaState;
    }
}