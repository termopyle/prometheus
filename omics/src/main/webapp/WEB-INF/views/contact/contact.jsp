<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
		
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<!-- contents S-->
	<div id="contents" class="subcon conwrap">
		<h2 class="hidden">content</h2>
		<h3>Contact</h3>
		<!-- contents 정렬 S-->
		<div class="contact">
			<h4>KOBIC Location</h4>
			<p>125 Gwahak-ro, Yuseong-gu, Daejeon, 34141, Korea Tel: 82-42-879-8511, Fax: 82-42-879-8519</p>
			<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3212.359141103144!2d127.35673431582494!3d36.376298980038115!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x35654bb4e59e07c9%3A0xa8ee4dd9a73bbaf4!2z6rWt6rCA7IOd66qF7Jew6rWs7J6Q7JuQ7KCV67O07IS87YSw!5e0!3m2!1sko!2skr!4v1500530989408" width="980" height="300" frameborder="0" style="border:0" allowfullscreen></iframe>			 
		</div>
		<div class="contact mt30">
			<h4>Manager in charge</h4>
			<table class="contact">
				<tbody>
					<tr>
						<th>Manager in charge</th>
						<th>Email</th>
					</tr>
					<tr>
						<td>Project Leader: Yong-Min Kim Ph.D.</td>
						<td>ymkim@kribb.re.kr</td>
					</tr>
					<tr>
						<td>System construction coordinator: Gunhwan Ko</td>
						<td>kogun82@kribb.re.kr</td>
					</tr>
					<tr>
						<td>Database construction coordinator: Insu Jang</td>
						<td>insu078@kribb.re.kr</td>
					</tr>
					<tr>
						<td>Bioinformatics analysis coordinator: Namjin Koo</td>
						<td>njkoo@kribb.re.kr</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- contents 정렬 E--> 
		
	</div>
	<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>
	