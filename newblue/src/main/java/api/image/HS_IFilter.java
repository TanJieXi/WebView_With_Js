package api.image;

/*! @brief IClear控制
*/
public class HS_IFilter {

    /*! @brief  获取高级滤波可控制个数
     *  @return 高级滤波可控制个数
    */
	public native int getSize();

    /*! @brief  获取高级滤波对应的索引值
     *  @param  value : 高级滤波值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前高级滤波对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的高级滤波值
     *  @param  index : 索引值
     *  @return 高级滤波值
    */
	public native int getValue(int index);

    /*! @brief  获取当前高级滤波值
     *  @return 高级滤波值
    */
	public native int getValue();

    /*! @brief  设置高级滤波
     *  @param  add  : HSTrue = 高级滤波加, HSFalse = 高级滤波减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置高级滤波值
     *  @param  value : 高级滤波值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
