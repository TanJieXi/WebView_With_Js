package api.measure.math;

import api.measure.struct.HS_BindMath;
import api.measure.struct.HS_BindUser;
import api.type.HS_PointF;

public class HS_MathNormal {
	/*! @brief  自动计算直线距离
     *  @param  pos        : 测量项索引号
     *  @param  index      : 结果索引值(如METHOD_DISTANCE_2LINE有2个结果)
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getAutoDistance(int pos, int index);
    public native String getAutoDistanceString(int pos, int index, boolean invalidRet);

    public native double getAutoGirth(int pos, int index);
    public native String getAutoGirthString(int pos, int index, boolean invalidRet);

    public native double getAutoArea(int pos, int index);
    public native String getAutoAreaString(int pos, int index, boolean invalidRet);

    public native double getAutoVolume(int pos);
    public native String getAutoVolumeString(int pos, boolean invalidRet);

    /*! @brief  计算直线距离
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getDistanceLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getDistanceLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getDistanceLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getDistanceLine(int pos, int index);
    public native String getDistanceLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getDistanceLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getDistanceLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getDistanceLineString(int pos, int index, boolean invalidRet);

    /*! @brief  计算曲线距离
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getDistanceCurve(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int index);
    public native double getDistanceCurve(int pos, int index);
    public native String getDistanceCurveString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int index, boolean invalidRet);
    public native String getDistanceCurveString(int pos, int index, boolean invalidRet);

    /*! @brief  计算椭圆长轴
     *  @param  pt1        : 椭圆点1
     *  @param  pt2        : 椭圆点2
     *  @param  pt3        : 椭圆点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 椭圆长轴(mm)
     *  @retval public native String  : 格式化后的椭圆长轴字符串
    */
    public native double getLongAxisEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3);
    public native double getLongAxisEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3);
    public native double getLongAxisEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getLongAxisEllipse(int pos, int index);
    public native String getLongAxisEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean invalidRet);
    public native String getLongAxisEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean invalidRet);
    public native String getLongAxisEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getLongAxisEllipseString(int pos, int index, boolean invalidRet);

    /*! @brief  计算椭圆短轴
     *  @param  pt1        : 椭圆点1
     *  @param  pt2        : 椭圆点2
     *  @param  pt3        : 椭圆点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 椭圆短轴(mm)
     *  @retval public native String  : 格式化后的椭圆短轴字符串
    */
    public native double getShortAxisEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3);
    public native double getShortAxisEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3);
    public native double getShortAxisEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getShortAxisEllipse(int pos, int index);
    public native String getShortAxisEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean invalidRet);
    public native String getShortAxisEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean invalidRet);
    public native String getShortAxisEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getShortAxisEllipseString(int pos, int index, boolean invalidRet);

    /*! @brief  计算椭圆周长
     *  @param  pt1        : 椭圆点1
     *  @param  pt2        : 椭圆点2
     *  @param  pt3        : 椭圆点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 椭圆周长(mm)
     *  @retval public native String  : 格式化后的椭圆周长字符串
    */
    public native double getGirthEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3);
    public native double getGirthEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3);
    public native double getGirthEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getGirthEllipse(int pos, int index);
    public native String getGirthEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean invalidRet);
    public native String getGirthEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean invalidRet);
    public native String getGirthEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getGirthEllipseString(int pos, int index, boolean invalidRet);

    /*! @brief  计算曲线周长
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getGirthCurve(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int index);
    public native double getGirthCurve(int pos, int index);
    public native String getGirthCurveString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int index, boolean invalidRet);
    public native String getGirthCurveString(int pos, int index, boolean invalidRet);

    /*! @brief  计算矩形周长
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getGirthRect(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getGirthRect(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getGirthRect(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getGirthRect(int pos, int index);
    public native String getGirthRectString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getGirthRectString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getGirthRectString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getGirthRectString(int pos, int index, boolean invalidRet);

    /*! @brief  计算椭圆面积
     *  @param  pt1        : 椭圆点1
     *  @param  pt2        : 椭圆点2
     *  @param  pt3        : 椭圆点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 椭圆面积(cm^2)
     *  @retval public native String  : 格式化后的椭圆面积字符串
    */
    public native double getAreaEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3);
    public native double getAreaEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3);
    public native double getAreaEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getAreaEllipse(int pos, int index);
    public native String getAreaEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean invalidRet);
    public native String getAreaEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean invalidRet);
    public native String getAreaEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getAreaEllipseString(int pos, int index, boolean invalidRet);

    /*! @brief  计算曲线面积
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getAreaCurve(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int index);
    public native double getAreaCurve(int pos, int index);
    public native String getAreaCurveString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int index, boolean invalidRet);
    public native String getAreaCurveString(int pos, int index, boolean invalidRet);

    /*! @brief  计算矩形面积
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 距离值(mm)
     *  @retval public native String  : 格式化后的距离值字符串
    */
    public native double getAreaRect(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getAreaRect(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getAreaRect(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getAreaRect(int pos, int index);
    public native String getAreaRectString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getAreaRectString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getAreaRectString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getAreaRectString(int pos, int index, boolean invalidRet);

    /*! @brief  计算单椭圆体积
     *  @param  pt1        : 椭圆点1
     *  @param  pt2        : 椭圆点2
     *  @param  pt3        : 椭圆点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 单椭圆体积(ml)
     *  @retval public native String  : 格式化后的单椭圆体积字符串
    */
    public native double getVolumeEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3);
    public native double getVolumeEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3);
    public native double getVolumeEllipse(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getVolumeEllipse(int pos, int index);
    public native String getVolumeEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean invalidRet);
    public native String getVolumeEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean invalidRet);
    public native String getVolumeEllipseString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getVolumeEllipseString(int pos, int index, boolean invalidRet);

    /*! @brief  计算椭圆+直线体积
     *  @param  pt1        : 椭圆点1
     *  @param  pt2        : 椭圆点2
     *  @param  pt3        : 椭圆点3
     *  @param  pt4        : 直线端点1
     *  @param  pt5        : 直线端点5
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 椭圆+直线体积(ml)
     *  @retval public native String  : 格式化后的椭圆+直线体积字符串
    */
    public native double getVolumeEllipseLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5);
    public native double getVolumeEllipseLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5);
    public native double getVolumeEllipseLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getVolumeEllipseLine(int pos, int index);
    public native String getVolumeEllipseLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, boolean invalidRet);
    public native String getVolumeEllipseLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, boolean invalidRet);
    public native String getVolumeEllipseLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getVolumeEllipseLineString(int pos, int index, boolean invalidRet);

    /*! @brief  计算双椭圆体积
     *  @param  pt1        : 第一个椭圆点1
     *  @param  pt2        : 第一个椭圆点2
     *  @param  pt3        : 第一个椭圆点3
     *  @param  pt4        : 第二个椭圆点1
     *  @param  pt5        : 第二个椭圆点2
     *  @param  pt6        : 第二个椭圆点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 双椭圆体积(ml)
     *  @retval public native String  : 格式化后的双椭圆体积字符串
    */
    public native double getVolumeEllipse2(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6);
    public native double getVolumeEllipse2(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6);
    public native double getVolumeEllipse2(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getVolumeEllipse2(int pos, int index);
    public native String getVolumeEllipse2String(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6, boolean invalidRet);
    public native String getVolumeEllipse2String(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, boolean invalidRet);
    public native String getVolumeEllipse2String(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getVolumeEllipse2String(int pos, int index, boolean invalidRet);

    /*! @brief  计算三直线体积
     *  @param  pt1        : 第一条直线端点1
     *  @param  pt2        : 第一条直线端点2
     *  @param  pt3        : 第二条直线端点3
     *  @param  pt4        : 第二条直线端点1
     *  @param  pt5        : 第三条直线端点2
     *  @param  pt6        : 第三条直线端点3
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 三直线体积(ml)
     *  @retval public native String  : 格式化后的三直线体积字符串
    */
    public native double getVolumeLine3(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6);
    public native double getVolumeLine3(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6);
    public native double getVolumeLine3(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getVolumeLine3(int pos, int index);
    public native String getVolumeLine3String(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6, boolean invalidRet);
    public native String getVolumeLine3String(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, boolean invalidRet);
    public native String getVolumeLine3String(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getVolumeLine3String(int pos, int index, boolean invalidRet);

    /*! @brief  计算单直线体积
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 单直线体积(ml)
     *  @retval public native String  : 格式化后的单直线体积字符串
    */
    public native double getVolumeLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getVolumeLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getVolumeLine(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getVolumeLine(int pos, int index);
    public native String getVolumeLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getVolumeLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getVolumeLineString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getVolumeLineString(int pos, int index, boolean invalidRet);

    /*! @brief  计算两直线夹角
     *  @param  pt1        : 两条直线的交点
     *  @param  pt2        : 第一条直接端点
     *  @param  pt3        : 第二条直接端点
     *  @param  acuteAngle : 当夹角为钝角时,是否转换为锐角
     *  @param  angle1     : 计算第一条与水平线的夹角(HSNull = 不计算)
     *  @param  angle2     : 计算第二条与水平线的夹角(HSNull = 不计算)
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两直线夹角
     *  @retval public native String  : 格式化后的两直线夹角字符串
    */
    public native double getAngle(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean acuteAngle, Float angle1, Float angle2);
    public native double getAngle(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean acuteAngle, Float angle1, Float angle2);
    public native double getAngle(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean acuteAngle, Float angle1, Float angle2);
    public native double getAngle(int pos, int index, boolean acuteAngle, Float angle1, Float angle2);
    public native String getAngleString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, boolean acuteAngle, Float angle1, Float angle2, boolean invalidRet);
    public native String getAngleString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, boolean acuteAngle, Float angle1, Float angle2, boolean invalidRet);
    public native String getAngleString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean acuteAngle, Float angle1, Float angle2, boolean invalidRet);
    public native String getAngleString(int pos, int index, boolean acuteAngle, Float angle1, Float angle2, boolean invalidRet);

    /*! @brief  计算(M)心率
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : (M)心率
     *  @retval public native String  : 格式化后的(M)心率字符串
    */
    public native double getHeartRate(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getHeartRate(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getHeartRate(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getHeartRate(int pos, int index);
    public native String getHeartRateString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getHeartRateString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getHeartRateString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getHeartRateString(int pos, int index, boolean invalidRet);

    /*! @brief  计算(M)斜率
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : (M)斜率
     *  @retval public native String  : 格式化后的(M)斜率字符串
    */
    public native double getSlope(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getSlope(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getSlope(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getSlope(int pos, int index);
    public native String getSlopeString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getSlopeString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getSlopeString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getSlopeString(int pos, int index, boolean invalidRet);

    /*! @brief  计算(M)距离
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : (M)距离(mm)
     *  @retval public native String  : 格式化后的(M)距离字符串
    */
    public native double getMDistance(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getMDistance(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getMDistance(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getMDistance(int pos, int index);
    public native String getMDistanceString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getMDistanceString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getMDistanceString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getMDistanceString(int pos, int index, boolean invalidRet);

    /*! @brief  计算(M)时间
     *  @param  pt1        : 直接端点1
     *  @param  pt2        : 直接端点2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : (M)时间
     *  @retval public native String  : 格式化后的(M)时间字符串
    */
    public native double getMTime(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getMTime(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getMTime(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getMTime(int pos, int index);
    public native String getMTimeString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getMTimeString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getMTimeString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getMTimeString(int pos, int index, boolean invalidRet);

    /*! @brief  计算两直线比率
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两直线比率
     *  @retval public native String  : 格式化后的两直线比率字符串
    */
    public native double getRatioDistance(int pos);
    public native double getRatioDistance(double distance1, double distance2);
    public native String getRatioDistanceString(int pos, boolean invalidRet);
    public native String getRatioDistanceString(double distance1, double distance2, boolean invalidRet);

    /*! @brief  计算两直线狭窄率
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两直线狭窄率
     *  @retval public native String  : 格式化后的两直线狭窄率字符串
    */
    public native double getNarrowDistance(int pos, Boolean valid);
    public native double getNarrowDistance(double distance1, double distance2, Boolean valid);
    public native String getNarrowDistanceString(int pos, boolean invalidRet);
    public native String getNarrowDistanceString(double distance1, double distance2, boolean invalidRet);

    /*! @brief  计算两周长比率
     *  @param  girth1     : 周长1
     *  @param  girth2     : 周长2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两周长比率
     *  @retval public native String  : 格式化后的两周长比率字符串
    */
    public native double getRatioGirth(int pos);
    public native double getRatioGirth(double girth1, double girth2);
    public native String getRatioGirthString(int pos, boolean invalidRet);
    public native String getRatioGirthString(double girth1, double girth2, boolean invalidRet);

    /*! @brief  计算两周长狭窄率
     *  @param  girth1     : 周长1
     *  @param  girth2     : 周长2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两周长狭窄率
     *  @retval public native String  : 格式化后的两周长狭窄率字符串
    */
    public native double getNarrowGirth(int pos, Boolean valid);
    public native double getNarrowGirth(double girth1, double girth2, Boolean valid);
    public native String getNarrowGirthString(int pos, boolean invalidRet);
    public native String getNarrowGirthString(double girth1, double girth2, boolean invalidRet);

    /*! @brief  计算两面积比率
     *  @param  area1      : 面积1
     *  @param  area2      : 面积2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两面积比率
     *  @retval public native String  : 格式化后的两面积比率字符串
    */
    public native double getRatioArea(int pos);
    public native double getRatioArea(double area1, double area2);
    public native String getRatioAreaString(int pos, boolean invalidRet);
    public native String getRatioAreaString(double area1, double area2, boolean invalidRet);

    /*! @brief  计算两面积狭窄率
     *  @param  area1      : 面积1
     *  @param  area2      : 面积2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 两面积狭窄率
     *  @retval public native String  : 格式化后的两面积狭窄率字符串
    */
    public native double getNarrowArea(int pos, Boolean valid);
    public native double getNarrowArea(double area1, double area2, Boolean valid);
    public native String getNarrowAreaString(int pos, boolean invalidRet);
    public native String getNarrowAreaString(double area1, double area2, boolean invalidRet);
}
