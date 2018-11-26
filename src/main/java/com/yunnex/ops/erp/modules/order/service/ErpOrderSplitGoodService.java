package com.yunnex.ops.erp.modules.order.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderSplitGoodDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitGood;

/**
 * 分单商品业务
 * 
 * @author linqunzhi
 * @date 2018年4月8日
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderSplitGoodService extends CrudService<ErpOrderSplitGoodDao, ErpOrderSplitGood> {

    /**
     * 根据分单id获取分单商品信息列表
     *
     * @param splitId
     * @return
     * @date 2018年4月8日
     * @author linqunzhi
     */
    public List<ErpOrderSplitGood> findListBySplitId(String splitId) {
        logger.info("findListBySplitId start | splitId={}", splitId);
        if (StringUtils.isBlank(splitId)) {
            logger.info("splitId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        List<ErpOrderSplitGood> result = dao.findBySplitId(splitId);
        logger.info("findListBySplitId end | result.size={}", result == null ? 0 : result.size());
        return result;

    }

}
