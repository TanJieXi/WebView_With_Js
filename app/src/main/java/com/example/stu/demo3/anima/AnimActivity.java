package com.example.stu.demo3.anima;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.widget.TextView;

import com.example.stu.demo3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AnimActivity extends AppCompatActivity {
    @BindView(R.id.tv_new)
    TextView tv_text;
    @OnClick(R.id.tv_new) void finishAc(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        ButterKnife.bind(this);

        setupWindowAnimations();
    }



    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }
}
