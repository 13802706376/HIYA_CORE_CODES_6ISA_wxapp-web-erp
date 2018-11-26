package com.yunnex.ops.erp.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.sys.entity.JobNumberInfo;

/**
 * 工号管理DAO接口
 * 
 * @author SunQ
 * @date 2018年1月24日
 */
@MyBatisDao
public interface JobNumberInfoDao extends CrudDao<JobNumberInfo> {

    JobNumberInfo getByUserId(@Param("userId") String userId);
}