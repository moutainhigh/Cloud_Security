package com.cn.ctbri.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.OrderTaskDao;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IOrderTaskService;

/**
 * 创 建 人  ：  tangxr
 * 创建日期：  2015-11-10
 * 描        述：  订单任务业务层实现类
 * 版        本：  1.0
 */
@Service
public class OrderTaskServiceImpl implements IOrderTaskService{
	@Autowired
	OrderTaskDao orderTaskDao;
    /**
     * 功能描述：保存订单
     *       @time 2015-11-10
     */
	public void insertOrderTask(OrderTask orderTask) {
		orderTaskDao.save(orderTask);
	}
	
	public List<OrderTask> findOrderTask(Map<String, Object> paramMap) {
		return this.orderTaskDao.findOrderTask(paramMap);
	}

	public void update(OrderTask orderTask) {
		this.orderTaskDao.update(orderTask);
	}

    public int insert(Task task) {
        int taskId = orderTaskDao.insert(task);
        return taskId;
    }

    /**
     * 根据资产获取订单类型
     * @param order_asset_Id
     */
    public OrderAsset getTypeByAssetId(int order_asset_Id) {
        return orderTaskDao.getTypeByAssetId(order_asset_Id);
    }

    /**
     * 下一次扫描时间
     * @param paramMap
     */
    public Date getNextScanTime(Map<String, Object> paramMap) {
        return orderTaskDao.getNextScanTime(paramMap);
    }

    public void updateTask(Task task) {
        this.orderTaskDao.updateTask(task);
    }

    public void insertTaskWarn(TaskWarn taskwarn) {
        this.orderTaskDao.insertTaskWarn(taskwarn);
    }

    public List<Task> getTaskStatus(Order order) {
        return orderTaskDao.getTaskStatus(order);
    }

    public List<Task> findDelTask(Map<String, Object> delmap) {
        return this.orderTaskDao.findDelTask(delmap);
    }

    public List<Task> findTaskByGroupId(String group_id) {
        return this.orderTaskDao.findTaskByGroupId(group_id);
    }

    public List<OrderTask> findExpTask(Map<String, Object> map) {
        return this.orderTaskDao.findExpTask(map);
    }

    public List getArnhemTask() {
        return this.orderTaskDao.getArnhemTask();
    }

    public List getWebsocTask() {
        return this.orderTaskDao.getWebsocTask();
    }

	public int findDistrictIdByTaskId(String taskId) {
		return this.orderTaskDao.findDistrictIdByTaskId(taskId);
	}

	public List getArnhemTaskByEngine(String engine_addr) {
		return this.orderTaskDao.getArnhemTaskByEngine(engine_addr);
	}

	public List<Task> getDels() {
		return this.orderTaskDao.getDels();
	}

	public List<OrderTask> findByOrderId(String orderId) {
		return this.orderTaskDao.findByOrderId(orderId);
	}

	public void deleteByOrderId(String orderId) {
		orderTaskDao.delete(orderId);
	}
}
