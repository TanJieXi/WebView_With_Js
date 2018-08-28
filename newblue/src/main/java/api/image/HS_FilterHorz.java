package api.image;

/*! @brief 横向空间滤波
*/
public class HS_FilterHorz {

    /*! @brief  获取横向滤波可控制个数
     *  @return 横向滤波可控制个数
    */
	public native int getSize();

    /*! @brief  获取横向滤波对应的索引值
     *  @param  value : 横向滤波值
     *  @return 索引值
    */
	public native int getIndex(boolean value);

    /*! @brief  获取当前横向滤波对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的横向滤波值
     *  @param  index : 索引值
     *  @return 横向滤波值
    */
	public native boolean getValue(int index);

    /*! @brief  获取当前横向滤波值
     *  @return 横向滤波值
    */
	public native boolean getValue();

    /*! @brief  设置横向滤波
     *  @param  add  : HSTrue = 横向滤波加, HSFalse = 横向滤波减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置横向滤波值
     *  @param  value : 横向滤波值
     *  @return 是否设置成功
    */
	public native boolean setValue(boolean value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
