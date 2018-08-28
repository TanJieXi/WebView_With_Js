package api.hard;

/*! @brief 多普勒控制-信号平滑
*/
public class HS_DopplerSignalSmooth {

    /*! @brief  获取信号平滑可控制个数
     *  @return 信号平滑可控制个数
    */
	public native int getSize();

    /*! @brief  获取信号平滑对应的索引值
     *  @param  value : 信号平滑值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前信号平滑对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的信号平滑值
     *  @param  index : 索引值
     *  @return 信号平滑值
    */
	public native int getValue(int index);

    /*! @brief  获取当前信号平滑值
     *  @return 信号平滑值
    */
	public native int getValue();

    /*! @brief  设置信号平滑
     *  @param  add  : HSTrue = 信号平滑加, HSFalse = 信号平滑减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置信号平滑值
     *  @param  value : 信号平滑值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
