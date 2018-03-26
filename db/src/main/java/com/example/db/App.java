package com.example.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.db.bean.DaoMaster;
import com.example.db.bean.DaoSession;

/**
 * Created by TanJieXi on 2018/3/26.
 */

public class App extends Application {

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDataBase();
    }

    /**
     * 配置数据库
     */
    private void setupDataBase() {
        //创建数据库shop.db  DevOpenHelper 创建SQLite数据库的SQLiteOpenHelper的具体实现
        //DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        MyOpenHelper devOpenHelper = new MyOpenHelper(this,"shop.db");
        // 获取可写数据库
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        //获取数据库对象 DaoMster作为数据库对象，用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者  ，DaoSession管理所有的Dao对象，Dao对象中存在着增删改查等
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant(){
        return mDaoSession;
    }


}
