/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yunnex.ops.erp.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.TreeDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

    void updateName(@Param("id") String id, @Param("name") String name);

    Integer countByParentId(@Param("parentId") String parentId);
}
