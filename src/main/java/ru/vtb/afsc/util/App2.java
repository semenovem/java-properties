
package ru.vtb.afsc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import ru.vtb.afsc.env.EnvDebug;
import ru.vtb.afsc.env.EnvVariable;
import ru.vtb.afsc.env.EnvEngine;


public class App2 {

    @EnvVariable(name = "ENV_HOSTNAME_STRING")
    private static String testVariable;

    @EnvVariable(name = "ENV_HOSTNAME_INT")
    private static int testVariableInt;



    public static void main(String[] args) throws IOException, IllegalAccessException {

        System.out.println(EnvDebug.showUse(App2.class));



        EnvEngine.set(System.getenv(), App2.class);

        MgrProperties props = new MgrProperties("/Users/evg/_dev/java/prop/src/main/resources/config.properties");
        Map<String, String> listProps = props.getAll();

        EnvEngine.set(listProps, App2.class);


        System.out.println(EnvDebug.showUse(App2.class));



        System.out.println("-------------------");
        System.out.println("-------------------");

        ArrayList<String> res = EnvEngine.verify(App2.class);

        System.out.println(res);





//        AppEnvHolder env = new AppEnvHolder();

//        String[] propNames = env.getExtPropNames();
//
//        for (String s : propNames) {
//            System.out.println(s);
//        }

//        Map<String, String> sys = System.getenv();
//        env.setValues(sys);
//        env.setValues(listProps);




//        ArrayList<String> results = env.verify();
//        for (String s: results) {
//            System.out.println(s);
//        }
    }
}
