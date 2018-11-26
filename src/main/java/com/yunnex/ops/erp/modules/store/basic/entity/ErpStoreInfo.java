package com.yunnex.ops.erp.modules.store.basic.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 门店基本信息Entity
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStoreInfo extends DataEntity<ErpStoreInfo> {
	
	private static final long serialVersionUID = 1L;
	private Integer isMain = 0;		// 是否是掌贝进件主体,0:否,1:是,默认0
	private String shortName;		// 门店简称
	private String province;       // 省编码
	private String city;		// 市编码
	private String area;		// 区编码
	private String provinceName;   // 省名称
	private String cityName;       // 市名称
	private String areaName;       // 区名称
	private String address;		// 门店经营地址
	private String telephone;		// 门店电话
	private String companyUrl;		// 公司网址
	private String productName;		// 产品名称
	private String productConcreteInfo;		// 投放产品具体信息
	private Integer businessType = 0;		// 商户类型，1：个体工商商户，2：企业商户，默认1
	private Integer auditStatus = 0;		// 审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0
	private String cover;          // 门店封面图片
	private String shopInfoId;		// 商户ID
	private String legalPersonId;		// 法人ID
	private String credentialsId;		// 营业资质ID
	private String weixinPayId;		// 微信支付ID
	private String unionpayId;		// 银联支付ID
	private String advertiserFriendsId;		// 朋友圈广告主ID
	private String advertiserWeiboId;		// 微博广告主ID
	private String advertiserMomoId;		// 陌陌广告主ID
	private Integer finishStatus;  // 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
    private Integer pageEditTag;// 页面编辑标记 1=基本信息；2=证件信息；3=门店照片；4=账号信息；5=推广图片素材
	
	public ErpStoreInfo() {
		super();
	}

	public ErpStoreInfo(String id){
		super(id);
	}

	@NotNull(message="是否是掌贝进件主体,0:否,1:是,默认0不能为空")
	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}
	
	@Length(min=1, max=64, message="门店简称长度必须介于 1 和 64 之间")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Length(min=1, max=64, message="门店经营地址长度必须介于 1 和 64 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=1, max=64, message="门店所在城市长度必须介于 1 和 64 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Length(min=1, max=20, message="门店电话长度必须介于 1 和 20 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=64, message="公司网址长度必须介于 0 和 64 之间")
	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	
	@Length(min=0, max=128, message="产品名称长度必须介于 0 和 128 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=64, message="投放产品具体信息长度必须介于 0 和 64 之间")
	public String getProductConcreteInfo() {
		return productConcreteInfo;
	}

	public void setProductConcreteInfo(String productConcreteInfo) {
		this.productConcreteInfo = productConcreteInfo;
	}
	
	@NotNull(message="商户类型，0：个体工商商户，1：企业商户，默认0不能为空")
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	@NotNull(message="审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0不能为空")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=64, message="门店封面图片长度必须介于 0 和 255 之间")
	public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Length(min=0, max=64, message="商户ID长度必须介于 0 和 64 之间")
	public String getShopInfoId() {
		return shopInfoId;
	}

	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	
	@Length(min=0, max=64, message="法人ID长度必须介于 0 和 64 之间")
	public String getLegalPersonId() {
		return legalPersonId;
	}

	public void setLegalPersonId(String legalPersonId) {
		this.legalPersonId = legalPersonId;
	}
	
	@Length(min=0, max=64, message="营业资质ID长度必须介于 0 和 64 之间")
	public String getCredentialsId() {
		return credentialsId;
	}

	public void setCredentialsId(String credentialsId) {
		this.credentialsId = credentialsId;
	}
	
	@Length(min=0, max=64, message="微信支付ID长度必须介于 0 和 64 之间")
	public String getWeixinPayId() {
		return weixinPayId;
	}

	public void setWeixinPayId(String weixinPayId) {
		this.weixinPayId = weixinPayId;
	}
	
	@Length(min=0, max=64, message="银联支付ID长度必须介于 0 和 64 之间")
	public String getUnionpayId() {
		return unionpayId;
	}

	public void setUnionpayId(String unionpayId) {
		this.unionpayId = unionpayId;
	}
	
	@Length(min=0, max=64, message="朋友圈广告主ID长度必须介于 0 和 64 之间")
	public String getAdvertiserFriendsId() {
		return advertiserFriendsId;
	}

	public void setAdvertiserFriendsId(String advertiserFriendsId) {
		this.advertiserFriendsId = advertiserFriendsId;
	}
	
	@Length(min=0, max=64, message="微博广告主ID长度必须介于 0 和 64 之间")
	public String getAdvertiserWeiboId() {
		return advertiserWeiboId;
	}

	public void setAdvertiserWeiboId(String advertiserWeiboId) {
		this.advertiserWeiboId = advertiserWeiboId;
	}
	
	@Length(min=0, max=64, message="陌陌广告主ID长度必须介于 0 和 64 之间")
	public String getAdvertiserMomoId() {
		return advertiserMomoId;
	}

	public void setAdvertiserMomoId(String advertiserMomoId) {
		this.advertiserMomoId = advertiserMomoId;
	}

    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    public Integer getPageEditTag() {
        return pageEditTag;
    }

    public void setPageEditTag(Integer pageEditTag) {
        this.pageEditTag = pageEditTag;
    }

    @Override
    public String toString() {
        return "ErpStoreInfo [isMain=" + isMain + ", shortName=" + shortName + ", province=" + province + ", city=" + city + ", area=" + area + ", provinceName=" + provinceName + ", cityName=" + cityName + ", areaName=" + areaName + ", address=" + address + ", telephone=" + telephone + ", companyUrl=" + companyUrl + ", productName=" + productName + ", productConcreteInfo=" + productConcreteInfo + ", businessType=" + businessType + ", auditStatus=" + auditStatus + ", cover=" + cover + ", shopInfoId=" + shopInfoId + ", legalPersonId=" + legalPersonId + ", credentialsId=" + credentialsId + ", weixinPayId=" + weixinPayId + ", unionpayId=" + unionpayId + ", advertiserFriendsId=" + advertiserFriendsId + ", advertiserWeiboId=" + advertiserWeiboId + ", advertiserMomoId=" + advertiserMomoId + ", finishStatus=" + finishStatus + "]";
    }
	
}