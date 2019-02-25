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
		<h2 class="hidden">메인컨텐츠</h2>
		<h3>Genome Archive</h3>
		
		<div class="category01">
		<form action="taxonomy" id="search_form">
			<!-- search S -->
			<div class="search" style="top: 5px;">
				<fieldset>
					<legend>search</legend>
					<label for="query"></label>
					<input type="text" id="keyword" name="keyword" class="sch1" 
					placeholder="search" value=""
					onkeyup="writeGeneName(this.value);"
					onfocus="writeGeneName(this.value);"
					autocomplete = "off"
					/>
					
					<div id="searchFrame" class="main_search_frame" onblur="outFocus();"							
					style="position:absolute; width:380px; /* border:1px solid #eee; */ border: 0px; background-color:rgba(255, 255, 255, 0.95);
					margin-left:275px; top:37px; ">						
					</div>
					
					<label for="sch2">searchbutton</label>
					<input type="submit" id="searchbtn" class="sch2" alt="search" value=""/>
				</fieldset>					
			</div>
			<!-- search E --> 
			<!-- <input type="button" name="download_btn" id="download_btn" value="Download" class="btnblue ml20" /> -->
		</form>
	
		<script type="text/javascript" src="./resources/js/search.js"></script>

		
			<p>Genome information collected from RefSeq (NCBI,
				http://www.ncbi.nlm.nih.gov/refseq/), Ensembl
				(http://asia.ensembl.org/), Phytozome
				(https://phytozome.jgi.doe.gov/pz/portal.html) and SGN (Sol Genomics
				Network, https://solgenomics.net/) were served in “Genome Archive”.
				The genome information provides based on taxonomy tree and users are
				able to access “General Information” containing assembly and
				annotation information. Users can access genome information through
				genome browser and are able to download sequence of genome, CDS and
				peptide.</p>
		</div>

		<div class="category02">
				<div class="treecontrol">
					<a href="#"><img src="./resources/images/sub/tree_close.gif" alt="전체 닫기" /></a>
					<a href="#"><img src="./resources/images/sub/tree_open.gif" alt="전체 열기" /></a>
				</div>
				<!-- Taxon Tree S -->
					<div id="loading"></div>
					<div class="tree_ul" style=" width:570px; float:left; background:url('./resources/images/taxonomy/sch_bg.gif') right bottom #f9f9f9 no-repeat;">
						<ul id="tb" class="easyui-tree"  style="height: 390px; margin-top: 10px;"
							onMouseOver="scroll_over('tb')" onMouseOut="scroll_out('tb')"
							url="initData.do?keyword=<c:out value='${param.keyword}'/>"
							data-options="
								onClick: function(node){								
									var id = node.id;								
									if (node){
										var s = node.text;								
										var rank = node.rank;
										var end_yn = node.end_yn;
										var leaf_node = node.leaf_node;	
										if (node.attributes){
											s += ','+node.attributes.p1+','+node.attributes.p2;
										}
	
										if(leaf_node != 'Y'){
											update(id, 0x001, 'tb', rank);
										}
										update(id, 0x001, 'tb', rank);
									}
								},
								lines: true,
								formatter:function(node){
									$('#loading').hide();
									var id = node.id;	
									var s = '';
									var rank = node.rank;
									var name = node.text;
									var rName = replaceAll(name,'_',' ');
									var end_yn = node.end_yn;
									var link = 'javascript:taxonomyDataCall();';																	
									if(end_yn == 'Y'){
										if (node.children && node.children.length!=0){	
											s = '<a href=' + link + '><div id='+node.id+' class=taxonomy_name name='+name+'>' + rName + ' <img src=./resources/images/taxonomy/zoom.png /><span>('+ node.children.length +')</span></div></a>';	
										}
										else{
											s = '<a href=' + link + '><div id='+node.id+' class=taxonomy_name name='+name+'>' + rName + ' <img src=./resources/images/taxonomy/zoom.png /></div></a>';
										}
																			
									}else{								
										s = rName;
									}
								
									return s;
								}
								">
						</ul>
					</div>
	
				<!-- Taxon Tree E -->
				
				<div class="category05">
					<dl>
						<dt>Information</dt>
						<dd>&bull; Assembly ID: <span id="refSeqAssemblyID"></span></dd>
						<dd>&bull; Assembly Name: <span id="assemblyName"></span></dd>
						<dd>&bull; Assembly Level: <span id="assemblyLevel"></span></dd>
						<dd>&bull; Assembly Types: <span id="assemblyType"></span></dd>
						<dd>&bull; Taxonomy ID: <span id="taxId"></span></dd>
						<dd>&bull; Genome size: <span id="fileSize"></span></dd>
						<dd>&bull; GeneBank ID: <span id="geneBankID"></span></dd>
						<dd>&bull; Release date: <span id="releaseDate"></span></dd>
											
					</dl>	
					<!-- <input type ="button" value="Genome-Browser" id="btn_taxnomy"class="btn-off" /> -->
					<input type ="button" value="Ref-Seq" id="ref_seq"class="btn-off" />		
					<input type ="button" value="Ensembl" id="ensembl"class="btn-off" />		
					<input type ="button" value="Raw Data" id="rawdata"class="btn-off" />		
					<input type="hidden" id="species_name" name="species_name" />	
					
				</div>
				<div id="hiddenData"></div>	
			</div>
			
		</div>
	<!-- contents E --> 
	
<c:choose>
	<c:when test="${param.keyword ne null}">
		<script>
		
			var pep;
			var cds;
			var genome;
		
			//검색에 의한 호출
			$(function() {
				$.ajax({
					url : 'getTaxnomyData.do',
					type : 'POST',
					async : true,
					dataType : "Json",
					data : { "taxon_id" : "${param.taxon_id}", 
							"species_name" : "${param.keyword}"
					},
					success : function(getData) {
						
		 				$("#refSeqAssemblyID").html(getData.data[0].refSeqAssemblyID);
		 				$("#assemblyName").html(getData.data[0].assemblyName);
		 				$("#assemblyLevel").html(getData.data[0].assemblyLevel);
		 				$("#assemblyType").html(getData.data[0].assemblyType);
		 				$("#taxId").html(getData.data[0].taxId);
		 				$("#fileSize").html(getData.data[0].fileSize);
		 				$("#geneBankID").html(getData.data[0].genBankAssemblyID);
						$("#releaseDate").html(getData.data[0].releaseDate);
						$("#hiddenData").html("<input type='hidden' id='organism' value="+getData.data[0].organism+" />");
						$("#species_name").html(getData.data[0].organism);
						$("#btn_taxnomy").attr("class","btn-on");
						
						for(var i=0; i<getData.existDB.length; i++ ){
							$("#"+getData.existDB[i].db).attr("class","btn-on");
						}
						
						pep = getData.data[0].pepFile;
						cds = getData.data[0].cdsFile;
						genome = getData.data[0].genomeFile;
					}
				});					
			});
		</script> 
	</c:when>
</c:choose>

<script>

var pep;
var cds;
var genome;

//트리 click 이벤트에 대한 호출
function taxonomyDataCall (tax_id){
	$.ajax({
		url : 'getTaxnomyData.do',
		type : 'POST',
		async : true,
		dataType : "Json",
		data : { "taxon_id" : $('.tree-node-selected .taxonomy_name').attr('id'),
				"species_name" : "${param.keyword}"
		},
		success : function(getData) {
			
			$("#refSeqAssemblyID").html(getData.data[0].refSeqAssemblyID);
			$("#assemblyName").html(getData.data[0].assemblyName);
			$("#assemblyLevel").html(getData.data[0].assemblyLevel);
			$("#assemblyType").html(getData.data[0].assemblyType);
			$("#taxId").html(getData.data[0].taxId);
			$("#fileSize").html(getData.data[0].fileSize);
			$("#geneBankID").html(getData.data[0].genBankAssemblyID);
			$("#releaseDate").html(getData.data[0].releaseDate);
			$("#hiddenData").html("<input type='hidden' id='organism' value="+getData.data[0].organism+" />");
			$("#species_name").html(getData.data[0].organism);
			$("#btn_taxnomy").attr("class","btn-on");
			
			for(var i=0; i<getData.existDB.length; i++ ){
				$("#"+getData.existDB[i].db).attr("class","btn-on");
			}
			
			pep = getData.data[0].pepFile;
			cds = getData.data[0].cdsFile;
			genome = getData.data[0].genomeFile;
		}
	});		
} 
		
$( "#btn_taxnomy" ).click(function() {
 	var taxon_id = $("#taxId").text();
 	var species_name = $("#species_name").text();
 	var assebmly_id = $("#refSeqAssemblyID").text();
	//species_name.replace(' ', '|');
 	location.href="<c:url value='circos'/>?taxon_id="+taxon_id+"&species_name="+species_name+"&pep="+pep+"&cds="+cds+"&genome="+genome+"&assembly_id="+assebmly_id;
});

$( "#ref_seq" ).click(function() {
	if($("#ref_seq").attr("class") == "btn-off"){
		alert("No Data");
	}else{
	 	var taxon_id = $("#taxId").text();
	 	var species_name = $("#species_name").text();
	 	
		//species_name.replace(' ', '|');
	 	location.href="<c:url value='circos'/>?taxon_id="+taxon_id+"&species_name="+species_name+"&pep="+pep+"&cds="+cds+"&genome="+genome+"&db=ref_seq";
	}
});

$( "#ensembl" ).click(function() {
	if($("#ensembl").attr("class") == "btn-off"){
		alert("No Data");
	}else{
	 	var taxon_id = $("#taxId").text();
	 	var species_name = $("#species_name").text();
	 	
		//species_name.replace(' ', '|');
	 	location.href="<c:url value='circos'/>?taxon_id="+taxon_id+"&species_name="+species_name+"&pep="+pep+"&cds="+cds+"&genome="+genome+"&db=ensembl";
	}
});

$( "#rawdata" ).click(function() {
	if($("#rawdata").attr("class") == "btn-off"){
		alert("No Data");
	}else{
	 	var taxon_id = $("#taxId").text();
	 	var species_name = $("#species_name").text();
	 	
		//species_name.replace(' ', '|');
	 	location.href="<c:url value='circos'/>?taxon_id="+taxon_id+"&species_name="+species_name+"&pep="+pep+"&cds="+cds+"&genome="+genome+"&db=plant";
	}
});
</script> 

<jsp:include page="../common/footer.jsp" flush="false"/>