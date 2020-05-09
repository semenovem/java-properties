package ru.vtb.afsc.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vtb.afsc.cli.ArgumentParser.Option;

public class ArgumentParserTest {
    final private String[] testArgs0 = {"--config", "dir/dir/file.properties"};
    final private String[] testArgs1 = {"--anythings"};
    final private String[] testArgs2 = {"--config"};

    @Test
    @DisplayName("analysis of a arguments in which there is a config")
    void parsing0() {
        ArgumentParser mgr = new ArgumentParser(testArgs0);
        assertTrue(mgr.has(Option.CONFIGURATION_FILE));
        assertEquals(mgr.get(Option.CONFIGURATION_FILE), "dir/dir/file.properties");
    }

    @Test
    @DisplayName("analysis of a arguments in which there is no config")
    void parsing1() {
        ArgumentParser mgr = new ArgumentParser(testArgs1);
        assertFalse(mgr.has(Option.CONFIGURATION_FILE));
        assertNull(mgr.get(Option.CONFIGURATION_FILE));
    }

    @Test
    @DisplayName(
        "analysis of a arguments in which there is a config,but without required parameter")
    void parsing2() {
        ArgumentParser mgr = new ArgumentParser(testArgs2);
        assertFalse(mgr.has(Option.CONFIGURATION_FILE));
        assertNull(mgr.get(Option.CONFIGURATION_FILE));
    }
}
