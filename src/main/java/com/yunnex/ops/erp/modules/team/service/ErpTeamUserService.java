package com.yunnex.ops.erp.modules.team.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.team.dao.ErpTeamUserDao;
import com.yunnex.ops.erp.modules.team.entity.ErpTeamUser;

/**
 * 成员Service
 * @author huanghaidong
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class ErpTeamUserService extends CrudService<ErpTeamUserDao, ErpTeamUser> {

    @Autowired
    private ErpTeamUserDao erpTeamUserDao;

    @Override
	public ErpTeamUser get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpTeamUser> findList(ErpTeamUser erpTeamUser) {
		return super.findList(erpTeamUser);
	}

    @Override
	public Page<ErpTeamUser> findPage(Page<ErpTeamUser> page, ErpTeamUser erpTeamUser) {
		return super.findPage(page, erpTeamUser);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpTeamUser erpTeamUser) {
		super.save(erpTeamUser);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpTeamUser erpTeamUser) {
		super.delete(erpTeamUser);
	}

    public void deleteByTeamId(String teamId) {
        erpTeamUserDao.deleteByTeamId(teamId);
    }

    public List<ErpTeamUser> findListByTeamId(String teamId) {
        return erpTeamUserDao.findListByTeamId(teamId);
    }

	public List<ErpTeamUser> findwhereuser(String del, String leaderf, String userid) {
		return erpTeamUserDao.findwhereuser(del, leaderf, userid);
	}
	
	public List<ErpTeamUser> findwhereteam(String del, String tid) {
		return erpTeamUserDao.findwhereteam(del, tid);
	}

}