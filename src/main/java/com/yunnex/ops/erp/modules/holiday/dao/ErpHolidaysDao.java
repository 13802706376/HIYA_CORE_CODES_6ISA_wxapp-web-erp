package com.yunnex.ops.erp.modules.holiday.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.holiday.entity.ErpHolidays;

/**
 * 节假日配置DAO接口
 * @author pch
 * @version 2017-11-02
 */
@MyBatisDao
public interface ErpHolidaysDao extends CrudDao<ErpHolidays> {
	public Date enddate(Date stardate, Integer hourage);

	public int getholiday(@Param("stardate") Date stardate, @Param("enddate") Date enddate,@Param("del")String del);
	
	public int wheredate(@Param("inputdate") String inputdate,@Param("del")String del); 

}