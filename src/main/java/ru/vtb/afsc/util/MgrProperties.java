package ru.vtb.afsc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MgrProperties {
  final private Properties properties = new Properties();

  public MgrProperties(final String propertiesFilePath) throws IOException {
    try (InputStream input = new FileInputStream(propertiesFilePath)) {
      properties.load(input);
    }
  }

  public String getProp(final String propName) {
    return properties.getProperty(propName);
  }

  public String getProp(final String propName, final String defaultValue) {
    return properties.getProperty(propName, defaultValue);
  }
}
