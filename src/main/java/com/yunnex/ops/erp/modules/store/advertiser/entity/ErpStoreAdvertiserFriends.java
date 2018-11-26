package com.yunnex.ops.erp.modules.store.advertiser.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 朋友圈广告主开通资料Entity
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStoreAdvertiserFriends extends DataEntity<ErpStoreAdvertiserFriends> {
	
	private static final long serialVersionUID = 1L;
	private Integer provideAccountInfo;		// 提供公众号账号、密码,0:否,1:是,默认0
	private String accountNo;		// 公众号登录账号
	private String accountPassword;		// 公众号登录密码
	private String accountOriginalId;		// 公众号原始ID
	private String advertiserScreenshot;		// 广告主开通截图
	private String storeScreenshot;		// 门店开通截图
	private Integer auditStatus = 0;       // 审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0
	private String erpStoreInfoId;     // 封装数据用，数据库中没有对应字段
	private Integer finishStatus;  // 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
	
	public ErpStoreAdvertiserFriends() {
		super();
	}

	public ErpStoreAdvertiserFriends(String id){
		super(id);
	}

	@NotNull(message="提供公众号账号、密码,0:否,1:是,默认0不能为空")
	public Integer getProvideAccountInfo() {
		return provideAccountInfo;
	}

	public void setProvideAccountInfo(Integer provideAccountInfo) {
		this.provideAccountInfo = provideAccountInfo;
	}
	
	@Length(min=0, max=64, message="公众号登录账号长度必须介于 0 和 64 之间")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Length(min=0, max=64, message="公众号登录密码长度必须介于 0 和 64 之间")
	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	
	@Length(min=0, max=64, message="公众号原始ID长度必须介于 0 和 64 之间")
	public String getAccountOriginalId() {
		return accountOriginalId;
	}

	public void setAccountOriginalId(String accountOriginalId) {
		this.accountOriginalId = accountOriginalId;
	}
	
	@Length(min=0, max=255, message="广告主开通截图长度必须介于 0 和 255 之间")
	public String getAdvertiserScreenshot() {
		return advertiserScreenshot;
	}

	public void setAdvertiserScreenshot(String advertiserScreenshot) {
		this.advertiserScreenshot = advertiserScreenshot;
	}
	
	@Length(min=0, max=255, message="门店开通截图长度必须介于 0 和 255 之间")
	public String getStoreScreenshot() {
		return storeScreenshot;
	}

	public void setStoreScreenshot(String storeScreenshot) {
		this.storeScreenshot = storeScreenshot;
	}

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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
        return "ErpStoreAdvertiserFriends [provideAccountInfo=" + provideAccountInfo + ", accountNo=" + accountNo + ", accountPassword=" + accountPassword + ", accountOriginalId=" + accountOriginalId + ", advertiserScreenshot=" + advertiserScreenshot + ", storeScreenshot=" + storeScreenshot + ", auditStatus=" + auditStatus + ", erpStoreInfoId=" + erpStoreInfoId + ", finishStatus=" + finishStatus + "]";
    }
	
}