package api.image;

/*! @brief 检查部位控制
*/
public class HS_Part {

    /*! @brief  获取部位名列表
     *  @return 部位名列表
     */
	public native String[] getNames();

    /*! @brief 获取可用部位个数
     *  @return 可用部位个数
     */
    public native int getSize();

    /*! @brief  获取部位名对应的索引值
     *  @return 索引值
     */
    public native int getIndex();

    /*! @brief  设置部位名索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
     */
    public native boolean setIndex(int index);

    /*! @brief  获取当前部位名
     *  @return 当前部位名
     */
    public native String getName();

    /*! @brief  更新部位
     *  @return 更新状态
     */
    public native boolean setUpdate();
}
