package api.image;

/*! @brief 穿刺线
*/
public class HS_Puncture {

	// 角度模式
	public class AngleMode {
		public static final int AngleMode_20_30_40     = 0;
		public static final int AngleMode_25_35_45     = 1;
		public static final int AngleMode_30_40_50     = 2;
		public static final int AngleMode_35_45_55     = 3;
		public static final int AngleMode_40_50_60     = 4;
		public static final int AngleMode_45_55_65     = 5;
		public static final int AngleMode_50_60_70     = 6;
		public static final int AngleMode_10To80Step5  = 7;
		public static final int AngleMode_10To80Step1  = 8;
		public static final int AngleMode_Custom       = 9;
	}


    /*! @brief  设置是否打开穿刺线功能
     *  @param  show : 是否打开
     *  @return 是否设置成功
     */
    public native boolean setOpen(boolean open);

    /*! @brief  获取穿刺线功能是否打开
     *  @return 是否打开
     */
    public native boolean isOpened();

    /*! @brief  设置是否显示穿刺线深度
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
    public native boolean setShowDepth(boolean show);

    /*! @brief  获取穿刺线深度是否显示
     *  @return 是否显示
     */
    public native boolean isShowDepth();


    /*! @brief  设置穿刺线平移位置数组
     *  @param  values   : 平移位置数组
     *  @param  defValue : 默认值
     *  @return 是否设置成功
     */
    public native boolean setTranslates(double[] values);

    /*! @brief  设置穿刺线平移位置
     *  @param  add : 递加/递减
     *  @return 是否设置成功
     */
    public native boolean setTranslate(boolean add);

    /*! @brief  设置穿刺线平移位置
     *  @param  index : 索引值
     *  @return 是否设置成功
     */
    public native boolean setTranslate(int index);

    /*! @brief  设置穿刺线平移位置
     *  @param  translate : 穿刺线平移位置(mm)
     *  @return 是否设置成功
     */
    public native boolean setTranslate(double translate);

    /*! @brief  获取穿刺线平移位置索引
     *  @return 穿刺线平移位置索引
     */
    public native double getTranslateIndex();

    /*! @brief  获取穿刺线平移位置
     *  @return 穿刺线平移位置(mm)
     */
    public native double getTranslateValue();


    /*! @brief  设置穿刺线角度模式
     *  @param  mode : 角度模式
     *  @return 是否设置成功
     */
    public native boolean setAngleMode(int mode);

    /*! @brief  获取穿刺线角度模式
     *  @return 穿刺线角度模式
     */
    public native int getAngleMode();

    /*! @brief  设置穿刺线角度数组
     *  @param  values   : 角度数组
     *  @param  defValue : 默认值
     *  @return 是否设置成功
     */
    public native boolean setAngles(double[] values);

    /*! @brief  设置穿刺线角度
     *  @param  add : 递加/递减
     *  @return 是否设置成功
     */
    public native boolean setAngle(boolean add);

    /*! @brief  设置穿刺线角度
     *  @param  index : 索引值
     *  @return 是否设置成功
     */
    public native boolean setAngle(int index);

    /*! @brief  设置穿刺线角度
     *  @param  point : 穿刺线角度
     *  @return 是否设置成功
     */
    public native boolean setAngle(double angle);

    /*! @brief  获取穿刺线角度索引
     *  @return 穿刺线角度索引
     */
    public native double getAngleIndex();

    /*! @brief  获取穿刺线角度
     *  @return 穿刺线角度
     */
    public native double getAngleValue();
}
