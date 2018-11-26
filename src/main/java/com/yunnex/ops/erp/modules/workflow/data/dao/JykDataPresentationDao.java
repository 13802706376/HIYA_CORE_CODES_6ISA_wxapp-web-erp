package com.yunnex.ops.erp.modules.workflow.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.data.entity.JykDataPresentation;

@MyBatisDao
public interface JykDataPresentationDao extends CrudDao<JykDataPresentation> {

    /**
     * 根据分单ID获取数据报告对象集合
     *
     * @param splitId
     * @return
     * @date 2018年1月23日
     * @author SunQ
     */
    List<JykDataPresentation> findListBySplitId(@Param("splitId") String splitId);
    
    /**
     * 根据分单ID和状态获取数据报告对象集合
     *
     * @param splitId
     * @param state
     * @return
     * @date 2018年1月23日
     * @author SunQ
     */
    List<JykDataPresentation> findListBySplitIdAndState(@Param("splitId") String splitId, @Param("state") String state);
    
    /**
     * 获取唯一的数据报告对象
     *
     * @param splitId
     * @param state
     * @param dataType
     * @return
     * @date 2018年1月23日
     * @author SunQ
     */
    JykDataPresentation getOnlyOne(@Param("splitId") String splitId, @Param("state") String state, @Param("dataType") String dataType);
}