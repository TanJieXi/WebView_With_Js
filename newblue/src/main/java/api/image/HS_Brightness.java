package api.image;

/*! @brief 亮度控制
*/
public class HS_Brightness {

    /*! @brief  获取亮度可控制个数
     *  @return 亮度可控制个数
	*/
	public native int getSize();

    /*! @brief  获取亮度值对应的索引值
     *  @param  value : 亮度值
     *  @return 索引值
	*/
	public native int getIndex(int value);

    /*! @brief  获取当前亮度值对应的索引值
     *  @return 索引值
	*/
	public native int getIndex();

    /*! @brief  获取索引值对应的亮度值
     *  @param  index : 索引值
     *  @return 亮度值
	*/
	public native int getValue(int index);

    /*! @brief  获取当前亮度值
     *  @return 亮度值
	*/
	public native int getValue();

    /*! @brief  设置亮度值
     *  @param  add  : HsTrue = 亮度加,   HsFalse = 亮度减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
	*/
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置亮度值
     *  @param  value : 亮度值
     *  @return 是否设置成功
	*/
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
	*/
    public native boolean setIndex(int index);
}
