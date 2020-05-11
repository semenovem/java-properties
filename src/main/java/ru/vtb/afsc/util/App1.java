
package ru.vtb.afsc.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import ru.vtb.afsc.cli.ArgumentParser;
import ru.vtb.afsc.cli.ArgumentParser.Option;

import java.lang.Class.*;

public class App1 {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        String[] s = {"--config", "help"};
        ArgumentParser cmd = new ArgumentParser(s);


        Method[] methods = ArgumentParser.class.getMethods();

        for (Method m: methods) {
            System.out.println(m.getName());
        }



        System.exit(0);

        cmd.man();

        System.out.println("------------");


        String filePath = cmd.get(Option.CONFIGURATION_FILE);
        System.out.println(filePath);


        System.out.println(cmd.has(Option.CONFIGURATION_FILE));



    }

}
