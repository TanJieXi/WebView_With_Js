package api.hard;

/*! @brief 多普勒控制-脉冲重复频率
*/
public class HS_DopplerPRF {

    /*! @brief  获取脉冲重复频率可控制个数
     *  @return 脉冲重复频率可控制个数
    */
	public native int getSize();

    /*! @brief  获取脉冲重复频率对应的索引值
     *  @param  value : 脉冲重复频率值(Hz)
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前脉冲重复频率对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的脉冲重复频率值
     *  @param  index : 索引值
     *  @return 脉冲重复频率值(Hz)
    */
	public native int getValue(int index);

    /*! @brief  获取当前脉冲重复频率值
     *  @return 脉冲重复频率值(Hz)
    */
	public native int getValue();

    /*! @brief  设置脉冲重复频率
     *  @param  add  : HSTrue = 脉冲重复频率加, HSFalse = 脉冲重复频率减
     *  @param  loop : HSTrue = 循环加减,       HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置脉冲重复频率值
     *  @param  value : 脉冲重复频率值(Hz)
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
