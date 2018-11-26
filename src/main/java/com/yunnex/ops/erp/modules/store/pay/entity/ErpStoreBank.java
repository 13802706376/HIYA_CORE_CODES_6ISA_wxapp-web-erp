package com.yunnex.ops.erp.modules.store.pay.entity;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 银行信息Entity
 * 
 * @author yunnex
 * @version 2017-12-15
 */
public class ErpStoreBank extends DataEntity<ErpStoreBank> {

	private static final long serialVersionUID = 1L;
    private Integer accountType; // 账号类型，1：对公账号，2：法人账号，默认1
    private Integer bankId; // 银行编码，唯一对应银行名称
    private String bankName; // 银行名称
    private String openAccountName; // 开户名称
    private String branchBankName; // 开户名称
    private String creditCardNo; // 银行卡号
    private String bankNo; // 银行联行号，唯一对应支行
    private Integer zhangbeiBindCount; // 当前银联账号绑定掌贝设备数量
    private Integer payWay; // 支付方式，0：微信，1：银联
    private String province; // 省编码
    private String provinceName; // 省名称
    private String city; // 市编码
    private String cityName; // 市名称
    private String area; // 区编码
    private String areaName; // 区名称
    private String creditCardFrontPhoto; // 法人银行卡正面照
    private String creditCardReversePhoto; // 法人银行卡反面照
    private String openAccountLicence; // 开户许可证或银联印鉴证
    private String authorizeProxy; // 结算账号授权委托书
    private String shopInfoId; // 商户ID，数据库无对应属性，封装数据用
    private String erpStoreInfoId;// 门店基本信息ID
    private String finishStatus;// 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
	
	public ErpStoreBank() {
		super();
	}

	public ErpStoreBank(String id){
		super(id);
	}

	public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    @Length(min = 1, max = 64, message = "银行名称长度必须介于 1 和 64 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
    @Length(min = 0, max = 64, message = "开户名称长度必须介于 0 和 64 之间")
	public String getOpenAccountName() {
		return openAccountName;
	}

	public void setOpenAccountName(String openAccountName) {
		this.openAccountName = openAccountName;
	}
	
    @Length(min = 0, max = 64, message = "开户名称长度必须介于 0 和 64 之间")
	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	
    @Length(min = 1, max = 64, message = "银行卡号长度必须介于 1 和 64 之间")
	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	
    @Length(min = 0, max = 64, message = "银行联行号，唯一对应支行长度必须介于 0 和 64 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getZhangbeiBindCount() {
        return zhangbeiBindCount;
    }

    public void setZhangbeiBindCount(Integer zhangbeiBindCount) {
        this.zhangbeiBindCount = zhangbeiBindCount;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public String getCreditCardFrontPhoto() {
        return creditCardFrontPhoto;
    }

    public void setCreditCardFrontPhoto(String creditCardFrontPhoto) {
        this.creditCardFrontPhoto = creditCardFrontPhoto;
    }

    public String getCreditCardReversePhoto() {
        return creditCardReversePhoto;
    }

    public void setCreditCardReversePhoto(String creditCardReversePhoto) {
        this.creditCardReversePhoto = creditCardReversePhoto;
    }

    public String getOpenAccountLicence() {
        return openAccountLicence;
    }

    public void setOpenAccountLicence(String openAccountLicence) {
        this.openAccountLicence = openAccountLicence;
    }

    public String getAuthorizeProxy() {
        return authorizeProxy;
    }

    public void setAuthorizeProxy(String authorizeProxy) {
        this.authorizeProxy = authorizeProxy;
    }

    public String getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(String shopInfoId) {
        this.shopInfoId = shopInfoId;
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
        return "ErpStoreBank [accountType=" + accountType + ", bankId=" + bankId + ", bankName=" + bankName + ", openAccountName=" + openAccountName + ", branchBankName=" + branchBankName + ", creditCardNo=" + creditCardNo + ", bankNo=" + bankNo + ", zhangbeiBindCount=" + zhangbeiBindCount + ", payWay=" + payWay + ", province=" + province + ", provinceName=" + provinceName + ", city=" + city + ", cityName=" + cityName + ", area=" + area + ", areaName=" + areaName + ", creditCardFrontPhoto=" + creditCardFrontPhoto + ", creditCardReversePhoto=" + creditCardReversePhoto + ", openAccountLicence=" + openAccountLicence + ", authorizeProxy=" + authorizeProxy + ", shopInfoId=" + shopInfoId + ",erpStoreInfoId=" + erpStoreInfoId + ",finishStatus=" + finishStatus + "]";
    }
	
}