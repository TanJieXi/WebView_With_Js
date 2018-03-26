package com.example.db.utils;

import com.example.db.App;
import com.example.db.bean.ShopBean;

import java.util.List;

/**
 * Created by TanJieXi on 2018/3/26.
 */

public class DaoUtils {

    /**
     * 添加数据，如果重复就覆盖
     * @param shop
     */
    public static void insertDB(ShopBean shop){
        App.getDaoInstant().getShopBeanDao().insertOrReplace(shop);
    }

    /**
     * 添加数据，如果重复就覆盖
     * @param shop
     */
    public static void insertDB(List<ShopBean> shop){
        App.getDaoInstant().getShopBeanDao().insertOrReplaceInTx(shop);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteDB(long id){
        App.getDaoInstant().getShopBeanDao().deleteByKey(id);
    }
    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateLove(ShopBean shop) {
        App.getDaoInstant().getShopBeanDao().update(shop);
    }

    /**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     */


    /**
     * 查询全部数据
     */
    public static List<ShopBean> queryAll() {
        return App.getDaoInstant().getShopBeanDao().loadAll();
    }


}
