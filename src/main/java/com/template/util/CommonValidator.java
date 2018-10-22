package com.template.util;

import org.springframework.stereotype.Component;

/**
 * @discription : validator class that used to check value
 * @author : limth
 */
/* @Component : when adding this annotation configuration file, can register as a bean  */
@Component
public class CommonValidator {

    public static boolean isNull(Object data) {

        if(data == null)
            return true;

        return false;
    }

    public static boolean isNullOrEmpty(Object data) {

        if(data == null)
            return true;

        if(data == "" || data.toString().trim() == "")
            return true;

        return data.toString().isEmpty();
    }
}
