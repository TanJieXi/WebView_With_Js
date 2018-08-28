package api.os;

/*! @brief 多B活动位置控制
 */
public class HS_Active {

    /*! @brief  获取多B活动位置个数
     *  @return 多B活动位置个数
     *  @note   多B活动位置个数跟模式有关系
     *          BB模式2个,     控制活动位置有效
     *          4B模式4个,     控制活动位置有效
     *          9B模式9个,     控制活动位置有效
     *          B模式1个,      控制活动位置无效
     *          BM模式1个,     控制活动位置无效
     *          BM滚动模式1个, 控制活动位置无效
     *          M模式1个,      控制活动位置无效
     *          M滚动模式1个,  控制活动位置无效
     *          穿刺模式1个,   控制活动位置无效
     *          空间符合1个,   控制活动位置无效
     */
	public native int getSize();

    /*! @brief  获取当前活动位置
     *  @return 当前活动位置
     */
	public native int getIndex();

    /*! @brief  设置活动位置
     *  @param  index : 活动位置
     *  @return 是否设置成功
     */
	public native boolean setIndex(int index);

    /*! @brief  设置当前活动位置
     *  @param  add  : HsTrue = 位置加,   HsFalse = 位置减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);
}
