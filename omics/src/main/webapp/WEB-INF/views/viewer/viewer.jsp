<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- viewer -->
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/viewer.css" />

<script type="text/javascript" src="./resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery.qtip-1.0.0-rc3.min.js"></script>

<script type="text/javascript" src="./resources/js/kobic_util.js"></script>

<script type="text/javascript" src="./resources/js/TmHmm.js"></script>
<script type="text/javascript" src="./resources/js/gene_structure.js"></script>
<script type="text/javascript" src="./resources/js/domain_viewer.js"></script>
<script type="text/javascript" src="./resources/js/viewer_controller.js"></script>
<script type="text/javascript" src="./resources/js/multiloc.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>Gene Viewer</h3>
	
	<input id="tax_id" type="hidden" value="${tax_id}"/>
	<input id="refseq_assembly_id" type="hidden" value="${refseq_assembly_id}"/>
	<input id="protein_accession_id" type="hidden" value="${protein_accession_id}"/>
   
   	 <div id="gene-information">
		<div id='title-frame'>
			<div class="gene-structure-title title" style="background:#0b534a; margin-top:0;">Gene Information</div>
		</div>
		<div id="gene-information-frame">
			<table id="gene-info-table" class="gene-information-table_size">
				<tr class="gene-information-column gene-information-table">
					<td>LocusTag</td>
					<td>Species Name</td>
					<td>Annotation version</td>
				</tr>
				<tr class="gene-information-table">
					<td id='locustag-value'></td>
					<td id='species-value'></td>
					<td id='annot-value'></td>
				</tr>
			</table>
			<table  class="gene-information-table_size" style="margin-top: 15px;">
				<tr class="gene-information-column gene-information-table">
					<th colspan="2">Taxonomy info</th>
				</tr>
				<tr class="gene-information-table">
					<td class="gene-information-column" style="border-right: 1px solid #cccccc;">Lineage</td>
					<td style="width:90%; text-align: left; padding: 10px;">
						<c:forEach var="result" items="${tax_info}" varStatus="status">
							<a href="<c:url value="http://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id=${result.tax_id}"/>" target="_blank">${result.name_txt}</a>;
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div> 
	<div id="gene-structure">
		<div id='title-frame'> 
			<div class="gene-structure-title title" style="background:#006184;">Gene Structure</div>
			<div class="gene-structure-controller controller">
				<div class="label">Locus</div>
				<div class="display">
					<input class='locus' type='text' id='locus' readonly>
					<button id="PeptideDownloadButton" class="btnblue">Peptide Download</button>
					<button id="CDSDownloadButton" class="btngreen">CDS Download</button>
				</div>
					
