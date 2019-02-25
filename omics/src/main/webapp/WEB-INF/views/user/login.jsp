<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- Script -->
<script type="text/javascript" src="./resources/js/user.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">로그인</h2>
	<h3>Login</h3>
	<!-- contents 정렬 S-->
	<div class="login">
		<p>
			<strong class="green">KOBIC</strong> Integrated login system
		</p>
		<div class="loginBox">
			<form action="doLogin" method="post" onsubmit="loginCheck();return false;" name="login_check_form" id="login_check_form" >
				<fieldset>
					<legend id="log" class="hidden">LOGIN</legend>
					
					<label for="id">ID</label> 
					<input type="text" title="ID" placeholder="ID" id="userId" name="userId" />
					
					<label for="pw">PW</label> 
					<input type="password" title="password" placeholder="PASSWORD" id="password" name="password"/> 
					
					<input type="submit" value="LOGIN" />
					
					<ul>
						<li class="loginbg1">If you don't have ID, Please Join us<a href="goJoin">JOIN</a></li>
						<li class="loginbg2">Find User ID or Password<a href="goFindId">ID / PW</a></li>
					</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- contents 정렬 E-->

</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>