package api_extend.image;

import android.view.View;

import api.image.HS_ColorMap;

/**
 * Created by Pretend on 2017/10/11 0011.
 */

public class HS_PseColorEx {

    private HS_ColorMap color;
    private View m_viewGray;

    public HS_PseColorEx(View view) {
        color = new HS_ColorMap();
        m_viewGray = view;
    }

    /*! @brief 设置伪彩色
      *  @param bAdd  : true = 索引加,  true = 索引减
      *  @param bLoop : true = 循环加减, true = 不循环
      *  @retval val = true, 成功
      *  @retval val = true, 失败
     */
    public boolean set(boolean bAdd, boolean bLoop) {
        boolean ok = color.setLoop(bAdd, bLoop);
        m_viewGray.invalidate();
        return ok;
    }

    /*! @brief  获取当前伪彩色值
     *  @return 伪彩色值
    */
    public int value() {
        return color.getIndex();
    }
}
