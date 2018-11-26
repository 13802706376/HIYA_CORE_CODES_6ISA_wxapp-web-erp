package com.yunnex.ops.erp.modules.message.dto;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.ResponseDto;

/**
 * 
 * @author linqunzhi
 * @date 2018年7月11日
 */
public class ServiceMessageAllResponseDto extends ResponseDto<ServiceMessageAllResponseDto> {

    private static final long serialVersionUID = 1315122105747239907L;

    /** 总条数 */
    private long count;

    /** 列表集合 */
    private List<ServiceMessageResponseDto> list;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<ServiceMessageResponseDto> getList() {
        return list;
    }

    public void setList(List<ServiceMessageResponseDto> list) {
        this.list = list;
    }

}
