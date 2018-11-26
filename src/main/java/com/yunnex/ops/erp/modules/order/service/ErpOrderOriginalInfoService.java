package com.yunnex.ops.erp.modules.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderOriginalInfoDao;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderSplitInfoDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalInfo;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.workflow.flow.service.WorkFlowMonitorService;

/**
 * 订单Service
 * @author huanghaidong
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderOriginalInfoService extends CrudService<ErpOrderOriginalInfoDao, ErpOrderOriginalInfo> {

    @Autowired
    private ErpOrderOriginalInfoDao erpOrderOriginalInfoDao;
    @Autowired
    private ErpOrderSplitInfoDao erpOrderSplitInfoDao;
    @Autowired
    private WorkFlowMonitorService workFlowMonitorService;

    /**
     * 订单商品
     */
    @Autowired
    private ErpOrderOriginalGoodService erpOrderOriginalGoodService;

    @Override
	public ErpOrderOriginalInfo get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpOrderOriginalInfo> findList(ErpOrderOriginalInfo erpOrderOriginalInfo) {
		return super.findList(erpOrderOriginalInfo);
	}

    @Override
	public Page<ErpOrderOriginalInfo> findPage(Page<ErpOrderOriginalInfo> page, ErpOrderOriginalInfo erpOrderOriginalInfo) {
		return super.findPage(page, erpOrderOriginalInfo);
	}
	
    @Override
	@Transactional(readOnly = false)
    public void save(ErpOrderOriginalInfo erpOrderOriginalInfo) {
		super.save(erpOrderOriginalInfo);
	}
	
    @Override
	@Transactional(readOnly = false)
	public void delete(ErpOrderOriginalInfo erpOrderOriginalInfo) {
		super.delete(erpOrderOriginalInfo);
	}

    public ErpOrderOriginalInfo getDetail(String id) {
        return erpOrderOriginalInfoDao.getDetail(id);
    }
    
    public ErpOrderOriginalInfo getCalcInfo(String id, Integer goodType) {
    	return erpOrderOriginalInfoDao.getCalcInfo(id, goodType);
    }

    public int insert(ErpOrderOriginalInfo erpOrderOriginalInfo) {
        return erpOrderOriginalInfoDao.insert(erpOrderOriginalInfo);
    }

    public Integer countByOrderNumber(String orderNumber) {
        Integer count = erpOrderOriginalInfoDao.countByOrderNumber(orderNumber);
        return null == count ? 0 : count.intValue();
    }

    public Integer countByCreateDate(String startAt, String endAt) {
        Integer count = erpOrderOriginalInfoDao.countByCreateDate(startAt, endAt);
        return null == count ? 0 : count.intValue();
    }

    @Transactional(readOnly = false)
    public void updateOrderStatus(String orderNumber, Integer orderStatus) {
        erpOrderOriginalInfoDao.updateOrderStatus(orderNumber, orderStatus);
    }
    
	public ErpOrderOriginalInfo getUnCancelOrderByOrderNo(String orderNumber, Integer cancel) {
	    return erpOrderOriginalInfoDao.getCancelOrderByOrderNo(orderNumber, cancel);
	}
	
	/**
	 * 作废订单。将该订单下的所有流程结束（不删除流程数据，如变量和文件），然后将该订单标识为作废。
	 *
	 * @param orderId
	 * @throws Exception
	 * @date 2017年11月27日
	 * @author hsr
	 */
	@Transactional(readOnly = false)
    public void cancelOrder(String orderId) throws Exception {
        List<ErpOrderSplitInfo> orderSplitInfo = erpOrderSplitInfoDao.getByOrderId(orderId);
        for (ErpOrderSplitInfo erpOrderSplitInfo : orderSplitInfo) {
            workFlowMonitorService.endProcess(erpOrderSplitInfo.getProcInsId());
        }
	    erpOrderOriginalInfoDao.cancelOrder(orderId, 1);
	}
	
    /**
     * erp创建订单方法
     *
     * @param erpOrderOriginalInfo
     * @param goods
     * @date 2017年11月27日
     * @author SunQ
     */
    @Transactional(readOnly = false)
    public void createOrder(ErpOrderOriginalInfo erpOrderOriginalInfo, List<ErpOrderOriginalGood> goods) {

        /* 保存订单对象 */
        super.save(erpOrderOriginalInfo);

        /* 获取订单的ID */
        String orderId = erpOrderOriginalInfo.getId();

        /* 保存商品信息 */
        for (ErpOrderOriginalGood good : goods) {
            good.setOrderId(orderId);
            erpOrderOriginalGoodService.save(good);
        }
    }

    /**
     * erp删除订单方法
     *
     * @param erpOrderOriginalInfo
     * @date 2017年11月28日
     * @author SunQ
     */
    @Transactional(readOnly = false)
    public void deleteOrder(ErpOrderOriginalInfo erpOrderOriginalInfo) {

        /* 删除之前保存的商品信息 */
        erpOrderOriginalGoodService.deleteByOrderId(erpOrderOriginalInfo.getId());

        /* 删除订单 */
        erpOrderOriginalInfoDao.deleteById(erpOrderOriginalInfo.getId());
    }

    /**
     * erp更新订单方法
     *
     * @param erpOrderOriginalInfo
     * @param goods
     * @date 2017年11月28日
     * @author SunQ
     */
    @Transactional(readOnly = false)
    public void updateOrder(ErpOrderOriginalInfo erpOrderOriginalInfo, List<ErpOrderOriginalGood> goods) {

        /* 获取订单ID */
        String orderId = erpOrderOriginalInfo.getId();

        /* 获取数据库中保存的对象 */
        ErpOrderOriginalInfo dbErpOrderOriginalInfo = erpOrderOriginalInfoDao.get(orderId);

        /* 将修改内容赋值到数据库对象 */
        dbErpOrderOriginalInfo.setUpdateDate(erpOrderOriginalInfo.getUpdateDate());
        dbErpOrderOriginalInfo.setOrderType(erpOrderOriginalInfo.getOrderType());
        dbErpOrderOriginalInfo.setOrderNumber(erpOrderOriginalInfo.getOrderNumber());
        dbErpOrderOriginalInfo.setShopId(erpOrderOriginalInfo.getShopId());
        dbErpOrderOriginalInfo.setShopAbbreviation(erpOrderOriginalInfo.getShopAbbreviation());
        dbErpOrderOriginalInfo.setShopName(erpOrderOriginalInfo.getShopName());
        dbErpOrderOriginalInfo.setShopNumber(erpOrderOriginalInfo.getShopNumber());
        dbErpOrderOriginalInfo.setBuyDate(erpOrderOriginalInfo.getBuyDate());
        dbErpOrderOriginalInfo.setRealPrice(erpOrderOriginalInfo.getRealPrice());
        dbErpOrderOriginalInfo.setIndustryType(erpOrderOriginalInfo.getIndustryType());
        dbErpOrderOriginalInfo.setContactName(erpOrderOriginalInfo.getContactName());
        dbErpOrderOriginalInfo.setContactNumber(erpOrderOriginalInfo.getContactNumber());
        dbErpOrderOriginalInfo.setPromoteContact(erpOrderOriginalInfo.getPromoteContact());
        dbErpOrderOriginalInfo.setPromotePhone(erpOrderOriginalInfo.getPromotePhone());
        dbErpOrderOriginalInfo.setRemark(erpOrderOriginalInfo.getRemark());

        /* 更新数据库对象 */
        super.save(dbErpOrderOriginalInfo);

        /* 删除之前保存的商品信息 */
        erpOrderOriginalGoodService.deleteByOrderId(orderId);

        /* 保存新的商品信息 */
        for (ErpOrderOriginalGood good : goods) {
            good.setOrderId(orderId);
            erpOrderOriginalGoodService.save(good);
        }
    }

    /**
     * 根据掌贝id获取有商品待处理大于0的订单
     *
     * @param zhangbeiId 掌贝id
     * @param goodType 为空 查询所有
     * @return
     * @date 2018年4月19日
     * @author linqunzhi
     */
    public List<ErpOrderOriginalInfo> findNoBeginListByZhangbeiId(String zhangbeiId, Integer goodTypeId) {
        logger.info("findNoBeginListByZhangbeiId start | zhangbeiId={}|goodTypeId={}", zhangbeiId, goodTypeId);
        if(StringUtils.isBlank(zhangbeiId)) {
            logger.error("zhangbeiId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        List<ErpOrderOriginalInfo> result = dao.findNoBeginListByZhangbeiId(zhangbeiId, goodTypeId);
        logger.info("findNoBeginListByZhangbeiId end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 根据掌贝id获取第一条订单信息
     *
     * @param zhangbeiId
     * @param cancel 为空查询所有
     * @return
     * @date 2018年4月20日
     * @author linqunzhi
     */
    public  ErpOrderOriginalInfo getFirstByZhangbeiId(String zhangbeiId, Integer cancel) {
        logger.info("getFirstByZhangbeiId -> 根据掌贝id获取第一条订单信息 start | zhangbeiId={}|cancel={}", zhangbeiId, cancel);
        if(StringUtils.isBlank(zhangbeiId)) {
            logger.error("zhangbeiId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ErpOrderOriginalInfo result = dao.getFirstByZhangbeiId(zhangbeiId, cancel);
        logger.info("getFirstByZhangbeiId -> 根据掌贝id获取第一条订单信息  end | result={}", result);
        return result;
    }

    
}