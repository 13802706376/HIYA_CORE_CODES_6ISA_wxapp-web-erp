package com.yunnex.ops.erp.modules.shop.dto;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.ResponseDto;

/**
 * 服务进度列表 ResponseDto
 * 
 * @author linqunzhi
 * @date 2018年7月10日
 */
public class ShopServiceAllResponseDto extends ResponseDto<ShopServiceAllResponseDto> {

    private static final long serialVersionUID = -7184834556269540991L;

    /** 未开始服务 */
    private List<ShopServiceResponseDto> noBeginList;

    /** 进行中服务 */
    private List<ShopServiceResponseDto> beginList;

    /** 已完成服务 */
    private List<ShopServiceResponseDto> finishList;

    public List<ShopServiceResponseDto> getNoBeginList() {
        return noBeginList;
    }

    public void setNoBeginList(List<ShopServiceResponseDto> noBeginList) {
        this.noBeginList = noBeginList;
    }

    public List<ShopServiceResponseDto> getBeginList() {
        return beginList;
    }

    public void setBeginList(List<ShopServiceResponseDto> beginList) {
        this.beginList = beginList;
    }

    public List<ShopServiceResponseDto> getFinishList() {
        return finishList;
    }

    public void setFinishList(List<ShopServiceResponseDto> finishList) {
        this.finishList = finishList;
    }

}
