package com.yunnex.ops.erp.modules.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceProgress;
import com.yunnex.ops.erp.modules.message.extraModel.ServiceProgressExtra;

/**
 * 服务进度表DAO接口
 * @author yunnex
 * @version 2018-07-04
 */
@MyBatisDao
public interface ErpServiceProgressDao extends CrudDao<ErpServiceProgress> {

    /**
     * 进行中的服务进度，根据条件获取最大启动时间的服务进度
     *
     * @param zhangbeiId
     * @param userId
     * @param roleType
     * @return
     * @date 2018年7月6日
     * @author linqunzhi
     */
    ServiceProgressExtra getBeginMaxStartTime(@Param("zhangbeiId") String zhangbeiId, @Param("userId") String userId,
                    @Param("roleType") String roleType);

    /**
     * 根据服务类型 获取服务进度总数
     *
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    int countByServiceType(@Param("procInsId") String procInsId, @Param("serviceType") String serviceType);

    /**
     * 根据服务类型获取最大启动时间的服务进度
     *
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    ServiceProgressExtra getBeginMaxStartTimeByServiceType(@Param("procInsId") String procInsId, @Param("serviceType") String serviceType);

    /**
     * 根据流程id 获取服务进度列表
     *
     * @param procInsId
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    List<ServiceProgressExtra> findExtra(ServiceProgressExtra progressExtra);
	
}