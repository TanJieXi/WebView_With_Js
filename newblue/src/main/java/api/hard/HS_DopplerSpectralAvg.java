package api.hard;

/*! @brief 多普勒控制-频谱平均
*/
public class HS_DopplerSpectralAvg {

    /*! @brief  获取频谱平均可控制个数
     *  @return 频谱平均可控制个数
    */
	public native int getSize();

    /*! @brief  获取频谱平均对应的索引值
     *  @param  value : 频谱平均值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前频谱平均对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的频谱平均值
     *  @param  index : 索引值
     *  @return 频谱平均值
    */
	public native int getValue(int index);

    /*! @brief  获取当前频谱平均值
     *  @return 频谱平均值
    */
	public native int getValue();

    /*! @brief  设置频谱平均
     *  @param  add  : HSTrue = 频谱平均加, HSFalse = 频谱平均减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置频谱平均值
     *  @param  value : 频谱平均值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
