package com.yunnex.ops.erp.common.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 由于代码中需要直接访问配置，所以将所有配置复制到一个全局对象中。
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        if (props != null) {
            props.entrySet().forEach(entry -> {
                GlobalConfig.put(entry.getKey().toString(), entry.getValue().toString());
            });
        }
    }

}
