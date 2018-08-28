package com.healon.up20user.UI;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.newblue.App;
import com.example.newblue.R;
import com.example.newblue.jumppart.bpart.BCRedactActivity;
import com.example.newblue.utils.ToastUtil;
import com.healon.up20user.Base.BaseActivity;
import com.healon.up20user.Broud.Receiver;
import com.healon.up20user.Constants;
import com.healon.up20user.Dialog.DialogChooseItem;
import com.healon.up20user.Dialog.DialogNote;
import com.healon.up20user.Dialog.DialogUSBPermissionsWarning;
import com.healon.up20user.Dialog.PopWindowSetImage;
import com.healon.up20user.Dialog.PopWindowSetting;
import com.healon.up20user.Utils.UtilsBitmap;
import com.healon.up20user.Utils.UtilsDateTime;
import com.healon.up20user.Utils.UtilsProgress;
import com.healon.up20user.Utils.UtilsSDCard;
import com.healon.up20user.Utils.UtilsUsbAuthority;
import com.healon.up20user.View.ViewGrayBar;
import com.healon.up20user.View.ViewPopSeekBar;
import com.healon.up20user.View.ViewSample;
import com.healon.up20user.View.ViewSuspend;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;

import api.HS_ApiManager;
import api.hard.HS_Freeze;
import api.image.HS_Part;
import api.image.HS_Zoom;
import api.measure.HS_MeasureRemove;
import api.os.HS_Cine;
import api.os.HS_Connect;
import api.os.HS_Image;
import api.os.HS_Mode;
import api_extend.hard.HS_ConnectEx;

