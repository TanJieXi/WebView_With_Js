package api.hard;

/*! @brief 特殊频率控制
*/
public class HS_FreqSpecial {

    // 特殊频率类型
	public class Type {
		public static final int NORMAL  = 0;
		public static final int THI     = 1;
		public static final int OBESITY = 2;
	}

    /*! @brief 获取是否存在特殊频率类型值
     *  @return 是否存在特殊频率类型值
     */
    public native boolean hasType(int type);

    /*! @brief 设置特殊频率类型
     *  @param nFreqType : 特殊频率类型
     *  @return 是否设置成功
     */
    public native boolean setType(int type);

    /*! @brief 获取特殊频率类型值
     *  @return 特殊频率类型值
     */
    public native int getType();

    /*! @brief 是否与指定的特殊频率类型相符
     *  @return 是否与指定的特殊频率类型相符
     */
    public native boolean isType(int type);
}
