package com.zj.ultrasonic.core;

/**
 * 端口选择界面回调消息接口
 */
oneway interface IProbe {
	
	/**
	 *部署  模拟器所需资源文件 进度,最小值 0 ,最大值 100
	 */
	void NotifyDeploymentPercentage(float Value);
	void NotifyDeploymentDone();
	
	void SetProbe(int Position,int Value); 
}
