package com.yunnex.ops.erp.modules.team.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.team.dao.ErpTeamDao;
import com.yunnex.ops.erp.modules.team.entity.ErpTeam;
import com.yunnex.ops.erp.modules.team.entity.ErpTeamUser;

/**
 * 团队Service
 * @author huanghaidong
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class ErpTeamService extends CrudService<ErpTeamDao, ErpTeam> {

    @Autowired
    private ErpTeamUserService erpTeamUserService;
	@Autowired
	private ErpTeamDao erpTeamDao;


    @Override
	public ErpTeam get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpTeam> findList(ErpTeam erpTeam) {
		return super.findList(erpTeam);
	}

    @Override
	public Page<ErpTeam> findPage(Page<ErpTeam> page, ErpTeam erpTeam) {
		return super.findPage(page, erpTeam);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpTeam erpTeam) {
		super.save(erpTeam);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpTeam erpTeam) {
		super.delete(erpTeam);
	}

    @Transactional(readOnly = false)
    public void saveWithMemberAndLeader(ErpTeam erpTeam, String teamLeaderIds, String teamMemberIds) {
        super.save(erpTeam);
        updateTeamMemberAndLeader(erpTeam.getId(), teamLeaderIds, teamMemberIds);
    }

    @Transactional(readOnly = false)
    public void updateTeamMemberAndLeader(String teamId, String teamLeaderIds, String teamMemberIds) {
        erpTeamUserService.deleteByTeamId(teamId);

        Map<String, String> leaderMap = new HashMap<String, String>();
        Set<String> memberIdsSet = new HashSet<String>();
        if (StringUtils.isNotEmpty(teamLeaderIds)) {
            String[] leadersArray = teamLeaderIds.split(",");
            for (int i = 0; i < leadersArray.length; i++) {
                memberIdsSet.add(leadersArray[i]);
                leaderMap.put(leadersArray[i], "1");
            }
        }   
        if (StringUtils.isNotEmpty(teamMemberIds)) {
            String[] membersArray = teamMemberIds.split(",");
            for (int i = 0; i < membersArray.length; i++) {
                memberIdsSet.add(membersArray[i]);
            }
        }
        Iterator<String> it = memberIdsSet.iterator();
        while (it.hasNext()) {
            String userId = it.next();
            ErpTeamUser erpTeamUser = new ErpTeamUser();
            erpTeamUser.setTeamId(teamId);
            erpTeamUser.setUserId(userId);
            if (StringUtils.isNotEmpty(leaderMap.get(userId))) {
                erpTeamUser.setLeaderFlag(ErpTeamUser.LEADER_FLAG_YES);
            } else {
                erpTeamUser.setLeaderFlag(ErpTeamUser.LEADER_FLAG_NO);
            }
            erpTeamUserService.save(erpTeamUser);
        }
    }

	@Transactional(readOnly = true)
	public int findteam(String del, String teamid) {
		return erpTeamDao.findteam(del, teamid);
	}
}