<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>
<link rel="stylesheet" href="resources/themes/base/ui.all.css">

<script src="resources/js/jquery-1.3.2.js" type="text/javascript"></script>
<script src="resources/ui/ui.core.js" type="text/javascript"></script>
<script src="resources/ui/ui.draggable.js" type="text/javascript"></script>
<script src="resources/ui/ui.resizable.js" type="text/javascript"></script>
<script src="resources/ui/ui.dialog.js" type="text/javascript"></script>
<script type="text/javascript" src="./resources/js/hdfs_dialog.js"></script>
<script type="text/javascript" src="./resources/js/my_genes.js"></script>

<style type="text/css">
	label, input { display:block; }
	input.text { margin-bottom:12px; width:400px; padding: .4em; display:inline-block }
	fieldset { padding:0; border:0; margin-top:10px; font-size:12px; }
	fieldset p { display:inline-block; font-size:18px;}
</style>
 
<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 
<div id="output_dialog" title="KOBIC Cloud Data Explorer">
	<div id="dialog_output_contents">
	</div>
</div>

<script>
$( document ).ready(function() {
	path_request("/express/"+"${user_id}");
});
</script>
<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">My Genes</h2>
	<h3>My Genes</h3>
	<!-- contents 정렬 S-->
	<div class="subconW" id="subconW">	
		<div class="mygenes">
			<p>KOBIC provides the KoDS high speed transmission system, which is internally developed for a safe and fast online transmission of large-scale bio data, up to several terabytes.</p>						
			<ul class="kodsdown">
				<li><a href="<c:url value='https://www.kobic.re.kr/do_down?path=KoDS-unix-en.tar.gz'/>">Linux Download</a></li>
				<li><a href="<c:url value='https://www.kobic.re.kr/do_down?path=KoDS-windows-en.zip'/>">Windows Download</a></li>
				<li><a href="<c:url value='https://www.kobic.re.kr/do_down?path=KoDS-macos-en.dmg'/>">Mac Download</a></li>
			</ul>
		</div>	
		
		<form id="analysis_form" onsubmit="return false;">
		<fieldset>
			<div id="select_data" class="path"></div>
			<div class="genearea">
				<select name="analysis_program_name" id="analysis_program_name">
					<option value="blast">BLAST</option>
					<option value="interpro">InterPro</option>
					<option value="clustalomega">Clustal Omega</option>
					<option value="muscle">MUSCLE</option>
				</select>
				<input type="submit" value="Analysis" class="btnsky btnanalysis" onclick="analysis_form_check();"/>
				<input type="button" value="Parent path" class="btnsky btnprev" onclick="parent_path_request();" />
				<input type="button" value="Download" class="btnsky btndown"  onclick="file_transfer();" />
			</div>
			<input type="hidden" value="${p_path}" name="p_path">
			<div style="max-height:295px; overflow-y: scroll; clear:both; margin-bottom:30px;">
			<table class="pop2" style="margin:0">
				<colgroup>
					<col width="30" />
					<col width="*" />
					<col width="120" />
					<col width="120" />
				</colgroup>
				<thead>
					<tr>
						<th>Select</th>
						<th>Name</th>
						<th>File Size</th>
						<th>Created Date</th>
					</tr>
				</thead>            

				<tbody id="explorer_tbody">
				</tbody>
			</table>
			</div>
		</fieldset>
		</form>
		
