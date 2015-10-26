package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.MobileInfo;
import com.cn.ctbri.entity.User;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2014-12-31
 * 描        述：  用户数据访问层接口类
 * 版        本：  1.0
 */
public interface UserDao {

	/**
	 * 功能描述：插入用户
	 * 参数描述： User user
	 *		 @time 2014-12-31
	 */
	void insert(User user);

	/**
	 * 功能描述：根据用户名查询用户
	 * 参数描述：String name
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	List<User> findUserByName(String name);

	/**
	 * 功能描述：根据手机号码查询用户
	 * 参数描述：String mobile
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	List<User> findUserByMobile(String mobile);


	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：String email
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	List<User> findUserByEmail(String email);

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：User user
	 *		 @time 2015-1-5
	 * 返回值    ：  List<User>
	 */
	List<User> findUserByCombine(User user);
	/**
	 * 功能描述：修改用户
	 * 参数描述：User globle_user
	 *		 @time 2015-1-5
	 */
	void update(User globle_user);
	/**
	 * 功能描述：根据用户类型
	 * 参数描述：int type
	 *		 @time 2015-2-2
	 */
	List<User> findUserByUserType(int type);
	/**
	 * 功能描述：查询所有用户
	 * 参数描述：int type
	 * @param user 
	 *		 @time 2015-2-2
	 */
	List<User> findAll(User user);
	/**
	 * 功能描述：删除用户
	 * 参数描述：int id
	 *		 @time 2015-2-2
	 */
	void delete(int id);

	List<User> fuzzyQueryByName(User user);
	/**
	 * 功能描述：查询活跃用户数
	 *		 @time 2015-3-11
	 */
	List<DataAnalysis> findHaveServSum();

	List<DataAnalysis> queryByPage(DataAnalysis criteria, int i, int pageSize);
	/**
	 * 功能描述：根据id查询用户信息
	 */
	List<User> findUserById(int id);
	/**
     * 添加注册发送手机信息
     */
    void addMobile(MobileInfo mobileInfo);
    /**
     * 修改注册发送手机信息
     */
    void updateMobile(int times);
    
    MobileInfo getMobileById(String MobileNumber);
    void updatePass(User user);
    /**
     * 根据状态查询用户列表
     * @param status
     * @return
     */
    List<User> findUserByStatus(int status);
}
