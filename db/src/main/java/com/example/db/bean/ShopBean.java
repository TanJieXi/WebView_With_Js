package com.example.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TanJieXi on 2018/3/26.
 */

@Entity  //告诉这个对象是实体，只有被@Entity注释的Bean类才能被dao类操作
public class ShopBean {
    //标识为购物车列表
    public static final int TYPE_CART = 0x01;
    //表示为收藏列表
    public static final int TYPE_LOVE = 0x02;

    //不能用int
    @Id(autoincrement = true)
    private long id;

    //商品名称
    @Unique
    private String name;

    private String salename;

    //商品价格  @property 可以自定义字段名，外检不能使用
    @Property(nameInDb = "price")
    private String price;

    @Generated(hash = 1402258909)
    public ShopBean(long id, String name, String salename, String price) {
        this.id = id;
        this.name = name;
        this.salename = salename;
        this.price = price;
    }

    @Generated(hash = 748345971)
    public ShopBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShopBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salename='" + salename + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getSalename() {
        return this.salename;
    }

    public void setSalename(String salename) {
        this.salename = salename;
    }
}
