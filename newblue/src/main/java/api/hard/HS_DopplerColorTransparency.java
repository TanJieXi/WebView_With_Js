package api.hard;

/*! @brief 多普勒控制-颜色透明度
*/
public class HS_DopplerColorTransparency {

    /*! @brief  获取颜色透明度可控制个数
     *  @return 颜色透明度可控制个数
    */
	public native int getSize();

    /*! @brief  获取颜色透明度对应的索引值
     *  @param  value : 颜色透明度值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前颜色透明度对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的颜色透明度值
     *  @param  index : 索引值
     *  @return 颜色透明度值
    */
	public native int getValue(int index);

    /*! @brief  获取当前颜色透明度值
     *  @return 颜色透明度值
    */
	public native int getValue();

    /*! @brief  设置颜色透明度
     *  @param  add  : HSTrue = 颜色透明度加, HSFalse = 颜色透明度减
     *  @param  loop : HSTrue = 循环加减,     HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置颜色透明度值
     *  @param  value : 颜色透明度值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
