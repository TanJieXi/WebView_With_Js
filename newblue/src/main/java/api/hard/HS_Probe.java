package api.hard;

/*! @brief 探头参数
*/
public class HS_Probe {

	/*! @brief 探头信息  */
	public class Info{
		public boolean enable;		//<! 是否可用
		public String name;		//<! 探头名
		public int     cells;		//<! 阵元数
		public int     radius;		//<! 曲率半径
		public double  interval;	//<! 阵元间距
		public double  freq;		//<! 中心频率
	}


    /*! @brief  设置探头方向是否显示
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);


    /*! @brief  获取可使用的探头数
     *  @return 可使用的探头数
     */
	public native int getProbes();

    /*! @brief  获取探头信息
     *  @param  index : 探头索引值
     *  @return 探头信息
     */
	public native Info getInfo(int index);

    /*! @brief  获取当前探头信息
     *  @return 当前探头信息
     */
	public native Info getInfo();

    /*! @brief  设置探头索引值
     *  @param index : 探头索引值
     *  @return 是否设置成功
     */
    public native boolean setProbes(int index);
}
