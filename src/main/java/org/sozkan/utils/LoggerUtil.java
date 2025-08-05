package org.sozkan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static Logger getLogger() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        return LoggerFactory.getLogger(className);
    }

    public static void info(String message){
        getLogger().info(message);
    }
    public static void error(String message){
        getLogger().error(message);
    }

}
