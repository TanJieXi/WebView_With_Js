package api.os;

public class HS_Debug {

    /*! @brief  设置日志使能
     *  @param  enable : 使能
    */
	public native void log_setEnable(boolean enable);

    /*! @brief  获取日志使能
     *  @return 是否有效
    */
	public native boolean log_isEnable();

    /*! @brief  重置日志
    */
	public native void log_reset();

    /*! @brief  设置日志文件路径
    */
	public native void log_setFile(String file);

    /*! @brief  设置耗时使能
     *  @param  enable : 使能
    */
	public native void time_setEnable(boolean enable);

    /*! @brief  获取耗时使能
     *  @return 是否有效
    */
	public native boolean time_isEnable();

    /*! @brief  重置耗时
    */
	public native void time_reset();

    /*! @brief  获取耗时个数
     *  @return 耗时个数
    */
	public native int time_getCount();

    /*! @brief  获取耗时名称
     *  @param  index : 索引值
     *  @return 名称
    */
	public native String time_getName(int index);

    /*! @brief  查询耗时值
     *  @param  index : 索引值
     *  @return 耗时值
    */
	public native double time_query(int index);

    /*! @brief  查询耗时值
     *  @param  name : 名称
     *  @return 耗时值
    */
	public native double time_query(String name);
}
