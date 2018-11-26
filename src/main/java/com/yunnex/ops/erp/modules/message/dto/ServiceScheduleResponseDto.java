package com.yunnex.ops.erp.modules.message.dto;

import java.util.Date;

import com.yunnex.ops.erp.common.persistence.ResponseDto;

/**
 * 服务进度 responseDto
 * 
 * @author linqunzhi
 * @date 2018年7月6日
 */
public class ServiceScheduleResponseDto extends ResponseDto<ServiceScheduleResponseDto> {

    private static final long serialVersionUID = -4803117898993953114L;

    /** 服务进度名称 */
    private String name;

    /** 服务进度类型 */
    private String type;

    /** 流程id */
    private String procInsId;

    /** 服务类型 */
    private String serviceType;

    /** 启动时间 */
    private String startTimeStr;

    private Date startTime;

    /** 交互入口类型 */
    private String linkType;

    /** 交互入口参数 */
    private String linkParam;

    /** 状态（NoBegin 未开始，Begin 开始，End 结束） */
    private String status;

    /** 内容 */
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
