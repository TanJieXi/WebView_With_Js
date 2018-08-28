package api.image;

/*! @brief 超声碎石定位线
*/
public class HS_Location {

    /*! @brief 设置是否打开定位线功能
     *  @param open : 是否打开
     *  @return 是否设置成功
     */
	public native boolean setOpen(boolean open);

    /*! @brief 获取定位线功能是否打开
     *  @return 是否打开
     */
    public native boolean isOpened();


    /*! @brief  获取定位线角度可控制个数
     *  @return 定位线角度可控制个数
    */
    public native int getSize();

    /*! @brief  获取定位线角度对应的索引值
     *  @param  value : 定位线角度值
     *  @return 索引值
    */
    public native int getIndex(double value);

    /*! @brief  获取当前定位线角度对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的定位线角度值
     *  @param  index : 索引值
     *  @return 定位线角度值
    */
    public native double getValue(int index);

    /*! @brief  获取当前定位线角度值
     *  @return 定位线角度值
    */
    public native double getValue();

    /*! @brief  设置定位线角度
     *  @param  add  : HSTrue = 定位线角度加, HSFalse = 定位线角度减
     *  @param  loop : HSTrue = 循环加减,     HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置定位线角度值
     *  @param  value : 定位线角度值
     *  @return 是否设置成功
    */
    public native boolean setValue(double value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
