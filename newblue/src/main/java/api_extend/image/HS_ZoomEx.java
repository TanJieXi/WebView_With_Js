package api_extend.image;

import java.text.DecimalFormat;

import api.image.HS_Zoom;

/**
 * Created by Pretend on 2017/10/11 0011.
 */

public class HS_ZoomEx {
    private HS_Zoom zoom;
    public HS_ZoomEx() {
        zoom = new HS_Zoom();

    }

    /*! @brief  获取当前放大倍率值
    *  @return 放大倍率值(HSInt)
   */
    public String value() {
        DecimalFormat format = new DecimalFormat("0.00");
        if (!zoom.isOpened()) {
            return format.format(1.0);
        } else {
            return format.format(zoom.getValue());
        }
    }

    /*! @brief  设置放大倍率
   *  @param  add  : true = 放大倍率加, false = 放大倍率减
   *  @param  loop : true = 循环加减,  false = 不循环
   *  @return 是否设置成功
  */
    public boolean set(boolean add, boolean loop) {
        boolean ok = false;
        if (!zoom.isOpened()) {
            zoom.setMode(HS_Zoom.Mode.LOCAL);
            zoom.setOpen(true);
            zoom.setValue(1.0);
        }
        ok = zoom.setLoop(add, loop);
        if (Double.doubleToLongBits(zoom.getValue()) == Double.doubleToLongBits(1.0)) {
            zoom.setOpen(false);
        }
        return ok;
    }
}
