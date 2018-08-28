package api.measure;

import android.content.Context;

/*! @brief  测量回调操作
*/
public class HS_MeasureCallBack {

    /*! @brief  绑定注释回调函数
     *  @param  callbackClassContext  : 注释回调函数所在的类
     *  @param  callbackClassFunction : 注释回调函数名
     */
	public native void setAttachCallbackCaret(Context callbackClassContext, String callbackClassFunction);

    /*! @brief  解除注释回调函数的绑定
     */
	public native void setDetachCallbackCaret();

    /*! @brief  设置当前测量项注释文本
     *  @param  caret : 注释文本
     */
	public native void setCaret(String caret);
	
	//<! 回调接口
	private void onCallback() {

	}
}
