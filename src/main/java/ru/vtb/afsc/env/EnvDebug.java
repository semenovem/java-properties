package ru.vtb.afsc.env;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Дебаг для переменных окружения
 * Не должно быть в проде
 */
public class EnvDebug {

    /**
     * Показывает все использования переменных окружения
     * в формате {внешнее название переменой окружения}{переменная в коде}{значение переменной}
     */
    public static String showUse(final Class<?> clazz) {
        Set<Field> fields;

        try {
            fields = reflectionsScan(clazz);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException  e) {
            e.printStackTrace();

            return "Error";
        }

        String res = "";

        for(Field field: fields) {
            final EnvVariable annotate = field.getAnnotation(EnvVariable.class);

            try {
                field.setAccessible(true);

                res += "{" + annotate.name() + "}{" + field.getDeclaringClass().getName()
                    + "}{" + field.get(null) + "}\n";

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return "[\n" + res + "]";
    }


    /**
     * Используем метод поиска аннотированных полей из другого класса,
     * что бы  не дублировать код здесь
     */
    private static Set<Field> reflectionsScan(final Class<?> clazz)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class envEngineClass = Class.forName(EnvEngine.class.getName());
        Class[] paramTypes = new Class[] {Class.class};

        Method scan = envEngineClass.getDeclaredMethod("reflectionsScan", paramTypes);
        scan.setAccessible(true);

        return (Set<Field>) scan.invoke(null, clazz);
    }
}
