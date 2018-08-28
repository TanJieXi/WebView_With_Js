package api.measure;

import java.util.ArrayList;

import api.measure.struct.HS_OBITEM1;
import api.measure.struct.HS_OBITEM2;
import api.measure.struct.HS_OBITEM3;
import api.measure.struct.HS_OBITEM4;

public class HS_MeasureOBSTable {    
    /*! @brief  是否支持某产科部位
     *  @param  part   : 部位名(如: Human)
     *  @return 是否支持
     */
    public native boolean isSupport(String part);

    /*! @brief  是否支持某产科类型
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @return 是否支持
     */
    public native boolean isSupport(String part, String type);

    /*! @brief  是否支持某产科方法
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  method : 方法名(如: China)
     *  @return 是否支持
     */
    public native boolean isSupport(String part, String type, String method);

    /*! @brief  获取产科部位默认孕期
     *  @param  part : 部位名称(如: Human)
     *  @return 部位信息
     */
    public native int getPeriodDefault(String part);

    /*! @brief  获取产科部位当前孕期
     *  @param  part : 部位名称(如: Human)
     *  @return 部位信息
     */
    public native int getPeriodCurrent(String part);

    /*! @brief  获取产科部位信息
     *  @param  part : 部位名称(如: Human)
     *  @return 部位信息
     */
    public native HS_OBITEM1 getPart(String part);

    /*! @brief  获取所有支持的产科部位列表
     *  @return 所有支持的产科部位列表
     */
    public native HS_OBITEM1[] getParts();

    /*! @brief  获取所有支持的产科部位列表
     *  @param  ary : 部位信息列表
     *  @return 是否成功
     */
    public native boolean getParts(ArrayList<HS_OBITEM1> ary);

    /*! @brief  获取产科类型信息
     *  @param  part : 部位名(如: Human)
     *  @param  type : 类型名(如: BPD)
     *  @return 类型信息
     */
    public native HS_OBITEM2 getType(String part, String type);

    /*! @brief  获取所有支持的产科类型列表
     *  @param  part : 部位名(如: Human)
     *  @return 所有支持的产科类型列表
     */
    public native HS_OBITEM2[] getTypes(String part);

    /*! @brief  获取所有支持的产科类型列表
     *  @param  part : 部位名(如: Human)
     *  @param  ary : 类型信息列表
     *  @return 是否成功
     */
    public native boolean getTypes(String part, ArrayList<HS_OBITEM2> ary);

    /*! @brief  获取产科方法信息
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  method : 方法名(如: China)
     *  @return 方法信息
     */
    public native HS_OBITEM3 getMethod(String part, String type, String method);

    /*! @brief  获取所有支持的产科方法信息列表
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @return 所有支持的产科方法信息列表
     */
    public native HS_OBITEM3[] getMethods(String part, String type);

    /*! @brief  获取所有支持的产科方法信息列表
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  ary    : 方法信息列表
     *  @return 是否成功
     */
    public native boolean getMethods(String part, String type, ArrayList<HS_OBITEM3> ary);

    /*! @brief  获取产科表信息
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  method : 方法名(如: China)
     *  @return 产科表信息
     */
    public native HS_OBITEM4[] getTables(String part, String type, String method);

    /*! @brief  获取产科表信息
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  method : 方法名(如: China)
     *  @return 是否成功
     */
    public native boolean getTables(String part, String type, String method, ArrayList<HS_OBITEM4> ary);

    /*! @brief  重置某产科部位当前孕期天数
     *  @param  part   : 部位名(如: Human)
     */
    public native boolean setResetPeriodCurrent(String part);

    /*! @brief  设置某产科部位当前孕期天数
     *  @param  part   : 部位名(如: Human)
     *  @param  period : 孕期天数(如: 280)
     */
    public native boolean setPeriodCurrent(String part, int period);

    /*! @brief  重置某产科类型的当前测量方法
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  method : 方法名(如: China)
     */
    public native boolean setResetMethodCurrent(String part, String type);

    /*! @brief  设置某产科类型的当前测量方法
     *  @param  part   : 部位名(如: Human)
     *  @param  type   : 类型名(如: BPD)
     *  @param  method : 方法名(如: China)
     */
    public native boolean setMethodCurrent(String part, String type, String method);

    /*! @brief  插入一个新产科方法
     *  @param  part          : 部位名(如: Human)
     *  @param  type          : 类型名(如: BPD)
     *  @param  method        : 待添加的方法名
     *  @param  methodcomment : 方法描述
     */
    public native boolean insertMethod(String part, String type, String method, String methodcomment);

    /*! @brief  修改产科方法名
     *  @param  part             : 部位名(如: Human)
     *  @param  type             : 类型名(如: BPD)
     *  @param  method           : 方法名
     *  @param  newMethod        : 待修改的方法名
     *  @param  newMethodcomment : 待修改的方法描述
     */
    public native boolean modifyMethod(String part, String type, String method, String newMethod, String newMethodcomment);

    /*! @brief  删除产科方法
     *  @param  part      : 部位名(如: Human)
     *  @param  type      : 类型名(如: BPD)
     *  @param  method    : 方法名
     *  @param  newMethod : 待删除的方法名
     */
    public native boolean deleteMethod(String part, String type, String method);

    /*! @brief  插入记录
     *  @param  part      : 部位名(如: Human)
     *  @param  type      : 类型名(如: BPD)
     *  @param  method    : 方法名
     *  @param  ob4       : 待插入的记录内容(其中ob4.id无意义)
     */
    public native boolean insertMethodRecord(String part, String type, String method, HS_OBITEM4 ob4);

    /*! @brief  修改记录
     *  @param  part      : 部位名(如: Human)
     *  @param  type      : 类型名(如: BPD)
     *  @param  method    : 方法名
     *  @param  ob4       : 待修改的记录内容(其中ob4.id表示要修改的记录位置)
     */
    public native boolean modifyMethodRecord(String part, String type, String method, HS_OBITEM4 ob4);

    /*! @brief  删除记录
     *  @param  part      : 部位名(如: Human)
     *  @param  type      : 类型名(如: BPD)
     *  @param  method    : 方法名
     *  @param  ob4       : 待删除的记录内容(其中ob4.id表示要删除的记录位置)
     */
    public native boolean deleteMethodRecord(String part, String type, String method, HS_OBITEM4 ob4);

    /*! @brief  导出模板
     *  @param  file      : 产科表模板文件
     */
    public native boolean exportTemplateTxtFile(String file);

    /*! @brief  导入模板
     *  @param  file      : 产科表文件
     */
    public native boolean importTemplateTxtFile(String file);
}
