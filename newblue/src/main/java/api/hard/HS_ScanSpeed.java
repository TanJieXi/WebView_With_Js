package api.hard;

/*! @brief 扫描速度控制
*/
public class HS_ScanSpeed {

    /*! @brief  获取扫描速度可控制个数
     *  @return 扫描速度可控制个数
    */
	public native int getSize();

    /*! @brief  获取扫描速度对应的索引值
     *  @param  value : 扫描速度
     *  @return 索引值
    */
    public native int getIndex(int value);

    /*! @brief  获取当前扫描速度对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取扫描速度索引对应的扫描速度值
     *  @param  index : 索引值
     *  @return 扫描速度值
    */
    public native int getValue(int index);

    /*! @brief  获取当前扫描速度值
     *  @return 扫描速度值
    */
    public native int getValue();

    /*! @brief  设置扫描速度索引值
     *  @param  add  : HsTrue = 扫描速度加, HsFalse = 扫描速度减
     *  @param  loop : HsTrue = 循环加减,   HsFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置扫描速度值
     *  @param  value : 扫描速度值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置扫描速度索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
