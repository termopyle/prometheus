<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
		
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<link href="./resources/css/newick/tree.css" rel="stylesheet" type="text/css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="http://blog.accursedware.com/jquery-svgpan/jquery-svgpan.js"></script>

<script src="./resources/js/newick/treelib.js"></script>	


<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E -->

<!-- contents S-->
<div id="contents" class="subcon conwrap1200">
	<h2 class="hidden">메인컨텐츠</h2>
	
	<h3>Phylogenetic Tree</h3>


	<div class="newickarea">
		<div class="newicktit">
			Paste in a <a href="http://en.wikipedia.org/wiki/Newick_format">Newick-format</a>
			<select id="style">
				<option value="circle">Circle tree</option>
				<option value="circlephylogram">Circle phylogram</option>
				<option value="cladogram">Cladogram</option>
				<option value="rectanglecladogram">Rectangular cladogram</option>
				<option value="phylogram">Phylogram</option>
			</select>
			<button onclick="showtree('newick')">Show</button>
			<button  class="gray">File</button>
		</div>
		
		<textarea id="newick" rows="10" cols="100">
			(Phrynomantis_annectens_GB:0.02179898,(Phrynomantis_bifasciatus_GB:0.00255928,(Phrynomantis:1.0E-8,(Phrynomantis_microps_GB:0.00801643,Kaloula_pulchra_RdS_1200:0.0016947):3.4862E-4):0.00260154):0.05279985,(((((Kalophrynus_interlineatus_GB:0.00855247,Kalophrynus_pleurostigma_AK_1097:1.0E-8):0.00386018,Kalophrynus_interlineatus_USFS_34285:0.00720558):0.01927252,Kalophrynus_pleurostigma_GB:0.02673547):0.09653447,(((Synapturanus_MW_1004:0.01049649,Synapturanus_salseri_55:0.01777387):0.00769301,Synapturanus_mirandaribeiroi_SMNS_12078:0.00379453):0.07491132,(Otophryne_steyermarki_ROM_39677:0.11605342,(Otophryne_pyburni_GB:0.00817966,Otophryne_robusta_ROM_39679:0.01453495):0.00650537):0.04391901):0.02796312):0.00529357,(((Gastrophrynoides_immaculatus_GB:0.08435684,((Oreophryne_brachypus_GB:0.06764665,((Oreophryne_9731:0.02588912,Oreophryne_anulata_H_1366:0.02049606):0.01183727,Oreophryne_2289:0.03719342):0.00631442):0.00973818,(Aphantophryne_pansa_GB:0.02930301,((Cophixalus_sp_CCA_476L:0.02672845,(Cophixalus_7741:0.10419693,Cophixalus_GB2:0.0188221):0.03328761):0.01351682,((((Austrochaperina_derongo_2293:1.0E-8,Copiula_sp_GB:0.05526954):0.01373083,(Copiula_sp_3235:0.01624485,Copiula_oxyrhina_2721:0.01721637):0.01923231):0.00898059,((Liophryne_schlaginhaufeni_2380:0.04693231,(Choerophryne_sp_GB:0.0718503,Cophixalus_sphagnicola_GB:0.03226585):0.01033603):0.00897374,(Sphenophryne_2541:0.05558249,Liophryne_rhododactyla_GB:0.02514265):0.00105127):0.00548671):0.00322508,(((Callulops_robustus_45452:0.02753117,(Cophixalus_GB1:0.01258653,Sphenophryne_CCA_813M:1.0E-8):0.02663668):0.00649547,(Callulops_slateri_2099:0.0333122,(Xenobatrachus_6139:0.03254358,(Xenobatrachus_3137:0.02587402,Xenorhina_3689:0.02789936):0.03063027):0.01141148):0.00482339):0.00982716,Genyophryne_thomsoni_GB:0.05865749):0.00283429):0.00683594):0.0019028):0.00940788):0.01949546):0.03732726,(((((Hoplophryne_rogersi_GB:0.00632435,Hoplophryne_RdS_949:0.00106039):0.10169908,(((Alytes_GB:0.21742663,Xenopus_GB:0.30865338):0.04061648,Scaphiopus_GB:0.18826303):0.15756955,(((Ptychadena_RdS_905:0.12943349,(((Polypedates_K_3031:0.0045813,Polypedates_K_3221:0.00283169):0.13351979,Lithobates_sp_RdS_541:0.09783777):0.01508161,(Amietia_RdS_871:0.03346228,(Strongylopus_RdS_994:0.05734356,Tomopterna_tuberculosa_RdS_880:0.08947515):0.01094904):0.03703664):0.02015648):0.02126957,'Gephyromantis_2002_0187':0.13819408):0.03776968,(((Kassina_RdS_803:0.09485046,(Hyperolius_spinigularis_RdS_835:0.07270321,Afrixalus_uluguruensis_RdS_845:0.04620747):0.03703854):0.05544687,(Arthroleptis_tanneri_RdS_929:0.08472016,Arthroleptis_RdS_864:0.05498663):0.07315437):0.01373329,((Hemisus_sudanensis_T471:0.05348474,(Hemisus_marmoratus_GB:0.00506299,Hemisus_marmoratus_RdS_916:1.0E-8):0.0439317):0.11170604,(((Breviceps_mossambicus_GB:0.01800144,Breviceps_RdS_903:0.01327132):0.0142986,Breviceps_fuscus_GB:0.01013171):0.05001821,((Spelaeophryne_methneri_MW_1850:0.07082763,Probreviceps_macrodactylus_RdS_863:0.07551582):0.01106333,(Callulina_kreffti_GB:0.01391603,Callulina_RdS_936:1.0E-8):0.05395393):0.04209742):0.03055684):0.0692803):0.00652724):0.02339149):0.04220474):0.01307123,((Scaphiophryne_marmorata:0.01159035,((Scaphiophryne_madagascariensis_GB:0.00893541,(Scaphiophryne_magas_2002_2120:0.00591018,Scaphiophryne:1.0E-8):0.00111886):0.0061844,Scaphiophryne_marmorata_GB:0.00762443):0.00215603):0.01651066,(Scaphiophryne_calcarata_GB:0.0114932,'Scaphiophryne_2002b24':0.01321455):0.0254768):0.03186369):0.00436249,((Dyscophus_insularis_GB:0.02879084,((Dyscophus_antongilii_GB:0.00345982,Dyscophus_antongilii_2002_2231:0.00140559):0.00598556,(Dyscophus_guineti_GB:8.6531E-4,Dyscophus_guineti:0.00170556):0.00187926):0.03366466):0.04398788,((((((Micryletta_inornata_GB:0.01910409,Micryletta_inornata_FMNH_255121:0.00475841):0.00360949,Micryletta_inornata_K_1956:0.01911241):6.092E-4,Micryletta_inornata_K_3068:0.00339425):0.00700434,Micryletta_inornata_K_3246:0.00169022):0.01144132,Microhyla_inornata_AK_01090:0.00870828):0.06791586,((((Ramanella_variegata_0019C:0.04269837,(((Ramanella_cf_obscura_GB:0.01156473,Ramanella_obscura_MM_5980:0.00362126):0.02023386,Ramanella_montana_GB:0.02428504):0.00912066,Uperodon:0.03949657):0.00177002):0.00812288,(Kaloula_taprobanica_GB2:1.0E-8,Kaloula_taprobanica_GB1:1.0E-8):0.03244972):0.01045975,(((Phrynomantis_microps_RdS_1196:0.07624537,((Kaloula_pulchra_USFS_34083:0.00117755,Kaloula_pulchra_GB2:1.0E-8):0.00331888,Kaloula_pulchra_GB1:0.00789365):0.01076145):0.01271404,(Kaloula_picta_USFS_56931:0.03069865,(Kaloula_baleata_ROM_32925:1.0E-8,Kaloula_baleata_ROM_32932:0.00229552):0.0272835):0.01021104):0.0135403,(((Metaphrynella_sundana_GB:0.0170643,Metaphrynella_sundana_FMNH_231203:0.05361077):0.01464809,Phrynella_pulchra_GB:0.03241788):0.00733721,Metaphrynella_pollicaris_GB:0.02411358):0.05281393):0.01528852):0.02981526,((((Microhyla_butleri_210751:0.06296556,((Microhyla_sp_GB:0.03263484,(Chaperina_fusca_RMB_3053:0.02892035,Microhyla__berdmorei_204876:0.03129638):0.00690999):0.01319212,((Microhyla_heymonsi_210748:0.01321579,Microhyla_heymonsi_GB:0.02081304):0.01574558,(Microhyla_okinavensis_GB:0.0504939,(Microhyla_ornata_230957:0.03700954,Microhyla_pulchra_GB:0.02863546):0.0061525):0.00687912):0.01804295):0.01893275):0.01648656,(Microhyla_annectens_GB:0.02493534,Microhyla_marmorata_GB:0.03745115):0.06157869):0.01240898,(((Calluella_guttulata_GB2:0.00162157,Calluella_guttulata_GB1:5.91E-4):0.04611667,((Glyphoglossus_molossus_USFS_34043:0.00193656,Glyphoglossus_molossus_USFS_34044:0.00147522):2.0644E-4,Glyphoglossus_molossus_GB:0.00356786):0.03426798):0.0015541,Microhyla_achatina_RMB_2620:0.02050121):0.02593958):0.02454761,(Chaperina_fusca_GB:0.024496,(Chaperina_fusca_RMB_3031:4.0954E-4,Calluella_yunnanensis_FMNH_232988:0.01068415):0.01798478):0.10780526):0.01219955):0.00412789):0.00317533):0.00494869):0.00669548,((((Syncope_carvalhoi_KU_215720:0.02940392,(((Syncope_63:0.00119523,Syncope_31:1.0E-8):0.04285172,(Chiasmocleis_magnova_7:0.02264876,((Chiasmocleis_hudsoni_MAD_116:0.04232092,Chiasmocleis_hudsoni_GB:0.02647651):0.00976417,Chiasmocleis_bassleri_NMPGV_71148:0.03351313):0.03442304):0.00945613):0.03283232,Syncope_antenori_QCAZ_23824:0.00341303):0.01053755):0.02872945,((Chiasmocleis_shudikarensis_JIW_458:0.00840344,Chiasmocleis_shudikarensis_GB:0.0073854):0.02720334,(((Chiasmocleis_leucosticta:0.0218421,(((Chiasmocleis_albopunctata_C_565:7.262E-4,Chiasmocleis_albopunctata_C_572:7.2683E-4):7.2519E-4,Chiasmocleis_albopunctata_C_621:1.0E-8):0.00792932,Chiasmocleis_albopunctata_JMP_218:0.00851224):0.02334291):0.00781824,(((Chiasmocleis_schubarti_CFHB_9331:1.0E-8,Chiasmocleis_schubarti_CFHB_9332:1.0E-8):0.01033672,(Chiasmocleis_alagoanus_C_2683:0.00223312,Chiasmocleis_alagoanus_C_2682:6.8404E-4):0.00826292):0.00289029,((Chiasmocleis_capixaba_C_1437:1.0E-8,Chiasmocleis_capixaba_C_1438:0.00142585):0.00326343,(Chiasmocleis_carvalhoi_C_73:1.0E-8,Chiasmocleis_carvalhoi_C_76:0.04843738):0.00526655):0.01147209):0.00184498):0.01021453,(Chiasmocleis_ventrimaculata_ROM_40139:0.00363991,Chiasmocleis_ventrimaculata_KU_215540:0.00317289):0.05276669):0.01662765):0.00615344):0.05637969,(((((Hamptophryne_boliviana_JMP_216:1.0E-8,((Hamptophryne_boliviana_WED_57567:0.00227824,(Hamptophryne_boliviana_44_2003:1.0E-8,Hamptophryne_boliviana_WED_57569:0.0042268):1.0E-8):0.01414474,Hamptophryne_boliviana_GB:0.00106955):0.00370171):0.01795072,(Altigius_alios_3:1.0E-8,Altigius_alios_KU_215544:1.0E-8):0.04899699):0.01777478,(Arcovomer_passarellii_A_1452:0.00988567,Arcovomer_passarellii_A_466:0.04867512):0.03808683):0.00391764,((Dermatonotus_muelleri_D_1344:1.0E-8,(Dermatonotus_muelleri_GB:0.00152883,(Dermatonotus_muelleri:1.0E-8,Dermatonotus_muelleri_Bol_JMP_216:1.0E-8):7.0997E-4):0.00213171):0.04679149,(((Gastrophryne_elegans_RdS_726:0.01296384,((Gastrophryne_olivacea_RdS_295:0.00702281,(Gastrophryne_mazatlanensis_GB:0.00484482,Gastrophryne_carolinensis_GB:0.00535684):0.00296617):0.00997984,Gastrophryne_carolinensis_RdS_363:0.01608871):0.00694668):0.01592533,(Gastrophryne_usta_JAC_24021:0.02865566,(Gastrophryne_pictiventris:0.02615241,((Hypopachus_barberi_KU_291248:0.00617936,(Hypopachus_barberi_ENS_8580A:7.0189E-4,(Hypopachus_variolosus_JAC_19613:9.6205E-4,Hypopachus_variolosus_ENS_8049:0.00144466):9.7462E-4):0.00379767):0.01386268,((Hypopachus_variolosus_JHM_666:1.0E-8,Hypopachus_variolosus_UTA_A_53757:1.0E-8):0.01009017,(Hypopachus_variolosus_ENS_9818:0.00580662,(Hypopachus_variolosus_JAC_24359:0.01147418,(Hypopachus_variolosus_MSM_93:0.00758935,(Hypopachus_variolosus_GB:0.00745907,Hypopachus_variolosus_JAC_23325:0.00485193):0.01096572):0.00438203):0.00108745):0.00628969):0.01595772):0.00839267):0.00278469):0.00438149):0.01187784,(Chiasmocleis_panamensis_AJC_988:0.02855865,((((Elachistocleis_ovalis_E53:0.01129604,(Elachistocleis_surinamensis_E71:1.0E-8,Elachistocleis_ovalis_Ven_E_45_54:1.0E-8):0.00170194):0.00770594,Elachistocleis_bicolor_Uru_ZVCB_10599:0.00870667):0.00171694,Elachistocleis_ovalis_NKAG_6488:0.01034923):0.01668057,(((Elachistocleis_ovalis_USNM_8793:7.113E-4,Elachistocleis_ovalis_GB:0.00370103):1.0E-8,((Relictivomer_pearsei_45:7.3553E-4,Relictivomer_pearsei_43:6.9268E-4):0.00579694,Elachistocleis_bicolor_E51:0.00143657):7.0854E-4):0.00711665,Elachistocleis_ovalis_47_2003:0.01049402):0.0072062):0.02569125):0.00935418):0.00394669):0.0072273):0.01220555,(((Stereocyclops_incrassatus_MUFAI_2483:1.0E-8,Stereocyclops_incrassatus_MUFAI_2482:1.0E-8):0.00918976,(Hyophryne_histrio_MNRJ_38931:0.04164371,(Stereocyclops_A_399:1.0E-8,(Stereocyclops_incrassatus_CFBH_9335:1.0E-8,Stereocyclops_incrassatus_CFBH_9334:0.00482212):0.01283404):0.00203962):0.01078099):0.02571911,((Myersiella_microps_M_1131:0.00568967,(Myersiella_microps_Zaher:0.00406122,Myersiella_microps_M_2358:0.01474809):0.00194629):0.02835088,(Dasypops:1.0E-8,Dasypops_schirchi_GB:1.0E-8):0.03376276):0.01776601):0.00816417):0.014727):0.01093047,(((Nelsonophryne_aequatorialis_QCAZ_31242:0.01709545,(Ctenophryne_geayi_GB:0.00495336,(Ctenophryne_geayi_ROM_40138:7.1205E-4,Ctenophryne_geayi_WED_56812:1.0E-8):0.00184292):0.03537936):0.00523943,Nelsonophryne_aterrima_QCAZ_17124:0.02763021):0.00285584,'Melanophryne_barbatula_T4':0.02379366):0.03118072):0.01271192):0.00295555):0.00200862,(((Plethodontohyla_inguinalis_GB:0.0118358,(Plethodontohyla_brevipes_GB:0.00707457,(Platypelis_grandis_GB:0.00264966,(Platypelis_2002_0198:0.00515636,Plethodontohyla_2002_193:1.0E-8):0.00611829):0.02115796):1.0E-8):0.00527222,(Anodonthyla_boulengerii_GB:0.02933818,(Anodonthyla_2001c60:1.0E-8,Anodonthyla_montana_GB:1.0E-8):0.01329749):0.00565813):0.01245042,((Stumpffia_psologlossa_GB:0.03087309,Stumpffia_pygmaea_GB:0.02206909):0.03920692,((Rhombophryne_testudo:1.0E-8,Rhombophryne_2000c18:0.03870617):0.03130164,((Plethodontohyla_sp_GB:0.04627449,Plethodontohyla_1072:1.0E-8):1.0E-8,Rhombophryne_alluaudi:1.0E-8):0.00562807):0.00802412):0.02338252):0.04353706):0.00225091):0.01975524);
		</textarea>
		
		<p>
			<span id="message"></span>
		</p>
		
		<div class="newicksvg">
			<svg id="svg" xmlns="http://www.w3.org/2000/svg" version="1.1" height="1200" width="1200">
				<g id="viewport"></g>
			</svg>
		</div>
		
	</div>
	
	
	

