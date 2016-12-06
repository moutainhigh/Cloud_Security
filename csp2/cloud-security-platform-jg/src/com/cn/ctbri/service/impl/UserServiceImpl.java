package com.cn.ctbri.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.LoginHistory;
import com.cn.ctbri.entity.MobileInfo;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.pager.PageBean;
import com.cn.ctbri.service.IUserService;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-4
 * 描        述：  用户业务层实现类
 * 版        本：  1.0
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserDao userDao;
	/**
	 * 功能描述：插入用户
	 * 参数描述： User user
	 *		 @time 2014-12-31
	 */
	public void insert(User user) {
		userDao.insert(user);
	}

	/**
	 * 功能描述：根据用户名查询用户
	 * 参数描述：String name
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByName(String name) {
		return userDao.findUserByName(name);
	}

	/**
	 * 功能描述：根据手机号码查询用户
	 * 参数描述：String mobile
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByMobile(String mobile) {
		return userDao.findUserByMobile(mobile);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：String email
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：User user
	 *		 @time 2015-1-5
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByCombine(User user) {
		return userDao.findUserByCombine(user);
	}

	/**
	 * 功能描述：修改用户
	 * 参数描述：User globle_user
	 *		 @time 2015-1-5
	 */
	public void update(User globle_user) {
		userDao.update(globle_user);
	}
	
	/**
	 * 功能描述：根据用户类型
	 * 参数描述：int type
	 *		 @time 2015-2-2
	 */
	public List<User> findUserByUserType(int type) {
		return userDao.findUserByUserType(type);
	}
	/**
	 * 功能描述：查询所有用户
	 * 参数描述：int type
	 *		 @time 2015-2-2
	 */
	public List<User> findAll(User user) {
		return userDao.findAll(user);
	}
	/**
	 * 功能描述：删除用户
	 * 参数描述：int id
	 *		 @time 2015-2-2
	 */
	public void delete(int id) {
		userDao.delete(id);
	}
	/**
	 * 功能描述：按用户名模糊查询用户
	 * 参数描述：int id
	 *		 @time 2015-2-2
	 */
	public List<User> fuzzyQueryByName(User user) {
		return userDao.fuzzyQueryByName(user);
	}
	/**
	 * 功能描述：查询活跃用户数
	 *		 @time 2015-3-11
	 */
	public List<DataAnalysis> findHaveServSum() {
		return userDao.findHaveServSum();
	}

	public PageBean<DataAnalysis> queryByPage(DataAnalysis criteria,
			int pageCode) {
		try {
			int totalRecord = 0 ;
			List<DataAnalysis> list = userDao.findHaveServSum();//获取总记录数
			if(list!=null &&list.size()>=0){
				totalRecord = list.size();
			}
			// 使用当前页码和总记录数创建PageBean
			PageBean<DataAnalysis> pb = new PageBean<DataAnalysis>(pageCode, totalRecord);
			// 查询本页记录
			List<DataAnalysis> datas = userDao.queryByPage(criteria, (pageCode - 1) * pb.getPageSize(), pb.getPageSize());
			// 保存pageBean中
			pb.setDatas(datas);
			return pb;//返回pageBean
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public List<User> findUserById(int id){
		
		return userDao.findUserById(id);
		
	}

	public void addMobile(MobileInfo mobileInfo) {
		// TODO Auto-generated method stub
		userDao.addMobile(mobileInfo);
	}

	public void updateMobile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		userDao.updateMobile(map);
	}

	public MobileInfo getMobileById(String MobileNumber) {
		// TODO Auto-generated method stub
		return userDao.getMobileById(MobileNumber);
	}

	public int updatePass(User user) {
		// TODO Auto-generated method stub
		return userDao.updatePass(user);
	}

	public void insertLoginHistory(LoginHistory lh) {
		userDao.insertLoginHistory(lh);
		
	}
	
	public int updateBalance(User user){
		return userDao.updateBalance(user);
	}
	
	public int updateUserMobile(User user){
		return userDao.updateUserMobile(user);
	}

}
