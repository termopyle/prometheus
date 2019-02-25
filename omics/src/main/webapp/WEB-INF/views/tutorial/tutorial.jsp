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
		<h3>Tutorial</h3>
		<!-- contents 정렬 S-->
		
		<ul class="tab5">
			<li><a id="tab_cont1" onclick="con_tab(1);" class="on" style="width: 300px;">1.	Genome Archive and Gene Viewer</a></li>
			<li><a id="tab_cont2" onclick="con_tab(2);" class="off">2.	Gene Search</a></li>
			<li><a id="tab_cont3" onclick="con_tab(3);" class="off">3.	Genome Analysis</a></li>
			<li><a id="tab_cont4" onclick="con_tab(4);" class="off">4.	My Genes</a></li>
			<li><a id="tab_cont5" onclick="con_tab(5);" class="off">5.	KoDS</a></li>
		</ul>
		<div class="slidetabs"><a href="#contents">top</a></div>
		<div class="tutorialall" id="div_tab_1" style="display: block;">
			<div class="timages">
				<ol>
					<li id="tutorial1-top">
						<span>1.	Click species of interest in classification system that consist hierarchical structure by NCBI taxonomy tree</span> <img src="./resources/images/sub/figure1_1.png" alt="" />
					</li>
					<li>
						<span>2.	User can search species of interest directly by using ‘search panel’ and ‘search panel’ supports auto completion of species name for user-convenience.</span>
						<img src="./resources/images/sub/figure1_2.png" alt="" />
					</li>
					<li>
						<span>3.	Below the figure, it is example for searching ‘Saccharomyces cerevisiae’. In case by search function, you would show all species to include the typing word.</span>
					</li>
					<li>
						<span>4.	Representative genome information for ‘Saccharomyces cerevisiae’ are presented with assembly, taxonomy and GenBank information.</span>
						<img src="./resources/images/sub/figure1_4.png" alt="" />
					</li>
					<li>
						<span>5.	Users can access detailed information of genome by clicking ‘Genome Browser’ button.</span>
						<img src="./resources/images/sub/figure1_5.png" alt="" />
					</li>
					<li id="tutorial1-6">
						<span>6.	In ‘Genome Browser’, structural information of genome is presented by circular form with zoom in/out functions ranging from 100% to 1,000%. </span>
					</li>
					<li id="tutorial1-7">
						<span>7.	Version of genome could be chosen by database source such as NCBI-Refseq, Ensembl and others. In some case, it can’t show genome because the data is not supported by the database source.</span>
						<img src="./resources/images/sub/figure1_7.png" alt="" />
					</li>
					<li id="tutorial1-8">
						<span>8.	To download sequence files, users click three icons of ‘Genome’, ‘CDS’ and ‘PEP’.</span>
					</li>
					<li id="tutorial1-9">
						<span>9.	A gene search function by position or gene name. Type a gene name in entry of ‘Query’ and then click button of ‘Search’. The gene name is completed automatically if it exist in genome.</span>
						<img src="./resources/images/sub/figure1_9.png" alt="" />
					</li>
					<li id="tutorial1-10">
						<span>10.	The page indicates to flaking region of gene for users’ search. Additionally, if users want to investigate detailed information for the gene, you could select the gene (“SAF1”). Then, it moves a page of “Gene Viewer”.</span>
						<img src="./resources/images/sub/figure1_10.png" alt="" />
					</li>
					<li id="tutorial1-11">
						<span>11.	‘Gene Viewer’ provides detailed information of genes.</span>
					</li>
					<li id="tutorial1-12">
						<span>12.	In ‘Gene Information’ panel, locus tag, species name, annotation version and taxonomy information are presented.</span>
					</li>
					<li id="tutorial1-13">
						<span>13.	In ‘Gene Structure’ panel, general information about gene structures are provided by various tracks such as gene, mRNA, exon, CDS, ncRNA, repeat region, tRNA and rRNA. Also, you download the sequence of peptide and CDS.</span>
					</li>
					<li id="tutorial1-14">
						<span>14.	In ‘Domain Architecture’ panel, domain architectures of genes are provided using IPR terms from pre-performed InterProScan v5.0. The domain information of individual databases such as Pfam, Gene3D, ProSiteProfiles and PANTHER are shown.</span>
						<img src="./resources/images/sub/figure1_14.png" alt="" />
					</li>
					<li id="tutorial1-15">
						<span>15.	In ‘Subcellular localization’ panel, subcellular localization information predicted from three analysis programs such as TMHMM, MultiLoc2, and TargetP are shown. Detailed information is presented in each subpanels.</span>
						<img src="./resources/images/sub/figure1_15.png" alt="" />
					</li>
					<li id="tutorial1-16">
						<span>16.	In ‘Ortholog or Paralog information’ panel, ortholog genes of closed-relative species and paralog genes are presented from OrthoMCL analysis. Users can obtain the information of organism, assembly ID and protein ID for candidate orthologs.</span>
						<img src="./resources/images/sub/figure1_16.png" alt="" />
					</li>
				</ol>
			</div>
		</div>
		<div class="tutorialall" id="div_tab_2" style="display:none;">
			<div class="timages">
				<ol>
					<li>
						<span>Step 1. Select a gene numbers to identify.
							<span> 1.	Fill out numbers of genes to identify using Gene Search in entry of ‘No. of subtypes’ and click a submit button.<br />
							2.	If you type ‘3’ in No. of subtypes, three lines are created to perform Gene Search.<br />
							3.	Each gene is distinguished by identifier filled with gene name in entry of ‘subtype’ and this identifier is used for header in sequences of FASTA file. </span>
						</span>
						<img src="./resources/images/sub/figure2_1.png" alt="" />
					</li>
					<li id="tutorial2-2">
						<span>Step 2. Perform Gene Search.
							<span> 1.	Fill out domain architectures of genes of interest in entry of ‘Domains’ using IPR terms of domain. Each IPR term is distinguished by comma.<br />
							2.	To enhance specificity of Gene Search, users fill out entry of ‘with (include genes containing specific IPR terms)’ or ‘without (exclude genes containing specific IPR terms)’ using IPR terms of family, repeat, and site. Each IPR term is distinguished by comma.<br />
							3.	Select status of ordering of domains between in order or in any order.<br />
							4.	Select match type using entry of Perfect match.<br />
							If you do not select perfect match, all kinds of genes containing domain architectures filled by users with additional domains are searched.<br />
							5.	Click a search button to execute Gene Search. </span>
						</span>
						<img src="./resources/images/sub/figure2_2.png" alt="" />
					</li>
					<li id="tutorial2-3">
						<span>Step 3. Result of Gene Search.
							<span> 1.	In result window, statistical report of kingdom-wide distribution of genes of interest including domain architectures is shown.<br />
							2.	Users can download sequences of CDS or peptides by clicking ‘Peptide Download’ or ‘CDS Download’, respectively.<br />
							3.	Users can also download the sequence by select ‘ALL’ (include alternative spliced genes) or ‘Representative’ (exclude alternative spliced genes)<br />
							4.	If you want to download kingdom-specific sequences, click panel of certain kingdom in ‘No. of proteins distribution’. The status of each kingdom is indicated by blue line.<br />
							5.	Users can investigate the domain architectures of searched genes by clicking ‘Viewer Panel on’.<br />
							6.	To download the sequence file for each gene, click the button of ‘Peptide’ or ‘CDS’.<br />
							7.	To investigate detailed information of each gene, users can access ‘Gene Viewer’ page by clicking ‘Gene Viewer’ button.<br />
							8.	According to bottom of window, you move the result pages to click aside button. </span> 
						</span>
						<img src="./resources/images/sub/figure2_3.png" alt="" />
						<img src="./resources/images/sub/figure2_4.png" alt="" />
					</li>
				</ol>
			</div>
		</div>
		<div class="tutorialall" id="div_tab_3" style="display:none;">
			<div class="timages">
				<ol>
					<li id="tutorial3-1">
						<span>1. LAST
							<span> 1. Load the sample reference and sample query. They are mitochondrial DNA sequences of human and fugu.<br />
							2. Select ‘DNA’ in both ‘Reference sequence type’ and ‘Query sequence type’.<br />
							3.	Loaded data is in format of FASTA, select ‘0’ in ‘Input format’.<br />
							4.	We like to see the result of colored alignments in html format. Select ‘4’ in ‘Output type’ and ‘html’ in ‘Output extension type’.<br />
							5.	Select options -c -R01<br />
							6.	In this example, we do not select options in ‘Maximum expected alignments’, ‘Alignment type’, ‘Mach/Mismatch score matrix’, ‘Seeding schema’, and ‘Maximum initial matches’.<br />
							7.	Click submit button to execute LAST.<br />
							8.	Results can be accessed via ‘My Page’. In the ‘Detail View’, information of used options is shown. In the ‘View File’, the result of coloured alignments in html format is in ‘maf_convert’ folder. The ‘many to one alignment’ maf file is in ‘maf_split’ folder and presented as ‘output.split.maf’ file. The ‘one to one alignment’ maf file is in ‘maf_swap’ folder and presented as ‘output.split.swap.split.swap.maf’ file. In the dot plot, reference genome is along the top, and query genome is down the side. </span>
						</span>
						<img src="./resources/images/sub/figure3_1.png" alt="" />
						</li>
					<li id="tutorial3-2">
						<span>2. BLAST
							<span> 1.	Users can perform BLAST analysis by fill out sequence, upload users’ files in local computer (Local File), or users’ files in ‘My Gene’ (Cloud File).<br />
							2.	Select a blast program and database for blast.<br />
							3.	Fill out entry of ‘EMAIL’ and ‘TITLE’ and click submit button to execute BLAST. </span>
						</span>
						<img src="./resources/images/sub/figure3_2.png" alt="" />
					</li>
					<li id="tutorial3-3">
						<span>3. Multiple sequence alignment
							<span> 1.	To perform multiple sequence alignment, users select tab of alignment method. Then fill out sequence, upload users’ files in local computer (Local File), or users’ files in ‘My Gene’ (Cloud File).<br />
							2.	Fill out entry of ‘EMAIL’ and ‘TITLE’ and click submit button to execute Multiple sequence alignment. </span>
						</span>
						<img src="./resources/images/sub/figure3_3.png" alt="" />
					</li>
					<li id="tutorial3-4">
						<span>4. InterPro
							<span> 1.	Users can perform InterProScan v5.0 analysis by fill out sequence, upload users’ files in local computer (Local File), or users’ files in ‘My Gene’ (Cloud File). <br />
							2.	Fill out entry of ‘EMAIL’ and ‘TITLE’ and click submit button to execute InterProScan. </span>
						</span>
						<img src="./resources/images/sub/figure3_4.png" alt="" />
					</li>
					<li id="tutorial3-5">
						<span>5. Phylogenetic Viewer
							<span> 1.	To investigate phylogenetic tree, users can perform Phylogenetic Viewer by filling out phylogenetic tree information of newick format or selecting newick files in ‘My Gene’ (File)<br />
							2.	Then select a type of phylogenetic tree and click ‘show’ button. </span>
						</span>
						<img src="./resources/images/sub/figure3_5.png" alt="" />
					</li>
				</ol>
			</div>
		</div>
		<div class="tutorialall" id="div_tab_4" style="display:none;">
			<div class="timages">
				<ol>
					<li id="tutorial4-1">
						<span>1. Users can upload personal data or downloaded data from Prometheus and analyze them (upper panel). Users can also monitor the progress of analysis in ‘My Genes’ and download the result files from each program via a file menu. </span>
						<img src="./resources/images/sub/figure4_1.png" alt="" />
					</li>
					<li id="tutorial4-2">
						<span>2. In the case of data from LAST, the result of coloured alignments in html format is in ‘maf_convert’ folder. The ‘many to one alignment’ maf file is in ‘maf_split’ folder and presented as ‘output.split.maf’ file. The ‘one to one alignment’ maf file is in ‘maf_swap’ folder and presented as ‘output.split.swap.split.swap.maf’ file. In the dot plot, reference genome is along the top, and query genome is down the side.</span>
					</li>
					<li id="tutorial4-3">
						<span>3. In the case of data from InterProScan, the result file is shown in a graphic format and results are downloaded in a tsv file format.</span>
						<img src="./resources/images/sub/figure4_3.png" alt="" />
					</li>
					<li id="tutorial4-4">
						<span>4. In the case of data from Clustal Omega, phylogenetic tree data (output.tree) is provided with multiple sequence alignment data (output.clu). </span>
						<img src="./resources/images/sub/figure4_4.png" alt="" />
					</li>
				</ol>
			</div>			
		</div>
		<div class="tutorialall" id="div_tab_5"  style="display:none;">
			<div class="timages">
				<ol>
					<li id="tutorial5-1">
						<span>1. It is executed upon completion of downloading the KoDS high-speed transmission system 
						(KoDS v.3.5 as of the date of preparing this document) and the initial login screen will be displayed. 
						Upon selecting your preferred service system, you may log into the system using your user details.</span>
						<img src="./resources/images/sub/figure5_1.png" alt="" />
					</li>
					<li id="tutorial5-2">
						<span>2.The list of user local files is displayed on the left and the list of user data stored in the KOBIC cloud storage is displayed on the right. Select a data file you want to transmit and drag &amp; drop it to start transmitting data. The progress status of data transmission is displayed on the monitoring screen on the bottom along with the total transmission capacity and real-time network resource usage status. </span>
						<img src="./resources/images/sub/figure5_2.png" alt="" />
					</li>
				</ol>
			</div>			
		</div>
		
		<!-- contents 정렬 E--> 
		
	</div>
	<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>
	