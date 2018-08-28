package api_extend.image;

import api.image.HS_Gain;

/**
 * Created by Pretend on 2017/10/11 0011.
 */

public class HS_GainEx {
    private HS_Gain gain;

    public HS_GainEx() {
        gain = new HS_Gain();
    }

    public int value() {
        return gain.getIndex();
    }

    /*! @brief  获取当前总增益索引
   *  @return 索引值
  */
    public int index() {
        return gain.getSize();
    }

    /*! @brief 设置索引值
     *  @param  index : 索引值
     *  @retval val = true,  成功
     *  @retval val = false, 失败
    */
    public boolean set(int index) {
        return gain.setIndex(index);
    }
}
