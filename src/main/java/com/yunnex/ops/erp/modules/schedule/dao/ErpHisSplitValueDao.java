package com.yunnex.ops.erp.modules.schedule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.schedule.entity.ErpHisSplitValue;

/**
 * 生产进度小程序子表DAO接口
 * @author pengchenghe
 * @version 2018-01-19
 */
@MyBatisDao
public interface ErpHisSplitValueDao extends CrudDao<ErpHisSplitValue> {
	List<ErpHisSplitValue> findAllListWhereHisId(@Param("hisId")String hisId);
	
}