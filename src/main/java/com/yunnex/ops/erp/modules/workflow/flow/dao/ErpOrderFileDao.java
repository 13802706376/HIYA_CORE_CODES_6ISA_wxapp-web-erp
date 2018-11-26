package com.yunnex.ops.erp.modules.workflow.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.flow.entity.ErpOrderFile;

/**
 * 订单文件
 * @author yunnex
 * @date 2017年11月2日
 */
@MyBatisDao
public interface ErpOrderFileDao extends CrudDao<ErpOrderFile> {

    List<ErpOrderFile> findListByProcInsId(@Param("procInsId")String procInsId);
    
    int deleteBySplitId (@Param("fileTitle")String fileTitle,@Param("splitId")String splitId);

    List<ErpOrderFile> findListSubTask(@Param("erpOrderFile") ErpOrderFile erpOrderFile);
}