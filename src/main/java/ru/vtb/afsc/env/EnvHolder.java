package ru.vtb.afsc.env;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * Использование: 1. Наследовать класс 2. В нем объявить переменные среды \@Spec(extName =
 * "ENV_HOSTNAME", optional = true, sensitive = false) public static final String HOSTNAME = null;
 * 3. Методом setValues установить значения. Можно вызывать несколько раз. 4. Для проверки, все ли
 * обязательные переменные были установлены - verify
 *
 * @author semenov esemenov@vtb.ru
 */
public class EnvHolder {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Spec {

        /**
         * Название переменной вне приложения
         */
        String extName();

        /**
         * true - не обязательно передавать значение через переменную окружения если false (по
         * умолчанию) выкинем ошибку
         */
        boolean optional() default false;

        /**
         * Секретные данные, пароли etc закрыть звездочками значения таких полей при выводе в лог
         */
        boolean sensitive() default false;
    }

    private final HashSet<String> initializedExtFields = new HashSet<>();

    public void setValues(final Map<String, String> values) {
        final ArrayList<Field> fields = getFields();

        for (final Field field : fields) {
            final Spec spec = field.getAnnotation(Spec.class);
            final String extName = spec.extName();

            if (values.containsKey(extName)) {
                setValueToField(field, values.get(extName));
                initializedExtFields.add(extName);
            }
        }
    }

    public ArrayList<String> verify() {
        final ArrayList<String> results = new ArrayList<>();
        final ArrayList<Field> fields = getFields();

        boolean optional;
        for (Field field : fields) {
            final Spec spec = field.getAnnotation(Spec.class);
            final String extName = spec.extName();
            optional = spec.optional();

            if (!optional && !initializedExtFields.contains(extName)) {
                results.add("Не установлена обязательная переменная окружения: " + extName);
            }
        }
        return results;
    }

    @Override
    public String toString() {
        final ArrayList<Field> fields = getFields();
        final String[] strings = new String[fields.size()];
        boolean sens;

        for (int i = 0; i < strings.length; i++) {
            final Field field = fields.get(i);
            try {
                sens = field.getAnnotation(Spec.class).sensitive();

                strings[i] = (i == 0 ? "" : "\n") + field.getName() + "=" + (sens ? "******"
                    : field.get(this));
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Arrays.toString(strings);
    }

    private ArrayList<Field> getFields() {
        final Field[] fields = getClass().getFields();
        final ArrayList<Field> resultFields = new ArrayList<>();

        for (Field field : fields) {
            final Spec d = field.getAnnotation(Spec.class);
            if (d != null) {
                resultFields.add(field);
            }
        }
        return resultFields;
    }

    private void setValueToField(final Field field, final String value) {
        try {
            field.setAccessible(true);

            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

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
                    // there logger
                    System.out.println("ERROR не поддерживаем такой тип значения");
                }
            } catch (final RuntimeException ex) {
                System.out.println("ERROR при изменении значения поля");
                ex.printStackTrace();
            }

            field.setAccessible(false);
        } catch (final IllegalAccessException | NoSuchFieldException ex) {
            System.out.println("ERROR при изменении уровня доступа к полю");
            ex.printStackTrace();
        }
    }
}
