package api.os;

public class HS_Switch {
	public class DscOptimizeMode {
		public static final int ModeNone       = 0;	// 普通
		public static final int ModeMulThread  = 1;	// 多线程加速
		public static final int ModeGpu        = 2;	// GPU加速
	}
	
    /*! @brief  设置DSC优化模式
     *  @param  mode : DSC优化模式
     *  @param  arg  : 优化模式参数
     *  @note   当mode为ModeMulThread时,arg为多线程个数
     *  @note   当mode为ModeGpu时,arg为
     */
	public native void setDscOptimizeMode(int mode, int args);
	
	/*! @brief  设置是否优化覆盖层显示
     *  @param  show : 是否显示覆盖层
     *  @note   当设置不显示覆盖层时,覆盖层需要用户根据当前环境自行绘制
     */
	public native void setOverlayOptimize(boolean optimize);

    /*! @brief  设置是否显示覆盖层
     *  @param  show : 是否显示覆盖层
     *  @note   当设置不显示覆盖层时,覆盖层需要用户根据当前环境自行绘制
     */
	public native void setShowOverlay(boolean show);
}
