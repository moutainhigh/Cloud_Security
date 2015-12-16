package com.ctbri.service.impl;

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

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ctbri.dao.CsAlarmMapper;
import com.ctbri.service.AlarmService;
import com.ctbri.util.JsonMapper;
import com.ctbri.util.JsonUtil;
import com.ctbri.vo.CsAlarm;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("AlarmService")
@Component
public class AlarmServiceImpl extends ServiceCommon implements AlarmService {

	@POST
	@Path("findAlarmByUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAlarmByUserId(@FormParam("userId")
	Integer userId) {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
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
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		List<Map> alarmList = csAlarmMapper.findAlarmByUserId(userId);
		return jsonObject.element("count", alarmList.size()).toString();
	}

	@POST
	@Path("findAlarmByOrderId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAlarmByOrderId(@FormParam("orderId")
	String orderId, @FormParam("group_flag")
	Date group_flag, @FormParam("type")
	Integer type, @FormParam("count")
	Integer count, @FormParam("level")
	Integer level, @FormParam("name")
	String name, @FormParam("serviceId")
	Integer serviceId,@FormParam("pageNow")Integer pageNow,
	@FormParam("pageSize") Integer pageSize) {
		if(type==-1){
			type=null;
		}
		if(level==-1){
			level=null;
		}
		if(pageNow==-1||pageSize==-1){
			pageNow=null;
			pageSize=null;
		}
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("group_flag", group_flag);
		map.put("type", type);
		map.put("count", count);
		map.put("level", level);
		map.put("name", name);
		map.put("serviceId", serviceId);
		map.put("pageNow", pageNow);
		map.put("pageSize", pageSize);
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		List<Map> csAlam = csAlarmMapper.findAlarmByOrderId(map);
		return JsonUtil.encodeObject2Json(csAlam);
	}
	
	@POST
	@Path("findAlarm")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAlarm(@FormParam("websoc")
		Integer websoc, @FormParam("alarmId")
		String alarmId, @FormParam("group_flag")
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
		map.put("id", alarmId);
		map.put("group_flag", group_flag);
		map.put("type", type);
		map.put("count", count);
		map.put("level", level);
		map.put("name", name);
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		List<CsAlarm> csAlam = csAlarmMapper.findAlarmByAlarmId(map);
		return JsonUtil.encodeObject2Json(csAlam);
	}
	@POST
	@Path("findAlarmByAlarmId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAlarmByAlarmId( @FormParam("alarmId")Integer alarmId) {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAlarmMapper csAlarmMapper = sqlSession.getMapper(CsAlarmMapper.class);
		Map csAlam = csAlarmMapper.selectByPrimaryKey(alarmId);
		return JsonUtil.encodeObject2Json(csAlam);
	}
}
