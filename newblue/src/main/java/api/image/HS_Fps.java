package api.image;

/*! @brief 帧频控制
 */
public class HS_Fps {

    /*! @brief  获取硬件帧频
     *  @return 硬件帧频
     */
	public native double getFpsHard();

    /*! @brief  获取软件帧频
     *  @return 软件帧频
     */
	public native double getFpsSoft();

    /*! @brief  获取硬件帧时间(采集一帧所需时间)
     *  @return 硬件帧时间(ms)
     */
	public native double getFrameTimeHard();

    /*! @brief  获取软件帧时间(采集一帧所需时间)
     *  @return 软件帧时间(ms)
     */
	public native double getFrameTimeSoft();
}
