package com.cn.ctbri.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderListDao;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.pager.PageBean;
import com.cn.ctbri.service.IOrderListService;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2016-3-30
 * 描        述：  订单业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderListServiceImpl implements IOrderListService{
	@Autowired
	OrderListDao orderListDao;

	public void insert(OrderList ol) {
		orderListDao.insert(ol);
	}
	
	public OrderList findById(String id){
		return orderListDao.findById(id);
	}
	
	public void update(OrderList ol) {
		orderListDao.update(ol);
	}
	
	/**
     * 功能描述：消费记录-分页
     *       @time 2016-5-19
     * 返回值    ：  List<Order>
     */
	public PageBean<OrderList> queryPayRecordByPage(int userId, int pageCode){
		int totalRecord = 0;
		List orderList = orderListDao.findAllPayRecord(userId); //获取已经支付的所有订单
		if(orderList !=null && orderList.size() >= 0) {
			totalRecord = orderList.size();
		}
		
		// 使用当前页码和总记录数创建PageBean
		PageBean<OrderList> pb = new PageBean(pageCode, totalRecord,5);
		
		//2016-06-07
		if (pageCode < 1 || pageCode > pb.getTotalPage()) {
			pageCode = 1;
			pb.setPageCode(pageCode);
		}
		// 查询本页记录
		List<OrderList> datas = orderListDao.queryPayRecordByPage(userId, (pageCode - 1) * pb.getPageSize(), pb.getPageSize());
		
		for(OrderList ol: datas) {
			String[] serviceName = ol.getServerName().split(","); 
			Map<String,Integer> serverNameMap = new HashMap<String,Integer>();
			for (String name: serviceName){
	    		if(serverNameMap.containsKey(name)){
	    			int count = serverNameMap.get(name) +1;
	    			serverNameMap.put(name, count);
	    		}else {
	    			serverNameMap.put(name, 1);
	    		}
	    		
	    	}
			ol.setServerNameMap(serverNameMap);
		}
		
		// 保存pageBean中
		pb.setDatas(datas);
		return pb;//返回pageBean
	}
	
	public void updateBalanceFlag(OrderList ol){
		orderListDao.updateBalanceFlag(ol);
	}

	public List<OrderList> getPayRecord(Map<String, Object> param) {
		return orderListDao.findAllPayRecord((Integer) param.get("userId"));
	}
	
	public void updateOrderListId(String oldId, String newId) {
		orderListDao.updateOrderListId(oldId, newId);
	}

	public List getOrderInfoById(String orderId) {
		// TODO Auto-generated method stub
		return orderListDao.getOrderInfoById(orderId);
	}

}
