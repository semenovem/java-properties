package ru.vtb.afsc.util;

import java.util.HashMap;

public class MgrCmdLineArgs {
  final private HashMap<String, String> parsedList;

  public MgrCmdLineArgs(final String[] args) {
    parsedList = parseArgsToKeyValue(args);
  }

  public String getValueByKey(final String findableKey) {
    return parsedList.get(findableKey);
  }

  public boolean hasKey(final String findableKey) {
    return parsedList.containsKey(findableKey);
  }

  private static HashMap<String, String> parseArgsToKeyValue(final String[] args) {
    final HashMap<String, String> parsedList = new HashMap<>();

    for (String key : args) {
      if (!key.startsWith("-") || !key.contains("=")) {
        parsedList.put(key, null);

        continue;
      }

      String[] a = key.split("=", 2);
      parsedList.put(a[0].trim(), a[1].trim());
    }

    return parsedList;
  }
}
