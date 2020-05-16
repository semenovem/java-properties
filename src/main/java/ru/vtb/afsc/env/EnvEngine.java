package ru.vtb.afsc.env;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Механизм установки значений переменных окружения, в поля, аннотированные @EnvVariable
 * эти поля должны быть static и не быть final
 *
 * что бы убрать предупреждение линтеров, аннотировать дополнительно:
 * ...........
 */
public class EnvEngine {
    private EnvEngine() {}

    /**
     * Список переменных окружения, которые были проинициализированы ("снаружи" переданы данные)
     * Это вычисляется при разборе аргумента `map`, переданного в метод `set`.
     */
    private static final HashSet<String> initializedFields = new HashSet<>();

    private static boolean isInitializedDefaultValues = false;

    public static void set(final Map<String, String> map, final Class<?> clazz)
        throws IllegalAccessException {
        final Set<Field> fields = reflectionsScan(clazz);

        for (final Field field: fields) {
            final EnvVariable annotate = field.getAnnotation(EnvVariable.class);
            final String defaultValue = annotate.defaultValue();
            final String extNameVariable = annotate.name();

            if (!map.containsKey(extNameVariable)) {
                if (!defaultValue.isEmpty()) {
                    setValueToField(field, defaultValue);
                }

                continue;
            }

            initializedFields.add(extNameVariable);

            final String value = map.get(extNameVariable);

            setValueToField(field, value);
        }

        if (!isInitializedDefaultValues) {
            isInitializedDefaultValues = true;
        }
    }

    public static ArrayList<String> verify(final Class<?> clazz) {
        final Set<Field> fields = reflectionsScan(clazz);
        final ArrayList<String> res = new ArrayList<>();

        for (final Field field: fields) {
            final EnvVariable annotate = field.getAnnotation(EnvVariable.class);
            final String defaultValue = annotate.defaultValue();
            if (!defaultValue.isEmpty()) {
                continue;
            }

            final String name = annotate.name();

            if (!initializedFields.contains(name)) {
                res.add(name);
            }
        }

        return res;
    }

    private static Set<Field> reflectionsScan(final Class<?> clazz) {
        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(clazz.getPackage().getName()))
                .setScanners(new FieldAnnotationsScanner()));

        return reflections.getFieldsAnnotatedWith(EnvVariable.class);
    }


    private static void setValueToField(final Field field, final String value)
        throws IllegalAccessException, IllegalArgumentException {
        try {
            field.setAccessible(true);

            try {
                switch (field.getType().getName()) {
                    case "java.lang.String":
                        field.set(null, value);
                        break;

                    case "java.lang.Integer":
                    case "int":
                        field.setInt(null, Integer.parseInt(value));
                        break;

                    case "java.lang.Long":
                    case "long":
                        field.set(null, Long.parseLong(value));
                        break;

                    case "java.lang.Boolean":
                    case "boolean":
                        field.set(null, Boolean.parseBoolean(value));
                        break;

                    case "java.lang.Byte":
                    case "byte":
                        field.set(null, Byte.parseByte(value));
                        break;

                    case "java.lang.Short":
                    case "short":
                        field.set(null, Short.parseShort(value));
                        break;

                    case "java.lang.Float":
                    case "float":
                        field.set(null, Float.parseFloat(value));
                        break;

                    case "java.lang.Double":
                    case "double":
                        field.set(null, Double.parseDouble(value));
                        break;

                    case "java.lang.Character":
                    case "char":
                        field.setChar(null, value.charAt(0));
                        break;

                    default:
                        System.out.println("ERROR не поддерживаем такой тип");

                        throw new IllegalArgumentException("ERROR не поддерживаем такой тип");
                }
            } catch (final IllegalAccessException ex) {
                System.out.println("ERROR нельзя установить значение в поле");
                ex.printStackTrace();

                throw ex;
            }
        } catch (final IllegalAccessException ex) {
            System.out.println("ERROR при доступе к полю");
            ex.printStackTrace();

            throw ex;
        }
    }
}
