package com.yunnex.ops.erp.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.sys.dao.JobNumberInfoDao;
import com.yunnex.ops.erp.modules.sys.entity.JobNumberInfo;

/**
 * 工号管理Service
 * 
 * @author SunQ
 * @date 2018年2月7日
 */
@Service
@Transactional(readOnly = true)
public class JobNumberInfoService extends CrudService<JobNumberInfoDao, JobNumberInfo> {

    /**
     * 工号管理Dao
     */
    @Autowired
    private JobNumberInfoDao jobNumberDao;

    @Override
    public JobNumberInfo get(String id) {
        return super.get(id);
    }

    @Override
    public List<JobNumberInfo> findList(JobNumberInfo entity) {
        return super.findList(entity);
    }

    @Override
    public Page<JobNumberInfo> findPage(Page<JobNumberInfo> page, JobNumberInfo entity) {
        return super.findPage(page, entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void save(JobNumberInfo entity) {
        super.save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(JobNumberInfo entity) {
        super.delete(entity);
    }
    
    public JobNumberInfo getByUserId(String userId) {
        return jobNumberDao.getByUserId(userId);
    }
}