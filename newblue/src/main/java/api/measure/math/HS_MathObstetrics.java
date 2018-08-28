package api.measure.math;

import api.measure.struct.HS_BindMath;
import api.measure.struct.HS_BindUser;
import api.measure.struct.HS_OBITEM4;
import api.type.HS_DateTime;

public class HS_MathObstetrics {

    /*! @brief  根据末次月经计算人类孕周/预产期
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 孕周/预产期
     *  @retval HSStrUtf8  : 格式化后的孕周/预产期字符串
    */
    public native int      getHumanGALMP(HS_DateTime lmp);
    public native String getHumanGALMPString(HS_BindMath bindMath, HS_BindUser bindUser, HS_DateTime lmp, boolean invalidRet);
    public native HS_DateTime getHumanEDDLMP(HS_DateTime lmp);
    public native String getHumanEDDLMPString(HS_BindMath bindMath, HS_BindUser bindUser, HS_DateTime lmp, boolean invalidRet);

    /*! @brief  根据排卵日期计算人类孕周/预产期
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 孕周/预产期
     *  @retval HSStrUtf8  : 格式化后的孕周/预产期字符串
    */
    public native int      getHumanGAOVI(HS_DateTime ovi);
    public native String getHumanGAOVIString(HS_BindMath bindMath, HS_BindUser bindUser, HS_DateTime ovi, boolean invalidRet);
    public native HS_DateTime getHumanEDDOVI(HS_DateTime ovi);
    public native String getHumanEDDOVIString(HS_BindMath bindMath, HS_BindUser bindUser, HS_DateTime ovi, boolean invalidRet);

    /*! @brief  根据基础体温计算人类孕周/预产期
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 孕周/预产期
     *  @retval HSStrUtf8  : 格式化后的孕周/预产期字符串
    */
    public native int      getHumanGABBT(double bbt);
    public native String getHumanGABBTString(HS_BindMath bindMath, HS_BindUser bindUser, double bbt, boolean invalidRet);
    public native HS_DateTime getHumanEDDBBT(double bbt);
    public native String getHumanEDDBBTString(HS_BindMath bindMath, HS_BindUser bindUser, double bbt, boolean invalidRet);

    /*! @brief  计算胎重(从from向前查找)
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 胎重
     *  @retval HSStrUtf8  : 格式化后的胎重字符串
    */
    public native double getHumanFW(int fatus, int from, String caption, String retstr, boolean invalidRet, boolean findAll);
    public native String getHumanFWString(int fatus, boolean invalidRet);

    /*! @brief  计算孕周/胎龄
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 孕周/胎龄
     *  @retval HSStrUtf8  : 格式化后的孕周/胎龄字符串
    */
    public native int    getGA(int pos, HS_OBITEM4 retob);
    public native String getGAString(int pos, HS_OBITEM4 retob, boolean invalidRet);
    /*! @brief  计算预产期
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 预产期
     *  @retval HSStrUtf8  : 格式化后的预产期字符串
    */
    public native HS_DateTime getEDD(int pos);
    public native String getEDDString(int pos, boolean invalidRet);

    /*! @brief  计算平均孕周/胎龄(人类时nFatus=0,1,2; 动物时nFatus=0)
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 平均孕周/胎龄
     *  @retval HSStrUtf8  : 格式化后的平均孕周/胎龄字符串
    */
    public native int    getGAEqu(int fatus, String part);
    public native String getGAEquString(int fatus, HS_BindMath bindMath, HS_BindUser bindUser, String part, boolean invalidRet);

    /*! @brief  计算平均预产期(人类时nFatus=0,1,2; 动物时nFatus=0)
     *  @param  distance1  : 距离1
     *  @param  distance2  : 距离2
     *  @param  invalidRet : HSTrue = 测量值无效时,返回无效字符串, HSFalse = 测量值无效时,返回空字符串
     *  @retval HSDouble   : 平均预产期
     *  @retval HSStrUtf8  : 格式化后的平均预产期字符串
    */
    public native HS_DateTime getEDDEqu(int fatus, HS_BindMath bindMath, HS_BindUser bindUser, String part, int period);
    public native String getEDDEquString(int fatus, HS_BindMath bindMath, HS_BindUser bindUser, String part, int period, boolean invalidRet);
}
