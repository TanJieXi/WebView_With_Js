package api.hard;

/*! @brief TGC位置控制
 */
public class HS_TgcPos {

    /*! @brief  获取TGC可控制个数
     *  @return TGC可控制个数
    */
	public native int getSize();

    /*! @brief  获取TGC对应的索引值
     *  @param  value : TGC
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前TGC索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取TGC索引对应的TGC值
     *  @param  index : 索引值
     *  @return TGC值
    */
	public native int getValue(int index);

    /*! @brief  获取当前TGC值
     *  @return TGC值
    */
	public native int getValue();

    /*! @brief  设置TGC
     *  @param  add  : HsTrue = TGC加,    HsFalse = TGC减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置TGC值
     *  @param  value : TGC值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置TGC索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
