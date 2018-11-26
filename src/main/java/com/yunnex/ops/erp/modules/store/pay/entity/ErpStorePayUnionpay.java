package com.yunnex.ops.erp.modules.store.pay.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 银联支付开通资料Entity
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStorePayUnionpay extends DataEntity<ErpStorePayUnionpay> {
	
	private static final long serialVersionUID = 1L;
	private Integer auditStatus=0;		// 审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0
	private String bankId;		// 支付银行ID
	private String lianDan;		// 银联支付三联单
	private String storePhotoDoorHead;		// 门头照
	private String storePhotoCashierDesk;		// 收银台照
	private String storePhotoEnvironment;		// 店内环境照
	private String additionalPhoto;		// 补充资料
	private ErpStoreBank bank;     // 银行信息
	private String erpStoreInfoId;     // 封装数据用，数据库中没有对应字段
	private Integer finishStatus;  // 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
	
	public ErpStorePayUnionpay() {
		super();
	}

	public ErpStorePayUnionpay(String id){
		super(id);
	}

	@NotNull(message="审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0不能为空")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=64, message="支付银行ID长度必须介于 0 和 64 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=512, message="银联支付三联单长度必须介于 0 和 512 之间")
	public String getLianDan() {
		return lianDan;
	}

	public void setLianDan(String lianDan) {
		this.lianDan = lianDan;
	}
	
	@Length(min=0, max=255, message="门头照长度必须介于 0 和 255 之间")
	public String getStorePhotoDoorHead() {
		return storePhotoDoorHead;
	}

	public void setStorePhotoDoorHead(String storePhotoDoorHead) {
		this.storePhotoDoorHead = storePhotoDoorHead;
	}
	
	@Length(min=0, max=255, message="收银台照长度必须介于 0 和 255 之间")
	public String getStorePhotoCashierDesk() {
		return storePhotoCashierDesk;
	}

	public void setStorePhotoCashierDesk(String storePhotoCashierDesk) {
		this.storePhotoCashierDesk = storePhotoCashierDesk;
	}
	
	@Length(min=0, max=255, message="店内环境照长度必须介于 0 和 255 之间")
	public String getStorePhotoEnvironment() {
		return storePhotoEnvironment;
	}

	public void setStorePhotoEnvironment(String storePhotoEnvironment) {
		this.storePhotoEnvironment = storePhotoEnvironment;
	}
	
	@Length(min=0, max=255, message="补充资料长度必须介于 0 和 255 之间")
	public String getAdditionalPhoto() {
		return additionalPhoto;
	}

	public void setAdditionalPhoto(String additionalPhoto) {
		this.additionalPhoto = additionalPhoto;
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

    @Override
    public String toString() {
        return "ErpStorePayUnionpay [auditStatus=" + auditStatus + ", bankId=" + bankId + ", lianDan=" + lianDan + ", storePhotoDoorHead=" + storePhotoDoorHead + ", storePhotoCashierDesk=" + storePhotoCashierDesk + ", storePhotoEnvironment=" + storePhotoEnvironment + ", additionalPhoto=" + additionalPhoto + ", bank=" + bank + ", erpStoreInfoId=" + erpStoreInfoId + ", finishStatus=" + finishStatus + "]";
    }
	
}