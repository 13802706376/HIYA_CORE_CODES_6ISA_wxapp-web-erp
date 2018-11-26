package com.yunnex.ops.erp.modules.message.dto;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.RequestDto;

/**
 * 获取服务通知 requestDto
 * 
 * @author linqunzhi
 * @date 2018年7月12日
 */
public class ServiceMessageAllRequestDto extends RequestDto<ServiceMessageAllRequestDto> {

    private static final long serialVersionUID = -735885614522415014L;
    
    /** 掌贝id */
    private String zhangbeiId;
    
    /** 服务类型集合 */
    private List<String> serviceTypeList;

    public String getZhangbeiId() {
        return zhangbeiId;
    }

    public void setZhangbeiId(String zhangbeiId) {
        this.zhangbeiId = zhangbeiId;
    }

    public List<String> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<String> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }
}
