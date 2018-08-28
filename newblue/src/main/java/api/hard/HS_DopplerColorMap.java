package api.hard;

import api.type.HS_Rgba;

/*! @brief 多普勒控制-调色板
*/
public class HS_DopplerColorMap {

    /*! @brief  获取映射关键字名称
     *  @return 映射关键字名称
    */
	public native String[] getKeys();


    /*! @brief  获取调色板可控制个数
     *  @return 调色板可控制个数
    */
    public native int getSize(String name);

    /*! @brief  获取调色板对应的索引值
     *  @param  value : 调色板值
     *  @return 索引值
    */
    public native int getIndex(String name);

    /*! @brief  获取索引值对应的调色板值
     *  @param  index : 索引值
     *  @return 调色板值
    */
    public native HS_Rgba[] getValue(String name, int index);

    /*! @brief  获取索引值对应的调色板值
     *  @param  index : 索引值
     *  @return 调色板值
    */
    public native HS_Rgba[] getValue(String name);

    /*! @brief  设置调色板
     *  @param  add  : HSTrue = 调色板加, HSFalse = 调色板减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(String name, boolean add, boolean loop);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(String name, int index);


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
