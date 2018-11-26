package com.yunnex.ops.erp.modules.shop.dto;

import com.yunnex.ops.erp.common.persistence.ResponseDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceScheduleResponseDto;

/**
 * 商户服务 ResponseDto
 * 
 * @author linqunzhi
 * @date 2018年7月10日
 */
public class ShopServiceResponseDto extends ResponseDto<ShopServiceResponseDto> {

    private static final long serialVersionUID = -8326098765641667934L;

    /** 流程id */
    private String procInsId;

    /** 商品类型名称 */
    private String goodTypeName;

    /** 商品备注（商品类型×数量） */
    private String goodRemark;

    /** 商品类型图片 */
    private String goodPhotoUrl;

    /** 完成时间 */
    private String finishTime;

    /** 是否有服务进度信息 Y：是，N：否 （Y，才能进行跳转） */
    private String hasScheduleFlag;

    /** 服务类型 */
    private String serviceType;

    /** 进行中的服务进度 */
    private ServiceScheduleResponseDto beginService;

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    public String getGoodRemark() {
        return goodRemark;
    }

    public void setGoodRemark(String goodRemark) {
        this.goodRemark = goodRemark;
    }

    public String getGoodPhotoUrl() {
        return goodPhotoUrl;
    }

    public void setGoodPhotoUrl(String goodPhotoUrl) {
        this.goodPhotoUrl = goodPhotoUrl;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getHasScheduleFlag() {
        return hasScheduleFlag;
    }

    public void setHasScheduleFlag(String hasScheduleFlag) {
        this.hasScheduleFlag = hasScheduleFlag;
    }

    public ServiceScheduleResponseDto getBeginService() {
        return beginService;
    }

    public void setBeginService(ServiceScheduleResponseDto beginService) {
        this.beginService = beginService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
