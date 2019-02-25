<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<%@page import="org.kobic.omics.common.utils.Utils"%>

<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg" />
</jsp:include>
<script type="text/javascript" src="./resources/js/analysis.js"></script>

<!-- <link href="./resources/css/newick/tree.css" rel="stylesheet" type="text/css" /> -->
<script src="./resources/js/newick/jquery.min.js"></script>

<script type="text/javascript" src="./resources/js/newick/jquery-svgpan.js"></script>
<script src="./resources/js/newick/treelib.js"></script>	

<script src="resources/ui/ui.core.js" type="text/javascript"></script>
<script src="resources/ui/ui.draggable.js" type="text/javascript"></script>
<script src="resources/ui/ui.resizable.js" type="text/javascript"></script>
<script src="resources/ui/ui.dialog.js" type="text/javascript"></script>
<script src="resources/ui/effects.core.js" type="text/javascript"></script>
<script src="resources/ui/effects.highlight.js" type="text/javascript"></script>
<script type="text/javascript" src="./resources/js/hdfs_dialog_analysis.js"></script>
<script type="text/javascript" src="./resources/js/mypage.js"></script>

<link rel="stylesheet" href="resources/themes/base/ui.all.css">


<!-- subV S -->
<div class="subV">
	<img src="./resources/images/sub/sub1_top.png" alt="" />
</div>




<!-- 다이얼로그 창 -->
<div id="dialog" title="KOBIC Cloud Data Explorer">
	<form>
		<fieldset>
			<div id="validateTips" class="poppath"><p id="select_data"></p></div>
			<div class="floatR">				
				<input type="button" value="Parent path" class="btnsky btnprev" onclick="parent_path_request();"/>
				<input type="button" value="Download" class="btnsky btndown"  onclick="file_transfer();"/>
			</div>
			<input type="hidden" value="${pPath}" name="p_path">
			<table class="pop2">
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
		</fieldset>
	</form>
	
</div>



<!-- subV E -->
<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>Analysis</h3>

	<!-- contents 정렬 S-->
	<div class="subconW" id="subconW">
		
		<input type="hidden" name="input_type_id" id="input_type_id" value="${inputTypeNum}" />
		<input type="hidden" name="input_sub_type_id" id="input_sub_type_id" value="${inputSubNum}" />
		
		<ul class="tab4">
			<li><a id="group_tab1" onclick="con_analysis_group_tab(1);" class="on">Genome-scale</br> sequence alignment</a></li>
			<li><a id="group_tab2" onclick="con_analysis_group_tab(2);">Gene-scale</br> sequence alignment</a></li>
			<li><a id="group_tab3" onclick="con_analysis_group_tab(3);">Multiple</br> sequence alignment</a></li>
			<li><a id="group_tab4" onclick="con_analysis_group_tab(4);" style="padding-top:20px;">Protein domain search</a></li>
			<li><a id="group_tab5" onclick="con_analysis_group_tab(5);" style="padding-top:20px;">Phylogenetic tree</a></li>				
		</ul>		
		
		<div class="analysiscon">
	
			<ul class="tab">
				<li><a id="tab_cont1" onclick="con_tab(1);" class="on">LAST</a></li>
				<li><a id="tab_cont2" onclick="con_tab(2);" style="display: none;">BLAST</a></li>
				<li><a id="tab_cont3" onclick="con_tab(3);" style="display: none;">InterPro</a></li>
				<li><a id="tab_cont4" onclick="con_tab(4);" style="display: none;">Clustal Omega</a></li>
				<li><a id="tab_cont5" onclick="con_tab(5);" style="display: none;">MUSCLE</a></li>
				<li><a id="tab_cont6" onclick="con_tab(6);" style="display: none;">Phylogeny Viewer</a></li>
			</ul>
	
			<div id="div_tab_1" style="display: block;">
				<form id="last_form" name="last_form" method="post">
					<div id="step">
						<input type="hidden" name="pipeline_id" id="pipeline_id"
							value="${last_id}" />
						<div class="step1">
							<fieldset>
								<legend>
									<strong>STEP 1</strong> Enter your input reference sequence
								</legend>
								<div class="in">
									<label for="reference sequence"> <a href="#" style="background:#2cbfa8; width:200px">Reference FASTA form sequence</a></label>
									<a onclick="output1_1();" href="#" style="background:#00493f; width:70px;">Sample</a>	
									<label for="files1_1" class="filebox" style="background:#006185; width:70px;">Local File
										<input type="file" id="files1_1" name="file" onchange="open_file_dialog(1)">
									</label>
									<a onclick="open_dialog('/express/' + '${user_id}', 1);" href="#" style="background:#393572; width:70px;">Cloud File</a>	
									<a onclick="delete1_1();" href="#" style="background:#888; width:70px;">Clear</a>							
									<textarea id="input1" name="last|FILE|input1" cols="10" rows="7">FASTA</textarea>
								</div>
	
								<div class="in" style="padding-top: 0;">
									<label for="query sequence"><a href="#"	style="background:#2cbfa8; width:200px">Query FASTA form sequence</a></label>
									<a onclick="output1_2()" href="#" style="background:#00493f; width:70px;">Sample</a>
									<label for="files1_2" class="filebox" style="background:#006185; width:70px;">Local File
										<input type="file" id="files1_2" name="file" onchange="open_file_dialog(2)">
									</label>
									<a onclick="open_dialog('/express/' + '${user_id}', 2);" href="#" style="background:#393572; width:70px;">Cloud File</a>
									<a onclick="delete1_2()" href="#" style="background:#888; width:70px;">Clear</a>
									<textarea id="input3" name="last|FILE|input3" cols="10" rows="7">FASTA</textarea>
								</div>
							</fieldset>
						</div>
	
						<fieldset>
							<table class="depth02 mt10">
								<colgroup>
									<col width="20%" />
									<col width="30%" />
									<col width="20%" />
									<col width="30%" />
								</colgroup>
	
								<tr>
									<th>Reference sequence type</th>
									<td>
										<input type="radio" id="input2" name="last|COMBO|input2" value="DNA" checked="checked" />DNA 
										<input type="radio" id="input2" name="last|COMBO|input2" value="PROTEIN" />PROTEIN</td>
									<th>Query sequence type</th>
									<td>
										<input type="radio" id="input4" name="last|COMBO|input4" value="GENOME"  />GENOME
										<input type="radio" id="input4" name="last|COMBO|input4" value="DNA" checked="checked" />DNA
										<input type="radio" id="input4" name="last|COMBO|input4" value="RNA" />RNA
										<input type="radio" id="input4" name="last|COMBO|input4" value="PROTEIN" />PROTEIN</td>
								</tr>
								<tr>
									<th>Input format</th>
									<td><select id="-Q" name="last|COMBO|-Q">
											<option value="0" selected="selected">0</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
									</select>
									<br>0=fasta, 1=fastq-sanger, 2=fastq-solexa, 3=fastq-illumina</td>
									<th>Output type</th>
									<td><select id="-j" name="last|COMBO|-j">
											<option value="1">0</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4" selected="selected">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7" >7</option>
									</select>
									<br>0=match counts, 1=gapless, 2=redundant gapped, 3=gapped, 4=column ambiguity estimates, 5=gamma-centroid, 6=LAMA, 7=expected counts</td>
								</tr>
								<tr>
									<th>Output extension type</th>
									<td colspan="3">
										<input type="radio" id="input5" name="last|COMBO|input5" value="axt" />axt 
										<input type="radio" id="input5" name="last|COMBO|input5" value="blast" />blast 
										<input type="radio" id="input5" name="last|COMBO|input5" value="blasttab" />blasttab 
										<input type="radio" id="input5" name="last|COMBO|input5" value="html" checked="checked" />html
										<input type="radio" id="input5" name="last|COMBO|input5" value="psl" />psl 
										<input type="radio" id="input5" name="last|COMBO|input5" value="sam" />sam 
										<input type="radio" id="input5" name="last|COMBO|input5" value="tab" />tab
									</td>
								</tr>
								<tr>
									<th>Maximum expected alignments</th>
									<td><input type="text" name="last|STRING|-E" id="-E"
										value="0.05" /></td>
									<th>Alignment type</th>
									<td><input type="text" name="last|STRING|-T" id="-T"
										value="0" /></td>
								</tr>
								<tr>
									<th>Mach/Mismatch score matrix</th>
									<td><input type="text" name="last|STRING|-p" id="-p"
										value="null" /></td>
									<th>Seeding schema</th>
									<td><input type="text" name="last|STRING|-u" id="-u"
										value="NEAR" /></td>
								</tr>
								<tr>
									<th>Maximum initial matches</th>
									<td colspan="3"><input type="text" name="last|STRING|-m"
										id="-m" value="50" /></td>
								</tr>
							</table>
						</fieldset>
	
						<div class="step2">
							<fieldset>
								<legend>
									<strong>STEP 2</strong> Submit your job
								</legend>
								<div class="in">
									<label> <input id="notification_last"
										name="notification_last" type="checkbox" checked="checked"
										class="mr5" /> Be notified by email
									</label> <span class="fontI">(Tick this box if you want to be
										notified by email when the results are available)</span>
									<div id="submission_last" class="submission">
										<label for="email">EMAIL</label> <input id="email_address"
											name="email_address" type="text"
											value="${user_model.email_adress}" /><br /> <label
											for="title">TITLE</label> <input id="project_title"
											name="project_title" type="text" value="${last_title}" /><br />
										<span class="fontI">If available, the title will be
											included in the subject of the notification email and can be
											used as a way to identify your analysis</span> <input type="text"
											name="last|FOLDER|output_folder" id="output_folder" value="/"
											style="display: none;" />
									</div>
								</div>
							</fieldset>
						</div>
					</div>
	
					<div class="mt20 txtC">
						<input type="button" class="btnblue" value="Submit" id="last_form_button" name="last_form_check_button" onclick="analysis_form_check('last_form');" /> 
						<input type="button" class="btngray" value="Reset" onclick="form_reset('last_form');" />
					</div>
	
				</form>
			</div>
	
			<div id="div_tab_2" style="display: none;">
				<form id="blast_form" name="blast_form" method="post">
					<div id="step">
						<input type="hidden" name="pipeline_id" id="pipeline_id"
							value="${blast_id}" />
						<div class="step1">
							<fieldset>
								<legend>
									<strong>STEP 1</strong> Enter your input FASTA form sequence
								</legend>
								<div class="in">
									<label for="FASTA form sequence"><a href="#" class="btnSgreen">FASTA form sequence</a></label>
									
									<a onclick="output2();" href="#" style="background:#00493f; width:70px;">Sample</a>	
									<label for="files2" class="filebox" style="background:#006185; width:70px;">Local File
										<input type="file" id="files2" name="file" onchange="open_file_dialog(3)">
									</label>
									<a onclick="open_dialog('/express/' + '${user_id}', 3);" href="#" style="background:#393572; width:70px;">Cloud File</a>	
									<a onclick="delete2();" href="#" style="background:#888; width:70px;">Clear</a>
									
									<textarea id="input_blast" name="hadoop_blast|FILE|input1" cols="10" rows="7">${inputBlast}</textarea>
								</div>
							</fieldset>
						</div>
						<fieldset>
							<table class="depth02 mt10">
								<colgroup>
									<col width="20%" />
									<col width="30%" />
									<col width="20%" />
									<col width="30%" />
								</colgroup>
	
								<!-- <tr>
									<th>Program</th>
									<td colspan="3"><select id="task"
										name="hadoop_blast|COMBO|input3">
											<option value="blastp" selected="selected">blastp</option>
											<option value="blastn">blastn</option>
									</select></td>
								</tr> -->
								<!-- blastp only S -->
								<tr id="blastp_tr">
									<th>Kingdom</th>
									<td><select id="-kingdom" name="hadoop_blast|COMBO|input2">
											<option value="archaea_prot_db" selected="selected">archaea_prot_db</option>
											<option value="bacteria_prot_db">bacteria_prot_db</option>
											<option value="fungi_prot_db">fungi_prot_db</option>
											<option value="invertebrate_prot_db">invertebrate_prot_db</option>
											<option value="nr">nr</option>
											<option value="plant_prot_db">plant_prot_db</option>
											<option value="protozoa_prot_db">protozoa_prot_db</option>
											<option value="vertebrate_mammalian_prot_db">vertebrate_mammalian_prot_db</option>
											<option value="vertebrate_other_prot_db">vertebrate_other_prot_db</option>
									</select></td>
								</tr>
	
	
								<!-- blastp only E -->
	
	
								<!-- <tr id="blastp_tr" style='display:;'>
									<th>Kingdom</th>
									<td><select id="-kingdom" name="hadoop_blast|COMBO|-kingdom">
											<option value="archaea_prot_db" selected="selected">archaea_prot_db</option>
											<option value="bacteria_prot_db">bacteria_prot_db</option>
											<option value="fungi_prot_db">fungi_prot_db</option>
											<option value="invertebrate_prot_db">invertebrate_prot_db</option>
											<option value="nr">nr</option>
											<option value="plant_prot_db">plant_prot_db</option>
											<option value="protozoa_prot_db">protozoa_prot_db</option>
											<option value="vertebrate_mammalian_prot_db">vertebrate_mammalian_prot_db</option>
											<option value="vertebrate_other_prot_db">vertebrate_other_prot_db</option>
									</select></td>							
								</tr> -->
	
							</table>
						</fieldset>
	
						<div class="step2">
							<fieldset>
								<legend>
									<strong>STEP 2</strong> Submit your job
								</legend>
								<div class="in">
									<label> <input id="notification_blast"
										name="notification_blast" type="checkbox" checked="checked"
										class="mr5" /> Be notified by email
									</label> <span class="fontI">(Tick this box if you want to be
										notified by email when the results are available)</span>
									<div id="submission_blast" class="submission">
										<label for="email">EMAIL</label> <input id="email_address"
											name="email_address" type="text"
											value="${user_model.email_adress}" /><br /> <label
											for="title">TITLE</label> <input id="project_title"
											name="project_title" type="text" value="${blast_title}" /><br />
										<span class="fontI">If available, the title will be
											included in the subject of the notification email and can be
											used as a way to identify your analysis</span> <input type="text"
											name="hadoop_blast|FOLDER|output_folder" id="output_folder"
											value="/" style="display: none;" />
									</div>
								</div>
							</fieldset>
						</div>
					</div>
	
					<div class="mt20 txtC">
						<input type="button" class="btnblue" value="Submit"
							id="blast_form_button" name="blast_form_button"
							onclick="analysis_form_check('blast_form');" /> <input
							type="button" class="btngray" value="Reset"
							onclick="form_reset('blast_form');" />
					</div>
				</form>
			</div>
	
			<div id="div_tab_3" style="display: none;">
				<form method="POST" id="interpro_form" name="interpro_form">
					<div id="step">
						<input type="hidden" name="pipeline_id"
							id="pipeline_id_interpro_form" value="${interpro_id}" />
						<div class="step1">
							<fieldset>
								<legend>
									<strong>STEP 1</strong> Enter your input sequence
								</legend>
								<div class="in">
									<label for="sequence"><a href="#" class="btnSgreen">FASTA form sequence</a></label>
									
									<a onclick="output3();" href="#" style="background:#00493f; width:70px;">Sample</a>	
									<label for="files3" class="filebox" style="background:#006185; width:70px;">Local File
										<input type="file" id="files3" name="file" onchange="open_file_dialog(4)">
									</label>
									<a onclick="open_dialog('/express/' + '${user_id}', 4);" href="#" style="background:#393572; width:70px;">Cloud File</a>	
									<a onclick="delete3();" href="#" style="background:#888; width:70px;">Clear</a>
									
									<textarea cols="47" id="input_interpro" name="interproscan|FILE|input" rows="7">${inputInterpro}</textarea>
								</div>
							</fieldset>
	
						</div>
	
						<div class="step2">
							<fieldset>
								<legend>
									<strong>STEP 2</strong> Submit your job
								</legend>
								<div class="in">
									<label> <input id="notification_interpro"
										name="notification_interpro" type="checkbox" checked="checked"
										class="mr5" /> Be notified by email
									</label> <span class="fontI">(Tick this box if you want to be
										notified by email when the results are available)</span>
									<div id="submission_interpro" class="submission">
										<label for="email">EMAIL</label> <input id="email_address"
											name="email_address" type="text"
											value="${user_model.email_adress}" /><br /> <label
											for="title">TITLE</label> <input id="project_title"
											name="project_title" type="text" value="${interpro_title}" /><br />
										<span class="fontI">If available, the title will be
											included in the subject of the notification email and can be
											used as a way to identify your analysis</span> <input type="text"
											name="interproscan|FOLDER|output_folder" id="output_folder"
											value="/" style="display: none;" />
									</div>
								</div>
							</fieldset>
						</div>
					</div>
	
					<div class="mt20 txtC">
						<input type="button" class="btnblue" value="Submit"
							id="interpro_form_button" name="interpro_form_button"
							onclick="analysis_form_check('interpro_form');" /> <input
							type="button" class="btngray" value="Reset"
							onclick="form_reset('interpro_form');" />
					</div>
				</form>
			</div>
	
			<div id="div_tab_4" style="display: none;">
				<form method="POST" id="clustalomega_form" name="clustalomega_form">
					<div id="step">
						<input type="hidden" name="pipeline_id"
							id="pipeline_id_clustalomega_form" value="${clustalomega_id}" />
						<div class="step1">
							<fieldset>
								<legend>
									<strong>STEP 1</strong> Enter your input sequence
								</legend>
								<div class="in">
									<label for="sequence"><a href="#" class="btnSgreen">FASTA form sequence</a></label>
									
									<a onclick="output4();" href="#" style="background:#00493f; width:70px;">Sample</a>	
									<label for="files4" class="filebox" style="background:#006185; width:70px;">Local File
										<input type="file" id="files4" name="file" onchange="open_file_dialog(5)">
									</label>
									<a onclick="open_dialog('/express/' + '${user_id}', 5);" href="#" style="background:#393572; width:70px;">Cloud File</a>	
									<a onclick="delete4();" href="#" style="background:#888; width:70px;">Clear</a>
									
									<textarea cols="47" id="input_clustalomega" name="clustalo|FILE|input"
										rows="7">${inputClustalomega}</textarea>
								</div>
							</fieldset>
	
						</div>
	
						<div class="step2">
							<fieldset>
								<legend>
									<strong>STEP 2</strong> Submit your job
								</legend>
								<div class="in">
									<label> <input id="notification_clustalomega"
										name="notification_clustalomega" type="checkbox"
										checked="checked" class="mr5" /> Be notified by email
									</label> <span class="fontI">(Tick this box if you want to be
										notified by email when the results are available)</span>
									<div id="submission_clustalomega" class="submission">
										<label for="email">EMAIL</label> <input id="email_address"
											name="email_address" type="text"
											value="${user_model.email_adress}" /><br /> <label
											for="title">TITLE</label> <input id="project_title"
											name="project_title" type="text" value="${clustalomega_title}" /><br />
										<span class="fontI">If available, the title will be
											included in the subject of the notification email and can be
											used as a way to identify your analysis</span> <input type="text"
											name="clustalo|FOLDER|output_folder" id="output_folder"
											value="/" style="display: none;" />
									</div>
								</div>
							</fieldset>
						</div>
					</div>
	
					<div class="mt20 txtC">
						<input type="button" class="btnblue" value="Submit"
							id="clustalomega_form_button" name="clustalomega_form_button"
							onclick="analysis_form_check('clustalomega_form');" /> <input
							type="button" class="btngray" value="Reset"
							onclick="form_reset('clustalomega_form');" />
					</div>
				</form>
			</div>
	
			<div id="div_tab_5" style="display: none;">
				<form method="POST" id="muscle_form" name="muscle_form">
					<div id="step">
						<input type="hidden" name="pipeline_id"
							id="pipeline_id_muscle_form" value="${muscle_id}" />
						<div class="step1">
							<fieldset>
								<legend>
									<strong>STEP 1</strong> Enter your input sequence
								</legend>
								<div class="in">
									<label for="sequence"><a href="#" class="btnSgreen">FASTA form sequence</a></label>
									
									<a onclick="output5();" href="#" style="background:#00493f; width:70px;">Sample</a>	
									<label for="files5" class="filebox" style="background:#006185; width:70px;">Local File
										<input type="file" id="files5" name="file" onchange="open_file_dialog(6)">
									</label>
									<a onclick="open_dialog('/express/' + '${user_id}', 6);" href="#" style="background:#393572; width:70px;">Cloud File</a>	
									<a onclick="delete5();" href="#" style="background:#888; width:70px;">Clear</a>
									
									<textarea cols="47" id="input_muscle" name="muscle|FILE|input1"
										rows="7">${inputMuscle}</textarea>
								</div>
							</fieldset>
	
						</div>
	
						<div class="step2">
							<fieldset>
								<legend>
									<strong>STEP 2</strong> Submit your job
								</legend>
								<div class="in">
									<label> <input id="notification_muscle"
										name="notification_muscle" type="checkbox" checked="checked"
										class="mr5" /> Be notified by email
									</label> <span class="fontI">(Tick this box if you want to be
										notified by email when the results are available)</span>
									<div id="submission_muscle" class="submission">
										<label for="email">EMAIL</label> <input id="email_address"
											name="email_address" type="text"
											value="${user_model.email_adress}" /><br /> <label
											for="title">TITLE</label> <input id="project_title"
											name="project_title" type="text" value="${muscle_title}" /><br />
										<span class="fontI">If available, the title will be
											included in the subject of the notification email and can be
											used as a way to identify your analysis</span> <input type="text"
											name="muscle|FOLDER|output_folder" id="output_folder"
											value="/" style="display: none;" />
									</div>
								</div>
							</fieldset>
						</div>
					</div>
	
					<div class="mt20 txtC">
						<input type="button" class="btnblue" value="Submit"
							id="muscle_form_button" name="muscle_form_button"
							onclick="analysis_form_check('muscle_form');" /> <input
							type="button" class="btngray" value="Reset"
							onclick="form_reset('muscle_form');" />
					</div>
	
				</form>
			</div>
	
	
			<%-- <div id="div_tab_6" style="display: none;">
				<form method="POST" id="mega_form" name="mega_form">
					<div id="step">
						<input type="hidden" name="pipeline_id" id="pipeline_id_mega_form"
							value="${mega_id}" />
						<div class="step1">
							<fieldset>
								<legend>
									<strong>STEP 1</strong> Enter your input sequence
								</legend>
								<div class="in">
									<label for="sequence"><a href="#" class="btnSgreen">FASTA form sequence</a></label>
									<a onclick="open_dialog('/express/' + '${user_id}', 7);" href="#" class="btnSgray">file</a>
									<textarea cols="47" id="input_mega" name="mega|FILE|input1" rows="7">insert sequence</textarea>
								</div>
								<div class="btnbox">
									<input type="button" class="btndeepgreen" value="Sample" />	
									<input type="button" class="btndeepblue" value="Clear" onclick="text_area_clear(this.form);" />
								</div>	
							</fieldset>
	
						</div>
	
						<div class="step2">
							<fieldset>
								<legend>
									<strong>STEP 2</strong> Submit your job
								</legend>
								<div class="in">
									<label> <input id="notification_muscle"
										name="notification_muscle" type="checkbox" checked="checked"
										class="mr5" /> Be notified by email
									</label> <span class="fontI">(Tick this box if you want to be
										notified by email when the results are available)</span>
									<div id="submission_muscle" class="submission">
										<label for="email">EMAIL</label> <input id="email_address"
											name="email_address" type="text"
											value="${user_model.email_adress}" /><br /> <label
											for="title">TITLE</label> <input id="project_title"
											name="project_title" type="text" value="${muscle_title}" /><br />
										<span class="fontI">If available, the title will be
											included in the subject of the notification email and can be
											used as a way to identify your analysis</span> <input type="text"
											name="muscle|FOLDER|output_folder" id="output_folder"
											value="/" style="display: none;" />
									</div>
								</div>
							</fieldset>
						</div>
					</div>
	
					<div class="mt20 txtC">
						<input type="button" class="btnblue" value="Submit"
							id="muscle_form_button" name="muscle_form_button"
							onclick="analysis_form_check('muscle_form');" /> <input
							type="button" class="btngray" value="Reset"
							onclick="form_reset('muscle_form');" />
					</div>
	
				</form>
			</div> --%>
	
	
			<div id="div_tab_6" style="display: none;">
	
				<div class="newickarea" style="width:940px;">
					<div class="step1">
						<fieldset>
							<legend>
								<strong>Paste in a <a href="http://en.wikipedia.org/wiki/Newick_format" style="color:#fff; padding:0;">Newick-format</a></strong>
								<select id="style">
								<option value="circle">Circle tree</option>
								<option value="circlephylogram">Circle phylogram</option>
								<option value="cladogram">Cladogram</option>
								<option value="rectanglecladogram">Rectangular cladogram</option>
								<option value="phylogram">Phylogram</option>
								</select>
							</legend>
							<div class="btnbox">
								<a onclick="open_dialog('/express/' + '${user_id}', 8);" href="#" class="btngray">File</a>
								<input type="button" class="btndeepgreen" value="Sample" onclick="output6();" />	
								<button onclick="showtree('newick');" class="btngreen">Show</button>
								<input type="button" class="btndeepblue" value="Clear" onclick="delete6();" />
							</div>								
						</fieldset>
	
					</div>
					
					<div class="step2">
						<fieldset>
							<legend><strong style="border:none">Insert Newick String</strong></legend>
							<div class="in">
								<textarea id="newick" rows="10" cols="100" style="width:880px;"></textarea>
							</div>
						</fieldset>
					</div>
	
	<!-- 				<p style="width:940px;"> -->
	<!-- 					<span id="message"></span> -->
	<!-- 				</p> -->
	
					<div id="newick_area" class="step6" style="display: none;">
