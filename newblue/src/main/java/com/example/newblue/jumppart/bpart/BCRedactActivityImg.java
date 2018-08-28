package com.example.newblue.jumppart.bpart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newblue.R;
import com.example.newblue.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leilei on 2018/2/28.
 * B超图片查看器
 */

public class BCRedactActivityImg extends AppCompatActivity {
    public static final String EstablishingTableImage_FilePatch = "BCRedactActivityImg_FilePatch";
    private SamplePagerAdapter SamplePagerAdapter;
    private List<String> mList;
    private String FilePatch;
    @BindView(R.id.follow_toolbar_title)
    TextView title;
    @BindView(R.id.view_pager)
    HackyViewPager viewPager;
    @BindView(R.id.follow_save)
    RelativeLayout follow_save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity_bc_redact_img);
        ButterKnife.bind(this);
        initView();
        initSetDate();
    }
    @OnClick({R.id.follow_re_back})
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.follow_re_back){
            finish();
        }
    }
    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("图片查看");
        follow_save.setVisibility(View.GONE);
    }

    private void initSetDate() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FilePatch = bundle.getString(EstablishingTableImage_FilePatch);
            if (!TextUtils.isEmpty(FilePatch) && FilePatch.length() > 5) {
                mList = new ArrayList<>();
                String[] array = FilePatch.split(",");
                for (int i = 0; i < array.length; i++) {
                    mList.add(array[i]);
                }
                SamplePagerAdapter = new SamplePagerAdapter(mList);
                viewPager.setAdapter(SamplePagerAdapter);
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
