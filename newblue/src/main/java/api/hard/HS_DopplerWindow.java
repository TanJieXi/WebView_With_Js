package api.hard;

/*! @brief 多普勒控制-取景框
*/
public class HS_DopplerWindow {

    /*! @brief  设置取景框位置
     *  @param  startLine  : 起始扫描线
     *  @param  startDepth : 起始深度
     *  @param  width      : 宽度
     *  @param  height     : 高度(mm)
     *  @return 是否设置成功
    */
	public native boolean setWindow(int startLine, int startDepth, int width, int height);

    /*! @brief  设置取景框宽度
     *  @param  startLine : 起始扫描线
     *  @param  width     : 宽度
     *  @return 是否设置成功
    */
	public native boolean setWidth(int startLine, int width);

    /*! @brief  设置取景框深度
     *  @param  startDepth : 起始深度(mm)
     *  @param  height     : 高度(mm)
     *  @return 是否设置成功
    */
	public native boolean setVolume(int startDepth, int height);

    /*! @brief  设置取景框宽度
     *  @param  minLines : 最小扫描线范围
     *  @param  maxLines : 最大扫描线范围
     *  @return 是否设置成功
    */
	public native boolean setWidth(int minWidth, int maxWidth, int curWidth);

    /*! @brief  设置取景框深度
     *  @param  minDepths : 最小深度范围
     *  @param  maxDepths : 最大深度范围
     *  @return 是否设置成功
    */
	public native boolean setVolume(int minHeight, int maxHeight, int curHeight);

    /*! @brief  获取取景框起始扫描线
     *  @return 取景框起始扫描线
    */
    public native int getStartLine();

    /*! @brief  获取取景框结束扫描线
     *  @return 取景框结束扫描线
    */
    public native int getEndLine();

    /*! @brief  获取取景框起始深度
     *  @return 取景框起始深度
    */
    public native int getStartDepth();

    /*! @brief  获取取景框结束深度
     *  @return 取景框结束深度
    */
    public native int getEndDepth();
}
