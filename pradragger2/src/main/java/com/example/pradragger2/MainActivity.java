package com.example.pradragger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pradragger2.one.bean.bean.Cloth;
import com.example.pradragger2.one.bean.bean.Coloths;
import com.example.pradragger2.one.bean.bean.Shoe;
import com.example.pradragger2.one.bean.component.DaggerMainComponent;
import com.example.pradragger2.one.bean.module.MainModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private TextView tv_text;
    @Inject
    Cloth cloth;
    @Inject
    Coloths cloths;
    @Inject
    Shoe shoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text = findViewById(R.id.tv_text);

        DaggerMainComponent.builder().mainModule(new MainModule()).build().inject(this);
        boolean isEqual = cloth == cloths.getColor();
        tv_text.setText(isEqual + "");
    }
}
