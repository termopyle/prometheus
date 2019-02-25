<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>
<%
	String userId = (String) request.getSession().getAttribute("uid");
	String isAdmin = (String) request.getSession().getAttribute("is_admin");
	String isLogin = (String) request.getSession().getAttribute("is_login");
%>
<c:set value="<%=isLogin %>" var="is_login"></c:set>

<!-- Script -->
<script type="text/javascript" src="./resources/js/user.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<c:if test="${is_login == true }">
	<script>
		alert("It is already signed.");
		history.back();
	</script>
</c:if>

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">회원가입</h2>
	<h3>Join</h3>
	 <!-- contents 정렬 S-->    
	<p class="mb10"> <strong class="red">*</strong> This is a required field.</p>
	<form action="doJoin" method="post" onsubmit="joinCheck();return false;" name="join_check_form" id="join_check_form" >
		<table class="join" summary="회원가입에 필요한 입력사항">
			<caption>회원가입</caption>
	
			<colgroup>
				<col width="20%" />
				<col width="80%" />
			</colgroup>
	
			<tbody>
				<tr>
					<th width="100">
						<strong class="red">*</strong> User ID 
					</th>
					<td>
						<input type="text" id="userId" name="userId" size="30" />
						<input name="join" class="btngreen" id="join" onclick="checkId();" type="button" value="Duplicate Check">
						<input type="hidden" id="duplicate" name="duplicate" value="">
						<span id="check"></span>
					</td>
				</tr>
				<tr>
					<th>
						<strong class="red">*</strong> Password 
					</th>
					<td>
						<input type="password" id="password" name="password" size="30"/>
					</td>
				</tr>
				<tr>
					<th>
						<strong class="red">*</strong> Confirm Password 
					</th>
					<td>
						<input type="password" id="password2" name="password2" size="30"/>
					</td>
				</tr>
				<tr>
					<th>
						<strong class="red">*</strong> User Name 
					</th>
					<td>
						<input type="text" id="userName" name="userName" size="30"/>
					</td>
				</tr>
				<tr>
					<th>
						 Scientific Technology Registration Number
					</th>
					<td>
						<input type="text" id="identity_number" name="identity_number" size="30" onkeydown="return onlyNum(event);" onkeyup="removeChar(event);" style="ime-mode:disable;" maxlength="8"/>
					</td>
				</tr>
				<tr>
					<th>
						<strong class="red">*</strong> Organization 
					</th>
					<td>
						<input type="text" id="organization" name="organization" size="30"/>
					</td>
				</tr>
				<tr>
					<th>
						Position
					</th>
					<td>
						<input type="text" id="position" name="position" size="30"/>
					</td>
				</tr>
				<tr>
					<th><strong class="red">*</strong> E-mail Address</th>
					<td>
						<input type="text" id="emailAdress" name="emailAdress" size="50"/>
						<span>ex)&nbsp; example@email.com</span>
					</td>
				</tr>
				<tr>
					<th><strong class="red">*</strong> Telephone Number</th>
					<td>
						<input type="text" id="tel" name="tel" size="30" onkeydown="return onlyNum(event);" onkeyup="removeChar(event);" style="ime-mode:disable;"/>						
						<span>Please enter only numbers.</span>
					</td>
				</tr>
				<tr>
					<th>Mobile Number</th>
					<td>
						<input type="text" id="hp" name="hp" size="30" onkeydown="return onlyNum(event);" onkeyup="removeChar(event);" style="ime-mode:disable;"/>
						<span>Please enter only numbers.</span>
					</td>
				</tr>
				<tr>
					<th>Fax Number</th>
					<td>
						<input type="text" id="fax" name="fax" size="30" onkeydown="return onlyNum(event);" onkeyup="removeChar(event);" style="ime-mode:disable;"/>
						<span>Please enter only numbers.</span>
					</td>
				</tr>
			</tbody>
		</table>
	
		<div class="txtC mt20">
			<input type="submit" class="btngreen" value="Join" />
			<input type="reset" class="btngray" value="Reset" />
		</div>
   </form>
    <!-- contents 정렬 E--> 

</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>