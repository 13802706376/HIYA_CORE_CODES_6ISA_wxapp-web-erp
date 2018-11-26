package com.yunnex.ops.erp.modules.order.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.order.entity.ErpBeginOrderApi;
import com.yunnex.ops.erp.modules.order.entity.ErpNoBeginOrderApi;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.entity.ErpOverOrderApi;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitInfoService;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;

import yunnex.common.core.dto.ApiResult;

/**
 * Controller
 * 
 * @author pengchenghe
 * @version 2018-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/api/selectOrderApi")
public class ErpOrderController extends BaseController {

    @Autowired
    private ErpOrderSplitInfoService erpOrderSplitInfoService;
    @Autowired
    private ErpShopInfoService shopService;

    @Value("${domain.wxapp.res}")
    private String environmentPhotoUrl;


    /*
     * @RequiresPermissions("order:erpOrderSplitInfo:view")
     * 
     * @RequestMapping(value = {"list", ""}) public String list(ErpOrderSplitInfo erpOrderSplitInfo,
     * HttpServletRequest request, HttpServletResponse response, Model model) {
     * Page<ErpOrderSplitInfo> page = erpOrderSplitInfoService.findPage(new
     * Page<ErpOrderSplitInfo>(request, response), erpOrderSplitInfo); model.addAttribute("page",
     * page); return "modules/order/erpOrderSplitInfoList"; }
     */

    /**
     * 进行中订单
     * 
     */
    @RequestMapping(value = "beginOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<List<ErpBeginOrderApi>> beginOrder(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        String zhangbeiId = params.get("zhangbeiId").toString();
        List<ErpOrderSplitInfo> split = erpOrderSplitInfoService.findBeginOrder(zhangbeiId);
        List<ErpBeginOrderApi> beginApi = new ArrayList<ErpBeginOrderApi>();
        ErpBeginOrderApi begin = null;
        if (!split.isEmpty()) {
            for (int i = 0; i < split.size(); i++) {
                begin = new ErpBeginOrderApi();
                begin.setSplitId(split.get(i).getId());
                if (null == split.get(i).getErpStoreInfoId() || "".equals(split.get(i).getErpStoreInfoId())) {
                    begin.setErpStoreInfoId("");
                } else {
                    begin.setErpStoreInfoId(split.get(i).getErpStoreInfoId());
                }
                begin.setGoodName(split.get(i).getGoodName());
                begin.setGoodTypeName(split.get(i).getGoodTypeName());
                begin.setShopName(split.get(i).getShopName());
                if (null == split.get(i).getShortName() || "".equals(split.get(i).getShortName())) {
                    begin.setShortName("订单还未选择门店");
                } else {
                    begin.setShortName(split.get(i).getShortName());
                }
                begin.setGoodTypeId(split.get(i).getGoodTypeId().toString());
                if (null != split.get(i).getEnvironmentPhoto() && !"".equals(split.get(i).getEnvironmentPhoto())) {
                    begin.setEnvironmentPhoto(environmentPhotoUrl + (split.get(i).getEnvironmentPhoto().split(";"))[0]);
                } else {
                    begin.setEnvironmentPhoto("");
                }
                beginApi.add(begin);
            }
            return ApiResult.build(beginApi);
        }
        ApiResult<List<ErpBeginOrderApi>> result = ApiResult.build(null);
        result.setCode("1");
        return result;
    }

    /**
     * 完成的订单
     * 
     */
    @RequestMapping(value = "overOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<List<ErpOverOrderApi>> overOrder(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        String zhangbeiId = params.get("zhangbeiId").toString();
        List<ErpOrderSplitInfo> split = erpOrderSplitInfoService.findOverOrder(zhangbeiId);
        List<ErpOverOrderApi> overApi = new ArrayList<ErpOverOrderApi>();
        ErpOverOrderApi over = null;
        if (!split.isEmpty()) {
            for (int i = 0; i < split.size(); i++) {
                over = new ErpOverOrderApi();
                over.setSplitId(split.get(i).getId());
                over.setErpStoreInfoId(split.get(i).getErpStoreInfoId());
                over.setGoodName(split.get(i).getGoodName());
                over.setGoodTypeName(split.get(i).getGoodTypeName());
                over.setShopName(split.get(i).getShopName());
                over.setShortName(split.get(i).getShortName());
                over.setGoodTypeId(split.get(i).getGoodTypeId().toString());
                if (null != split.get(i).getEnvironmentPhoto() && !"".equals(split.get(i).getEnvironmentPhoto())) {
                    over.setEnvironmentPhoto(environmentPhotoUrl + (split.get(i).getEnvironmentPhoto().split(";"))[0]);
                } else {
                    over.setEnvironmentPhoto("");
                }
                overApi.add(over);
            }
            return ApiResult.build(overApi);
        }
        ApiResult<List<ErpOverOrderApi>> result = ApiResult.build(null);
        result.setCode("1");
        return result;
    }

    /**
     * 未开始的订单
     * 
     */
    @RequestMapping(value = "noBeginOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<List<ErpNoBeginOrderApi>> noBeginOrder(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        String zhangbeiId = params.get("zhangbeiId").toString();
        List<ErpOrderSplitInfo> split = erpOrderSplitInfoService.findNoBeginOrder(zhangbeiId);
        List<ErpNoBeginOrderApi> nobeginApi = new ArrayList<ErpNoBeginOrderApi>();
        ErpNoBeginOrderApi nobegin = null;
        if (!split.isEmpty()) {
            for (int i = 0; i < split.size(); i++) {
                nobegin = new ErpNoBeginOrderApi();
                nobegin.setGoodName(split.get(i).getGoodName());
                nobegin.setGoodTypeName(split.get(i).getGoodTypeName());
                nobegin.setShopName(split.get(i).getShopName());
                nobegin.setId(split.get(i).getShopInfoId());
                nobegin.setGoodTypeId(split.get(i).getGoodTypeId().toString());
                nobeginApi.add(nobegin);
            }
            return ApiResult.build(nobeginApi);
        }
        ApiResult<List<ErpNoBeginOrderApi>> result = ApiResult.build(null);
        result.setCode("1");
        return result;
    }


    /**
     * 根据掌贝id 获取商户信息
     * 
     */
    @RequestMapping(value = "getShop", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<ErpShopInfo> getShop(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        String zhangbeiId = params.get("zhangbeiId").toString();
        ErpShopInfo shop = shopService.getByZhangbeiId(zhangbeiId);
        return ApiResult.build(shop);
    }


}