<div id="dialog" title="KOBIC Cloud Data Explorer">
	<form>
		<fieldset>
			<div id="validateTips" class="poppath"><p id="dialog_select_data"></p></div>
			<div class="floatR">				
				<input type="button" value="Parent path" class="btnsky btnprev" onclick="dialog_parent_path_request();"/>
				<input type="button" value="Download" class="btnsky btndown"  onclick="dialog_file_transfer();"/>
			</div>
			<input type="hidden" value="${p_path}" name="dialog_p_path">
			<table class="pop2">
				<colgroup>
					<col width="30" />
					<col width="*" />
					<col width="80" />
					<col width="120" />
				</colgroup>
				<thead>
					<tr>
						<th>Select</th>
						<th>Name</th>
						<th>File Size</th>
						<th>Created Date</th>
					</tr>
				</thead>            

				<tbody id="dialog_explorer_tbody">
					<!-- 
					<c:forEach var="file_list" items="${file_list}">
						<tr>
							<c:choose>
								<c:when test="${file_list.isFile == 'true'}">
									<td class="txtL"><img src="resources/images/sub/file.png" alt="file" />${fn:split(file_list.name,'^')[0]}</td>
								</c:when>
								<c:otherwise>
									<td class="txtL">
										<img src="resources/images/sub/folder.png" alt="folder" />
										<a style="cursor: pointer;" onclick="path_request('${file_list.HPath}');">${fn:split(file_list.name,'^')[0]}</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td><c:out value="${fn:split(file_list.name,'^')[1]}"/></td>
							<td>${file_list.createDate}</td>
						</tr>
					</c:forEach>
					 -->
				</tbody>
			</table>
		</fieldset>
	</form>
