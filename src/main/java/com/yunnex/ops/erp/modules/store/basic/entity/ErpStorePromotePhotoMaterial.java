package com.yunnex.ops.erp.modules.store.basic.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 推广图片素材Entity
 * 
 * @author yunnex
 * @version 2017-12-09
 */
public class ErpStorePromotePhotoMaterial extends DataEntity<ErpStorePromotePhotoMaterial> {
	
	private static final long serialVersionUID = 1L;
    private Integer auditStatus = 0; // 审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0
    private String environmentPhoto; // 门店环境图
    private String productPhoto; // 产品图
    private Integer environmentPhotoCount; // 门店环境图数量
    private Integer productPhotoCount; // 产品图数量
    private String menuOrServicePhoto; // 菜单/服务类目表图片
    private Integer menuOrServicePhotoCount; // 菜单/服务类目表图片数量
    private String erpStoreInfoId; // 门店信息ID
    private Integer finishStatus; // 资料完成状态，1：未进行，2：未完成，3：当前页面，4：已完成
	
	public ErpStorePromotePhotoMaterial() {
		super();
	}

	public ErpStorePromotePhotoMaterial(String id){
		super(id);
	}

    @NotNull(message = "审核状态，0：未提交，1：待审核，2：正在审核，3：拒绝，4：通过，默认0不能为空")
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
    @Length(min = 0, max = 2048, message = "门店环境图长度必须介于 0 和 2048 之间")
	public String getEnvironmentPhoto() {
		return environmentPhoto;
	}

	public void setEnvironmentPhoto(String environmentPhoto) {
		this.environmentPhoto = environmentPhoto;
	}
	
    @Length(min = 0, max = 2048, message = "产品图长度必须介于 0 和 2048 之间")
	public String getProductPhoto() {
		return productPhoto;
	}

	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}
	
	public Integer getEnvironmentPhotoCount() {
		return environmentPhotoCount;
	}

	public void setEnvironmentPhotoCount(Integer environmentPhotoCount) {
		this.environmentPhotoCount = environmentPhotoCount;
	}
	
	public Integer getProductPhotoCount() {
		return productPhotoCount;
	}

	public void setProductPhotoCount(Integer productPhotoCount) {
		this.productPhotoCount = productPhotoCount;
	}
	
    @Length(min = 0, max = 64, message = "门店信息ID长度必须介于 0 和 64 之间")
    public String getErpStoreInfoId() {
        return erpStoreInfoId;
	}

    public void setErpStoreInfoId(String storeInfoId) {
        this.erpStoreInfoId = storeInfoId;
	}

    public String getMenuOrServicePhoto() {
        return menuOrServicePhoto;
    }

    public void setMenuOrServicePhoto(String menuOrServicePhoto) {
        this.menuOrServicePhoto = menuOrServicePhoto;
    }

    public Integer getMenuOrServicePhotoCount() {
        return menuOrServicePhotoCount;
    }

    public void setMenuOrServicePhotoCount(Integer menuOrServicePhotoCount) {
        this.menuOrServicePhotoCount = menuOrServicePhotoCount;
    }

    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    @Override
    public String toString() {
        return "ErpStorePromotePhotoMaterial [auditStatus=" + auditStatus + ", environmentPhoto=" + environmentPhoto + ", productPhoto=" + productPhoto + ", environmentPhotoCount=" + environmentPhotoCount + ", productPhotoCount=" + productPhotoCount + ", menuOrServicePhoto=" + menuOrServicePhoto + ", menuOrServicePhotoCount=" + menuOrServicePhotoCount + ", erpStoreInfoId=" + erpStoreInfoId + ", finishStatus=" + finishStatus + "]";
    }
	
}