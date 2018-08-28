package api.hard;

/*! @brief 多普勒控制-基线
*/
public class HS_DopplerBaseLine {

    /*! @brief  获取基线可控制个数
     *  @return 基线可控制个数
    */
	public native int getSize();

    /*! @brief  获取基线对应的索引值
     *  @param  value : 基线值(百分比)
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前基线对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的基线值
     *  @param  index : 索引值
     *  @return 基线值(百分比)
    */
	public native int getValue(int index);

    /*! @brief  获取当前基线值
     *  @return 基线值(百分比)
    */
	public native int getValue();

    /*! @brief  设置基线
     *  @param  add  : HSTrue = 基线加,   HSFalse = 基线减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置基线值
     *  @param  value : 基线值(百分比)
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
