package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-27
 * 描        述：  任务业务层接口
 * 版        本：  1.0
 */
public interface ITaskService {

	/**
	 * 功       能：  根据OrderAssetId查询任务
	 * 创建日期：  2015-1-27
	 * 版        本：  1.0
	 */
	List<Task> findTaskByOrderAssetId(int orderAssetId);

	Object findByOrderId(String orderId);

    int insert(Task task);

	Task findProgressByOrderId(Map<String, Object> paramMap);

	Task findBasicInfoByOrderId(Map<String, Object> paramMap);

	List findScanTimeByOrderId(String orderId);

    Task findNearlyTask(String orderId);

    Task findTaskList(Map<String, Object> hisMap);

	Task getNewStatus(Map<String, Object> paramMap);

	//根据订单资产id,删除任务
    void deleteTaskByOaId(String order_asset_Id);
    int findTaskByUserId(int userId);
    //根据orderId,修改task表是否查看告警标识为1
    void update(Map<String, Object> hisMap);
    //根据订单id查询任务列表
    List<Task> findAllByOrderId(Map<String, Object> paramMap);

    //二期
    //查找任务
	List<Task> findTask(Map<String, Object> map);

	//根据orderid 查任务
	List<Task> findTaskByOrderId(String orderId);

	void updateTask(Task t);

	//完成并入库的任务
	List<Task> findFinishByOrderId(Map<String, Object> paramMap);

	int findissueCount(String orderId);

	//查询任务进度
	List<Task> findProgssByOrderId(Map<String, Object> pMap);

	List<Task> findFinishAlarmByOrderId(Map<String, Object> paramMap);

	void insertTaskWarn(TaskWarn taskwarn);
}
