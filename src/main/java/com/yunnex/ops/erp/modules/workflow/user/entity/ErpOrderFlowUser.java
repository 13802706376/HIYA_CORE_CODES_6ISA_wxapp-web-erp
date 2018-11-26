package com.yunnex.ops.erp.modules.workflow.user.entity;

import org.hibernate.validator.constraints.Length;
import com.yunnex.ops.erp.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 工作流人员关系Entity
 * @author Frank
 * @version 2017-10-27
 */
public class ErpOrderFlowUser extends DataEntity<ErpOrderFlowUser> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 原始订单信息
	private String splitId;		// 分单序号
	private String flowId;		// 流程编号
	private User user;		// 人员编号
	private String flowUserId; //用户标识 
	private String remark;		// 备注
	
	public String getFlowUserId() {
        return flowUserId;
    }


    public void setFlowUserId(String flowUserId) {
        this.flowUserId = flowUserId;
    }


    public ErpOrderFlowUser() {
		super();
	}
	

	public ErpOrderFlowUser(String id){
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
	
	@Length(min=1, max=64, message="流程编号长度必须介于 1 和 64 之间")
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@NotNull(message="人员编号不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}