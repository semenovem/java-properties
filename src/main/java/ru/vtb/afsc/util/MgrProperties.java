package ru.vtb.afsc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;

public class MgrProperties {

    private final Properties properties = new Properties();

    public MgrProperties(final String propertiesFilePath) throws IOException {
        final InputStream input = new FileInputStream(propertiesFilePath);
        properties.load(input);
    }

    public String getProp(final String propName) {
        return properties.getProperty(propName);
    }

    public String getProp(final String propName, final String defaultValue) {
        return properties.getProperty(propName, defaultValue);
    }

    public Map<String, String> getAll() {
        final Enumeration<?> e = properties.propertyNames();
        final HashMap<String, String> list = new HashMap<>();

        while (e.hasMoreElements()) {
            final String key = (String) e.nextElement();

            list.put(key, getProp(key));
        }

        return list;
    }
}
