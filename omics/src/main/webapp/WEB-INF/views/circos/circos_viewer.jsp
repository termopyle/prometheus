<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include> 

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

	<!-- contents S-->
	<div id="contents" class="subcon conwrap1200">
		<h2 class="hidden">메인컨텐츠</h2>
		<h3>${species_name}</h3>
			<div id="step">
				<form id="jd_toolSubmissionForm">
					<div class="step2">
						<h5>General Information</h5>
						<div class="category03">
							<div class="tablebox">
								<table>
									<colgroup>
										<col width="200px" />
										<col width="328px" />								
									</colgroup>
									<thead>
										<tr>
											<th colspan="2">${species_name}</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>Taxonomy ID</td>
											<td>${assemblyResult.taxId}</td>
											
										</tr>
										<tr>
											<td>Species Name</td>
											<td>${assemblyResult.organism}</td>
											
										</tr>
										<tr>
											<td>Assembly ID</td>
											<td>${assemblyResult.refSeqAssemblyID}</td>
											
										</tr>
										<tr>
											<td>Assembly Name</td>
											<td>${assemblyResult.assemblyName}</td>										
										</tr>
										<tr>
											<td>Assembly Type</td>
											<td>${assemblyResult.assemblyType}</td>										
										</tr>
										<tr>
											<td>Assembly Level</td>
											<td>${assemblyResult.assemblyLevel}</td>
										</tr>
										<tr>
											<td>Release Type</td>
											<td>${assemblyResult.releaseType}</td>										
										</tr>									
										<tr>
											<td>Genome Representation</td>
											<td>${assemblyResult.genomeRepresentation}</td>
											
										</tr>
										<tr>
											<td>Gene Count</td>
											<td>${assemblyResult.geneCount}</td>										
										</tr>
										<tr>
											<td>source</td>
											<td>${assemblyResult.source}</td>										
										</tr>										
										<tr>
											<td>Release Version</td>
											<td>${assemblyResult.releaseVersion}</td>										
										</tr>										
										<tr>
											<td>Release Date</td>
											<td>${assemblyResult.releaseDate}</td>										
										</tr>										
									</tbody>
								</table>
							</div>
							<div class="tablebox ml20" id="entrez_recoards_table">
								<table>
									<thead>
										<tr>
											<th colspan="2">Entrez records</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>Database name</td>
											<td>Direct links</td>
										</tr>
										<c:set value="0" var="max_line"></c:set>
										<c:forEach items="${entrez}" var="entrez_value" >
											
											<c:if test="${fn:length(entrez_value.databaseNm) != 0}">
											<c:set value="${max_line + 1}" var="max_line"></c:set>
												<tr>
													<td>${entrez_value.databaseNm}</td>
													<td>
														<a target="_blank" href="<c:url value="https://www.ncbi.nlm.nih.gov/${entrez_value.databaseLinks}"/>">
														<img src="./resources/images/common/link_go.png" alt="" /> ${entrez_value.linksCount}
														</a>
													</td>
												</tr>
											</c:if>
										</c:forEach>
										<c:set value="${11-max_line}" var="min_line"></c:set>
 										<c:if test="${min_line > 0}">												
											<c:forEach var="i" begin="0" end="${min_line - 1}" step="1">
												<tr>
													<td></td><td></td>
												</tr>
											</c:forEach>
										</c:if> 

									</tbody>
								</table>
							 	<c:if test="${min_line < 0}">							 	
									<script>
										$("#entrez_recoards_table").css("overflow-y","auto");
										$("#entrez_recoards_table").css("overflow-x","hidden");
										$("#entrez_recoards_table").css("width","560px");
										$("#entrez_recoards_table").css("height","292px");
									</script>															
							 	</c:if> 
							</div>
						</div>
					</div>
					<div class="step3">
						<h5>Genome-Browser<span>Control Toolbox</span></h5>
						<div class="in inbg">
							<table class="depth03">
								<colgroup>
								<col width="120" />
								<col width="120" />
								<col width="220" />
								<col width="300" />
								<col width="*" />
								</colgroup>
								<tr>
									<th>Zoom In</th>
									<th>Search Type</th>
									<th>Query</th>
									<th rowspan="2" class="left">
										<input type="button" name="searchbtn" id="searchbtn" value="Search" class="btnbluesearch" onclick="check_position(tab_number);"/>																
										<input type="button" name="prev_btn" id="prev_btn" value="" class="btnbluesearch_prev" style="cursor: pointer;" onclick="progress('pre', tab_number);"/>
										<input type="button" name="refresh_btn" id="refresh_btn" value="" class="btnbluesearch_refresh" style="cursor: pointer;" onclick="circos_view_call(tab_number,'ref_seq');"/>
										<input type="button" name="next_btn" id="next_btn" value="" class="btnbluesearch_next" style="cursor: pointer;" onclick="progress('nex', tab_number);"/>															
									</th>
									<th>Result</th>
								</tr>
								<tr>
									<td>
										<select name="zoom_in" id="zoom_in">
											<option value="100">100%</option>
											<option value="200">200%</option>
											<option value="300">300%</option>
											<option value="400">400%</option>
											<option selected="selected" value="500">500%</option>
											<option value="600">600%</option>
											<option value="700">700%</option>
											<option value="800">800%</option>
											<option value="900">900%</option>
											<option value="1000">1000%</option>											
										</select>
									</td>
									<td>
										<select name="search_tyep" id="search_type">
											<option value="position">Position</option>
											<option value="gene_name">Gene Name</option>
										</select>
									</td>
									<td>
										<input type="text" id="query" name="query" 
										class="sch" placeholder="ex.) gene name, protein id.."
										onkeyup="writeGeneName(this.value);"
										onfocus="writeGeneName(this.value);"
										autocomplete = "off" />
										
										<div id="searchFrame" class="searchFrame" onblur="outFocus();"							
										 	style="position:absolute; background-color:rgba(255, 255, 255, 0.8); 
										 	width:188px; border:1px solid #eee;">						
										</div>
										
									</td>
									<td>
										<p id="preview">${species_name} loading..</p>
							        </td>
								</tr>
							</table>
							

						</div>
						<div class="in">
							<ul class="tab02">
