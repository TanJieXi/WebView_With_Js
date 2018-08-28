package api.os;

import android.content.Context;
import android.graphics.Bitmap;

import api.type.HS_Size;

/*! @brief 图像操作
*/
public class HS_Image {

    /*! @brief  绑定刷新回调函数(冻结/切换活动图/电影回放等)
     *  @param  fun : 刷新回调函数
     *  @param  arg : 回调函数参数
     */
	public native void setAttachCallbackRefresh(Context callbackClassContext, String callbackClassFunction);

    /*! @brief  解除刷新回调函数的绑定
     */
	public native void setDetachCallbackRefresh();

    /*! @brief  线程锁定
     */
    public native void setLock();

    /*! @brief  线程解锁
     */
    public native void setUnlock();


    /*! @brief  设置图像尺寸
     *  @param  width  : 图像宽度
     *  @param  height : 图像高度
     *  @return 是否设置成功
     */
    public native boolean setSize(int width, int height);

    /*! @brief  获取图像尺寸
     *  @return 图像尺寸
     */
    public native HS_Size getSize();

    /*! @brief  获取输出图像
     *  @return 输出图像
     */
    public native Bitmap getImage();

    /*! @brief  获取输出图像
     *  @param  image : 输出图像
     *  @return 是否获取成功
     */
    public native boolean getImage(Bitmap image);

    /*! @brief  保存当前图像
     *  @param  file : 文件路径
     *  @return 是否保存成功
     */
    public native boolean save(String file);

    /*! @brief  设置图像处理对象,用于自定义后处理算法
     *  @param  interface : 外部接口对象
     *  @return val == NULL, 设置失败
     *  @return val != NULL, 原图像处理接口对象
     *  @note   对象在堆空间中,需用户手动delete
     */
    //public native HS_ProcessInterface* setProcessInterface(HS_ProcessInterface* interface1);
}
