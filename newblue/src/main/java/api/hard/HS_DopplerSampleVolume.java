package api.hard;

/*! @brief 多普勒控制-音量
*/
public class HS_DopplerSampleVolume {

    /*! @brief  获取音量可控制个数
     *  @return 音量可控制个数
    */
	public native int getSize();

    /*! @brief  获取音量对应的索引值
     *  @param  value : 音量值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前音量对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的音量值
     *  @param  index : 索引值
     *  @return 音量值
    */
	public native int getValue(int index);

    /*! @brief  获取当前音量值
     *  @return 音量值
    */
	public native int getValue();

    /*! @brief  设置音量
     *  @param  add  : HSTrue = 音量加, HSFalse = 音量减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置音量值
     *  @param  value : 音量值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
