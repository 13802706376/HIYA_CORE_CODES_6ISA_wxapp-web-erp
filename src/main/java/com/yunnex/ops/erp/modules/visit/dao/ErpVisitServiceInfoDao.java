package com.yunnex.ops.erp.modules.visit.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceInfo;

/**
 * 上门服务主数据DAO接口
 * @author yunnex
 * @version 2018-07-16
 */
@MyBatisDao
public interface ErpVisitServiceInfoDao extends CrudDao<ErpVisitServiceInfo> {

    /**
     * 根据流程id 和 上门目的code 获取唯一上门信息
     *
     * @param procInsId
     * @param serviceGoalCode
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    ErpVisitServiceInfo getByGoalCode(@Param("procInsId") String procInsId, @Param("serviceGoalCode") String serviceGoalCode);
	
}