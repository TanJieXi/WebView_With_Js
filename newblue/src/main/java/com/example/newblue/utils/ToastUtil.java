package com.example.newblue.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.newblue.App;

public class ToastUtil {
	private static Toast mToast;

	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
			mToast = null;// toast隐藏后，将其置为null
		}
	};

	/**
	 * 自定义显示Toast
	 * 
	 * @param context
	 * @param message
	 */
//	public static void showShortToast(Context context, String message) {
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View view = inflater.inflate(R.layout.custom_toast, null);// 自定义布局
//		TextView text = (TextView) view.findViewById(R.id.toast_message);// 显示的提示文字
//		text.setText(message);
//		mHandler.removeCallbacks(r);
//		if (mToast == null) {// 只有mToast==null时才重新创建，否则只需更改提示文字
//			mToast = new Toast(context);
//			mToast.setDuration(Toast.LENGTH_SHORT);
//			mToast.setGravity(Gravity.BOTTOM, 0, 220);
//			mToast.setView(view);
//		}
//		mHandler.postDelayed(r, 1000);// 延迟1秒隐藏toast
//		mToast.show();
//	}

	/**
	 * 解决系统Toast连续点击重复显示问题
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showShortToast(Context context, String msg) {
		if (mToast != null) {
			mToast.cancel();
			mToast=null;
		}
		mToast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
		mToast.show();
	}
	/**
	 * 解决系统Toast连续点击重复显示问题
	 *
	 *
	 * @param msg
	 */
	public static void showShortToast( String msg) {
		if (mToast != null) {
			mToast.cancel();
			mToast=null;
		}
		mToast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
		mToast.show();
	}
}
