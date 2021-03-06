package cn.gotom.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 注释
 * 
 * @author <mailto:peixere@qq.com>shaoguo pei</mailto>
 * 
 * @version 20130529
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Description
{
    String value() default "";
    
    Class<?> clazz() default Description.class;
}
