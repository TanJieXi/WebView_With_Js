package api.hard;

/*! @brief 焦点个数控制
 */
public class HS_FocusCount {

    /*! @brief  获取焦点个数可控制个数
     *  @return 焦点个数可控制个数
    */
	public native int getSize();

    /*! @brief  获取焦点个数对应的索引值
     *  @param  value : 焦点个数值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前焦点个数对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的焦点个数
     *  @param  index : 索引值
     *  @return 焦点个数数值
    */
	public native int getValue(int index);

    /*! @brief  获取当前焦点个数值
     *  @return 焦点个数
    */
	public native int getValue();

    /*! @brief  设置焦点个数
     *  @param  add  : HsTrue = 焦点个数加, HsFalse = 焦点个数减
     *  @param  loop : HsTrue = 循环加减,   HsFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置焦点个数值
     *  @param  value : 焦点个数值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置焦点个数索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
