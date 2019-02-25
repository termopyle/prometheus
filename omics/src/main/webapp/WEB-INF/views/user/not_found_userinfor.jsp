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


<!-- contents S -->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">아이디/비밀번호찾기 실패</h2>
	<h3>Not found</h3>
	<!-- contents 정렬 S-->
	<div class="foundbox">
		<p class="notfound">
			Sorry, No matching information.<br />Please check your information again.
		</p>
	</div>
	<div class="txtC mt20">
		<input type="button" class="btnblue" onclick="location.href='goJoin'" value="Join" /> 
		<input type="button" class="btngreen" onclick="history.back()" value="Go back" />
	</div>

	<!-- contents 정렬 E-->

</div>

<!-- contents E -->


<jsp:include page="../common/footer.jsp" flush="false"/>