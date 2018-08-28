package api.image;

import api.type.HS_Rgba;

/*! @brief 调色板控制
*/
public class HS_ColorMap {

    /*! @brief  获取映射关键字名称
     *  @return 映射关键字名称
    */
	public native String getKey();


    /*! @brief  获取调色板可控制个数
     *  @return 调色板可控制个数
    */
    public native int getSize();

    /*! @brief  获取调色板对应的索引值
     *  @param  value : 调色板值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的调色板值
     *  @param  index : 索引值
     *  @return 调色板值
    */
    public native HS_Rgba[] getValue(int index);

    /*! @brief  获取索引值对应的调色板值
     *  @param  index : 索引值
     *  @return 调色板值
    */
    public native HS_Rgba[] getValue();

    /*! @brief  设置调色板
     *  @param  add  : HSTrue = 调色板加, HSFalse = 调色板减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
