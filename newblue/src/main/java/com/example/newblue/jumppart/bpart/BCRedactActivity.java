package com.example.newblue.jumppart.bpart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newblue.R;
import com.example.newblue.utils.ToastUtil;
import com.jakewharton.rxbinding2.view.RxView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by leilei on 2018/2/27.
 */

public class BCRedactActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BCRedactActivity_FilePatch = "BCRedactActivity_FilePatch";
    public static final String BCRedactActivity_DATE = "BCRedactActivity_DATE";
    private static final String TAG = "BCRedactActivity";
    private RecyclerView recyclerView;
    private List<String> mList;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    //private DBManager dbManager = new DBManager(this);
    public static final String DataCode = "cdu";
    private TextView show_tv;
    private RadioGroup radiogp;
    private Button table_type, history_btn, result_btn;
    private EditText remark_edt;
    private Integer[] mIntegers = new Integer[]{};
    private String choice_part = "", is_normal = "";
    //private Net MyNet = new Net(this, this);
    private RadioButton radio1, radio2;
    @BindView(R.id.follow_toolbar_title)
    TextView follow_toolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity_bc_redact);
        ButterKnife.bind(this);
        initView();
        initSetDate();
    }

    @SuppressLint("CheckResult")
    private void initView() {
        follow_toolbar_title.setText("B超检测报告");
        mList = new ArrayList<>();
        show_tv = findViewById(R.id.show_tv);
        recyclerView = findViewById(R.id.recyclerView);
        radiogp = findViewById(R.id.radiogp);
        table_type = findViewById(R.id.table_type);
        history_btn = findViewById(R.id.history_btn);
        result_btn = findViewById(R.id.result_btn);
        remark_edt = findViewById(R.id.remark_edt);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radiogp.setOnCheckedChangeListener(listener);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(6, getResources().getDimensionPixelSize(R.dimen.public_family_recycle_item), true));
        RxView.clicks(table_type)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        SingleChoice2(table_type.getText().toString());
                    }
                });
        RxView.clicks(result_btn)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        showDialog();
                    }
                });
    }


    private void initSetDate() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String[] array = bundle.getString(BCRedactActivity_FilePatch).split(",");
            for (int i = 0; i < array.length; i++) {
                if (!TextUtils.isEmpty(array[i])) {
                    mList.add(array[i]);
                }
            }
            String mdate = bundle.getString(BCRedactActivity_DATE);
            if (!TextUtils.isEmpty(mdate)) {
                history_btn.setVisibility(View.GONE);
                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(mdate);
                    choice_part = jsonObject2.optString("exampart");//选择部位
                    String normal = jsonObject2.optString("normal");//是否正常
                    String jianyi = jsonObject2.optString("jianyi");//建议
                    table_type.setText(choice_part);
                    if (normal.equals("正常")) {
                        radio1.setChecked(true);
                    } else {
                        radio2.setChecked(true);
                    }
                    remark_edt.setText(jianyi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            mList = dbManager.getBCRedactTableList(UsersMod.get_id() + "", DataCode);
            if (mList.size() > 0) {
                show_tv.setVisibility(View.GONE);
            }
            recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
            recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.follow_item_establishing, mList) {
                @Override
                protected void convert(BaseViewHolder holder, String item) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 6;
                    Bitmap bitmap = BitmapFactory.decodeFile(item, options);
                    holder.setImageBitmap(R.id.item_establishing_imageView, bitmap);
                    holder.addOnClickListener(R.id.delete_imageview);
                }
            });
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String url = (String) adapter.getItem(position);
                    Intent intentBCRedactActivityImg = new Intent(BCRedactActivity.this, BCRedactActivityImg.class);
                    intentBCRedactActivityImg.putExtra(BCRedactActivityImg.EstablishingTableImage_FilePatch, url);
                    startActivity(intentBCRedactActivityImg);
                }
            });
            mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mList.size() > 0) {
                        mList.remove(position);
                        mAdapter.setNewData(mList);
                    }
                }
            });
        } else {
            ToastUtil.showShortToast("无B超图片数据!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dbManager.closeDB();
        //CacheActivity.getInstance().removeActivity(this);
    }


    @OnClick({R.id.follow_re_back, R.id.follow_save, R.id.history_btn})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.follow_re_back) {
            finish();
        } else if (id == R.id.follow_save) {
            Save_All();
        } else if (id == R.id.history_btn) {
            startActivity(new Intent(BCRedactActivity.this, BCRedactTableList.class));
        }
    }

    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup aroup, int checkId) {
            // TODO Auto-generated method stub
            switch (checkId) {
                case R.id.radio1:
                    is_normal = "正常";
                    break;
                case R.id.radio2:
                    is_normal = "异常";
                    break;

                default:
                    break;
            }
        }
    };

    private void SingleChoice2(String mdata) {
        if (!mdata.contains("请选择")) {
            String[] mValue = mdata.split(",");
            mIntegers = new Integer[mValue.length];
            for (int i = 0; i < mValue.length; i++) {
                String[] mDatasub = mValue[i].split("\\.");
                if (mDatasub.length > 0) {
                    mIntegers[i] = Integer.valueOf(mDatasub[0]) - 1;
                }
            }
        }
        String[] array = {"1.腹部", "2.腰部", "3.背部"};
        new MaterialDialog.Builder(this)
                .title("请选择：")
                .items(array)
                .canceledOnTouchOutside(false)
                .autoDismiss(false)
                .itemsCallbackMultiChoice(mIntegers, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder builder1 = new StringBuilder();
                        StringBuilder builder2 = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) builder1.append(",");
                            builder1.append(which[i] + 1);
                        }
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) builder2.append(",");
                            builder2.append(text[i]);
                        }
