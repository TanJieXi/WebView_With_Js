package api.hard;

/*! @brief 多普勒控制-信号量
*/
public class HS_DopplerSignalScale {

    /*! @brief  获取信号量可控制个数
     *  @return 信号量可控制个数
    */
	public native int getSize();

    /*! @brief  获取信号量对应的索引值
     *  @param  value : 信号量值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前信号量对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的信号量值
     *  @param  index : 索引值
     *  @return 信号量值
    */
	public native int getValue(int index);

    /*! @brief  获取当前信号量值
     *  @return 信号量值
    */
	public native int getValue();

    /*! @brief  设置信号量
     *  @param  add  : HSTrue = 信号量加, HSFalse = 信号量减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置信号量值
     *  @param  value : 信号量值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
