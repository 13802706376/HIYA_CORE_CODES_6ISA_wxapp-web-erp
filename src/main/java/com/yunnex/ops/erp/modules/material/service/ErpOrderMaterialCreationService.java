package com.yunnex.ops.erp.modules.material.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.material.dao.ErpOrderMaterialCreationDao;
import com.yunnex.ops.erp.modules.material.entity.ErpOrderMaterialCreation;

/**
 * 物料制作Service
 * 
 * @author yunnex
 * @version 2018-05-25
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderMaterialCreationService extends CrudService<ErpOrderMaterialCreationDao, ErpOrderMaterialCreation> {

    public ErpOrderMaterialCreation get(String id) {
        return super.get(id);
    }

    @Transactional(readOnly = false)
    public void save(ErpOrderMaterialCreation erpOrderMaterialCreation) {
        super.save(erpOrderMaterialCreation);
    }

    @Transactional(readOnly = false)
    public void delete(ErpOrderMaterialCreation erpOrderMaterialCreation) {
        super.delete(erpOrderMaterialCreation);
    }

    /**
     * 根据流程id获取 物料信息
     *
     * @param procInsId
     * @return
     * @date 2018年7月24日
     * @author linqunzhi
     */
    public ErpOrderMaterialCreation getByProcInsId(String procInsId) {
        ErpOrderMaterialCreation result = dao.getByProcInsId(procInsId);
        return result;
    }


}