</div>

		<ul class="tab3">
			<li><a href="my_genes" <c:if test="${type=='all'}">class="on"</c:if>>ALL</a></li>
			<li><a href="my_genes?type=last" <c:if test="${type=='last'}">class="on"</c:if>>LAST</a></li>
			<li><a href="my_genes?type=blast" <c:if test="${type=='blast'}">class="on"</c:if>>BLAST</a></li>
			<li><a href="my_genes?type=interpro" <c:if test="${type=='interpro'}">class="on"</c:if>>InterPro</a></li>
			<li><a href="my_genes?type=clustalo" <c:if test="${type=='clustalo'}">class="on"</c:if>>Clustal Omega</a></li>
			<li><a href="my_genes?type=muscle" <c:if test="${type=='muscle'}">class="on"</c:if>>MUSCLE</a></li>
		</ul>
		
		<c:choose>
			<c:when test="${fn:length(instance_pipline) == 0}">
				<div class="foundbox">
					<p class="notfound">
						Sorry, No data.<br />Please make a project.
					</p>	
				</div>
				<div class="txtC mt20">
					<input type="button" class="btnblue" onclick="location.href='analysis'" value="Make a project" /> 
				</div>
			</c:when>
			<c:otherwise>
				<table class="mypagetype01">		    
					<colgroup>
					<col width="100" />
					<col width="200" />
					<col width="150" />
					<col width="*" />
					<col width="130" />
					<col width="90" />
					</colgroup>
					<thead>
						<tr>
							<th>Status</th>
							<th>Project Name</th>
							<th>Start Time</th>					
							<th>Progress</th>
							<th colspan="2">View</th>
							
						</tr>
					</thead>
					
					<c:forEach var="instance_pipline_item" items="${instance_pipline}" varStatus="var">
						<c:set value="2" var="colspan"></c:set>
						<c:choose>
							<c:when test="${instance_pipline_item.status == 0}">
								<c:set value="mypagetype01" var="mypagetype"></c:set>
								<c:set value="Waiting" var="status"></c:set>
							</c:when>
							<c:when test="${instance_pipline_item.status == 1}">
								<c:set value="mypagetype02" var="mypagetype"></c:set>
								<c:set value="Running" var="status"></c:set>
							</c:when>
							<c:when test="${instance_pipline_item.status == 2}">
								<c:set value="mypagetype03" var="mypagetype"></c:set>
								<c:set value="Compete" var="status"></c:set>
							</c:when>
						</c:choose>
						
						<tr>
							
							<c:choose>
								<c:when test="${instance_pipline_item.status == 0}">
									<td class="typebg"><img alt="" src="./resources/images/sub/maypage_yellow.png"></td>
								</c:when>
								<c:when test="${instance_pipline_item.status == 1}">
									<td class="typebg"><img alt="" src="./resources/images/sub/maypage_green.png"></td>
								</c:when>
								<c:when test="${instance_pipline_item.status == 2}">
									<td class="typebg"><img alt="" src="./resources/images/sub/maypage_blue.png"></td>
								</c:when>
								<c:otherwise>
									<td class="typebg"><img alt="" src="./resources/images/sub/maypage_red.png"></td>
								</c:otherwise>
							</c:choose>
							<td>${instance_pipline_item.instanceName}</td>
							<td>${instance_pipline_item.createDate}</td>
									
							<td>		
								<div class="type01_container">
									<div id="myProgress">
										<c:choose>
											<c:when test="${instance_pipline_item.status == 0}">
												0%
											</c:when>
											<c:when test="${instance_pipline_item.status == 1}">
												<div class="progress_label progress50">50%</div>
											</c:when>
											<c:when test="${instance_pipline_item.status == 2}">
												<div class="progress_label progress100">100%</div>
											</c:when>
										</c:choose>
									</div>								
								</div>
							</td>
							<th><a href="#<c:out value="${instance_pipline_item.id}"/>" class="typebtn">Parameters</a></th>
							<td>
							<c:choose>
								<c:when test="${instance_pipline_item.status == 2}">
									<input type="button" value="Result" class="btnview" onclick="open_dialog('${path_map[instance_pipline_item.instanceID]}');"/>
								</c:when>
								<c:otherwise>
									<input type="button" value="Result" class="btnview" disabled="disabled"/>
								</c:otherwise>
							</c:choose>							
							</td>		
						</tr>
							
						<tbody id="${instance_pipline_item.id}" style="display:none;">
							<c:forEach items="${xml_dispatch_map}" var="entry" varStatus="index">
								<c:if test="${entry.key == instance_pipline_item.instanceID}">
									<tr>
										<th colspan="2">Program</th>
										<th colspan="2">Parameters</th>
										<th colspan="2">Description</th> 
									</tr>
									
									<c:forEach items="${entry.value.modulesBeanList}" var="module" varStatus="index">
									
										<c:if test="${module.moduleName != 'START' && module.moduleName != 'END'}">
											<tr>						
												<td colspan="2" rowspan="${module.parameterNumber + 1}">${module.moduleName}</td>
						
												<c:forEach items="${entry.value.parametersBeanList}" var="params">
													
													<c:if test="${module.key ==  params.key}">
														<tr id="${params.name }">												
															<td>${params.name }</td>
															<td>${params.setupValue }</td>
															<td colspan="2">${params.description }</td>
														</tr>
													</c:if>
													
												</c:forEach>
											</tr>	
										</c:if>
									</c:forEach>
									
								</c:if>
							</c:forEach>		
						</tbody>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
		
		<!-- 
		<c:if test="${fn:length(instance_pipline) == 0}">
			<div class="foundbox">
				<p class="notfound">
					Sorry, No data.<br />Please make a project.
				</p>	
			</div>
			<div class="txtC mt20">
				<input type="button" class="btnblue" onclick="location.href='analysis'" value="Make a project" /> 
			</div>
		</c:if>
 		-->
 		
		<script>
			$("[id^='output_file']").remove();
		</script>
		
		<!--페이징 start -->
		<div class="paging">		
			<c:if test="${page_info.totalCount > 0}">
				<a href="<c:url value='my_genes?type=${type}&page_num=${page_info.firstPageNo}'/>">
					<img src="./resources/images/sub/btn_first.gif" alt="처음 페이지" /></a>
				<a href="<c:url value='my_genes?type=${type}&page_num=${page_info.prevPageNo}'/>">
					<img src="./resources/images/sub/btn_prev.gif" alt="이전 페이지" /></a>
				<c:forEach var="i" begin="${page_info.startPageNo }" end="${page_info.endPageNo}">
					<c:choose>
						<c:when test="${page_info.pageNo == i }">
							<a href="<c:url value='my_genes?type=${type}&page_num=${i}'/>">
								<span class="selected">${i}</span>
							</a>						
						</c:when>
						<c:otherwise>					
							<a href="<c:url value='my_genes?type=${type}&page_num=${i}'/>">
								<span>${i}</span>
							</a>					
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<a href="<c:url value='my_genes?type=${type}&page_num=${page_info.nextPageNo}'/>">
					<img src="./resources/images/sub/btn_next.gif" alt="다음 페이지" /></a>
				<a href="<c:url value='my_genes?type=${type}&page_num=${page_info.finalPageNo}'/>">
					<img src="./resources/images/sub/btn_last.gif" alt="마지막 페이지" /></a>
			</c:if>
		</div>		
		<!--페이징 end -->		
		
	</div>
	<!-- contents 정렬 E-->
</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>