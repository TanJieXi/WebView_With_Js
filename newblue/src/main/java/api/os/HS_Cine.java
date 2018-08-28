package api.os;

import android.graphics.Bitmap;

/*! @brief 电影
*/
public class HS_Cine {

    // 电影保存结果
	public class Result {
		public static final int NONE    = 0;
		public static final int SAVING  = 1;
		public static final int FAILED  = 2;
		public static final int SUCCESS = 3;
	}

    /*! @brief 获取电影帧数
	*/
    public native int getSize();

    /*! @brief  获取电影帧图像(在不刷新当前帧的情况下获取指定的帧图像)
     *  @param  index : 电影帧位置
     *  @return 帧图像
	*/
    public native Bitmap getValue(int index);

    /*! @brief  获取电影帧图像(在不刷新当前帧的情况下获取指定的帧图像)
     *  @param  index : 电影帧位置
     *  @param  image : 电影帧图像
     *  @return 是否成功
    */
    public native boolean getValue(int index, Bitmap image);

    /*! @brief  获取当前播放位置
     *  @return 当前播放位置
    */
    public native int getIndex();

    /*! @brief  设置播放位置
     *  @param  index : 播放位置
     *  @return 是否设置成功
	*/
    public native boolean setIndex(int index);

    /*! @brief 设置播放位置
     *  @param  add  : HSTrue = 位置加,   HSFalse = 位置减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
	*/
	public native boolean setIndex(boolean add, boolean loop);


    /*! @brief 设置翻转播放状态
     *  @return 是否设置成功
    */
    public native boolean setPlay();

    /*! @brief 设置播放状态
     *  @param  play : 是否播放
     *  @return 是否设置成功
    */
    public native boolean setPlay(boolean play);

    /*! @brief 获取播放状态
     *  @return 是否正在播放
    */
    public native boolean isPlay();


    /*! @brief  设置最大帧数
     *  @param  count : 最大帧数
     *  @return 是否设置成功
    */
    public native boolean setMaxCount(int count);

    /*! @brief  清除当前电影帧
     *  @note   冻结后有效(如正在播放,调用后将停止播放)
    */
    public native void clear();


    /*! @vbrief 保存文件
     *  @param  file            : 文件名
     *  @return 是否保存成功
	*/
    public native boolean save(String file);

    /*! @brief  获取保存进度
     *  @param  percent = HSTrue  : 百分比
     *  @param  percent = HSFalse : 帧数
     *  @return 返回当前保存进度
    */
    public native int getSaveProgress(boolean percent);

    /*! @brief  获取保存结果
     *  @return 保存结果
    */
    public native int getSaveResult();
}
