package com.yunnex.ops.erp.modules.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;

/**
 * 分单DAO接口
 * @author huanghaidong
 * @version 2017-10-24
 */
@MyBatisDao
public interface ErpOrderSplitInfoDao extends CrudDao<ErpOrderSplitInfo> {

    int countByOrderId(@Param("orderId") String orderId);

    List<ErpOrderSplitInfo> findListByOrderId(@Param("orderId") String orderId);

	List<ErpOrderSplitInfo> findListByOrderInfo(@Param("orderId") String orderId, @Param("goodType") Integer goodType);
	
    int updateNum(@Param("id") String id, @Param("num") Integer num);
	
    int updateHurryFlag(@Param("id") String id, @Param("hurryFlag") Integer hurryFlag);
    
    List<ErpOrderSplitInfo> findListByParams(ErpOrderSplitInfo entity);
    
    List<String> findFollowOrderByParams(ErpOrderSplitInfo erpOrderSplitInfo);
            
    ErpOrderSplitInfo getByProcInstId(String procInsId);

	List<ErpOrderSplitInfo> getBystate(@Param("status") Integer status,@Param("userId") String userId);
	
	List<ErpOrderSplitInfo> findcomplete(@Param("status") Integer status,@Param("del") String del,@Param("orderNumber") String orderNumber,@Param("splitId")String splitId,@Param("shopId")String shopId,@Param("hurryFlag") Integer hurryFlag);

	List<ErpOrderSplitInfo> findListByOrderInfoAndUser(@Param("orderId") String orderId, @Param("goodType") Integer goodType);

    List<ErpOrderSplitInfo> getByOrderId(@Param("orderId") String orderId);
    
    void updateState(@Param("pendingProdFlag") String pendingProdFlag, @Param("taskState") String taskState, @Param("id") String id);
    
    List<ErpOrderSplitInfo> findBeginOrder(@Param("zhangbeiId") String zhangbeiId);
    
    List<ErpOrderSplitInfo> findOverOrder(@Param("zhangbeiId") String zhangbeiId);
    
    List<ErpOrderSplitInfo> findNoBeginOrder(@Param("zhangbeiId") String zhangbeiId);
    
    Map<String, String> getDiagnosisTaskInfo(String splitId);
    
    boolean updateCommentCount(@Param("id") String id, @Param("commentCount") Integer commentCount);

    boolean updatePendingStatus(@Param("id") String id, @Param("activationTime") Date activationTime,
                    @Param("pendingProdFlag") String pendingProdFlag);

    boolean updateRiskStatus(@Param("timeoutFlag") String timeoutFlag, @Param("jykOrderIds") Set<String> jykOrderIds);
    
    /**
     * 根据掌贝id获取进行时分单列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年4月3日
     * @author linqunzhi
     */
    List<ErpOrderSplitInfo> findListByZhangbeiIdStatus(@Param("zhangbeiId") String zhangbeiId , @Param("status") Integer status);

    /** 
     *  计算所有订单中包含客常来类型，其他类型的 其他类型服务数量总数(订单中商品有goodTypeIds中任意一个，计算商品中goodIds的总num值)
     *
     * @param zhangbeiId
     * @param goodTypeIds
     * @param goodIds
     * @return
     * @date 2018年4月9日
     * @author linqunzhi
     */
    int countKeBeginByZhangbeiId(@Param("zhangbeiId") String zhangbeiId, @Param("goodTypeIds") String[] goodTypeIds,
                    @Param("goodIds") String[] goodIds);

    /**
     * 正在跑的流程，根据zhangbeiid、流程角色类型 获取用户id
     *
     * @param zhangbeiId
     * @param roleTypeList
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    List<String> findBeginByRole(@Param("zhangbeiId") String zhangbeiId, @Param("roleTypeList") List<String> roleTypeList);

    /**
     * 修改分单数据
     *
     * @param info
     * @date 2018年7月23日
     * @author linqunzhi
     */
    void updateInfo(ErpOrderSplitInfo info);
}
