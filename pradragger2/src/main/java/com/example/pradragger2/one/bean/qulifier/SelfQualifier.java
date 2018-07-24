package com.example.pradragger2.one.bean.qulifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by TanJieXi on 2018/7/24.
 */
public class SelfQualifier {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RedCloth{}


    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BlueCloth{}



}
