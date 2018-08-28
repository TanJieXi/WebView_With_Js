package api.image;

/*! @brief 纵向空间滤波
*/
public class HS_FilterVert {

    /*! @brief  获取纵向滤波可控制个数
     *  @return 纵向滤波可控制个数
    */
	public native int getSize();

    /*! @brief  获取纵向滤波对应的索引值
     *  @param  value : 纵向滤波值
     *  @return 索引值
    */
	public native int getIndex(boolean value);

    /*! @brief  获取当前纵向滤波对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的纵向滤波值
     *  @param  index : 索引值
     *  @return 纵向滤波值
    */
	public native boolean getValue(int index);

    /*! @brief  获取当前纵向滤波值
     *  @return 纵向滤波值
    */
	public native boolean getValue();

    /*! @brief  设置纵向滤波
     *  @param  add  : HSTrue = 纵向滤波加, HSFalse = 纵向滤波减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置纵向滤波值
     *  @param  value : 纵向滤波值
     *  @return 是否设置成功
    */
	public native boolean setValue(boolean value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
