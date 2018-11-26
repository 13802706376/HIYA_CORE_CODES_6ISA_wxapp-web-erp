package com.yunnex.ops.erp.modules.order.entity;

public class ErpNoBeginOrderApi {
	private String goodName;	//商品名称	string	
	private String goodTypeName;	//商品类型	string	
	private String id;	//订单主键	string	
	private String shopName;	//商户名称	string
	private String goodTypeId;
	

	public String getGoodTypeId() {
		return goodTypeId;
	}
	public void setGoodTypeId(String goodTypeId) {
		this.goodTypeId = goodTypeId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	

}
