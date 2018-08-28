package api_extend.image;

import android.app.Activity;
import android.widget.LinearLayout;

import com.healon.up20user.Constants;
import com.healon.up20user.Share.SharePrefreceHelper;
import com.healon.up20user.Utils.UtilsResolution;


/**
 * Created by Pretend on 2017/11/10 0010.
 */

public class HS_ImageHeight {

    private LinearLayout m_llMainContent;
    private Activity m_context;

    public HS_ImageHeight(LinearLayout mainContent , Activity context) {
        this.m_llMainContent = mainContent;
        this.m_context=context;
    }

    public int value() {
        //return UtilsDisplay.dip2px(m_llMainContent.getContext(),100) - m_llMainContent.getPaddingBottom();
        return 25 - SharePrefreceHelper.getInstence(m_context, Constants.MANUFACTURER_NAME).getHeight();
    }

    /*! @brief  获取当前总增益索引
   *  @return 索引值
  */
    public int index() {
        //return UtilsDisplay.dip2px(m_llMainContent.getContext(),100);
        return 25;
    }

    /*! @brief 设置索引值
     *  @param  index : 索引值
     *  @retval val = true,  成功
     *  @retval val = false, 失败
    */
    public boolean set(int index) {
        m_llMainContent.setPadding(m_llMainContent.getPaddingLeft(), UtilsResolution.getScreenHeight(m_context) * index / 100,
                m_llMainContent.getPaddingRight(), UtilsResolution.getScreenHeight(m_context) * index /100);
        SharePrefreceHelper.getInstence(m_context, Constants.MANUFACTURER_NAME).setHeight(index);
        return true;
    }
}
