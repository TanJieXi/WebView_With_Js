package api.measure;

import api.type.HS_Font;
import api.type.HS_Pen;
import api.type.HS_Rgba;

public class HS_MeasureAttrib {
	 /*! @brief  设置是否支持手指测量
     *  @param  finger_support : 是否支持手指测量
     */
    public native void setFingerSupport(boolean finger_support);

    /*! @brief  获取是否支持手指测量
     *  @return 是否支持手指测量
     */
    public native boolean isFingerSupport();

    /*! @brief  设置是否显示手指形状
     *  @param  finger_show : 是否显示手指形状
     */
    public native void setFingerShow(boolean finger_show);

    /*! @brief  获取是否显示手指形状
     *  @return 是否显示手指形状
     */
    public native boolean isFingerShow();

    /*! @brief  设置手指形状及大小
     *  @param  arrow_w  : 箭头宽度
     *  @param  arrow_l  : 箭头手形长度
     *  @param  circle_r : 触摸圆半径
     *  @param  scale    : 放大倍率
     *  @param  color    : 颜色值
     */
    public native void setFinger(float arrow_w, float arrow_l, float circle_r, float scale, HS_Rgba color);

    /*! @brief  设置缺省的测量线条画笔
     *  @param  pen_measuring : 测量中的画笔
     *  @param  pen_finish    : 完成时的画笔
     *  @param  pen_modify    : 修改时的画笔
     *  @param  pen_selected  : 选中时的画笔
     *  @note   只对新创建的测量项有效
     */
    public native void setDefaultPen(HS_Pen pen_measuring, HS_Pen pen_finish, HS_Pen pen_modify, HS_Pen pen_selected);

    /*! @brief  获取缺省的测量线条画笔
     *  @param  pen_measuring : 测量中的画笔
     *  @param  pen_finish    : 完成时的画笔
     *  @param  pen_modify    : 修改时的画笔
     *  @param  pen_selected  : 选中时的画笔
     */
    public native void getDefaultPen(HS_Pen pen_measuring, HS_Pen pen_finish, HS_Pen pen_modify, HS_Pen pen_selected);

    /*! @brief  设置索引号对应的测量线条画笔
     *  @param  pos          : 测量项的索引号
     *  @param  penMeasuring : 测量中的画笔
     *  @param  penFinish    : 完成时的画笔
     *  @param  penModify    : 修改时的画笔
     *  @param  penSelected  : 选中时的画笔
     */
    public native void setPen(int pos, HS_Pen pen_measuring, HS_Pen pen_finish, HS_Pen pen_modify, HS_Pen pen_selected);

    /*! @brief  获取索引号对应的测量线条画笔
     *  @param  pos          : 测量项的索引号
     *  @param  penMeasuring : 测量中的画笔
     *  @param  penFinish    : 完成时的画笔
     *  @param  penModify    : 修改时的画笔
     *  @param  penSelected  : 选中时的画笔
     */
    public native void getPen(int pos, HS_Pen pen_measuring, HS_Pen pen_finish, HS_Pen pen_modify, HS_Pen pen_selected);

    /*! @brief  设置缺省的测量线序号字体
     *  @param  font : 缺省的测量线序号字体
     *  @note   只对新创建的测量项有效
     */
    public native void setDefaultFontOrder(HS_Font font);

    /*! @brief  获取缺省的测量线序号字体
     *  @return 缺省的测量线序号字体
     */
    public native HS_Font getDefaultFontOrder();

    /*! @brief  设置索引号对应的测量线序号字体
     *  @param  pos          : 测量项的索引号
     *  @param  font : 索引号对应的测量线序号字体
     */
    public native void setFontOrder(int pos, HS_Font font);

    /*! @brief  获取索引号对应的测量线序号字体
     *  @return 索引号对应的测量线序号字体
     */
    public native HS_Font getFontOrder(int pos);

