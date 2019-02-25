<?xml version="1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>Prometheus The Omics Portal for Comparative Genomics</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="keywords" content="Prometheus The Omics Portal for Comparative Genomics"/>

<link href="./resources/images/common/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link href="./resources/css/basic.css" rel="stylesheet" type="text/css" />

<link href="./resources/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="./resources/themes/icon.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="./resources/js/jquery.js"></script>
<script type="text/javascript" src="./resources/js/script.js"></script>
<script type="text/javascript" src="./resources/js/jquery.revolver.min.js"></script>

<script type="text/javascript" src="<c:url value='./resources/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='./resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='./resources/js/taxonomy_tree.js'/>"></script>
<%
	request.setAttribute("bg_value", request.getParameter("bg_value"));
%>

</head>

<%
	String userId = (String) request.getSession().getAttribute("uid");
	String isAdmin = (String) request.getSession().getAttribute("is_admin");
	String isLogin = (String) request.getSession().getAttribute("is_login");
%>

<c:set value="<%=userId %>" var="user_id"></c:set>
<c:set value="<%=isAdmin %>" var="is_admin"></c:set>
<c:set value="<%=isLogin %>" var="is_login"></c:set>

<body class="${bg_value}">

<!-- MODAL -->
<div class="setup" id="setup"> 
	<div class="menu"> 
		<div id="texts" class="texts"></div>
		<a href="#setup" class="btn-close close">OK</a> 
	</div> 
</div> 

<!-- ==================== skipNavigation ==================== -->
<ul class="skipNavigation">
	<li><a href="#tmenu">menu</a></li>
	<li><a href="#contents">contents</a></li>
	<li><a href="#footer">footer</a></li>
</ul>

<!-- sitewrapper S -->
<div id="sitewrapper" class="${bg_value}">

		<!--header S -->
		<div id="header">
			<h1>
				<a href="<c:url value="./"/>">Prometheus</a>
				<span>The Omics Portal for Comparative Genomics</span>
			</h1>
			
			<!-- tm S -->
			<ul class="globalmenu">
				<li><a href="<c:url value="https://www.kobis.re.kr/"/>" target="_blank">KOBIS</a></li>
				<li><a href="https://www.bioexpress.re.kr/bioexpress.en" target="_blank">BIOEXPRESS</a></li>
			</ul>
			<ul class="tm">
				<li><a href="<c:url value="./"/>">HOME</a></li>
				
				<c:choose>
					<c:when test="${is_login == true }">
						<li><a href="<c:url value="doLogout"/>">LOGOUT</a></li>
						<li><a href="<c:url value="mypage"/>">MY PAGE</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="<c:url value="goLogin"/>">LOGIN</a></li>
					</c:otherwise>
				</c:choose>
				
			</ul>
			<!-- tm E -->
			
			<!-- menu S -->
			<div id="tmbox">
				<h2 class="hidden">menu</h2>
				<ul id="tmenu">
					<li><a href="<c:url value="go_intro"/>">Intro</a></li>
					<li><a href="<c:url value="taxonomy"/>">Archive</a></li>
					<li><a href="<c:url value="geneGroupSearch"/>">Gene Search</a></li>
					<li><a href="<c:url value="analysis"/>">Analysis</a></li>
					<li><a href="<c:url value="my_genes"/>">My Genes</a></li>
					<li class="last"><a href="<c:url value="https://www.bioexpress.re.kr/bioexpress.en"/>" target="_blank">Bio-Express</a></li>
					<li><a href="<c:url value="go_tutorial"/>">Tutorial</a></li>
					<li><a href="<c:url value="go_contact"/>">Contact</a></li>
				</ul>
			</div>
			<!--menu E -->

		</div>
		<!-- header E -->
		