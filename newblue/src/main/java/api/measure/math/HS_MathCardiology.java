package api.measure.math;

import api.measure.struct.HS_BindMath;
import api.measure.struct.HS_BindUser;
import api.measure.struct.HS_CUBE_TEICHHOLZ;
import api.type.HS_PointF;

public class HS_MathCardiology {
    /*! @brief  计算体表面积(许文生氏公式)
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 体表面积(m^2)
     *  @retval HSStrUtf8  : 格式化后的体表面积字符串
    */
	public native double getBSA(double height, double weight);
    public native String getBSAString(HS_BindMath bindMath, HS_BindUser bindUser, double height, double weight, boolean invalidRet);

    /*! @brief  计算心肌重量
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 心肌重量
     *  @retval HSStrUtf8  : 格式化后的心肌重量字符串
    */
    public native double getLVMW(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6);
    public native double getLVMW(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6);
    public native double getLVMW(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native String getLVMWString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6, boolean invalidRet);
    public native String getLVMWString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, boolean invalidRet);
    public native String getLVMWString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);

    /*! @brief  计算重量指数
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 重量指数
     *  @retval HSStrUtf8  : 格式化后的重量指数字符串
    */
    public native double getLVMWI(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6, double height, double weight);
    public native double getLVMWI(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, double height, double weight);
    public native double getLVMWI(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, double height, double weight);
    public native String getLVMWIString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6, double height, double weight, boolean invalidRet);
    public native String getLVMWIString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, double height, double weight, boolean invalidRet);
    public native String getLVMWIString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, double height, double weight, boolean invalidRet);

    /*! @brief  计算二尖瓣口流量
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 二尖瓣口流量
     *  @retval HSStrUtf8  : 格式化后的二尖瓣口流量字符串
    */
    public native double getQMV(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2);
    public native double getQMV(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2);
    public native double getQMV(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native String getQMVString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, boolean invalidRet);
    public native String getQMVString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, boolean invalidRet);
    public native String getQMVString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);

    /*! @brief  计算左室Cube/Teichholz/Gibson功能
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 左室Cube/Teichholz/Gibson功能
     *  @retval HSStrUtf8  : 格式化后的左室Cube/Teichholz/Gibson功能字符串
    */
    public native HS_CUBE_TEICHHOLZ getTeichholz(String strEt, String strHr, HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, double height, double weight, boolean invalidRet);
    public native HS_CUBE_TEICHHOLZ getTeichholz(String strEt, String strHr, HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, double height, double weight, boolean invalidRet);
    public native HS_CUBE_TEICHHOLZ getTeichholz(String strEt, String strHr, HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, double height, double weight, boolean invalidRet);
}