<!--  				<span class='left ctrl-button panning rounded-corners'></span>
				<span class='zoom-in ctrl-button zooming rounded-corners'>+</span>
				<span class='zoom-out ctrl-button zooming rounded-corners'>-</span>
				<span class='right ctrl-button panning rounded-corners'>></span> -->
			</div>
 			</div> 
		<div class="gene-structure-view" style="background:#e5eff3"></div>
	</div>  

  	<div id="viewerPanel" style="clear:both;">
  		<div class="domain-title title" style="background:#008570;">Domain Architecture</div>
  	</div>
 
	<div id="viewerPanelLegend" style="position:relative;background:none;width:100%;">
		<fieldset style="width:958px;clear:both;display:block;float:left;border:1px solid #ddd; padding:10px; margin-bottom:30px">
			<legend>Legend</legend>
			<div style="width:100%;height:10px;background:none;">
				<div class="rounded-corners" style="width:50px;height:10px;background:yellow;border:1px solid lightgray;text-align:center;font-weight:bold;color:white;float:left;"></div>
				<div style="width:200px;height:10px;line-height:10px;text-indent:10px;background:none;">IPR term</div>
			</div>
			<div style="width:100%;height:5px;"></div>
			<div style="width:100%;height:10px;background:none;">
 				<div style="width:50px;height:10px;background:orange;border:1px solid lightgray;text-align:center;font-weight:bold;color:white;float:left;"></div>
 				<div style="width:200px;height:10px;line-height:10px;text-indent:10px;background:none;">Non-IPR term</div>
 			</div>
		</fieldset>
	</div>


	<div id="subcellular_localization" class="subcellular">
 	<div id="TMHMM-frame">
  		<div class="multiloc-title title" style="background:#2c4b65; margin-top:0;">Subcellular localization</div>
  		<div class="sublocal_subtitle" style="padding-top:10px;"><span>TMHMM for prediction of transmembrane helices in proteins</span></div>
		<canvas id="canvas" width="920" height="300" style="background:##e9edef; padding:20px 30px;">This web-browser does not support the HTML5 canvas</canvas>
		<canvas id="realCanvas" width="920" height="20"style="background:##e9edef; padding:0 30px 20px 30px ">This web-browser does not support the HTML5 canvas</canvas>

		<img id="cellbg1" src="./resources/images/TMHMM/cellbg1.png" alt="" style="display:none;">
		<img id="cellbg2" src="./resources/images/TMHMM/cellbg2.png" alt="" style="display:none;" width="100" height="70">
		<img id="cellbg3" src="./resources/images/TMHMM/cellbg3.png" alt="" style="display:none;">
		<img id="cellbg4" src="./resources/images/TMHMM/cellbg4.png" alt="" style="display:none;">

		<img id="cell_inside_only" src="./resources/images/TMHMM/cell_inside_only.png" alt="" style="display:none;">
		<img id="cell_outside_only" src="./resources/images/TMHMM/cell_outside_only.png" alt="" style="display:none;">
		
		<img id="sub_TMhelix" src="./resources/images/TMHMM/sub_TMhelix.png" alt="" style="display:none;">
		<img id="sub_inside" src="./resources/images/TMHMM/sub_inside.png" alt="" style="display:none;">
		<img id="sub_outside" src="./resources/images/TMHMM/sub_outside.png" alt="" style="display:none;">
  	</div> 

  	<div id="multiloc-frame">
		<div class="sublocal_subtitle"><span>Multiloc for Protein localization</span></div>
	     	<c:choose>
 			<c:when test="${not empty multiloc_result}">
			<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" src="./resources/images/cell/cell_12.png" style="display:block;"/>The image is modified from Kelrinsong's work.</div></div>
			<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_01" src="./resources/images/cell/cell_01.png" style="opacity: ${multiloc_result.cytoplasmic} "></div></div> 
			<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_02" src="./resources/images/cell/cell_02.png" style="opacity: ${multiloc_result.mitochondrial} "></div></div> 
		    <div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_03" src="./resources/images/cell/cell_03.png" style="opacity: ${multiloc_result.peroxisomal} "></div></div>
		    <div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_04" src="./resources/images/cell/cell_04.png" style="opacity: ${multiloc_result.nuclear} "></div></div>
		  	<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_05" src="./resources/images/cell/cell_05.png" style="opacity: ${multiloc_result.er} "></div></div> 
		  	<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_06" src="./resources/images/cell/cell_06.png" style="opacity: ${multiloc_result.plasma_membrane} "></div></div>
		    <div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_07" src="./resources/images/cell/cell_07.png" style="opacity: ${multiloc_result.golgi_apparatus} "></div></div>
		   	<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_08" src="./resources/images/cell/cell_08.png" style="opacity: ${multiloc_result.lysosomal} "></div></div>
		    <div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_09" src="./resources/images/cell/cell_09.png" style="opacity: ${multiloc_result.vacuolar} "></div></div>
		  	<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_10" src="./resources/images/cell/cell_10.png" style="opacity: ${multiloc_result.chloroplast} "></div></div> 
		 	<div class="multiloc-div-absolute"><div class="multiloc-div-relative"><img class="multiloc-image" id="cell_11" src="./resources/images/cell/cell_11.png" style="opacity: ${multiloc_result.extracellular} "></div></div> 
	 		    
 		    <div class="multiloc-div-absolute">
				<div class="multiloc-div-relative"><img class="multiloc-image" usemap="#imgmap" src="./resources/images/cell/cell_image.png" style="opacity:1">
					<map id="imgmap" name="imgmap">
						<area title='cytoplasmic' onmouseover="mover('cell_01');" onmouseout="mout('cell_01',$('#td_cell_01').text());" shape="poly" alt="" title="" coords="22,99,16,101,13,105,9,109,6,117,6,127,8,137,14,149,32,171,37,181,36,186,33,187,32,188,31,191,36,197,48,210,56,216,58,204,61,196,66,187,72,180,82,172,92,165,102,160,112,157,127,155,113,135,114,139,111,143,108,146,101,151,93,154,88,155,81,156,77,153,75,149,73,155,68,159,62,163,53,166,46,167,43,167,39,166,38,163,38,161,38,156,41,152,46,148,51,145,59,142,56,140,55,137,55,133,57,129,62,123,68,120,76,118,84,117,90,120,93,123,91,128,86,134,95,131,101,131,108,132,113,135,93,104,91,108,86,111,82,112,66,113,51,113,36,113,33,113,30,110,28,107,27,103,25,100,22,99,68,112,73,118,77,117,83,117,89,119,93,122,92,127,94,132,100,130,108,132,113,135,113,139,110,144,105,148,110,158,119,156,129,155,138,156,148,159,160,165,172,176,181,188,190,201,203,221,214,242,224,265,226,270,237,269,236,265,234,260,232,255,229,251,225,245,222,239,219,234,215,227,215,225,211,220,209,217,207,213,206,207,205,201,205,191,204,184,207,182,212,182,217,182,222,182,226,185,228,191,229,200,229,207,231,201,230,193,229,186,228,175,228,163,232,161,239,160,244,162,246,165,245,173,246,183,247,193,247,180,248,178,254,177,256,176,255,169,255,166,256,165,260,163,265,163,265,154,266,147,266,142,268,140,272,141,276,147,279,152,278,157,279,170,281,175,283,179,288,179,289,171,289,165,291,163,297,165,304,171,306,175,306,177,308,181,310,185,309,190,309,193,312,195,316,192,320,187,323,181,319,177,315,173,312,169,311,158,312,151,309,147,305,144,303,143,301,140,299,136,298,132,297,129,297,126,297,124,295,121,294,112,295,107,294,103,295,100,295,96,297,93,301,87,306,80,309,76,311,74,316,71,322,67,324,67,326,70,325,75,325,79,329,74,332,70,333,68,337,66,343,63,350,60,355,57,362,55,366,56,367,65,367,65,377,60,387,55,392,52,400,51,412,51,428,50,431,49,434,50,434,64,443,63,444,80,453,78,458,76,459,63,459,54,461,49,465,40,469,33,474,27,457,23,445,19,398,29,398,33,394,40,388,46,383,50,377,51,370,51,364,49,358,44,354,40,351,34,351,31,325,27,320,38,314,46,309,50,300,54,291,55,282,54,275,51,268,45,264,40,261,34,259,27,227,69,227,79,225,86,221,91,216,94,211,95,200,94,192,93,187,95,179,100,170,107,164,111,156,115,147,116,137,115,131,112,127,108,122,104,120,98,119,92,118,91,118,85,119,82,120,80,90,101,92,104,91,106,88,109,85,111,74,112,67,112,62,92,77,94,87,99,92,105,86,111,107,129,132,112,123,105,119,98,117,89,118,81,122,77,131,72,143,66,153,62,167,58,180,56,189,55,200,55,211,56,219,59,225,64,227,69,227,78,264,38,260,32,259,28,259,26,261,24,267,21,273,20,283,19,292,19,302,19,310,20,318,22,324,25,325,27,323,33,353,37,351,33,351,30,354,28,362,26,378,25,389,26,396,28,399,31,397,36,394,40,469,34,472,29,474,27,465,25,454,22,445,20,442,16,433,13,421,11,399,11,359,8,342,8,324,8,304,7,278,6,257,6,243,8,234,9,230,8,220,13,210,17,204,22,200,25,201,26,206,26,214,26,217,25,218,27,216,30,208,34,197,40,185,40,173,39,166,38,159,38,159,39,162,41,167,43,171,46,170,48,168,51,163,53,160,52,155,51,130,55,111,58,93,60,79,63,66,67,54,71,44,76,36,82,31,85,29,89,31,91,34,93,38,92,48,91,63,92,580,55,588,66,593,76,594,86,595,94,595,103,593,114,591,123,591,126,603,122,603,113,603,101,603,96,604,94,607,93,612,93,615,95,616,97,618,100,621,106,621,111,621,116,621,128,624,125,625,122,627,118,627,112,627,108,628,106,631,106,633,107,634,109,634,112,634,145,632,148,627,154,625,156,623,160,619,174,611,197,605,213,605,224,618,224,628,222,637,218,640,215,642,211,640,207,638,203,637,202,636,204,634,206,632,205,631,202,632,198,632,194,634,189,636,185,641,181,648,177,655,173,663,169,672,164,679,159,684,156,692,149,699,142,707,133,709,127,714,120,713,116,711,109,708,102,703,97,698,92,692,87,686,83,674,76,664,73,648,68,638,65,628,65,611,61,590,57,581,55"/>
						<area title='Mitochondrial' onmouseover="mover('cell_02');" onmouseout="mout('cell_02',$('#td_cell_02').text());" shape="poly" alt="" title="" coords="119,94,121,101,123,104,127,108,131,111,137,114,143,116,150,116,156,115,163,112,170,108,175,103,181,99,185,96,189,94,193,93,199,93,206,95,212,95,218,94,221,91,224,87,226,83,227,80,227,73,227,68,226,65,222,62,216,57,210,55,203,54,192,55,182,56,169,58,157,62,146,65,135,70,128,73,122,77,119,81,118,88,119,96" />
						<area title='Peroxisomal'  onmouseover="mover('cell_03');" onmouseout="mout('cell_03',$('#td_cell_03').text());" shape="poly" alt="" title="" coords="262,35,270,45,279,51,292,55,305,52,314,46,320,36,324,27,323,22,313,20,287,18,272,19,263,22,258,25,258,27,263,36" />
						<area title='Nuclear'  onmouseover="mover('cell_04');" onmouseout="mout('cell_04',$('#td_cell_04').text());" shape="poly" alt="" title="" coords="473,29,465,37,461,47,460,71,459,78,440,81,421,85,405,91,399,97,398,105,409,113,431,119,460,125,517,130,549,130,578,127,589,124,594,111,595,90,590,69,575,47,568,36,550,21,518,11,497,13,479,21,472,29" /> 
					 	<area title='Er'  onmouseover="mover('cell_05');" onmouseout="mout('cell_05',$('#td_cell_05').text());" shape="poly" alt="" title="" coords="205,186,205,205,209,217,225,244,233,257,238,277,246,283,255,283,265,293,279,300,293,296,304,282,313,280,317,272,327,274,350,256,359,250,371,251,376,249,381,253,387,259,408,259,421,260,429,257,425,252,416,236,414,225,414,217,429,209,451,198,478,195,508,196,535,206,551,219,548,226,559,244,563,272,577,275,581,268,586,263,592,261,595,253,597,250,601,247,599,239,597,233,600,230,604,225,605,213,607,204,619,166,623,157,632,147,634,134,633,116,634,107,631,105,625,106,627,115,622,127,621,129,621,104,614,93,608,92,602,96,604,105,605,122,585,128,568,131,560,133,538,136,529,135,513,137,493,136,487,133,472,133,463,133,444,130,422,125,409,119,397,111,396,107,397,95,403,90,414,82,427,82,443,79,444,63,435,62,432,63,433,50,424,52,397,50,383,54,367,67,365,55,358,56,345,61,336,68,328,76,321,76,323,69,317,70,304,80,295,94,293,111,295,125,301,141,309,147,313,156,311,167,317,176,325,181,313,194,306,195,306,183,303,172,292,163,287,167,287,179,278,176,276,162,276,150,272,142,267,140,263,163,254,163,256,175,246,178,243,163,239,160,229,160,229,168,227,186,222,181,214,179,206,183,206,189"/>
						<area title='Plasma_membrane'  onmouseover="mover('cell_06');" onmouseout="mout('cell_06',$('#td_cell_06').text());" shape="poly" alt="" title="" coords="4,136,13,182,45,268,99,319,196,378,245,396,316,403,429,402,510,379,598,319,640,267,682,179,702,136,657,176,646,180,634,195,639,202,645,214,628,223,598,222,597,254,581,276,564,276,552,304,536,306,526,314,488,310,464,314,440,288,386,279,349,260,325,277,284,301,247,289,237,273,229,266,234,308,217,342,191,347,167,347,137,337,84,292,46,225,50,213,31,194,26,184,36,181,29,170,-4,128"/>
						<area title='Golgi_apparatus'  onmouseover="mover('cell_07');" onmouseout="mout('cell_07',$('#td_cell_07').text());" shape="poly" alt="" title="" coords="416,226,419,242,430,254,430,258,422,263,415,267,416,271,424,275,427,280,438,280,447,285,449,295,454,302,462,306,467,310,473,312,485,310,494,307,499,309,516,312,527,310,532,305,541,303,549,301,557,283,561,263,559,245,555,236,544,226,544,217,533,207,509,198,480,197,459,197,443,201,434,206,435,214,426,217,418,220,416,227" />
						<area title='Lysosomal' onmouseover="mover('cell_08');" onmouseout="mout('cell_08',$('#td_cell_08').text());" shape="poly" alt="" title="" coords="357,42,360,45,366,48,372,50,378,49,384,47,390,43,394,38,396,34,396,29,393,27,384,25,371,24,362,26,354,27,351,30,351,33,354,38,357,42,361,45"/>				
						<area title='Vacuolar'  onmouseover="mover('cell_09');" onmouseout="mout('cell_09',$('#td_cell_09').text());" shape="poly" alt="" title="" coords="75,177,65,188,56,205,54,223,60,246,67,262,100,304,130,329,157,345,184,349,205,343,219,335,227,320,232,304,229,280,216,249,187,197,164,169,149,159,129,154,106,157,85,168,74,177" />
						<area title='Chloroplast'  onmouseover="mover('cell_10');" onmouseout="mout('cell_10',$('#td_cell_10').text());" shape="poly" alt="" title="" coords="68,120,59,123,55,131,56,142,43,148,38,155,38,163,44,166,61,162,73,154,75,149,77,154,88,154,101,149,111,139,113,134,106,131,94,130,81,134,91,126,90,120,84,117,74,117,66,120" />
						<area title='Extracellular'  onmouseover="mover('cell_11');" onmouseout="mout('cell_11',$('#td_cell_11').text());" shape="poly" alt="" title="" coords="0,117,1,408,714,409,715,121,708,135,701,147,689,171,679,190,664,220,652,246,640,269,629,288,617,303,604,317,583,335,563,350,537,366,512,379,483,391,453,401,427,405,401,407,329,407,284,403,253,398,220,389,181,370,142,347,111,326,73,297,50,273,32,240,17,196,6,140,5,117,-3,116,6,143,5,128,6,115,12,105,18,101,23,99,26,101,28,109,29,90,32,85,41,77,59,69,78,63,100,58,126,54,149,52,157,50,159,38,165,37,187,39,197,38,200,25,204,20,214,15,229,8,245,7,270,5,281,5,295,6,329,7,361,7,387,9,417,11,429,11,442,15,451,20,475,26,480,21,487,17,499,14,515,12,534,15,547,20,567,34,575,45,580,54,603,59,624,63,641,65,656,68,680,78,697,89,708,101,713,113,715,120,709,133,715,132,715,1,6,1,1,81,1,143,7,142"/>					
					</map>
				</div>
		    </div>  
		       
		    </c:when>
			    <c:otherwise>
			    <div class="multiloc-div-absolute">
					<div class="multiloc-div-relative"><img class="multiloc-image" src="./resources/images/cell/cell_no_result.png" style="display:block;"/>The image is modified from Kelrinsong's work.</div>
			    </div>
			    </c:otherwise>
		    </c:choose>
		 
		<div class="multiloc-div-relative" style="top: 50px;  left: 740px;">
			<table class="multiloc_table">
				<tr>
					<th id="th_cell_01">Cytoplasmic</th>
					<td id="td_cell_01">${multiloc_result.cytoplasmic}</td>
				</tr>
				<tr>
					<th id="th_cell_02">Mitochondrial</th>
					<td id="td_cell_02">${multiloc_result.mitochondrial}</td>
				</tr>
				<tr>
					<th id="th_cell_03">Peroxisomal</th>
					<td id="td_cell_03">${multiloc_result.peroxisomal}</td>
				</tr>
				<tr>
					<th id="th_cell_04">Nuclear</th>
					<td id="td_cell_04">${multiloc_result.nuclear}</td>
				</tr>
				<tr>
					<th id="th_cell_05">Er</th>
					<td id="td_cell_05">${multiloc_result.er}</td>
				</tr>
				<tr>
					<th id="th_cell_06">Plasma_membrane</th>
					<td id="td_cell_06">${multiloc_result.plasma_membrane}</td>
				</tr>	
				<tr>
					<th id="th_cell_07">Golgi_apparatus</th>
					<td id="td_cell_07">${multiloc_result.golgi_apparatus}</td>
				</tr>
				<tr>
					<th id="th_cell_08">Lysosomal</th>
					<td id="td_cell_08">${multiloc_result.lysosomal}</td>
				</tr>
				<tr>
					<th id="th_cell_09">Vacuolar</th>
					<td id="td_cell_09">${multiloc_result.vacuolar}</td>
				</tr>
				<tr>
					<th id="th_cell_10">Chloroplast</th>
					<td id="td_cell_10">${multiloc_result.chloroplast}</td>
				</tr>
				<tr>
					<th id="th_cell_11">Extracellular</th>
					<td id="td_cell_11">${multiloc_result.extracellular}</td>
				</tr> 
			</table>
		</div>
	</div>
	
	<div id="TargetP-frame">
		<div class="sublocal_subtitle" style="padding-top:10px;"><span>TargetP for prediction of the subcellular location of eukaryotic proteins</span></div>
			<table class="targetp-table_size">
				<tr class="targetp-column targetp-table">
					<td>Len</td>
					<td>cTP</td>
					<td>mTP</td>
					<td>SP</td>
					<td>other</td>
					<td>Loc</td>
					<td>RC</td>
					<td>TPlen</td>
				</tr>
				<tr class="targetp-table">
				<c:choose>
	 			<c:when test="${not empty targetp_result}">
					<td>${targetp_result.len}</td>
					<td>${targetp_result.ctp}</td>
					<td>${targetp_result.mtp}</td>
					<td>${targetp_result.sp}</td>
					<td>${targetp_result.other}</td>
					<td>${targetp_result.loc}</td>
					<td>${targetp_result.rc}</td>
					<td>${targetp_result.tplen}</td>
					</c:when> 
					<c:otherwise>
						<th colspan="8" style="font-weight:lighter">No result</th> 
					</c:otherwise>
	 			</c:choose>
				</tr>
			</table>
			<div class="targetp-description">
				<b>Len</b> : Sequence length<br>
				<b>cTP</b> : a chloroplast transit peptide<br>
				<b>mTP</b> : a mitochondrial targeting peptide<br>
				<b>SP</b> : a signal peptide<br>
				<b>other</b> : other<br>
				<b>Loc</b> : Prediction of localization, based on the scores above. The possible values are "C" (Chloroplast), "M" (Mitochondrion), "S" (Secretory pathway) and "_" (Any <br> &nbsp; &nbsp; &nbsp; &nbsp; other location)<br>
				<b>RC</b> : Reliability class, from 1 to 5, where 1 indicates the strongest prediction. RC is a measure of the size of the difference ('diff') between the highest (winning) and <br> &nbsp; &nbsp; &nbsp; &nbsp; the second highest output scores. There are 5 reliability classes, defined as follows: <br>
					&nbsp; &nbsp; &nbsp; &nbsp; 1 : diff > 0.800<br>
					&nbsp; &nbsp; &nbsp; &nbsp; 2 : 0.800 > diff > 0.600<br>
					&nbsp; &nbsp; &nbsp; &nbsp; 3 : 0.600 > diff > 0.400<br>
					&nbsp; &nbsp; &nbsp; &nbsp; 4 : 0.400 > diff > 0.200<br>
					&nbsp; &nbsp; &nbsp; &nbsp; 5 : 0.200 > diff<br>
				<b>TPlen</b> : Predicted presequence length from cleavage site predictions (using SignalP and ChloroP)<br>
			</div>
		</div>
	</div>
	
	<div id="Ortho-Pralog-frame" style="background:#f5f1fa; margin-top:30px; paddimg-bottom:20px;">
		<div class="ortho-paralog-title title" style="margin-top:0; background:#433f79">Ortholog or Paralog information </div>
		 <div class="ortho-paralog-subtitle subtitle"><span>Result of OrthoMCL</span></div>
		<table class="orthoMCL">
			<tr>
				<th>No</th>
				<th>Query Organism</th>
				<th>Query<br/> Assembly ID</th>
				<th>Query ID</th>
				<th>Subject Organism</th>
				<th>Subject<br/> Assembly ID</th>
				<th>Subject ID</th>
				<th>Description</th>
			</tr>
			<c:forEach var="entry" items="${ortho_mcl}" varStatus="status">
				<tr>
					<td class="ortho-paralog-column">${entry.no}</td>
					<td class="ortho-paralog-column">${entry.src_organism}</td>
					<td class="ortho-paralog-column">${entry.src_assembly_id}</td>
					<td class="ortho-paralog-column">${entry.src_protein_id}</td>
					<td class="ortho-paralog-column">${entry.tar_organism}</td>
					<td class="ortho-paralog-column">${entry.tar_assembly_id}</td>
					<td class="ortho-paralog-column">${entry.tar_protein_id}</td>
					<td class="ortho-paralog-column">${entry.desc}</td>
				</tr>
			</c:forEach>
		</table>
