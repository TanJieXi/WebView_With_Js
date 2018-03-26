package com.example.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.db.bean.ShopBean;
import com.example.db.utils.DaoUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_one,btn_two;
    private String[] names = {"张三","李四","王五","你好"};
    private List<ShopBean> datas = new ArrayList<>();
    private long i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one:
                ShopBean b;
                for(String n :names){
                    b = new ShopBean();
                    b.setName(n);
                    b.setId(i++);
                    datas.add(b);
                }
                DaoUtils.insertDB(datas);
                break;
            case R.id.btn_two:

                List<ShopBean> shopBeans = DaoUtils.queryAll();
                Log.i("sdafdasfgs",shopBeans.toString());

                break;
        }
    }
}
