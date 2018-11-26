package com.yunnex.ops.erp.modules.shop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.constants.CommonConstants.FailMsg;
import com.yunnex.ops.erp.common.constants.LoginConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.result.BaseResult;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.DateUtils;
import com.yunnex.ops.erp.common.utils.HttpUtil;
import com.yunnex.ops.erp.common.utils.JedisUtils;
import com.yunnex.ops.erp.common.utils.MD5Util;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageResponseDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceScheduleResponseDto;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceMessage;
import com.yunnex.ops.erp.modules.message.extraModel.ServiceProgressExtra;
import com.yunnex.ops.erp.modules.message.service.ErpServiceMessageService;
import com.yunnex.ops.erp.modules.message.service.ErpServiceProgressService;
import com.yunnex.ops.erp.modules.message.service.ServiceLinkVariableService;
import com.yunnex.ops.erp.modules.order.constans.OrderConstants;
import com.yunnex.ops.erp.modules.order.constans.OrderGoodConstants;
import com.yunnex.ops.erp.modules.order.constans.SplitConstants;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderGoodServiceInfo;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderOriginalInfo;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitGood;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.service.ErpOrderGoodServiceInfoService;
import com.yunnex.ops.erp.modules.order.service.ErpOrderOriginalGoodService;
import com.yunnex.ops.erp.modules.order.service.ErpOrderOriginalInfoService;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitGoodService;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.order.view.OrderSplitInfoView;
import com.yunnex.ops.erp.modules.shop.dao.ErpShopInfoDao;
import com.yunnex.ops.erp.modules.shop.dto.ShopAdviserResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopBusinessInfoResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopServiceAllResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopServiceMessageResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopServiceResponseDto;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.shop.view.ShopIndexView;
import com.yunnex.ops.erp.modules.store.basic.service.ErpStorePromotePhotoMaterialService;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;
import com.yunnex.ops.erp.modules.sys.entity.JobNumberInfo;
import com.yunnex.ops.erp.modules.sys.entity.SysConstants;
import com.yunnex.ops.erp.modules.sys.entity.User;
import com.yunnex.ops.erp.modules.sys.service.JobNumberInfoService;
import com.yunnex.ops.erp.modules.sys.service.SysConstantsService;
import com.yunnex.ops.erp.modules.sys.service.UserService;
import com.yunnex.ops.erp.modules.workflow.delivery.entity.ErpDeliveryService;
import com.yunnex.ops.erp.modules.workflow.delivery.service.ErpDeliveryServiceService;
import com.yunnex.ops.erp.modules.workflow.enums.FlowServiceType;
import com.yunnex.ops.erp.modules.workflow.flow.common.WorkFlowConstants;
import com.yunnex.ops.erp.modules.workflow.user.constant.OrderFlowUserConstants;

