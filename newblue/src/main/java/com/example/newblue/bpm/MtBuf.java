package com.example.newblue.bpm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.contec.jar.contec08a.DeviceCommand;
import com.contec.jar.contec08a.DevicePackManager;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;


public class MtBuf {
	private static final String TAG = "com.testBlueTooth.Mtbuf";
	public static Vector<Integer> m_buf = null;

	public static final int e_pack_pressure_back = 0x46;

	/* 回调对象,通过它我们把结果返回给调用者 */
	private ICallBackInfo iCallBackInfo;
	private Context context;

	public void setCallBack(Context context, ICallBackInfo callBack){
		this.context=context;
		this.iCallBackInfo =callBack;
	}

	public MtBuf() {
		m_buf = new Vector<Integer>();
	}

	public synchronized int Count() {
		return m_buf.size();
	}

	DevicePackManager mPackManager = new DevicePackManager();
	private DeviceData mDeviceData;
	public static final int e_pack_hand_back = 0x48;
	public static final int e_pack_oxygen_back = 0x47;
	private int mType = 0;
	public synchronized void write(byte[] buf, int count,
								   OutputStream pOutputStream) throws Exception {

		int state = mPackManager.arrangeMessage(buf, count, mType);
		int x = DevicePackManager.Flag_User;
		switch (state) {
			case e_pack_hand_back:
				switch (mType) {
					case 9:
						mType = 5;
						pOutputStream.write(DeviceCommand.DELETE_BP());
						break;
					case 0:
						mType = 3;
						pOutputStream.write(DeviceCommand.correct_time_notice);
						break;
//			case 1:
//				mType = 2;
//				pOutputStream.write(DeviceCommand.REQUEST_OXYGEN());
//				break;
//			case 7:
//				mType = 8;
//				pOutputStream.write(DeviceCommand.REQUEST_OXYGEN());
//				break;
//			case 2:
//				mType = 5;
//				pOutputStream.write(DeviceCommand.DELETE_OXYGEN());
//				break;
//			case 8:
//				mType = 5;
//				pOutputStream.write(DeviceCommand.DELETE_OXYGEN());
//				break;
					case 3:
						mType = 1;

						if (x == 0x11) {
							mType = 7;// 三个用户
						} else {
							mType = 1;// 单用户
						}

						pOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
						break;
				}
				break;
			case 0x30:// 确认校正时间正确
				pOutputStream.write(DeviceCommand.Correct_Time());
				break;
			case 0x40:// 校正时间正确
				pOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
				break;
			case e_pack_pressure_back:
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {////防止最后一条命令血压设备接收不到
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<byte[]> _dataList = mPackManager.mDeviceData.mData_blood;
				int _size = _dataList.size();
				DeviceData _mData = new DeviceData(null);
				for (int i = 0; i < _size; i++) {
					byte[] _byte = _dataList.get(i);

/*
 * 11-26 21:40:14.986: I/Data(21374):  82 80 85 d8 ce e2 90 8b 80 9a 95 a6 8c 81
11-26 21:40:14.996: I/Data(21374):     82 80 84 d6 cb e1 90 8b 80 9a 95 a7 99 81
11-26 21:40:14.996: E/DevicePackManager(21374): >>>>>>>>>>>>>>>>>>>2
11-26 21:40:14.996: E/JAR(21374): blooddi:88  bloodgao:133
11-26 21:40:14.996: E/JAR(21374): blooddi:86  bloodgao:132

 * */

					final int hiPre = (_byte[0] & 0xff)*0xFF+  ( _byte[1] & 0xff)  ;//((_byte[0] << 8 )|( _byte[6] & 0xff)) & 0xffff;// 高压
					final int lowPre = _byte[2] & 0xff;// 低压
					final int pulPre = _byte[3] & 0xff;// 低压



					if(i==_size-1){

//					new Handler() {
//						public void handleMessage(Message msg) {
//							super.handleMessage(msg);
//
//						};
//					};

						new Thread(new Runnable() {
							@Override
							public void run() {

								Message msg = new Message();
								msg.what = pulPre;
								msg.arg1 = hiPre;
								msg.arg2 = lowPre;
								handler.sendMessage(msg);

							}
						}).start();
					}


				}
				if (_size == 0) {
					pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());

					Log.e(TAG, "-------Pressure-------");
					Log.e(TAG, "血压血压血压血压血压");

				}else{
					pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());

				}


				break;
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			iCallBackInfo.ReturnData( msg.arg1, msg.arg2,msg.what);
		};
	};

}
