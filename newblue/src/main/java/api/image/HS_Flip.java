package api.image;

/*! @brief 对当前活动图上下翻转
*/
public class HS_Flip {

    /*! @brief  获取上下翻转可控制个数
     *  @return 上下翻转可控制个数
    */
	public native int getSize();

    /*! @brief  获取上下翻转对应的索引值
     *  @param  value : 上下翻转值
     *  @return 索引值
    */
	public native int getIndex(boolean value);

    /*! @brief  获取当前上下翻转对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的上下翻转值
     *  @param  index : 索引值
     *  @return 上下翻转值
    */
	public native boolean getValue(int index);

    /*! @brief  获取当前上下翻转值
     *  @return 上下翻转值
    */
	public native boolean getValue();

    /*! @brief  设置上下翻转
     *  @param  add  : HSTrue = 上下翻转加, HSFalse = 上下翻转减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置上下翻转值
     *  @param  value : 上下翻转值
     *  @return 是否设置成功
    */
	public native boolean setValue(boolean value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
