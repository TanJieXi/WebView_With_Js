package api.hard;

/*! @brief 多普勒控制-壁滤波
*/
public class HS_DopplerWallFilter {

    /*! @brief  获取壁滤波可控制个数
     *  @return 壁滤波可控制个数
    */
	public native int getSize();

    /*! @brief  获取壁滤波对应的索引值
     *  @param  value : 壁滤波值
     *  @return 索引值
    */
    public native int getIndex(int value);

    /*! @brief  获取当前壁滤波对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的壁滤波值
     *  @param  index : 索引值
     *  @return 壁滤波值
    */
    public native int getValue(int index);

    /*! @brief  获取当前壁滤波值
     *  @return 壁滤波值
    */
    public native int getValue();

    /*! @brief  设置壁滤波
     *  @param  add  : HSTrue = 壁滤波加, HSFalse = 壁滤波减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置壁滤波值
     *  @param  value : 壁滤波值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
