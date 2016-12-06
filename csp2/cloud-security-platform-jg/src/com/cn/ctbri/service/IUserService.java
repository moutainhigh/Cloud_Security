package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.LoginHistory;
import com.cn.ctbri.entity.MobileInfo;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.pager.PageBean;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：  用户业务层接口
 * 版        本：  1.0
 */
public interface IUserService {
	
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
	/**
	 * 功能描述：按用户名模糊查询用户
	 * 参数描述：User user
	 *		 @time 2015-2-3
	 */
	List<User> fuzzyQueryByName(User user);
	/**
	 * 功能描述：查询活跃用户数
	 *		 @time 2015-3-11
	 */
	List<DataAnalysis> findHaveServSum();
	/**
	 * 分页查询
	 * @param pageCode
	 */
	PageBean<DataAnalysis> queryByPage(DataAnalysis criteria, int pageCode);

    List<User> findUserById(int id);
	
    /**
     * 添加注册发送手机信息
     */
    void addMobile(MobileInfo mobileInfo);
    /**
     * 修改注册发送手机信息
     */
    void updateMobile(Map<String, Object> map);
    
    MobileInfo getMobileById(String MobileNumber);
    
    int updatePass(User user);
    
    //插入用户登录历史
    void insertLoginHistory(LoginHistory lh);
    
    //更改安全币余额
    int updateBalance(User user);
    
    //更新用户的手机号码
    int updateUserMobile(User user);
}
