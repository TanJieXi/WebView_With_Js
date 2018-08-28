package api.hard;

/*! @brief 焦点位置控制
 */
public class HS_FocusPos {

    /*! @brief  设置焦点位置是否显示
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  获取焦点位置是否显示
     *  @return 是否显示
     */
    public native boolean isShowed();


    /*! @brief  获取焦点深度可控制个数
     *  @return 焦点深度可控制个数
    */
    public native int getSize();

    /*! @brief  获取焦点深度对应的索引值
     *  @param  value : 焦点深度(mm)
     *  @return 索引值
    */
    public native int getIndex(int value);

    /*! @brief  获取当前焦点深度索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取焦点深度索引对应的焦点深度值
     *  @param  index : 索引值
     *  @return 焦点深度值(mm)
    */
    public native int getValue(int index);

    /*! @brief  获取当前焦点深度值
     *  @return 焦点深度值(mm)
    */
    public native int getValue();

    /*! @brief  设置焦点深度
     *  @param  add  : HsTrue = 焦点深度加, HsFalse = 焦点深度减
     *  @param  loop : HsTrue = 循环加减,   HsFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置焦点深度值
     *  @param  value : 焦点深度值(mm)
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置焦点深度索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