<%-- 		<div class="ortho-paralog-subtitle subtitle"><span>Ortholog</span></div>
		<table>
			<tr>
				<th>Ortholog proteins</th>
				<th>Genes</th>
			</tr>
			<c:forEach var="entry" items="${orthologs}" varStatus="status">
			<tr>      
				<td class="ortho-paralog-column">${entry.key}</td>
				<td class="ortho-paralog-column">
				<c:choose>
					<c:when test="${not empty orthologs[entry.key]}">
						<table>
							<tr style="background-color:#eee;">
								<th>LocusVersion</th>
								<th>AssemblyName</th>
								<th>Source</th>
								<th>Start</th>
								<th>End</th>
								<th>Strand</th>
								<th>GeneName</th>
							</tr>
							<c:forEach var="ortholog" items="${orthologs[entry.key]}" varStatus="status">
							<tr>
								<td><c:out value="${ortholog.getLocusVersion()}"/></td>
								<td><c:out value="${ortholog.getAssemblyName()}"/></td>
								<td><c:out value="${ortholog.getSource()}"/></td>
								<td><c:out value="${ortholog.getStart()}"/></td>
								<td><c:out value="${ortholog.getEnd()}"/></td>
								<td><c:out value="${ortholog.getStrand()}"/></td>
								<td><c:out value="${ortholog.getGeneName()}"/></td>
							</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<c:out value="None"/>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>
		
 		<div class="ortho-paralog-subtitle subtitle" style="padding-top:10px;"><span>Paralog</span></div>
		<table>
			<tr>
				<th>Paralog proteins</th>
				<th>Genes</th>
			</tr>
			<c:forEach var="entry" items="${paralogs}" varStatus="status">
			<tr>      
				<td class="ortho-paralog-column">${entry.key}</td>
				<td class="ortho-paralog-column">
				<c:choose>
					<c:when test="${not empty paralogs[entry.key]}">
						<table>
							<tr>
								<th>LocusVersion</th>
								<th>AssemblyName</th>
								<th>Source</th>
								<th>Start</th>
								<th>End</th>
								<th>Strand</th>
								<th>GeneName</th>
							</tr>
							<c:forEach var="paralog" items="${paralogs[entry.key]}" varStatus="status">
							<tr>
								<td><c:out value="${paralog.getLocusVersion()}"/></td>
								<td><c:out value="${paralog.getAssemblyName()}"/></td>
								<td><c:out value="${paralog.getSource()}"/></td>
								<td><c:out value="${paralog.getStart()}"/></td>
								<td><c:out value="${paralog.getEnd()}"/></td>
								<td><c:out value="${paralog.getStrand()}"/></td>
								<td><c:out value="${paralog.getGeneName()}"/></td>
							</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<c:out value="None"/>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table> --%>
	</div>
</div>
<!-- contents E -->


<div id="gene_structure_loading" style="float:left;background:none;position:absolute;width:32px;height:32px;"><img src="./resources/images/geneSearch/ajax_loding.gif" /></div>
<div id="protein_domains_loading" style="float:left;background:none;position:absolute;width:32px;height:32px;"><img src="./resources/images/geneSearch/ajax_loding.gif" /></div>
<div id="tmhmm_loading" style="float:left;background:none;position:absolute;width:32px;height:32px;"><img src="./resources/images/geneSearch/ajax_loding.gif" /></div>
	
<jsp:include page="../common/footer.jsp" flush="false"/>