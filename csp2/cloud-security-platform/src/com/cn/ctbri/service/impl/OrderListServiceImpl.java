package com.cn.ctbri.service.impl;

import java.util.List;

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
	public PageBean queryPayRecordByPage(int userId, int pageCode){
		int totalRecord = 0;
		List orderList = orderListDao.findAllPayRecord(userId); //获取已经支付的所有订单
		if(orderList !=null && orderList.size() >= 0) {
			totalRecord = orderList.size();
		}
		
		// 使用当前页码和总记录数创建PageBean
		PageBean pb = new PageBean(pageCode, totalRecord,25);
		// 查询本页记录
		List<OrderList> datas = orderListDao.queryPayRecordByPage(userId, (pageCode - 1) * pb.getPageSize(), pb.getPageSize());
		
//		List<Object> pageList = new ArrayList<Object>();
//		Map<String,Object> m = new HashMap<String,Object>();
//		for(OrderList data : datas){
//			List<String> nameList = orderDao.findServiceAndNameByOrderId(data.getOrderId());
//			String serviceName = nameList.toString();
//			m.put("payTime", data.getPay_date());
//			m.put("id", data.getId());
//			m.put("sericeName", serviceName.substring(1, serviceName.length()-1));
//			m.put("price", data.getPrice());
//			pageList.add(m);
//		}
		// 保存pageBean中
		pb.setDatas(datas);
		return pb;//返回pageBean
	}

}
