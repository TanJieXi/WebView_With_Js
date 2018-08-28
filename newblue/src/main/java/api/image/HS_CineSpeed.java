package api.image;

/*! @brief 电影速度控制
*/
public class HS_CineSpeed {

    /*! @brief  获取电影速度可控制个数
     *  @return 电影速度可控制个数
    */
	public native int getSize();

    /*! @brief  获取电影速度对应的索引值
     *  @param  value : 电影速度值
     *  @return 索引值
    */
	public native int getIndex(double value);

    /*! @brief  获取当前电影速度对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的电影速度值
     *  @param  index : 索引值
     *  @return 电影速度值
    */
	public native double getValue(int index);

    /*! @brief  获取当前电影速度值
     *  @return 电影速度值
    */
	public native double getValue();

    /*! @brief  设置电影速度
     *  @param  add  : HSTrue = 电影速度加, HSFalse = 电影速度减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(double add, double loop);

    /*! @brief  设置电影速度值
     *  @param  value : 电影速度值
     *  @return 是否设置成功
    */
    public native boolean setValue(double value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
