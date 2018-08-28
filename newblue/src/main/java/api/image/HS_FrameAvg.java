package api.image;

/*! @brief 帧相关控制
*/
public class HS_FrameAvg {

    /*! @brief  获取帧相关可控制个数
     *  @return 帧相关可控制个数
    */
	public native int getSize();

    /*! @brief  获取帧相关对应的索引值
     *  @param  value : 帧相关值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前帧相关对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的帧相关值
     *  @param  index : 索引值
     *  @return 帧相关值
    */
	public native int getValue(int index);

    /*! @brief  获取当前帧相关值
     *  @return 帧相关值
    */
	public native int getValue();

    /*! @brief  设置帧相关
     *  @param  add  : HSTrue = 帧相关加, HSFalse = 帧相关减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置帧相关值
     *  @param  value : 帧相关值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