//                        ToastUtil.showShortToast(builder1.toString() + "     " + builder2.toString());
                        if (builder1.toString().length() > 0 && builder2.toString().length() > 0) {
                            dialog.dismiss();
                            table_type.setText(builder2.toString());
                            choice_part = builder2.toString();
                        } else {
                            ToastUtil.showShortToast("类型至少需要选择一项!");
                        }
                        return true;
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void Save_All() {

        if (mList.size() <= 0) {
            ToastUtil.showShortToast("图片不能为空!");
            return;
        }
        if (mList.size() > 20) {
            ToastUtil.showShortToast("上传图片不能多余20张");
            return;
        }
        if (choice_part.equals("")) {
            ToastUtil.showShortToast("请选择检测部位!");
            return;
        }
        if (is_normal.equals("")) {
            ToastUtil.showShortToast("请选择是否正常!");
            return;
        }

        Log.e("mList长度", mList.size() + "==");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mList.size(); i++) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(mList.get(i));
        }
        String remark = remark_edt.getText().toString();//建议
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        try {
            int hid = 3;
            int doctor_userid = 100;
            String age = "0";
               /* if (!TextUtils.isEmpty(UsersMod.get_uAge()) && !UsersMod.get_uAge().contains("-1")) {
                    age = UsersMod.get_uAge();
                }*/
            age = "21";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonObject.put("file", stringBuilder.toString());
            jsonObject2.put("hid", hid + "");
            jsonObject2.put("userid", "3212");
            jsonObject2.put("exampart", choice_part); //选择部位
            jsonObject2.put("normal", is_normal); //是否正常
            jsonObject2.put("jianyi", remark); //建议
            jsonObject2.put("sex", "男"); //性别
            jsonObject2.put("name", "大刘磊"); //姓名
            jsonObject2.put("age", age); //年龄
            jsonObject2.put("regdatetime", formatter.format(new Date())); //检查日期
            jsonObject2.put("examdatetime", formatter.format(new Date())); //检查时间
            jsonObject2.put("reportdatetime", formatter.format(new Date())); //报告时间
            jsonObject2.put("jianchayisheng", doctor_userid); //检查医生userid
            jsonObject2.put("shenqingyisheng", doctor_userid); //申请医生userid
            Log.e("保存的数据11==", "B超图片保存地址:" + stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("保存拍照数据失败=", "保存拍照数据失败");
        }
        //String pushStr = "uid=" + UsersMod.get_id() + "&data=" + jsonObject2.toString();
        //Log.e("保存的json", pushStr);

        Log.i("dsfdsgsdfg", jsonObject2.toString());
        //MyNet.Post2(Net.SERVLET_ADDBMODE, DataCode, pushStr, 1, jsonObject.toString());

    }


    private void showDialog() {
        String[] str = {"肝脏：肝脏形态正常，边缘规则，包膜光整，肝实质内回声均质，纹理清楚，肝脏超声未见明显异常。",
                "肝脏:肝脏大小、形态正常，包膜光整，肝内血管走行较清晰，光点分布尚均匀，其内未见明显异常光团。",
                "胆囊：胆囊大小、形态正常，囊壁光整，囊腔内透声好，胆总管无扩张。",
                "胆囊：大小、形态正常， 胆囊壁均勻，腔内未见明显结石、息肉。肝内胆管无扩张。",
                "胰腺：胰腺大小、形态正常，边缘规整，内部回声均匀，胰管未见扩张",
                "胰脏：大小形态正常。胰实质回声均质，前壁光滑。",
                "脾脏：脾脏大小、形态正常，包膜光整，内光点均匀。",
                "脾脏：脾脏厚度正常。",
                "双肾：大小形态正常，位置正 常，包膜光整，双肾窦未见结石、积液，双肾超声来见明显异常。双侧输尿管不扩张。",
                "左肾:左肾大小、形态正常，包膜光滑，肾实质回声均匀，集合系统未见明显分离",
                "右肾:左肾大小、形态正常，包膜光滑，肾实质回声均匀，集合系统未见明显分离"
        };
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            if (!list.contains(str[i])) {
                list.add(str[i]);
            }
        }

        String[] newStr = list.toArray(new String[1]);
        final StringBuilder builder1 = new StringBuilder();
        new MaterialDialog.Builder(this)
                .title("请选择：")
                .items(newStr)
                .canceledOnTouchOutside(false)
                .autoDismiss(false)
                .itemsCallbackMultiChoice(new Integer[]{-1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        for (int i = 0; i < text.length; i++) {
//                            if (i > 0) builder1.append(",");
                            builder1.append(text[i] + "");
                        }
                        if (builder1.toString().length() > 0) {
                            dialog.dismiss();
                            remark_edt.setText(builder1.toString());
                        } else {
                            ToastUtil.showShortToast("类型至少需要选择一项!");
                        }
                        return false;
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
