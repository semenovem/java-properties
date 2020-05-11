
package ru.vtb.afsc.util;

import java.io.IOException;
import java.util.HashMap;
import ru.vtb.afsc.env.EnvHolder;
import ru.vtb.afsc.env.AppEnvHolder;

public class App2 {
    public static void main(String[] args) throws IOException {
        MgrProperties props = new MgrProperties("/Users/evg/_dev/java/prop/src/main/resources/config.properties");
        HashMap<String, String> listProps = props.getAll();


        AppEnvHolder env = new AppEnvHolder();

//        String[] propNames = env.getExtPropNames();
//
//        for (String s : propNames) {
//            System.out.println(s);
//        }

        final boolean success = env.init(listProps);


        System.out.println("-------------------- toString .... " + ", success init: " + success);
        System.out.println(env);
//        env.field();

    }



}
