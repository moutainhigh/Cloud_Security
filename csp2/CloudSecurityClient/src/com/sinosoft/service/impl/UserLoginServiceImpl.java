package com.sinosoft.service.impl;

import java.io.IOException;
import java.io.Reader;

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

import com.sinosoft.dao.CsUserMapper;
import com.sinosoft.exception.CloudException;
import com.sinosoft.service.UserLoginService;
import com.sinosoft.util.JsonUtil;
import com.sinosoft.util.MD5Util;
import com.sinosoft.vo.CsUser;
import com.sun.jersey.spi.resource.Singleton;

/**
 * 用户登陆
 * 
 */
@Singleton
@Path("LoginService")
@Component
public class UserLoginServiceImpl extends ServiceCommon implements UserLoginService {

	/**
	 * 用户登陆
	 * 
	 * @param account
	 *            用户名
	 * @param password
	 *            密码
	 * @throws CloudException
	 */
	@POST
	@Path("{login}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@FormParam("account")
	String account, @FormParam("password")
	String password) {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsUserMapper csUserMapper = sqlSession.getMapper(CsUserMapper.class);
		CsUser user = csUserMapper.selectByUserName(account);
		if (user == null) {
			user = new CsUser();
			user.setState("10002");
		} else {
			if (MD5Util.MD5(password).equals(user.getPassword())) {
				user.setState("10001");
			} else {
				user = new CsUser();
				user.setState("10003");
			}
		}
		return JsonUtil.encodeObject2Json(user);

	}
}
