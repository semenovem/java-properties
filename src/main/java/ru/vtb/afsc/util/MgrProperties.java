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

    public MgrProperties(final String filePath) throws IOException {
        loadPropertyFile(filePath);
    }

    public MgrProperties(final String ... filePaths) throws IOException {
        for (final String filePath: filePaths) {
            if (filePath == null || filePath.isEmpty()) {
                continue;
            }
            loadPropertyFile(filePath);
            break;
        }
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

    private void loadPropertyFile(final String filePath) throws IOException {
        final InputStream input = new FileInputStream(filePath);
        properties.load(input);
    }
}
