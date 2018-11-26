package com.yunnex.ops.erp.modules.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderGoodServiceInfo;

/**
 * 订单商品服务DAO接口
 * 
 * @author yunnex
 * @version 2018-06-02
 */
@MyBatisDao
public interface ErpOrderGoodServiceInfoDao extends CrudDao<ErpOrderGoodServiceInfo> {
    void insertBatch(@Param("list") List<ErpOrderGoodServiceInfo> list);
    List<ErpOrderGoodServiceInfo> getOrderGoodServiceByOrderId(@Param("orderId") String orderId);
    Map<String, Object> querySum(@Param("orderId") String orderId);
    ErpOrderGoodServiceInfo getOrderGoodServiceByOrderIdSingle(@Param("orderId") String orderId, @Param("itemId") String itemId);

    ErpOrderGoodServiceInfo getOrderGoodServiceExists(@Param("orderId") String orderId, @Param("itemId") String itemId);

    /**
     * 业务定义：根据订单ID新增服务记录
     * 
     * @date 2018年7月3日
     * @author R/Q
     */
    void addByOrderId(String orderId);

    /**
     * 业务定义：根据订单ID删除服务记录
     * 
     * @date 2018年7月3日
     * @author R/Q
     */
    void deleteRecordByOrderId(String orderId);

    /**
     * 获取服务项目分组之后 数量的总数
     *
     * @param zhangbeiId
     * @param itemIdList
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     */
    List<ErpOrderGoodServiceInfo> findByItemIdGroup(@Param("zhangbeiId") String zhangbeiId, @Param("itemIdList") List<String> itemIdList);

}