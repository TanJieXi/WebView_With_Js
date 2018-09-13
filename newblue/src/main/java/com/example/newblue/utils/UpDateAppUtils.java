package com.example.newblue.utils;

import android.app.Activity;
import android.os.Environment;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by TanJieXi on 2018/8/30.
 */
public class UpDateAppUtils {
    private static volatile UpDateAppUtils sUpDateAppUtils = null;

    private UpDateAppUtils() {

    }

    public static UpDateAppUtils getInstance() {
        if (sUpDateAppUtils == null) {
            synchronized (UpDateAppUtils.class) {
                if (sUpDateAppUtils == null) {
                    sUpDateAppUtils = new UpDateAppUtils();
                }
            }
        }
        return sUpDateAppUtils;
    }

    public void upDataApp(final Activity activity , String url) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.lianyi.app" + File.separator + "app";
        if (NetworkUtils.Available()) {
            new UpdateAppManager
                    .Builder()
                    //当前Activity
                    .setActivity(activity)
                    //更新地址
                    .setUpdateUrl(url)
                    //实现httpManager接口的对象
                    .setHttpManager(new OkGoUpdateHttpUtil())
                    //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
                    .hideDialogOnDownloading(false)
                    //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                    .setTargetPath(path)
                    .build()
                    .checkNewApp(new UpdateCallback() {
                        @Override
                        protected UpdateAppBean parseJson(String json) {
                            UpdateAppBean updateAppBean = new UpdateAppBean();
                            int vercode = AndroidUtils.getVersionCode(activity);
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                String update = "No";
                                if (jsonObject.getLong("pad_code_new") > vercode) {
                                    update = "Yes";
                                }
                                updateAppBean
                                        //（必须）是否更新,No
                                        .setUpdate(update)
                                        //（必须）新版本号
                                        .setNewVersion(jsonObject.optString("pad_name_new"))
                                        //（必须）下载地址
                                        .setApkFileUrl(jsonObject.optString("pad_url_new"))
                                        //（必须）更新内容
                                        .setUpdateLog(jsonObject.optString("pad_cont_new"))
                                        //大小，不设置不显示大小，可以不设置
//                                            .setTargetSize(jsonObject.optString("target_size"))
                                        //是否强制更新，可以不设置
                                        .setConstraint(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return updateAppBean;
                        }

                        @Override
                        protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                            updateAppManager.showDialogFragment();
                        }



                        @Override
                        public void onBefore() {
                            CProgressDialogUtils.showProgressDialog(activity);
                        }



                        @Override
                        public void onAfter() {
                            CProgressDialogUtils.cancelProgressDialog(activity);
                        }


                        @Override
                        public void noNewApp() {
                            ToastUtil.showShortToast("没有新版本");
                        }
                    });
        } else {
            ToastUtil.showShortToast("请连接网络后重试");
        }
    }

}