<!-- 								<li><a id="tab_cont1" onclick="circos_view_call('1', 'ref_seq')" class="on" >Ref-Seq</a></li> -->
<!-- 								<li><a id="tab_cont2" onclick="circos_view_call('2', 'ensemble')" class="off">Ensemble</a></li> -->
<!-- 								<li><a id="tab_cont3" onclick="circos_view_call('3', 'plant')" class="off">Raw Data</a></li> -->
								<li>
									<a class="on">
										<c:choose>
											<c:when test="${db == 'ref_seq'}">Ref-Seq</c:when>
											<c:when test="${db == 'ensembl'}">Ensembl</c:when>
											<c:when test="${db == 'plant'}">Raw Data</c:when>
										</c:choose>
									</a>
								</li>
							</ul>
							
							<div id="div_tab_1" style="display:block;">
								<div id="spinner" class="inbox">
									<div id="sgv_div" style="text-align:center;"> 
									<div id="sgv_value">
										<br/><br/>
										<div class="load2">																
										</div>		
										<br/>
										<strong>This is being processed..</strong>Please wait...							
									</div> 
									</div>
								</div>
							</div>
							
<!-- 							<div id="div_tab_1" style="display:block;"> -->
<!-- 								<div id="spinner" class="inbox"> -->
<!-- 									<div id="sgv_div" style="text-align:center;">  -->
<!-- 									<div id="sgv_value_1"> -->
<!-- 										<br/><br/> -->
<!-- 										<div class="load2">																 -->
<!-- 										</div>		 -->
<!-- 										<br/> -->
<!-- 										<strong>This is being processed..</strong>Please wait...							 -->
<!-- 									</div>  -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							
<!-- 							<div id="div_tab_2" style="display:none;"> -->
<!-- 								<div id="spinner" class="inbox"> -->
<!-- 									<div id="sgv_div" style="text-align:center;">  -->
<!-- 									<div id="sgv_value_2"> -->
<!-- 										<br/><br/> -->
<!-- 										<div class="load2">																 -->
<!-- 										</div>		 -->
<!-- 										<br/> -->
<!-- 										<strong>Ensemble This is being processed..</strong>Please wait...							 -->
<!-- 									</div>  -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							
<!-- 							<div id="div_tab_3" style="display:none;"> -->
<!-- 								<div id="spinner" class="inbox"> -->
<!-- 									<div id="sgv_div" style="text-align:center;">  -->
<!-- 									<div id="sgv_value_3"> -->
<!-- 										<br/><br/> -->
<!-- 										<div class="load2">																 -->
<!-- 										</div>		 -->
<!-- 										<br/> -->
<!-- 										<strong>Others This is being processed..</strong>Please wait...							 -->
<!-- 									</div>  -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							
						</div>
					</div>
					<!-- <div class="step5">
						<p class="stepbox01">This box proposes actions. <strong>Edit - Analyse - Export</strong> only when the 'Table view' tab is visible</p>
						<p class="stepbox02">Send to:
							<select>
								<option>PNG</option>
								<option>HTML</option>
								<option>SGV</option>
							</select>
							<input type="button" name="download_btn" id="download_btn" value="Download" class="btnblue ml20" />
						</p>
					</div> -->
					
					<ul class="GIlink">
		              	<li class="color01"><a href="<c:url value='do_file_down?path=${genome}'/>">Genome</a></li>
		              	<li class="color02"><a href="<c:url value='do_file_down?path=${cds}'/>">CDS </a></li>
		              	<li class="color03"><a href="<c:url value='do_file_down?path=${pep}'/>">PEP</a></li>
		            </ul>
				</form>
			</div>	
	</div>

	<script>
		var gene_name = "${gene_names}";
		
		var db_path = "${db_path}";
		console.log(db_path);
		var data = gene_name.split(',');
		data.sort();
	</script>
	<script type="text/javascript" src="./resources/js/circos_search.js"></script>
	<!-- contents E --> 
	<input type="hidden" value="${db}" id="db"/>
	<input type="hidden" value="${taxon_id}" id="taxon_id"/>
	<input type="hidden" value="${auto_position}" id="auto_position"/>
	<input type="hidden" value="${total_length}" id="total_length"/>
	
<script type="text/javascript" src="./resources/js/cgviewer.js"></script>
<jsp:include page="../common/footer.jsp" flush="false"/>