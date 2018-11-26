package com.yunnex.ops.erp.common.freemarker;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
/**
 * date  2018 02-07
 * 方法类
 * author
 */
@Component  
public class TemplateServiceFreeMarkerImpl {  
  
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateServiceFreeMarkerImpl.class);
  
    @Autowired  
    private FreeMarkerConfigurer freeMarkerConfigurer;  
  
    @Autowired  
    private Configuration freeMarkerConfiguration;  
  
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {  
        this.freeMarkerConfigurer = freeMarkerConfigurer;  
    }  
  
    public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {  
        this.freeMarkerConfiguration = freeMarkerConfiguration;  
    }  
  
    public String getContent(String templateName, Map<String, Object> model) {  
        try {  
            
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();  
            TemplateHashModel staticModels = wrapper.getStaticModels();  
            TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get("com.yunnex.ops.erp.modules.sys.utils.UserUtils");  
            if(model!=null){
                model.put("UserUtils", fileStatics);
            }
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);  
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);  

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            try {  
                Template t = freeMarkerConfiguration.getTemplate(templateName);  
                return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);  
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }  
        }  
  
        return null;  
    }  
} 