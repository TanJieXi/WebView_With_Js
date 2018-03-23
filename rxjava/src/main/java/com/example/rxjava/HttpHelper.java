package com.example.rxjava;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TanJieXi on 2018/3/23.
 */

public class HttpHelper {
    private BufferedInputStream sBis;
    private ByteArrayOutputStream sBaos;
    private static HttpHelper mHttpHelper = null;

    private HttpHelper() {

    }

    public static HttpHelper getInstance (){
        if(mHttpHelper == null){
            synchronized (HttpHelper.class){
                if(mHttpHelper == null){
                    mHttpHelper = new HttpHelper();
                }
            }
        }
        return mHttpHelper;
    }

    public void downLoadData(final String path, final CallBackListener callBackListener) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                  sBis = new BufferedInputStream(connection.getInputStream());
                sBaos = new ByteArrayOutputStream();

              int len = 0;
                byte[] buf = new byte[1024];
                while ((len = sBis.read(buf)) != -1) {
                    sBaos.write(buf, 0, len);
                }
                sBaos.flush();
                byte[] bytes = sBaos.toByteArray();

                callBackListener.onFetch(bytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sBis.close();
                sBaos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface CallBackListener{
        void onFetch(byte[] bytes);
    }
}
