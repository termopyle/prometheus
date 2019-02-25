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
		<h3>Intro</h3>
	
		<div class="intro">			
			<p><img src="./resources/images/sub/img3.png" alt="" class="introimg1" />Since next-generation sequencing (NGS) technology was developed in the mid-2000s, an enormous amount of genomic information has been analyzed and amassed in public databases. As the numbers of sequenced genomes increased, many tools and pipelines were developed to investigate gene functions, identify gene families, and perform comparative genomic analyses. However, the application of comparative analyses is restricted to functional gene annotations and newly sequenced genome analyses. Newly sequenced genomes are initially compared to those that have previously been analyzed, including genomes of closely related species, to provide information on genome structure changes and gene repertoires. Such comparisons can also predict gene paralogues, which are genes related by duplication events, or orthologues, which are those related by speciation events. As orthologues tend to be more similar in function that paralogues, they are widely used for functional gene annotations. Moreover, recent gene-of-interest studies that include multigenome orthologues offer insight into their mechanisms for adapting to the environment. However, these comparative genomic analyses were performed at genome-, genus-, or kingdom-wide levels, thereby restricting comparisons to the species, family, or order level. To understand the evolution of genes of interest more precisely, interkingdom analyses are needed, particularly because many genes in eukaryotic genomes have universal common ancestries in Bacteria and Archaea. </p>
			<p><img src="./resources/images/sub/img4.png" alt="" class="introimg2" />We construct an omics portal for interkingdom comparative genomic analyses named Prometheus. We collected 17,215 sequences from 16,730 species and constructed four primary databases to provide basic genome information, with more detailed information on individual genes provided in secondary databases. Researchers can then access detailed information on genes of interest, such as gene structure, domain architecture, subcellular localization, orthologues, and paralogues, as well as their sequences. In particular, Prometheus provides Gene Search to identify genes of interest based on their domain architectures from prokaryotes to eukaryotes and performs various comparative analyses, such as comparison of chromosome sequences, sequence alignment, and phylogenetic analyses. Furthermore, researchers can perform various bioinformatics analyses with these and their own sequencing data in a cloud-based platform, BioExpress. Prometheus presents a new paradigm for genome research, from single genes of interest to entire gene pathways.</p>	
		</div>
	
	</div>
	<!-- contents E -->
	
<jsp:include page="../common/footer.jsp" flush="false"/>
	