package ru.vtb.afsc.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CmdLineArgsTest {
  final private String[] args = { "--config=dir/dir/file.properties", "-c=dir/file.properties", "anythingKey" };

  @Test
  void parsingArgs() {
    MgrCmdLineArgs mgr = new MgrCmdLineArgs(args);

    assertTrue(mgr.hasKey("--config"));
    assertFalse(mgr.hasKey("unknownKey"));

    assertEquals(mgr.getValueByKey("--config"), "dir/dir/file.properties");
    assertEquals(mgr.getValueByKey("-c"), "dir/file.properties");
    assertNull(mgr.getValueByKey("anythingKey"));
  }
}
