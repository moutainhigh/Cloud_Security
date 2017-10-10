package com.cn.ctbri.portscanDao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DaoSupport;
 
import static org.springframework.util.Assert.notNull;
 
/**
 * MyBatis数据源切换
 * @author : zoutuo
 * @version : 1.0
 * @date : 14-3-27 下午3:57
 */
public class MySqlSessionDaoSupport2 extends DaoSupport {
 
    private SqlSession sqlSession;
 
    private boolean externalSqlSession;
 
    @Autowired(required = false)
    public  void setSqlSessionFactory(@Qualifier("sqlSessionFactoryB") SqlSessionFactory sqlSessionFactory) {
    	System.out.println("aaaaaaa+++++bbbb");
        if (!this.externalSqlSession) {
            this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
        }
    }
 
    @Autowired(required = false)
    public final void setSqlSessionTemplate(@Qualifier("sqlSession2")SqlSessionTemplate sqlSessionTemplate) {
        System.out.println("sqlswssssssssssss");
    	this.sqlSession = sqlSessionTemplate;
        this.externalSqlSession = true;
    }
 
    /**
     * Users should use this method to get a SqlSession to call its statement methods
     * This is SqlSession is managed by spring. Users should not commit/rollback/close it
     * because it will be automatically done.
     *
     * @return Spring managed thread safe SqlSession
     */
    public final SqlSession getSqlSession() {
        return this.sqlSession;
    }
 
    /**
     * {@inheritDoc}
     */
    protected void checkDaoConfig() {
        notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }
}