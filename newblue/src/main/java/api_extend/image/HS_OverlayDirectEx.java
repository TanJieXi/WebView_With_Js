package api_extend.image;

import android.app.Activity;
import android.graphics.BitmapFactory;

import com.example.newblue.R;
import com.healon.up20user.Utils.UtilsResolution;

import api.os.overlay.HS_OverlayDirect;
import api.type.HS_Size;

/**
 * Created by Pretend on 2017/8/28 0028.
 */

public class HS_OverlayDirectEx {

    private Activity m_activity;

    public HS_OverlayDirectEx(Activity activity) {
        m_activity = activity;
    }

    public void setOverlayDirect() {
        HS_OverlayDirect direct = new HS_OverlayDirect();
        direct.setNearDSC(true);

        HS_Size size = HS_Size.New(10 * UtilsResolution.getDensity(m_activity), 10 * UtilsResolution.getDensity(m_activity));
        direct.setSize(size);
            direct.setImage(HS_OverlayDirect.Status.UnfreezeActive, BitmapFactory.decodeResource(m_activity.getResources(), R.mipmap.follow_probe_active_run_hs));
            direct.setImage(HS_OverlayDirect.Status.UnfreezeUnactive, BitmapFactory.decodeResource(m_activity.getResources(), R.mipmap.follow_probe_unactive_hs));
            direct.setImage(HS_OverlayDirect.Status.FreezeActive, BitmapFactory.decodeResource(m_activity.getResources(), R.mipmap.follow_probe_active_pause_hs));
            direct.setImage(HS_OverlayDirect.Status.FreezeUnactive, BitmapFactory.decodeResource(m_activity.getResources(), R.mipmap.follow_probe_active_pause_hs));
    }
}
