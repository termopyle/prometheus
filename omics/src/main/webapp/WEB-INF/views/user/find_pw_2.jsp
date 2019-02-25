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
    <h2 class="hidden">사용자 비밀번호찾기 완료</h2>
    <h3>Find Password</h3>
    <!-- contents 정렬 S-->    
			<div class="foundbox">
				<p class="found">
				Your temporary password has been sent to
				<br><strong>${user_email}</strong></p>
			</div>
			<div class="txtC mt20">
				<input type="button" onclick="location.href='goLogin'" class="btngreen" value="Login" />
				<input type="button" onclick="location.href='goFindId'" class="btngray" value="Find ID" />
			</div>
   
   <!-- contents 정렬 E--> 
    
</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>