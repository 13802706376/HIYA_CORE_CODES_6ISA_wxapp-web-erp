package com.yunnex.ops.erp.modules.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.modules.order.constans.OrderGoodConstants;
import com.yunnex.ops.erp.modules.order.constans.SplitConstants;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderSplitInfoDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalInfo;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.view.OrderSplitInfoView;
import com.yunnex.ops.erp.modules.sys.entity.SysConstants;
import com.yunnex.ops.erp.modules.sys.service.SysConstantsService;
import com.yunnex.ops.erp.modules.sys.utils.UserUtils;
import com.yunnex.ops.erp.modules.workflow.channel.entity.JykOrderPromotionChannel;
import com.yunnex.ops.erp.modules.workflow.data.constants.JykDataPresentationConstants;
import com.yunnex.ops.erp.modules.workflow.data.entity.JykDataPresentation;
import com.yunnex.ops.erp.modules.workflow.data.service.JykDataPresentationService;
import com.yunnex.ops.erp.modules.workflow.effect.entity.JykDeliveryEffectInfo;
import com.yunnex.ops.erp.modules.workflow.effect.service.JykDeliveryEffectInfoService;

/**
 * 分单Service
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderSplitInfoService extends CrudService<ErpOrderSplitInfoDao, ErpOrderSplitInfo> {

    @Autowired
    private ErpOrderOriginalGoodService erpOrderOriginalGoodService;

    @Autowired
    private ErpOrderOriginalInfoService erpOrderOriginalInfoService;

    @Autowired
    private ErpOrderSplitInfoDao erpOrderSplitInfoDao;
    @Autowired
    private ErpOrderSplitGoodService erpOrderSplitGoodService;
    @Autowired
    private SysConstantsService sysConstantsService;
    @Autowired
    private JykDataPresentationService jykDataPresentationService;
    @Autowired
    private JykDeliveryEffectInfoService jykDeliveryEffectInfoService;
    // 图片base路径
    @Value("${domain.wxapp.res}")
    private String photoBaseUrl;

    private final String GOOD_TYPE_NAME_JU = "引流推广服务";
    
    private final String GOOD_TYPE_NAME_KE = "客常来服务";

    @Override
    public ErpOrderSplitInfo get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpOrderSplitInfo> findList(ErpOrderSplitInfo erpOrderSplitInfo) {
        return super.findList(erpOrderSplitInfo);
    }

    @Override
    public Page<ErpOrderSplitInfo> findPage(Page<ErpOrderSplitInfo> page, ErpOrderSplitInfo erpOrderSplitInfo) {
        return super.findPage(page, erpOrderSplitInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpOrderSplitInfo erpOrderSplitInfo) {
        super.save(erpOrderSplitInfo);


    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpOrderSplitInfo erpOrderSplitInfo) {
        super.delete(erpOrderSplitInfo);
    }

	@Transactional(readOnly = true)
	public List<ErpOrderSplitInfo> getBystate(Integer status) {
	    
		return erpOrderSplitInfoDao.getBystate(status,UserUtils.getUser().getId());
	}
	@Transactional(readOnly = true)
	public List<ErpOrderSplitInfo> findcomplete(Integer status, String del, String orderNumber,String splitId,String shopId,Integer hurryFlag){
		return erpOrderSplitInfoDao.findcomplete(status, del, orderNumber, splitId, shopId, hurryFlag);
	}


    /**
     * 分单操作
     *
     * @param goodId 订单商品id
     * @param num 数量
     * @return
     * @date 2017年10月26日
     * @author huanghaidong
     */
    @Transactional(readOnly = false)
    public ErpOrderSplitInfo split(String goodId, Integer num, String planningExpert) {
        ErpOrderOriginalGood erpOrderOriginalGood = erpOrderOriginalGoodService.get(String.valueOf(goodId));
        if (null == erpOrderOriginalGood || Integer.compare(erpOrderOriginalGood.getPendingNum(), num) < 0) {
            return null;
        }
        ErpOrderOriginalInfo erpOrderOriginalInfo = erpOrderOriginalInfoService.get(String.valueOf(erpOrderOriginalGood.getOrderId()));
        int count = countByOrderId(erpOrderOriginalGood.getOrderId());
        ErpOrderSplitInfo erpOrderSplitInfo = new ErpOrderSplitInfo();
        erpOrderSplitInfo.setOrderId(erpOrderOriginalGood.getOrderId());
        erpOrderSplitInfo.setShopId(erpOrderOriginalInfo.getShopId());
        erpOrderSplitInfo.setOrderNumber(erpOrderOriginalInfo.getOrderNumber());
        erpOrderSplitInfo.setSplitId(count + 1);
        erpOrderSplitInfo.setOriginalGoodId(erpOrderOriginalGood.getId());
        erpOrderSplitInfo.setGoodName(erpOrderOriginalGood.getGoodName());
        erpOrderSplitInfo.setGoodTypeId(erpOrderOriginalGood.getGoodTypeId());
        erpOrderSplitInfo.setGoodTypeName(erpOrderOriginalGood.getGoodTypeName());
        erpOrderSplitInfo.setNum(num);
        erpOrderSplitInfo.setPrice(erpOrderOriginalGood.getRealPrice());
        erpOrderSplitInfo.setStatus(ErpOrderSplitInfo.STATUS_PROCESS);
        erpOrderSplitInfo.setPlanningExpert(planningExpert);
        super.save(erpOrderSplitInfo);
        // 减少订单商品待处理商品数量，增加订单商品处理中的数量
        erpOrderOriginalGoodService.decreasePendingNum(goodId, num);
        return erpOrderSplitInfo;
    }

    private Integer countByOrderId(String orderId) {
        return erpOrderSplitInfoDao.countByOrderId(orderId);
    }

    public List<ErpOrderSplitInfo> findListByOrderId(String orderId) {
        return erpOrderSplitInfoDao.findListByOrderId(orderId);
    }
    
    public List<ErpOrderSplitInfo> findListByOrderInfo(String orderId, Integer goodType) {
    	return erpOrderSplitInfoDao.findListByOrderInfo(orderId, goodType);
    }

    /**
     * 编辑分单任务量，大于当前任务量，则扣除订单商品待处理量，如果小于当前任务量，则将多余的返回给订单商品待处理量
     *
     * @param id
     * @param num
     * @return
     * @date 2017年10月26日
     * @author huanghaidong
     */
    @Transactional(readOnly = false)
    public boolean editNum(String id, Integer num) {
        ErpOrderSplitInfo erpOrderSplitInfo = get(id);
        int difference = num - erpOrderSplitInfo.getNum();
        if (difference > 0) {
            ErpOrderOriginalGood erpOrderOriginalGood = erpOrderOriginalGoodService.get(erpOrderSplitInfo.getOriginalGoodId());
            if (erpOrderOriginalGood.getPendingNum() < difference) {
                return false;
            }
        }
        erpOrderSplitInfoDao.updateNum(id, num);
        erpOrderOriginalGoodService.decreasePendingNum(erpOrderSplitInfo.getOriginalGoodId(), difference);
        return true;
    }

    /**
     * 更新订单加急状态
     *
     * @param id
     * @param hurryFlag
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    @Transactional(readOnly = false)
    public boolean updateHurryFlag(String id, Integer hurryFlag) {
        return erpOrderSplitInfoDao.updateHurryFlag(id, hurryFlag) > 0;
    }

    /**
     * 通过查询条件获取分单信息
     *
     * @param shopName
     * @param orderNumber
     * @param hurryFlag
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    public List<ErpOrderSplitInfo> findListByParams(String shopName, String orderNumber, Integer hurryFlag,List<String> goodTypes) {
        ErpOrderSplitInfo erpOrderSplitInfo = new ErpOrderSplitInfo();
        erpOrderSplitInfo.setShopName(shopName);
        erpOrderSplitInfo.setOrderNumber(orderNumber);
        erpOrderSplitInfo.setHurryFlag(hurryFlag);
        erpOrderSplitInfo.setGoodTypes(goodTypes);
        return erpOrderSplitInfoDao.findListByParams(erpOrderSplitInfo);
    }

    /**
     * 通过查询条件获取分单信息
     *
     * @param shopName
     * @param orderNumber
     * @param hurryFlag
     * @return
     * @date 2017年10月31日
     * @author yunnex
     */
    public List<String> findFollowOrderByParams(String shopName, String orderNumber, Integer hurryFlag,List<String> goodTypes) {
        ErpOrderSplitInfo erpOrderSplitInfo = new ErpOrderSplitInfo();
        erpOrderSplitInfo.setShopName(shopName);
        erpOrderSplitInfo.setOrderNumber(orderNumber);
        erpOrderSplitInfo.setHurryFlag(hurryFlag);
        erpOrderSplitInfo.setUserId(UserUtils.getUser().getId());
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        return erpOrderSplitInfoDao.findFollowOrderByParams(erpOrderSplitInfo);
    }
    
    public ErpOrderSplitInfo getByProsIncId(String procInsId) {
        return erpOrderSplitInfoDao.getByProcInstId(procInsId);
    }
    
	public List<ErpOrderSplitInfo> findListByOrderInfoAndUser(String orderId, Integer goodType){
		return erpOrderSplitInfoDao.findListByOrderInfoAndUser(orderId, goodType);
	}
	
	/**
     * 初始化 推广参数，根据promotionChannelList 如果存在设置成1，不存在设置成0 
     * @date 2017年11月20日
     * @author czj
     */
    public void initExtendParams(  Map<String, Object> vars,List<JykOrderPromotionChannel> promotionChannelList) 
    {
        vars.put("isfriends", 0);
        vars.put("isweibo", 0);
        vars.put("ismomo", 0);
        for (JykOrderPromotionChannel promotionChannel : promotionChannelList) 
        {
            if ("1".equals(promotionChannel.getPromotionChannel()))
            {
                vars.put("isfriends", 1);
            }
            else if ("2".equals(promotionChannel.getPromotionChannel()))
            {
                vars.put("isweibo", 1);
            }
            else if ("3".equals(promotionChannel.getPromotionChannel())) 
            {
                vars.put("ismomo", 1);
            }
        }
    }
    
    @Transactional(readOnly = false)
    public void updateState(String pendingProdFlag, String taskState, String id) {
        erpOrderSplitInfoDao.updateState(pendingProdFlag, taskState, id);
    }
    
    public List<ErpOrderSplitInfo> findBeginOrder(String zhangbeiId){
    	return erpOrderSplitInfoDao.findBeginOrder(zhangbeiId);
    }
    
    public List<ErpOrderSplitInfo> findOverOrder(String zhangbeiId){
    	return erpOrderSplitInfoDao.findOverOrder(zhangbeiId);
    }
    
    public List<ErpOrderSplitInfo> findNoBeginOrder(String zhangbeiId){
    	return erpOrderSplitInfoDao.findNoBeginOrder(zhangbeiId);
    }
   
    public Map<String, String> getDiagnosisTaskInfo(String splitId) {
        return erpOrderSplitInfoDao.getDiagnosisTaskInfo(splitId);
    }

    @Transactional(readOnly = false)
    public boolean updatePendingStatus(String id, Date activationTime, String pendingProdFlag) {
        return dao.updatePendingStatus(id, activationTime, pendingProdFlag);
    }

    @Transactional(readOnly = false)
    public boolean updateRiskStatus(String timeoutFlag, Set<String> jykOrderIds) {
        return dao.updateRiskStatus(timeoutFlag, jykOrderIds);
    }
    
    /**
     * 获取进行时分单列表view
     *
     * @param zhangbeiId 掌贝id
     * @return
     * @date 2018年4月3日
     * @author linqunzhi
     */
    public List<OrderSplitInfoView> findBeginListView(String zhangbeiId) {
        logger.info("findBeginListView start | zhangbeiId={}", zhangbeiId);
        if (StringUtils.isBlank(zhangbeiId)) {
            logger.info("zhangbeiId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // 获取进行时的分单列表
        List<ErpOrderSplitInfo> splitList = dao.findListByZhangbeiIdStatus(zhangbeiId, SplitConstants.STATUS_BEGIN);
        // 获取图片地址
        Map<String, String> photoMap = getGoodPhotoMap();
        // 获取分单view列表
        List<OrderSplitInfoView> splitViewList = getSplitInfoViewList(splitList, photoMap.get(GOOD_TYPE_NAME_JU));
        // 获取客常来商品总条数
        int keCount = erpOrderOriginalGoodService.countByZhangbeiId(zhangbeiId, OrderGoodConstants.GOOD_TYPE_KE_ID);
        // 获取客常来view
        OrderSplitInfoView keView = null;
        if (keCount > 0) {
            //计算所有订单中包含客常来类型，其他类型(服务名称必须是 客常来门店费)的 客常来门店费, 服务数量总数(订单中商品有goodTypeIds中任意一个，计算商品中goodIds的总num值)
            SysConstants sysConstants = sysConstantsService.getByKey(OrderGoodConstants.GOOD_KE_CHANG_LAI_MEN_DIAN_KEY);
            int keNum = 0;
            if (sysConstants != null) {
                String dictValue = sysConstants.getValue();
                logger.info("计算门店总数的配置：dictValue={}", dictValue);
                if (StringUtils.isNotBlank(dictValue)) {
                    String goodTypeIdsStr = null;
                    String goodIdsStr = null;
                    try {
                        JSONObject jsonObj = (JSONObject) JSONObject.parse(dictValue);
                        goodTypeIdsStr = jsonObj.getString("goodTypeIds");
                        goodIdsStr = jsonObj.getString("goodIds");
                    } catch (Exception e) {
                        logger.info("转换json出错！dictValue={}", dictValue);
                    }
                    if (StringUtils.isBlank(goodTypeIdsStr) || StringUtils.isBlank(goodIdsStr)) {
                        logger.info("有值为空不需要计算门店总数");
                    } else {
                        String[] goodTypeIds = goodTypeIdsStr.split(CommonConstants.Sign.COMMA);
                        String[] goodIds = goodIdsStr.split(CommonConstants.Sign.COMMA);
                        keNum = dao.countKeBeginByZhangbeiId(zhangbeiId, goodTypeIds, goodIds);
                    }
                }

            } else {
                logger.info("无计算门店总数的配置");
            }
            // 获取客常来view
            keView = new OrderSplitInfoView();
            // 如果数量大于0，则显示门店*keNum
            if (keNum > 0) {
                keView.setGoodRemark("门店*" + keNum);
            }
            keView.setGoodTypeName(GOOD_TYPE_NAME_KE);
            keView.setGoodPhotoUrl(photoMap.get(GOOD_TYPE_NAME_KE));
        }
        List<OrderSplitInfoView> result = new ArrayList<>();
        if (splitViewList != null) {
            result.addAll(splitViewList);
        }
        if (keView != null) {
            result.add(keView);
        }
        logger.info("findBeginListView end | result.size={}", result.size());
        return result;
    }
    
    /**
     * 获取已完成分单列表view
     *
     * @param zhangbeiId
     * @return
     * @date 2018年4月3日
     * @author linqunzhi
     */
    public List<OrderSplitInfoView> findFinishListView(String zhangbeiId) {
        logger.info("findFinishListView start | zhangbeiId={}", zhangbeiId);
        if (StringUtils.isBlank(zhangbeiId)) {
            logger.info("zhangbeiId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // 获取已完成的分单列表
        List<ErpOrderSplitInfo> splitList = dao.findListByZhangbeiIdStatus(zhangbeiId, SplitConstants.STATUS_FINISH);
        //获取图片地址
        Map<String,String> photoMap = getGoodPhotoMap();
        List<OrderSplitInfoView> result = getSplitInfoViewList(splitList,photoMap.get(GOOD_TYPE_NAME_JU));
        logger.info("findFinishListView end | result.size={}", result == null ? 0 : result.size());
        return result;
    }
    
    /**
     * 获未启动服务列表view
     *
     * @param zhangbeiId
     * @return
     * @date 2018年4月9日
     * @author linqunzhi
     */
    public  List<OrderSplitInfoView> findNoBeginListView(String zhangbeiId) {
        logger.info("findNoBeginListView start |  zhangbeiId={}",zhangbeiId);
        //获取聚引客订单商品中待处理数>0的订单
        List<ErpOrderOriginalInfo> list = erpOrderOriginalInfoService.findNoBeginListByZhangbeiId(zhangbeiId,OrderGoodConstants.GOOD_TYPE_JU_ID);
        //获取图片地址
        Map<String,String> photoMap = getGoodPhotoMap();
        //获取分单view列表
        List<OrderSplitInfoView> result = getSplitInfoViewListByOrderList(list,photoMap.get(GOOD_TYPE_NAME_JU));
        logger.info("findNoBeginListView end |  result.size={}",result == null ? 0:result.size());
        return result;
    }

    /**
     * 根据订单列表获取分单视图列表
     *
     * @param list
     * @return
     * @date 2018年4月9日
     * @author linqunzhi
     * @param string 
     */
    private List<OrderSplitInfoView> getSplitInfoViewListByOrderList(List<ErpOrderOriginalInfo> list, String goodPhotoUrl) {
        List<OrderSplitInfoView> result = null;
        if (list != null) {
            // 分割字符串
            String splitStr = "; ";
            result = new ArrayList<>();
            OrderSplitInfoView view = null;
            //同类型商品汇总map
            Map<String,Integer> goodMap = null;
            for (ErpOrderOriginalInfo info : list) {
                String orderId = info.getId();
                // 获取聚引客待处理>0的商品
                List<ErpOrderOriginalGood> goodList = erpOrderOriginalGoodService.findNoBeginListByOrderId(orderId,OrderGoodConstants.GOOD_TYPE_JU_ID);
                StringBuilder strBuilder = new StringBuilder();
                if (goodList != null) {
                    view = new OrderSplitInfoView();
                    goodMap = new HashMap<>();
                    for (ErpOrderOriginalGood good : goodList) {
                        // 商品名称
                        String goodName = good.getGoodName();
                        Integer sum = goodMap.get(goodName);
                        // 待处理商品数量
                        int pendingNum = good.getPendingNum();
                        if (sum == null) {
                            goodMap.put(goodName, pendingNum);
                        }else {
                            sum += pendingNum;
                            goodMap.put(goodName, sum);
                        }
                    }
                    for(Map.Entry<String, Integer> entry : goodMap.entrySet()) {
                        strBuilder.append(splitStr).append(entry.getKey()).append(CommonConstants.Sign.ASTERISK).append(entry.getValue());
                    }
                    // 商品备注（商品类型*数量）
                    String goodRemark = strBuilder.toString();
                    if (goodRemark.length() > splitStr.length()) {
                        goodRemark = goodRemark.substring(splitStr.length());
                    }
                    view.setGoodTypeName(GOOD_TYPE_NAME_JU);
                    view.setGoodRemark(goodRemark);
                    view.setGoodPhotoUrl(goodPhotoUrl);
                    result.add(view);
                }

            }
        }
        return result;
    }
    
    /**
     * 根据分单信息列表，获取分单视图列表
     *
     * @param splitList
     * @return
     * @date 2018年4月3日
     * @author linqunzhi
     */
    private List<OrderSplitInfoView> getSplitInfoViewList(List<ErpOrderSplitInfo> splitList,String goodPhotoUrl) {
        List<OrderSplitInfoView> result = null;
        if (splitList != null) {
            // 分割字符串
            String splitStr = "; ";
            result = new ArrayList<>();
          //同类型商品汇总map
            Map<String,Integer> goodMap = null;
            for (int i = 0; i < splitList.size(); i++) {
                ErpOrderSplitInfo info = splitList.get(i);
                // 分单id
                String splitId = info.getId();
                List<ErpOrderSplitGood> goodList = erpOrderSplitGoodService.findListBySplitId(splitId);
                StringBuilder strBuilder = new StringBuilder();
                goodMap = new HashMap<>();
                if(goodList != null) {
                    for (ErpOrderSplitGood good : goodList) {
                        // 商品名称
                        String goodName = good.getGoodName();
                        Integer sum = goodMap.get(goodName);
                        // 商品数量
                        int num = good.getNum();
                        if (sum == null) {
                            goodMap.put(goodName, num);
                        }else {
                            sum += num;
                            goodMap.put(goodName, sum);
                        }
                    }
                    for(Map.Entry<String, Integer> entry : goodMap.entrySet()) {
                        strBuilder.append(splitStr).append(entry.getKey()).append(CommonConstants.Sign.ASTERISK).append(entry.getValue());
                    }
                    //商品备注（商品类型*数量）
                    String goodRemark = strBuilder.toString();
                    if(goodRemark.length() > splitStr.length()) {
                        goodRemark = goodRemark.substring(splitStr.length());
                    }
                    OrderSplitInfoView view = new OrderSplitInfoView();
                    view.setSplitId(splitId);
                    view.setGoodTypeName(GOOD_TYPE_NAME_JU);
                    view.setGoodRemark(goodRemark);
                    view.setGoodPhotoUrl(goodPhotoUrl);
                    result.add(view);
                }
               
            }
        }
        return result;
    }

    /**
     * 获取商品分类图片路径
     *
     * @return
     * @date 2018年4月13日
     * @author linqunzhi
     */
    private Map<String, String> getGoodPhotoMap() {
        // 客常来、聚引客 服务图片地址常量
        SysConstants dict = sysConstantsService.getByKey(OrderGoodConstants.GOOD_PHOTO_URLS_KEY);
        Map<String, String> result = new HashMap<>();
        if (dict != null) {
            String dictValue = dict.getValue();
            logger.info("客常来、聚引客 服务图片地址：dictValue={}", dictValue);
            if (StringUtils.isNotBlank(dictValue)) {
                String keChangLai = null;
                String juYinKe = null;
                try {
                    JSONObject jsonObj = (JSONObject) JSONObject.parse(dictValue);
                    keChangLai = jsonObj.getString("keChangLai");
                    juYinKe = jsonObj.getString("juYinKe");
                } catch (Exception e) {
                    logger.info("转换json出错！dictValue={}", dictValue,e);
                }
                if (StringUtils.isNotBlank(keChangLai)) {
                    result.put(GOOD_TYPE_NAME_KE, photoBaseUrl + keChangLai);
                }
                if (StringUtils.isNotBlank(juYinKe)) {
                    result.put(GOOD_TYPE_NAME_JU, photoBaseUrl + juYinKe);
                }
            }
        }
        return result;
    }

    /**
     * 获取分单首页开通模块list
     *
     * @param splitId
     * @return
     * @date 2018年4月9日
     * @author linqunzhi
     */
    public List<String> getIndexOpenList(String splitId) {
        logger.info("getIndexOpenList start | splitId={}", splitId);
        if (StringUtils.isBlank(splitId)) {
            logger.info("splitId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ErpOrderSplitInfo split = this.get(splitId);
        if (split == null) {
            logger.info("分单不存在！splitId={}", splitId);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        List<String> result = new ArrayList<>();
        //服务进度 无条件开放
        result.add(SplitConstants.SplitIndexConstants.MODULES_SERVICE);
        // 推广提案是否发布小程序
        Integer pushWx = split.getPublishToWxapp();
        if (pushWx != null && pushWx.equals(SplitConstants.PUSH_WX_Y)) {
            result.add(SplitConstants.SplitIndexConstants.MODULES_PROMOTION);
        }
        // 获取已发布小程序类型为首日的 推广数据报告
        JykDataPresentation jykDataPresentation = jykDataPresentationService.getOnlyOne(splitId, JykDataPresentationConstants.STATE_WX,
                        JykDataPresentationConstants.DATA_TYPE_FIRST);
        if (jykDataPresentation != null) {
            result.add(SplitConstants.SplitIndexConstants.MODULES_PRESENTATION);
            result.add(SplitConstants.SplitIndexConstants.MODULES_COMMENT);
        }
        // 根据分单id获取（已发布小程序 或 确认投放 状态的推广页面预览信息）
        JykDeliveryEffectInfo jykDelivery = jykDeliveryEffectInfoService.getBySplitId(splitId);
        if (jykDelivery != null) {
            result.add(SplitConstants.SplitIndexConstants.MODULES_DELIVIERY);
        }
        logger.info("getIndexOpenList end | result.size={}", result.size());
        return result;
    }

    /**
     * 正在跑的流程，根据zhangbeiid、流程角色类型 获取用户id
     *
     * @param zhangbeiId
     * @param roleTypeList
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    public List<String> findBeginByRole(String zhangbeiId, List<String> roleTypeList) {
        logger.info("findBeginByRole start");
        String roleTypeListStr = JSON.toJSONString(roleTypeList);
        logger.info("param : zhangbeiId={}|roleTypeList={}", zhangbeiId, roleTypeListStr);
        List<String> result = dao.findBeginByRole(zhangbeiId, roleTypeList);
        logger.info("findBeginByRole end | result.size()", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 根据掌贝id 和 状态获取 分单列表
     *
     * @param zhangbeiId
     * @param status
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    public List<ErpOrderSplitInfo> findListByZhangbeiIdStatus(String zhangbeiId, Integer status) {
        logger.info("findListByZhangbeiIdStatus start | zhangbeiId={}|status={}", zhangbeiId, status);
        List<ErpOrderSplitInfo> result = dao.findListByZhangbeiIdStatus(zhangbeiId, status);
        logger.info("findListByZhangbeiIdStatus end | result.size = {}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 查看效果报告
     *
     * @param id
     * @date 2018年7月23日
     * @author linqunzhi
     */
    @Transactional(readOnly = false)
    public void lookEffect(String id) {
        logger.info("lookEffect start | id={}", id);
        if (StringUtils.isBlank(id)) {
            logger.info("id 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ErpOrderSplitInfo info = new ErpOrderSplitInfo(id);
        info.setLookEffectFlag(CommonConstants.Sign.YES);
        erpOrderSplitInfoDao.updateInfo(info);
        logger.info("lookEffect end");
    }
}
