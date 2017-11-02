package com.cn.ctbri.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.Service;
import com.cn.ctbri.entity.Task;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-15
 * 描        述：  服务接口类
 * 版        本：  1.0
 */
public interface IServService {
	/**
	 * 功能描述： 根据id查询服务
	 * 参数描述： int serviceid
	 *		 @time 2015-1-15
	 *	返回值 ：Service
	 */
	Serv findById(int serviceid);

	/**
     * 功能描述： 根据条件查询服务
     * 参数描述： Serv service
     *       @time 2015-1-21
     *  返回值 ：Serv
     */
    List<Serv> findServiceByParam(Serv service);

	/**
     * 功能描述： 根据任务查询服务
     * 参数描述： Serv service
     *       @time 2015-1-31
     *  返回值 ：Serv
     */
	List<HashMap<String, Object>> findByTask(Task task);


}
