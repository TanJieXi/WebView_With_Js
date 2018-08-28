package api.os;

import api.type.HS_Point;
import api.type.HS_Rect;

public class HS_Mode {
	
	/*! @brief  模式类  */
	public class Mode {
		public static final int MODE_NULL     = -1;	//<! 空模式
		public static final int MODE_B        = 0;	//<! B模式
		public static final int MODE_BB       = 1;	//<! BB模式
		public static final int MODE_4B       = 2;	//<! 4B模式
		public static final int MODE_9B       = 3;	//<! 9B模式
		public static final int MODE_BM       = 4;	//<! BM模式
		public static final int MODE_BMSCROLL = 5;	//<! BM模式
		public static final int MODE_M        = 6;	//<! M模式
		public static final int MODE_MSCROLL  = 7;	//<! M模式
		public static final int MODE_SPACE    = 8;	//<! 空间符合模式
		public static final int MODE_CFM      = 9;	//<! CFM模式
		public static final int MODE_DPDI     = 10;	//<! DPDI模式
		public static final int MODE_PDI      = 11;	//<! PDI模式
		public static final int MODE_P        = 12;	//<! P模式
		public static final int MODE_W        = 13;	//<! W模式
		public static final int MODE_PW       = 14;	//<! PW模式
		public static final int MODE_MAX      = 15;	//<! 最大模式
	}

    /*! @brief  设置模式
     *  @param  type : 模式类型
     *  @return 是否设置成功
     */
	public native boolean setMode(int mode);

    /*! @brief  模式类型
     *  @param  type : 模式类别
     *  @return 是否设置成功
     */
	public native int getMode();

    /*! @brief  是否为指定模式
     *  @param  mode : 模式类别
     *  @return 是否为指定模式
     */
	public native boolean isMode(int mode);


    /*! @brief  是否为B模式
     *  @return 是否为B模式
     */
	public native boolean isModeB();

    /*! @brief  是否为BB模式
     *  @return 是否为BB模式
     */
	public native boolean isModeBB();

    /*! @brief  是否为4B模式
     *  @return 是否为4B模式
     */
	public native boolean isMode4B();

    /*! @brief  是否为9B模式
     *  @return 是否为9B模式
     */
	public native boolean isMode9B();

    /*! @brief  是否为BM模式
     *  @return 是否为BM模式
     */
	public native boolean isModeBM();

    /*! @brief  是否为BMScroll模式
     *  @return 是否为BMScroll模式
     */
	public native boolean isModeBMScroll();

    /*! @brief  是否为M模式
     *  @return 是否为M模式
     */
	public native boolean isModeM();

    /*! @brief  是否为MScroll模式
     *  @return 是否为MScroll模式
     */
	public native boolean isModeMScroll();

    /*! @brief  是否为Space模式
     *  @return 是否为Space模式
     */
	public native boolean isModeSpace();

    /*! @brief  是否为CFM模式
     *  @return 是否为CFM模式
     */
	public native boolean isModeCFM();

    /*! @brief  是否为DPDI模式
     *  @return 是否为DPDI模式
     */
	public native boolean isModeDPDI();

    /*! @brief  是否为PDI模式
     *  @return 是否为PDI模式
     */
	public native boolean isModePDI();

    /*! @brief  是否为P模式
     *  @return 是否为P模式
     */
	public native boolean isModeP();

    /*! @brief  是否为W模式
     *  @return 是否为W模式
     */
	public native boolean isModeW();

    /*! @brief  是否为PW模式
     *  @return 是否为PW模式
     */
	public native boolean isModePW();


    /*! @brief  是否包含B模式(MODE_B/MODE_BB/MODE_4B/MODE_9B/MODE_BM/MODE_BMScroll/MODE_SPACE/MODE_CFM/MODE_DPDI/MODE_PDI/MODE_P/MODE_PW)
     *  @return 是否包含B模式
     */
	public native boolean hasB();

    /*! @brief  是否为多B模式(MODE_BB/MODE_4B/MODE_9B)
     *  @return 是否为多B模式
     */
	public native boolean mulB();

    /*! @brief  是否包含M模式(MODE_BM/MODE_BMSCROLL/MODE_M/MODE_MSCROLL)
     *  @return 是否包含M模式
     */
	public native boolean hasM();

    /*! @brief  是否为单M模式(MODE_M/MODE_MSCROLL)
     *  @return 是否为单M模式
     */
	public native boolean isM();

    /*! @brief  是否为单P模式(MODE_P)
     *  @return 是否为单P模式
     */
	public native boolean isP();

    /*! @brief  是否为单W模式(MODE_W)
     *  @return 是否为单W模式
     */
	public native boolean isW();


    /*! @brief  判断点point位置是否在B图像内
     *  @return 是否在B图像内
     */
	public native boolean inB(HS_Point point);

    /*! @brief  判断点point位置是否在M图像内
     *  @return 是否在M图像内
     */
	public native boolean inM(HS_Point point);

    /*! @brief  判断点point位置是否在P图像内
     *  @return 是否在P图像内
     */
	public native boolean inP(HS_Point point);

    /*! @brief  判断点point位置是否在W图像内
     *  @return 是否在W图像内
     */
	public native boolean inW(HS_Point point);


    /*! @brief  判断点point位置是否在B图像内
     *  @return 是否在B图像内
     */
	public native HS_Rect getRect(HS_Point point);

    /*! @brief  获取模式矩形位置
     *  @param  block : 模式块序号(block=-1,返回总矩形; 否则返回各子块矩形)
     *  @return 模式矩形位置
     *  @note   MODE_B        : 返回B矩形
     *  @note   MODE_BB       : block=0时返回B1矩形; block=1时返回B2矩形
     *  @note   MODE_4B       : block=0时返回B1矩形; block=1时返回B2矩形; block=2时返回B3矩形; block=3时返回B4矩形
     *  @note   MODE_9B       : block=0时返回B1矩形; block=1时返回B2矩形;        ...         ; block=8时返回B9矩形
     *  @note   MODE_BM       : block=0时返回B矩形;  block=1时返回M矩形
     *  @note   MODE_BMScroll : block=0时返回B矩形;  block=1时返回M矩形
     *  @note   MODE_B        : 返回M矩形
     *  @note   MODE_MSCROLL  : 返回M矩形
     *  @note   MODE_SPACE    : 返回B矩形
     *  @note   MODE_CFM      : 返回B矩形
     *  @note   MODE_DPDI     : 返回B矩形
     *  @note   MODE_PDI      : 返回B矩形
     *  @note   MODE_P        : 返回B矩形
     *  @note   MODE_W        : 返回B矩形
     *  @note   MODE_PW       : block=0时返回B矩形;  block=1时返回W矩形
     */
	public native HS_Rect getRect(int block);


    /*! @brief  获取M图像时间戳
     *  @return M图像时间戳
     */
	public native long[] getMStamp();
}
