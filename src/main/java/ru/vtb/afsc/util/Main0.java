package ru.vtb.afsc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main0 {

    public static void main(String[] args) {

        String str = "localhost:50001 localhost:50002";

        parse(str);

    }


    public Main0() {
    }

    private static void parse(String text) {
        Pattern pattern = Pattern.compile(".*?:\\d+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int start=matcher.start();
            int end=matcher.end();

            String[] substr = text.substring(start, end).split(":");


            System.out.println("Найдено совпадение " + substr[0] + " с "+ substr[1] + " по " + (end-1) + " позицию");
        }
    }

}
