package com.yunnex.ops.erp.modules.store.basic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.constants.CommonConstants.FailMsg;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.HttpUtil;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.StoreConstants;
import com.yunnex.ops.erp.modules.store.basic.dao.ErpStorePromotePhotoMaterialDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStorePromotePhotoMaterial;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.store.common.StoreUtils;

/**
 * 推广图片素材Service
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class ErpStorePromotePhotoMaterialService extends CrudService<ErpStorePromotePhotoMaterialDao, ErpStorePromotePhotoMaterial> {

    // 推广图片素材路径
    @Value("${domain.wxapp.res}")
    private String photoBaseUrl;

    @Autowired
    private ShopUtils shopUtils;

    @Value("${api_erp_store_upload_promotional_pictures}")
    private String uploadPromotionalPictures;

    @Override
    public ErpStorePromotePhotoMaterial get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpStorePromotePhotoMaterial> findList(ErpStorePromotePhotoMaterial erpStorePromotePhotoMaterial) {
        return super.findList(erpStorePromotePhotoMaterial);
    }

    @Override
    public Page<ErpStorePromotePhotoMaterial> findPage(Page<ErpStorePromotePhotoMaterial> page,
                    ErpStorePromotePhotoMaterial erpStorePromotePhotoMaterial) {
        return super.findPage(page, erpStorePromotePhotoMaterial);
    }

    /**
     * 根据门店id获取 推广资料
     *
     * @param storeId
     * @return
     * @date 2018年5月29日
     * @author linqunzhi
     */
    public ErpStorePromotePhotoMaterial getByStoreId(String storeId) {
        return dao.getByStoreId(storeId);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpStorePromotePhotoMaterial erpStorePromotePhotoMaterial) {
        super.save(erpStorePromotePhotoMaterial);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpStorePromotePhotoMaterial erpStorePromotePhotoMaterial) {
        super.delete(erpStorePromotePhotoMaterial);
    }

    @Transactional(readOnly = false)
    public String saveInfo(ErpStorePromotePhotoMaterial erpStorePromotePhotoMaterial) {
        logger.info("saveInfo start | erpStorePromotePhotoMaterial={}", JSON.toJSONString(erpStorePromotePhotoMaterial));
        // 检查是否可编辑
        if (erpStorePromotePhotoMaterial != null) {
            String storeId = erpStorePromotePhotoMaterial.getErpStoreInfoId();
            ErpStorePromotePhotoMaterial photo = this.getByStoreId(storeId);
            String id = erpStorePromotePhotoMaterial.getId();
            if (StringUtils.isBlank(id) && photo != null) {
                logger.error("门店已存在推广素材，不能再新增！storeId={}", storeId);
                throw new ServiceException(CommonConstants.FailMsg.PARAM);
            }
            ErpStorePromotePhotoMaterial photoMaterial = super.get(erpStorePromotePhotoMaterial.getId());
            StoreUtils.checkEditable(photoMaterial != null ? photoMaterial.getAuditStatus() : StoreConstants.STATUS_UNCOMMITTED);
            super.save(erpStorePromotePhotoMaterial);
            // 进入推广图片上传流程
            if (erpStorePromotePhotoMaterial != null) {
                id = erpStorePromotePhotoMaterial.getId();
                Map<String, String> map = Maps.newHashMap();
                map.put("storeId", erpStorePromotePhotoMaterial.getErpStoreInfoId());
                String result = HttpUtil.sendHttpPostReqToServerByParams(uploadPromotionalPictures, map);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject == null || jsonObject.getInteger("code") != 0) {
                    throw new RuntimeException("推广图片上传流程流转失败");
                }
            }
            logger.info("saveInfo end | id={}", id);
            return id;
        } else {
            throw new RuntimeException("参数错误！");
        }

    }

    public ErpStorePromotePhotoMaterial getErpStoreInfo(String erpStoreInfoId, Integer isMain) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        return dao.getErpStoreInfo(erpStoreInfoId, loginShop.getId(), isMain);
    }

    /**
     * 根据商户id 获取商户主体门店的推广图片素材
     *
     * @param shopId
     * @return
     * @date 2018年4月2日
     * @author linqunzhi
     */
    public ErpStorePromotePhotoMaterial getStoreMainByShopId(String shopId) {
        logger.info("getStoreMainByShopId start | shopId={}", shopId);
        if (StringUtils.isBlank(shopId)) {
            logger.info("shopId 不能为空");
            throw new ServiceException(FailMsg.PARAM);
        }
        ErpStorePromotePhotoMaterial obj = dao.getStoreMainByShopId(shopId);
        logger.info("getStoreMainByShopId end");
        return obj;
    }
    
    /**
     * 根据商户id 获取商户主体门店图片
     *
     * @param shopId
     * @return
     * @date 2018年4月2日
     * @author linqunzhi
     */
    public String getStoreMainPhotoByShopId(String shopId) {
        logger.info("getStoreMainPhotoByShopId start | shopId={}", shopId);
        String result = null;
        ErpStorePromotePhotoMaterial obj = getStoreMainByShopId(shopId);
        if (obj != null) {
            // 获取门店环境图（多个以逗号隔开，第一个为门店图）
            String photos = obj.getEnvironmentPhoto();
            if (StringUtils.isNotBlank(photos)) {
                result = photoBaseUrl + photos.split(CommonConstants.Sign.SEMICOLON)[0];
            }
        }
        logger.info("getStoreMainPhotoByShopId end | result={}", result);
        return result;
    }

}
