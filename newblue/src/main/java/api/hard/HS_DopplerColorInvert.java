package api.hard;

/*! @brief 多普勒控制-颜色翻转
*/
public class HS_DopplerColorInvert {

    /*! @brief  获取颜色翻转可控制个数
     *  @return 颜色翻转可控制个数
    */
	public native int getSize();

    /*! @brief  获取颜色翻转对应的索引值
     *  @param  value : 颜色翻转值
     *  @return 索引值
    */
    public native int getIndex(boolean value);

    /*! @brief  获取当前颜色翻转对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的颜色翻转值
     *  @param  index : 索引值
     *  @return 颜色翻转值
    */
    public native boolean getValue(int index);

    /*! @brief  获取当前颜色翻转值
     *  @return 颜色翻转值
    */
    public native boolean getValue();

    /*! @brief  设置颜色翻转
     *  @param  add  : HSTrue = 颜色翻转加, HSFalse = 颜色翻转减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置颜色翻转值
     *  @param  value : 颜色翻转值
     *  @return 是否设置成功
    */
    public native boolean setValue(boolean value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
