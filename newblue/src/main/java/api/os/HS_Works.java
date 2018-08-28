package api.os;

/*! @brief 工作线程配置  */
public class HS_Works {

	/*! @brief  模式类  */
	public class Work {
		public static final int SAMPLE  = 0;
		public static final int PROCESS = 1;
		public static final int MAX     = 2;
	}
	
	
	/*! @brief  启动读线程
     *  @param  flag : 读线程标志
     *  @retval val = HSTrue, 成功
     *  @retval val = HSFalse,失败
     */
    public native boolean setStart(int flag);

    /*! @brief  停止读线程
     *  @param  flag : 读线程标志
     *  @retval val = HSTrue, 成功
     *  @retval val = HSFalse,失败
     */
    public native boolean setStop(int flag);

    /*! @brief  停止所有读/写线程
     */
    public native void setStop();

    /*! @brief  获取读线程运行状态
     *  @param  flag : 读线程标志
     *  @retval val = HSTrue, 正在运行
     *  @retval val = HSFalse,未运行
     */
    public native boolean isRun(int flag);

    /*! @brief  配置线程是否正在配置
     *  @return 是否正在配置
     */
    public native boolean isConfiging();

    /*! @brief  配置线程是否配置完成
     *  @return 是否配置完成
     */
    public native boolean isConfigFinish();
}
