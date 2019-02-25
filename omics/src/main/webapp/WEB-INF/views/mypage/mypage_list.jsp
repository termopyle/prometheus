<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
		
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<script type="text/javascript" src="./resources/js/mypage.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E -->

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>MY PAGE</h3>

	<form method="post" onsubmit="do_pass_check('${user_model.password}');return false;" name="user_infor_form" id="user_infor_form" >
			<input type="hidden" name="user_email" value="${user_model.email_adress}"/>
			<input type="hidden" name="user_id" value="${user_model.user_id}"/>
			<table class="join" summary="user info table" id="user_infor_table">
				<caption>User Information table</caption>
				<colgroup>
					<col style="width: 20%" />
					<col style="width: 80%" />
				</colgroup>
				<tbody>
					<tr>
						<th width="100">&nbsp;&nbsp;&nbsp;User ID</th>
						<td>${user_model.user_id}</td>
					</tr>
					<tr>
						<th>&nbsp;&nbsp;&nbsp;Name</th>
						<td>${user_model.user_name}</td>
					</tr>
 					<tr>
						<th><strong class="red">*</strong> Change password </th>
						<td>
							<input type="password" size="30" id="password1" name="password1" />
							<span style="font-size:11px;">&nbsp;&nbsp;It must be 8-20 characters long and contain at least one upper case letter, lower case letter, number, and special character such as ~!@#$%^&*. </span>
						</td>
					</tr>
					<tr>
						<th><strong class="red">*</strong> Change password confirm </th>
						<td><input type="password" size="30" id="password2" name="password2" /></td>
					</tr>
					<tr>
						<th>&nbsp;&nbsp;&nbsp;Scientific Technology Registration Number</th>
						<td><input type="text" size="30" id="identity_number" name="identity_number" value="${user_model.identity_number}" disabled="disabled"/></td>
					</tr>
					<tr>
						<th><strong class="red">*</strong> Organization </th>
						<td><input type="text" size="30" id="organization" name="organization" value="${user_model.organization}" disabled="disabled"/></td>
					</tr>
					<tr>
						<th>&nbsp;&nbsp;&nbsp;Position</th>
						<td><input type="text" size="30" id="position1" name="position" value="${user_model.position}" disabled="disabled"/></td>
					</tr>
					<tr>
						<th> <strong class="red">*</strong> E-mail address</th>
						<td><input type="text" size="50" id="emailAdress" name="emailAdress" value="${user_model.email_adress}" disabled="disabled"/></td>
					</tr>
					<tr>
						<th> <strong class="red">*</strong> Telephone number</th>
						<td><input type="text" size="30" id="tel" name="tel" value="${user_model.tel}" disabled="disabled"/></td>
					</tr>
					<tr>
						<th>&nbsp;&nbsp;&nbsp;Mobile number</th>
						<td><input type="text" size="30" id="hp" name="hp" value="${user_model.hp}" disabled="disabled"/></td>
					</tr>
					<tr>
						<th>&nbsp;&nbsp;&nbsp;Fax number</th>
						<td><input type="text" size="30" id="fax" name="fax" value="${user_model.fax}" disabled="disabled"/></td>
					</tr>
				</tbody>
			</table>
			
			
			<div class="txtC mt20" id="user_infor_button">
				<input type="button" class="btngreen" value="Confirm" onclick="do_user_infor_modify_check();"/> 
				<input type="reset" class="btngray" value="Reset" />
			</div>
			
			<div id="user_pass_check" style="display:none;">
				<div class="foundbox mt30">
					<p class="password">Enter your original password<br /><input type="password" id="password_check" name="password_check"/></p>
				</div>
				<div class="txtC mt20">
					<input type="submit" class="btngreen" value="Confirm" /> 
					<input type="button" class="btngray" value="Cancel" onclick="location.href='mypage'" />
				</div>	
			</div>
			
		</form>

</div>

<!-- contents E -->

<jsp:include page="../common/footer.jsp" flush="false"/>