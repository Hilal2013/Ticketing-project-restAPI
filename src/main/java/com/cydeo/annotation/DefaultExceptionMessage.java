package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//this annotation i will put with method
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultExceptionMessage {
//how you can create annotation
    String defaultMessage() default "";
    //Im trying to create one annotation//I can put this one top of the method//if there is exception happens show
    //whatever this annotation is holding
    //this is default if you want to customize this message go to dtoDefaultexceptionmessage

}