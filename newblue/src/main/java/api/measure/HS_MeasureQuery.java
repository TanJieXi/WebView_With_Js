package api.measure;

import api.measure.struct.HS_BindMath;
import api.measure.struct.HS_BindUser;
import api.type.HS_PointF;

public class HS_MeasureQuery {
	/*! @brief  获取是否为空
     *  @return 是否为空
     */
    public native boolean isEmpty();

    /*! @brief  获取测量项个数
     *  @return 测量项个数
     */
    public native int getCount();

    /*! @brief  获取是否正在测量
     *  @return 是否正在测量
     */
    public native boolean isMeasuring();

    /*! @brief  获取当前测量项的索引号
     *  @return 当前测量项的索引号,-1=没有正在测量的项
     */
	public native int getCurrent();
	
    /*! @brief  获取唯一识别码对应的测量项索引号
     *  @param  idCode : 唯一识别码(大小写区别)
     *  @return 当前测量项的索引号,-1=没有正在测量的项
     */
	public native int getIndex(String idCode);

    /*! @brief  获取测量项的唯一识别码
     *  @param  base : 测量项
     *  @return 测量项的唯一识别码
     */
	public native String getIdCode(int index);

    /*! @brief  获取绑定测量计算参数
     *  @param  base : 测量项
     *  @return 绑定测量计算参数
     */
	public native HS_BindMath getBindMath(int index);

    /*! @brief  获取绑定用户配置参数
     *  @param  base : 测量项
     *  @return 绑定用户配置参数
     */
    public native HS_BindUser getBindUser(int index);

    /*! @brief  获取测量项基本测量线条数
     *  @param  base : 测量项
     *  @return 测量项基本测量线条数
     */
    public native int getLineCount(int index);

    /*! @brief  获取测量项坐标点
     *  @param  base : 测量项
     *  @return 测量项坐标点
     */
    public native HS_PointF[] getPoints(int index);
}
