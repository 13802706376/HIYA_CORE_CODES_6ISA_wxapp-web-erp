package com.yunnex.ops.erp.modules.shop.dto;

import com.yunnex.ops.erp.common.persistence.ResponseDto;

/**
 * 商户业务数据 responseDto
 * 
 * @author linqunzhi
 * @date 2018年5月29日
 */
public class ShopBusinessInfoResponseDto extends ResponseDto<ShopBusinessInfoResponseDto> {

    private static final long serialVersionUID = 6478578003920244116L;

    /** 商户名称 */
    private String shopName;

    /** 是否购买过聚引客（Y:是，N：否） */
    private String buyJuYinKeFlag;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBuyJuYinKeFlag() {
        return buyJuYinKeFlag;
    }

    public void setBuyJuYinKeFlag(String buyJuYinKeFlag) {
        this.buyJuYinKeFlag = buyJuYinKeFlag;
    }
}
