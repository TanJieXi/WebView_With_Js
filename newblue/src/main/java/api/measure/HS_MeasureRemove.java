package api.measure;

public class HS_MeasureRemove {
	/*! @brief  取消当前测量(正处于测量中时有效)
     *  @return 是否成功
     */
    public native boolean cancel();

    /*! @brief  删除测量项
     *  @param  strIdCode : 测量项唯一识别串
     *  @return 是否成功
     */
    public native boolean remove(String strIdCode);

    /*! @brief  删除测量项
     *  @param  index : 索引值
     *  @return 是否成功
     */
    public native boolean remove(int index);

    /*! @brief  删除第一个测量项
     *  @return 是否成功
     */
    public native boolean removeFirst();

    /*! @brief  删除最后一个测量项
     *  @return 是否成功
     */
    public native boolean removeLast();

    /*! @brief  删除所有测量项
     */
    public native void clear();
}
