package org.sozkan.utils;

import java.io.File;
import java.nio.file.Files;
import java.util.Properties;

public class ConfigFileReader {

    public static String readConfigFile(String source, String property) {
        File file = new File(source);
        Properties properties = new Properties();

        try{
            properties.load(Files.newInputStream(file.toPath()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties.getProperty(property);
    }
}
