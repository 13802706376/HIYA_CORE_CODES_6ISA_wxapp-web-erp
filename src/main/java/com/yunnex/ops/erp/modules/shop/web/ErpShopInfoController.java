package com.yunnex.ops.erp.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.config.Global;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.result.BaseResult;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.common.web.BaseController;
import com.yunnex.ops.erp.modules.qualify.service.ErpShopExtensionQualifyService;
import com.yunnex.ops.erp.modules.qualify.service.ErpShopPayQualifyService;
import com.yunnex.ops.erp.modules.shop.dto.ShopAdviserResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopBusinessInfoResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopServiceAllResponseDto;
import com.yunnex.ops.erp.modules.shop.dto.ShopServiceMessageResponseDto;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoApiService;
import com.yunnex.ops.erp.modules.shop.service.ErpShopInfoService;
import com.yunnex.ops.erp.modules.shop.view.ShopIndexView;
import com.yunnex.ops.erp.modules.sys.entity.Dict;
import com.yunnex.ops.erp.modules.sys.service.DictService;

import yunnex.common.core.dto.ApiResult;

/**
 * 商户管理Controller
 * 
 * @author huanghaidong
 * @version 2017-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/erpShopInfo")
public class ErpShopInfoController extends BaseController {

    @Autowired
    private ErpShopInfoService erpShopInfoService;

    @Autowired
    private DictService dictService;

    @Autowired
    private ErpShopPayQualifyService erpShopPayQualifyService;

    @Autowired
    private ErpShopExtensionQualifyService erpShopExtensionQualifyService;

    @Autowired
    private ErpShopInfoApiService erpShopInfoApiService;

    @ModelAttribute
    public ErpShopInfo get(@RequestParam(required = false) String id) {
        ErpShopInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = erpShopInfoService.get(id);
        }
        if (entity == null) {
            entity = new ErpShopInfo();
        }
        return entity;
    }

    @RequiresPermissions("shop:erpShopInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(ErpShopInfo erpShopInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ErpShopInfo> page = erpShopInfoService.findPage(new Page<ErpShopInfo>(request, response), erpShopInfo);
        model.addAttribute("page", page);
        return "modules/shop/erpShopInfoList";
    }

    @RequiresPermissions("shop:erpShopInfo:view")
    @RequestMapping(value = "form")
    public String form(ErpShopInfo erpShopInfo, Model model) {
        List<Dict> extensionList = dictService.findListByType("extension_passageway_qulify");
        List<Dict> payList = dictService.findListByType("pay_passageway_qulify");
        if (CollectionUtils.isNotEmpty(payList)) {
            List<String> payQualifyList = erpShopPayQualifyService.findPayQualifyList(erpShopInfo.getId());
            if (CollectionUtils.isNotEmpty(payQualifyList)) {
                for (Dict dict : payList) {
                    if (payQualifyList.contains(dict.getValue())) {
                        dict.setHasPermission(true);
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(extensionList)) {
            List<String> extensionQualifyList = erpShopExtensionQualifyService.findExtensionQualifyList(erpShopInfo.getId());
            if (CollectionUtils.isNotEmpty(extensionQualifyList)) {
                for (Dict dict : extensionList) {
                    if (extensionQualifyList.contains(dict.getValue())) {
                        dict.setHasPermission(true);
                    }
                }
            }
        }
        model.addAttribute("erpShopInfo", erpShopInfo).addAttribute("extensionList", extensionList).addAttribute("payList", payList);
        return "modules/shop/erpShopInfoForm";
    }

    @RequiresPermissions("shop:erpShopInfo:edit")
    @RequestMapping(value = "save")
    public String save(ErpShopInfo erpShopInfo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, erpShopInfo)) {
            return form(erpShopInfo, model);
        }
        erpShopInfoService.save(erpShopInfo);
        addMessage(redirectAttributes, "保存商户成功");
        return "redirect:" + Global.getAdminPath() + "/shop/erpShopInfo/?repage";
    }

    @RequiresPermissions("shop:erpShopInfo:edit")
    @RequestMapping(value = "delete")
    public String delete(ErpShopInfo erpShopInfo, RedirectAttributes redirectAttributes) {
        erpShopInfoService.delete(erpShopInfo);
        addMessage(redirectAttributes, "删除商户成功");
        return "redirect:" + Global.getAdminPath() + "/shop/erpShopInfo/?repage";
    }

    @RequiresPermissions("shop:erpShopInfo:edit")
    @RequestMapping(value = "syncAll")
    @ResponseBody
    public JSONObject syncAll() {
        boolean result = erpShopInfoApiService.syncAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    /**
     * 商户选择查询页面
     *
     * @param request
     * @param response
     * @param keyWord
     * @param model
     * @return
     * @date 2017年11月27日
     * @author SunQ
     */
    @RequestMapping(value = "shopSearchList")
    public String shopSearchList(HttpServletRequest request, HttpServletResponse response, String keyWord, Model model) {
        ErpShopInfo erpShopInfo = new ErpShopInfo();
        erpShopInfo.setDelFlag("0");
        if (StringUtils.isNotBlank(keyWord)) {
            erpShopInfo.setName(keyWord);
            erpShopInfo.setNumber(keyWord);
        }

        Page<ErpShopInfo> page = erpShopInfoService.searchList(new Page<ErpShopInfo>(request, response), erpShopInfo);
        model.addAttribute("page", page);
        model.addAttribute("shopList", page.getList());
        model.addAttribute("keyWord", keyWord);
        return "modules/shop/erpShopSearchList";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<ApiResult<ErpShopInfo>> login(@RequestParam("zhangbeiId") String accountNo, @RequestParam String zhangbeiPassword,
                    @RequestParam Boolean rememberMe, HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("uuid");
        BaseResult baseResult = erpShopInfoService.login(accountNo, zhangbeiPassword, uuid, rememberMe, response);
        ApiResult<ErpShopInfo> apiResult = ApiResult.build();
        if (!BaseResult.isSuccess(baseResult)) {
            apiResult.error(baseResult.getCode(), baseResult.getMessage());
            return ResponseEntity.ok(apiResult);
        }
        apiResult.setEntry((ErpShopInfo) baseResult.getAttach());
        apiResult.setMessage("登录成功");
        return ResponseEntity.ok(apiResult);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<ApiResult<Map<String, String>>> logout(HttpServletResponse response) {
        Map<String, String> result = erpShopInfoService.logout(response);
        ApiResult<Map<String, String>> apiResult = ApiResult.build(result);
        apiResult.setCode(result.get("code"));
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 获取店铺首页信息
     *
     * @return
     * @throws Exception
     * @date 2018年4月2日
     * @author linqunzhi
     */
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public @ResponseBody ApiResult<ShopIndexView> index(@RequestBody Map<String, Object> params) {
        String zhangbeiId = StringUtils.toString(params.get("zhangbeiId"), null);
        ApiResult<ShopIndexView> result = ApiResult.build();
        try {
            ShopIndexView view = erpShopInfoService.getIndexInfo(zhangbeiId);
            result.setEntry(view);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取首页信息异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取店铺业务信息
     *
     * @param params
     * @return
     * @date 2018年5月29日
     * @author linqunzhi
     */
    @RequestMapping(value = "businessInfo", method = RequestMethod.POST)
    public @ResponseBody ApiResult<ShopBusinessInfoResponseDto> businessInfo(@RequestBody Map<String, Object> params) {
        String zhangbeiId = StringUtils.toString(params.get("zhangbeiId"), null);
        ApiResult<ShopBusinessInfoResponseDto> result = ApiResult.build();
        try {
            ShopBusinessInfoResponseDto view = erpShopInfoService.getBusinessInfo(zhangbeiId);
            result.setEntry(view);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取店铺业务信息异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取我的专属顾问团队
     *
     * @return
     * @throws Exception
     * @date 2018年4月2日
     * @author linqunzhi
     */
    @RequestMapping(value = "adviserTeam", method = RequestMethod.POST)
    public @ResponseBody ApiResult<List<ShopAdviserResponseDto>> adviserTeam(@RequestBody Map<String, Object> params) {
        String zhangbeiId = StringUtils.toString(params.get("zhangbeiId"), null);
        ApiResult<List<ShopAdviserResponseDto>> result = ApiResult.build();
        try {
            List<ShopAdviserResponseDto> view = erpShopInfoService.getAdviserTeam(zhangbeiId);
            result.setEntry(view);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取我的专属顾问团队异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取服务列表数据
     *
     * @param params
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    @RequestMapping(value = "serviceAllList", method = RequestMethod.POST)
    public @ResponseBody ApiResult<ShopServiceAllResponseDto> serviceAllList(@RequestBody Map<String, Object> params) {
        String zhangbeiId = StringUtils.toString(params.get("zhangbeiId"), null);
        ApiResult<ShopServiceAllResponseDto> result = ApiResult.build();
        try {
            ShopServiceAllResponseDto dto = erpShopInfoService.getServiceAllList(zhangbeiId);
            result.setEntry(dto);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取服务列表数据异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

    /**
     * 获取首页服务通知 列表
     *
     * @param params
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    @RequestMapping(value = "serviceMessageBox", method = RequestMethod.POST)
    public @ResponseBody ApiResult<List<ShopServiceMessageResponseDto>> serviceMessageBox(@RequestBody Map<String, Object> params) {
        String zhangbeiId = StringUtils.toString(params.get("zhangbeiId"), null);
        ApiResult<List<ShopServiceMessageResponseDto>> result = ApiResult.build();
        try {
            List<ShopServiceMessageResponseDto> list = erpShopInfoService.getServiceMessageBox(zhangbeiId);
            result.setEntry(list);
        } catch (ServiceException e) {
            result.error(e.getMessage());
        } catch (Exception e) {
            logger.info("获取首页服务通知 列表数据异常", e);
            result.error(CommonConstants.FailMsg.SYSTEM);
        }
        return result;
    }

}
