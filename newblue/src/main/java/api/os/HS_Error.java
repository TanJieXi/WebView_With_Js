package api.os;

/*! @brief 系统错误标识  */
public class HS_Error {

    // 错误类型
	public class Error
    {
    	public static final int NO_SET_PATH_OR_SET_CONNECT_MODE = 0;	//<! 没有设置系统路径
    	public static final int NO_INIT_SYSTEM                  = 1;	//<! 没有初始化系统
    	public static final int LOAD_PART_FAILE                 = 2;	//<! 加载部位文件失败
    	public static final int SUCCESS                         = 3;	//<! 正常
    	public static final int MAX                             = 4;	//<! 最大
    }
    
    /*! @brief  设置系统错误标识
     *  @param  error : 系统错误标识
     *  @return 是否设置成功
     */
	public native boolean setError(int error);

    /*! @brief  判断系统错误标识是否相同
     *  @return 是否相同
     */
	public native boolean isError(int error);

    /*! @brief  获取系统错误标识
     *  @return 系统错误标识
     */
	public native int getError();
}
