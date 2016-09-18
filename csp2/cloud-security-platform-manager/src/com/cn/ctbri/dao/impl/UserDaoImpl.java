package com.cn.ctbri.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.DataAnalysis;
import com.cn.ctbri.entity.MobileInfo;
import com.cn.ctbri.entity.User;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2014-12-31
 * 描        述：   用户数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class UserDaoImpl extends DaoCommon implements UserDao{

	/**
	 * 功        能： UserMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.UserMapper.";		
	/**
	 * 功        能： LoginHistoryMapper命名空间
	 */
	private String ns_lh = "com.cn.ctbri.entity.LoginHistoryMapper.";	
	/**
	 * 功能描述：插入用户
	 * 参数描述： User user
	 *		 @time 2014-12-31
	 */
	public void insert(User user) {
		this.getSqlSession().insert(ns + "insert", user);
	}

	/**
	 * 功能描述：根据用户名查询用户
	 * 参数描述：String name
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByName(String name) {
		return this.getSqlSession().selectList(ns + "findUserByName", name);
	}

	/**
	 * 功能描述：根据手机号码查询用户
	 * 参数描述：String mobile
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByMobile(String mobile) {
		return this.getSqlSession().selectList(ns + "findUserByMobile", mobile);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：String email
	 *		 @time 2014-12-31
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByEmail(String email) {
		return this.getSqlSession().selectList(ns + "findUserByEmail", email);
	}

	/**
	 * 功能描述：根据邮箱查询用户
	 * 参数描述：User user
	 *		 @time 2015-1-5
	 * 返回值    ：  List<User>
	 */
	public List<User> findUserByCombine(User user) {
		return this.getSqlSession().selectList(ns + "findUserByCombine",user);
	}

	public void update(User globle_user) {
		this.getSqlSession().update(ns + "update",globle_user);
	}
	/**
	 * 功能描述：根据用户类型
	 * 参数描述：int type
	 *		 @time 2015-2-2
	 */
	public List<User> findUserByUserType(int type) {
		return getSqlSession().selectList(ns + "findUserByUserType",type);
	}

	public List<User> findAll(User user) {
		return getSqlSession().selectList(ns + "list",user);

	}

	public void delete(int id) {
		getSqlSession().delete(ns +"delete",id);
		
	}

	public List<User> fuzzyQueryByName(User user) {
		return getSqlSession().selectList(ns +"fuzzyQueryByName",user);
	}
	/**
	 * 功能描述：查询活跃用户数
	 *		 @time 2015-3-11
	 */
	public List<DataAnalysis> findHaveServSum() {
		return getSqlSession().selectList(ns +"findHaveServSum");
	}

	public List<DataAnalysis> queryByPage(DataAnalysis criteria,int offset, int len) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pageNow", offset);
        paramMap.put("pageSize", len);
        List<DataAnalysis> list = getSqlSession().selectList(ns +"queryByPage",paramMap);
		return list;
	}

	/**
     * 功能描述：根据id查询用户
     * 参数描述：int id
     * 返回值    ：  List
     */
	public List<User> findUserById(int id){
		return getSqlSession().selectList(ns+"findUserById", id);
	}

	public void addMobile(MobileInfo mobileInfo) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(ns + "addMobile", mobileInfo);
	}

	public void updateMobile(int times) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(ns + "updateMobile", times);
	}

	public MobileInfo getMobileById(String MobileNumber) {
		// TODO Auto-generated method stub
		MobileInfo mobileInfo = this.getSqlSession().selectOne("getMobileById", MobileNumber);
		return mobileInfo;
	}

	public void updatePass(User user) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(ns+"updatePass",user);
	}

	public List<User> findUserByStatus(int status) {
		return getSqlSession().selectList(ns+"findUserByStatus", status);
	}

	public int getCountByDates(Map<String, Object> paramMap) {
		return this.getSqlSession().selectOne(ns+"findCountByDates", paramMap);
	}

	public int findRegisterCount() {
		return this.getSqlSession().selectOne(ns+"findRegisterCount");
	}

	public int findRegisterCountByDates(Map<String, Object> paramMap) {
		return this.getSqlSession().selectOne(ns_lh+"findRegisterCountByDates", paramMap);
	}

	public List findLoginTop10(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns_lh+"findLoginTop10", paramMap);
	}

	public List findTimesTop5(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns_lh+"findTimesTop5", paramMap);
	}

	public void insertAPINum(APINum num) {
		getSqlSession().insert(ns+"insertAPINum", num);
	}
	
	public List findUserApikey(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ns+"findUserApiKey", paramMap);
	}

	public List<User> findUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(ns+"findUser", map);
	}
}
