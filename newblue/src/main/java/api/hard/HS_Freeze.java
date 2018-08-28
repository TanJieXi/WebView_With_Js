package api.hard;

/*! @brief 设备状态控制
*/
public class HS_Freeze {

    /*! @brief  设置是否显示冻结标识
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  获取冻结标识是否显示
     *  @return 是否显示
     */
    public native boolean isShowed();


    /*! @brief  反转设备状态
     *  @return 是否设置成功
    */
    public native boolean setReverse();

    /*! @brief  设置设备为解冻状态
     *  @return 是否设置成功
    */
    public native boolean setRun();

    /*! @brief  设置设备为冻结状态
     *  @return 是否设置成功
    */
    public native boolean setFreeze();

    /*! @brief  获取当前设备是否为解冻状态
     *  @retval val = HsTrue,  解冻
     *  @retval val = HsFalse, 冻结
    */
    public native boolean isRun();

    /*! @brief  获取当前设备是否为冻结状态
     *  @retval val = HsTrue,  冻结
     *  @retval val = HsFalse, 解冻
    */
    public native boolean isFreeze();
}
