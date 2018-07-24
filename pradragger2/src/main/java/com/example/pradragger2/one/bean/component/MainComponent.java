package com.example.pradragger2.one.bean.component;

import com.example.pradragger2.MainActivity;
import com.example.pradragger2.one.bean.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by TanJieXi on 2018/7/24.
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
