package api;

public class HS_ApiManager {

    /*! @brief  分配全局sdk接口指针
     */
	public native void alloc();

    /*! @brief  释放全局sdk接口指针
     */
	public native void free();
}
