package com.sinosoft.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import com.sinosoft.dao.CsAlarmMapper;
import com.sinosoft.service.AlarmService;
import com.sinosoft.util.JsonUtil;
import com.sinosoft.vo.CsAlarm;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("AlarmService")
@Component
public class AlarmServiceImpl implements AlarmService {
	private final static SqlSessionFactory sqlSessionFactory;
	static {
		String resource = "SqlMapConfig.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	@POST
	@Path("findAlarmByUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAlarmByUserId(@FormParam("userId")
	Integer userId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		List<Map> alarmList = csAlarmMapper.findAlarmByUserId(userId);
		return JsonUtil.encodeObject2Json(alarmList);
	}

	@POST
	@Path("countAlarm")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String countAlarm(@FormParam("userId")
	Integer userId) {
		JSONObject jsonObject = new JSONObject();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		List<Map> alarmList = csAlarmMapper.findAlarmByUserId(userId);
		return jsonObject.element("count", alarmList.size()).toString();
	}

	@POST
	@Path("findAlarmByOrderId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAlarmByOrderId(@FormParam("websoc")
	Integer websoc, @FormParam("orderId")
	String orderId, @FormParam("group_flag")
	Date group_flag, @FormParam("type")
	Integer type, @FormParam("count")
	Integer count, @FormParam("level")
	Integer level, @FormParam("name")
	String name) {
		if(type==-1){
			type=null;
		}
		if(level==-1){
			level=null;
		}
		Map map = new HashMap();
		map.put("websoc", websoc);
		map.put("orderId", orderId);
		map.put("group_flag", group_flag);
		map.put("type", type);
		map.put("count", count);
		map.put("level", level);
		map.put("name", name);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		List<CsAlarm> csAlam = csAlarmMapper.findAlarmByOrderId(map);
		return JsonUtil.encodeObject2Json(csAlam);
	}

}
