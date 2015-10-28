package com.sinosoft.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sinosoft.dao.CsAlarmMapper;
import com.sinosoft.dao.CsAssetMapper;
import com.sinosoft.exception.CloudException;
import com.sinosoft.service.AssetService;
import com.sinosoft.util.JsonMapper;
import com.sinosoft.util.JsonUtil;
import com.sinosoft.vo.CsAsset;
import com.sun.jersey.spi.resource.Singleton;

/**
 * 查询资产
 * 
 */
@Singleton
@Path("AssetService")
@Component
public class AssetServiceImpl implements AssetService {
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
	@Path("selectByPrimaryKey")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String selectByPrimaryKey(@FormParam("id")
	Integer id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		CsAsset csAsset = csAssetMapper.selectByPrimaryKey(id);
		return JsonUtil.encodeObject2Json(csAsset);
	}

	@POST
	@Path("selectByUserId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String selectByUserId(@FormParam("userid")
	Integer userid,@FormParam("pageNow")Integer pageNow,@FormParam("pageSize")Integer pageSize) throws CloudException {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		if(pageNow==-1||pageSize==-1){
			pageNow=null;
			pageSize=null;
		}
		Map map = new HashMap();
		map.put("userid", userid);
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
	@Path("selectByUserIdAndStatus")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String selectByUserIdAndStatus(@FormParam("userid")
			Integer userid,@FormParam("pageNow")Integer pageNow,
			@FormParam("pageSize")Integer pageSize,
			@FormParam("status")Integer status) throws CloudException {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsAssetMapper csAssetMapper = sqlSession.getMapper(CsAssetMapper.class);
		if(pageNow==-1||pageSize==-1){
			pageNow=null;
			pageSize=null;
		}
		Map map = new HashMap();
		map.put("userid", userid);
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
