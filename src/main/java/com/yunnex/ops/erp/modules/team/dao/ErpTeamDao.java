package com.yunnex.ops.erp.modules.team.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.team.entity.ErpTeam;

/**
 * 团队DAO接口
 * @author huanghaidong
 * @version 2017-10-26
 */
@MyBatisDao
public interface ErpTeamDao extends CrudDao<ErpTeam> {
	int findteam(@Param("del") String del, @Param("teamid") String teamid);
	
}