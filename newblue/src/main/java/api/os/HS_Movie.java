package api.os;

import android.content.Context;
import android.graphics.Bitmap;

/*! @brief 视频播放
*/

public class HS_Movie {

    /*! @brief  绑定电影刷新回调函数
     *  @param  fun : 刷新回调函数
     *  @param  arg : 回调函数参数
     */
	public native void setAttachCallbackMovie(Context callbackClassContext, String callbackClassFunction);

    /*! @brief  解除电影刷新回调函数的绑定
     */
	public native void setDetachCallbackMovie();

	
    /*! @brief  获取电影文件首帧缩略图
     *  @param  file : 电影文件
     *  @return 首帧缩略图
    */
	public native Bitmap getThumb(String file);


    /*! @brief  加载电影文件
     *  @param  file : 电影文件
     *  @return 是否成功
    */
	public native boolean setFile(String file);

    /*! @brief  关闭电影文件
    */
	public native void setClose();


    /*! @brief  获取电影帧数
     *  @return 电影帧数
    */
	public native int getSize();

    /*! @brief  获取电影宽度
     *  @return 电影宽度
    */
    public native int getWidth();

    /*! @brief  获取电影高度
     *  @return 电影高度
    */
    public native int getHeight();

    /*! @brief  获取电影帧频
     *  @return 电影帧频
    */
    public native double getFps();

    /*! @brief  获取电影速度控制数量
     *  @return 电影速度控制数量
    */
    public native int getSpeedCount();

    /*! @brief  获取当前电影速度索引
     *  @return 当前电影速度索引
    */
    public native int getSpeedIndex();

    /*! @brief  获取当前电影速度值
     *  @return 当前电影速度值
    */
    public native double getSpeedValue();

    /*! @brief  获取当前电影帧位置
     *  @return 当前电影帧位置
    */
    public native int getPos();

    /*! @brief  当前电影帧是否在起始位置
     *  @return 是否在起始位置
    */
    public native boolean isHome();

    /*! @brief  当前电影帧是否在结束位置
     *  @return 是否在结束位置
    */
    public native boolean isEnd();
    
    /*! @brief  获取刷新图像
     *  @return 刷新图像
     */
	public native Bitmap getImage();


    /*! @brief  获取当前播放状态
     *  @return 是否为播放状态
    */
	public native boolean isPlay();

    /*! @brief  设置播放状态
     *  @param  play : 播放状态
     *  @return 是否设置成功
    */
    public native boolean setPlay(boolean play);

    /*! @brief  设置循环播放
     *  @param  loop : 循环播放
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean loop);

    /*! @brief  设置当前电影速度索引
     *  @return 是否设置成功
    */
    public native boolean setSpeed(int speed);

    /*! @brief  设置播放位置
     *  @param  add  : HSTrue = 位置加,   HSFalse = 位置减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setSpeed(boolean add, boolean loop);

    /*! @brief  设置播放位置
     *  @param  pos : 播放位置
     *  @return 是否设置成功
    */
    public native boolean setPos(int pos);

    /*! @brief  设置播放位置
     *  @param  add  : HSTrue = 位置加,   HSFalse = 位置减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setPos(boolean add, boolean loop);

    /*! @brief  设置为起始位置
     *  @return 是否设置成功
    */
    public native boolean setHome();

    /*! @brief  设置为结束位置
     *  @return 是否设置成功
    */
    public native boolean setEnd();
}
