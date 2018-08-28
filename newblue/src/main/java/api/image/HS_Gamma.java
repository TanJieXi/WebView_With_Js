package api.image;

/*! @brief 伽玛校正控制
*/
public class HS_Gamma {

    /*! @brief  获取伽玛校正可控制个数
     *  @return 伽玛校正可控制个数
    */
	public native int getSize();

    /*! @brief  获取伽玛校正对应的索引值
     *  @param  value : 伽玛校正值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前伽玛校正对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的伽玛校正值
     *  @param  index : 索引值
     *  @return 伽玛校正值
    */
	public native int getValue(int index);

    /*! @brief  获取当前伽玛校正值
     *  @return 伽玛校正值
    */
	public native int getValue();

    /*! @brief  设置伽玛校正
     *  @param  add  : HSTrue = 伽玛校正加, HSFalse = 伽玛校正减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置伽玛校正值
     *  @param  value : 伽玛校正值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
