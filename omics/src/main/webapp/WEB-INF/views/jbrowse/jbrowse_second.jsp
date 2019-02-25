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
		<form id="Url_information" name="Url_information" method="get" action="jbrowse_index.do">
			<input type="hidden" id="genomicfnaLocation" name="genomicfnaLocation" value="${generalInfo.genomicfnaLocation}"/>
			<input type="hidden" id="JBrowse" name="JBrowse" value="${generalInfo.jbrowse}"/>
			<dl>
				<dt>URL</dt>
				<dd>&bull; GenomicfnaLocation: ${generalInfo.genomicfnaLocation}</dd>
				<dd>&bull; JBrowse: ${generalInfo.jbrowse}</dd>
			</dl>
			<dl>
				<dd>&bull; RefSeqAssemblyID:
					<select id="refSeqAssemblyID" name="refSeqAssemblyID">
					<option>RefSeqAssemblyID 선택</option>
						<c:forEach var="result" items="${jbrowseResult}" varStatus="status">
							<option>${result.refSeqAssemblyID}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dd>&bull; Organism: ${generalInfo.organism}</dd>
			</dl>
			
			<table>
				<tr>
					<td colspan=1><input type="submit" value="입력"></td>
				</tr>
			</table>
		</form>
	</body>
</html>