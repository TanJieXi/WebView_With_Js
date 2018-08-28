package api.image;

/*! @brief 水平线相关
*/
public class HS_LineHorz {

    /*! @brief  获取横向线相关可控制个数
     *  @return 横向线相关可控制个数
    */
	public native int getSize();

    /*! @brief  获取横向线相关对应的索引值
     *  @param  value : 横向线相关值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前横向线相关对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的横向线相关值
     *  @param  index : 索引值
     *  @return 横向线相关值
    */
	public native int getValue(int index);

    /*! @brief  获取当前横向线相关值
     *  @return 横向线相关值
    */
	public native int getValue();

    /*! @brief  设置横向线相关
     *  @param  add  : HSTrue = 横向线相关加, HSFalse = 横向线相关减
     *  @param  loop : HSTrue = 循环加减,     HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置横向线相关值
     *  @param  value : 横向线相关值
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
