package api.hard;

/*! @brief 线密度控制
*/
public class HS_LineDensity {

    //<! 线密度
	public class Density {
		public static final int LOW     = 0;
		public static final int MIDDLE  = 1;
		public static final int HIGH    = 2;
	}

    /*! @brief  获取线密度可控制个数
     *  @return 线密度可控制个数
    */
	public native int getSize();

    /*! @brief  获取线密度对应的索引值
     *  @param  value : 线密度值
     *  @return 索引值
    */
    public native int getIndex(int value);

    /*! @brief  获取当前线密度对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的线密度值
     *  @param  index : 索引值
     *  @return 线密度值
    */
    public native int getValue(int index);

    /*! @brief  获取当前线密度值
     *  @return 线密度值
    */
    public native int getValue();

    /*! @brief  设置线密度
     *  @param  add  : HsTrue = 位置加,   HsFalse = 位置减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置线密度值
     *  @param  value : 线密度值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置线密度索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