/**
 * 商户管理Service
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class ErpShopInfoService extends CrudService<ErpShopInfoDao, ErpShopInfo> {

    @Value("${api_shop_login_url}")
    private String oemShopLoginUrl;
    private static final String OEM_LOGIN_ERROR_CODE = "10000";
    private static final String ERP_LOGIN_ERROR_CODE = "20000";

    /** 首页通知最大显示条数 */
    private static int MAX_MESSAGE_SIZE = 5;

    @Autowired
    private ErpShopInfoDao erpShopInfoDao;
    @Autowired
    private ShopUtils shopUtils;
    @Autowired
    private ErpStorePromotePhotoMaterialService erpStorePromotePhotoMaterialService;
    @Autowired
    private ErpOrderSplitInfoService erpOrderSplitInfoService;
    @Autowired
    private ErpOrderOriginalInfoService erpOrderOriginalInfoService;
    @Autowired
    private ErpOrderOriginalGoodService erpOrderOriginalGoodService;
    @Autowired
    private JobNumberInfoService jobNumberInfoService;
    @Autowired
    private ErpDeliveryServiceService deliveryServiceService;
    @Autowired
    @Lazy
    private ErpServiceProgressService serviceScheduleService;
    @Autowired
    private SysConstantsService sysConstantsService;
    @Autowired
    private ErpOrderSplitGoodService erpOrderSplitGoodService;
    @Autowired
    private ErpServiceMessageService serviceMessageService;
    @Autowired
    private ErpOrderGoodServiceInfoService goodServiceInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServiceLinkVariableService serviceLinkVariableService;

    private static final Map<String, Integer> ADVISER_TEAM_MAP = getAdviserTeamMap();

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public ErpShopInfo get(String id) {
        return super.get(id);
    }


    @Override
    public List<ErpShopInfo> findList(ErpShopInfo erpShopInfo) {
        return super.findList(erpShopInfo);
    }

    public ErpShopInfo getByZhangbeiId(String zhangbeiId) {


        ErpShopInfo shopInfo = erpShopInfoDao.getByZhangbeiId(zhangbeiId);
        return shopInfo;
    }


    @Override
    public Page<ErpShopInfo> findPage(Page<ErpShopInfo> page, ErpShopInfo erpShopInfo) {
        return super.findPage(page, erpShopInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpShopInfo erpShopInfo) {
        super.save(erpShopInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpShopInfo erpShopInfo) {
        super.delete(erpShopInfo);
    }

    public int countShopByZhangbeiId(String zhangbeiId) {
        Integer count = erpShopInfoDao.countShopByZhangbeiId(zhangbeiId);
        return count == null ? 0 : count.intValue();
    }

    @Transactional(readOnly = false)
    public boolean updateByZhangbeiId(ErpShopInfo erpShopInfo) {
        return erpShopInfoDao.updateByZhangbeiId(erpShopInfo) > 0;
    }

    @Transactional(readOnly = false)
    public boolean updateIntoPiecesById(String id) {
        return erpShopInfoDao.updateIntoPiecesById(id) > 0;
    }

    @Transactional(readOnly = false)
    public boolean insert(ErpShopInfo erpShopInfo) {
        return erpShopInfoDao.insert(erpShopInfo) > 0;
    }

    public Page<ErpShopInfo> searchList(Page<ErpShopInfo> page, ErpShopInfo erpShopInfo) {

        erpShopInfo.setPage(page);
        page.setList(erpShopInfoDao.findAllList(erpShopInfo));
        return page;
    }

    public List<ErpShopInfo> findshopwaiter(String shopid) {
        return erpShopInfoDao.findshopwaiter(shopid);

    }

    /**
     * 商户登录。在OEM进行验证。使用掌贝ID或者登录名作为账号。
     */
    public BaseResult login(String accountNo, String zhangbeiPassword, String uuid, Boolean rememberMe, HttpServletResponse response) {
        logger.info("小程序商户登录入参：accountNo={}, zhangbeiPassword={},uuid={}, rememberMe={}", accountNo, zhangbeiPassword, uuid, rememberMe);
        BaseResult baseResult = new BaseResult();
        if (StringUtils.isBlank(accountNo)) {
            baseResult.error(BaseResult.CODE_ERROR_ARG, "登录账号不能为空！");
            return baseResult;
        }
        if (StringUtils.isBlank(zhangbeiPassword)) {
            baseResult.error(BaseResult.CODE_ERROR_ARG, "密码不能为空！");
            return baseResult;
        }

        try {
            // 验证通过后，获取该商户在ERP特有的信息
            ErpShopInfo erpShopInfo = null;
            // 先通过掌贝ID获取
            erpShopInfo = dao.getByZhangbeiId(accountNo);

            // 如果没有再通过登录名获取
            if (erpShopInfo == null) {
                erpShopInfo = dao.getByLoginName(accountNo);
            }

            if (erpShopInfo == null) {
                return baseResult.error(BaseResult.CODE_ERROR_ARG, "用户名或密码不正确！");
            }

            // 审核通过才到OEM验证，否则在这边验证
            if (2 == erpShopInfo.getZhangbeiState()) {
                // 统一到OEM进行商户验证
                baseResult = oemShopLogin(accountNo, zhangbeiPassword);
                if (!BaseResult.isSuccess(baseResult)) {
                    return baseResult;
                }
            } else {
                // 密码不正确
                String pwdEncrypt = MD5Util.MD5(zhangbeiPassword);
                if (!erpShopInfo.getZhangbeiPassword().equalsIgnoreCase(pwdEncrypt)) {
                    return baseResult.error(BaseResult.CODE_ERROR_ARG, "用户名或密码不正确！");
                }
            }

            // 保存登录信息到redis中
            String shopToken = (erpShopInfo.getId() + ":" + uuid).toUpperCase();
            String result = JedisUtils.set(LoginConstants.SESSION_PREFIX + shopToken, mapper.writeValueAsString(erpShopInfo),
                            LoginConstants.SESSION_TIMEOUT);
            if (result == null) {
                throw new ServiceException("登录失败！redis异常！");
            }

            // 保存登录凭证到cookie中, token: ID+时间截(cookie设置的时间）
            shopToken += "-" + System.currentTimeMillis();
            response.setHeader(LoginConstants.SHOP_TOKEN, shopToken); // 小程序不支持Cookie
            if (rememberMe) {
                // 记住我,保存用户名和密码到cookie中,有效期两周
                String rememberMeStr = Base64Utils.encodeToString((accountNo + ":" + zhangbeiPassword + ":" + uuid).getBytes());
                response.setHeader(LoginConstants.REMEMBER_ME, rememberMeStr);
            } else {
                response.setHeader(LoginConstants.REMEMBER_ME, "");
            }

            // 登录成功，将商户信息返回
            baseResult.setAttach(erpShopInfo);
            logger.info("登录成功！登录账号：{}，名称：{}", accountNo, erpShopInfo.getName());
        } catch (ServiceException | JsonProcessingException e) {
            logger.info("登录失败！登录账号：{}", accountNo, e);
            baseResult.error(ERP_LOGIN_ERROR_CODE, "小程序系统异常！");
            return baseResult;
        }
        return baseResult;
    }

    /**
     * 调用OEM接口验证商户，验证通过即可
     */
    private BaseResult oemShopLogin(String username, String password) {
        BaseResult baseResult = new BaseResult();
        try {
            JSONObject params = new JSONObject();
            params.put("userName", username);
            params.put("passWord", password);

            String result = HttpUtil.sendHttpPostReqToServerByReqbody(oemShopLoginUrl, params.toJSONString(), "application/json");

            if (result == null) {
                baseResult.error(OEM_LOGIN_ERROR_CODE, "OEM登录出错！");
                return baseResult;
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!"0".equals(jsonObject.getString("code"))) {
                baseResult.error(OEM_LOGIN_ERROR_CODE, jsonObject.getString("message"));
                return baseResult;
            }
        } catch (ServiceException e) {
            baseResult.error(OEM_LOGIN_ERROR_CODE, "调用OEM商户验证接口异常！");
            return baseResult;
        }
        return baseResult;
    }

    public Map<String, String> logout(HttpServletResponse response) {
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        Map<String, String> result = Maps.newHashMap();
        if (loginShop == null) {
            result.put("code", "0"); // 已退出
            result.put("msg", "商户已退出或会话已失效！");
            logger.info(result.get("msg"));
            return result;
        }
        String sessionKey = shopUtils.getSessionKey();
        long del = JedisUtils.del(sessionKey);
        if (del == 1) {
            response.setHeader(LoginConstants.SHOP_TOKEN, "");
            response.setHeader(LoginConstants.REMEMBER_ME, "");
            result.put("code", "0"); // 成功
            result.put("msg", LoginConstants.LOGOUT_SUCCESS);
            logger.info("{}商户ID：{}，名称：{}", result.get("msg"), loginShop.getZhangbeiId(), loginShop.getName());
        } else {
            result.put("code", "-1"); // 失败，其他原因
            result.put("msg", LoginConstants.LOGOUT_FAIL + "其他原因！");
            logger.info("{}商户ID：{}，名称：{}", result.get("msg"), loginShop.getZhangbeiId(), loginShop.getName());
        }
        return result;
    }

    /**
     * 获取店铺首页信息
     *
     * @param zhangbeiId 掌贝id
     * @return
     * @date 2018年4月2日
     * @author linqunzhi
     */
    public ShopIndexView getIndexInfo(String zhangbeiId) {
        logger.info("getIndexInfo start | zhangbeiId={}", zhangbeiId);
        if (StringUtils.isBlank(zhangbeiId)) {
            logger.info("zhangbeiId 不能为空");
            throw new ServiceException(FailMsg.PARAM);
        }
        ErpShopInfo shop = erpShopInfoDao.getByZhangbeiId(zhangbeiId);
        if (shop == null) {
            logger.info("无商户信息，zhangbeiId={}", zhangbeiId);
            throw new ServiceException(FailMsg.PARAM);
        }
        String shopId = shop.getId();
        // 获取店铺主题门店的推广图片素材
        String shopPhotoUrl = erpStorePromotePhotoMaterialService.getStoreMainPhotoByShopId(shopId);
        // 是否展示门店管理
        boolean showStoreManager = true;
        // 获取商户第一条订单信息
        ErpOrderOriginalInfo orderInfo = erpOrderOriginalInfoService.getFirstByZhangbeiId(zhangbeiId, OrderConstants.CANCEL_NO);
        if (orderInfo != null) {
            Integer orderType = orderInfo.getOrderType();
            if (orderType == null) {
                logger.error("orderType 为空 | id={}", orderInfo.getId());
                throw new ServiceException(CommonConstants.FailMsg.DATA);

            }
            // 如果是服务商类型，则隐藏管理门店
            if (OrderConstants.ORDER_TYPE_SERVICE == orderType.intValue()) {
                showStoreManager = false;
            }
        }
        // 进行时时服务
        List<OrderSplitInfoView> beginList = erpOrderSplitInfoService.findBeginListView(zhangbeiId);
        // 未启动时服务
        List<OrderSplitInfoView> noBeginList = erpOrderSplitInfoService.findNoBeginListView(zhangbeiId);
        // 已完成服务
        List<OrderSplitInfoView> finishList = erpOrderSplitInfoService.findFinishListView(zhangbeiId);
        // 数据赋值
        ShopIndexView view = new ShopIndexView();
        view.setShopPhotoUrl(shopPhotoUrl);
        view.setBeginList(beginList);
        view.setNoBeginList(noBeginList);
        view.setFinishList(finishList);
        view.setShopId(shopId);
        view.setShopName(shop.getName());
        view.setStoreCount(shop.getStoreCount());
        view.setShowStoreManager(showStoreManager);
        logger.info("getIndexInfo end");
        return view;
    }

    /**
     * 获取商户业务信息
     *
     * @param zhangbeiId
     * @return
     * @date 2018年5月29日
     * @author linqunzhi
     */
    public ShopBusinessInfoResponseDto getBusinessInfo(String zhangbeiId) {
        logger.info("getBusinessInfo start | zhangbeiId={}", zhangbeiId);
        if (StringUtils.isBlank(zhangbeiId)) {
            logger.info("zhangbeiId 不能为空");
            throw new ServiceException(FailMsg.PARAM);
        }
        ErpShopInfo shop = erpShopInfoDao.getByZhangbeiId(zhangbeiId);
        if (shop == null) {
            logger.info("无商户信息，zhangbeiId={}", zhangbeiId);
            throw new ServiceException(FailMsg.PARAM);
        }
        // 购买聚引客的商品总条数
        int count = erpOrderOriginalGoodService.countByZhangbeiId(zhangbeiId, OrderGoodConstants.GOOD_TYPE_JU_ID);
        // 是否购买过聚引客商品
        String buyJuYinKeFlag = count > 0 ? CommonConstants.Sign.YES : CommonConstants.Sign.NO;
        ShopBusinessInfoResponseDto result = new ShopBusinessInfoResponseDto();
        result.setShopName(shop.getName());
        result.setBuyJuYinKeFlag(buyJuYinKeFlag);
        logger.info("getBusinessInfo end");
        return result;
    }

    /**
     * 根据掌贝id获取 顾问团队列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月3日
     * @author linqunzhi
     */
    public List<ShopAdviserResponseDto> getAdviserTeam(String zhangbeiId) {
        logger.info("getAdviserTeam start | zhangbeiId={}", zhangbeiId);
        if (StringUtils.isBlank(zhangbeiId)) {
            logger.error("zhangbeiId 不能为空！");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ErpShopInfo shop = this.getByZhangbeiId(zhangbeiId);
        if (shop == null) {
            logger.info("商户不存在！zhangbeiId={}", zhangbeiId);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // ### 运营顾问 处理 ####
        String operationAdviserId = shop.getOperationAdviserId();
        List<ShopAdviserResponseDto> result = new ArrayList<>();
        if (StringUtils.isNotBlank(operationAdviserId)) {
            ShopAdviserResponseDto operationAdviserDto = getShopOperationAdviserDto(operationAdviserId);
            result.add(operationAdviserDto);
        }
        // ### 开户顾问 处理 ###
        List<ShopAdviserResponseDto> accountAdviserDtoList = getShopAccountAdviserDtoList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(accountAdviserDtoList)) {
            result.addAll(accountAdviserDtoList);
        }
        // ### 策划专家 处理 ###
        List<ShopAdviserResponseDto> planningExpertDtoList = getShopPlanningExpertDtoList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(planningExpertDtoList)) {
            result.addAll(planningExpertDtoList);
        }

        // ## 获取正在进行时的服务 以及 计算 默认页###
        int defaultIndex = -1;// 默认展示页下标
        Date maxStartTime = null;// 最大启动时间
        String lastRoleType = null;// 上一个
        for (int i = 0; i < result.size(); i++) {
            ShopAdviserResponseDto dto = result.get(i);
            dto.setDefaultFlag(CommonConstants.Sign.NO);
            // 角色类型
            String roleType = dto.getRoleType();
            ServiceScheduleResponseDto beginService = getServiceScheduleResponseDto(zhangbeiId, dto.getUserId(), roleType);
            dto.setBeginService(beginService);
            // 启动时间
            Date startTime = beginService == null ? null : beginService.getStartTime();
            // 是否需要修改默认展示页下标
            boolean needUpdate = needUpdateIndex(maxStartTime, startTime, lastRoleType, roleType);
            if (needUpdate) {
                defaultIndex = i;
                maxStartTime = startTime;
                lastRoleType = roleType;
            }
        }
        // 设置默认展示页
        if (defaultIndex >= 0) {
            result.get(defaultIndex).setDefaultFlag(CommonConstants.Sign.YES);
        }
        logger.info("getAdviserTeam end | result.size={}", result.size());
        return result;
    }

    /**
     * 是否需要修改默认展示也下标<BR/>
     * 1、角色有进行中的服务（即“当前服务”），且“当前服务”启动时间最晚的（最近启动的），则为前端需默认显示的信息；<BR/>
     * 2、若3种都没有“当前服务”或"当前服务"的启动时间一样，则按“运营顾问”>“策划专家">"开户顾问"的优先级显示首页；<BR/>
     *
     * @param maxStartTime
     * @param startTime
     * @param roleType
     * @return
     * @date 2018年7月6日
     * @author linqunzhi
     * @param roleType
     */
    private boolean needUpdateIndex(Date maxStartTime, Date startTime, String lastRoleType, String roleType) {
        long maxStart = maxStartTime == null ? 0L : maxStartTime.getTime();
        long start = startTime == null ? 0L : startTime.getTime();
        // 如果 启动时间大于 之前的最大启动时间 则返回 true
        if (start > maxStart) {
            return true;
        } else if (start == maxStart) {
            int lastRole = getRoleTypeIndex(lastRoleType);
            int role = getRoleTypeIndex(roleType);
            // 如果角色类型 大于 之前的角色类型
            if (role > lastRole) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取服务进度
     *
     * @param zhangbeiId
     * @param userId
     * @param roleType
     * @return
     * @date 2018年7月6日
     * @author linqunzhi
     */
    private ServiceScheduleResponseDto getServiceScheduleResponseDto(String zhangbeiId, String userId, String roleType) {
        ServiceProgressExtra scheduleExtra = serviceScheduleService.getBeginMaxStartTime(zhangbeiId, userId, roleType);
        ServiceScheduleResponseDto beginService = getServiceScheduleResponseDto(scheduleExtra);
        return beginService;
    }

    /**
     * 根据服务进度扩展 获取 服务进度responseDto
     *
     * @param scheduleExtra
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private static ServiceScheduleResponseDto getServiceScheduleResponseDto(ServiceProgressExtra scheduleExtra) {
        if (scheduleExtra == null) {
            return null;
        }
        ServiceScheduleResponseDto beginService = new ServiceScheduleResponseDto();
        beginService.setName(scheduleExtra.getName());
        beginService.setType(scheduleExtra.getType());
        beginService.setProcInsId(scheduleExtra.getProcInsId());
        beginService.setServiceType(scheduleExtra.getServiceType());
        beginService.setStartTime(scheduleExtra.getStartTime());
        return beginService;
    }


    /**
     * 获取策划专家集合
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    private List<ShopAdviserResponseDto> getShopPlanningExpertDtoList(String zhangbeiId) {
        List<String> roleTypeList = new ArrayList<>();
        roleTypeList.add(OrderFlowUserConstants.FLOW_USER_PLANNING_EXPERT);
        List<String> planningExpertIdList = erpOrderSplitInfoService.findBeginByRole(zhangbeiId, roleTypeList);
        if (CollectionUtils.isEmpty(planningExpertIdList)) {
            return null;
        }
        List<ShopAdviserResponseDto> result = new ArrayList<>();
        for (String id : planningExpertIdList) {
            ShopAdviserResponseDto dto = getShopAdviserResponseDto(id);
            dto.setRoleName("策划专家");
            dto.setRoleType(OrderFlowUserConstants.FLOW_USER_PLANNING_EXPERT);
            dto.setCommentInfo("策划能力评分");
            dto.setDuty("为您提供经营诊断、方案策划、推广上线推进等服务");
            result.add(dto);
        }
        return result;
    }

    /**
     * 获取开户顾问集合
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    private List<ShopAdviserResponseDto> getShopAccountAdviserDtoList(String zhangbeiId) {
        List<String> typeList = getServiceTypeList();
        List<String> roleTypeList = new ArrayList<>();
        roleTypeList.add(OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER);
        // 获取去重复的开户顾问id集合
        List<String> accountAdviserIds = deliveryServiceService.findBeginByTypeAndRole(zhangbeiId, typeList, roleTypeList);
        if (CollectionUtils.isEmpty(accountAdviserIds)) {
            return null;
        }
        List<ShopAdviserResponseDto> result = new ArrayList<>();
        for (String id : accountAdviserIds) {
            ShopAdviserResponseDto dto = getShopAdviserResponseDto(id);
            dto.setRoleName("开户顾问");
            dto.setRoleType(OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER);
            dto.setCommentInfo("开户能力评分");
            dto.setDuty("为您提供掌贝后台开通、微信支付宝以及银联开通等服务");
            
            result.add(dto);
        }
        return result;
    }

    private ShopAdviserResponseDto getShopOperationAdviserDto(String operationAdviserId) {
        ShopAdviserResponseDto dto = getShopAdviserResponseDto(operationAdviserId);
        dto.setRoleName("运营顾问");
        dto.setRoleType(OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER);
        dto.setCommentInfo("运营能力评分");
        dto.setDuty("您的专属掌贝运营顾问，很高兴为您服务");
        return dto;
    }

    /**
     * 根据用户id获取员工信息
     *
     * @param userId
     * @return
     * @date 2018年7月5日
     * @author linqunzhi
     */
    private ShopAdviserResponseDto getShopAdviserResponseDto(String userId) {
        ShopAdviserResponseDto operationAdviser = new ShopAdviserResponseDto();
        User user = userService.get(userId);
        if (user == null) {
            logger.info("用户信息不存在！userId={}", userId);
            return operationAdviser;
        }
        
        JobNumberInfo jobInfo = jobNumberInfoService.getByUserId(userId);
        if (jobInfo == null) {
            logger.info("员工不存在！userId={}", userId);
            if("agent".equals(user.getType())) {
            	 operationAdviser.setTelephone(user.getMobile());
            }
            return operationAdviser;
        }
        
        //非服务商用户，“工号管理”中的手机号作为小程序前端“我的专属顾问团队”对应人员的手机号
        if("erp".equals(user.getType())) {
        	 operationAdviser.setTelephone(jobInfo.getTelephone());
        }else {
        	operationAdviser.setTelephone(user.getMobile());
        }
        operationAdviser.setIconImg(jobInfo.getIconImg());
        operationAdviser.setJobNumber(jobInfo.getJobNumber());
        operationAdviser.setScore(jobInfo.getScore());
        operationAdviser.setUserId(userId);
        return operationAdviser;
    }

    /**
     * 获取 顾问团队大小map
     *
     * @return
     * @date 2018年7月6日
     * @author linqunzhi
     */
    private static Map<String, Integer> getAdviserTeamMap() {
        Map<String, Integer> map = new HashMap<>();
        int index = 0;
        map.put(OrderFlowUserConstants.FLOW_USER_ACCOUNT_ADVISER, index);
        index = index + 1;
        map.put(OrderFlowUserConstants.FLOW_USER_PLANNING_EXPERT, index);
        index = index + 1;
        map.put(OrderFlowUserConstants.FLOW_USER_OPERATION_ADVISER, index);
        return map;
    }

    /**
     * 获取角色 大小值
     *
     * @param roleType
     * @return
     * @date 2018年7月6日
     * @author linqunzhi
     */
    private static int getRoleTypeIndex(String roleType) {
        if (StringUtils.isBlank(roleType)) {
            return -1;
        }
        Integer index = ADVISER_TEAM_MAP.get(roleType);
        if (index == null) {
            return -1;
        }
        return index;
    }


    /**
     * 获取商户服务列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    public ShopServiceAllResponseDto getServiceAllList(String zhangbeiId) {
        logger.info("getServiceAllList start | zhangbeiId={}", zhangbeiId);
        // 未开始服务列表
        List<ShopServiceResponseDto> noBeginList = getNoBeginShopService(zhangbeiId);
        // 进行中服务列表
        List<ShopServiceResponseDto> beginList = getBeginShopService(zhangbeiId);
        // 已完成服务列表
        List<ShopServiceResponseDto> finishList = getFinishShopService(zhangbeiId);
        ShopServiceAllResponseDto result = new ShopServiceAllResponseDto();
        result.setNoBeginList(noBeginList);
        result.setBeginList(beginList);
        result.setFinishList(finishList);
        logger.info("getServiceAllList end ");
        return result;
    }

    /**
     * 获取已完成的服务列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private List<ShopServiceResponseDto> getFinishShopService(String zhangbeiId) {
        List<ShopServiceResponseDto> result = new ArrayList<>();
        // 获取引流服务已完成列表
        List<ShopServiceResponseDto> splitList = getFinishSplitList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(splitList)) {
            result.addAll(splitList);
        }
        // 获取 交付流程中 服务已完成列表
        List<ShopServiceResponseDto> deliveryList = getFinishDeliveryList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            result.addAll(deliveryList);
        }
        return result;
    }

    private List<ShopServiceResponseDto> getFinishDeliveryList(String zhangbeiId) {
        List<String> serviceTypeList = getServiceTypeList();
        List<ErpDeliveryService> list = new ArrayList<>();
        // 交付服务
        List<ErpDeliveryService> deliveryList = deliveryServiceService.findByEndFlag(zhangbeiId, CommonConstants.Sign.YES, serviceTypeList);
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            list.addAll(deliveryList);
        }
        // 智慧餐厅服务
        List<ErpDeliveryService> zhihuiList = deliveryServiceService.findZhiHuiByEndFlag(zhangbeiId, CommonConstants.Sign.YES);
        if (CollectionUtils.isNotEmpty(zhihuiList)) {
            for (ErpDeliveryService zhihui : zhihuiList) {
                zhihui.setServiceType(FlowServiceType.ZHI_HUI_CAN_TING.getType());
            }
            list.addAll(zhihuiList);
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<String, String> photoMap = sysConstantsService.getGoodPhotoMap();
        List<ShopServiceResponseDto> result = new ArrayList<>();
        ShopServiceResponseDto dto = null;
        for (ErpDeliveryService service : list) {
            dto = new ShopServiceResponseDto();
            // 服务类型
            String serviceType = service.getServiceType();
            dto.setServiceType(serviceType);
            dto.setFinishTime(DateUtils.formatDate(service.getFlowEndTime()));
            dto.setGoodPhotoUrl(photoMap.get(serviceType));
            dto.setGoodRemark("服务*1");
            dto.setGoodTypeName(FlowServiceType.getByType(serviceType).getName());
            String procInsId = service.getProcInsId();
            dto.setProcInsId(procInsId);
            dto.setHasScheduleFlag(calculateHasScheduleFlag(procInsId, serviceType));

            result.add(dto);
        }
        return result;
    }


    /**
     * 获取需要进行操作的服务类型集合
     *
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     */
    private List<String> getServiceTypeList() {
        List<String> result = new ArrayList<>();
        result.add(FlowServiceType.SPLIT_JU_YIN_KE.getType());
        result.add(FlowServiceType.DELIVERY_FMPS.getType());
        result.add(FlowServiceType.DELIVERY_FMPS_BASIC.getType());
        result.add(FlowServiceType.DELIVERY_JYK.getType());
        result.add(FlowServiceType.DELIVERY_INTO_PIECES.getType());
        return result;
    }


    /**
     * 获取引流服务完成列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private List<ShopServiceResponseDto> getFinishSplitList(String zhangbeiId) {
        List<ShopServiceResponseDto> result = null;
        // 获取进行时的分单列表
        List<ErpOrderSplitInfo> splitList = erpOrderSplitInfoService.findListByZhangbeiIdStatus(zhangbeiId, SplitConstants.STATUS_FINISH);
        if (splitList != null) {
            result = new ArrayList<>();
            for (int i = 0; i < splitList.size(); i++) {
                ErpOrderSplitInfo info = splitList.get(i);
                // 分单id
                String splitId = info.getId();
                List<ErpOrderSplitGood> goodList = erpOrderSplitGoodService.findListBySplitId(splitId);
                if (goodList != null) {
                    // 商品备注（商品类型*数量）
                    String goodRemark = calculateSplitGoodRemark(goodList);
                    ShopServiceResponseDto view = new ShopServiceResponseDto();
                    view.setGoodTypeName(FlowServiceType.SPLIT_JU_YIN_KE.getName());
                    // 流程id
                    String procInsId = info.getProcInsId();
                    view.setProcInsId(procInsId);
                    // 服务类型
                    String serviceType = FlowServiceType.SPLIT_JU_YIN_KE.getType();
                    view.setServiceType(serviceType);
                    view.setGoodPhotoUrl(getGoodPhotoUrl(serviceType));
                    view.setGoodRemark(goodRemark);
                    // 是否有流程进度数据
                    String hasScheduleFlag = calculateHasScheduleFlag(procInsId, serviceType);
                    view.setHasScheduleFlag(hasScheduleFlag);
                    // 完成时间
                    view.setFinishTime(DateUtils.formatDate(info.getEndTime()));
                    result.add(view);
                }
            }
        }
        return result;
    }

    /**
     * 获取进行中的服务列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private List<ShopServiceResponseDto> getBeginShopService(String zhangbeiId) {
        List<ShopServiceResponseDto> result = new ArrayList<>();
        // 获取引流服务进行中列表
        List<ShopServiceResponseDto> splitList = getBeginSplitList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(splitList)) {
            result.addAll(splitList);
        }
        // 获取 交付流程中 服务进行中列表
        List<ShopServiceResponseDto> deliveryList = getBeginDeliveryList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            result.addAll(deliveryList);
        }
        return result;
    }


    private List<ShopServiceResponseDto> getBeginDeliveryList(String zhangbeiId) {
        List<String> serviceTypeList = getServiceTypeList();
        List<ErpDeliveryService> list = new ArrayList<>();
        // 交付服务
        List<ErpDeliveryService> deliveryList = deliveryServiceService.findByEndFlag(zhangbeiId, CommonConstants.Sign.NO, serviceTypeList);
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            list.addAll(deliveryList);
        }
        // 智慧餐厅服务
        List<ErpDeliveryService> zhihuiList = deliveryServiceService.findZhiHuiByEndFlag(zhangbeiId, CommonConstants.Sign.NO);
        if (CollectionUtils.isNotEmpty(zhihuiList)) {
            for (ErpDeliveryService zhihui : zhihuiList) {
                zhihui.setServiceType(FlowServiceType.ZHI_HUI_CAN_TING.getType());
            }
            list.addAll(zhihuiList);
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<String, String> photoMap = sysConstantsService.getGoodPhotoMap();
        List<ShopServiceResponseDto> result = new ArrayList<>();
        ShopServiceResponseDto dto = null;
        for (ErpDeliveryService service : list) {
            dto = new ShopServiceResponseDto();
            // 服务类型
            String serviceType = service.getServiceType();
            dto.setServiceType(serviceType);
            dto.setGoodPhotoUrl(photoMap.get(serviceType));
            dto.setGoodRemark("服务*1");
            dto.setGoodTypeName(FlowServiceType.getByType(serviceType).getName());
            String procInsId = service.getProcInsId();
            dto.setProcInsId(procInsId);
            dto.setHasScheduleFlag(calculateHasScheduleFlag(procInsId, serviceType));
            // 获取正在进行的服务进度节点
            ServiceProgressExtra scheduleExtra = serviceScheduleService.getBeginMaxStartTime(procInsId, serviceType);
            ServiceScheduleResponseDto beginService = getServiceScheduleResponseDto(scheduleExtra);
            dto.setBeginService(beginService);
            result.add(dto);
        }
        return result;
    }


    /**
     * 获取引流服务进行中列表
     * 
     * @param zhangbeiId
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    private List<ShopServiceResponseDto> getBeginSplitList(String zhangbeiId) {
        List<ShopServiceResponseDto> result = null;
        // 获取进行时的分单列表
        List<ErpOrderSplitInfo> splitList = erpOrderSplitInfoService.findListByZhangbeiIdStatus(zhangbeiId, SplitConstants.STATUS_BEGIN);
        if (splitList != null) {
            result = new ArrayList<>();
            // 同类型商品汇总map
            for (int i = 0; i < splitList.size(); i++) {
                ErpOrderSplitInfo info = splitList.get(i);
                // 分单id
                String splitId = info.getId();
                List<ErpOrderSplitGood> goodList = erpOrderSplitGoodService.findListBySplitId(splitId);
                if (goodList != null) {
                    // 商品备注（商品类型*数量）
                    String goodRemark = calculateSplitGoodRemark(goodList);
                    ShopServiceResponseDto view = new ShopServiceResponseDto();
                    view.setGoodTypeName(FlowServiceType.SPLIT_JU_YIN_KE.getName());
                    // 流程id
                    String procInsId = info.getProcInsId();
                    view.setProcInsId(procInsId);
                    // 上级服务类型
                    String serviceType = FlowServiceType.SPLIT_JU_YIN_KE.getType();
                    view.setServiceType(serviceType);
                    view.setGoodPhotoUrl(getGoodPhotoUrl(serviceType));
                    view.setGoodRemark(goodRemark);
                    // 是否有流程进度数据
                    String hasScheduleFlag = calculateHasScheduleFlag(procInsId, serviceType);
                    view.setHasScheduleFlag(hasScheduleFlag);
                    // 获取正在进行的服务进度节点
                    ServiceProgressExtra scheduleExtra = serviceScheduleService.getBeginMaxStartTime(procInsId, serviceType);
                    ServiceScheduleResponseDto beginService = getServiceScheduleResponseDto(scheduleExtra);
                    view.setBeginService(beginService);
                    result.add(view);
                }
            }
        }
        return result;
    }


    /**
     * 计算 是否有流程进度
     *
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private String calculateHasScheduleFlag(String procInsId, String serviceType) {
        boolean has = serviceScheduleService.hasByServiceType(procInsId, serviceType);
        String hasScheduleFlag = has ? CommonConstants.Sign.YES : CommonConstants.Sign.NO;
        return hasScheduleFlag;
    }


    /**
     * 获取 未启动服务列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    private List<ShopServiceResponseDto> getNoBeginShopService(String zhangbeiId) {
        List<ShopServiceResponseDto> result = new ArrayList<>();
        // 获取引流服务未启动列表
        List<ShopServiceResponseDto> splitList = getNoBeginSplitList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(splitList)) {
            result.addAll(splitList);
        }
        // 获取 交付流程中 服务未启动列表
        List<ShopServiceResponseDto> deliveryList = getNoBeginDeliveryList(zhangbeiId);
        if (CollectionUtils.isNotEmpty(deliveryList)) {
            result.addAll(deliveryList);
        }
        return result;
    }


    private List<ShopServiceResponseDto> getNoBeginDeliveryList(String zhangbeiId) {
        List<String> itemCodeList = new ArrayList<>();
        itemCodeList.add(WorkFlowConstants.DELIVERY_ITEM_CODE_KCL);
        itemCodeList.add(WorkFlowConstants.DELIVERY_ITEM_CODE_KCL_BASIC);
        itemCodeList.add(WorkFlowConstants.DELIVERY_ITEM_CODE_JYK);
        itemCodeList.add(WorkFlowConstants.DELIVERY_ITEM_CODE_ZB);
        List<SysConstants> itemList = sysConstantsService.findByKeyList(itemCodeList);
        if (CollectionUtils.isEmpty(itemList)) {
            return null;
        }
        List<String> itemIdList = new ArrayList<>();
        for (SysConstants obj : itemList) {
            String value = obj.getValue();
            if (value != null) {
                itemIdList.add(value);
            }
        }
        // 获取 服务项 数量总数
        List<ErpOrderGoodServiceInfo> serviceInfoList = goodServiceInfoService.findByItemIdGroup(zhangbeiId, itemIdList);
        if (CollectionUtils.isEmpty(serviceInfoList)) {
            return null;
        }
        // 获取 key=服务项目id ，value=服务类型名称的 map
        Map<String, FlowServiceType> itemIdServiceNameMap = getItemIdServiceNameMap(itemList);
        // 图片地址map
        Map<String, String> photoMap = sysConstantsService.getGoodPhotoMap();
        List<ShopServiceResponseDto> result = new ArrayList<>();
        ShopServiceResponseDto dto = null;
        for (ErpOrderGoodServiceInfo serviceInfo : serviceInfoList) {
            Integer pendingNum = serviceInfo.getPendingNum();
            if (pendingNum != null && pendingNum > 0) {
                String itemId = serviceInfo.getServiceItemId();
                FlowServiceType serviceType = itemIdServiceNameMap.get(itemId);
                dto = new ShopServiceResponseDto();
                dto.setGoodTypeName(serviceType.getName());
                String goodRemark = "服务*" + pendingNum;
                dto.setGoodRemark(goodRemark);
                dto.setGoodPhotoUrl(photoMap.get(serviceType.getType()));
                dto.setHasScheduleFlag(CommonConstants.Sign.NO);

                result.add(dto);
            }
        }
        return result;
    }


    /**
     * 获取 key=服务项目id ，value=服务类型enum map
     *
     * @param itemList
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     */
    private Map<String, FlowServiceType> getItemIdServiceNameMap(List<SysConstants> itemList) {
        Map<String, FlowServiceType> map = new HashMap<>();
        for (SysConstants obj : itemList) {
            String key = obj.getKey();
            String value = obj.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (WorkFlowConstants.DELIVERY_ITEM_CODE_KCL.equals(key)) {
                map.put(value, FlowServiceType.DELIVERY_FMPS);
            } else if (WorkFlowConstants.DELIVERY_ITEM_CODE_KCL_BASIC.equals(key)) {
                map.put(value, FlowServiceType.DELIVERY_FMPS_BASIC);
            } else if (WorkFlowConstants.DELIVERY_ITEM_CODE_JYK.equals(key)) {
                map.put(value, FlowServiceType.DELIVERY_JYK);
            } else if (WorkFlowConstants.DELIVERY_ITEM_CODE_ZB.equals(key)) {
                map.put(value, FlowServiceType.DELIVERY_INTO_PIECES);
            } else if (WorkFlowConstants.DELIVERY_ITEM_CODE_WR.equals(key)) {
                map.put(value, FlowServiceType.ZHI_HUI_CAN_TING);
            }
        }
        return map;
    }


    /**
     * 获取引流服务未启动列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    private List<ShopServiceResponseDto> getNoBeginSplitList(String zhangbeiId) {
        List<ErpOrderOriginalInfo> list = erpOrderOriginalInfoService.findNoBeginListByZhangbeiId(zhangbeiId, OrderGoodConstants.GOOD_TYPE_JU_ID);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<ShopServiceResponseDto> result = new ArrayList<>();
        ShopServiceResponseDto view = null;
        for (ErpOrderOriginalInfo info : list) {
            String orderId = info.getId();
            // 获取聚引客待处理>0的商品
            List<ErpOrderOriginalGood> goodList = erpOrderOriginalGoodService.findNoBeginListByOrderId(orderId, OrderGoodConstants.GOOD_TYPE_JU_ID);
            if (goodList != null) {
                view = new ShopServiceResponseDto();
                view.setGoodTypeName(FlowServiceType.SPLIT_JU_YIN_KE.getName());
                // 计算聚引客商品备注
                String goodRemark = calculateOrderGoodRemark(goodList);
                view.setGoodRemark(goodRemark);
                view.setGoodPhotoUrl(getGoodPhotoUrl(FlowServiceType.SPLIT_JU_YIN_KE.getType()));
                view.setHasScheduleFlag(CommonConstants.Sign.NO);
                result.add(view);
            }
        }
        return result;
    }

    /**
     * 计算引流服务 订单商品备注（商品类型×数量）
     *
     * @param goodList
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    private String calculateOrderGoodRemark(List<ErpOrderOriginalGood> goodList) {
        if (CollectionUtils.isEmpty(goodList)) {
            return null;
        }
        // 分割字符串
        String splitStr = "; ";
        // 同类型商品汇总map
        Map<String, Integer> goodMap = null;
        StringBuilder strBuilder = new StringBuilder();
        goodMap = new HashMap<>();
        for (ErpOrderOriginalGood good : goodList) {
            // 商品名称
            String goodName = good.getGoodName();
            Integer sum = goodMap.get(goodName);
            // 待处理商品数量
            int pendingNum = good.getPendingNum();
            if (sum == null) {
                goodMap.put(goodName, pendingNum);
            } else {
                sum += pendingNum;
                goodMap.put(goodName, sum);
            }
        }
        for (Map.Entry<String, Integer> entry : goodMap.entrySet()) {
            strBuilder.append(splitStr).append(entry.getKey()).append(CommonConstants.Sign.ASTERISK).append(entry.getValue());
        }
        // 商品备注（商品类型*数量）
        String goodRemark = strBuilder.toString();
        if (goodRemark.length() > splitStr.length()) {
            goodRemark = goodRemark.substring(splitStr.length());
        }
        return goodRemark;
    }

    /**
     * 计算引流服务 分单商品备注（商品类型×数量）
     *
     * @param goodList
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private String calculateSplitGoodRemark(List<ErpOrderSplitGood> goodList) {
        if (CollectionUtils.isEmpty(goodList)) {
            return null;
        }
        // 分割字符串
        String splitStr = "; ";
        StringBuilder strBuilder = new StringBuilder();
        // 同类型商品汇总map
        Map<String, Integer> goodMap = new HashMap<>();
        for (ErpOrderSplitGood good : goodList) {
            // 商品名称
            String goodName = good.getGoodName();
            Integer sum = goodMap.get(goodName);
            // 商品数量
            int num = good.getNum();
            if (sum == null) {
                goodMap.put(goodName, num);
            } else {
                sum += num;
                goodMap.put(goodName, sum);
            }
        }
        for (Map.Entry<String, Integer> entry : goodMap.entrySet()) {
            strBuilder.append(splitStr).append(entry.getKey()).append(CommonConstants.Sign.ASTERISK).append(entry.getValue());
        }
        // 商品备注（商品类型*数量）
        String goodRemark = strBuilder.toString();
        if (goodRemark.length() > splitStr.length()) {
            goodRemark = goodRemark.substring(splitStr.length());
        }
        return goodRemark;
    }

    /**
     * 根据上级服务类型获取商品图片地址
     *
     * @param parentType
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    private String getGoodPhotoUrl(String parentType) {
        Map<String, String> map = sysConstantsService.getGoodPhotoMap();
        return map.get(parentType);
    }

    /**
     * 获取首页服务通知 列表
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    public List<ShopServiceMessageResponseDto> getServiceMessageBox(String zhangbeiId) {
        logger.info("getServiceMessageBox start | zhangbeiId={}", zhangbeiId);
        List<ErpServiceMessage> list = serviceMessageService.findNoEnd(zhangbeiId);
        List<ShopServiceMessageResponseDto> result = getServiceMessageResponseDtoList(list);
        logger.info("getServiceMessageBox end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 根据服务通知 获取 首页服务通知列表
     *
     * @param list
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    private List<ShopServiceMessageResponseDto> getServiceMessageResponseDtoList(List<ErpServiceMessage> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<String, String> goodPhotoMap = sysConstantsService.getGoodPhotoMap();
        List<ShopServiceMessageResponseDto> result = new ArrayList<>();
        ShopServiceMessageResponseDto dto = null;
        ServiceMessageResponseDto messageDto = null;
        // 每个流程对应一个服务通知模块
        Map<String, ShopServiceMessageResponseDto> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            // 最大显示条数
            if (i >= MAX_MESSAGE_SIZE) {
                break;
            }
            ErpServiceMessage message = list.get(i);
            String procInsId = message.getProcInsId();
            String serviceType = message.getServiceType();
            String key = procInsId + CommonConstants.Sign.COMMA + serviceType;
            dto = map.get(key);
            if (dto == null) {
                dto = new ShopServiceMessageResponseDto();
                dto.setMessageList(new ArrayList<>());
                FlowServiceType serviceTypeEnum = FlowServiceType.getByType(serviceType);
                String serviceName = serviceTypeEnum == null ? null : serviceTypeEnum.getName();
                dto.setServiceName(serviceName);
                dto.setServiceNums(message.getServiceNums());
                // 服务类型 图片
                dto.setServiceLogo(goodPhotoMap.get(serviceType));
                result.add(dto);

                map.put(key, dto);
            }
            messageDto = new ServiceMessageResponseDto();
            messageDto.setId(message.getId());
            messageDto.setContent(message.getContent());
            messageDto.setType(message.getType());
            messageDto.setLinkType(message.getLinkType());
            // 替换交互入口 变量
            String linkParam = serviceLinkVariableService.replaceLinkParam(procInsId, message.getServiceType(), message.getNodeType(),
                            message.getLinkParam());
            messageDto.setLinkParam(linkParam);
            dto.getMessageList().add(messageDto);
        }
        return result;
    }
}
