package com.yunnex.ops.erp.modules.store.pay.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 微信支付开通资料Entity
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStorePayWeixin extends DataEntity<ErpStorePayWeixin> {
	
	private static final long serialVersionUID = 1L;
	private String bankId;		// 支付银行ID
	private Integer provideAccountInfo=0;		// 提供公众号账号、密码,0:否,1:是,默认0
	private String publicAccountNo;		// 公众号登录账号
	private String publicAccountPassword;		// 公众号登录密码
	private String publicAccountAppid;		// 公众号APPID
	private Integer auditStatus=0;		// 审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0
	private ErpStoreBank bank;     // 银行信息
	private Integer finishStatus;  // 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
	private Integer shopBusinessType;  // 商户类型，封装数据用
	
    /** 邮箱账号 */
    private String emailNo;

    /** 邮箱密码 */
    private String emailPassword;

    /** 运营人员身份证 */
    private String operatorIdcard;

    /** 运营人员邮箱 */
    private String operatorEmail;

    /** 运营人员手机号 */
    private String operatorMobile;

    /** 运营人员名称 */
    private String operatorName;

    /** 微信号 */
    private String weixinNo;

    /** 公众号名称 */
    private String publicAccountName;

    // ########## 封装数据用，数据库中没有对应字段 start ############

    private String erpStoreInfoId;

    /** 是否开通公众号 （Y:是，N：否） */
    private String openFlag;

    // ########## 封装数据用，数据库中没有对应字段 end ############

	public ErpStorePayWeixin() {
		super();
	}

	public ErpStorePayWeixin(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="支付银行ID长度必须介于 0 和 64 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@NotNull(message="提供公众号账号、密码,0:否,1:是,默认0不能为空")
	public Integer getProvideAccountInfo() {
		return provideAccountInfo;
	}

	public void setProvideAccountInfo(Integer provideAccountInfo) {
		this.provideAccountInfo = provideAccountInfo;
	}
	
	@Length(min=0, max=64, message="公众号登录账号长度必须介于 0 和 64 之间")
	public String getPublicAccountNo() {
		return publicAccountNo;
	}

	public void setPublicAccountNo(String publicAccountNo) {
		this.publicAccountNo = publicAccountNo;
	}
	
	@Length(min=0, max=64, message="公众号登录密码长度必须介于 0 和 64 之间")
	public String getPublicAccountPassword() {
		return publicAccountPassword;
	}

	public void setPublicAccountPassword(String publicAccountPassword) {
		this.publicAccountPassword = publicAccountPassword;
	}
	
	@Length(min=0, max=64, message="公众号APPID长度必须介于 0 和 64 之间")
	public String getPublicAccountAppid() {
		return publicAccountAppid;
	}

	public void setPublicAccountAppid(String publicAccountAppid) {
		this.publicAccountAppid = publicAccountAppid;
	}
	
	@NotNull(message="审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0不能为空")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

    public ErpStoreBank getBank() {
        return bank;
    }

    public void setBank(ErpStoreBank bank) {
        this.bank = bank;
    }

    public String getErpStoreInfoId() {
        return erpStoreInfoId;
    }

    public void setErpStoreInfoId(String erpStoreInfoId) {
        this.erpStoreInfoId = erpStoreInfoId;
    }

    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    public Integer getShopBusinessType() {
        return shopBusinessType;
    }

    public void setShopBusinessType(Integer shopBusinessType) {
        this.shopBusinessType = shopBusinessType;
    }

    public String getEmailNo() {
        return emailNo;
    }

    public void setEmailNo(String emailNo) {
        this.emailNo = emailNo;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getOperatorIdcard() {
        return operatorIdcard;
    }

    public void setOperatorIdcard(String operatorIdcard) {
        this.operatorIdcard = operatorIdcard;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getOperatorMobile() {
        return operatorMobile;
    }

    public void setOperatorMobile(String operatorMobile) {
        this.operatorMobile = operatorMobile;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getWeixinNo() {
        return weixinNo;
    }

    public void setWeixinNo(String weixinNo) {
        this.weixinNo = weixinNo;
    }

    public String getPublicAccountName() {
        return publicAccountName;
    }

    public void setPublicAccountName(String publicAccountName) {
        this.publicAccountName = publicAccountName;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    @Override
    public String toString() {
        return "ErpStorePayWeixin [bankId=" + bankId + ", provideAccountInfo=" + provideAccountInfo + ", publicAccountNo=" + publicAccountNo + ", publicAccountPassword=" + publicAccountPassword + ", publicAccountAppid=" + publicAccountAppid + ", auditStatus=" + auditStatus + ", bank=" + bank + ", erpStoreInfoId=" + erpStoreInfoId + ", finishStatus=" + finishStatus + ", shopBusinessType=" + shopBusinessType + ", emailNo=" + emailNo + ", emailPassword=" + emailPassword + ", operatorIdcard=" + operatorIdcard + ", operatorEmail=" + operatorEmail + ", operatorMobile=" + operatorMobile + ", operatorName=" + operatorName + "]";
    }
	
}