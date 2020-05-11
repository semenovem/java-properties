package ru.vtb.afsc.env;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    }

    public boolean init(HashMap<String, String> properties) {
        ArrayList<Field> fields = getFields();
        boolean success = true;

        for (Field field : fields) {
            final Spec spec = field.getAnnotation(Spec.class);
            boolean initialized = false;

            String extName = spec.extName();

            if (properties.containsKey(extName)) {
                initialized = true;
                setValueToField(field, properties.get(extName));
            } else {
                String sysEnvValue = System.getenv(extName);

                if (sysEnvValue != null) {
                    initialized = true;
                    setValueToField(field, sysEnvValue);
                }
            }

            if (!spec.optional() && !initialized) {
                success = false;
                // there logger
                System.out.println("!!!!!!! ERROR: " + field.getName() +  " обязательное поле не инициализировано");
            }
        }

        return success;
    }

    @Override
    public String toString() {
        final ArrayList<Field> fields = getFields();
        final String[] strings = new String[fields.size()];

        for (int i = 0; i < strings.length; i++) {
            final Field field = fields.get(i);
            try {
                strings[i] = (i == 0 ? "" : "\n") + field.getName() + "=" + field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Arrays.toString(strings);
    }

    private String[] getExtPropNames() {
        ArrayList<Field> fields = getFields();
        String[] propNames = new String[fields.size()];

        for (int i = 0; i < propNames.length; i++) {
            propNames[i] = fields.get(i).getAnnotation(Spec.class).extName();
        }

        return propNames;
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

    private void setValueToField(Field field, String value) {
        try {
            field.setAccessible(true);

            try {
                if (field.get(this) instanceof Long) {
                    field.set(this, Long.parseLong(value));
                } else if (field.get(this) instanceof Integer) {
                    field.set(this, Integer.parseInt(value));
                } else if (field.get(this) instanceof String) {
                    field.set(this, value);
                } else if (field.get(this) instanceof Boolean) {
                    field.set(this, Boolean.parseBoolean(value));
                } else {
                    // there logger
                    System.out.println("ERROR не поддерживаем такой тип значения");
                }
            } catch (Exception ex) {
                System.out.println("ERROR при изменении уровня доступа к полю");
                ex.printStackTrace();
            }

            field.setAccessible(false);
        } catch (Exception ex) {
            System.out.println("!!!!!!! ERROR" + " не поддерживаем такой тип значения");
            ex.printStackTrace();
        }
    }
}
