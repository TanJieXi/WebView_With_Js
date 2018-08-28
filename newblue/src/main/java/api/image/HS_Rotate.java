package api.image;

/*! @brief 旋转控制
*/
public class HS_Rotate {

    /*! @brief  获取旋转可控制个数
     *  @return 旋转可控制个数
	*/
	public native int getSize();

    /*! @brief  获取旋转值对应的索引值
     *  @param  value : 旋转值
     *  @return 索引值
	*/
	public native int getIndex(int value);

    /*! @brief  获取当前旋转值对应的索引值
     *  @return 索引值
	*/
	public native int getIndex();

    /*! @brief  获取索引值对应的旋转值
     *  @param  index : 索引值
     *  @return 旋转值
	*/
	public native int getValue(int index);

    /*! @brief  获取当前旋转值
     *  @return 旋转值
	*/
	public native int getValue();

    /*! @brief  设置旋转值
     *  @param  add  : HsTrue = 旋转加,   HsFalse = 旋转减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
	*/
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置旋转值
     *  @param  value : 旋转值
     *  @return 是否设置成功
	*/
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
	*/
    public native boolean setIndex(int index);
}
