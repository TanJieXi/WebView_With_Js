package api.hard;

/*! @brief 多普勒控制-包络大小
*/
public class HS_DopplerPacketSize {

    /*! @brief  获取包络大小可控制个数
     *  @return 包络大小可控制个数
    */
	public native int getSize();

    /*! @brief  获取包络大小对应的索引值
     *  @param  value : 包络大小值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前包络大小对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的包络大小值
     *  @param  index : 索引值
     *  @return 包络大小值
    */
	public native int getValue(int index);

    /*! @brief  获取当前包络大小值
     *  @return 包络大小值
    */
	public native int getValue();

    /*! @brief  设置包络大小
     *  @param  add  : HSTrue = 包络大小加, HSFalse = 包络大小减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置包络大小值
     *  @param  value : 包络大小值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
