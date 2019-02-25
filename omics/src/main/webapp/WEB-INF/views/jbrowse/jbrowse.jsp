<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
	<form id="loginForm" name="loginForm" method="get" action="jbrowse_result.do">
		<table>
			<tr>
				<td>kindom</td>
				<td><input type="text" id="kingdom" name="kingdom"/></td>
			</tr>
			<tr>
				<td>taxId</td>
				<td><input type="text" id="taxId" name="taxId"/></td>
			</tr>
			<tr>
				<td>version</td>
				<td><input type="text" id="version" name="version"/></td>
			</tr>
			<tr>
				<td colspan=3><input type="submit" value="입력"></td>
			</tr>
		</table>
	</form>
</body>
</html>