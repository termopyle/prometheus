<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

	<!-- footer S -->
	<div id="footer">
		<div class="footer">
			<p class="f_logo">
				<a href="<c:url value="https://www.kobic.re.kr/"/>" target="_blank"><img src="./resources/images/common/kobic_logo.gif" alt="kobic" /></a>
			</p>
			<address>
			125 Gwahak-ro, Yuseong-gu, Daejeon, 34141, Korea.<br />
			Tel: 82-42-879-8511, Fax: 82-42-879-8519, E-mail: webmaster@kobic.kr<br />
			COPYRIGHT&copy; 2017 BY KOREAN BIOINFORMATION CENTER(KOBIC). ALL RIGHTS RESERVED.
			</address>
			<div class="familysite site">
				<p><img src="./resources/images/common/linc_site.gif" alt="Family Site" /></p>
				<ul>
					<li><a href="<c:url value='https://bioexpress.kobic.re.kr/'/>" target="_blank">BIOEXPRESS</a></li>	
					<li><a href="<c:url value='https://www.kobic.re.kr/'/>" target="_blank">Korean Bioinformation Center</a></li>	
					<li><a href="<c:url value='https://www.kobis.re.kr/'/>" target="_blank">Korean Bio-resource Information System</a></li>	
					<li><a href="<c:url value='https://www.biodata.kr/'/>" target="_blank">BIODATA</a></li>					
				</ul> 
			</div>
		</div>
	</div>
	<!-- footer E --> 
	
</div>
<!-- sitewrapper E -->

<script>
	$('#setup').hide();
	$('a.btn-close').bind('click', function() {
		$(this.hash).hide();
		return false;
	});
</script>
</body>
</html>