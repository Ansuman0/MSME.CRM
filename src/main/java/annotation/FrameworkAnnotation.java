package annotation;

import enums.CategoryType;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Framework Annotation is user built vt. Annotation which is annotated on
 * top of test methods to log the author details and category details to the
 * extent report.
 * Runtime retention value indicate that this com.vt.annotation will be
 * available at run time for reflection operations.
 */

@Retention(RUNTIME)
@Target(METHOD)
@Documented
public @interface FrameworkAnnotation {

    public String[] author();

    public CategoryType[] category();

}