package api.image;

/*! @brief 反相
*/
public class HS_Negative {

    /*! @brief  获取反相可控制个数
     *  @return 反相可控制个数
    */
	public native int getSize();

    /*! @brief  获取反相对应的索引值
     *  @param  value : 反相值
     *  @return 索引值
    */
	public native int getIndex(boolean value);

    /*! @brief  获取当前反相对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的反相值
     *  @param  index : 索引值
     *  @return 反相值
    */
	public native boolean getValue(int index);

    /*! @brief  获取当前反相值
     *  @return 反相值
    */
    public native boolean getValue();

    /*! @brief  设置反相
     *  @param  add  : HSTrue = 反相加,   HSFalse = 反相减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置反相值
     *  @param  value : 反相值
     *  @return 是否设置成功
    */
    public native boolean setValue(boolean value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
