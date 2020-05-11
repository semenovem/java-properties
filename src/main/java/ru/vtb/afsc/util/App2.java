
package ru.vtb.afsc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ru.vtb.afsc.env.AppEnvHolder;

import static ru.vtb.afsc.env.AppEnvHolder.HOSTNAME;

public class App2 {
    public static void main(String[] args) throws IOException {
        MgrProperties props = new MgrProperties("/Users/evg/_dev/java/prop/src/main/resources/config.properties");
        Map<String, String> listProps = props.getAll();

        System.out.println("1) " + HOSTNAME + " " + props.getProp("ENV_HOSTNAME"));


        AppEnvHolder env = new AppEnvHolder();

//        String[] propNames = env.getExtPropNames();
//
//        for (String s : propNames) {
//            System.out.println(s);
//        }

        Map<String, String> sys = System.getenv();
        env.setValues(sys);
        env.setValues(listProps);




        ArrayList<String> results = env.verify();

        for (String s: results) {
            System.out.println(s);
        }

        System.out.println("2) " + HOSTNAME);



        System.out.println("-------------------- toString .... " + ", success init: ");
        System.out.println(env);
//        env.field();

    }



}
