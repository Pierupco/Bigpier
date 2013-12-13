package com.pier.support.util;

import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PropertiesManager {
    /**
     * logger
     */
    private static Logger eblogger = Logger.getLogger(PropertiesManager.class.getName());
    private static String appConfigPrefix = "config/";

    public static Properties getProperties(String name, String locale) {
        return (loadTopLevelProperties(appConfigPrefix + name + "/" + locale + ".prop.txt", false));
    }

    private static String getTopLevelPath(String name) {
        URL url = PropertiesManager.class.getResource("/" + name);
        if (url == null) {
            eblogger.warning("Cannot find properties path " + name);
            return (null);
        }
        return (url.getPath());

    }

    private static Properties loadTopLevelProperties(String name, boolean useXML) {
        PropertiesPersister dpp = new DefaultPropertiesPersister();

        Properties props = new Properties();
        try {
            InputStream is = PropertiesManager.class.getResourceAsStream("/" + name);
            if (useXML) {
                dpp.loadFromXml(props, is);
            } else {
                dpp.load(props, new InputStreamReader(is, "UTF-8"));
            }
        } catch (Exception e) {
            eblogger.log(Level.WARNING, "Cannot load top level property for " + name, e);
            props = null;
        }
        return (props);
    }

    public static Properties getAppProperties(String name) {
        return (loadTopLevelProperties(name, false));
    }

    public static File getAppFile(String name) {
        return (new File(getTopLevelPath(name)));
    }

    public static String getAppPath(String name) {
        return (getTopLevelPath(name));
    }

    /**
     * Read the properties files form config directory.
     */

    public static String getConfigPath(String name) {
        return (getTopLevelPath(appConfigPrefix + name));
    }

    public static File getConfigFile(String name) {
        return (new File(getConfigPath(name)));
    }
}
