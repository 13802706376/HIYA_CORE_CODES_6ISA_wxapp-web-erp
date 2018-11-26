package com.yunnex.ops.erp.modules.order.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.constants.CommonConstants.FailMsg;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderOriginalGoodDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalGood;

/**
 * 订单商品Service
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderOriginalGoodService extends CrudService<ErpOrderOriginalGoodDao, ErpOrderOriginalGood> {

    @Autowired
    private ErpOrderOriginalGoodDao erpOrderOriginalGoodDao;

    @Override
    public ErpOrderOriginalGood get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpOrderOriginalGood> findList(ErpOrderOriginalGood erpOrderOriginalGood) {
        return super.findList(erpOrderOriginalGood);
    }

    @Override
    public Page<ErpOrderOriginalGood> findPage(Page<ErpOrderOriginalGood> page, ErpOrderOriginalGood erpOrderOriginalGood) {
        return super.findPage(page, erpOrderOriginalGood);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpOrderOriginalGood erpOrderOriginalGood) {
        super.save(erpOrderOriginalGood);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpOrderOriginalGood erpOrderOriginalGood) {
        super.delete(erpOrderOriginalGood);
    }

    public List<ErpOrderOriginalGood> findListByOrderId(String orderId) {
        return erpOrderOriginalGoodDao.findListByOrderId(orderId);
    }

    public List<ErpOrderOriginalGood> findListByOrderInfo(String orderId, Integer goodType) {
        return erpOrderOriginalGoodDao.findListByOrderInfo(orderId, goodType);
    }

    @Transactional(readOnly = false)
    public boolean decreasePendingNum(String id, Integer num) {
        return erpOrderOriginalGoodDao.decreasePendingNum(id, num) > 0;
    }

    @Transactional(readOnly = false)
    public boolean decreaseProcessNum(String id, Integer num) {
        return erpOrderOriginalGoodDao.decreaseProcessNum(id, num) > 0;
    }

    @Transactional(readOnly = false)
    public void deleteByOrderId(String id) {
        erpOrderOriginalGoodDao.deleteByOrderId(id);
    }

    /**
     *  根据订单id获取所有待处理数量大于0的商品服务
     *
     * @param orderId
     * @param goodTypeId 为空查询所有
     * @return
     * @date 2018年4月19日
     * @author linqunzhi
     */
    public List<ErpOrderOriginalGood> findNoBeginListByOrderId(String orderId,Integer goodTypeId) {
        logger.info("findNoBeginListByOrderId start | orderId={}|goodTypeId={}", orderId , goodTypeId);
        if (StringUtils.isBlank(orderId)) {
            logger.info("orderId 不能为空");
            throw new ServiceException(FailMsg.PARAM);
        }
        List<ErpOrderOriginalGood> list = erpOrderOriginalGoodDao.findNoBeginListByOrderId(orderId,goodTypeId);
        logger.info("findNoBeginListByOrderId end |size={}", list == null ? 0 : list.size());
        return list;
    }
    
    /**
     * 根据掌贝id获取有商品总条数
     *
     * @param zhangbeiId 掌贝id
     * @param goodType 为空 查询所有
     * @return
     * @date 2018年4月19日
     * @author linqunzhi
     */
    public int countByZhangbeiId(String zhangbeiId, Integer goodTypeId) {
        logger.info("countByZhangbeiId start | zhangbeiId={}|goodTypeId={}", zhangbeiId, goodTypeId);
        int result = dao.countByZhangbeiId(zhangbeiId, goodTypeId);
        logger.info("countByZhangbeiId end | result={}", result);
        return result;
    }
}
