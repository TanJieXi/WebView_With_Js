package com.healon.up20user.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newblue.R;


/**
 * 权限点击了取消
 * Created by Administrator on 2017/5/5.
 */

public class DialogUSBPermissionsWarning extends Dialog implements View.OnClickListener {
    private Context m_context;
    private WindowManager.LayoutParams m_layprm;
    private Button m_btnYes;
    private TextView m_tvNote;

    public DialogUSBPermissionsWarning(Context context) {
        super(context, R.style.follow_promptDlg);
        this.m_context = context;
        //禁止退出
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        //设置window属性
        m_layprm = getWindow().getAttributes();
        m_layprm.gravity = Gravity.CENTER;
        m_layprm.alpha = 1.0f;
        getWindow().setAttributes(m_layprm);
        setContent();
    }

    public DialogUSBPermissionsWarning(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogUSBPermissionsWarning(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setContent() {
        View root = LayoutInflater.from(m_context).inflate(R.layout.follow_dialog_permission_exit,
                new LinearLayout(m_context), false);
        m_tvNote = (TextView) root.findViewById(R.id.tv_note);
        m_btnYes = (Button) root.findViewById(R.id.btn_yes);
        m_btnYes.setOnClickListener(this);
        setContentView(root);
    }

    public void setInfo(String btnyesText) {
        m_btnYes.setText(btnyesText);
    }

    public void setTextViewText(String text) {
        m_tvNote.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
             //   AppManager.getInstance().killAllActivity();
                System.exit(0);
                this.dismiss();
                break;
        }
    }
}
