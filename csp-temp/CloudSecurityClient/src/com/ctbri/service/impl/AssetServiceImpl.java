package com.ctbri.service.impl;

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

import com.ctbri.dao.CsAssetMapper;
import com.ctbri.exception.CloudException;
import com.ctbri.service.AssetService;
import com.ctbri.util.JsonMapper;
import com.ctbri.util.JsonUtil;
import com.ctbri.vo.CsAsset;
import com.sun.jersey.spi.resource.Singleton;

/**
 * 查询资产
 * 
 */
@Singleton
@Path("AssetService")
@Component
public class AssetServiceImpl extends ServiceCommon implements AssetService {
	
	@POST
	@Path("selectByPrimaryKey")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String selectByPrimaryKey(@FormParam("id")
	Integer id) {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		CsAsset csAsset = csAssetMapper.selectByPrimaryKey(id);
		return JsonUtil.encodeObject2Json(csAsset);
	}

	@POST
	@Path("selectByUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String selectByUserId(@FormParam("userId")
	Integer userId,@FormParam("pageNow")Integer pageNow,@FormParam("pageSize")Integer pageSize) throws CloudException {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		if(pageNow==-1||pageSize==-1){
			pageNow=null;
			pageSize=null;
		}
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("pageNow", pageNow);
		map.put("pageSize", pageSize);
		List<CsAsset> assetList = csAssetMapper.selectByUserId(map);
		if (assetList == null || assetList.size() == 0) {
			throw new CloudException("ASSET.QUERY.NOT.EXIST");
		} else {
			JsonMapper jsonMapper = new JsonMapper();
			return jsonMapper.toJson(assetList);
		}
	}
	@POST
	@Path("getCountByUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getCountByUserId(@FormParam("userId")Integer userId) throws CloudException {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		Map map = new HashMap();
		map.put("userId", userId);
		List<CsAsset> assetList = csAssetMapper.selectByUserId(map);
		if (assetList == null || assetList.size() == 0) {
			throw new CloudException("ASSET.QUERY.NOT.EXIST");
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("count", assetList.size());
			return jsonObject.toString();
		}
	}

	@POST
	@Path("selectByUserIdAndStatus")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String selectByUserIdAndStatus(@FormParam("userId")
			Integer userId,@FormParam("pageNow")Integer pageNow,
			@FormParam("pageSize")Integer pageSize,
			@FormParam("status")Integer status) throws CloudException {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		if(pageNow==-1||pageSize==-1){
			pageNow=null;
			pageSize=null;
		}
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("status", status);
		map.put("pageNow", pageNow);
		map.put("pageSize", pageSize);
		List<CsAsset> assetList = csAssetMapper.selectByUserIdAndStatus(map);
		if (assetList == null) {
			throw new CloudException("ASSET.QUERY.NOT.EXIST");
		} else {
			JsonMapper jsonMapper = new JsonMapper();
			return jsonMapper.toJson(assetList);
		}
	}
}
