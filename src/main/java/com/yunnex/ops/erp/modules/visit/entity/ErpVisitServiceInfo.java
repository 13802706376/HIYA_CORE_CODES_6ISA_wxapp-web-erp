package com.yunnex.ops.erp.modules.visit.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 上门服务主数据Entity
 * 
 * @author yunnex
 * @version 2018-07-16
 */
public class ErpVisitServiceInfo extends DataEntity<ErpVisitServiceInfo> {

    private static final long serialVersionUID = 1L;
    private String shopInfoId; // 商户ID，erp_shop_info.id
    private String serviceAddress; // 上门服务目的地
    private String serviceTypeCode; // 服务类型code，erp_visit_service_item.service_type_code
    private String serviceGoalCode; // 上门服务目的code，erp_visit_service_item.service_goal_code
    private String serviceGoal; // 上门服务目的（手填）
    private String serviceUser; // 上门服务人员,sys_user.id
    private String servicePreparationInfo; // 服务人员准备清单
    private String shopAttendees; // 商户参与人员
    private String shopPreparationInfo; // 商户准备清单
    private String trainRecorder; // 培训记录员
    private String serviceReason; // 服务原因
    private String shopNeedsPicture; // 商户需求截图，英文半角分号分隔
    private Date appointedStartTime; // 预约开始时间
    private Date appointedEndTime; // 预约结束时间
    private Date serviceStartTime; // 服务开始时间
    private Date serviceEndTime; // 服务结束时间
    private String auditStatus; // 审核状态 0=已预约，1=待审核，2=已审核（待上门），3=审核不通过，4=已上门，5=已取消
    private String auditUser; // 审核人,sys_user.id
    private String cancelReason; // 取消原因
    private String modifySuggest; // 修改建议（驳回原因）
    private String receivingReport; // 验收单
    private String procInsId; // 流程实例ID
    private String remark; // 备注
    private String hardwareDeliverFlag; // 是否硬件交付 Y/是，N/否
    private String remindFlag; // 上门提醒标识（Y 已提醒,N 未提醒）

    public ErpVisitServiceInfo() {
        super();
    }

    public ErpVisitServiceInfo(String id) {
        super(id);
    }

    public String getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(String shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    @Length(min = 0, max = 100, message = "上门服务目的地长度必须介于 0 和 100 之间")
    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }

    public String getServiceGoalCode() {
        return serviceGoalCode;
    }

    public void setServiceGoalCode(String serviceGoalCode) {
        this.serviceGoalCode = serviceGoalCode;
    }

    @Length(min = 0, max = 100, message = "上门服务目的（手填）长度必须介于 0 和 100 之间")
    public String getServiceGoal() {
        return serviceGoal;
    }

    public void setServiceGoal(String serviceGoal) {
        this.serviceGoal = serviceGoal;
    }

    public String getServiceUser() {
        return serviceUser;
    }

    public void setServiceUser(String serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Length(min = 0, max = 100, message = "服务人员准备清单长度必须介于 0 和 100 之间")
    public String getServicePreparationInfo() {
        return servicePreparationInfo;
    }

    public void setServicePreparationInfo(String servicePreparationInfo) {
        this.servicePreparationInfo = servicePreparationInfo;
    }

    @Length(min = 0, max = 100, message = "商户参与人员长度必须介于 0 和 100 之间")
    public String getShopAttendees() {
        return shopAttendees;
    }

    public void setShopAttendees(String shopAttendees) {
        this.shopAttendees = shopAttendees;
    }

    @Length(min = 0, max = 100, message = "商户准备清单长度必须介于 0 和 100 之间")
    public String getShopPreparationInfo() {
        return shopPreparationInfo;
    }

    public void setShopPreparationInfo(String shopPreparationInfo) {
        this.shopPreparationInfo = shopPreparationInfo;
    }

    @Length(min = 0, max = 64, message = "培训记录员长度必须介于 0 和 64 之间")
    public String getTrainRecorder() {
        return trainRecorder;
    }

    public void setTrainRecorder(String trainRecorder) {
        this.trainRecorder = trainRecorder;
    }

    @Length(min = 0, max = 100, message = "服务原因长度必须介于 0 和 100 之间")
    public String getServiceReason() {
        return serviceReason;
    }

    public void setServiceReason(String serviceReason) {
        this.serviceReason = serviceReason;
    }

    @Length(min = 0, max = 300, message = "商户需求截图，英文半角分号分隔长度必须介于 0 和 300 之间")
    public String getShopNeedsPicture() {
        return shopNeedsPicture;
    }

    public void setShopNeedsPicture(String shopNeedsPicture) {
        this.shopNeedsPicture = shopNeedsPicture;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "预约开始时间不能为空")
    public Date getAppointedStartTime() {
        return appointedStartTime;
    }

    public void setAppointedStartTime(Date appointedStartTime) {
        this.appointedStartTime = appointedStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAppointedEndTime() {
        return appointedEndTime;
    }

    public void setAppointedEndTime(Date appointedEndTime) {
        this.appointedEndTime = appointedEndTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    @Length(min = 0, max = 1, message = "审核状态 0=已预约，1=待审核，2=已审核（待上门），3=审核不通过，4=已上门，5=已取消长度必须介于 0 和 1 之间")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    @Length(min = 0, max = 100, message = "取消原因长度必须介于 0 和 100 之间")
    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Length(min = 0, max = 100, message = "修改建议（驳回原因）长度必须介于 0 和 100 之间")
    public String getModifySuggest() {
        return modifySuggest;
    }

    public void setModifySuggest(String modifySuggest) {
        this.modifySuggest = modifySuggest;
    }

    @Length(min = 0, max = 300, message = "验收单长度必须介于 0 和 300 之间")
    public String getReceivingReport() {
        return receivingReport;
    }

    public void setReceivingReport(String receivingReport) {
        this.receivingReport = receivingReport;
    }

    @Length(min = 0, max = 64, message = "流程实例ID长度必须介于 0 和 64 之间")
    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    @Length(min = 0, max = 256, message = "备注长度必须介于 0 和 256 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 2, message = "是否硬件交付 Y/是，N/否长度必须介于 0 和 2 之间")
    public String getHardwareDeliverFlag() {
        return hardwareDeliverFlag;
    }

    public void setHardwareDeliverFlag(String hardwareDeliverFlag) {
        this.hardwareDeliverFlag = hardwareDeliverFlag;
    }

    public String getRemindFlag() {
        return remindFlag;
    }

    public void setRemindFlag(String remindFlag) {
        this.remindFlag = remindFlag;
    }
}
