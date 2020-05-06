package ru.vtb.afsc.util;

import java.util.ArrayList;
import javafx.util.Pair;

public class MgrCmdLineArgs {
  final private ArrayList<Pair<String, String>> parsedList;

  public MgrCmdLineArgs(final String[] args) {
    parsedList = parseArgsToKeyValue(args);
  }

  public String getValueByKey(final String findableKey) {
    for (final Pair<String, String> it : parsedList) {
      final String key = it.getKey();

      if (key.startsWith(findableKey)) {
        return it.getValue();
      }
    }

    return null;
  }

  public boolean hasKey(final String findableKey) {
    for (final Pair<String, String> it : parsedList) {
      final String key = it.getKey();

      if (key.startsWith(findableKey)) {
        return true;
      }
    }

    return false;
  }

  private static ArrayList<Pair<String, String>> parseArgsToKeyValue(final String[] args) {
    final ArrayList<Pair<String, String>> parsedList = new ArrayList<>();

    for (String key : args) {
      if (!key.startsWith("-") || !key.contains("=")) {
        parsedList.add(new Pair<>(key, null));

        continue;
      }

      String[] a = key.split("=", 2);
      parsedList.add(new Pair<>(a[0].trim(), a[1].trim()));
    }

    return parsedList;
  }
}