<script>
	
	if (!String.prototype.trim)
	{
		String.prototype.trim=function(){return this.replace(/^\s+|\s+$/g, '');};
	}
	
	function showtree(element_id)
	{
	    var t = new Tree();
	    var element = document.getElementById(element_id);
	    var newick = element.value;
	    newick = newick.trim(newick);
		t.Parse(newick);
	
		if (t.error != 0)
		{
			document.getElementById('message').innerHTML='Error parsing tree';
		}
		else
		{
			/* document.getElementById('message').innerHTML='Parsed OK'; */
			
			t.WriteNewick();
			
			t.ComputeWeights(t.root);
			
			var td = null;
			
			var selectmenu = document.getElementById('style');
			var drawing_type = (selectmenu.options[selectmenu.selectedIndex].value);
			
			switch (drawing_type)
			{
				case 'rectanglecladogram':
					td = new RectangleTreeDrawer();
					break;
			
				case 'phylogram':
					if (t.has_edge_lengths)
					{
						td = new PhylogramTreeDrawer();
					}
					else
					{
						td = new RectangleTreeDrawer();
					}
					break;
					
				case 'circle':
					td = new CircleTreeDrawer();
					break;
					
				case 'circlephylogram':
					if (t.has_edge_lengths)
					{
						td = new CirclePhylogramDrawer();
					}
					else
					{
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
			while (svg.hasChildNodes()) 
			{
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
			
			
			td.Init(t, {svg_id: 'viewport', width:500, height:500, fontHeight:10, root_length:0.1} );
			
			td.CalcCoordinates();
			td.Draw();
			
			// label leaves...
			
			var n = new NodeIterator(t.root);
			var q = n.Begin();
			while (q != null)
			{
				if (q.IsLeaf())
				{
					switch (drawing_type)
					{
						case 'circle':
						case 'circlephylogram':
							var align = 'left';
							var angle = q.angle * 180.0/Math.PI;
							if ((q.angle > Math.PI/2.0) && (q.angle < 1.5 * Math.PI))
							{
								align = 'right';
								angle += 180.0;
							}
							drawRotatedText('viewport', q.xy, q.label, angle, align)
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
		}
		
	
	}

</script>

</div>

<!-- contents E -->


<jsp:include page="../common/footer.jsp" flush="false"/>
	