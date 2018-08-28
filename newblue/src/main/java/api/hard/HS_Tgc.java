package api.hard;

/*! @brief TGC控制
*/
public class HS_Tgc {

    /*! @brief  获取TGC个数可控制个数
     *  @return TGC个数可控制个数
    */
	public native int getSize();

    /*! @brief  获取所有TGC项的值列表
     *  @return 所有TGC项的值列表
    */
	public native int[] getValue();

    /*! @brief  获取所有TGC项的索引值列表
     *  @return 所有TGC项的索引值列表
    */
	public native int[] getIndex();

    /*! @brief  设置所有TGC项值
     *  @param  value : 所有TGC项的值列表
     *  @return 是否设置成功
    */
    public native boolean setValue(int[] value);

    /*! @brief  设置所有TGC项索引值
     *  @param  index : 所有TGC项的索引值列表
     *  @return 是否设置成功
    */
    public native boolean setIndex(int[] index);

    /*! @brief  获取TGC可控制个数
     *  @return TGC可控制个数
    */
	public native int getSize(int tgc);

    /*! @brief  获取TGC对应的索引值
     *  @param  value : TGC
     *  @return 索引值
    */
	public native int getIndex(int tgc, int value);

    /*! @brief  获取当前TGC索引值
     *  @return 索引值
    */
	public native int getIndex(int tgc);

    /*! @brief  获取TGC索引对应的TGC值
     *  @param  index : 索引值
     *  @return TGC值
    */
	public native int getValue(int tgc, int index);

    /*! @brief  获取当前TGC值
     *  @return TGC值
    */
	public native int getValue(int tgc);

    /*! @brief  设置TGC
     *  @param  add  : HsTrue = TGC加,    HsFalse = TGC减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(int tgc, boolean add, boolean loop);

    /*! @brief  设置TGC值
     *  @param  value : TGC值
     *  @return 是否设置成功
    */
	public native boolean setValue(int tgc, int value);

    /*! @brief  设置TGC索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int tgc, int index);
	
    /*! @brief  设置是否显示TGC曲线
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  获取TGC曲线是否显示
     *  @return 是否显示
     */
    public native boolean isShowed();

    /*! @brief  设置是否显示TGC节点短线
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
    public native boolean setShowNodes(boolean show);

    /*! @brief  获取TGC节点短线是否显示
     *  @return 是否显示
     */
    public native boolean isShowNodes();

    /*! @brief  设置是否显示TGC深度
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
    public native boolean setShowDepth(boolean show);

    /*! @brief  获取TGC深度是否显示
     *  @return 是否显示
     */
    public native boolean isShowDepth();


    /*! @brief  设置是否自动隐藏TGC曲线
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
    public native int getAutoTime();
}
