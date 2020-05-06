
package ru.vtb.afsc.util;

import java.io.IOException;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        final String[] localArgs = { "--config=/Users/evg/_dev/java/prop/src/main/resources/config.properties" };
        MgrCmdLineArgs mgr = new MgrCmdLineArgs(localArgs);

        String propsFilePath = mgr.getValueByKey("--config");

        System.out.println(propsFilePath);

        try {
            MgrProperties mgrProps = new MgrProperties(propsFilePath);

            System.out.println(mgrProps.getProp("db.user"));
        } catch (IOException e) {
            System.out.println("-----------------");
            e.printStackTrace();
        }
    }

}
