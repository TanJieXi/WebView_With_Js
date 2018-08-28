package api.os;

import android.hardware.usb.UsbManager;

/*! @brief 设置连接方式控制
*/
public class HS_Connect {
    /*! @brief  绑定UsbManager管理对象
     *  @param  jniEnv    : Java Native Interface环境变量, windows环境传递HSNull
     *  @param  usbMgr    : android UsbManager管理对象, windows环境传递HSNull
    */
	public native void attachUsbManager(UsbManager usbMgr);

    /*! @brief  解绑UsbManager管理对象
    */
    public native void detachUsbManager();

    /*! @brief  获取设备连接状态
     *  @return 设备是否已经连接
    */
	public native boolean isConnected();

    /*! @brief  获取设备是否连接成功过
     *  @return 设备是否连接成功过
    */
	public native boolean isOnceSucceeded();

    /*! @brief  连接设备
     *  @param  real      : 是否为真实模式
     *  @param  user      : 探头识别码
     *  @param  config    : 探头配置文件
     *  @param  obTable   : 产科表数据库
     *  @param  palettes  : 调色板
     *  @param  templates : 打印模板数据库
     *  @param  iClear    : IClear动态库路径
     *  @param  openCL    : OpenCL动态库路径
     *  @return 设备是否连接成功
    */
	public native boolean setConnect(boolean real, String user, String config, String obTable, String palettes, String templates, String iClear, String openCL);

    /*! @brief  设置设备状态为断开
    */
	public native void setUnconnect();

    /*! @brief  关闭系统
     *  @return 是否关闭成功
    */
	public native boolean setClose();

    /*! @brief  获取设备配置进度百分比
     *  @return 设备配置进度百分比[0%, 100%]
    */
    public native int getProgress();
}
