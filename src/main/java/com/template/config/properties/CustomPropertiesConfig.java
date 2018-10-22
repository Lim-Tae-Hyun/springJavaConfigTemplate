package com.template.config.properties;

import com.template.util.CommonValidator;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.context.annotation.Configuration;

/**
 * @description : property file configuration class
 * @author : limth
 */
/* @Configuration : used on classes which define beans */
@Configuration
public class CustomPropertiesConfig extends PropertiesConfiguration {

    public CustomPropertiesConfig() {
        super();
    }

    public CustomPropertiesConfig(String fileName) throws ConfigurationException {
        super(fileName);
    }

    @Override
    public String getString(String key) {
        String prop = (String)super.getProperty(key);
        try {
            return prop;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getString(String key, String defaultValue) {
        String prop = (String)super.getProperty(key);
        try {
            if ( CommonValidator.isNullOrEmpty(prop) ) {
                return defaultValue;
            }

            return prop;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @description : configures reloading strategy when property value in file is modified
     * @param configuration
     * @throws ConfigurationException
     */
    public void setReloadingStrategyDefault(PropertiesConfiguration configuration) throws ConfigurationException {
        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        configuration.setEncoding("UTF-8");
        strategy.setConfiguration(configuration);
        strategy.setRefreshDelay(1000);
        strategy.init();
        super.setReloadingStrategy(strategy);
        super.refresh();
    }
}
