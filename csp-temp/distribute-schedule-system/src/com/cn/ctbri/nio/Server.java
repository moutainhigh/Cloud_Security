package com.cn.ctbri.nio;

/**
 * 描     述： 服務器接口
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
public interface Server {
	/**
	  * 启动服务器
	  */
	 public void start();
	 /**
	  * 重启程序
	  */
	 public void restart();
	 
	 /**
	  * 停止程序运行
	  */
	 public void stop();
}
