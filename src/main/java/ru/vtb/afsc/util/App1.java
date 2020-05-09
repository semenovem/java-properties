
package ru.vtb.afsc.util;

import ru.vtb.afsc.cli.ArgumentParser;
import ru.vtb.afsc.cli.ArgumentParser.Option;

public class App1 {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        String[] s = {"--config", "--help"};


        ArgumentParser cmd = new ArgumentParser(s);

        cmd.man();

        System.out.println("------------");


        String filePath = cmd.get(Option.CONFIGURATION_FILE);
        System.out.println(filePath);


        System.out.println(cmd.has(Option.CONFIGURATION_FILE));



    }

}
