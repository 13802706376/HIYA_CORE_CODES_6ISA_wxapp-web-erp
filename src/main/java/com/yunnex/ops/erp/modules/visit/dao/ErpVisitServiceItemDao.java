package com.yunnex.ops.erp.modules.visit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceItem;

/**
 * 上门服务项目类型DAO接口
 * @author yunnex
 * @version 2018-07-16
 */
@MyBatisDao
public interface ErpVisitServiceItemDao extends CrudDao<ErpVisitServiceItem> {

    /**
     * 根据visitInfoId 获取勾选服务项目
     *
     * @param visitInfoId
     * @return
     * @date 2018年8月7日
     * @author linqunzhi
     */
    List<ErpVisitServiceItem> findByVisitInfoId(@Param("visitInfoId") String visitInfoId);

    /**
     * 根据visitInfoId 获取上门目的名称
     *
     * @param visitServiceInfoId
     * @return
     * @date 2018年8月9日
     * @author linqunzhi
     */
    String getServiceGoalName(@Param("visitInfoId") String visitInfoId);
	
}