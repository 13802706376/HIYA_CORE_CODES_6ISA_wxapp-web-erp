package com.yunnex.ops.erp.modules.message.dto;

import com.yunnex.ops.erp.common.persistence.ResponseDto;

public class ServiceMessageResponseDto extends ResponseDto<ServiceMessageResponseDto> {

    private static final long serialVersionUID = -3050469630001976364L;

    /** 服务通知id */
    private String id;

    /** 服务名称 */
    private String serviceName;

    /** 服务*数量 */
    private String serviceNums;

    /** 服务logo */
    private String serviceLogo;

    /** 服务*数量 */
    private String content;

    /** 类型(Pending 待处理，Key 关键信息) */
    private String type;

    /** 跳转类型 */
    private String linkType;

    /** 跳转参数 */
    private String linkParam;

    /** 通知时间 */
    private String startDateStr;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNums() {
        return serviceNums;
    }

    public void setServiceNums(String serviceNums) {
        this.serviceNums = serviceNums;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getServiceLogo() {
        return serviceLogo;
    }

    public void setServiceLogo(String serviceLogo) {
        this.serviceLogo = serviceLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }
}
