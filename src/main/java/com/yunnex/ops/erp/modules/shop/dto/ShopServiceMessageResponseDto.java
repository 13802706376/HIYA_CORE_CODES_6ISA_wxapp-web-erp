package com.yunnex.ops.erp.modules.shop.dto;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.ResponseDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageResponseDto;

/**
 * 商户首页 服务通知 ResponseDto
 * 
 * @author linqunzhi
 * @date 2018年7月11日
 */
public class ShopServiceMessageResponseDto extends ResponseDto<ShopServiceMessageResponseDto> {

    private static final long serialVersionUID = 4769871054618590444L;

    /** 服务名称 */
    private String serviceName;

    /** 服务*数量 */
    private String serviceNums;

    /** 服务logo */
    private String serviceLogo;

    /** 服务通知信息列表 */
    private List<ServiceMessageResponseDto> messageList;

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

    public List<ServiceMessageResponseDto> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<ServiceMessageResponseDto> messageList) {
        this.messageList = messageList;
    }

    public String getServiceLogo() {
        return serviceLogo;
    }

    public void setServiceLogo(String serviceLogo) {
        this.serviceLogo = serviceLogo;
    }

}
