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
	<h2 class="hidden">사용자 아이디 찾기</h2>
	<h3>Find User ID</h3>
  	<!-- contents 정렬 S-->    
		<div class="idpw">
			<div id="d1">
				<ul class="idpw_tab">
					<li><a href="<c:url value="goFindId"/>" class="on1">Find User ID</a></li>
					<li><a href="<c:url value="goFindPw"/>">Find Password</a></li>
				</ul>
				<div class="idpwbox">
					<form method="post" onsubmit="findCheck_by_id();return false;" id="find_check_form_id" name="find_check_form_id" action="doFindUserInfo" >
						<fieldset>
							<legend class="hidden">Find User ID</legend>
							<p class="id1">
								<label for="idname">Name</label>
								<input type="text" name="userName" id="userName" title="Name" placeholder="User Name" />
							</p>
							<p class="id2">
								<label for="idemail">Email Address</label>
								<input type="email" name="emailAdress" id="emailAdress" title="Email Address" placeholder="Email Address" />
							</p>
							<p class="id3">
								<input type="hidden" class="input-text" name="find_type" value="find_user_id"/>
								<input type="submit" value="Find" />
							</p>
						</fieldset>
					</form>
				</div>
			</div>
		</div>   
    <!-- contents 정렬 E--> 

</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>