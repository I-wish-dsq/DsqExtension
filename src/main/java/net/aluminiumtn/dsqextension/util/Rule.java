package net.aluminiumtn.dsqextension.util;

import net.aluminiumtn.dsqextension.enums.RuleTags;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Rule {
    String desc() default "Описание отсутствует";
    boolean defaultValue() default false;
    RuleTags[] tags() default {};
}