package com.yunnex.ops.erp.modules.order.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.ActEntity;

/**
 * 分单Entity
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
public class ErpOrderSplitInfo extends ActEntity<ErpOrderSplitInfo> {

    public static final int STATUS_PROCESS = 0;

    public static final int HURRY_FLAG_YES = 1;

    private static final long serialVersionUID = 1L;
    private String orderId; // 原始订单id
    private String shopId; // 商户id
    private String orderNumber; // 原始订单编号
    private Integer splitId; // 分单序号
    private String originalGoodId; // 订单商品id
    private String goodName; // 商品名称
    private Long goodTypeId; // 商品类型id
    private String goodTypeName; // 商品类型名称
    private Integer num; // num
    private Long price; // 价格(单位：分)
    private Integer status; // 1处理中 2已完成
    private String remark; // 备注
    private Long sort; // 排序字段
    private Integer hurryFlag; // 加急标识
    // 策划专家接口人
    private String planningExpert;

    private String shopName; // 商户名称

    private List<String> goodTypes; // 商品类型

    private String userId;
    
    private Date promotionTime;
    
    private Date nextContactTime;
    
    private String channel;
    
    private String planningName;
    
    //是否生产库
    private String pendingProdFlag;
    
    //下次营业执照沟通时间
    private Date nextLicenseTime;
    
    //下次资质沟通时间
    private Date nextQualificationTime;
    
    private Integer publishToWxapp = 0;   // 是否发布到小程序，0：否，1：是，默认0

    private Integer commentCount = 0;   // 评论数
    
    //临时小程序api字段
	private String environmentPhoto;	//门店图
	private String erpStoreInfoId;	//门店ID	string	后续查询订单进度传参
	private String shortName;	//门店简称	string	
	private String shopInfoId; //zhangbeiid临时字段
	
    private String pendingProduced = "N"; // 是否进入过待生产库，N：否，Y：是
    private String timeoutFlag = "N"; // 是否存在超时风险，N：否，Y：是
    private Date activationTime; // 从待生产库激活的时间

    private String suspendFlag = "N";// 是否暂停,Y/N
    private String suspendReason;// 暂停原因(1存在资质问题-qualification_problem;2商户主动要求延迟上线ask_for_delay_launch3.退款中refunding)
    private String suspendReasonContent;// 具体暂停原因
    private Date endTime; // 流程结束时间
    private String lookEffectFlag; // 是否查看过效果报告 （Y 是 N 否）

    public String getShopInfoId() {
		return shopInfoId;
	}

	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Date getNextLicenseTime()
	{
		return nextLicenseTime;
	}

	public void setNextLicenseTime(Date nextLicenseTime)
	{
		this.nextLicenseTime = nextLicenseTime;
	}

	public Date getNextQualificationTime()
	{
		return nextQualificationTime;
	}

	public void setNextQualificationTime(Date nextQualificationTime)
	{
		this.nextQualificationTime = nextQualificationTime;
	}

	public String getPendingProdFlag()
	{
		return pendingProdFlag;
	}

	public void setPendingProdFlag(String pendingProdFlag)
	{
		this.pendingProdFlag = pendingProdFlag;
	}

    public Integer getPublishToWxapp() {
        return publishToWxapp;
    }

    public void setPublishToWxapp(Integer publishToWxapp) {
        this.publishToWxapp = publishToWxapp;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getPlanningName() {
		return planningName;
	}

	public void setPlanningName(String planningName) {
		this.planningName = planningName;
	}

	public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPromotionTime() {
        return promotionTime;
    }

    public void setPromotionTime(Date promotionTime) {
        this.promotionTime = promotionTime;
    }

    public Date getNextContactTime() {
        return nextContactTime;
    }

    public void setNextContactTime(Date nextContactTime) {
        this.nextContactTime = nextContactTime;
    }

    public List<String> getGoodTypes() {
        return goodTypes;
    }

    public void setGoodTypes(List<String> goodTypes) {
        this.goodTypes = goodTypes;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPlanningExpert() {
        return planningExpert;
    }

    public void setPlanningExpert(String planningExpert) {
        this.planningExpert = planningExpert;
    }

    public Integer getHurryFlag() {
        return hurryFlag;
    }

    public void setHurryFlag(Integer hurryFlag) {
        this.hurryFlag = hurryFlag;
    }

    public ErpOrderSplitInfo() {
        super();
    }

    public ErpOrderSplitInfo(String id) {
        super(id);
    }

    @NotNull(message = "原始订单id不能为空")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @NotNull(message = "商户id不能为空")
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Length(min = 1, max = 50, message = "原始订单编号长度必须介于 1 和 50 之间")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @NotNull(message = "分单序号不能为空")
    public Integer getSplitId() {
        return splitId;
    }

    public void setSplitId(Integer splitId) {
        this.splitId = splitId;
    }

    @NotNull(message = "订单商品id不能为空")
    public String getOriginalGoodId() {
        return originalGoodId;
    }

    public void setOriginalGoodId(String originalGoodId) {
        this.originalGoodId = originalGoodId;
    }

    @Length(min = 1, max = 50, message = "商品名称长度必须介于 1 和 50 之间")
    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @NotNull(message = "商品类型id不能为空")
    public Long getGoodTypeId() {
        return goodTypeId;
    }

    public void setGoodTypeId(Long goodTypeId) {
        this.goodTypeId = goodTypeId;
    }

    @Length(min = 1, max = 50, message = "商品类型名称长度必须介于 1 和 50 之间")
    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    @NotNull(message = "num不能为空")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @NotNull(message = "价格(单位：分)不能为空")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @NotNull(message = "1处理中 2已完成不能为空")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Length(min = 1, max = 256, message = "备注长度必须介于 1 和 256 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @NotNull(message = "排序字段不能为空")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getPendingProduced() {
        return pendingProduced;
    }

    public void setPendingProduced(String pendingProduced) {
        this.pendingProduced = pendingProduced;
    }

    public String getTimeoutFlag() {
        return timeoutFlag;
    }

    public void setTimeoutFlag(String timeoutFlag) {
        this.timeoutFlag = timeoutFlag;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public String getSuspendFlag() {
        return suspendFlag;
    }

    public void setSuspendFlag(String suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public String getSuspendReason() {
        return suspendReason;
    }

    public void setSuspendReason(String suspendReason) {
        this.suspendReason = suspendReason;
    }

    public String getSuspendReasonContent() {
        return suspendReasonContent;
    }

    public void setSuspendReasonContent(String suspendReasonContent) {
        this.suspendReasonContent = suspendReasonContent;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLookEffectFlag() {
        return lookEffectFlag;
    }

    public void setLookEffectFlag(String lookEffectFlag) {
        this.lookEffectFlag = lookEffectFlag;
    }

}
