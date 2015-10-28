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

import com.sinosoft.dao.CsOrderMapper;
import com.sinosoft.service.OrderService;
import com.sinosoft.util.JsonUtil;
import com.sinosoft.vo.CsOrder;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("OrderService")
@Component
public class OrderServiceImpl implements OrderService {

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

	JSONObject jsonObject = null;

	@POST
	@Path("countOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String countOrder(@FormParam("userId")
	Integer userId) {
		jsonObject = new JSONObject();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		List<CsOrder> csOrderList = csOrderMapper.findOrderByUserId(userId);
		return jsonObject.element("count", csOrderList.size()).toString();
	}

	@POST
	@Path("countServerOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String countServerOrder(@FormParam("userId")Integer userId,
			@FormParam("state")String state,@FormParam("currentDate")Date currentDate) {
		jsonObject = new JSONObject();
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("state", state);
		map.put("currentDate", currentDate);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		List<Map> csOrderMaps = csOrderMapper.findByCombineOrderTrack(map);
		return jsonObject.element("count", csOrderMaps.size()).toString();
	}

	@POST
	@Path("findOrderByOrderId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findOrderByOrderId(@FormParam("id")
	String id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		Map csOrder = csOrderMapper.findOrderByOrderId(id);
		return JsonUtil.encodeObject2Json(csOrder);
	}

	@POST
	@Path("findOrderByUserIdAndState")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findOrderByUserIdAndState(@FormParam("userId")Integer userId,
			@FormParam("state")String state,@FormParam("currentDate")Date currentDate,
			@FormParam("pageNow")Integer pageNow,@FormParam("pageSize")Integer pageSize) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		if(pageNow==-1||pageSize==-1){
			pageNow=null;
			pageSize=null;
		}
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("state", state);
		map.put("currentDate", currentDate);
		map.put("pageNow",pageNow);
		map.put("pageSize", pageSize);
		List<Map> csOrderList = csOrderMapper.findOrderByUserIdAndState(map);
		return JsonUtil.encodeObject2Json(csOrderList);
	}

	@POST
	@Path("findOrderMessage")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findOrderMessage(@FormParam("userId")
	Integer userId, @FormParam("id")
	String id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("id", id);
		Map csOrder = csOrderMapper.findOrderMessage(map);
		return JsonUtil.encodeObject2Json(csOrder);
	}

	@POST
	@Path("findOrderByUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findOrderByUserId(@FormParam("userId")
	Integer userId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		List<CsOrder> csOrderList = csOrderMapper.findOrderByUserId(userId);
		return JsonUtil.encodeObject2Json(csOrderList);
	}

	@POST
	@Path("findByCombineOrderTrack")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findByCombineOrderTrack(@FormParam("userId")Integer userId,
			@FormParam("state")String state,@FormParam("currentDate")Date currentDate,
			@FormParam("type")Integer type,@FormParam("sid")Integer sid,
			@FormParam("begin_date")Date begin_date,@FormParam("end_date")Date end_date) {
		if(type==-1){
			type=null;
		}
		if(sid==-1){
			sid=null;
		}
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("state", state);
		map.put("currentDate", currentDate);
		map.put("type", type);
		map.put("sid", sid);
		map.put("begin_date", begin_date);
		map.put("end_date", end_date);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		List<Map> csOrderList = csOrderMapper.findByCombineOrderTrack(map);
		return JsonUtil.encodeObject2Json(csOrderList);
	}

}
