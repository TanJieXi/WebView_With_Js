package com.healon.up20user.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.newblue.R;

import java.util.Timer;
import java.util.TimerTask;

import api.measure.HS_MeasureCallBack;

/**
 * Created by Pretend on 2017/8/7 0007.
 */

public class DialogNote extends Dialog implements View.OnClickListener {
    private Context m_context;
    private WindowManager.LayoutParams m_lpManager;
    private EditText m_editNote;
    private String m_strNote;
    private Button m_btnNo;
    private Button m_btnYes;
    private View root;

    public DialogNote(Context context) {
        super(context, R.style.follow_promptDlg);
        this.m_context = context;
        //设置window属性
        m_lpManager = getWindow().getAttributes();
        m_lpManager.gravity = Gravity.CENTER;
        m_lpManager.alpha = 1.0f;
        getWindow().setAttributes(m_lpManager);
        setContent();
    }

    public DialogNote(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogNote(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setContent() {
        root = LayoutInflater.from(m_context).inflate(R.layout.follow_dialog_notes,
                new LinearLayout(m_context), false);
        m_editNote = (EditText) root.findViewById(R.id.tv_note);
        m_btnNo = (Button) root.findViewById(R.id.btn_no);
        m_btnYes = (Button) root.findViewById(R.id.btn_yes);
        m_btnNo.setOnClickListener(this);
        m_btnYes.setOnClickListener(this);
        setContentView(root);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ((InputMethodManager) m_context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(m_editNote, 0);
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        if (v == m_btnNo) {
            m_strNote = "";
            new HS_MeasureCallBack().setCaret(m_strNote);
            dismiss();
        }
        if (v == m_btnYes) {
            m_strNote = m_editNote.getText().toString().trim();
            new HS_MeasureCallBack().setCaret(m_strNote);
            dismiss();
        }
    }

    public void showDialog() {
        super.show();
    }


}
