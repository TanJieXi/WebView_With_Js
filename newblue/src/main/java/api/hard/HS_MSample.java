package api.hard;

/*! @brief M超采样线控制
*/
public class HS_MSample {

    /*! @brief  设置是否显示M采样线
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  获取M采样线是否显示
     *  @return 是否显示
     */
    public native boolean isShowed();


    /*! @brief  获取M采样线位置可控制个数
     *  @return M采样线位置可控制个数
    */
	public native int getSize();

    /*! @brief  获取M采样线位置对应的索引值
     *  @param  value : M采样线位置值
     *  @return 索引值
    */
    public native int getIndex(int value);

    /*! @brief  获取当前M采样线位置对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的M采样线位置值
     *  @param  index : 索引值
     *  @return M采样线位置值
    */
    public native int getValue(int index);

    /*! @brief  获取当前M采样线位置值
     *  @return M采样线位置值
    */
    public native int getValue();

    /*! @brief  设置M采样线位置
     *  @param  add  : HsTrue = 位置加,   HsFalse = 位置减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置M采样线位置值
     *  @param  value : M采样线位置值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置M采样线位置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
