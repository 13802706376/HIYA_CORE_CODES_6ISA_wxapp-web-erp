package com.yunnex.ops.erp.modules.visit.entity;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 上门服务项目类型Entity
 * @author yunnex
 * @version 2018-07-16
 */
public class ErpVisitServiceItem extends DataEntity<ErpVisitServiceItem> {
	
	private static final long serialVersionUID = 1L;
	private String serviceTypeCode;		// 服务类型code
	private String serviceType;		// 服务类型
	private String serviceGoalCode;		// 服务目的code
	private String serviceGoal;		// 服务目的
	private String serviceItemCode;		// 服务项code
	private String serviceItem;		// 服务项
	private Integer serviceTimeLength;		// 服务时长（分钟）
	private String serviceTimeLengthTxt;		// 服务时长（显示字段）
	private String serviceItemAttendees;		// 服务项参与人
	private String serviceFlag;		// 服务标识，0=基本介绍类，1=支付开通类，2=营销策划类，3=培训类，4=物料类，5=售后服务类，6=休息项
	private String defaultFlag;		// 默认标识，Y=是，N=否（需要商户确认是否需要该服务）
	private String remark;		// 备注
	
	public ErpVisitServiceItem() {
		super();
	}

	public ErpVisitServiceItem(String id){
		super(id);
	}

	@Length(min=1, max=1, message="服务类型code长度必须介于 1 和 1 之间")
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}
	
	@Length(min=1, max=20, message="服务类型长度必须介于 1 和 20 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=1, max=20, message="服务目的code长度必须介于 1 和 20 之间")
	public String getServiceGoalCode() {
		return serviceGoalCode;
	}

	public void setServiceGoalCode(String serviceGoalCode) {
		this.serviceGoalCode = serviceGoalCode;
	}
	
	@Length(min=1, max=20, message="服务目的长度必须介于 1 和 20 之间")
	public String getServiceGoal() {
		return serviceGoal;
	}

	public void setServiceGoal(String serviceGoal) {
		this.serviceGoal = serviceGoal;
	}
	
	@Length(min=1, max=20, message="服务项code长度必须介于 1 和 20 之间")
	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}
	
	@Length(min=1, max=20, message="服务项长度必须介于 1 和 20 之间")
	public String getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}
	
	public Integer getServiceTimeLength() {
		return serviceTimeLength;
	}

	public void setServiceTimeLength(Integer serviceTimeLength) {
		this.serviceTimeLength = serviceTimeLength;
	}
	
	@Length(min=0, max=20, message="服务时长（显示字段）长度必须介于 0 和 20 之间")
	public String getServiceTimeLengthTxt() {
		return serviceTimeLengthTxt;
	}

	public void setServiceTimeLengthTxt(String serviceTimeLengthTxt) {
		this.serviceTimeLengthTxt = serviceTimeLengthTxt;
	}
	
	@Length(min=0, max=100, message="服务项参与人长度必须介于 0 和 100 之间")
	public String getServiceItemAttendees() {
		return serviceItemAttendees;
	}

	public void setServiceItemAttendees(String serviceItemAttendees) {
		this.serviceItemAttendees = serviceItemAttendees;
	}
	
	@Length(min=1, max=2, message="服务标识，0=基本介绍类，1=支付开通类，2=营销策划类，3=培训类，4=物料类，5=售后服务类，6=休息项长度必须介于 1 和 2 之间")
	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}
	
	@Length(min=1, max=2, message="默认标识，Y=是，N=否（需要商户确认是否需要该服务）长度必须介于 1 和 2 之间")
	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}