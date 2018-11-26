package com.yunnex.ops.erp.modules.workflow.effect.dao;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.effect.entity.JykDeliveryEffectInfo;

/**
 * 聚引客投放效果Dao
 * 
 * @author SunQ
 * @date 2018年1月25日
 */
@MyBatisDao
public interface JykDeliveryEffectInfoDao extends CrudDao<JykDeliveryEffectInfo> {

    /**
     * 逻辑删除之前上传的内容
     *
     * @param splitId
     * @date 2018年1月25日
     * @author SunQ
     */
    void deleteBefore(@Param("splitId") String splitId);
    
    /**
     * 更新状态
     *
     * @param id
     * @param state
     * @date 2018年1月25日
     * @author SunQ
     */
    void updateState(@Param("id") String id, @Param("state") String state);
    
    /**
     * 获取分单对应渠道的投放效果对象
     *
     * @param splitId
     * @return
     * @date 2018年1月25日
     * @author SunQ
     */
    JykDeliveryEffectInfo getBySplitId(@Param("splitId") String splitId);
}