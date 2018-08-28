package api.image;

/*! @brief 噪声抑制控制
*/
public class HS_Denoise {

    /*! @brief  获取噪声抑制可控制个数
     *  @return 噪声抑制可控制个数
    */
	public native int getSize();

    /*! @brief  获取噪声抑制对应的索引值
     *  @param  value : 噪声抑制值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前噪声抑制对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的噪声抑制值
     *  @param  index : 索引值
     *  @return 噪声抑制值
    */
	public native int getValue(int index);

    /*! @brief  获取当前噪声抑制值
     *  @return 噪声抑制值
    */
	public native int getValue();

    /*! @brief  设置噪声抑制
     *  @param  add  : HSTrue = 噪声抑制加, HSFalse = 噪声抑制减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置噪声抑制值
     *  @param  value : 噪声抑制值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
