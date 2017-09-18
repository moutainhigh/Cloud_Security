/**
 * 创 建 人  ：  裴丹丹
 * 创建日期：  2017年8月22日
 * 描        述：  
 * 版        本：  1.0
 */
package com.cn.ctbri.service;

import java.util.List;

import com.cn.ctbri.entity.Xlist;

/**
 * 创 建 人  ：  裴丹丹
 * 创建日期：  2017年8月22日
 * 描        述：  
 * 版        本：  1.0
 */
public interface IXlistService {
	/**
	 * 创 建 人  ：  裴丹丹
	 * 创建日期：  2017年8月22日
	 * 功能描述：列表出所有专区二级页面
	 * 参数描述：
	 * 返回值    ：List<Xlist>
	 */
	List<Xlist> listXlist();
	
	/**
	 * 创 建 人  ：  裴丹丹
	 * 创建日期：  2017年8月22日
	 * 功能描述：根据id查找X专区二级页面
	 * 参数描述：
	 * 返回值    ：  Xlist
	 */
	Xlist findById(int id);
	
	/**
	 * 创 建 人  ：  裴丹丹
	 * 创建日期：  2017年8月22日
	 * 功能描述：
	 * 参数描述：
	 * 返回值    ：  void
	 */
	int insert(Xlist xlist);
	
	/**
	 * 创 建 人  ：  裴丹丹
	 * 创建日期：  2017年8月22日
	 * 功能描述：
	 * 参数描述：
	 * 返回值    ：  int
	 */
	int updateById(int id);
	
	/**
	 * 创 建 人  ：  裴丹丹
	 * 创建日期：  2017年8月22日
	 * 功能描述：
	 * 参数描述：
	 * 返回值    ：  int
	 */
	int deleteById(int id);
}
