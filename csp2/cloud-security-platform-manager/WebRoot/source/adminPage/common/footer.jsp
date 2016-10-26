<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<body>
		<div id="bottom">
			<div class="bot_top">
				<div class="bot_top-wrap">
					<img class="btnicon" src="${ctx}/source/backstageImg/btnicon.png"/>
					<img class="code" src="${ctx}/source/backstageImg/QRCode.png"/>
					<ul>
						<li class="title">联系我们</li>
						<li>
							<a href="javascript:;">客户电话</a>
						</li>
					</ul>
					<ul>
						<li class="title">厂商合作</li>
						<li>
							<a href="javascript:;">华为</a>
						</li>
						<li>
							<a href="javascript:;">安恒</a>
						</li>
						<li>
							<a href="javascript:;">知道创宇</a>
						</li>
					</ul>
					<ul>
						<li class="title">支持帮助</li>
						<li>
							<a href="javascript:;">常见问题</a>
						</li>
					</ul>
					<ul>
						<li class="title">新手入门</li>
						<li>
							<a href="javascript:;">新用户注册</a>
						</li>
						<li>
							<a href="javascript:;">用户登录</a>
						</li>
						<li>
							<a href="javascript:;">找回密码</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="bot_bottom">
				<div class="bot_bottom-wrap">CopyRight &copy; 中国电信股份有限公司北京研究院&nbsp;京ICP备12019458号 - 10</div>
			</div>
		</div>
			
	</body>
</html>
