package api.measure.math;

import api.measure.struct.HS_BindMath;
import api.measure.struct.HS_BindUser;
import api.type.HS_PointF;

public class HS_MathUrology {
	/*! @brief  计算残余尿样
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 残余尿样
     *  @retval HSStrUtf8  : 格式化后的残余尿样字符串
    */
    // 残余尿样
    public native double getCYNY(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6);
    public native double getCYNY(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6);
    public native double getCYNY(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts);
    public native double getCYNY(int pos);
    public native String getCYNYString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF pt1, HS_PointF pt2, HS_PointF pt3, HS_PointF pt4, HS_PointF pt5, HS_PointF pt6, boolean invalidRet);
    public native String getCYNYString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, int pos1, int pos2, int pos3, int pos4, int pos5, int pos6, boolean invalidRet);
    public native String getCYNYString(HS_BindMath bindMath, HS_BindUser bindUser, HS_PointF[] pts, boolean invalidRet);
    public native String getCYNYString(int pos, boolean invalidRet);
    
    
    /*! @brief  计算PPSA
     *  @param  volume     : 前列腺体积
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : PPSA
     *  @retval HSStrUtf8  : 格式化后的PPSA字符串
    */
    public native double  getPPSA(double volume);
    public native String getPPSAString(double volume, boolean invalidRet);
    /*! @brief  计算PSAD
     *  @param  psa        : 血清
     *  @param  volume     : 前列腺体积
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : PSAD
     *  @retval HSStrUtf8  : 格式化后的PSAD字符串
    */
    public native double  getPSAD(double psa, double volume);
    public native String getPSADString(double psa, double volume, boolean invalidRet);
}
