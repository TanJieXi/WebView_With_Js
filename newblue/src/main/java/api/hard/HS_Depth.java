package api.hard;

public class HS_Depth {

    /*! @brief  设置是否显示横向标尺
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setHorzShow(boolean show);

    /*! @brief  设置是否显示纵向标尺
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setVertShow(boolean show);

    /*! @brief  设置是否时间横向标尺(M超横向标尺)
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setHorzShowTime(boolean show);


    /*! @brief  获取深度可控制次数
     *  @return 深度可控制次数
	*/
	public native int getSize();

    /*! @brief  获取深度值对应的索引值
     *  @param  value : 深度值(mm)
     *  @return 索引值
	*/
	public native int getIndex(int value);

    /*! @brief  获取当前深度对应的索引值
     *  @return 索引值
	*/
	public native int getIndex();

    /*! @brief  获取索引值对应的深度值
     *  @param  index : 索引值
     *  @return 深度值(mm)
	*/
	public native int getValue(int index);

    /*! @brief  获取当前深度值
     *  @return 深度值(mm)
    */
	public native int getValue();

    /*! @brief  设置深度
     *  @param  add  : HsTrue = 深度加,   HsFalse = 深度减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
	*/
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置深度值
     *  @param  value : 深度值(mm)
     *  @return 是否设置成功
	*/
    public native boolean setValue(int value);

    /*! @brief  设置深度索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
	*/
    public native boolean setIndex(int index);
}
