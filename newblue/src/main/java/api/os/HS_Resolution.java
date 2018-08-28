package api.os;

import api.type.HS_Point;
import api.type.HS_PointF;

public class HS_Resolution {

    /*! @brief  获取源图坐标
     *  @param  point = 屏幕像素坐标
     *  @return 源图坐标
     */
	public native HS_Point screenPixelToScanlinePixel(HS_Point point);

    /*! @brief  获取源图坐标
     *  @param  point = 屏幕像素坐标
     *  @return 源图坐标
     */
	public native HS_PointF screenPixelToScanlineDepth(HS_Point point);

    /*! @brief  获取屏幕像素坐标
     *  @param  point = 源图坐标
     *  @return 屏幕像素坐标
     */
    public native HS_Point scanlinePixelToScreenPixel(int block, HS_Point point);

    /*! @brief  获取屏幕像素坐标
     *  @param  point = 源图坐标
     *  @return 屏幕像素坐标
     */
    public native HS_Point scanlineDepthToScreenPixel(int block, HS_PointF point);


    /*! @brief  获取B图DSC左边界值
     *  @param  action = 活动位置
     *  @return B图DSC左边界值
     *  @note   本函数将对action进行合法处理
     */
    public native int[] getDsc_b_l(int action);

    /*! @brief  获取B图DSC左边界值
     *  @param  point = 图像内的坐标
     *  @return B图DSC左边界值
     *  @note   本函数将对action进行合法处理
     */
    public native int[] getDsc_b_l(HS_Point point);

    /*! @brief  获取B图DSC右边界值
     *  @param  action = 活动位置
     *  @return B图DSC右边界值
     *  @note   本函数将对action进行合法处理
     */
    public native int[] getDsc_b_r(int action);

    /*! @brief  获取B图DSC右边界值
     *  @param  point = 图像内的坐标
     *  @return B图DSC右边界值
     *  @note   本函数将对action进行合法处理
     */
    public native int[] getDsc_b_r(HS_Point point);

    /*! @brief  获取每一条线在图像中的开始坐标(线数=cell*2)
     *  @param  action = 活动位置
     *  @return 每一条线在图像中的开始坐标(线数=cell*2)
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_t(int action);

    /*! @brief  获取每一条线在图像中的开始坐标(线数=cell*2)
     *  @param  point = 图像内的坐标
     *  @return 每一条线在图像中的开始坐标(线数=cell*2)
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_t(HS_Point point);

    /*! @brief  获取每一条线在图像中的结束坐标(线数=cell*2)
     *  @param  action = 活动位置
     *  @return 每一条线在图像中的结束坐标(线数=cell*2)
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_b(int action);

    /*! @brief  获取每一条线在图像中的结束坐标(线数=cell*2)
     *  @param  point = 图像内的坐标
     *  @return 每一条线在图像中的结束坐标(线数=cell*2)
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_b(HS_Point point);

    /*! @brief  获取存放最左侧深度(cm)点坐标
     *  @param  action = 活动位置
     *  @return 存放最左侧深度(cm)点坐标
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_depth_l(int action);

    /*! @brief  获取存放最左侧深度(cm)点坐标
     *  @param  point = 图像内的坐标
     *  @return 存放最左侧深度(cm)点坐标
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_depth_l(HS_Point point);

    /*! @brief  获取存放最右侧深度(cm)点坐标
     *  @param  action = 活动位置
     *  @return 存放最右侧深度(cm)点坐标
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_depth_r(int action);

    /*! @brief  获取存放最右侧深度(cm)点坐标
     *  @param  point = 图像内的坐标
     *  @return 存放最右侧深度(cm)点坐标
     *  @note   本函数将对action进行合法处理
     */
    public native HS_Point[] getDsc_b_depth_r(HS_Point point);

    /*! @brief  获取凸阵探头最凹处离图像顶部像素值
     *  @param  action = 活动位置
     *  @return 凸阵探头最凹处离图像顶部像素值
     *  @note   本函数将对action进行合法处理
     */
    public native double getDsc_b_chordals(int action);

    /*! @brief  获取凸阵探头最凹处离图像顶部像素值
     *  @param  point = 图像内的坐标
     *  @return 凸阵探头最凹处离图像顶部像素值
     */
    public native double getDsc_b_chordals(HS_Point point);


    /*! @brief  获取B模式横向分辨率
     *  @param  action = 活动位置
     *  @return B模式横向分辨率
     *  @note   本函数将对action进行合法处理
     */
    public native double getBHorz(int action);

    /*! @brief  获取B模式横向分辨率
     *  @param  point = 图像内的坐标
     *  @return B模式横向分辨率
     */
	public native double getBHorz(HS_Point point);
	
    /*! @brief  获取B模式纵向分辨率
     *  @param  action = 活动位置
     *  @return B模式纵向分辨率
     *  @note   本函数将对action进行合法处理
     */
    public native double getBVert(int action);

    /*! @brief  获取B模式纵向分辨率
     *  @param  point = 图像内的坐标
     *  @return B模式纵向分辨率
     */
    public native double getBVert(HS_Point point);

    /*! @brief  获取M模式横向分辨率
     *  @param  action = 活动位置
     *  @return M模式横向分辨率
     *  @note   本函数将对action进行合法处理
     */
    public native double getMHorz(int action);

    /*! @brief  获取M模式横向分辨率
     *  @param  point = 图像内的坐标
     *  @return M模式横向分辨率
     */
    public native double getMHorz(HS_Point point);

    /*! @brief  获取M模式纵向分辨率
     *  @param  action = 活动位置
     *  @return M模式纵向分辨率
     *  @note   本函数将对action进行合法处理
     */
    public native double getMVert(int action);

    /*! @brief  获取M模式纵向分辨率
     *  @param  point = 图像内的坐标
     *  @return M模式纵向分辨率
     */
    public native double getMVert(HS_Point point);
}
