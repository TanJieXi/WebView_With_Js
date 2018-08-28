package api.hard;

/*! @brief 多普勒控制-增益
*/
public class HS_DopplerGain {

    /*! @brief  获取增益可控制个数
     *  @return 增益可控制个数
    */
	public native int getSize();

    /*! @brief  获取增益对应的索引值
     *  @param  value : 增益值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前增益对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的增益值
     *  @param  index : 索引值
     *  @return 增益值
    */
	public native int getValue(int index);

    /*! @brief  获取当前增益值
     *  @return 增益值
    */
	public native int getValue();

    /*! @brief  设置增益
     *  @param  add  : HSTrue = 增益加,  HSFalse = 增益减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置增益值
     *  @param  value : 增益值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
