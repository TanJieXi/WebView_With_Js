package api.image;

/*! @brief 对当前活动图左右镜像
*/
public class HS_Mirror {

    /*! @brief  获取左右镜像可控制个数
     *  @return 左右镜像可控制个数
    */
	public native int getSize();

    /*! @brief  获取左右镜像对应的索引值
     *  @param  value : 左右镜像值
     *  @return 索引值
    */
	public native int getIndex(boolean value);

    /*! @brief  获取当前左右镜像对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的左右镜像值
     *  @param  index : 索引值
     *  @return 左右镜像值
    */
	public native boolean getValue(int index);

    /*! @brief  获取当前左右镜像值
     *  @return 左右镜像值
    */
	public native boolean getValue();

    /*! @brief  设置左右镜像
     *  @param  add  : HSTrue = 左右镜像加, HSFalse = 左右镜像减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置左右镜像值
     *  @param  value : 左右镜像值
     *  @return 是否设置成功
    */
	public native boolean setValue(boolean value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
