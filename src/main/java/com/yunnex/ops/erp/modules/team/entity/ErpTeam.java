package com.yunnex.ops.erp.modules.team.entity;

import org.hibernate.validator.constraints.Length;

import com.yunnex.ops.erp.common.persistence.DataEntity;

/**
 * 团队Entity
 * @author huanghaidong
 * @version 2017-10-26
 */
public class ErpTeam extends DataEntity<ErpTeam> {

    private String leaderNames;
    private String memberNames;
	
    public String getLeaderNames() {
        return leaderNames;
    }

    public void setLeaderNames(String leaderNames) {
        this.leaderNames = leaderNames;
    }

    public String getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(String memberNames) {
        this.memberNames = memberNames;
    }

    private static final long serialVersionUID = 1L;
	private String teamName;		// 团队名称
	
	public ErpTeam() {
		super();
	}

	public ErpTeam(String id){
		super(id);
	}

	@Length(min=1, max=100, message="团队名称长度必须介于 1 和 100 之间")
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
}