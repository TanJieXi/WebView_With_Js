package api.image;

/*! @brief PICC
*/
public class HS_Picc {

    /*! @brief  设置是否打开PICC功能
     *  @param  show : 是否打开
     *  @return 是否设置成功
     */
	public native boolean setOpen(boolean open);

    /*! @brief  获取PICC功能是否打开
     *  @return 是否打开
     */
    public native boolean isOpened();
}
