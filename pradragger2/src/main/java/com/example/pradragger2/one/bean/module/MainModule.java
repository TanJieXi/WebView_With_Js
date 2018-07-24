package com.example.pradragger2.one.bean.module;

import com.example.pradragger2.one.bean.bean.Cloth;
import com.example.pradragger2.one.bean.bean.Coloths;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TanJieXi on 2018/7/24.
 */
@Module
public class MainModule {


   /* @Provides
    @SelfQualifier.RedCloth
    public Cloth getCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return  cloth;
    }

    @Provides
    @SelfQualifier.BlueCloth
    public Cloth getBlueCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return  cloth;
    }*/


    @Provides
    @Singleton
    public Cloth getCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return  cloth;
    }

    @Provides
    public Coloths getClother(Cloth cloth){
        return new Coloths(cloth);
    }

}
