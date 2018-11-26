package com.yunnex.ops.erp.modules.shop.dto;

import com.yunnex.ops.erp.common.persistence.ResponseDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceScheduleResponseDto;

/**
 * 商户顾问数据 responseDto
 * 
 * @author linqunzhi
 * @date 2018年7月3日
 */
public class ShopAdviserResponseDto extends ResponseDto<ShopAdviserResponseDto> {

    private static final long serialVersionUID = 6478578003920244116L;

    /** 用户id */
    private String userId;

    /** 流程角色类型 */
    private String roleType;

    /** 评价描述 */
    private String commentInfo;

    /** 是否是默认显示页 */
    private String defaultFlag;

    /** 职责 */
    private String duty;

    /** 头像路径 */
    private String iconImg;

    /** 工号（花名） */
    private String jobNumber;

    /** 角色名称 */
    private String roleName;

    /** 评价分数 */
    private String score;

    /** 电话号码 */
    private String telephone;

    /** 进行中的服务进度 */
    private ServiceScheduleResponseDto beginService;

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public ServiceScheduleResponseDto getBeginService() {
        return beginService;
    }

    public void setBeginService(ServiceScheduleResponseDto beginService) {
        this.beginService = beginService;
    }

}
