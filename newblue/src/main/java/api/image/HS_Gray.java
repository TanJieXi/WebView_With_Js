package api.image;

/*! @brief 灰阶控制
*/
public class HS_Gray {

    /*! @brief  设置是否显示灰阶曲线
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief 获取灰阶曲线是否显示
     *  @return 是否显示
     */
	public native boolean isShowed();


    /*! @brief  获取灰阶可控制个数
     *  @return 灰阶可控制个数
    */
	public native int getSize();

    /*! @brief  获取灰阶对应的索引值
     *  @param  value : 灰阶值
     *  @return 索引值
    */
    public native int getIndex(int value);

    /*! @brief  获取当前灰阶对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的灰阶值
     *  @param  index : 索引值
     *  @return 灰阶值
    */
    public native int getValue(int index);

    /*! @brief  获取当前灰阶值
     *  @return 灰阶值
    */
    public native int getValue();

    /*! @brief  设置灰阶
     *  @param  add  : HSTrue = 灰阶加,   HSFalse = 灰阶减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置灰阶值
     *  @param  value : 灰阶值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);


    /*! @brief  设置是否自动隐藏灰阶曲线
     *  @param  autoHide : 是否自动隐藏
     *  @return 是否设置成功
     */
    public native boolean setAutoHide(boolean autoHide);

    /*! @brief  获取是否自动隐藏
     *  @return 是否自动隐藏
    */
    public native boolean isAutoHide();

    /*! @brief  设置显示毫秒数
     *  @param  msTime : 显示毫秒数
     *  @return 是否设置成功
     */
    public native boolean setAutoTime(int msTime);

    /*! @brief  获取显示毫秒数
     *  @return 显示毫秒数(ms)
    */
    public native boolean isAutoTime();
}
