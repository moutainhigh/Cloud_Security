package com.cn.ctbri.service;

import com.cn.ctbri.entity.APIKey;


/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  apiKey业务层接口
 * 版        本：  1.0
 */
public interface IAPIKeyService {

	//查找key
	APIKey findByKey(String apiKey);
	
}
