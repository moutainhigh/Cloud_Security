package com.cn.ctbri.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.NoticeDao;
import com.cn.ctbri.dao.UserDao;
import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.User;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-3-10
 * 描        述：   公告数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class NoticeDaoImpl extends DaoCommon implements NoticeDao{

	/**
	 * 功        能： NoticeMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.NoticeMapper.";		

	/**
     * 功能描述：查询所有公告
     *       @time 2015-3-10
     */
	public List<Notice> findAllNotice() {
		return getSqlSession().selectList(ns + "list");

	}

	/**
     * 功能描述：添加公告
     *       @time 2015-3-10
     */
    public void addNotice(Notice notice) {
        this.getSqlSession().insert(ns + "insert", notice);
    }

    /**
     * 功能描述：删除公告
     *       @time 2015-3-10
     */
    public void deleteNotice(int id) {
        getSqlSession().delete(ns +"delete",id);
    }

    /**
     * 功能描述：修改公告
     *       @time 2015-3-10
     */
    public void updateNotice(Notice notice) {
        this.getSqlSession().update(ns + "update",notice);
    }

    /**
     * 功能描述：查询公告
     *       @time 2015-3-11
     */
    public Notice findNoticeById(Notice notice) {
        return getSqlSession().selectOne(ns + "list",notice);
    }

}
