package com.yunnex.ops.erp.modules.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalInfo;

/**
 * 订单DAO接口
 * @author huanghaidong
 * @version 2017-10-24
 */
@MyBatisDao
public interface ErpOrderOriginalInfoDao extends CrudDao<ErpOrderOriginalInfo> {

    ErpOrderOriginalInfo getDetail(@Param("id") String id);
    
    ErpOrderOriginalInfo getCancelOrderByOrderNo(@Param("orderNumber") String orderNumber, @Param("cancel") Integer cancel);
    
    ErpOrderOriginalInfo getCalcInfo(@Param("id") String id, @Param("goodType") Integer goodType);

    Integer countByOrderNumber(@Param("orderNumber") String orderNumber);

    Integer countByCreateDate(@Param("startAt") String startAt, @Param("endAt") String endAt);

    int updateOrderStatus(@Param("orderNumber") String orderNumber, @Param("orderStatus") Integer orderStatus);

    void cancelOrder(@Param("orderId") String orderId, @Param("cancel") Integer cancel);
	
    /**
     * 通过ID对订单进行物理删除
     *
     * @param id
     * @date 2017年11月28日
     * @author SunQ
     */
    void deleteById(@Param("id") String id);

    /**
     * 根据掌贝id获取有商品待处理大于0的订单
     *
     * @param zhangbeiId
     * @param goodTypeId 为空查询所有
     * @return
     * @date 2018年4月19日
     * @author linqunzhi
     */
     
    List<ErpOrderOriginalInfo> findNoBeginListByZhangbeiId(@Param("zhangbeiId") String zhangbeiId,@Param("goodTypeId") Integer goodTypeId);

    /**
     * 根据掌贝id获取第一条订单信息
     *
     * @param zhangbeiId
     * @param cancel 为空查询所有
     * @return
     * @date 2018年4月20日
     * @author linqunzhi
     */
    ErpOrderOriginalInfo getFirstByZhangbeiId(@Param("zhangbeiId") String zhangbeiId,@Param("cancel") Integer cancel);

}