package com.yunnex.ops.erp.modules.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalGood;

/**
 * 订单商品DAO接口
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
@MyBatisDao
public interface ErpOrderOriginalGoodDao extends CrudDao<ErpOrderOriginalGood> {

    List<ErpOrderOriginalGood> findListByOrderId(@Param("orderId") String orderId);

    List<ErpOrderOriginalGood> findListByOrderInfo(@Param("orderId") String orderId, @Param("goodType") Integer goodType);

    int decreasePendingNum(@Param("id") String id, @Param("num") Integer num);

    int decreaseProcessNum(@Param("id") String id, @Param("num") Integer num);

    /**
     * 通过订单ID删除订单关联的商品信息
     *
     * @param id
     * @return
     * @date 2017年11月28日
     * @author SunQ
     */
    int deleteByOrderId(@Param("orderId") String id);

    /**
     * 根据订单id获取所有待处理数量大于0的商品服务
     *
     * @param orderId
     * @param goodTypeId 为空查询所有
     * @return
     * @date 2018年4月19日
     * @author linqunzhi
     */
    List<ErpOrderOriginalGood> findNoBeginListByOrderId(@Param("orderId") String orderId, @Param("goodTypeId") Integer goodTypeId);

    /**
     * 根据掌贝id获取有商品总条数
     *
     * @param zhangbeiId
     * @param goodTypeId 为空 查询所有
     * @return
     * @date 2018年4月19日
     * @author linqunzhi
     */
    int countByZhangbeiId(@Param("zhangbeiId") String zhangbeiId,@Param("goodTypeId") Integer goodTypeId);
}
