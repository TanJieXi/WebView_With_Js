package api.hard;

/*! @brief 多普勒控制-修正角度
*/
public class HS_DopplerCorrectionAngle {

    /*! @brief  获取修正角度可控制个数
     *  @return 修正角度可控制个数
    */
	public native int getSize();

    /*! @brief  获取修正角度对应的索引值
     *  @param  value : 修正角度值
     *  @return 索引值
    */
	public native int getIndex(double value);

    /*! @brief  获取当前修正角度对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的修正角度值
     *  @param  index : 索引值
     *  @return 修正角度值
    */
	public native double getValue(int index);

    /*! @brief  获取当前修正角度值
     *  @return 修正角度值
    */
	public native double getValue();

    /*! @brief  设置修正角度
     *  @param  add  : HSTrue = 修正角度加, HSFalse = 修正角度减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置修正角度值
     *  @param  value : 修正角度值
     *  @return 是否设置成功
    */
    public native boolean setValue(double value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
