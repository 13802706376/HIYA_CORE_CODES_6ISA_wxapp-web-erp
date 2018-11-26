package com.yunnex.ops.erp.modules.team.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.team.entity.ErpTeamUser;

/**
 * 成员DAO接口
 * @author huanghaidong
 * @version 2017-10-26
 */
@MyBatisDao
public interface ErpTeamUserDao extends CrudDao<ErpTeamUser> {

    void deleteByTeamId(String teamId);

    List<ErpTeamUser> findListByTeamId(@Param("teamId") String teamId);

	List<ErpTeamUser> findwhereuser(@Param("del") String del, @Param("leaderf") String leaderf,
			@Param("userid") String userid);

	List<ErpTeamUser> findwhereteam(@Param("del") String del, @Param("tid") String tid);
}