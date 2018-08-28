package com.example.newblue.jumppart.bpart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newblue.R;
import com.example.newblue.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by leilei on 2018/2/27.
 */

public class BCRedactTableList extends AppCompatActivity {
    private BaseQuickAdapter<FamilyBean, BaseViewHolder> mAdapter;
    private List<FamilyBean> mList;
    //private DBManager dbManager = new DBManager(this);
    public static final String DataCode = "cdu";
    @BindView(R.id.follow_toolbar_title)
    TextView follow_toolbar_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.follow_save)
    RelativeLayout follow_save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity_bc_redact_list);
        ButterKnife.bind(this);
        initView();
        initSetData();
    }

    private void initView() {
        follow_toolbar_title.setText("B超历史");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        follow_save.setVisibility(View.GONE);
    }

    @OnClick({R.id.follow_re_back})
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.follow_re_back){
            finish();
        }
    }
    private void initSetData() {
        //mList = dbManager.getBCRedactTableList(UsersMod.get_id() + "", DataCode);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<FamilyBean, BaseViewHolder>(R.layout.follow_item_establish_list2, mList) {
            @Override
            protected void convert(BaseViewHolder holder, FamilyBean item) {
                try {
                    JSONObject jsonObject = new JSONObject(item.uname);
                    ImageView imageView = holder.getView(R.id.bc_imv);
                    if (!TextUtils.isEmpty(item.image)) {
                        JSONObject jsonObject1 = new JSONObject(item.image);
                        String[] array = jsonObject1.getString("file").split(",");
                        String url = "";
                        for (int i = 0; i < array.length; i++) {
                            url = array[0];
                            Log.e("显示url", url + "");
                        }
                        Glide.with(BCRedactTableList.this).load(url).override(800, 800)
                                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .centerCrop().error(R.drawable.image).into(imageView);
                    }
                    String exampart = jsonObject.optString("exampart");//选择部位
                    String normal = jsonObject.optString("normal");//是否正常
                    String jianyi = jsonObject.optString("jianyi");//建议
                    holder.setText(R.id.content_tv, exampart + "");
                    holder.setText(R.id.normal_tv, normal + "");
                    holder.setText(R.id.remark_tv, jianyi + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                holder.addOnClickListener(R.id.change_btn);
                holder.addOnClickListener(R.id.edt_btn);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FamilyBean familyBean = (FamilyBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.change_btn:
                        //dbManager.deleteBCRedactTableList(familyBean._id);
                        ToastUtil.showShortToast("删除成功!");
                        initSetData();
                        break;
                    case R.id.edt_btn:
                        showDialog3("备注", "请输入备注", familyBean._id, mList.get(position).uname);
                        break;

                }

            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FamilyBean familyBean = (FamilyBean) adapter.getItem(position);
                Intent intent = new Intent(BCRedactTableList.this, BCRedactActivity.class);
                try {
                    if (!TextUtils.isEmpty(familyBean.image)) {
                        JSONObject jsonObject = new JSONObject(familyBean.image);
                        intent.putExtra(BCRedactActivity.BCRedactActivity_FilePatch, jsonObject.getString("file"));
                        intent.putExtra(BCRedactActivity.BCRedactActivity_DATE, familyBean.uname);
                        startActivity(intent);
                    } else {
                        ToastUtil.showShortToast("无图片");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dbManager.closeDB();
        //CacheActivity.getInstance().removeActivity(this);
    }

    public void showDialog3(String mTitle, String mContent, final String _id, final String json) {
        new MaterialDialog.Builder(this)
                .title(mTitle)
//                .titleColorRes(R.color.red)
                .content(mContent)
//                .contentColorRes(R.color.red)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .positiveText("确定")
                .negativeText("取消")
                .input("请输入备注", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.toString().length() > 0) {
                            dialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                jsonObject.put("content", input.toString());
                               /* String date = "action=docdu&type=cdu&data=" + jsonObject.toString() + "&userid=" + UsersMod.get_id();
                                dbManager.updateBCRedactTableList(_id, date);*/
                                ToastUtil.showShortToast("修改成功!");
                                initSetData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtil.showShortToast("请输入备注!");
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
