package com.utour.util;

import java.util.Optional;

public class SystemUtils {

    public static String getEnv(String envName){
        return getEnv(envName, null);
    }

    public static String getEnv(String envName, String defaultString){
        return Optional.ofNullable(System.getenv(envName)).orElse(defaultString);
    }

    public static String getProperty(String propName, String defaultString){
        return System.getProperty(propName, defaultString);
    }

}
