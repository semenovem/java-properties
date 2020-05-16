package ru.vtb.afsc.env;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnvVariable {

    /**
     * Название переменной вне приложения
     */
    String name();

    /**
     * Значение по умолчанию
     */
    String defaultValue() default "";

    /**
     * Секретные данные, пароли etc закрыть звездочками значения таких полей при выводе в лог
     */
    boolean sensitive() default false;
}
