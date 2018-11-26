package com.yunnex.ops.erp.modules.material.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.material.entity.ErpOrderMaterialCreation;

/**
 * 物料制作DAO接口
 * @author yunnex
 * @version 2018-05-25
 */
@MyBatisDao
public interface ErpOrderMaterialCreationDao extends CrudDao<ErpOrderMaterialCreation> {

    /**
     * 根据流程id获取唯一 数据
     *
     * @param procInsId
     * @return
     * @date 2018年7月24日
     * @author linqunzhi
     */
    ErpOrderMaterialCreation getByProcInsId(@Param("procInsId") String procInsId);

}
