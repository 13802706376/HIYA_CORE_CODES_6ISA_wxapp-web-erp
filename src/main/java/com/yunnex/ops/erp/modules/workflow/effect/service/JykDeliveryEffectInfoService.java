package com.yunnex.ops.erp.modules.workflow.effect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.workflow.effect.dao.JykDeliveryEffectInfoDao;
import com.yunnex.ops.erp.modules.workflow.effect.entity.JykDeliveryEffectInfo;

/**
 * 聚引客投放效果Service
 * 
 * @author SunQ
 * @date 2018年1月25日
 */
@Service
@Transactional(readOnly = true)
public class JykDeliveryEffectInfoService extends CrudService<JykDeliveryEffectInfoDao, JykDeliveryEffectInfo> {

    @Autowired
    private JykDeliveryEffectInfoDao jykDeliveryEffectInfoDao;

    @Override
    public JykDeliveryEffectInfo get(String id) {
        return super.get(id);
    }

    @Override
    public List<JykDeliveryEffectInfo> findList(JykDeliveryEffectInfo entity) {
        return super.findList(entity);
    }

    @Override
    public Page<JykDeliveryEffectInfo> findPage(Page<JykDeliveryEffectInfo> page, JykDeliveryEffectInfo entity) {
        return super.findPage(page, entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void save(JykDeliveryEffectInfo entity) {
        super.save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(JykDeliveryEffectInfo entity) {
        super.delete(entity);
    }
    
    @Transactional(readOnly = false)
    public void deleteBefore(String splitId) {
        jykDeliveryEffectInfoDao.deleteBefore(splitId);
    }
    
    @Transactional(readOnly = false)
    public void updateState(String id, String state) {
        jykDeliveryEffectInfoDao.updateState(id, state);
    }
    
    /**
     * 根据分单id获取（已发布小程序 或 确认投放 状态的推广页面预览信息）
     *
     * @param splitId
     * @return
     * @date 2018年4月9日
     * @author linqunzhi
     */
    public JykDeliveryEffectInfo getBySplitId(String splitId) {
        return jykDeliveryEffectInfoDao.getBySplitId(splitId);
    }
}