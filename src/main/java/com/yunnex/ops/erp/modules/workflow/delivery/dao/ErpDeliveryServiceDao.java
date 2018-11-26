package com.yunnex.ops.erp.modules.workflow.delivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.delivery.entity.ErpDeliveryService;

/**
 * erp_delivery_serviceDAO接口
 * 
 * @author hanhan
 * @version 2018-05-26
 */
@MyBatisDao
public interface ErpDeliveryServiceDao extends CrudDao<ErpDeliveryService> {
    ErpDeliveryService getDeliveryInfoByProsIncId(@Param("procInsId") String procInsId);

    List<String> findTaskIdByShopId(String zhangbeiId);

    ErpDeliveryService getDeliveryInfoByShopId(String zhangbeiId);

    ErpDeliveryService getDeliveryInfoByOrederId(@Param("orderId") String orderId);

    /**
     * 正在跑的流程，根据流程类型集合 和 角色类型集合 获取人员id
     * 
     * @param zhangbeiId
     * @param typeList 流程类型
     * @param roleTypeList 角色类型
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    List<String> findBeginByTypeAndRole(@Param("zhangbeiId") String zhangbeiId, @Param("typeList") List<String> typeList,
                    @Param("roleTypeList") List<String> roleTypeList);

    /**
     * 根据掌贝id 和 结束状态 获取列表
     *
     * @param zhangbeiId
     * @param endFlag
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     * @param serviceTypeList
     */
    List<ErpDeliveryService> findByEndFlag(@Param("zhangbeiId") String zhangbeiId, @Param("endFlag") String endFlag,
                    @Param("serviceTypeList") List<String> serviceTypeList);

    /**
     * 获取智慧餐厅服务 列表
     *
     * @param zhangbeiId
     * @param endFlag
     * @return
     * @date 2018年9月3日
     * @author linqunzhi
     */
    List<ErpDeliveryService> findZhiHuiByEndFlag(@Param("zhangbeiId") String zhangbeiId, @Param("endFlag") String endFlag);
}
