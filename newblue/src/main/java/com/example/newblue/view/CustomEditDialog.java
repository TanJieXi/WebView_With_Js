package com.example.newblue.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newblue.R;


/**
 * 加载提醒对话框
 */
public class CustomEditDialog extends ProgressDialog {
	private String message = "";
	public CustomEditDialog(Context context) {
		super(context);
	}

	public CustomEditDialog(Context context, String message) {
		super(context);
		this.message = message;
	}

	public CustomEditDialog(Context context, int theme, String message) {
		super(context, theme);
		this.message = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init(getContext());
	}

	private void init(Context context) {
		// 设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
		setCancelable(false);
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.follow_load_eidt_dialog);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		// 设置背景层透明度
		params.dimAmount = 0.2f;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);
	}

	@Override
	public void show() {
		super.show();
	}

	/**
	 * 当窗口焦点改变时调用
	 */
	public void onWindowFocusChanged(boolean hasFocus) {
		ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
		// 获取ImageView上的动画背景
		AnimationDrawable spinner = (AnimationDrawable) imageView
				.getBackground();
		// 开始动画
		spinner.start();
		if (message != null && message.length() > 0) {
			findViewById(R.id.message).setVisibility(View.VISIBLE);
			TextView txt = (TextView) findViewById(R.id.message);
			txt.setText(message);
			txt.invalidate();
		}
	}

}