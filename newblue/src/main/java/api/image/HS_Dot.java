package api.image;

/*! @brief 点增强控制
*/
public class HS_Dot {

    /*! @brief  获取点增强可控制个数
     *  @return 点增强可控制个数
    */
	public native int getSize();

    /*! @brief  获取点增强对应的索引值
     *  @param  value : 点增强值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前点增强对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的点增强值
     *  @param  index : 索引值
     *  @return 点增强值
    */
	public native int getValue(int index);

    /*! @brief  获取当前点增强值
     *  @return 点增强值
    */
	public native int getValue();

    /*! @brief  设置点增强
     *  @param  add  : HSTrue = 点增强加, HSFalse = 点增强减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置点增强值
     *  @param  value : 点增强值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
