package com.yunnex.ops.erp.modules.store.basic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfo;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfoList;
import com.yunnex.ops.erp.modules.store.basic.entity.ErpStoreInfoParam;

/**
 * 门店基本信息DAO接口
 * 
 * @author yunnex
 * @version 2017-12-09
 */
@MyBatisDao
public interface ErpStoreInfoDao extends CrudDao<ErpStoreInfo> {
	
    ErpStoreInfoParam getErpStoreInfo(@Param("erpStoreInfoId") String erpStoreInfoId, 
                                      @Param("shopId") String shopId, @Param("isMain") Integer isMain);
    
    void updateFK(@Param("field") String field, @Param("value") String value, @Param("erpStoreInfoId") String erpStoreInfoId);
    
    List<ErpStoreInfoList> getStoreList(ErpStoreInfoList erpStoreInfoList);
    
    Map<String, Integer> getFinishStatus(String erpStoreInfoId);
    
    ErpStoreInfoParam getBasic(@Param("erpStoreInfoId") String erpStoreInfoId, @Param("shopId") String shopId, @Param("isMain") Integer isMain);

    ErpStoreInfoParam getDocuments(@Param("erpStoreInfoId") String erpStoreInfoId, @Param("shopId") String shopId, @Param("isMain") Integer isMain);

    ErpStoreInfoParam getAccounts(@Param("erpStoreInfoId") String erpStoreInfoId, @Param("shopId") String shopId, @Param("isMain") Integer isMain);

    void updatePageEditTag(@Param("erpStoreInfoId") String erpStoreInfoId, @Param("pageEditTag") Integer pageEditTag);

    List<Map<String, String>> queryPassword(Map<String, Object> paramMap);

    void updatePwd(Map<String, Object> paramMap);

    /**
     * 根据掌贝id获取主门店信息
     *
     * @param zhangbeiId
     * @return
     * @date 2018年8月6日
     * @author linqunzhi
     */
    ErpStoreInfo getMainByZhangbeiId(@Param("zhangbeiId") String zhangbeiId);

}