public class MainActivity extends BaseActivity implements Handler.Callback, ViewSample.CallbackProgress,
        SeekBar.OnSeekBarChangeListener, PopWindowSetImage.ZoomCollback{
    private final int CINE_SAVE_PROGRESS = 1000;
    private final int CINE_SAVE_FAIL = 2000;
    private LinearLayout m_llMainContent;
    //顶部控件
    private LinearLayout m_llOpenMenu;
    private TextView m_tvPatientId, m_tvName;
    //图片区域控件
    //private ViewSampleSurface m_viewSample;
    private ViewSample m_viewSample;
    private ViewSuspend m_ivDelete;
    private ImageButton m_ivzomm;
    private TextView m_viewMeasureResult;
    private ViewGrayBar m_viewGrayBar;
    private View m_viewImageContent;
    //底部按钮控件
    private ViewPopSeekBar m_seekBar;
    private ImageView m_ivPrevious, m_ivForward, m_ivPlay, m_ivSaveMovie;
    private ImageButton m_imgBtnCheckBodyParts, m_imgBtnChangeMode, m_imgBtnFreeze, m_imgBtnMeasure, m_imgBtnSavePic, imgbtn_checkPoint2;

    private Receiver receiver;
    private Handler m_handler;
    private HS_ConnectEx m_connectEx = null;
    //是否可以测量
    private boolean m_bMeasuring = false;
    private boolean mReceiverTag = false;
    private PopWindowSetImage popWindowSetImage;
    private HS_Image m_hsImage;

    private String finalFile;
    private String content = "";
    private StringBuilder stringBuilder = new StringBuilder();

    private String uName = "小花花";  //名字
    private String uNUidCard = "510181199312070989";

    static {
        try {
            // hs-lib
            System.loadLibrary("ffmpeg_java3");
            System.loadLibrary("opencv_java3");
            System.loadLibrary("HS_USLib");
            System.loadLibrary("HS_USApi");
        } catch (Exception e) {
            Log.e("load so", e.getMessage());
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.follow_activity_main_u20;
    }

    @Override
    public void beforeInitView() {
        new HS_ApiManager().alloc();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.READ_EXTERNAL_STORAGE)
                    .send();
        }
        m_handler = new Handler(this);
    }

    /**
     * 回调权限申请是否成功
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的 AndPermission 自动完成。
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {//允许
            connect();
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {//拒绝
            finish();
        }
    };


    @Override
    public void initView() {
        m_llMainContent = (LinearLayout) findViewById(R.id.mainContent);
        //CacheActivity.getInstance().addActivity(MainActivity.this);
        //顶部控件
        {
            m_llOpenMenu = (LinearLayout) findViewById(R.id.ll_openMenu);
            m_llOpenMenu.setOnClickListener(this);
            m_tvPatientId = findViewByIdNoCast(R.id.tv_main_id);
            m_tvName = findViewByIdNoCast(R.id.tv_name);
            m_tvName.setText(uName);
            m_tvPatientId.setText(uNUidCard);
        }
        //底部按钮控件
        {
            m_ivPrevious = (ImageView) findViewById(R.id.iv_previous);
            m_ivForward = (ImageView) findViewById(R.id.iv_forward);
            m_seekBar = findViewByIdNoCast(R.id.skb_play_progress);
            m_seekBar.setEnabled(false);
            m_seekBar.setOnSeekBarChangeListener(this);
            m_ivPlay = (ImageView) findViewById(R.id.iv_play);
            m_ivSaveMovie = (ImageView) findViewById(R.id.iv_save);
            m_imgBtnCheckBodyParts = findViewByIdNoCast(R.id.imgbtn_checkPoint);
            imgbtn_checkPoint2 = findViewByIdNoCast(R.id.imgbtn_checkPoint2);
            m_imgBtnChangeMode = findViewByIdNoCast(R.id.imgbtn_changeMode);
            m_imgBtnFreeze = findViewByIdNoCast(R.id.imgbtn_freeze);
            m_imgBtnMeasure = findViewByIdNoCast(R.id.imgbtn_measure);
//            imgbtn_measure2 = findViewByIdNoCast(R.id.imgbtn_measure2);
            m_imgBtnSavePic = findViewByIdNoCast(R.id.imgbtn_savePic);
            m_ivSaveMovie.setVisibility(View.GONE);
        }

        //图像显示区域控件
        {
            m_viewImageContent = findViewByIdNoCast(R.id.view_imageContentBg);
            m_viewImageContent.setOnClickListener(this);
            m_viewGrayBar = findViewByIdNoCast(R.id.gray_bar_view);
            m_viewMeasureResult = (TextView) findViewById(R.id.tv_showValue);
            m_ivDelete = findViewByIdNoCast(R.id.iv_delete);
            m_ivzomm = findViewByIdNoCast(R.id.iv_zoom);
            m_ivDelete.setOnClickListener(this);
            m_ivzomm.setOnClickListener(this);
            m_viewSample = findViewByIdNoCast(R.id.img_mainview);
            m_viewSample.setSeekBar(m_seekBar);
            m_viewSample.setCallbackProgress(this);
            m_hsImage = new HS_Image();
            m_hsImage.setAttachCallbackRefresh(this, "onCallBackRefresh");
        }
        imgbtn_checkPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stringBuilder.append("/storage/emulated/0/znjtys_img/BC_IMAGE/1517903968185.png,/storage/emulated/0/znjtys_img/BC_IMAGE/1517903968185.png,/storage/emulated/0/znjtys_img/BC_IMAGE/1517904921086.png");
                //点击了
                Intent intentBCRedactActivity = new Intent(MainActivity.this, BCRedactActivity.class);
                intentBCRedactActivity.putExtra(BCRedactActivity.BCRedactActivity_FilePatch, stringBuilder.toString());
                startActivity(intentBCRedactActivity);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        new HS_Freeze().setFreeze();
        if (m_connectEx != null) m_connectEx.clean();
        if (m_hsImage != null) m_hsImage.setDetachCallbackRefresh();
        if (m_seekBar != null) m_seekBar.setOnSeekBarChangeListener(null);
//        if (receiver != null) receiver.clean();
        UtilsProgress.clean();
    }

    @Override
    public void initData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
            connect();
        }
    }

    /**
     * 权限申请成功调用此方法
     */
    private void connect() {
        if (m_connectEx == null) {
            m_connectEx = new HS_ConnectEx(this, m_handler, m_viewGrayBar, m_viewSample);
        }
        if (!mReceiverTag) {
            this.registerReceiver();
        }
        if (!Constants.DEVICE_REAL) {
            m_connectEx.connect();
        } else {
            UsbDevice device = UtilsUsbAuthority.getDevice(App.getContext());
            if (device == null) {
                Toast.makeText(this, "请连接设备", Toast.LENGTH_SHORT).show();
            } else if (UtilsUsbAuthority.hasPermission(device, App.getContext())) {
                m_connectEx.connect();
            } else {
                UtilsUsbAuthority.requestPermission(device, App.getContext());
            }
        }
    }

    @Override
    public void onClick(View view) {
        HS_Cine cine = null;
        switch ( view.getId() ) {
            case R.id.iv_play:  //播放电影
                new HS_Cine().setPlay();
                this.onUpdateControl();
                break;
            case R.id.iv_save:
                this.onCineSave(cine);
                break;
            case R.id.iv_forward:   //快进
                new HS_Cine().setIndex(true, true);
                m_seekBar.setProgress(new HS_Cine().getIndex());
                break;
            case R.id.iv_previous:  //快退
                new HS_Cine().setIndex(false, true);
                m_seekBar.setProgress(new HS_Cine().getIndex());
                break;
            case R.id.imgbtn_freeze:    // 冻结
                this.onFreeze();
                break;
            case R.id.imgbtn_changeMode:    // B/BM
                this.onChangeMode();
                break;
            case R.id.imgbtn_measure:   // 测量
                this.onMeasure();
                break;
            case R.id.imgbtn_savePic:   // 点击保存图片
                this.onSavePicture();
                break;
            case R.id.iv_delete:    //删除测量项，从最新的一项开始删除
                new HS_MeasureRemove().removeLast();
                break;
            case R.id.imgbtn_checkPoint://检查部位
                this.onCheckPart();
                break;
            case R.id.iv_zoom:    //
                if (new HS_Freeze().isRun()) {
                    popWindowSetImage = new PopWindowSetImage(MainActivity.this, findViewById(R.id.gray_bar_view), m_llMainContent, m_viewSample);
                    popWindowSetImage.showPoP(findViewById(R.id.title), isTabletDevice());
                    popWindowSetImage.setZoomCollback(this);
                }
                break;
        }
    }

    public void onCheck(View view) {
        ToastUtil.showShortToast("点击了onCheck");
        Intent intentBCRedactActivity = new Intent(MainActivity.this, BCRedactActivity.class);
        intentBCRedactActivity.putExtra(BCRedactActivity.BCRedactActivity_FilePatch, stringBuilder.toString());
        startActivity(intentBCRedactActivity);
    }

    //注册广播
    private void registerReceiver() {
        //监听otg插入 拔出
        if (receiver == null) {
            receiver = new Receiver(m_handler);
            mReceiverTag = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ACTION_DEVICE_PERMISSION);
            //filter.addAction(Constants.ACTION_UDISK_PERMISSION);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
            registerReceiver(receiver, filter);
        }
    }

    //销毁广播
    private void unregisterReceiver() {
        if (receiver != null) receiver.clean();
        if (mReceiverTag) {
            mReceiverTag = false;
            if (receiver != null) receiver.clean();
            this.unregisterReceiver(receiver);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        boolean ok = false;
        switch ( message.what ) {
            case Constants.UPDATE_PATIENT:
                //显示病人ID 姓名
                m_tvName.setText(uName);
                m_tvPatientId.setText(uNUidCard);
                ok = true;
                break;
            case Constants.UPDATE_BUTTON_STATE:
                this.onUpdateControl();
                ok = true;
                break;
            case Constants.RECONNECT_DEVICE:
                Toast.makeText(this, "设备已连接", Toast.LENGTH_SHORT).show();
                if (new HS_Mode().getMode() == HS_Mode.Mode.MODE_NULL) {
                    this.initData();
                }
                ok = true;
                break;
            case Constants.DISCONNECT_DEVICE:
                m_connectEx.getConnect().setUnconnect();
                if (new HS_Freeze().isFreeze()) {
                    Toast.makeText(this, "设备已断开", Toast.LENGTH_SHORT).show();
                    HS_Cine cine = new HS_Cine();
                    m_seekBar.setMax(cine.getSize() - 1);
                    m_seekBar.setProgress(cine.getIndex());
                    //m_viewSample.setCinePlay(false);
                    Constants.INIT_SEEKBAR = true;
                    this.onUpdateControl();
                } else {
                    DialogUSBPermissionsWarning dialogUnusual = new DialogUSBPermissionsWarning(this);
                    dialogUnusual.setTextViewText("设备已断开请重启设备");
                    dialogUnusual.show();
                }
                ok = true;
                break;
            case Constants.DEVICE_PERMISSION_GRANTED_SUCCESS://确定
                m_connectEx.connect();
                ok = true;
                break;
            case Constants.DEVICE_PERMISSION_GRANTED_FAIL://取消
                DialogUSBPermissionsWarning dialogFail = new DialogUSBPermissionsWarning(this);
                dialogFail.show();
                ok = true;
                break;
            case Constants.UDISK_PERMISSION_GRANTED_FAIL:
                this.onUpdateControl();
                break;
            case Constants.SHOW_HARD_CONFIG_PROGRESS:
                if(UtilsProgress.getPanel()!=null){
                    UtilsProgress.getPanel().setText("正在加载" + " " + String.valueOf(new HS_Connect().getProgress()) + "%");
                }
                break;
            case CINE_SAVE_PROGRESS:
                UtilsProgress.getPanel().setText("正在保存" + " " + String.valueOf(new HS_Cine().getSaveProgress(true)) + "%");
                break;
            case CINE_SAVE_FAIL:
                UtilsProgress.dismiss();
                break;
            case Constants.CONNECT_FINISH:
                m_connectEx.onFinish();
                break;
        }
        return ok;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (new HS_Freeze().isFreeze()) {
                HS_Cine cine = new HS_Cine();
                if (cine.isPlay()) {
                    cine.setPlay(false);
                }
                cine.setIndex(progress);
                this.onUpdateControl();
                m_seekBar.setSeekBarText("" + progress + "/" + cine.getSize());
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //m_viewSample.setCinePlay(false);
        this.onUpdateControl();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    //增益窗口回调
    @Override
    public void progresscollback() {
        if (new HS_Freeze().isRun()) {
            popWindowSetImage = new PopWindowSetImage(MainActivity.this, findViewById(R.id.gray_bar_view), m_llMainContent, m_viewSample);
            popWindowSetImage.showPoP(findViewById(R.id.title), isTabletDevice());
            popWindowSetImage.setZoomCollback(this);
        }
    }


    /**
     * 修改图标
     */
    private void onUpdateControl() {
//        //动态修改布局大小
//        int width = UtilsResolution.getScreenWidth(this) * SharePrefreceHelper.getInstence(this, Constants.MANUFACTURER_NAME).getWidth() / 100;
//        int height = UtilsResolution.getScreenHeight(this) * SharePrefreceHelper.getInstence(this, Constants.MANUFACTURER_NAME).getHeight() / 100;
//        m_llMainContent.setPadding(width, height, width, height);

        //设置监听
        {
            if (m_connectEx.getConnect().isConnected() && !m_imgBtnChangeMode.hasOnClickListeners()) {
                m_ivPrevious.setOnClickListener(this);
                m_ivForward.setOnClickListener(this);
                m_ivPlay.setOnClickListener(this);
                m_ivSaveMovie.setOnClickListener(this);
                m_imgBtnCheckBodyParts.setOnClickListener(this);
                m_imgBtnChangeMode.setOnClickListener(this);
                m_imgBtnFreeze.setOnClickListener(this);
                m_imgBtnMeasure.setOnClickListener(this);
                m_imgBtnSavePic.setOnClickListener(this);
            }
        }

        //底部按钮控件
        {
            //上一帧按钮状态变化
            if (new HS_Freeze().isFreeze() && !new HS_Cine().isPlay() && !m_bMeasuring) {
                m_ivPrevious.setImageResource(R.mipmap.follow_cine_prev_normal);
                m_ivPrevious.setEnabled(true);
            } else {
                m_ivPrevious.setImageResource(R.mipmap.follow_cine_prev_disable);
                m_ivPrevious.setEnabled(false);
            }

            //下一帧按钮状态变化
            if (new HS_Freeze().isFreeze() && !new HS_Cine().isPlay() && !m_bMeasuring) {
                m_ivForward.setImageResource(R.mipmap.follow_cine_next_normal);
                m_ivForward.setEnabled(true);
            } else {
                m_ivForward.setImageResource(R.mipmap.follow_cine_next_disable);
                m_ivForward.setEnabled(false);
            }

            //seekBar状态变化
            if (new HS_Freeze().isFreeze() && !m_bMeasuring) {//冻结
                m_seekBar.setEnabled(true);
                m_seekBar.setThumb(getResources().getDrawable(R.mipmap.follow_thumb_normal));
            } else {//运行
                m_seekBar.setProgress(0);
                m_seekBar.setEnabled(false);
                m_seekBar.setThumb(getResources().getDrawable(R.mipmap.follow_thumb_disable));
            }

            //播放按钮状态变化
            if (new HS_Freeze().isRun() || m_bMeasuring) {
                m_ivPlay.setEnabled(false);
                m_ivPlay.setImageResource(R.mipmap.follow_cine_play_disable);
            } else if (new HS_Cine().isPlay()) {
                m_ivPlay.setImageResource(R.mipmap.follow_cine_pause_normal);
                m_ivPlay.setEnabled(true);
            } else {
                m_ivPlay.setImageResource(R.mipmap.follow_cine_play_normal);
                m_ivPlay.setEnabled(true);
            }

            //保存电影按钮状态变化
            if (new HS_Freeze().isFreeze() && !new HS_Cine().isPlay() && !m_bMeasuring) {
                m_ivSaveMovie.setEnabled(true);
                m_ivSaveMovie.setImageResource(R.mipmap.follow_cine_save_normal);
            } else {
                m_ivSaveMovie.setEnabled(false);
                m_ivSaveMovie.setImageResource(R.mipmap.follow_cine_save_disable);
            }


            //检查部位按钮状态变化
            if (new HS_Freeze().isRun()) {
                m_imgBtnCheckBodyParts.setBackgroundResource(R.mipmap.follow_bottom_part_normal);
                m_imgBtnCheckBodyParts.setEnabled(true);
            } else {
                m_imgBtnCheckBodyParts.setBackgroundResource(R.mipmap.follow_bottom_part_disable);
                m_imgBtnCheckBodyParts.setEnabled(false);
            }

            //B、BM模式转换
            if (new HS_Freeze().isRun()) {
                if (new HS_Mode().isModeB()) {
                    m_imgBtnChangeMode.setBackgroundResource(R.mipmap.follow_bottom_pattern_bm_normal);
                } else {
                    m_imgBtnChangeMode.setBackgroundResource(R.mipmap.follow_bottom_pattern_b_normal);
                }
            } else {
                if (new HS_Mode().isModeB()) {
                    m_imgBtnChangeMode.setBackgroundResource(R.mipmap.follow_bottom_pattern_bm_disable);
                } else {
                    m_imgBtnChangeMode.setBackgroundResource(R.mipmap.follow_bottom_pattern_b_disable);
                }
            }

            //冻结解冻按钮状态变化
            if (new HS_Freeze().isRun()) {
                m_imgBtnFreeze.setBackgroundResource(R.mipmap.follow_bottom_freeze_normal);
            } else {
                m_imgBtnFreeze.setBackgroundResource(R.mipmap.follow_bottom_freeze_selected);
            }

            //测量按钮状态变化
            if (new HS_Freeze().isFreeze() && !new HS_Cine().isPlay()) {
                m_imgBtnMeasure.setEnabled(true);
                m_imgBtnMeasure.setBackgroundResource(R.mipmap.follow_bottom_measure_normal);
            } else {
                m_imgBtnMeasure.setEnabled(false);
                m_imgBtnMeasure.setBackgroundResource(R.mipmap.follow_bottom_measure_disable);
            }

            if (m_connectEx.getConnect().isConnected()) {
                m_imgBtnSavePic.setBackgroundResource(R.mipmap.follow_bottom_save_normal);
            } else {
                m_imgBtnSavePic.setBackgroundResource(R.mipmap.follow_bottom_save_disable);
            }
        }
        //图像区域按钮
        {
            if (new HS_Freeze().isRun()) {
                m_ivDelete.setVisibility(View.GONE);
                m_viewMeasureResult.setText(null);
                m_viewMeasureResult.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stringBuilder.delete(0, stringBuilder.length());
    }


    private void onCallBackCaret() {
        DialogNote note = new DialogNote(this);
        note.showDialog();
    }

    //SDK 回调
    private void onCallBackRefresh() {
        m_viewSample.onCallbackRefresh();
    }

    /**
     * 视频保存
     */
    public void onCineSave(HS_Cine hs_cine) {
        UtilsProgress.showWithStatus(this, "视频保存中");
        final boolean[] save = {true};
        String file = UtilsSDCard.root() + Constants.PATH_PATIENT + "/";
        file += "张三";//保存的位置
        new File(file).mkdirs();
        new HS_Cine().save(file + "/" + UtilsDateTime.getFileName(".avi"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (save[0]) {
                    m_handler.sendEmptyMessage(CINE_SAVE_PROGRESS);
                    if (new HS_Cine().getSaveProgress(true) == 100) {
                        save[0] = false;
                        m_handler.sendEmptyMessage(CINE_SAVE_FAIL);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 冻结运行
     */
    private void onFreeze() {
        if (!m_connectEx.getConnect().isConnected()) {
            this.initData();
        } else {
            HS_Freeze freeze = new HS_Freeze();
            if (freeze.setReverse()) {
                if (freeze.isFreeze()) {
                    HS_Cine cine = new HS_Cine();
                    m_seekBar.setMax(cine.getSize() - 1);
                    m_seekBar.setProgress(cine.getIndex());
                    //m_viewSample.setCinePlay(false);
                    Constants.INIT_SEEKBAR = true;
                } else {
                    m_bMeasuring = false;
                    m_seekBar.setMax(0);
                    m_seekBar.setEnabled(false);
                    //m_viewSample.setCinePlay(false);
                }
            }
            this.onUpdateControl();
        }
    }

    /**
     * B/Bm
     */
    public void onChangeMode() {
        if (new HS_Freeze().isRun()) {
            HS_Mode mode = new HS_Mode();
            if (mode.isModeB()) {
                mode.setMode(HS_Mode.Mode.MODE_BM);
            } else if (mode.isModeBM()) {
                mode.setMode(HS_Mode.Mode.MODE_B);
            }
            this.onUpdateControl();
        }
    }

    /**
     * 测量
     */
    private void onMeasure() {
        if (new HS_Freeze().isFreeze() && !new HS_Cine().isPlay()) {
            m_ivDelete.setView(m_viewSample);
            m_viewMeasureResult.setVisibility(View.VISIBLE);
            m_ivDelete.setVisibility(View.VISIBLE);
            DialogChooseItem.showDialog(this, "测量");
            m_bMeasuring = true;
            this.onUpdateControl();
        }
    }

    /**
     * 截取图像区域并保存到SD卡
     */
    public void onSavePicture() {
        UtilsProgress.showWithStatus(this, "图像保存中...", SVProgressHUD.SVProgressHUDMaskType.Clear);
        final boolean[] ok = new boolean[1];
        final Bitmap screen = UtilsBitmap.capture(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Bitmap frame = UtilsBitmap.capture(MainActivity.this);//m_viewSample.getBitmap();
                Bitmap frame = m_viewSample.getSaveBitmap();
                if (frame != null) {
//                    int location[] = new int[2];
//                    m_viewSample.getLocationOnScreen(location);
//                    Bitmap union = UtilsBitmap.union(frame, screen, location[0], location[1]);
//                    Bitmap block = null;
//                    if (MainActivity.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                        // 横屏
//                        block = UtilsBitmap.block(union, UtilsResolution.dip2px(MainActivity.this, 90), 0,
//                                MainActivity.this.getWindowManager().getDefaultDisplay().getWidth(),
//                                m_viewSample.getHeight() + UtilsResolution.dip2px(MainActivity.this, 60));
//                        block = UtilsBitmap.block(union, 0, 0,
//                                MainActivity.this.getWindowManager().getDefaultDisplay().getWidth(),
//                                0);
//                    } else {
//                        block = UtilsBitmap.block(union, 0, 0, 0, m_viewSample.getHeight() +
//                                UtilsResolution.dip2px(MainActivity.this, 60));
//                    }

                    // 图片的路径
//                    String file = UtilsSDCard.root() + Constants.PATH_PATIENT + "/";
//                    file += UsersMod.get_uName();
//                    file += "/" + UtilsDateTime.getFileName();
                    String file = UtilsSDCard.createFile(Constants.FINGERPRINT_ALBUM_PATH, System.currentTimeMillis() + "", frame);
                    //可以自己定义保存图像格式
//                    switch (HS_Setting.getValue(Constants.SETTINGS_TABLE, MainActivity.this, HS_SettingKey.KEY_IMAGEFORMAT,
//                            new HSVariant(HSEnum.ImageFormat.Png)).toInt()) {
//                        case HSEnum.ImageFormat.Png:
//                            file += ".png";
//                            break;
//                        case HSEnum.ImageFormat.Bmp:
//                            file += ".bmp";
//                            break;
//                        case HSEnum.ImageFormat.Jpg:
//                            file += ".jpg";
//                            break;
//                        case HSEnum.ImageFormat.Dcm:
//                            file += ".dcm";
//                            break;
//                    file += ".png";
                    //                   }
//                    ok[0] = UtilsBitmap.save(block, file, 100);

                    finalFile = file;
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(file);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UtilsProgress.dismiss();
                            ToastUtil.showShortToast("保存成功");
//                            showDialog3("备注", "请输入备注");
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 检查部位
     */
    private void onCheckPart() {
        final List<String> listValue = m_viewSample.getParts();
        PopWindowSetting popWindowSetting = new PopWindowSetting(this, listValue, new HS_Part().getIndex());
        popWindowSetting.setTextCollBcak(new PopWindowSetting.SetTextCollBcak() {
            @Override
            public void callbackSetText(int index, String text, TextView bindView, String bindKey) {
                new HS_Part().setIndex(index);
                m_viewSample.setPart();
            }
        });
        popWindowSetting.showPopupWindow(m_imgBtnCheckBodyParts);
    }

    @Override
    public void zoomcollback() {
        if (new HS_Zoom().isOpened()) {
            m_ivzomm.setVisibility(View.VISIBLE);
        } else {
            m_ivzomm.setVisibility(View.GONE);
        }
        popWindowSetImage = null;
    }

    /*@Override
    public void uiNetReturnData(String tUrl, String tData, int tSign) {
        switch ( tSign ) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(tData);
                    if (jsonObject.getString("result").equals("success")) {
                        Toast toast = Toast.makeText(this, "图像保存成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
        }
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (m_connectEx.isFinished()) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                m_viewSample.stopThreed();
                if (receiver != null)
                    this.unregisterReceiver(receiver);
                UtilsProgress.dismiss();
                UtilsProgress.clean();
                MainActivity.this.finish();
            }
        } else {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new HS_ApiManager().free();
        //CacheActivity.getInstance().removeActivity(this);
    }
}
