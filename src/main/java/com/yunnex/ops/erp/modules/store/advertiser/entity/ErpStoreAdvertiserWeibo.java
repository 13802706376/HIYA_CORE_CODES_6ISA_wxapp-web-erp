package com.yunnex.ops.erp.modules.store.advertiser.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 微博广告主开通资料Entity
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStoreAdvertiserWeibo extends DataEntity<ErpStoreAdvertiserWeibo> {
	
	private static final long serialVersionUID = 1L;
	private Integer accountType;		// 账号类型，0：个人账号，1：企业账号，默认0
	private String accountNo;		// 微博登录账号
	private String accountPassword;		// 微博登录密码
	private String uid;		// 微博UID
	private String nickName;		// 微博昵称
	private String relationProveLetter;		// 账号关系证明函
	private String advAuthLetter;		// 广告授权函
	private String promotePromiseLetter;		// 推广承诺函
	private Integer auditStatus = 0;       // 审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0
	private String erpStoreInfoId;     // 封装数据用，数据库中没有对应字段
	private Integer finishStatus;  // 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
	
	public ErpStoreAdvertiserWeibo() {
		super();
	}

	public ErpStoreAdvertiserWeibo(String id){
		super(id);
	}

	@NotNull(message="账号类型，0：个人账号，1：企业账号，默认0不能为空")
	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	
	@Length(min=0, max=64, message="微博登录账号长度必须介于 0 和 64 之间")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Length(min=0, max=64, message="微博登录密码长度必须介于 0 和 64 之间")
	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	
	@Length(min=0, max=64, message="微博UID长度必须介于 0 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=64, message="微博昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=0, max=255, message="账号关系证明函长度必须介于 0 和 255 之间")
	public String getRelationProveLetter() {
		return relationProveLetter;
	}

	public void setRelationProveLetter(String relationProveLetter) {
		this.relationProveLetter = relationProveLetter;
	}
	
	@Length(min=0, max=255, message="广告授权函长度必须介于 0 和 255 之间")
	public String getAdvAuthLetter() {
		return advAuthLetter;
	}

	public void setAdvAuthLetter(String advAuthLetter) {
		this.advAuthLetter = advAuthLetter;
	}
	
	@Length(min=0, max=255, message="推广承诺函长度必须介于 0 和 255 之间")
	public String getPromotePromiseLetter() {
		return promotePromiseLetter;
	}

	public void setPromotePromiseLetter(String promotePromiseLetter) {
		this.promotePromiseLetter = promotePromiseLetter;
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
        return "ErpStoreAdvertiserWeibo [accountType=" + accountType + ", accountNo=" + accountNo + ", accountPassword=" + accountPassword + ", uid=" + uid + ", nickName=" + nickName + ", relationProveLetter=" + relationProveLetter + ", advAuthLetter=" + advAuthLetter + ", promotePromiseLetter=" + promotePromiseLetter + ", auditStatus=" + auditStatus + ", erpStoreInfoId=" + erpStoreInfoId + ", finishStatus=" + finishStatus + "]";
    }
	
}