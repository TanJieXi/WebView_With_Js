package com.example.readbigfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Scanner;

/**
 * 注意这个第二个按钮里面的方法，如果类型不确定，注意使用泛型
 */
public class MainActivity extends AppCompatActivity {
    private File mFile;
    private String filezip = Environment.getExternalStorageDirectory().getAbsoluteFile()
            + File.separator + "com.cd7d.zk" + File.separator
            + "Downloadzip";
    private StringBuilder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFile = FileUtils.getFileByPath(filezip + File.separator + "222111.txt");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }

    public void opens(View view) {
        //mBuilder = new StringBuilder();

        new Thread(new Runnable() {
            @Override
            public void run() {
               // mBuilder.append(FileIOUtils.readFile2String(mFile.toString()));
                mBuilder = readResource();
                if (mBuilder == null || mBuilder.length() < 10) {
                    return;
                }
                LogUtil.i("dsfdsafdsaf", "真正的结束");
                try {
                    //JSONObject json = new JSONObject(s);
                    //Log.i("dsfdsfgds",json.toString());
                   /* JSONObject object = new JSONObject(JSON.parseObject(s));
                    final JSONArray jsonArray = new JSONArray(JSON.parseArray(object.getString("user")));
                    final JSONArray jsonArray2 = new JSONArray(JSON.parseArray(object.getString("userinfo")));
                    Log.i("dsfdsfgds",jsonArray.length() + "");
                    Log.i("dsfdsfgds",jsonArray2.length() + "");*/


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }


    public StringBuilder readResource() {
        StringBuilder sb = new StringBuilder();
        //for (String fileDirectory : this.readResourceDirectory())// 得到文件存放路径，我这里使用了一个方法从XML文件中读出文件的
        // 存放路径，当然也可以用绝对路径来代替这里的fileDriectory
        //{
        long fileLength = mFile.length();
        Scanner scan = null;
        try {
            MappedByteBuffer inputBuffer = new RandomAccessFile(mFile, "r")
                    .getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
                            fileLength);// 读取大文件

            byte[] dst = new byte[(int) fileLength];
            for (int offset = 0; offset < fileLength; offset++) {
                byte b = inputBuffer.get();
                dst[offset] = b;
            }

            scan = new Scanner(new ByteArrayInputStream(dst)).useDelimiter(" ");
            while (scan.hasNext()) {
                String s = scan.next().trim();
               // sb.append(s);
                Log.i("dsfdssdflakd", s + "");
            }
            Log.i("dsfdssdflakd", "结束");
            return sb;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(scan != null){
                scan.close();
            }
        }
    }

    /**
     * 用流下载，使用Gson把流转换成对象
     * @param view
     */
    public void opensstream(View view) {
        if(!mFile.exists()){
            return;
        }
        try {
            long startTime = System.currentTimeMillis();
            Log.i("dfdsafgdsf","开始：" +  startTime);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(mFile));
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(bis,"UTF-8");
            Bean bean = gson.fromJson(reader,Bean.class);
            Log.i("dfdsafgdsf",bean.toString());
            long endTime = System.currentTimeMillis();
            List<Bean.UserBean> user = bean.getUser();
            List<Bean.UserinfoBean> userinfo = bean.getUserinfo();
            Log.i("dfdsafgdsf","结束：" +  endTime);
            Log.i("dfdsafgdsf",user.size() + "");
            Log.i("dfdsafgdsf",userinfo.size() + "");
            Log.i("dfdsafgdsf","耗时：" + (endTime - startTime));


            for(Bean.UserinfoBean b : userinfo){
                String o = JSON.toJSONString(b);
                org.json.JSONObject json = new org.json.JSONObject(o);
                JSONObject hfx = json.optJSONObject("hfx");
                if(hfx == null){
                    String ss = json.optString("hfx");
                    Log.i("dsfdsfdsgdsf","json对象: null");
                }else{
                    Log.i("dsfdsfdsgdsf","json对象:" + hfx.toString());
                    Bean.UserinfoBean.HfxBean hfxBean = gson.fromJson(hfx.toString(), Bean.UserinfoBean.HfxBean.class);
                    Log.i("dsfdsfdsgdsf","json对象:" + hfxBean.toString());
                }
            }

        } catch (Exception e) {
            Log.i("dsfsdfag",e.toString());
            e.printStackTrace();
        }
    }




}
