package com.ctbri.service;

public interface OrderAssetService {
	/**
	 * 
	 * @Title: findAssetNameByOrderId
	 * @Description: 根据orderid查询Asset名称
	 * @param orderId
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	String findAssetNameByOrderId(String orderId);
}
