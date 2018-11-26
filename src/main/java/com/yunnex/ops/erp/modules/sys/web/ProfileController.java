package com.yunnex.ops.erp.modules.sys.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunnex.ops.erp.common.config.Global;

@Controller
public class ProfileController {
    @Value("${productName}")
    private String productName;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jyk_flow_scheduler}")
    private String jykFlowScheduler;
    @Value("${cas.server.url}")
    private String casServerUrl;

    @Value("${api_shop_login_url}")
    private String apiShopLoginUrl;
    @Value("${api_erp_diagnosis_summary}")
    private String api_erp_diagnosis_summary;
    @Value("${api_good_info_url}")
    private String api_good_info_url;

    @RequestMapping("hello/profile")
    public void test() {
        System.out.println(productName);
        System.out.println(jdbcUrl);
        System.out.println(jykFlowScheduler);
        System.out.println(casServerUrl);
        System.out.println(apiShopLoginUrl);
        System.out.println(api_erp_diagnosis_summary);
        System.out.println(api_good_info_url);

        System.out.println("--------------------------");

        System.out.println(Global.getAdminPath());
        System.out.println(Global.getUserfilesBaseDir());
        System.out.println(Global.getConfig("api_shop_login_url"));
        System.out.println(Global.getMaxUploadSize());
        System.out.println(Global.getOemDomain());
        System.out.println(Global.getProjectPath());
    }
}
