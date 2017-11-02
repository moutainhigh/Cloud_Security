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

import com.ctbri.dao.CsOrderMapper;
import com.ctbri.service.OrderService;
import com.ctbri.util.JsonUtil;
import com.ctbri.vo.CsOrder;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("OrderService")
@Component
public class OrderServiceImpl extends ServiceCommon implements OrderService {

	JSONObject jsonObject = null;

	@POST
	@Path("countOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String countOrder(@FormParam("userId")
	Integer userId) {
		jsonObject = new JSONObject();
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
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
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
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
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		Map csOrder = csOrderMapper.findOrderByOrderId(id);
		List<Map> score = csOrderMapper.findScoreByOrderId(id);
		if(score.size()>0){
			csOrder.put("score", score.get(0).get("score"));
		}else{
			csOrder.put("score", "");
		}
		return JsonUtil.encodeObject2Json(csOrder);
	}

	@POST
	@Path("findOrderByUserIdAndState")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findOrderByUserIdAndState(@FormParam("userId")Integer userId,
			@FormParam("state")String state,@FormParam("currentDate")Date currentDate,
			@FormParam("pageNow")Integer pageNow,@FormParam("pageSize")Integer pageSize) {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
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
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
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
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
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
			@FormParam("type")Integer type,@FormParam("sId")Integer sId,
			@FormParam("begin_date")Date begin_date,@FormParam("end_date")Date end_date) {
		if(type==-1){
			type=null;
		}
		if(sId==-1){
			sId=null;
		}
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("state", state);
		map.put("currentDate", currentDate);
		map.put("type", type);
		map.put("sId", sId);
		map.put("begin_date", begin_date);
		map.put("end_date", end_date);
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsOrderMapper csOrderMapper = sqlSession.getMapper(CsOrderMapper.class);
		List<Map> csOrderList = csOrderMapper.findByCombineOrderTrack(map);
		return JsonUtil.encodeObject2Json(csOrderList);
	}

}
