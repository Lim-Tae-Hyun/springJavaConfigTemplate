package com.template.config.properties;

import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * @description : reloadable property configuration class
 * @author : limth
 */
/* @Configuration : used on classes which define beans */
@Configuration
public class ReroadblePropertiesConfig {

    private final static String propertiesPath = "config/properties/";

    @Bean
    public static FileChangedReloadingStrategy fileChangedReloadingStrategy() {
        return new FileChangedReloadingStrategy();
    }

    public static String getFilePath(String fileName) {
        try {
            File file = new ClassPathResource(propertiesPath + fileName).getFile();
            return file.getPath().replace("\\", "/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
