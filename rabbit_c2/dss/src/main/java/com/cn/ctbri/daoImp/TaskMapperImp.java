package com.cn.ctbri.daoImp;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.dao.TaskMapper;
import com.cn.ctbri.model.Task;
@Repository(value="taskDao")
@Transactional
public class TaskMapperImp implements TaskMapper {
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession ;
	@Override
	public int deleteByPrimaryKey(Long taskid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Task record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Task record) {
		// TODO Auto-generated method stub
		
		return sqlSession.insert("insertSelective", record);
	}

	@Override
	public Task selectByPrimaryKey(Long taskid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Task record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Task record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Task> selectByTask(Task record) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectByTask", record);
	}

}
