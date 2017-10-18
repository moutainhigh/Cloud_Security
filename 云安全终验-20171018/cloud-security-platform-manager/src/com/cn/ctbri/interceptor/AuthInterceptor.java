package com.cn.ctbri.interceptor;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cn.ctbri.annotation.AuthPassport;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAuthorityService;
import com.cn.ctbri.util.CommonUtil;


public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	IAuthorityService authorityService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	//当前登录用户
    	User user = CommonUtil.getGloble_user(request);
    	//获取访问Url
		String path = request.getServletPath();
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            //没有声明需要权限,或者声明不验证权限
                if(authPassport == null || authPassport.validate() == false)
                return true;
            else{/**在这里实现自己的权限验证逻辑*/
        		// 超级管理员有所有的权限，系统管理员拥有指定的权限，注册用户只有前台的权限
        		if (user.getType()==0) {//用户类型（0：超级管理员，1：管理员，2：用户）
        			return true;
        		}
            	if(user.getType()==1){
            		//获取系统管理员的拥有的所有可以访问的Url
            		List<String> listPath = authorityService.findUrlByUserType(1);
            		return hasAuth(request, response, path, listPath);
            	}
            	if(user.getType()==2){
            		//获取系统管理员的拥有的所有可以访问的Url
            		List<String> listPath = authorityService.findUrlByUserType(2);
            		return hasAuth(request, response, path, listPath);
            	}
                else//如果验证失败
                {
                    //返回到登录界面
                    response.sendRedirect(request.getContextPath()+"/toLoginUI.html");
                    return false;
                }       
            }
        }
        else
            return true;   
     }

    //是否具有访问权限
	private boolean hasAuth(HttpServletRequest request,
			HttpServletResponse response, String path, List<String> listPath)
			throws IOException {
		if(listPath.contains(path)){
			return true;
		}else{
			//否则提示无访问权限
			 response.sendRedirect(request.getContextPath()+"/loginUI.html");
		     return false;
		}
	}
}
