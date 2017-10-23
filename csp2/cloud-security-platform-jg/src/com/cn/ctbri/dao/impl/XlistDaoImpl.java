/**
 * 创 建 人  ：  裴丹丹
 * 创建日期：  2017年8月22日
 * 描        述：  
 * 版        本：  1.0
 */
package com.cn.ctbri.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.XlistDao;
import com.cn.ctbri.entity.Xlist;

/**
 * 创 建 人  ：  裴丹丹
 * 创建日期：  2017年8月22日
 * 描        述：  
 * 版        本：  1.0
 */
@Repository
public class XlistDaoImpl extends DaoCommon implements XlistDao {
	
	private static final String ns = "com.cn.ctbri.entity.XlistMapper.";

	
	public List<Xlist> listXlist() {
		return this.getSqlSession().selectList(ns + "listAll");
	}


	public Xlist findById(int id) {
		return this.getSqlSession().selectOne(ns + "findById", id);
	}


	public int insert(Xlist xlist) {;
		return this.getSqlSession().insert(ns + "insert", xlist);
	}


	public int updateById(int id) {
		return this.getSqlSession().update(ns + "updateById", id);
	}

	public int deleteById(int id) {
		return this.getSqlSession().delete(ns + "deleteById", id);
	}

}
