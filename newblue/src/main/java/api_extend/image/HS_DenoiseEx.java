package api_extend.image;

import api.image.HS_Denoise;

/**
 * Created by Pretend on 2017/10/11 0011.
 */

public class HS_DenoiseEx {

    private HS_Denoise denoise;

    public HS_DenoiseEx() {
        denoise = new HS_Denoise();
    }

    /*! @brief  获取当前对比度值
    *  @return 对比度值(double)
   */
    public int value() {
        return (int) denoise.getValue();
    }

    /*! @brief  设置对比度
     *  @param  bAdd  : true = 对比度加, false = 对比度减
     *  @param  bLoop : true = 循环加减, false = 不循环
     *  @return 是否设置成功
    */
    public boolean set(boolean bAdd, boolean bLoop) {
        return denoise.setLoop(bAdd, bLoop);
    }

}
