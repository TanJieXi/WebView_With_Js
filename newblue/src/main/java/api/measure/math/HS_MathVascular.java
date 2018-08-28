package api.measure.math;

public class HS_MathVascular {
	/*! @brief  计算两面积%STA
     *  @param  area1      : 面积1
     *  @param  area2      : 面积2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval double   : 两面积%STA
     *  @retval HSStrUtf8  : 格式化后的两面积%STA字符串
    */
    public native double getSTA(int pos, Boolean valid);
    public native double getSTA(double area1, double area2, Boolean valid);
    public native String getSTAString(int pos, boolean invalidRet);
    public native String getSTAString(double area1, double area2, boolean invalidRet);

    /*! @brief  计算两直线%STD
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval double   : 两直线%STD
     *  @retval HSStrUtf8  : 格式化后的两直线%STD字符串
    */
    public native double getSTD(int pos, Boolean valid);
    public native double getSTD(double distance1, double distance2, Boolean valid);
    public native String getSTDString(int pos, boolean invalidRet);
    public native String getSTDString(double distance1, double distance2, boolean invalidRet);
}
