package com.yunnex.ops.erp.modules.workflow.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.workflow.data.dao.JykDataPresentationDao;
import com.yunnex.ops.erp.modules.workflow.data.entity.JykDataPresentation;

/**
 * 数据报告查询Service
 * 
 * @author SunQ
 * @date 2018年1月23日
 */
@Service
@Transactional(readOnly = true)
public class JykDataPresentationService extends CrudService<JykDataPresentationDao, JykDataPresentation> {

    @Autowired
    private JykDataPresentationDao jykDataPresentationDao;

    @Override
    public JykDataPresentation get(String id) {
        return super.get(id);
    }

    @Override
    public JykDataPresentation get(JykDataPresentation entity) {
        return super.get(entity);
    }

    @Override
    public List<JykDataPresentation> findList(JykDataPresentation entity) {
        return super.findList(entity);
    }

    @Override
    public Page<JykDataPresentation> findPage(Page<JykDataPresentation> page, JykDataPresentation entity) {
        return super.findPage(page, entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void save(JykDataPresentation entity) {
        super.save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(JykDataPresentation entity) {
        super.delete(entity);
    }
    
    public List<JykDataPresentation> findListBySplitId(String splitId) {
        return jykDataPresentationDao.findListBySplitId(splitId);
    }
    
    public List<JykDataPresentation> findListBySplitIdAndState(String splitId, String state) {
        return jykDataPresentationDao.findListBySplitIdAndState(splitId, state);
    }
    
    public JykDataPresentation getOnlyOne(String splitId, String state, String dataType) {
        return jykDataPresentationDao.getOnlyOne(splitId, state, dataType);
    }
}