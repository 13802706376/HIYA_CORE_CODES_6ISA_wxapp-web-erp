package com.yunnex.ops.erp.modules.order.entity;

public class ErpBeginOrderApi {
	private String environmentPhoto;	//门店图
	private String erpStoreInfoId;	//门店ID	string	后续查询订单进度传参
	private String goodName;	//商品名称	string	
	private String goodTypeName;	//商品类型	string	
	private String shopName;	//商户名称	string	
	private String shortName;	//门店简称	string	
	private String splitId;	//分单主键
	private String goodTypeId;
	

	public String getGoodTypeId() {
		return goodTypeId;
	}
	public void setGoodTypeId(String goodTypeId) {
		this.goodTypeId = goodTypeId;
	}
	public String getEnvironmentPhoto() {
		return environmentPhoto;
	}
	public void setEnvironmentPhoto(String environmentPhoto) {
		this.environmentPhoto = environmentPhoto;
	}
	public String getErpStoreInfoId() {
		return erpStoreInfoId;
	}
	public void setErpStoreInfoId(String erpStoreInfoId) {
		this.erpStoreInfoId = erpStoreInfoId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodTypeName() {
		return goodTypeName;
	}
	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getSplitId() {
		return splitId;
	}
	public void setSplitId(String splitId) {
		this.splitId = splitId;
	}
}
