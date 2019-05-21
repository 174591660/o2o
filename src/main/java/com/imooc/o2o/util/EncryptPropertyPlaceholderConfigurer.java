package com.imooc.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 这个类继承了PropertyPlaceholderConfigurer，覆盖了convertProperty方法，对配置文件的某些属性键值对进行解密
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            String decryptValue = DESUtils.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName) {
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