<!-- 						<div id="svgDown" class="btnsvgDown"><a href="javascript:;" onclick="save();">Dlownload</a></div> -->
						<div id="newicksvg" class="newicksvg" style="width:940px;">					
							<svg id="svg" xmlns="http://www.w3.org/2000/svg" version="1.1" height="1140" width="880" style="overflow: hidden;">
							</svg>
						</div>					
					</div>
				</div>
			</div>
	
		</div>
	</div>
	<!-- contents 정렬 E-->
</div>

<!-- contents E -->

<script>
	var program_name = "${analysis_program_name}";

	if (program_name == "blast") {
		con_tab(2);
	} else if (program_name == "interpro") {
		con_tab(3);
	} else if (program_name == "clustalomega") {
		con_tab(4);
	} else if (program_name == "muscle") {
		con_tab(5);
	} else if (program_name == "mega") {
		con_tab(6);
	} else if (program_name == "phylogeneticview") {
		con_tab(7);		
	}

	if (program_name != "") {
		var form_name = "${analysis_program_name}" + "_form";
		var line = "${line}";
		//line = line.replaceAll("<br>", "\r\n");
		$("#" + form_name).find("textarea").text(line);
	}
	
	if (!String.prototype.trim) {
		String.prototype.trim=function(){return this.replace(/^\s+|\s+$/g, '');};
	}
	
	function showtree(element_id) {
	    
		var t = new Tree();
	    var element = document.getElementById(element_id);
	    var newick = element.value;
	    newick = newick.trim(newick);
	    
	    if(newick == "insert newick string" || newick == ""){
	    	
	    	alert("newick");
	    	
	    	return;
	    }else if(newick.search(/\s/) != -1){
	    	
			if(confirm("Removes spaces in the data.") == true){
	    		
	    		newick = newick.replace(/\n/g, "");
	    	}else {
	    		
	    		return;
	    	}
	    }
	    
		t.Parse(newick);
		
		if (t.error != 0) {
// 			document.getElementById('message').innerHTML='Error parsing tree';
			alert('Error parsing tree');
		}
		else {
			/* document.getElementById('message').innerHTML='Parsed OK'; */
			
			t.WriteNewick();
			
			t.ComputeWeights(t.root);
			
			var td = null;
			
			var selectmenu = document.getElementById('style');
			var drawing_type = (selectmenu.options[selectmenu.selectedIndex].value);
			
			switch (drawing_type) {
				
			case 'rectanglecladogram':
					td = new RectangleTreeDrawer();
					break;
			
				case 'phylogram':
					if (t.has_edge_lengths) {
						td = new PhylogramTreeDrawer();
					}
					else {
						td = new RectangleTreeDrawer();
					}
					break;
					
				case 'circle':
					td = new CircleTreeDrawer();
					break;
					
				case 'circlephylogram':
					if (t.has_edge_lengths) {
						td = new CirclePhylogramDrawer();
					}
					else {
						td = new CircleTreeDrawer();
					}
					break;
					
				case 'cladogram':
				default:
					td = new TreeDrawer();
					break;
			}
			
			// clear existing diagram, if any
			var svg = document.getElementById('svg');
			while (svg.hasChildNodes()) {
				svg.removeChild(svg.lastChild);
			}
			
			
			var cssStyle = document.createElementNS('http://www.w3.org/2000/svg','style');
			cssStyle.setAttribute('type','text/css');
			
			var style=document.createTextNode("text{font-size:6px;}");
			cssStyle.appendChild(style);
			
			svg.appendChild(cssStyle);
			
			
			var g = document.createElementNS('http://www.w3.org/2000/svg','g');
			g.setAttribute('id','viewport');
			svg.appendChild(g);
			
			
			td.Init(t, {svg_id: 'viewport', width:700, height:700, fontHeight:12, root_length:0.1, left:1000} );
			
			td.CalcCoordinates();
			td.Draw();
			
			// label leaves...
			
			var n = new NodeIterator(t.root);
			var q = n.Begin();
			while (q != null) {
				
				if (q.IsLeaf()) {
					
					switch (drawing_type) {
						case 'circle':
						case 'circlephylogram':
							var align = 'left';
							var angle = q.angle * 180.0/Math.PI;
							
							if ((q.angle > Math.PI/2.0) && (q.angle < 1.5 * Math.PI)) {
							
								align = 'right';
								angle += 180.0;
							}
							
							drawRotatedText('viewport', q.xy, q.label, angle, align);
							break;
					
						case 'cladogram':
						case 'rectanglecladogram':
						case 'phylogram':
						default:				
							drawText('viewport', q.xy, q.label);
							break;
					}
				}
				q = n.Next();
			}
			
			
			// pan
			$('svg').svgPan('viewport');
			
			// phylogeny tree 그릴때만 보여주기
			document.getElementById("newick_area").style.display = 'block';
		}
	}

	function open_file_dialog(id) {

		var files;
		if (id == 1) {
			files = document.getElementById('files1_1').files;
		} else if (id == 2) {
			var files = document.getElementById('files1_2').files;
		} else if (id == 3) {
			var files = document.getElementById('files2').files;
		} else if (id == 4) {
			var files = document.getElementById('files3').files;
		} else if (id == 5) {
			var files = document.getElementById('files4').files;
		} else if (id == 6) {
			var files = document.getElementById('files5').files;
		}

		if (!files.length) {
			alert('Please select a file');
			return;
		}

		var file = files[0];

		var reader = new FileReader();

		reader.onload = function(event) {
			if (id == 1) {
				var content = document.getElementById('input1');
				content.value = event.target.result;
			} else if (id == 2) {
				var content = document.getElementById('input3');
				content.value = event.target.result;
			} else if (id == 3) {
				var content = document.getElementById('input_blast');
				content.value = event.target.result;
			} else if (id == 4) {
				var content = document.getElementById('input_interpro');
				content.value = event.target.result;
			} else if (id == 5) {
				var content = document.getElementById('input_clustalomega');
				content.value = event.target.result;
			} else if (id == 6) {
				var content = document.getElementById('input_muscle');
				content.value = event.target.result;
			}
		};

		reader.onerror = function(event) {
			var errcode = event.target.error.code;
			if (errcode == 1) {
				alert("File not found");
			} else if (errcode == 2) {
				alert("It is not safe or there is a change in the file");
			} else if (errcode == 3) {
				alert("Reading stopped");
			} else if (errcode == 4) {
				alert("I could not read the file due to a problem with access rights");
			} else if (errcode == 5) {
				alert("There is a URL length limitation issue");
			}
		};

		reader.readAsText(file); //기본 인코딩 : utf-8
	}

	function output1_1() {
		document.getElementById("input1").value = ">humanMito\nGATCACAGGTCTATCACCCTATTAACCACTCACGGGAGCTCTCCATGCATTTGGTATTTTCGTCTGGGGGGTGTGCACGCGATAGCATTGCGAGACGCTGGAGCCGGAGCACCCTATGTCGCAGTATCTGTCTTTGATTCCTGCCTCATTCTATTATTTATCGCACCTACGTTCAATATTACAGGCGAACATACCTACTAAAGTGTGTTAATTAATTAATGCTTGTAGGACATAATAATAACAATTGAATGTCTGCACAGCCGCTTTCCACACAGACATCATAACAAAAAATTTCCACCAAACCCCCCCCTCCCCCCGCTTCTGGCCACAGCACTTAAACACATCTCTGCCAAACCCCAAAAACAAAGAACCCTAACACCAGCCTAACCAGATTTCAAATTTTATCTTTAGGCGGTATGCACTTTTAACAGTCACCCCCCAACTAACACATTATTTTCCCCTCCCACTCCCATACTACTAATCTCATCAATACAACCCCCGCCCATCCTACCCAGCACACACACACCGCTGCTAACCCCATACCCCGAACCAACCAAACCCCAAAGACACCCCCCACAGTTTATGTAGCTTACCTCCTCAAAGCAATACACTGAAAATGTTTAGACGGGCTCACATCACCCCATAAACAAATAGGTTTGGTCCTAGCCTTTCTATTAGCTCTTAGTAAGATTACACATGCAAGCATCCCCGTTCCAGTGAGTTCACCCTCTAAATCACCACGATCAAAAGGGACAAGCATCAAGCACGCAGCAATGCAGCTCAAAACGCTTAGCCTAGCCACACCCCCACGGGAAACAGCAGTGATTAACCTTTAGCAATAAACGAAAGTTTAACTAAGCTATACTAACCCCAGGGTTGGTCAATTTCGTGCCAGCCACCGCGGTCACACGATTAACCCAAGTCAATAGAAGCCGGCGTAAAGAGTGTTTTAGATCACCCCCTCCCCAATAAAGCTAAAACTCACCTGAGTTGTAAAAAACTCCAGTTGACACAAAATAGACTACGAAAGTGGCTTTAACATATCTGAACACACAATAGCTAAGACCCAAACTGGGATTAGATACCCCACTATGCTTAGCCCTAAACCTCAACAGTTAAATCAACAAAACTGCTCGCCAGAACACTACGAGCCACAGCTTAAAACTCAAAGGACCTGGCGGTGCTTCATATCCCTCTAGAGGAGCCTGTTCTGTAATCGATAAACCCCGATCAACCTCACCACCTCTTGCTCAGCCTATATACCGCCATCTTCAGCAAACCCTGATGAAGGCTACAAAGTAAGCGCAAGTACCCACGTAAAGACGTTAGGTCAAGGTGTAGCCCATGAGGTGGCAAGAAATGGGCTACATTTTCTACCCCAGAAAACTACGATAGCCCTTATGAAACTTAAGGGTCGAAGGTGGATTTAGCAGTAAACTGAGAGTAGAGTGCTTAGTTGAACAGGGCCCTGAAGCGCGTACACACCGCCCGTCACCCTCCTCAAGTATACTTCAAAGGACATTTAACTAAAACCCCTACGCATTTATATAGAGGAGACAAGTCGTAACATGGTAAGTGTACTGGAAAGTGCACTTGGACGAACCAGAGTGTAGCTTAACACAAAGCACCCAACTTACACTTAGGAGATTTCAACTTAACTTGACCGCTCTGAGCTAAACCTAGCCCCAAACCCACTCCACCTTACTACCAGACAACCTTAGCCAAACCATTTACCCAAATAAAGTATAGGCGATAGAAATTGAAACCTGGCGCAATAGATATAGTACCGCAAGGGAAAGATGAAAAATTATAACCAAGCATAATATAGCAAGGACTAACCCCTATACCTTCTGCATAATGAATTAACTAGAAATAACTTTGCAAGGAGAGCCAAAGCTAAGACCCCCGAAACCAGACGAGCTACCTAAGAACAGCTAAAAGAGCACACCCGTCTATGTAGCAAAATAGTGGGAAGATTTATAGGTAGAGGCGACAAACCTACCGAGCCTGGTGATAGCTGGTTGTCCAAGATAGAATCTTAGTTCAACTTTAAATTTGCCCACAGAACCCTCTAAATCCCCTTGTAAATTTAACTGTTAGTCCAAAGAGGAACAGCTCTTTGGACACTAGGAAAAAACCTTGTAGAGAGAGTAAAAAATTTAACACCCATAGTAGGCCTAAAAGCAGCCACCAATTAAGAAAGCGTTCAAGCTCAACACCCACTACCTAAAAAATCCCAAACATATAACTGAACTCCTCACACCCAATTGGACCAATCTATCACCCTATAGAAGAACTAATGTTAGTATAAGTAACATGAAAACATTCTCCTCCGCATAAGCCTGCGTCAGATCAAAACACTGAACTGACAATTAACAGCCCAATATCTACAATCAACCAACAAGTCATTATTACCCTCACTGTCAACCCAACACAGGCATGCTCATAAGGAAAGGTTAAAAAAAGTAAAAGGAACTCGGCAAACCTTACCCCGCCTGTTTACCAAAAACATCACCTCTAGCATCACCAGTATTAGAGGCACCGCCTGCCCAGTGACACATGTTTAACGGCCGCGGTACCCTAACCGTGCAaaggtagcataatcacttgttccttaaatagggacctgtatgaatggctccacgagggttcagctgtctcttacttttaaccagtgaaattgacctgcccgtgaagaggcgggcatgacacagcaagacgagaagaccctatggagctttaatttaTTAATGCAAACAGTACCTAACAAACCCACAGGTCCTAAACTACCAAACCTGCATTAAAAATTTCGGTTGGGGCGACCTCGGAGCAGAACCCAACCTCCGAGCAGTACATGCTAAGACTTCACCAGTCAAAGCGAACTACTATACTCAATTGATCCAATAACTTGACCAACGGAACAAGTTACCCTAGGGATAACAGCGCAATCCTATTCTAGAGTCCATATCAACAATAGGGTTTACGACCTCGATGTTGGATCAGGACATCCCGATGGTGCAGCCGCTATTAAAGGTTCGTTTGTTCAACGATTAAAGTCCTACGTGATCTGAGTTCAGACCGGAGTAATCCAGGTCGGTTTCTATCTACTTCAAATTCCTCCCTGTACGAAAGGACAAGAGAAATAAGGCCTACTTCACAAAGCGCCTTCCCCCGTAAATGATATCATCTCAACTTAGTATTATACCCACACCCACCCAAGAACAGGGTTTgttaagatggcagagcccggtaatcgcataaaacttaaaactttacagtcagaggttcaattcctcttcttaacaacaTACCCATGGCCAACCTCCTACTCCTCATTGTACCCATTCTAATCGCAATGGCATTCCTAATGCTTACCGAACGAAAAATTCTAGGCTATATACAACTACGCAAAGGCCCCAACGTTGTAGGCCCCTACGGGCTACTACAACCCTTCGCTGACGCCATAAAACTCTTCACCAAAGAGCCCCTAAAACCCGCCACATCTACCATCACCCTCTACATCACCGCCCCGACCTTAGCTCTCACCATCGCTCTTCTACTATGAACCCCCCTCCCCATACCCAACCCCCTGGTCAACCTCAACCTAGGCCTCCTATTTATTCTAGCCACCTCTAGCCTAGCCGTTTACTCAATCCTCTGATCAGGGTGAGCATCAAACTCAAACTACGCCCTGATCGGCGCACTGCGAGCAGTAGCCCAAACAATCTCATATGAAGTCACCCTAGCCATCATTCTACTATCAACATTACTAATAAGTGGCTCCTTTAACCTCTCCACCCTTATCACAACACAAGAACACCTCTGATTACTCCTGCCATCATGACCCTTGGCCATAATATGATTTATCTCCACACTAGCAGAGACCAACCGAACCCCCTTCGACCTTGCCGAAGGGGAGTCCGAACTAGTCTCAGGCTTCAACATCGAATACGCCGCAGGCCCCTTCGCCCTATTCTTCATAGCCGAATACACAAACATTATTATAATAAACACCCTCACCACTACAATCTTCCTAGGAACAACATATGACGCACTCTCCCCTGAACTCTACACAACATATTTTGTCACCAAGACCCTACTTCTAACCTCCCTGTTCTTATGAATTCGAACAGCATACCCCCGATTCCGCTACGACCAACTCATACACCTCCTATGAAAAAACTTCCTACCACTCACCCTAGCATTACTTATATGATATGTCTCCATACCCATTACAATCTCCAGCATTCCCCCTCAAACCTAAGAAATATGTCTGATAAAAGAGTTACTTTGATAGAGTAAATAATAGGAGCTTAAACCCCCTTATTTctaggactatgagaatcgaacccatccctgagaatccaaaattctccgtgccacctatcacaccccatcctaAAGTAAGGTCAGCTAAATAAGCTATCGGGCCCATACCCCGAAAATGTTGGTTATACCCTTCCCGTACTAATTAATCCCCTGGCCCAACCCGTCATCTACTCTACCATCTTTGCAGGCACACTCATCACAGCGCTAAGCTCGCACTGATTTTTTACCTGAGTAGGCCTAGAAATAAACATGCTAGCTTTTATTCCAGTTCTAACCAAAAAAATAAACCCTCGTTCCACAGAAGCTGCCATCAAGTATTTCCTCACGCAAGCAACCGCATCCATAATCCTTCTAATAGCTATCCTCTTCAACAATATACTCTCCGGACAATGAACCATAACCAATACTACCAATCAATACTCATCATTAATAATCATAATGGCTATAGCAATAAAACTAGGAATAGCCCCCTTTCACTTCTGAGTCCCAGAGGTTACCCAAGGCACCCCTCTGACATCCGGCCTGCTTCTTCTCACATGACAAAAACTAGCCCCCATCTCAATCATATACCAAATCTCTCCCTCACTAAACGTAAGCCTTCTCCTCACTCTCTCAATCTTATCCATCATAGCAGGCAGTTGAGGTGGATTAAACCAAACCCAGCTACGCAAAATCTTAGCATACTCCTCAATTACCCACATAGGATGAATAATAGCAGTTCTACCGTACAACCCTAACATAACCATTCTTAATTTAACTATTTATATTATCCTAACTACTACCGCATTCCTACTACTCAACTTAAACTCCAGCACCACGACCCTACTACTATCTCGCACCTGAAACAAGCTAACATGACTAACACCCTTAATTCCATCCACCCTCCTCTCCCTAGGAGGCCTGCCCCCGCTAACCGGCTTTTTGCCCAAATGGGCCATTATCGAAGAATTCACAAAAAACAATAGCCTCATCATCCCCACCATCATAGCCACCATCACCCTCCTTAACCTCTACTTCTACCTACGCCTAATCTACTCCACCTCAATCACACTACTCCCCATATCTAACAACGTAAAAATAAAATGACAGTTTGAACATACAAAACCCACCCCATTCCTCCCCACACTCATCGCCCTTACCACGCTACTCCTACCTATCTCCCCTTTTATACTAATAATCTTATAGAAATTTAGGTTAAATACAGACCAAGAGCCTTCAAAGCCCTCAGTAAGTTGCAATACTTAATTTCTGCAACAGCTAAGGACTGCAAAACCCCACTCTGCATCAACTGAACGCAAATCAGCCACTTTAATTAAGCTAAGCCCTTACTAGACCAATGGGACTTAAACCCACAAACACTTAGTTAACAGCTAAGCACCCTAATCAACTGGCTTCAATCTACTTCTCCCGCCGCCGGGAAAAAAGGCGGGAGAAGCCCCGGCAGGTTTGAAGCTGCTTCTTCGAATTTGCAATTCAATATGAAAATCACCTCGGAGCTGGTAAAAAGAGGCCTAACCCCTGTCTTTAGATTTACAGTCCAATGCTTCACTCAGCCATTTTACCTCACCCCCACTGATGTTCGCCGACCGTTGACTATTCTCTACAAACCACAAAGACATTGGAACACTATACCTATTATTCGGCGCATGAGCTGGAGTCCTAGGCACAGCTCTAAGCCTCCTTATTCGAGCCGAGCTGGGCCAGCCAGGCAACCTTCTAGGTAACGACCACATCTACAACGTTATCGTCACAGCCCATGCATTTGTAATAATCTTCTTCATAGTAATACCCATCATAATCGGAGGCTTTGGCAACTGACTAGTTCCCCTAATAATCGGTGCCCCCGATATGGCGTTTCCCCGCATAAACAACATAAGCTTCTGACTCTTACCTCCCTCTCTCCTACTCCTGCTCGCATCTGCTATAGTGGAGGCCGGAGCAGGAACAGGTTGAACAGTCTACCCTCCCTTAGCAGGGAACTACTCCCACCCTGGAGCCTCCGTAGACCTAACCATCTTCTCCTTACACCTAGCAGGTGTCTCCTCTATCTTAGGGGCCATCAATTTCATCACAACAATTATCAATATAAAACCCCCTGCCATAACCCAATACCAAACGCCCCTCTTCGTCTGATCCGTCCTAATCACAGCAGTCCTACTTCTCCTATCTCTCCCAGTCCTAGCTGCTGGCATCACTATACTACTAACAGACCGCAACCTCAACACCACCTTCTTCGACCCCGCCGGAGGAGGAGACCCCATTCTATACCAACACCTATTCTGATTTTTCGGTCACCCTGAAGTTTATATTCTTATCCTACCAGGCTTCGGAATAATCTCCCATATTGTAACTTACTACTCCGGAAAAAAAGAACCATTTGGATACATAGGTATGGTCTGAGCTATGATATCAATTGGCTTCCTAGGGTTTATCGTGTGAGCACACCATATATTTACAGTAGGAATAGACGTAGACACACGAGCATATTTCACCTCCGCTACCATAATCATCGCTATCCCCACCGGCGTCAAAGTATTTAGCTGACTCGCCACACTCCACGGAAGCAATATGAAATGATCTGCTGCAGTGCTCTGAGCCCTAGGATTCATCTTTCTTTTCACCGTAGGTGGCCTGACTGGCATTGTATTAGCAAACTCATCACTAGACATCGTACTACACGACACGTACTACGTTGTAGCTCACTTCCACTATGTCCTATCAATAGGAGCTGTATTTGCCATCATAGGAGGCTTCATTCACTGATTTCCCCTATTCTCAGGCTACACCCTAGACCAAACCTACGCCAAAATCCATTTCACTATCATATTCATCGGCGTAAATCTAACTTTCTTCCCACAACACTTTCTCGGCCTATCCGGAATGCCCCGACGTTACTCGGACTACCCCGATGCATACACCACATGAAACATCCTATCATCTGTAGGCTCATTCATTTCTCTAACAGCAGTAATATTAATAATTTTCATGATTTGAGAAGCCTTCGCTTCGAAGCGAAAAGTCCTAATAGTAGAAGAACCCTCCATAAACCTGGAGTGACTATATGGATGCCCCCCACCCTACCACACATTCGAAGAACCCGTATACATAAAATCTAGACAaaaaaggaaggaatcgaaccccccaaagctggtttcaagccaaccccatggcctccatgactttttcAAAAAGGTATTAGAAAAACCATTTCATAACTTTGTCAAAGTTAAATTATAGGCTAAATCCTATATATCTTAATGGCACATGCAGCGCAAGTAGGTCTACAAGACGCTACTTCCCCTATCATAGAAGAGCTTATCACCTTTCATGATCACGCCCTCATAATCATTTTCCTTATCTGCTTCCTAGTCCTGTATGCCCTTTTCCTAACACTCACAACAAAACTAACTAATACTAACATCTCAGACGCTCAGGAAATAGAAACCGTCTGAACTATCCTGCCCGCCATCATCCTAGTCCTCATCGCCCTCCCATCCCTACGCATCCTTTACATAACAGACGAGGTCAACGATCCCTCCCTTACCATCAAATCAATTGGCCACCAATGGTACTGAACCTACGAGTACACCGACTACGGCGGACTAATCTTCAACTCCTACATACTTCCCCCATTATTCCTAGAACCAGGCGACCTGCGACTCCTTGACGTTGACAATCGAGTAGTACTCCCGATTGAAGCCCCCATTCGTATAATAATTACATCACAAGACGTCTTGCACTCATGAGCTGTCCCCACATTAGGCTTAAAAACAGATGCAATTCCCGGACGTCTAAACCAAACCACTTTCACCGCTACACGACCGGGGGTATACTACGGTCAATGCTCTGAAATCTGTGGAGCAAACCACAGTTTCATGCCCATCGTCCTAGAATTAATTCCCCTAAAAATCTTTGAAATAGGGCCCGTATTTACCCTATAGCACCCCCTCTACCCCCTCTAGAGCCCACTGTAAAGCTAACTTAGCATTAACCTTTTAAGTTAAAGATTAAGAGAACCAACACCTCTTTACAGTGAAATGCCCCAACTAAATACTACCGTATGGCCCACCATAATTACCCCCATACTCCTTACACTATTCCTCATCACCCAACTAAAAATATTAAACACAAACTACCACCTACCTCCCTCACCAAAGCCCATAAAAATAAAAAATTATAACAAACCCTGAGAACCAAAATGAACGAAAATCTGTTCGCTTCATTCATTGCCCCCACAATCCTAGGCCTACCCGCCGCAGTACTGATCATTCTATTTCCCCCTCTATTGATCCCCACCTCCAAATATCTCATCAACAACCGACTAATCACCACCCAACAATGACTAATCAAACTAACCTCAAAACAAATGATAGCCATACACAACACTAAAGGACGAACCTGATCTCTTATACTAGTATCCTTAATCATTTTTATTGCCACAACTAACCTCCTCGGACTCCTGCCTCACTCATTTACACCAACCACCCAACTATCTATAAACCTAGCCATGGCCATCCCCTTATGAGCGGGCGCAGTGATTATAGGCTTTCGCTCTAAGATTAAAAATGCCCTAGCCCACTTCTTACCACAAGGCACACCTACACCCCTTATCCCCATACTAGTTATTATCGAAACCATCAGCCTACTCATTCAACCAATAGCCCTGGCCGTACGCCTAACCGCTAACATTACTGCAGGCCACCTACTCATGCACCTAATTGGAAGCGCCACCCTAGCAATATCAACCATTAACCTTCCCTCTACACTTATCATCTTCACAATTCTAATTCTACTGACTATCCTAGAAATCGCTGTCGCCTTAATCCAAGCCTACGTTTTCACACTTCTAGTAAGCCTCTACCTGCACGACAACACATAATGACCCACCAATCACATGCCTATCATATAGTAAAACCCAGCCCATGACCCCTAACAGGGGCCCTCTCAGCCCTCCTAATGACCTCCGGCCTAGCCATGTGATTTCACTTCCACTCCATAACGCTCCTCATACTAGGCCTACTAACCAACACACTAACCATATACCAATGGTGGCGCGATGTAACACGAGAAAGCACATACCAAGGCCACCACACACCACCTGTCCAAAAAGGCCTTCGATACGGGATAATCCTATTTATTACCTCAGAAGTTTTTTTCTTCGCAGGATTTTTCTGAGCCTTTTACCACTCCAGCCTAGCCCCTACCCCCCAACTAGGAGGGCACTGGCCCCCAACAGGCATCACCCCGCTAAATCCCCTAGAAGTCCCACTCCTAAACACATCCGTATTACTCGCATCAGGAGTATCAATCACCTGAGCTCACCATAGTCTAATAGAAAACAACCGAAACCAAATAATTCAAGCACTGCTTATTACAATTTTACTGGGTCTCTATTTTACCCTCCTACAAGCCTCAGAGTACTTCGAGTCTCCCTTCACCATTTCCGACGGCATCTACGGCTCAACATTTTTTGTAGCCACAGGCTTCCACGGACTTCACGTCATTATTGGCTCAACTTTCCTCACTATCTGCTTCATCCGCCAACTAATATTTCACTTTACATCCAAACATCACTTTGGCTTCGAAGCCGCCGCCTGATACTGGCATTTTGTAGATGTGGTTTGACTATTTCTGTATGTCTCCATCTATTGATGAGGGTCTTACTCTTTTAGTATAAATAGTACCGTTAACTTCCAATTAACTAGTTTTGACAACATTCAAAAAAGAGTAATAAACTTCGCCTTAATTTTAATAATCAACACCCTCCTAGCCTTACTACTAATAATTATTACATTTTGACTACCACAACTCAACGGCTACATAGAAAAATCCACCCCTTACGAGTGCGGCTTCGACCCTATATCCCCCGCCCGCGTCCCTTTCTCCATAAAATTCTTCTTAGTAGCTATTACCTTCTTATTATTTGATCTAGAAATTGCCCTCCTTTTACCCCTACCATGAGCCCTACAAACAACTAACCTGCCACTAATAGTTATGTCATCCCTCTTATTAATCATCATCCTAGCCCTAAGTCTGGCCTATGAGTGACTACAAAAAGGATTAGACTGAGCCGAATTGGTATATAGTTTAAACAAAACGAATGATTTCGACTCATTAAATTATGATAATCATATTTACCAAATGCCCCTCATTTACATAAATATTATACTAGCATTTACCATCTCACTTCTAGGAATACTAGTATATCGCTCACACCTCATATCCTCCCTACTATGCCTAGAAGGAATAATACTATCGCTGTTCATTATAGCTACTCTCATAACCCTCAACACCCACTCCCTCTTAGCCAATATTGTGCCTATTGCCATACTAGTCTTTGCCGCCTGCGAAGCAGCGGTGGGCCTAGCCCTACTAGTCTCAATCTCCAACACATATGGCCTAGACTACGTACATAACCTAAACCTACTCCAATGCTAAAACTAATCGTCCCAACAATTATATTACTACCACTGACATGACTTTCCAAAAAGCACATAATTTGAATCAACACAACCACCCACAGCCTAATTATTAGCATCATCCCCCTACTATTTTTTAACCAAATCAACAACAACCTATTTAGCTGTTCCCCAACCTTTTCCTCCGACCCCCTAACAACCCCCCTCCTAATACTAACTACCTGACTCCTACCCCTCACAATCATGGCAAGCCAACGCCACTTATCCAGCGAACCACTATCACGAAAAAAACTCTACCTCTCTATACTAATCTCCCTACAAATCTCCTTAATTATAACATTCACAGCCACAGAACTAATCATATTTTATATCTTCTTCGAAACCACACTTATCCCCACCTTGGCTATCATCACCCGATGAGGCAACCAGCCAGAACGCCTGAACGCAGGCACATACTTCCTATTCTACACCCTAGTAGGCTCCCTTCCCCTACTCATCGCACTAATTTACACTCACAACACCCTAGGCTCACTAAACATTCTACTACTCACTCTCACTGCCCAAGAACTATCAAACTCCTGAGCCAACAACTTAATATGACTAGCTTACACAATAGCTTTTATAGTAAAGATACCTCTTTACGGACTCCACTTATGACTCCCTAAAGCCCATGTCGAAGCCCCCATCGCTGGGTCAATAGTACTTGCCGCAGTACTCTTAAAACTAGGCGGCTATGGTATAATACGCCTCACACTCATTCTCAACCCCCTGACAAAACACATAGCCTACCCCTTCCTTGTACTATCCCTATGAGGCATAATTATAACAAGCTCCATCTGCCTACGACAAACAGACCTAAAATCGCTCATTGCATACTCTTCAATCAGCCACATAGCCCTCGTAGTAACAGCCATTCTCATCCAAACCCCCTGAAGCTTCACCGGCGCAGTCATTCTCATAATCGCCCACGGACTCACATCCTCATTACTATTCTGCCTAGCAAACTCAAACTACGAACGCACTCACAGTCGCATCATAATCCTCTCTCAAGGACTTCAAACTCTACTCCCACTAATAGCTTTTTGATGACTTCTAGCAAGCCTCGCTAACCTCGCCTTACCCCCCACTATTAACCTACTGGGAGAACTCTCTGTGCTAGTAACCACGTTCTCCTGATCAAATATCACTCTCCTACTTACAGGACTCAACATACTAGTCACAGCCCTATACTCCCTCTACATATTTACCACAACACAATGGGGCTCACTCACCCACCACATTAACAACATAAAACCCTCATTCACACGAGAAAACACCCTCATGTTCATACACCTATCCCCCATTCTCCTCCTATCCCTCAACCCCGACATCATTACCGGGTTTTCCTCTTGTAAATATAGTTTAACCAAAACATCAGATTGTGAATCTGACAACAGAGGCTTACGACCCCTTATTTACCGAGAAAGCTCACAAGAACTGCTAACTCATGCCCCCATGTCTAACAACATGGCTTTCTCAACTTTTAAAGGATAACAGCTATCCATTGGTCTTAGGCCCCAAAAATTTTGGTGCAACTCCAAATAAAAGTAATAACCATGCACACTACTATAACCACCCTAACCCTGACTTCCCTAATTCCCCCCATCCTTACCACCCTCGTTAACCCTAACAAAAAAAACTCATACCCCCATTATGTAAAATCCATTGTCGCATCCACCTTTATTATCAGTCTCTTCCCCACAACAATATTCATGTGCCTAGACCAAGAAGTTATTATCTCGAACTGACACTGAGCCACAACCCAAACAACCCAGCTCTCCCTAAGCTTCAAACTAGACTACTTCTCCATAATATTCATCCCTGTAGCATTGTTCGTTACATGGTCCATCATAGAATTCTCACTGTGATATATAAACTCAGACCCAAACATTAATCAGTTCTTCAAATATCTACTCATTTTCCTAATTACCATACTAATCTTAGTTACCGCTAACAACCTATTCCAACTGTTCATCGGCTGAGAGGGCGTAGGAATTATATCCTTCTTGCTCATCAGTTGATGATACGCCCGAGCAGATGCCAACACAGCAGCCATTCAAGCAGTCCTATACAACCGTATCGGCGATATCGGTTTCATCCTCGCCTTAGCATGATTTATCCTACACTCCAACTCATGAGACCCACAACAAATAGCCCTTCTAAACGCTAATCCAAGCCTCACCCCACTACTAGGCCTCCTCCTAGCAGCAGCAGGCAAATCAGCCCAATTAGGTCTCCACCCCTGACTCCCCTCAGCCATAGAAGGCCCCACCCCAGTCTCAGCCCTACTCCACTCAAGCACTATAGTTGTAGCAGGAATCTTCTTACTCATCCGCTTCCACCCCCTAGCAGAAAATAGCCCACTAATCCAAACTCTAACACTATGCTTAGGCGCTATCACCACTCTGTTCGCAGCAGTCTGCGCCCTTACACAAAATGACATCAAAAAAATCGTAGCCTTCTCCACTTCAAGTCAACTAGGACTCATAATAGTTACAATCGGCATCAACCAACCACACCTAGCATTCCTGCACATCTGTACCCACGCCTTCTTCAAAGCCATACTATTTATGTGCTCCGGGTCCATCATCCACAACCTTAACAATGAACAAGATATTCGAAAAATAGGAGGACTACTCAAAACCATACCTCTCACTTCAACCTCCCTCACCATTGGCAGCCTAGCATTAGCAGGAATACCTTTCCTCACAGGTTTCTACTCCAAAGACCACATCATCGAAACCGCAAACATATCATACACAAACGCCTGAGCCCTATCTATTACTCTCATCGCTACCTCCCTGACAAGCGCCTATAGCACTCGAATAATTCTTCTCACCCTAACAGGTCAACCTCGCTTCCCCACCCTTACTAACATTAACGAAAATAACCCCACCCTACTAAACCCCATTAAACGCCTGGCAGCCGGAAGCCTATTCGCAGGATTTCTCATTACTAACAACATTTCCCCCGCATCCCCCTTCCAAACAACAATCCCCCTCTACCTAAAACTCACAGCCCTCGCTGTCACTTTCCTAGGACTTCTAACAGCCCTAGACCTCAACTACCTAACCAACAAACTTAAAATAAAATCCCCACTATGCACATTTTATTTCTCCAACATACTCGGATTCTACCCTAGCATCACACACCGCACAATCCCCTATCTAGGCCTTCTTACGAGCCAAAACCTGCCCCTACTCCTCCTAGACCTAACCTGACTAGAAAAGCTATTACCTAAAACAATTTCACAGCACCAAATCTCCACCTCCATCATCACCTCAACCCAAAAAGGCATAATTAAACTTTACTTCCTCTCTTTCTTCTTCCCACTCATCCTAACCCTACTCCTAATCACATAACCTATTCCCCCGAGCAATCTCAATTACAATATATACACCAACAAACAATGTTCAACCAGTAACCACTACTAATCAACGCCCATAATCATACAAAGCCCCCGCACCAATAGGATCCTCCCGAATCAACCCTGACCCCTCTCCTTCATAAATTATTCAGCTTCCTACACTATTAAAGTTTACCACAACCACCACCCCATCATACTCTTTCACCCACAGCACCAATCCTACCTCCATCGCTAACCCCACTAAAACACTCACCAAGACCTCAACCCCTGACCCCCATGCCTCAGGATACTCCTCAATAGCCATCGCTGTAGTATATCCAAAGACAACCATCATTCCCCCTAAATAAATTAAAAAAACTATTAAACCCATATAACCTCCCCCAAAATTCAGAATAATAACACACCCGACCACACCGCTAACAATCAGTACTAAACCCCCATAAATAGGAGAAGGCTTAGAAGAAAACCCCACAAACCCCATTACTAAACCCACACTCAACAGAAACAAAGCATACATCATTATTCTCGCACGGACTACAACCACGACCAATGATATGAAAAACCATCGTTGTATTTCAACTACAAGAACACCAATGACCCCAATACGCAAAATTAACCCCCTAATAAAATTAATTAACCACTCATTCATCGACCTCCCCACCCCATCCAACATCTCCGCATGATGAAACTTCGGCTCACTCCTTGGCGCCTGCCTGATCCTCCAAATCACCACAGGACTATTCCTAGCCATACACTACTCACCAGACGCCTCAACCGCCTTTTCATCAATCGCCCACATCACTCGAGACGTAAATTATGGCTGAATCATCCGCTACCTTCACGCCAATGGCGCCTCAATATTCTTTATCTGCCTCTTCCTACACATCGGGCGAGGCCTATATTACGGATCATTTCTCTACTCAGAAACCTGAAACATCGGCATTATCCTCCTGCTTGCAACTATAGCAACAGCCTTCATAGGCTATGTCCTCCCGTGAGGCCAAATATCATTCTGAGGGGCCACAGTAATTACAAACTTACTATCCGCCATCCCATACATTGGGACAGACCTAGTTCAATGAATCTGAGGAGGCTACTCAGTAGACAGTCCCACCCTCACACGATTCTTTACCTTTCACTTCATCTTACCCTTCATTATTGCAGCCCTAGCAGCACTCCACCTCCTATTCTTGCACGAAACGGGATCAAACAACCCCCTAGGAATCACCTCCCATTCCGATAAAATCACCTTCCACCCTTACTACACAATCAAAGACGCCCTCGGCTTACTTCTCTTCCTTCTCTCCTTAATGACATTAACACTATTCTCACCAGACCTCCTAGGCGACCCAGACAATTATACCCTAGCCAACCCCTTAAACACCCCTCCCCACATCAAGCCCGAATGATATTTCCTATTCGCCTACACAATTCTCCGATCCGTCCCTAACAAACTAGGAGGCGTCCTTGCCCTATTACTATCCATCCTCATCCTAGCAATAATCCCCATCCTCCATATATCCAAACAACAAAGCATAATATTTCGCCCACTAAGCCAATCACTTTATTGACTCCTAGCCGCAGACCTCCTCATTCTAACCTGAATCGGAGGACAACCAGTAAGCTACCCTTTTACCATCATTGGACAAGTAGCATCCGTACTATACTTCACAACAATCCTAATCCTAATACCAACTATCTCCCTAATTGAAAACAAAATACTCAAATGGGCCTGTCCTTGTAGTATAAACTAATACACCAGTCTTGTAAACCGGAGACGAAAACCTTTTTCCAAGGACAAATCAGAGAAAAAGTCTTTAACTCCACCATTAGCACCCAAAGCTAAGATTCTAATTTAAACTATTCTCTGTTCTTTCATGGGGAAGCAGATTTGGGTACCACCCAAGTATTGACTCACCCATCAACAACCGCTATGTATTTCGTACATTACTGCCAGCCACCATGAATATTGTACGGTACCATAAATACTTGACCACCTGTAGTACATAAAAACCCAACCCACATCAAACCCCCCCCCCCCATGCTTACAAGCAAGTACAGCAATCAACCTTCAACTATCACACATCAACTGCAACTCCAAAGCCACCCCTCACCCACTAGGATACCAACAAACCTACCCACCCTTAACAGTACATAGTACATAAAGTCATTTACCGTACATAGCACATTACAGTCAAATCCCTTCTCGTCCCCATGGATGACCCCCCTCAGATAGGGGTCCCTTGACCACCATCCTCCGTGAAATCAATATCCCGCACAAGAGTGCTACTCTCCTCGCTCCGGGCCCATAACACTTGGGGGTAGCTAAAGTGAACTGTATCCGACATCTGGTTCCTACTTCAGGGCCATAAAGCCTAAATAGCCCACACGTTCCCCTTAAATAAGACATCACGATG";
	}
	function delete1_1() {
		document.getElementById("input1").value = "FASTA";
	}

	function output1_2() {
		document.getElementById("input3").value = ">fuguMito\nGCTAGCGTAGCTTAACCAAAGCAGAGTACTGAAGATGCTAAGATGGGCCCTGAAAAGTCCCGCAGGCACAAAAGCTTGGTCCTGACTTTACTAACAACTCTGATCAAACTTACACATGCAAGTATCCGCATCCCAGTGAAaatgccccccgccccccgtcCGGAAATAGGGAGTTGGTATCAGGCACACAAATTTGTAGCCCATGACACCTAGCTTTGCCACGCCCCCAAGGGAATTCAGCAGTGATAAACATTAAGCCATAAGTGAAAACTTGACTTAGTTATGATCTAAAGAGTCGGTAAAACTCGTGCCAGCCACCGCGGTTATACGAGAGACCCAAGTTGTTAGCCAACGGCGTAAAGGGTGGTTAgaactaaaaacaacaaactgagACCGAACACCTTCAAGGCTGTTATACGCTTCCGAAGCAACGAAGAACAATAACGAAAGTAGCCTCACTAACTCGAACCCACGAAAGCTAGGACACAAACTGGGATTAGATACCCCACTATGCCTACCCCTAAACACGATATGAAACTACGTACATATCCGCCTGGTTACTACGAGCATTAGCTTAAAACCCAAAGGACTTGGCGGTGCTTTAAAACCATCTAGAGGAGCCTGTTTTAAAACCGATACTCCCCGTTCAACCTCACCCCTCCTTGTTTTAACCGCCTATATACCACCGTCGTCAGCCTACCCTGTGAAGGGCAAATAGTAGACAAAATTGGCACAGCCAAAAACGTCAGGTCGAGGTGTAGCGAATGGAGGGGGACAAAATGGGCTACATTCTCTGCCTAGAGAACACGAAAGATGTGCTGAAATGCACACCCGAAGGAGGATTTAGCAGTAAGCAAGAAATAGAGTGTCATGCTGAAACCGGCTATGAAGCGCGCACACACCGCCCGTCACTCTCCCCAAActcttaatttaaaaataactaATAAGCCAccaaaagaaaaggggaggcAAGTCGTAACATGGTAAGTGTACCGGAAGGTGCACTTGGAAAAACCGGAGCATAGCTTAACAGCTTAAAGCACCTCCCTTACACCGAGTTGACGCCCGTGCAAATCGAGCTGCCCCGACACCTAACAGCtagcccccaccccacccacaaCAAACCACTATAAATACCCCCTAAGATACTTaactaaacaaaacaaatcatttttccACCCTAGTATAGGAGATAGAAAAGGAACTAGGAGCTATAGATAAAGTACCGCAAGGGAACGctgaaagagaaatgaaataacccagtaaagtaaaaaaaagcagagattaCACCTCGTACCTTTTGCATCATGATTTAGCTAGTATAATTAGGCAAAGAGCACTTTAGTCTAACACCCCGAAACTGAATGAGCTACTCCAAGACAGCCTTTATAGGGCACATCCGTCTCTGTGGCAAAAGAGTGGAAAGAGCTTTGAGTAGAGGTGATAAACCTACCGAGTTCAGTTATAGCTGGTTGCCCGAGAACTGAGTATAAGCTCAGCCTTTTGGCTTCTTAACTCCATAACTATTTATATTAACCCGACTTTAAGAAACCAAAAGAGTTAATCAAAGGGGGTACAGCCCCTTTGATACAAGAAACAACTTTTAACAGGAGGATAAGGATCATAAAAAATCAAGGCACCGCGCTTAAGTAGGCTTAGAAGCAGCCACCACAAGAAAGCGTTAAAGCTCTAGCACATCCCTGCCACAAATACCAATAAAACACTCCTAACCCCTTCCCCTACCGGGCTTTTCTATGCTTCCATAGAAGAAATTATGCTAAAATGAGTAATAAGGGGCCGACCCCCTCCAAGCACAAGTGTACATCAGAACGAACCCCCACCGAAATTCAACGGACCCAACCAAAGAgggaaataaatattaaactcacaacaagaaaaacatttaacacttttCCGTTACCCCTACACTGGTGTGCCAAATAGGAAagactaaaagaaaaagaaggaactCGGCAAACTCAAAGCCTCGCCTGTTTACCAAAAACATCGCCTCTTGCTTCAATGAATAAGAGGTCACGCCTGCCCTGTGACTATATGTTTAACGGCCGCGGTATTTTGACCGTGCAAAGGTAGCGCAATCACTTGTCCTTTAAATGTGGACCTGTATGAATGGCATAACGAGGGCTTAGCTGTCTCCTTTCTCAAGTCAATGAACTTGATCTCCCCGTGCAGAAGCGGGGATAAAACCATAAGACGAGAAGACCCTATGGAGCTTTAGACAAAAAACAGCCCCTGTCAATAAACCCTAAATAAAGGGAATAAACCTAGTGAACCTGTTTTAATGTCTTTGGTTGGGGCGACCGCGGGGTAACAAAAAACCCCCATGTGGAATGAAAACACCCTTTTTAAACCCAAGAGTCACCACTCTAGGATACAGAACATCTGACCAATAATGATCGCCTAAAGCCGATTAACGAACCGAGTTACCCTAGGGATAACAGCGCAATCCTCTTTTAGAGTCCATATCGACAAGAGGGTTTACGACCTCGATGTTGGATCAGGACATCCTAATGGTGCAGCCGCTATTAAAGGTTCGTTTGTTCAACGATTAAAGTCCTACGTGATCTGAGTTCAGACCGGAGTAATCCAGGTCAGTTTCTATCTATGAAGTACCTTTTTCCAGTACGAAAGGACCGAAAAAGAGGGGCCAATGTACAAACAAGCCCCACTCTCACTTGCTGAATCCAGCTCAAGCAAATAAGAGAGTACAAAACtaagtcaaagaacatgacatGTTAGTGTGGCAGAGCCCGGTATTGCAAAAGCCTTAAACCCTTCGAACAGAGGTTCAACTCCTCTCCCTAACTATGACTACAATACTAATTACCCACTTAATTCACCCCCTAACCATTATTGTACCCGTTCTACTAGCCGTAGCTTTCTTAACATTAATTGAACGAAAAGTTTTAGGTTACATGCAGTTACGAAAGGGCCCCAACATCGTAGGACCTTACGGACTCCTCCAACCCATCGCCGATGGCGTTAAACTTTTCATCAAAGAACCAGTCCGACCATCTACCTCCGCTCCCATCCTATTCATTATTGCCCCTACACTAGCCCTTACTCTCGCCATAATAATATGAACACCAATGCCCCTCCCCTACCCCATCCTTGACTTAAATCTGGCCATTCTATTTGTCCTAGCTATCTCTAGCTTGGCAGTCTACTCTATTCTGGGCTCCGGATGGGCCTCCAACTCAAAATATGCCCTTATAGGATCCCTACGAGCAGTTGCACAAATAATCTCATACGAAGTAAGTCTAGGGCTAATCCTTTTATCattgattatttttacaggcAACTTTACCCTACAAACATTTAACGTCACCCAGGAAAGCATTTGACTAATCATCCCAACATGACCCCTTGCAGCAATATGATACATCTCCACGCTAGCCGAAACAAACCGAGCCCCCTTTGACCTAACAGAGGGGGAGTCCGAACTGGTATCTGGGTTCAATGTTGAATATGCAGGAGGTCCCTTTGCCCTATTTTTTCTGGCAGAATATGCCAACATCCTCTTAATAAACACGCTCTCCACAATCCTGTTCCTAGGAGCCTTACATATGCCCGCTCTTCCAGAACTAACCTCTATCAATTTAATatcaaaaacagccattttatcCCTCATCTTCCTATGAGCCCGAGCCTCCTACCCACGATTCCGATATGACCAGCTAATACACCTCACATGAAAAAACTTCCTACCACTTACATTAGCATTCATTATCTGACATCTTGCACTCCCAACTACAATAGCAGGCCTTCCCCCTCAAATATAAAAGGAACTGTGCCTGAAACAAAGGACCACTTTGATAGAGTGAATCATGAGGGTTAAATTCCCTCCAGCTCCTTAGAAAGAAGGGACTTGAACCCAACCCGGAGAGATCAAAACTCTCAGTGCTTCCACTACACCACTTTCTAGTAAAGTCAGCTAAATAAGCTTTTAGGCCCATACCCTAAAAATGGAGGTTAAAATCCCCCCTTTACAAATGAACCCTTATATTACTGCCTCCCTTTTATTTGGCCTGCTGTTAGGTACAACTATTACAACTACCAGCACACACTGACTAATTGCATGAATGGGCCTAGAAATTAACACCCTAGCTATTATCCCCCTAATAGCCCAACAACACCACCCACGAGCAATTGAAGCCACTACAAAATACTTCTTGACTCAAGCTGCCGCAGCAGCAACCCTCCTCCTCGCAGCTACAACCAACGCCTGAATAACAGGCCAATGAGAGCTACAGCAAGCTACCCACCCAGTCCCAACAACCATAATTACCATTGCATTAGCCTTAAAAATTGGACTAGCCCCACTCCACACCTGACTCCCAGAAGTAATACAAGGACTAGACCTTACAACTGGACTTATCCTGGCCACATGACAAAAAATCGCACCATTCTCCCTTCTATTACAAATTCAACCTAATAACCAAACACTCTTAATCCTCCTCGCCATCCTCTCCATACTTGTCGGCGGATGAGGGGGCCTAAATCAAACCCAAGTACGTAAAATTCTAGCCTACTCCTCCATCGCCCACCTAGGCTGAATAGTCATTATTCTTCAATTCTCACCAACCCTAGCTGTAATAACCCTAGCACTTTATATCATCAtaacctcctccaccttccttgCTTTCATGAtaaacaaaaccacaacaatTGGAACCTTAACAATCTCGTGAGCCAAAACCCCGATCATTTCATCCCTAATACCCCTAATCCTTCTGTCATTAGGAGGTTTACCCCCACTAACTGGCTTCATGCCTAAATGACTTATCCTTCAAGAACTTACAAAACAAGACCTAGGCATAACAGCCACATTAGCAGCCCTTAGCGCCCTACTAAGTCTTTATTTCTATCTACGCCTATCCTACGCTATAACCCTAACCATCTCCCCAAATAACCTAACAGGAACACTACCCTGACGAAcccagacaaataaaaaaacattaccAACCGCTATCTTATTATCCTCttccattctcctcctccccttaACCCCTGAAGTACTTACACTCTTTAACACATAAGAGACTTAGGTTAACACCAGACCAAGAGCCTTCAAAGCTCTCAGTGGAAGTGAAAATCTTTCAGTCCCTGATAAGACTTGCAAGCCATTATCTCACATCTTCTGCATGCAAAACAGACACTTTAATTAAGCTAAAGCCTTAACTAGATAGACAGGCCTCGATCCTGCAAACTCTTAGTTAACAGCTAAGCGCCCAAAACCAACGAGCTTCCATCTAACTTTCCCCGCCTAGCAAAAGCAAAAGGCGGGGAAAGCCCCGGCAGACGTCAATCTGCTTCTTTAGATATGCAATCTAACATGTAACACCCCAGGGCTGATAGAAAGAGGACTTAAACCTCTGTATATGGGGCTACAAACCACCGCTTAACCCTCAGCCATTCTACCTGTGGCAATCACACGCTGATTTTTCTCAACCAATCACAAAGATATCGGCACCCTATACCTAGTTTTTGGTGCCTGAGCCGGAATAGTAGGCACAGCACTAAGTCTTCTTATTCGGGCCGAACTCAGTCAACCCGGCGCACTCTTGGGCGATGACCAGATCTACAATGTAATCGTTACAGCCCATGCATTCGTAATGATTTTCTTTATAGTAATACCAATCATGATTGGAGGCTTTGGGAACTGATTAATCCCACTTATAATCGGAGCCCCAGACATGGCCTTCCCCCGAATGAACAACATAAGCTTCTGACTGCTTCCCCCAtccttcctccttctgctcGCATCCTCTGGAGTAGAAGCCGGAGCGGGTACGGGCTGAACTGTTTACCCACCCCTAGCAGGAAATCTTGCCCACGCAGGGGCTTCTGTAGACCTCACCATCTTCTCTCTTCATCTTGCAGGGGTCTCCTCTATTCTAGGGGCAATCAACTTCATCACAACCATCATTAACATGAAGCCCCCAGCAATCTCACAATACCAAACACCTCTTTTCGTGTGAGCCGTTTTAATTACTGCTGTACTTCTCCTGCTCTCCCTTCCAGTCCTTGCAGCAGGGATTACAATACTTCTCACTGACCGAAACCTAAATACAACCTTCTTTgacccagcaggaggaggagaccccATCTTGTACCAACACTTATTCTGATTCTTTGGACACCCTGAAGTCTACATTCTAATTCTCCCTGGCTTCGGAATAATTTCACACATCGTAGCCTACTACTCGGGCAAAAAAGAACCATTCGGCTACATGGGCATGGTCTGAGCCATAATGGCCATCGGTCTTCTTGGTTTTATTGTATGAGCCCACCACATGTTTACAGTCGGCATGGACGTAGACACCCGAGCCTACTTTACCTCTGCCACAATAATTATTGCCATCCCGACAGGAGTCAAAGTATTTAGCTGACTTGCAACCTTGCATGGAGGATCAATTAAATGAGAAACCCCTATACTATGAGCCCTCGGCTTCATCTTCCTATTTACAGTGGGTGGCCTAACCGGAATTGTCCTAGCCAATTCATCCCTAGACATCGTATTACACGACACCTACTACGTAGTTGCCCATTTCCACTACGTCCTCTCCATGGGTGCTGTATTTGCAATTATGGGTGCATTCGTACACTGATTCCCACTATTTTCAGGATACACACTCCACAGCACTTGAACTAAAATCCACTTCGGAGTAATGTTCATTGGTGTCAACCTAACCTTCTTCCCTCAACACTTCCTTGGTCTAGCTGGAATACCTCGACGATACTCCGACTACCCCGACGCCTACGCCCTATGAAACTCCGTCTCCTCAATTGGCTCAATAGTCTCTCTAGTGGCAGTAATTATGTTCCTCTTTATCCTCTGAGAAGCCTTCACCGCTAAGCGAGAAGTCCAATCAGTTGAACTAACAATGACAAATGTAGAATGACTACACGGGTGCCCTCCTCCTTACCACACATTCGAAGAGCCCGCCTTTGTCCAAACTCAAACCTCTATCCGAGAAAGGGAGGAATCGAACCCCCGTAAACTGGTTTCAAGCCAGCTACAAAACCACTCTGTCACTTTCTTCATAAGACTCTAGTAAAATGGCAATTACACTACTTTGTCAAGGTAGAATTGTGGGTTAAACCCCCACGTGTCTTATTATTAATGGCACATCCTTCACAACTAGGGTTTCAAGATGCAGCTTCACCAGTAATAGAAGAACTCCTTCACTTCCACGACCATGCTCTAATAATCGTATTCCTTATTAGCACATTAGTACTTTACATTATTGTTGCTATAGTCTCCACCAAACTAACTAACAAGTACATTCTAGACTCTCAAGAGATTGAAATTATCTGAACTATCCTACCAGCTATTATTCTTATCCTCATTGCCCTCCCTTCCCTCCGAATTCTTTACCTAATAGACGAAATCAACGACCCCCATCTAACAATTAAAGCTATAGGTCACCAATGATACTGAAGCTATGAATATACAGACTATAGCGACCTAGCCTTTGACTCATACATAGTCCCCACACAAGACCTGGCCCCCGGCCAATTCCGCCTTTTAGAAACTGACCATCGAATGGTCGTTCCAGTGGACTCCCCCATTCGAATCCTAGTCTCAGCAGAAGATGTCCTCCACTCCTGAGCCGTCCCCTCTCTAGGTGTAAAAATGGATGCCGTACCCGGCCGTCTAAACCAAACAGCCTTTATCCTGTCCCGACCTGGTGTTTTCTACGGCCAATGCTCTGAAATCTGCGGAGCTAACCACAGCTTCATACCAATCGTTGTTGAAGCCGTCCCCCTAGAACACTTTGAAAACTGATCCTCACTAATGCTCGAAGATACCTCACTAAGAAGCTAAAACGGACACAGCGTTAGCCTTTTAAGCTAAAAATGGTGCCTACCAAACACCCTTAGTGAAATGCCTCAACTCAACCCCGCACCTTGATTCCTCATTATGGTCTTCTCTTGATGTGTATTCCTAATCTTCCTCCCTCCAAAAATCATAGCTCACTTATTCCCAAATGAACCCTCCTCTCAAAACACTTGCCCCAAAGAAAtcaaaccctgaccctgatcatGACACTAAGCTTCTTCGACCAATTTCTTAGCCCCACCGCCTTTGGTATTCCCCTGATTGCCCTCGCTCTCCTGTTACCTTGGACCCTCTTCCCCACACCAACCAACCGTTGAACCAACAATCGTCTTTTAACACTTCAAAGCCAATTTATTAATCGATTTACTCAACAACTGCTCCTCCCACTAAACATAGGAGGGCACAAATGAGGCCCTTATATTGCCTCATTAATAGTGTTCCTAATTACCATCAATATGCTAGGACTCCTTCCATACACATTCACCCCCACCACACAGCTTTCCGTAAATATAGCCCTAGCTGTACCCCTATGACTCGCAACAGTAATCATCGGAATACGAAACAACCCAACAGCAGCCCTAGGCCACCTTCTTCCAGAAGGCACCCCCAACGCTCTAATCCCTATCCTAATTATTATCGAAACTGTCAGCCTCTTCATTCGACCTTTAGCACTAGGAGTTCGACTAACCGCTAACCTCACAGCAGGCCACCTGTTGATTCAACTAATCGCTACAGCTGCTTTCGTACTCCTCCCCCTTATACCAACTGTCGCCATTCTGACATCAACCCTATTATTCCTCCTGACACTTCTAGAAGTCGCCGTCGAAATAATCCAAGCCTATGTTTTCGTACTTCTTCTAAGCCTCTATCTACAAGAAAACGTTTAATGGCCCATCAAGCACACCCATACCACATAGTAGACCCAAGCCCATGACCCCTAACAGGAGCAGTCGCTGCCCTTCTCCTTACATCCGGCCTAGCCATTTGATTCCACTTTAACTCAACTATCCTAATAACCCTAGGACTAGTCCTACTTTTACTCACAATACTTCAATGATGACGAGATATCGTACGAGAAGGCACATTCCAAGGCCACCACACCCCTCCTGTCCAAAAAGGCCTTCGATACGGAATAATTCTATTTATTACCTCCGAAGTATTCTTCTTCCTTGGCTTCTTCTGAGCATTCTACCACGCAAGCCTAGCCCCCACTCCTGAATTAGGAGGCTGCTGACCCCCTACAGGCATTATCCCCTTAAATCCCTTCGAAGTTCCCCTCCTAAACACAGCAGTCCTACTGGCATCAGGAGTTACCGTAACCTGAGCTCACCACAGCATTATAGAAGGTGAGCGAAAACAAGCAATCCAATCCCTCACCCTGACAATCCTTCTAGGTTTCTACTTTACATTCCTGCAAGCAATAGAGTACTATGAAGCCCCCTTCACAATCGCAGATGGTGTTTACGGCTCAACCTTCTTCGTCGCTACCGGCTTCCACGGCCTTCACGTCATCATCGGATCAACCTTCTTAGCCGTATGTCTGCTACGACAAATCCGATTCCACTTCACCTCCGAACACCACTTTGGCTTCGAAGCAGCCGCCTGATACTGACACTTTGTAGATGTTGTCTGATTATTCTTATACATCTCAATCTACTGATGAGGCTCATAAATCTTTCTAGTATAAAGTCAGTACCTGTGACTTCCAATCACCCAGTCTTGGTTAAACTCCAAGGAAAGATAATGAACTTACTAACAACAATATTAATTATTACCACAGCTTTATCCCTCATCTTAATGACCGTCTCATTTTGACTCCCCGCCCTTACACCAGACTACCAAAAATTATCACCATATGAATGTGGCTTCGACCCCCTAGGGTCAGCCCGACTCCCCTTCTCACTACGCTTCTTCTTAGTCGCAATTCTGTTCCTCCTTTTCGACTTAGAAAtcgcccttctcctccccctcccttgaGGAGATCAGCTCCCATCCCCCACCCTAACACTTATATGGACCTCCGCCCTTCTAATTCTCCTCACAATTGGCCTAGCCTACGAATGACTCCAAGGAGGCCTTGAATGAGCCGAATAGGTAATTAGTTTAATCAAAACATTTGATTTCGGCTCAAAAAACTATGGTTAAAGTCCATAATTAACCTGATGACCCTCATCCAACTCTCATTCACCTCAATCTTCTTCCTAGGACTATTCGGCCTTGCATTTTACCGAGTCCACCTTCTATCTGCACTCTTATGTCTTGAAAGCATAATATTAGCCCTATTCCTCGCACTTTCAACATGAAGCCTGCAAATATCCTCCACCAGTTTTTCAGCCGCGCCGTTACTCCTTCTAGCATTCTCAGCATGCGAAGCCGGTGTAGGACTAGCTTTAATAGTCGCCACAGCCCGTACCCACGGATCAGATCACCTACAAAACCTAAACCTCCTACAATGCTAAAAATCCTAATCCCAACCACTATACTTATCCTAGCAACATGATTGACACCCCCTAAATGATTGTGGCCCTCTTCACTCCTCAACAGCCTCCTAATTGCTCTCACTAGCCTACTATGGCTAAAAAACGCCTCTGAAACAGGGTGAACTTTCCTCAACCCCTACTTAGCCACTGACCCACTATCAACCCCCCTTTTAATCCTCTCATGCTGACTCCTTCCTCTAATAATCCTAGCCAGCCAAAACCACACATCTCATGAACCAATTAACCGTCAACGAATGTATATCACACTTCTTGCCTCCCTGCAATTCTTCCTGATCCTTGCCTTCTCCGCCACAGAAATGATCATGTTTTACGTAATATTTGAAGCCACTCTAATCCCCACCCTGATCCTCATTACTCGCTGAGGAAACCAAGCAGAACGTCTTAACGCAGGTACATACTTCCTTTTCTATACCCTAGCAGGCTCTCTACCCCTCCTCATCGCACTACTACTCTTACAAAACTCTAATGGGTCCCTATCCCTCCTTATGCTCCCCCACTTGGGCCAACTTGAACTAACATCATATGCAGATAAGATCTGGTGGGCAGGGTGTATCCTGGCATTCCTAGTTAAAATACCCCTTTACGGAGTTCACCTTTGACTACCCAAAGCTCACGTAGAAGCCCCCATTGCAGGATCTATAGTGCTAGCCGCTGTACTACTAAAACTAGGGGGCTACGGCATAATACGAATTTTAGTCACCCTAGACCCCTTAACCAAAGAACTCAGCTACCCATTCATTGTCTTAGCCCTCTGAGGAGTGATTATAACTGGTTCCATCTGCATACGTCAAACAGACCTAAAATCATTAATTGCCTACTCATCAGTCAGCCACATAGGACTGGTAGTTGGAGGTATTCTCATCCAAACCCCCTGAGGATTCACCGGCGCGCTAATCCTCATAATTGCCCACGGATTAACATCATCCGCCTTATTCTGTCTAGCTAATACTAGCTACGAGCGCACACACAGCCGAACTATACTACTTGCTCGAGGTATGCAAATAATCCTCCCTCTCATAGCAGCCTGATGATTCATCGCAAGCCTCGCCAATTTAGCATTACCACCCCTCCCCAACTTAATAGGAGAACTAATGATTATTACCTCCCTATTCAATTGATCCCCGTGAACAATTGGCCTTACTGGACTAGGTACACTTATTACTGCCGGCTACTCGCTCTATATATTCCTCATAACCCAACGAGGGCCCGCCCCCAGCCACCTCCTAGCTCTTGAACCCTCACACACCCGAGAGCACCTTCTTAtcgccctccacctcctcccactaATCCTAATTATTACAAAACCAGAACTAATCTGAGGGTGAACTGCCTGTAGACATAGTTTAATCAAAACATTAGATTGTGATTCTAAAAACAGAGGTTAAAACCCTCTTGTCCACCGAAAGAGGTTCGCAACAATGAAGACTGCTAATCTTCATCCCCCTCGGTTAAACTCCGGGGCTCATTCGACCCAGCTTTTGAAGGATAATAGCTAATCCGTTGGTCTTAGGAACCAAAAACTCTTGGTGCAACTCCAAGTAAAAGCTATGCACTCCACCCCTCTAATTATAACATCTACCCTAATTATCATTTTTGCACTACTCATTTACCCAGTTTTAATAACCTTTTCCCCAAAACCACAAAACCCAAACTGAGCACTTATTCAAGTgaaaacagcagtaaaatacGCCTTCCTCGTCAGCCTCCTGCCCCTATGCCTCCACCTTAATGAAGGAACTGAATCTATCATCACTAACTTAAACTGAATAAACACCCTCACCTTTGACATCAACATCAGCCTTAAATTCGACTCCTACTCAATTATCTTTACCCCAGTAGCACTATACGTAACCTGATCCATTTTAGAATTTGCATCCTGATACATACACTCAGACCCATTCATAAACCGATTCTTTAAATACCTTCTGGTCTTCCTAATTGCAATAATTATTCTTGTTACCGCCAATAACATATTTCAACTCTTCATCGGCTGAGAGGGGGTTGGTATCATATCATTCCTTCTTATCGGCTGATGATACGCACGAGCAGACGCTAATACAGCCGCCCTACAAGCAGTCATCTATAATCGAGTCGGAGACATTGGATTAATTATCGCTATAGCTTGAATAGCCACCCACCTAAACTCCTGAGAACTACAACAAATTTTCTTTTCCACTAAAAACATAGACATAACCCTCCCATTAGTTGCCCTGATCTTAGCCGCAACAGGCAAATCAGCCCAATTCGGCCTTCATCCGTGACTTCCTTCTGCAATAGAAGGTCCTACACCGGTCTCTGCCCTACTCCACTCCAGCACTATAGTTGTTGCAGGAATCTTCTTAATAATCCGCATCTCCCCCCTCTTAGAAACCAACCCAACAGCCCTCACACTCTGCCTATGCCTAGGAGCCCTAACCACCCTATTTACCGCCACCTGCGCCCTAACCCAGAACGATATCaaaaaaaTTGTAGCTTTTTCAACTTCCAGTCAACTAGGCCTAATGATAGTCACCATTGGCCTAAATCAGCCCCAACTTGCCTTCCTGCACATCTGCACCCACGCTTTTTTCAAAGCCATATTATTCTTATGCTCTGGGTCTATTATTCACAGCTTAAATGATGAACAAGACATCCGCAAAATGGGAGGGATACACCACTTGACCCCTGTTACCTCTTCATGCCTAACAATTGGCAGCTTAGCCCTGACCGGAACCCCCTTCCTAGCCGGCTTCTTTTCCAAAGATGCCATCATCGAATCTTTAACCACCTCCCAATTAAACGCCTGAGCCCTATGCCTCACCCTCCTAGCAACTTCTTTCACAGCTATCTACAGCCTACGAGTCGTATTCTACGTATCCATAGGCCACCCTCGCTTTAATTCCCTTTCACCAATCAATGAAAATAACCCATCTGTAATTAACCCTATCAAACGACTGGCATGAGGCAGCATTATTGCTGGCCTATTAATCACCACAAACCTTCTCCCAACAAAAACACCTGTAATATCAATACCTATAGTTGTTAAACTTACTGCTCTTATCGTCACAATCTTGGGACTCCTAATTGCCCTAGAATTAGCTTCCTTAACCTCCAAACAACTTAAACCTACACCACACCTGTCCCCCCACCACTTCTCAAACATACTTGGCTTCTTCCCAACAATTGTACACCGTGCCTCTCCTAAAATTAATCTCATTTTAGGACAAACAATTGCTACCCAAATTATCGACCTAACCTGACTAGAAAAAGTTGGACCCAAAACAATTTCATCTATCAACACTCCCCTCATCTCTACCATCAGTAACATCCAACAAGGATCAATCAAGACATAccttgtcctcttcctcacgACCCTTGCTCTATCAACCCTCGTTCTTCTTACCTAACTGCTCGAAGAGCCCCCCGACCCAGCCCCCGCACCAGCTCTAATACTACAAGCAACGTCAATAACAAGACCCAGGCCCCCAATAGTAATACTCCCCCACCGCTAGAATATATAAGTGAAACCCCGTCCATATCACCTCGAAAAACTGCATCTCAGTCTATCTCTCCAGAAACCCCTCATCACACCTCTTCATCAAGGACCATATTTGTGTACAAGataccaaaaacaaaaaaaaaatatccaaaaaagaataaaaccacCTGTCAACCTGTAGGCGCCTTAGGATCCTTATCTGCAGACAGCGCTGacgaataaataaacacaaccaGTATACCCCCCATATAAATCATTAACAACACCAAAGACAAGAAAGTTCCCCCGTGCAAAACTGAAGCCCCACAGCAAAGAAGTGCAACCAGCACCAAATTGAAAACTCCATAAAAAGGAGCAGGATTTGTAGACAACACAATTATTACAACCAACATCcctaataaaagaaaaacaagcgcATAAAACATAGTTTCTGCCAGGATTTTAACCAGGACCTATGGCGTGAAAAACCATCGTTGTTACTCAACTACAAAAACACTAATGGCCAGCCTACGCAAAACCCACCCCCTACTAAAAATCGTAAACGACATAGTAATTGACCTTCCTACCCCCTCAAACATTTCCGCCTGATGAAACTTTGGCTCTCTACTCGGATTATGCCTTATTACACAAATCATCACAGGACTGTTCCTTGCAATACACTACACATCCGACATCTCTACCGCCTTTTCATCCGTAGCCCACATTTGCCGAGACGTAAACTACGGCTGACTAATTCGCAATCTACACGCAAACGGTGcctcattcttttttatttgcttaTACTCCCACATCGGCCGAGGTCTTTACTATGGCTCTTACCTAAGTAAAGAAACCTGAAACGTAGGGGTAGTCCTCTTACTTTTAGTAATGGCCACCGCTTTCGTAGGCTACGTTCTTCCATGAGGACAAATATCCTTCTGAGGCGCCACTGTAATTACAAACCTGCTCTCTGCTGTCCCCTACGTAGGAAACACGCTCGTTCAATGAGTATGAGGAGGCTTTTCAGTAGACAGCGCCACTCTAACACGATTCTTtgccttccacttcctcctcccatTTATCGTTGCAGCCGCTGCCATCGtacatcttatttttcttcacgAAACAGGCTCCAACAATCCCCTAGGACTCAATTCAAACGCAGACAAAATCCCATTCCACCCATACTTCTCTTACAAAGACCTCCTGGGCTTCACAATCATACTCTCAGCCCTCGCAACACTCGCCCTATTCTCTCCAAACTACCTCGGAGACCCTGACAACTTCACACCAGCCAATCCTCTAGTTACCCCCGCCCACATTAAACCAGAATGGTATTTCCTATTTGCATACGCAATTCTACGATCTATCCCCAATAAGCTAGGAGGTGTTCTGGCCCTTCTTGCCTCAATCTTAATTCTTATAGTAGTTCCTTTCTTACACACCTCTAAACAACGAAGCCTAACATTCCGCCCACTATCACAATTCCTATTCTGAACCCTAATTGCCGACGTCGTCATCCTAACCTGAATTGGAGGCATGCCCGTCGAACATCCTTACATTATTATCGGACAAATTGCCTCAGTACTTTACTTCTCTCTCTTCCTAATCTTGATGCCAATAGCCGGTTGACTAGAAAATAAAATACTAAACTAACAAGCATTAGTAGCTCAGATTCAGAGCGTCGGTCTTGTAAACCGAATGTCGGGGGTTAAAATCCCCCCTTATGCTCAAAAAGAAGGGACTTCAACCCCCACCACTGGCTCCCAAAGCCAGCATTCTTAATTAAACTACTTTTTGataatacatatatgtattatccccattcatatatattaaacattaatataatgcataattaaGACATAGTACTATATATTCACCTATAGTTCCTATAACCCATAAAGCAAGTACAGGAagctaaaaatgctaaaagcataactggaaaaatccctaaaaattgttcaaaaactgaacgaaatTTAAGACCGAACAATAAACTCATCAGTTAAGATATACCAGGACTCAACACCCCGTAAAATACCAATTATTAATGTAGTAAGAACCGACCATCAGTTGATTTCTTAATGCATATTATTATTGAAGGTGAGGGACAATAACAGTGGGGGTTTCACTAAATGAACTATTCCTGGCATTTGGTTCCTACTTCAGGGCCATTAATCGATTTATTCCTCATTCTTTCATCGACGCTGACATAAGTTGTTGGTGGAGTTCATCAGTGAGATAATCCCACATGCCGGGCGTTCTCTCCACAGGGGTCAGgttattttttctctctttcctttcaaTTGACATTTCAGAGTGCAGCGCGTCAATGGTTCATCAAGGTTGAACATTTTTTCTTGGTTTATGGTAATGTTAATTAATGAATTAAGACATTATTTAAGAATTACATTACTGATATCAAGGACATAAATAATAATACGATTCAACAATCATACAATTTCAcccccttcttctttttaaaaaaattaacgtataccccccctaccccccctaAAAAATAGGAGAGACCTTTAAGTTTGAACCAAGCTCTCCActtaattaaatattcatcatattattatcatatattataatattataataatataattatat";
	}
	function delete1_2() {
		document.getElementById("input3").value = "FASTA";
	}

	function output2() {
		document.getElementById("input_blast").value = ">ENSP00000216117\nMERPQPDSMPQDLSEALKEATKEVHTQAENAEFMRNFQKGQVTRDGFKLVMASLYHIYVALEEEIERNKESPVFAPVYFPEELHRKAALEQDLAFWYGPRWQEVIPYTPAMQRYVKRLHEVGRTEPELLVAHAYTRYLGDLSGGQVLKKIAQKALDLPSSGEGLAFFTFPNIASATKFKQLYRSRMNSLEMTPAVRQRVIEEAKTAFLLNIQLFEELQELLTHDTKDQSPSRAPGLRQRASNKVQDSAPVETPRGKPPLNTRSQAPLLRWVLTLSFLVATVAVGLYAM";
	}
	function delete2() {
		document.getElementById("input_blast").value = "FASTA";
	}

	function output3() {
		document.getElementById("input_interpro").value = ">Aquca_023_00143.1 pacid=22022986 transcript=Aquca_023_00143.1 locus=Aquca_023_00143 ID=Aquca_023_00143.1.v1.1 annot-version=v1.1\nMPATDYQGSSVPFGALGRSIMSIRRDQVHNMEGNHDSSVQEAELELFQKQVTERFQSLVSCNEELLSLPWIRHLLDEFICCQEEFRAILFNNKSHLSRPPMDRFIGEYFERSVKSLDVCNAIRDGIELIRQWEKYLEIVLTALDSNQRTLGEGQFRRAKKALTDLTIAMLDEKDSTAVIGHRNRSFGRNTTNKDSRPHGHFRSLSWSVSRSWSAAKQLQAISNNLTPPRGNEVMATNGLAVAVFTMSSILLFVMWTLVAAIPCQDRGLQMHFSIPRNFSWGASIMSLHDRIMEESKKRDRRNASGLMKEIHQIEKCARHMTELADSVQFPLTEEKEADVKQRVKELAEVCETMKDGLDGLDPLERRVREVFHRILRNRTEGLDSLSRPNHPE";
	}
	function delete3() {
		document.getElementById("input_interpro").value = "FASTA";
	}

	function output4() {
		document.getElementById("input_clustalomega").value = ">AT5G53360.1 | Symbols:  | TRAF-like superfamily protein | chr5:21648485-21649269 FORWARD LENGTH=233\nMDILYVQPVNQECIIALEKVAESLELPCKYYNLGCLGIFPYYSKLKHESQCNFRPYSCPYAGSECAAVGDITFLVAHLRDDHKVDMHTGCTFNHRYVKSNPREVENATWMLTVFQCFGQYFCLHFEAFQLGMAPVYMAFLRFMGDEDDARNYTYSLEVGGSGRKQTWEGTPRSVRDSHRKVRDSHDGLIIQRNMALFFSGGDKKELKLRVTGRIWKEQQNPDSGVCITSMCSS\n>AT5G37890.1 | Symbols:  | Protein with RING/U-box and TRAF-like domains | chr5:15090512-15091822 REVERSE LENGTH=286\nMVGAAILESPGEGIGSNSILSQKRQLSSSDAAKRDAKKRSTMLMDLEILDCPICYEAFTIPIFQCDNGHLACSSCCPKLNNKCPACTSPVGHNRCRAMESVLESILIPCPNAKLGCKKNVSYGKELTHEKECMFSHCACPALDCNYTSSYKDLYTHYRITHMEINQINTFICDIPLSVRMNISKKILIRTEHLTNHLFAVQCFREPYGVYVTVSCIAPSSPELSQYSYALSYTVDGHTVIYQSPEVKRVLKLSFQTPQENFMLIPNSLLRGDVLEMRISVKKLNKE\n>AT1G66630.1 | Symbols:  | Protein with RING/U-box and TRAF-like domains | chr1:24855479-24856714 REVERSE LENGTH=303\nMENITNNSERSLDRPKRQRPVSMENVGGTASGSEVARSATLLELDLLDCPICYHKLGAPIYQCDNGHIACSSCCKKVKYKCPYCSLRIGFFRSRILEKIVEAVVVSCPNAKYGCTEKIPYDNESESAHERVCEFTLCYCPEPECKYTGVYTDLYRHYHAEHKTDHSWFKCGEYNNAWLHVTGEKLSFLVLQEYEDGPLVVVQCSMESHGICVTVNCIAPCAPGVGEFSCHLIYRNGSEKITFESKKMNKIQKVSPENHVANYKPIPYYLRGEASNFMSIPYYLLDEASILKMQICIRRSGEEV\n>AT3G58040.1 | Symbols: SINAT2 | seven in absentia of Arabidopsis 2 | chr3:21489612-21491085 FORWARD LENGTH=308\nMAPGGSALKEVMESNSTGMDYEVKTAKVEVNNNKPTKPGSAGIGKYGIHSNNGVYELLECPVCTNLMYPPIHQCPNGHTLCSNCKLRVQNTCPTCRYELGNIRCLALEKVAESLEVPCRYQNLGCHDIFPYYSKLKHEQHCRFRPYTCPYAGSECSVTGDIPTLVVHLKDDHKVDMHDGCTFNHRYVKSNPHEVENATWMLTVFNCFGRQFCLHFEAFQLGMAPVYMAFLRFMGDENEAKKFSYSLEVGAHGRKLTWQGIPRSIRDSHRKVRDSQDGLIIPRNLALYFSGGDRQELKLRVTGRIWKEE\n>Type3|AT3G22170.1      Plant   [Arabidopsis thaliana]  839\nMDIDLRLHSGDLCKGDDEDRGLDNVLHNEEDMDIGKIEDVSVEVNTDDSVGMGVPTGELVEYTEGMNLEPLNGMEFESHGEAYSFYQEYSRAMGFNTAIQNSRRSKTTREFIDAKFACSRYGTKREYDKSFNRPRARQSKQDPENMAGRRTCAKTDCKASMHVKRRPDGKWVIHSFVREHNHELLPAQAVSEQTRKIYAAMAKQFAEYKTVISLKSDSKSSFEKGRTLSVETGDFKILLDFLSRMQSLNSNFFYAVDLGDDQRVKNVFWVDAKSRHNYGSFCDVVSLDTTYVRNKYKMPLAIFVGVNQHYQYMVLGCALISDESAATYSWLMETWLRAIGGQAPKVLITELDVVMNSIVPEIFPNTRHCLFLWHVLMKVSENLGQVVKQHDNFMPKFEKCIYKSGKDEDFARKWYKNLARFGLKDDQWMISLYEDRKKWAPTYMTDVLLAGMSTSQRADSINAFFDKYMHKKTSVQEFVKVYDTVLQDRCEEEAKADSEMWNKQPAMKSPSPFEKSVSEVYTPAVFKKFQIEVLGAIACSPREENRDATCSTFRVQDFENNQDFMVTWNQTKAEVSCICRLFEYKGYLCRHTLNVLQCCHLSSIPSQYILKRWTKDAKSRHFSGEPQQLQTRLLRYNDLCERALKLNEEASLSQESYNIAFLAIEGAIGNCAGINTSGRSLPDVVTSPTQGLISVEEDNHSRSAGKTSKKKNPTKKRKVNPEQDVMPVAAPESLQQMDKLSPRTVGIESYYGTQQSVQGMVQLNLMGPTRDNFYGNQQTMQGLRQLNSIAPSYDSYYGPQQGIHGQGVDFFRPANFSYDIRDDPNVRTTQLHEDASRHS\n>Type3|AT5G28530.1      Plant   [Arabidopsis thaliana]  685\nMALKPLNNIWIRRQQCPCGDWKCYIRLEEDESTITKSEIESTPTPTSQYDTVFTPYVGQIFTTDDEAFEYYSTFARKSGFSIRKARSTESQNLGVYRRDFVCYRSGFNQPRKKANVEHPRERKSVRCGCDGKLYLTKEVVDGVSHWYVSQFSNVHNHELLEDDQVRLLPAYRKIQQSDQERILLLSKAGFPVNRIVKLLELEKGVVSGQLPFIEKDVRNFVRACKKSVQENDAFMTEKRESDTLELLECCKGLAERDMDFVYDCTSDENQKVENIAWAYGDSVRGYSLFGDVVVFDTSYRSVPYGLLLGVFFGIDNNGKAMLLGCVLLQDESCRSFTWALQTFVRFMRGRHPQTILTDIDTGLKDAIGREMPNTNHVVFMSHIVSKLASWFSQTLGSHYEEFRAGFDMLCRAGNVDEFEQQWDLLVTRFGLVPDRHAALLYSCRASWLPCCIREHFVAQTMTSEFNLSIDSFLKRVVDGATCMQLLLEESALQVSAAASLAKQILPRFTYPSLKTCMPMEDHARGILTPYAFSVLQNEMVLSVQYAVAEMANGPFIVHHYKKMEGECCVIWNPENEEIQCSCKEFEHSGILCRHTLRVLTVKNCFHIPEQYFLLRWRQESPHVATENQNGQGIGDDSAQTFHSLTETLLTESMISKDRLDYANQELSLLIDRVRNTAPANCLYQP\n>Type3|AT2G32250.1      Plant   [Arabidopsis thaliana]  807\nMDDEDVEIDLLTKSSNVDVFCEASTSGNVAQCATVSELRNGMDFESKEAAYYFYREYARSVGFGITIKASRRSKRSGKFIDVKIACSRFGTKREKATAINPRSCPKTGCKAGLHMKRKEDEKWVIYNFVKEHNHEICPDDFYVSVRGKNKPAGALAIKKGLQLALEEEDLKLLLEHFMEMQDKQPGFFYAVDFDSDKRVRNVFWLDAKAKHDYCSFSDVVLFDTFYVRNGYRIPFAPFIGVSHHRQYVLLGCALIGEVSESTYSWLFRTWLKAVGGQAPGVMITDQDKLLSDIVVEVFPDVRHIFCLWSVLSKISEMLNPFVSQDDGFMESFGNCVASSWTDEHFERRWSNMIGKFELNENEWVQLLFRDRKKWVPHYFHGICLAGLSGPERSGSIASHFDKYMNSEATFKDFFELYMKFLQYRCDVEAKDDLEYQSKQPTLRSSLAFEKQLSLIYTDAAFKKFQAEVPGVVSCQLQKEREDGTTAIFRIEDFEERQNFFVALNNELLDACCSCHLFEYQGFLCKHAILVLQSADVSRVPSQYILKRWSKKGNNKEDKNDKCATIDNRMARFDDLCRRFVKLGVVASLSDEACKTALKLLEETVKHCVSMDNSSKFPSEPDKLMTGGSIGLENEGVLDCASKVSKKKKIQKKRKVYCGPEDATNRSEELRQETEQVSSRAPTFENCYIPQANMEEPELGSRATTLGVYYSTQQTNQGFPSISSIQNGYYGHPPTIQAMGNLHSIHERMSQYETQPSMQGAFQGQTGFRGSAIRGCYDIEETLHDMTMGSSQFQGSDSSHPSDHRLSN\n>Type3|AT2G27110.1      Plant   [Arabidopsis thaliana]  851\nMDVHLVEENVSMGNHEIGDEGDVEPSDCSGQNNMDNSLGVQDEIGIAEPCVGMEFNSEKEAKSFYDEYSRQLGFTSKLLPRTDGSVSVREFVCSSSSKRSKRRLSESCDAMVRIELQGHEKWVVTKFVKEHTHGLASSNMLHCLRPRRHFANSEKSSYQEGVNVPSGMMYVSMDANSRGARNASMATNTKRTIGRDAHNLLEYFKRMQAENPGFFYAVQLDEDNQMSNVFWADSRSRVAYTHFGDTVTLDTRYRCNQFRVPFAPFTGVNHHGQAILFGCALILDESDTSFIWLFKTFLTAMRDQPPVSLVTDQDRAIQIAAGQVFPGARHCINKWDVLREGQEKLAHVCLAYPSFQVELYNCINFTETIEEFESSWSSVIDKYDLGRHEWLNSLYNARAQWVPVYFRDSFFAAVFPSQGYSGSFFDGYVNQQTTLPMFFRLYERAMESWFEMEIEADLDTVNTPPVLKTPSPMENQAANLFTRKIFGKFQEELVETFAHTANRIEDDGTTSTFRVANFENDNKAYIVTFCYPEMRANCSCQMFEHSGILCRHVLTVFTVTNILTLPPHYILRRWTRNAKSMVELDEHVSENGHDSSIHRYNHLCREAIKYAEEGAITAEAYNIALGQLREGGKKVSVVRKRIGRAAPPSSHGGGIGSGDKTSLSAADTTPLLWPRQDEMIRRFNLNDGGARAQSVSDLNLPRMAPVSLHRDDTAPENMVALPCLKSLTWGMESKNTMPGGRVAVINLKLHDYRKFPSADMDVKFQLSSVTLEPMLRSMAYISEQLSSPANRVAVINLKLQDTETTTGESEVKFQVSRDTLGAMLRSMAYIREQLSIVGELQTESQAKKQRK\n>Type3|AT1G10240.1      Plant   [Arabidopsis thaliana]  680\nMSDDPGQMLLIYDDPSDQRSLSLDDASSTEESPDDNNLSLEAVHNAIPYLGQIFLTHDTAYEFYSTFAKRCGFSIRRHRTEGKDGVGKGLTRRYFVCHRAGNTPIKTLSEGKPQRNRRSSRCGCQAYLRISKLTELGSTEWRVTGFANHHNHELLEPNQVRFLPAYRSISDADKSRILMFSKTGISVQQMMRLLELEKCVEPGFLPFTEKDVRNLLQSFKKLDPEDENIDFLRMCQSIKEKDPNFKFEFTLDANDKLENIAWSYASSIQSYELFGDAVVFDTTHRLSAVEMPLGIWVGVNNYGVPCFFGCVLLRDENLRSWSWALQAFTGFMNGKAPQTILTDHNMCLKEAIAGEMPATKHALCIWMVVGKFPSWFNAGLGERYNDWKAEFYRLYHLESVEEFELGWRDMVNSFGLHTNRHINNLYASRSLWSLPYLRSHFLAGMTLTGRSKAINAFIQRFLSAQTRLAHFVEQVAVVVDFKDQATEQQTMQQNLQNISLKTGAPMESHAASVLTPFAFSKLQEQLVLAAHYASFQMDEGYLVRHHTKLDGGRKVYWVPQEGIISCSCQLFEFSGFLCRHALRVLSTGNCFQVPDRYLPLRWRRISTSFSKTFRSNAEDHGERVQLLQNLVSTLVSESAKSKERLDIATEQTSILLSRIREQPVSSLAIRDISSSVQRNF\n>Type3|AT1G80010.1      Plant   [Arabidopsis thaliana]  725\nMIGNSVYISPSPSPSDHSLSPNPNLCISAMEEQLVVDDDDDDLAPPPIPDLDIDLEDDDDACCHGLLHIAPNHEEETGCDENAFANEKCLMAPPPTPGMEFESYDDAYSFYNSYARELGFAIRVKSSWTKRNSKEKRGAVLCCNCQGFKLLKDAHSRRKETRTGCQAMIRLRLIHFDRWKVDQVKLDHNHSFDPQRAHNSKSHKKSSSSASPATKTNPEPPPHVQVRTIKLYRTLALDTPPALGTSLSSGETSDLSLDHFQSSRRLELRGGFRALQDFFFQIQLSSPNFLYLMDLADDGSLRNVFWIDARARAAYSHFGDVLLFDTTCLSNAYELPLVAFVGINHHGDTILLGCGLLADQSFETYVWLFRAWLTCMLGRPPQIFITEQCKAMRTAVSEVFPRAHHRLSLTHVLHNICQSVVQLQDSDLFPMALNRVVYGCLKVEEFETAWEEMIIRFGMTNNETIRDMFQDRELWAPVYLKDTFLAGALTFPLGNVAAPFIFSGYVHENTSLREFLEGYESFLDKKYTREALCDSESLKLIPKLKTTHPYESQMAKVFTMEIFRRFQDEVSAMSSCFGVTQVHSNGSASSYVVKEREGDKVRDFEVIYETSAAAQVRCFCVCGGFSFNGYQCRHVLLLLSHNGLQEVPPQYILQRWRKDVKRLYVAEFGSGRVDIMNPDQWYEHLHRRAMQVVEQGMRSKEHCRAAWEAFRECANKVQFVTEKPS";
	}
	function delete4() {
		document.getElementById("input_clustalomega").value = "FASTA";
	}

	function output5() {
		document.getElementById("input_muscle").value = ">AT5G53360.1 | Symbols:  | TRAF-like superfamily protein | chr5:21648485-21649269 FORWARD LENGTH=233\nMDILYVQPVNQECIIALEKVAESLELPCKYYNLGCLGIFPYYSKLKHESQCNFRPYSCPYAGSECAAVGDITFLVAHLRDDHKVDMHTGCTFNHRYVKSNPREVENATWMLTVFQCFGQYFCLHFEAFQLGMAPVYMAFLRFMGDEDDARNYTYSLEVGGSGRKQTWEGTPRSVRDSHRKVRDSHDGLIIQRNMALFFSGGDKKELKLRVTGRIWKEQQNPDSGVCITSMCSS\n>AT5G37890.1 | Symbols:  | Protein with RING/U-box and TRAF-like domains | chr5:15090512-15091822 REVERSE LENGTH=286\nMVGAAILESPGEGIGSNSILSQKRQLSSSDAAKRDAKKRSTMLMDLEILDCPICYEAFTIPIFQCDNGHLACSSCCPKLNNKCPACTSPVGHNRCRAMESVLESILIPCPNAKLGCKKNVSYGKELTHEKECMFSHCACPALDCNYTSSYKDLYTHYRITHMEINQINTFICDIPLSVRMNISKKILIRTEHLTNHLFAVQCFREPYGVYVTVSCIAPSSPELSQYSYALSYTVDGHTVIYQSPEVKRVLKLSFQTPQENFMLIPNSLLRGDVLEMRISVKKLNKE\n>AT1G66630.1 | Symbols:  | Protein with RING/U-box and TRAF-like domains | chr1:24855479-24856714 REVERSE LENGTH=303\nMENITNNSERSLDRPKRQRPVSMENVGGTASGSEVARSATLLELDLLDCPICYHKLGAPIYQCDNGHIACSSCCKKVKYKCPYCSLRIGFFRSRILEKIVEAVVVSCPNAKYGCTEKIPYDNESESAHERVCEFTLCYCPEPECKYTGVYTDLYRHYHAEHKTDHSWFKCGEYNNAWLHVTGEKLSFLVLQEYEDGPLVVVQCSMESHGICVTVNCIAPCAPGVGEFSCHLIYRNGSEKITFESKKMNKIQKVSPENHVANYKPIPYYLRGEASNFMSIPYYLLDEASILKMQICIRRSGEEV\n>AT3G58040.1 | Symbols: SINAT2 | seven in absentia of Arabidopsis 2 | chr3:21489612-21491085 FORWARD LENGTH=308\nMAPGGSALKEVMESNSTGMDYEVKTAKVEVNNNKPTKPGSAGIGKYGIHSNNGVYELLECPVCTNLMYPPIHQCPNGHTLCSNCKLRVQNTCPTCRYELGNIRCLALEKVAESLEVPCRYQNLGCHDIFPYYSKLKHEQHCRFRPYTCPYAGSECSVTGDIPTLVVHLKDDHKVDMHDGCTFNHRYVKSNPHEVENATWMLTVFNCFGRQFCLHFEAFQLGMAPVYMAFLRFMGDENEAKKFSYSLEVGAHGRKLTWQGIPRSIRDSHRKVRDSQDGLIIPRNLALYFSGGDRQELKLRVTGRIWKEE\n>Type3|AT3G22170.1      Plant   [Arabidopsis thaliana]  839\nMDIDLRLHSGDLCKGDDEDRGLDNVLHNEEDMDIGKIEDVSVEVNTDDSVGMGVPTGELVEYTEGMNLEPLNGMEFESHGEAYSFYQEYSRAMGFNTAIQNSRRSKTTREFIDAKFACSRYGTKREYDKSFNRPRARQSKQDPENMAGRRTCAKTDCKASMHVKRRPDGKWVIHSFVREHNHELLPAQAVSEQTRKIYAAMAKQFAEYKTVISLKSDSKSSFEKGRTLSVETGDFKILLDFLSRMQSLNSNFFYAVDLGDDQRVKNVFWVDAKSRHNYGSFCDVVSLDTTYVRNKYKMPLAIFVGVNQHYQYMVLGCALISDESAATYSWLMETWLRAIGGQAPKVLITELDVVMNSIVPEIFPNTRHCLFLWHVLMKVSENLGQVVKQHDNFMPKFEKCIYKSGKDEDFARKWYKNLARFGLKDDQWMISLYEDRKKWAPTYMTDVLLAGMSTSQRADSINAFFDKYMHKKTSVQEFVKVYDTVLQDRCEEEAKADSEMWNKQPAMKSPSPFEKSVSEVYTPAVFKKFQIEVLGAIACSPREENRDATCSTFRVQDFENNQDFMVTWNQTKAEVSCICRLFEYKGYLCRHTLNVLQCCHLSSIPSQYILKRWTKDAKSRHFSGEPQQLQTRLLRYNDLCERALKLNEEASLSQESYNIAFLAIEGAIGNCAGINTSGRSLPDVVTSPTQGLISVEEDNHSRSAGKTSKKKNPTKKRKVNPEQDVMPVAAPESLQQMDKLSPRTVGIESYYGTQQSVQGMVQLNLMGPTRDNFYGNQQTMQGLRQLNSIAPSYDSYYGPQQGIHGQGVDFFRPANFSYDIRDDPNVRTTQLHEDASRHS\n>Type3|AT5G28530.1      Plant   [Arabidopsis thaliana]  685\nMALKPLNNIWIRRQQCPCGDWKCYIRLEEDESTITKSEIESTPTPTSQYDTVFTPYVGQIFTTDDEAFEYYSTFARKSGFSIRKARSTESQNLGVYRRDFVCYRSGFNQPRKKANVEHPRERKSVRCGCDGKLYLTKEVVDGVSHWYVSQFSNVHNHELLEDDQVRLLPAYRKIQQSDQERILLLSKAGFPVNRIVKLLELEKGVVSGQLPFIEKDVRNFVRACKKSVQENDAFMTEKRESDTLELLECCKGLAERDMDFVYDCTSDENQKVENIAWAYGDSVRGYSLFGDVVVFDTSYRSVPYGLLLGVFFGIDNNGKAMLLGCVLLQDESCRSFTWALQTFVRFMRGRHPQTILTDIDTGLKDAIGREMPNTNHVVFMSHIVSKLASWFSQTLGSHYEEFRAGFDMLCRAGNVDEFEQQWDLLVTRFGLVPDRHAALLYSCRASWLPCCIREHFVAQTMTSEFNLSIDSFLKRVVDGATCMQLLLEESALQVSAAASLAKQILPRFTYPSLKTCMPMEDHARGILTPYAFSVLQNEMVLSVQYAVAEMANGPFIVHHYKKMEGECCVIWNPENEEIQCSCKEFEHSGILCRHTLRVLTVKNCFHIPEQYFLLRWRQESPHVATENQNGQGIGDDSAQTFHSLTETLLTESMISKDRLDYANQELSLLIDRVRNTAPANCLYQP\n>Type3|AT2G32250.1      Plant   [Arabidopsis thaliana]  807\nMDDEDVEIDLLTKSSNVDVFCEASTSGNVAQCATVSELRNGMDFESKEAAYYFYREYARSVGFGITIKASRRSKRSGKFIDVKIACSRFGTKREKATAINPRSCPKTGCKAGLHMKRKEDEKWVIYNFVKEHNHEICPDDFYVSVRGKNKPAGALAIKKGLQLALEEEDLKLLLEHFMEMQDKQPGFFYAVDFDSDKRVRNVFWLDAKAKHDYCSFSDVVLFDTFYVRNGYRIPFAPFIGVSHHRQYVLLGCALIGEVSESTYSWLFRTWLKAVGGQAPGVMITDQDKLLSDIVVEVFPDVRHIFCLWSVLSKISEMLNPFVSQDDGFMESFGNCVASSWTDEHFERRWSNMIGKFELNENEWVQLLFRDRKKWVPHYFHGICLAGLSGPERSGSIASHFDKYMNSEATFKDFFELYMKFLQYRCDVEAKDDLEYQSKQPTLRSSLAFEKQLSLIYTDAAFKKFQAEVPGVVSCQLQKEREDGTTAIFRIEDFEERQNFFVALNNELLDACCSCHLFEYQGFLCKHAILVLQSADVSRVPSQYILKRWSKKGNNKEDKNDKCATIDNRMARFDDLCRRFVKLGVVASLSDEACKTALKLLEETVKHCVSMDNSSKFPSEPDKLMTGGSIGLENEGVLDCASKVSKKKKIQKKRKVYCGPEDATNRSEELRQETEQVSSRAPTFENCYIPQANMEEPELGSRATTLGVYYSTQQTNQGFPSISSIQNGYYGHPPTIQAMGNLHSIHERMSQYETQPSMQGAFQGQTGFRGSAIRGCYDIEETLHDMTMGSSQFQGSDSSHPSDHRLSN\n>Type3|AT2G27110.1      Plant   [Arabidopsis thaliana]  851\nMDVHLVEENVSMGNHEIGDEGDVEPSDCSGQNNMDNSLGVQDEIGIAEPCVGMEFNSEKEAKSFYDEYSRQLGFTSKLLPRTDGSVSVREFVCSSSSKRSKRRLSESCDAMVRIELQGHEKWVVTKFVKEHTHGLASSNMLHCLRPRRHFANSEKSSYQEGVNVPSGMMYVSMDANSRGARNASMATNTKRTIGRDAHNLLEYFKRMQAENPGFFYAVQLDEDNQMSNVFWADSRSRVAYTHFGDTVTLDTRYRCNQFRVPFAPFTGVNHHGQAILFGCALILDESDTSFIWLFKTFLTAMRDQPPVSLVTDQDRAIQIAAGQVFPGARHCINKWDVLREGQEKLAHVCLAYPSFQVELYNCINFTETIEEFESSWSSVIDKYDLGRHEWLNSLYNARAQWVPVYFRDSFFAAVFPSQGYSGSFFDGYVNQQTTLPMFFRLYERAMESWFEMEIEADLDTVNTPPVLKTPSPMENQAANLFTRKIFGKFQEELVETFAHTANRIEDDGTTSTFRVANFENDNKAYIVTFCYPEMRANCSCQMFEHSGILCRHVLTVFTVTNILTLPPHYILRRWTRNAKSMVELDEHVSENGHDSSIHRYNHLCREAIKYAEEGAITAEAYNIALGQLREGGKKVSVVRKRIGRAAPPSSHGGGIGSGDKTSLSAADTTPLLWPRQDEMIRRFNLNDGGARAQSVSDLNLPRMAPVSLHRDDTAPENMVALPCLKSLTWGMESKNTMPGGRVAVINLKLHDYRKFPSADMDVKFQLSSVTLEPMLRSMAYISEQLSSPANRVAVINLKLQDTETTTGESEVKFQVSRDTLGAMLRSMAYIREQLSIVGELQTESQAKKQRK\n>Type3|AT1G10240.1      Plant   [Arabidopsis thaliana]  680\nMSDDPGQMLLIYDDPSDQRSLSLDDASSTEESPDDNNLSLEAVHNAIPYLGQIFLTHDTAYEFYSTFAKRCGFSIRRHRTEGKDGVGKGLTRRYFVCHRAGNTPIKTLSEGKPQRNRRSSRCGCQAYLRISKLTELGSTEWRVTGFANHHNHELLEPNQVRFLPAYRSISDADKSRILMFSKTGISVQQMMRLLELEKCVEPGFLPFTEKDVRNLLQSFKKLDPEDENIDFLRMCQSIKEKDPNFKFEFTLDANDKLENIAWSYASSIQSYELFGDAVVFDTTHRLSAVEMPLGIWVGVNNYGVPCFFGCVLLRDENLRSWSWALQAFTGFMNGKAPQTILTDHNMCLKEAIAGEMPATKHALCIWMVVGKFPSWFNAGLGERYNDWKAEFYRLYHLESVEEFELGWRDMVNSFGLHTNRHINNLYASRSLWSLPYLRSHFLAGMTLTGRSKAINAFIQRFLSAQTRLAHFVEQVAVVVDFKDQATEQQTMQQNLQNISLKTGAPMESHAASVLTPFAFSKLQEQLVLAAHYASFQMDEGYLVRHHTKLDGGRKVYWVPQEGIISCSCQLFEFSGFLCRHALRVLSTGNCFQVPDRYLPLRWRRISTSFSKTFRSNAEDHGERVQLLQNLVSTLVSESAKSKERLDIATEQTSILLSRIREQPVSSLAIRDISSSVQRNF\n>Type3|AT1G80010.1      Plant   [Arabidopsis thaliana]  725\nMIGNSVYISPSPSPSDHSLSPNPNLCISAMEEQLVVDDDDDDLAPPPIPDLDIDLEDDDDACCHGLLHIAPNHEEETGCDENAFANEKCLMAPPPTPGMEFESYDDAYSFYNSYARELGFAIRVKSSWTKRNSKEKRGAVLCCNCQGFKLLKDAHSRRKETRTGCQAMIRLRLIHFDRWKVDQVKLDHNHSFDPQRAHNSKSHKKSSSSASPATKTNPEPPPHVQVRTIKLYRTLALDTPPALGTSLSSGETSDLSLDHFQSSRRLELRGGFRALQDFFFQIQLSSPNFLYLMDLADDGSLRNVFWIDARARAAYSHFGDVLLFDTTCLSNAYELPLVAFVGINHHGDTILLGCGLLADQSFETYVWLFRAWLTCMLGRPPQIFITEQCKAMRTAVSEVFPRAHHRLSLTHVLHNICQSVVQLQDSDLFPMALNRVVYGCLKVEEFETAWEEMIIRFGMTNNETIRDMFQDRELWAPVYLKDTFLAGALTFPLGNVAAPFIFSGYVHENTSLREFLEGYESFLDKKYTREALCDSESLKLIPKLKTTHPYESQMAKVFTMEIFRRFQDEVSAMSSCFGVTQVHSNGSASSYVVKEREGDKVRDFEVIYETSAAAQVRCFCVCGGFSFNGYQCRHVLLLLSHNGLQEVPPQYILQRWRKDVKRLYVAEFGSGRVDIMNPDQWYEHLHRRAMQVVEQGMRSKEHCRAAWEAFRECANKVQFVTEKPS";
	}
	function delete5() {
		document.getElementById("input_muscle").value = "FASTA";
	}

	function output6() {
		document.getElementById("newick").value = "(Phrynomantis_annectens_GB:0.02179898,(Phrynomantis_bifasciatus_GB:0.00255928,(Phrynomantis:1.0E-8,(Phrynomantis_microps_GB:0.00801643,Kaloula_pulchra_RdS_1200:0.0016947):3.4862E-4):0.00260154):0.05279985,(((((Kalophrynus_interlineatus_GB:0.00855247,Kalophrynus_pleurostigma_AK_1097:1.0E-8):0.00386018,Kalophrynus_interlineatus_USFS_34285:0.00720558):0.01927252,Kalophrynus_pleurostigma_GB:0.02673547):0.09653447,(((Synapturanus_MW_1004:0.01049649,Synapturanus_salseri_55:0.01777387):0.00769301,Synapturanus_mirandaribeiroi_SMNS_12078:0.00379453):0.07491132,(Otophryne_steyermarki_ROM_39677:0.11605342,(Otophryne_pyburni_GB:0.00817966,Otophryne_robusta_ROM_39679:0.01453495):0.00650537):0.04391901):0.02796312):0.00529357,(((Gastrophrynoides_immaculatus_GB:0.08435684,((Oreophryne_brachypus_GB:0.06764665,((Oreophryne_9731:0.02588912,Oreophryne_anulata_H_1366:0.02049606):0.01183727,Oreophryne_2289:0.03719342):0.00631442):0.00973818,(Aphantophryne_pansa_GB:0.02930301,((Cophixalus_sp_CCA_476L:0.02672845,(Cophixalus_7741:0.10419693,Cophixalus_GB2:0.0188221):0.03328761):0.01351682,((((Austrochaperina_derongo_2293:1.0E-8,Copiula_sp_GB:0.05526954):0.01373083,(Copiula_sp_3235:0.01624485,Copiula_oxyrhina_2721:0.01721637):0.01923231):0.00898059,((Liophryne_schlaginhaufeni_2380:0.04693231,(Choerophryne_sp_GB:0.0718503,Cophixalus_sphagnicola_GB:0.03226585):0.01033603):0.00897374,(Sphenophryne_2541:0.05558249,Liophryne_rhododactyla_GB:0.02514265):0.00105127):0.00548671):0.00322508,(((Callulops_robustus_45452:0.02753117,(Cophixalus_GB1:0.01258653,Sphenophryne_CCA_813M:1.0E-8):0.02663668):0.00649547,(Callulops_slateri_2099:0.0333122,(Xenobatrachus_6139:0.03254358,(Xenobatrachus_3137:0.02587402,Xenorhina_3689:0.02789936):0.03063027):0.01141148):0.00482339):0.00982716,Genyophryne_thomsoni_GB:0.05865749):0.00283429):0.00683594):0.0019028):0.00940788):0.01949546):0.03732726,(((((Hoplophryne_rogersi_GB:0.00632435,Hoplophryne_RdS_949:0.00106039):0.10169908,(((Alytes_GB:0.21742663,Xenopus_GB:0.30865338):0.04061648,Scaphiopus_GB:0.18826303):0.15756955,(((Ptychadena_RdS_905:0.12943349,(((Polypedates_K_3031:0.0045813,Polypedates_K_3221:0.00283169):0.13351979,Lithobates_sp_RdS_541:0.09783777):0.01508161,(Amietia_RdS_871:0.03346228,(Strongylopus_RdS_994:0.05734356,Tomopterna_tuberculosa_RdS_880:0.08947515):0.01094904):0.03703664):0.02015648):0.02126957,'Gephyromantis_2002_0187':0.13819408):0.03776968,(((Kassina_RdS_803:0.09485046,(Hyperolius_spinigularis_RdS_835:0.07270321,Afrixalus_uluguruensis_RdS_845:0.04620747):0.03703854):0.05544687,(Arthroleptis_tanneri_RdS_929:0.08472016,Arthroleptis_RdS_864:0.05498663):0.07315437):0.01373329,((Hemisus_sudanensis_T471:0.05348474,(Hemisus_marmoratus_GB:0.00506299,Hemisus_marmoratus_RdS_916:1.0E-8):0.0439317):0.11170604,(((Breviceps_mossambicus_GB:0.01800144,Breviceps_RdS_903:0.01327132):0.0142986,Breviceps_fuscus_GB:0.01013171):0.05001821,((Spelaeophryne_methneri_MW_1850:0.07082763,Probreviceps_macrodactylus_RdS_863:0.07551582):0.01106333,(Callulina_kreffti_GB:0.01391603,Callulina_RdS_936:1.0E-8):0.05395393):0.04209742):0.03055684):0.0692803):0.00652724):0.02339149):0.04220474):0.01307123,((Scaphiophryne_marmorata:0.01159035,((Scaphiophryne_madagascariensis_GB:0.00893541,(Scaphiophryne_magas_2002_2120:0.00591018,Scaphiophryne:1.0E-8):0.00111886):0.0061844,Scaphiophryne_marmorata_GB:0.00762443):0.00215603):0.01651066,(Scaphiophryne_calcarata_GB:0.0114932,'Scaphiophryne_2002b24':0.01321455):0.0254768):0.03186369):0.00436249,((Dyscophus_insularis_GB:0.02879084,((Dyscophus_antongilii_GB:0.00345982,Dyscophus_antongilii_2002_2231:0.00140559):0.00598556,(Dyscophus_guineti_GB:8.6531E-4,Dyscophus_guineti:0.00170556):0.00187926):0.03366466):0.04398788,((((((Micryletta_inornata_GB:0.01910409,Micryletta_inornata_FMNH_255121:0.00475841):0.00360949,Micryletta_inornata_K_1956:0.01911241):6.092E-4,Micryletta_inornata_K_3068:0.00339425):0.00700434,Micryletta_inornata_K_3246:0.00169022):0.01144132,Microhyla_inornata_AK_01090:0.00870828):0.06791586,((((Ramanella_variegata_0019C:0.04269837,(((Ramanella_cf_obscura_GB:0.01156473,Ramanella_obscura_MM_5980:0.00362126):0.02023386,Ramanella_montana_GB:0.02428504):0.00912066,Uperodon:0.03949657):0.00177002):0.00812288,(Kaloula_taprobanica_GB2:1.0E-8,Kaloula_taprobanica_GB1:1.0E-8):0.03244972):0.01045975,(((Phrynomantis_microps_RdS_1196:0.07624537,((Kaloula_pulchra_USFS_34083:0.00117755,Kaloula_pulchra_GB2:1.0E-8):0.00331888,Kaloula_pulchra_GB1:0.00789365):0.01076145):0.01271404,(Kaloula_picta_USFS_56931:0.03069865,(Kaloula_baleata_ROM_32925:1.0E-8,Kaloula_baleata_ROM_32932:0.00229552):0.0272835):0.01021104):0.0135403,(((Metaphrynella_sundana_GB:0.0170643,Metaphrynella_sundana_FMNH_231203:0.05361077):0.01464809,Phrynella_pulchra_GB:0.03241788):0.00733721,Metaphrynella_pollicaris_GB:0.02411358):0.05281393):0.01528852):0.02981526,((((Microhyla_butleri_210751:0.06296556,((Microhyla_sp_GB:0.03263484,(Chaperina_fusca_RMB_3053:0.02892035,Microhyla__berdmorei_204876:0.03129638):0.00690999):0.01319212,((Microhyla_heymonsi_210748:0.01321579,Microhyla_heymonsi_GB:0.02081304):0.01574558,(Microhyla_okinavensis_GB:0.0504939,(Microhyla_ornata_230957:0.03700954,Microhyla_pulchra_GB:0.02863546):0.0061525):0.00687912):0.01804295):0.01893275):0.01648656,(Microhyla_annectens_GB:0.02493534,Microhyla_marmorata_GB:0.03745115):0.06157869):0.01240898,(((Calluella_guttulata_GB2:0.00162157,Calluella_guttulata_GB1:5.91E-4):0.04611667,((Glyphoglossus_molossus_USFS_34043:0.00193656,Glyphoglossus_molossus_USFS_34044:0.00147522):2.0644E-4,Glyphoglossus_molossus_GB:0.00356786):0.03426798):0.0015541,Microhyla_achatina_RMB_2620:0.02050121):0.02593958):0.02454761,(Chaperina_fusca_GB:0.024496,(Chaperina_fusca_RMB_3031:4.0954E-4,Calluella_yunnanensis_FMNH_232988:0.01068415):0.01798478):0.10780526):0.01219955):0.00412789):0.00317533):0.00494869):0.00669548,((((Syncope_carvalhoi_KU_215720:0.02940392,(((Syncope_63:0.00119523,Syncope_31:1.0E-8):0.04285172,(Chiasmocleis_magnova_7:0.02264876,((Chiasmocleis_hudsoni_MAD_116:0.04232092,Chiasmocleis_hudsoni_GB:0.02647651):0.00976417,Chiasmocleis_bassleri_NMPGV_71148:0.03351313):0.03442304):0.00945613):0.03283232,Syncope_antenori_QCAZ_23824:0.00341303):0.01053755):0.02872945,((Chiasmocleis_shudikarensis_JIW_458:0.00840344,Chiasmocleis_shudikarensis_GB:0.0073854):0.02720334,(((Chiasmocleis_leucosticta:0.0218421,(((Chiasmocleis_albopunctata_C_565:7.262E-4,Chiasmocleis_albopunctata_C_572:7.2683E-4):7.2519E-4,Chiasmocleis_albopunctata_C_621:1.0E-8):0.00792932,Chiasmocleis_albopunctata_JMP_218:0.00851224):0.02334291):0.00781824,(((Chiasmocleis_schubarti_CFHB_9331:1.0E-8,Chiasmocleis_schubarti_CFHB_9332:1.0E-8):0.01033672,(Chiasmocleis_alagoanus_C_2683:0.00223312,Chiasmocleis_alagoanus_C_2682:6.8404E-4):0.00826292):0.00289029,((Chiasmocleis_capixaba_C_1437:1.0E-8,Chiasmocleis_capixaba_C_1438:0.00142585):0.00326343,(Chiasmocleis_carvalhoi_C_73:1.0E-8,Chiasmocleis_carvalhoi_C_76:0.04843738):0.00526655):0.01147209):0.00184498):0.01021453,(Chiasmocleis_ventrimaculata_ROM_40139:0.00363991,Chiasmocleis_ventrimaculata_KU_215540:0.00317289):0.05276669):0.01662765):0.00615344):0.05637969,(((((Hamptophryne_boliviana_JMP_216:1.0E-8,((Hamptophryne_boliviana_WED_57567:0.00227824,(Hamptophryne_boliviana_44_2003:1.0E-8,Hamptophryne_boliviana_WED_57569:0.0042268):1.0E-8):0.01414474,Hamptophryne_boliviana_GB:0.00106955):0.00370171):0.01795072,(Altigius_alios_3:1.0E-8,Altigius_alios_KU_215544:1.0E-8):0.04899699):0.01777478,(Arcovomer_passarellii_A_1452:0.00988567,Arcovomer_passarellii_A_466:0.04867512):0.03808683):0.00391764,((Dermatonotus_muelleri_D_1344:1.0E-8,(Dermatonotus_muelleri_GB:0.00152883,(Dermatonotus_muelleri:1.0E-8,Dermatonotus_muelleri_Bol_JMP_216:1.0E-8):7.0997E-4):0.00213171):0.04679149,(((Gastrophryne_elegans_RdS_726:0.01296384,((Gastrophryne_olivacea_RdS_295:0.00702281,(Gastrophryne_mazatlanensis_GB:0.00484482,Gastrophryne_carolinensis_GB:0.00535684):0.00296617):0.00997984,Gastrophryne_carolinensis_RdS_363:0.01608871):0.00694668):0.01592533,(Gastrophryne_usta_JAC_24021:0.02865566,(Gastrophryne_pictiventris:0.02615241,((Hypopachus_barberi_KU_291248:0.00617936,(Hypopachus_barberi_ENS_8580A:7.0189E-4,(Hypopachus_variolosus_JAC_19613:9.6205E-4,Hypopachus_variolosus_ENS_8049:0.00144466):9.7462E-4):0.00379767):0.01386268,((Hypopachus_variolosus_JHM_666:1.0E-8,Hypopachus_variolosus_UTA_A_53757:1.0E-8):0.01009017,(Hypopachus_variolosus_ENS_9818:0.00580662,(Hypopachus_variolosus_JAC_24359:0.01147418,(Hypopachus_variolosus_MSM_93:0.00758935,(Hypopachus_variolosus_GB:0.00745907,Hypopachus_variolosus_JAC_23325:0.00485193):0.01096572):0.00438203):0.00108745):0.00628969):0.01595772):0.00839267):0.00278469):0.00438149):0.01187784,(Chiasmocleis_panamensis_AJC_988:0.02855865,((((Elachistocleis_ovalis_E53:0.01129604,(Elachistocleis_surinamensis_E71:1.0E-8,Elachistocleis_ovalis_Ven_E_45_54:1.0E-8):0.00170194):0.00770594,Elachistocleis_bicolor_Uru_ZVCB_10599:0.00870667):0.00171694,Elachistocleis_ovalis_NKAG_6488:0.01034923):0.01668057,(((Elachistocleis_ovalis_USNM_8793:7.113E-4,Elachistocleis_ovalis_GB:0.00370103):1.0E-8,((Relictivomer_pearsei_45:7.3553E-4,Relictivomer_pearsei_43:6.9268E-4):0.00579694,Elachistocleis_bicolor_E51:0.00143657):7.0854E-4):0.00711665,Elachistocleis_ovalis_47_2003:0.01049402):0.0072062):0.02569125):0.00935418):0.00394669):0.0072273):0.01220555,(((Stereocyclops_incrassatus_MUFAI_2483:1.0E-8,Stereocyclops_incrassatus_MUFAI_2482:1.0E-8):0.00918976,(Hyophryne_histrio_MNRJ_38931:0.04164371,(Stereocyclops_A_399:1.0E-8,(Stereocyclops_incrassatus_CFBH_9335:1.0E-8,Stereocyclops_incrassatus_CFBH_9334:0.00482212):0.01283404):0.00203962):0.01078099):0.02571911,((Myersiella_microps_M_1131:0.00568967,(Myersiella_microps_Zaher:0.00406122,Myersiella_microps_M_2358:0.01474809):0.00194629):0.02835088,(Dasypops:1.0E-8,Dasypops_schirchi_GB:1.0E-8):0.03376276):0.01776601):0.00816417):0.014727):0.01093047,(((Nelsonophryne_aequatorialis_QCAZ_31242:0.01709545,(Ctenophryne_geayi_GB:0.00495336,(Ctenophryne_geayi_ROM_40138:7.1205E-4,Ctenophryne_geayi_WED_56812:1.0E-8):0.00184292):0.03537936):0.00523943,Nelsonophryne_aterrima_QCAZ_17124:0.02763021):0.00285584,'Melanophryne_barbatula_T4':0.02379366):0.03118072):0.01271192):0.00295555):0.00200862,(((Plethodontohyla_inguinalis_GB:0.0118358,(Plethodontohyla_brevipes_GB:0.00707457,(Platypelis_grandis_GB:0.00264966,(Platypelis_2002_0198:0.00515636,Plethodontohyla_2002_193:1.0E-8):0.00611829):0.02115796):1.0E-8):0.00527222,(Anodonthyla_boulengerii_GB:0.02933818,(Anodonthyla_2001c60:1.0E-8,Anodonthyla_montana_GB:1.0E-8):0.01329749):0.00565813):0.01245042,((Stumpffia_psologlossa_GB:0.03087309,Stumpffia_pygmaea_GB:0.02206909):0.03920692,((Rhombophryne_testudo:1.0E-8,Rhombophryne_2000c18:0.03870617):0.03130164,((Plethodontohyla_sp_GB:0.04627449,Plethodontohyla_1072:1.0E-8):1.0E-8,Rhombophryne_alluaudi:1.0E-8):0.00562807):0.00802412):0.02338252):0.04353706):0.00225091):0.01975524);";
	}
	function delete6() {
		document.getElementById("newick").value = "insert newick string";
		document.getElementById("svg").innerHTML = '';
		document.getElementById("newick_area").style.display = 'none';
	}
</script>

<jsp:include page="../common/footer.jsp" flush="false" />