    /*! @brief  设置缺省的注释字体
     *  @param  font : 缺省的注释字体
     *  @note   只对新创建的注释测量项有效
     */
    public native void setDefaultFontCaret(HS_Font font);

    /*! @brief  获取缺省的注释字体
     *  @return 缺省的注释字体
     */
    public native HS_Font getDefaultFontCaret();

    /*! @brief  设置索引号对应的注释字体
     *  @param  pos          : 测量项的索引号
     *  @param  font : 索引号对应的注释字体
     */
    public native void setFontCaret(int pos, HS_Font font);

    /*! @brief  获取索引号对应的注释字体
     *  @return 索引号对应的注释字体
     */
    public native HS_Font getFontCaret(int pos);

    /*! @brief  设置缺省的测量线帽及选择参数
     *  @param  cap_radius_draw   : 测量线端点线帽的绘制半径
     *  @param  cap_radius_modify : 测量线端点线帽修改时的误差半径
     *  @param  line_width_error  : 测量线修改时的误差宽度
     *  @note   只对新创建的测量项有效
     */
    public native void setDefaultCap(float cap_radius_draw, float cap_radius_error, float line_width_error);

    /*! @brief  获取缺省的测量线帽及选择参数
     *  @param  cap_radius_draw   : 测量线端点线帽的绘制半径
     *  @param  cap_radius_modify : 测量线端点线帽修改时的误差半径
     *  @param  line_width_error  : 测量线修改时的误差宽度
     */
    public native void getDefaultCap(Float cap_radius_draw, Float cap_radius_error, Float line_width_error);

    /*! @brief  设置索引号对应的测量线帽及选择参数
     *  @param  cap_radius_draw   : 测量线端点线帽的绘制半径
     *  @param  cap_radius_modify : 测量线端点线帽修改时的误差半径
     *  @param  line_width_error  : 测量线修改时的误差宽度
     */
    public native void setCap(int pos, float cap_radius_draw, float cap_radius_error, float line_width_error);

    /*! @brief  获取索引号对应的测量线帽及选择参数
     *  @param  cap_radius_draw   : 测量线端点线帽的绘制半径
     *  @param  cap_radius_modify : 测量线端点线帽修改时的误差半径
     *  @param  line_width_error  : 测量线修改时的误差宽度
     */
    public native void getCap(int pos, Float cap_radius_draw, Float cap_radius_error, Float line_width_error);
    
    /*! @brief  设置缺省的箭头放大倍率
     *  @param  scale : 箭头放大倍率
     *  @note   只对新创建的测量项有效
     */
    public native void setDefaultArrowScale(float scale);

    /*! @brief  获取缺省的箭头放大倍率
     *  @return 缺省的箭头放大倍率
     */
    public native float getDefaultArrowScale();

    /*! @brief  设置索引号对应的箭头放大倍率
     *  @param  scale   : 箭头放大倍率
     */
    public native void setArrowScale(int pos, float scale);

    /*! @brief  获取索引号对应的箭头放大倍率
     *  @param  pos : 测量项索引号
     *  @return 索引号对应的箭头放大倍率
     */
    public native float getArrowScale(int pos);

    /*! @brief  设置缺省的角度圆弧距圆心距离倍率
     *  @param  scale : 角度圆弧距圆心距离倍率
     *  @note   只对新创建的测量项有效
     */
	public native void setDefaultAngleScale(float scale);

    /*! @brief  获取缺省的角度圆弧距圆心距离倍率
     *  @return 缺省的角度圆弧距圆心距离倍率
     */
    public native float getDefaultAngleScale();

    /*! @brief  设置索引号对应的角度圆弧距圆心距离倍率
     *  @param  scale   : 角度圆弧距圆心距离倍率
     */
    public native void setAngleScale(int pos, float scale);

    /*! @brief  获取索引号对应的角度圆弧距圆心距离倍率
     *  @param  pos : 测量项索引号
     *  @return 索引号对应的角度圆弧距圆心距离倍率
     */
    public native float getAngleScale(int pos);
}
