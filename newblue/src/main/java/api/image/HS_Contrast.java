package api.image;

/*! @brief 对比度控制
*/
public class HS_Contrast {

    /*! @brief  获取对比度可控制个数
     *  @return 对比度可控制个数
	*/
	public native int getSize();

    /*! @brief  获取对比度对应的索引值
     *  @param  value : 对比度值
     *  @return 索引值
	*/
	public native int getIndex(int value);

    /*! @brief  获取当前对比度对应的索引值
     *  @return 索引值
	*/
	public native int getIndex();

    /*! @brief  获取索引值对应的对比度值
     *  @param  index : 索引值
     *  @return 对比度值
	*/
	public native int getValue(int index);

    /*! @brief  获取当前对比度值
     *  @return 对比度值
	*/
	public native int getValue();

    /*! @brief  设置对比度
     *  @param  add  : HSTrue = 对比度加, HSFalse = 对比度减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置对比度值
     *  @param  value : 对比度值
     *  @return 是否设置成功
	*/
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
	*/
	public native boolean setIndex(int index);
}
