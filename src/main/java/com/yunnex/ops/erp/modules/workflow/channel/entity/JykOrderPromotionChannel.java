package com.yunnex.ops.erp.modules.workflow.channel.entity;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 聚引客订单推广渠道管理Entity
 * @author Frank
 * @version 2017-10-27
 */
public class JykOrderPromotionChannel extends DataEntity<JykOrderPromotionChannel> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 原始订单信息
	private String splitId;		// 分单序号
	private String promotionChannel;		// 推广渠道
	private String remark;		// 备注
	private Long sort;		// 排序字段
	
	public JykOrderPromotionChannel() {
		super();
	}

	public JykOrderPromotionChannel(String id){
		super(id);
	}

	@Length(min=1, max=64, message="原始订单信息长度必须介于 1 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=1, max=64, message="分单序号长度必须介于 1 和 64 之间")
	public String getSplitId() {
		return splitId;
	}

	public void setSplitId(String splitId) {
		this.splitId = splitId;
	}
	
	@Length(min=1, max=64, message="推广渠道长度必须介于 1 和 64 之间")
	public String getPromotionChannel() {
		return promotionChannel;
	}

	public void setPromotionChannel(String promotionChannel) {
		this.promotionChannel = promotionChannel;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
}