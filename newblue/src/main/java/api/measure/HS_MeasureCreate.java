package api.measure;

import api.measure.struct.HS_BindUser;

public class HS_MeasureCreate {
	public class MeasureItemString {
		public static final String HS_CLASSNAME_MeasureLine        = "H_MeasureLine";
		public static final String HS_CLASSNAME_MeasureLine2       = "H_MeasureLine2";
		public static final String HS_CLASSNAME_MeasureLine3       = "H_MeasureLine3";
		public static final String HS_CLASSNAME_MeasureLine4       = "H_MeasureLine4";
		public static final String HS_CLASSNAME_MeasureLineM       = "H_MeasureLineM";
		public static final String HS_CLASSNAME_MeasureCurve       = "H_MeasureCurve";
		public static final String HS_CLASSNAME_MeasureCurve2      = "H_MeasureCurve2";
		public static final String HS_CLASSNAME_MeasureEllipse     = "H_MeasureEllipse";
		public static final String HS_CLASSNAME_MeasureEllipse2    = "H_MeasureEllipse2";
		public static final String HS_CLASSNAME_MeasureEllipseLine = "H_MeasureEllipseLine";
		public static final String HS_CLASSNAME_MeasureCurveClose  = "H_MeasureCurveClose";
		public static final String HS_CLASSNAME_MeasureCurveClose2 = "H_MeasureCurveClose2";
		public static final String HS_CLASSNAME_MeasureRect        = "H_MeasureRect";
		public static final String HS_CLASSNAME_MeasureRect2       = "H_MeasureRect2";
		public static final String HS_CLASSNAME_MeasureAngle       = "H_MeasureAngle";
		public static final String HS_CLASSNAME_MeasureAngle2      = "H_MeasureAngle2";
		public static final String HS_CLASSNAME_MeasureArrow       = "H_MeasureArrow";
		public static final String HS_CLASSNAME_MeasureCaret       = "H_MeasureCaret";
	}
	
	/*! @brief  创建测量项
     *  @param  className : 测量项类名(必须使用上述定义范围内的类名，区分大小写)
     *  @param  idCode    : 标识本测量项的唯一识别码(不区分大小写)
     *  @return 是否成功
     *  @note   普通测量       : idCode用于客户端区别测量项的标志，可任意设置
     *  @note   产科测量       : idCode用于标识产科测量项类型，必须设置为"产科标识\部位\类型"，如"OBS\Human\BPD"、"OBS\Canine\BD"等
     *  @note   产科多胞胎测量 : idCode用于标识产科测量项类型，必须设置为"产科标识\部位\类型\胎数"，如"OBS\Human\BPD\1"、"OBS\Human\BPD\2"等(胎数值从1开始)
     */
	public native boolean createMeasure(String className, String idCode);

    /*! @brief  设置当前测量项的绑定用户配置参数
     *  @param  bindUser : 绑定用户配置参数
     */
	public native void setBindUser(HS_BindUser bindUser);

    /*! @brief  设置索引值对应测量项的绑定用户配置参数
     *  @param  index    : 测量项索引值
     *  @param  bindUser : 绑定用户配置参数
     */
	public native void setBindUser(int index, HS_BindUser bindUser);
}
