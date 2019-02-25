function getTMHMMMessageFromServer(p_tax_id, p_refseq_assembly_id, p_protein_accession_id) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "get_tmhmm";
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	/**cell_outside_only example**/
//	 	data: {tax_id:'886738', protein_accession_id:'WP_007403197.1', refseq_assembly_id:'GCF_000204585.1'},

	 	/**cell_in_out_side_both**/
	 	/**start: inside, end: inside**/ 
//	 	data: {tax_id:'210', protein_accession_id:'WP_000383778.1', refseq_assembly_id:'GCF_000688915.1'},
//	 	data: {tax_id:'210', protein_accession_id:'WP_026938321.1', refseq_assembly_id:'GCF_000688915.1'},
	 	/**start: outside, end: outside**/
//	 	data: {tax_id:'210', protein_accession_id:'WP_000398028.1', refseq_assembly_id:'GCF_000688915.1'},	
	 	/**start: outside, end: outside, TMHMM num: 156**/
//	 	data: {tax_id:'9986', protein_accession_id:'XP_008260745.1', refseq_assembly_id:'GCF_000003625.3'},
	 	/**start: inside, end: outside, TMHMM num: 170**/
//	 	data: {tax_id:'1046598', protein_accession_id:'WP_029508195.1', refseq_assembly_id:'GCF_000260455.1'},
	 	/**start: inside, end: inside, TMHMM num: 120**/
//	 	data: {tax_id:'214688', protein_accession_id:'WP_029600752.1', refseq_assembly_id:'GCF_000171775.1'},
	 	/**start: outside, end: inside, TMHMM num: 110**/
//	 	data: {tax_id:'991778', protein_accession_id:'WP_007330001.1', refseq_assembly_id:'GCF_000195185.1'},
	 	
	 	// cell_in_out_side_both
	 	data: {tax_id:p_tax_id, protein_accession_id:p_protein_accession_id, refseq_assembly_id:p_refseq_assembly_id},
	 	
	 	/**cell_inside_only example**/
//	 	data: {tax_id:'210', protein_accession_id:'WP_000290431.1', refseq_assembly_id:'GCF_000688915.1'},
	 	
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
		error:function(request, status, error){
			request.status = 500;
			alert("TmHmm\n" + "code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
			
			hiding("#tmhmm_loading");
			
			load();
		},
		success:function(json) {
//			if( json != null ) {
				transMembraneObj.init();
		 		transMembraneObj.paint( eval(json) );
				
				hiding("#tmhmm_loading");
		 		
		 		load();
//		 	}
		}
	});

 	return ret;
}

function drawing(ctx, x, y, path) {
	searchPic = new Image(100,100);
	searchPic.src = path;

	searchPic.onload = function() {
      ctx.drawImage(this, x, y);
    };
}

function drawingTM(ctx, x, y, width, height, path) {
	searchPic = new Image(100,100);
	searchPic.src = path;

	searchPic.onload = function() {
      ctx.drawImage(this, x, y, width, height);
    };
}

var transMembraneObj = {
	Tmhelix_width : 0,
	Tmhelix_height : 0,
	Tmhelix_x : 0,
	Tmhelix_y : 0,
	canvas : null,
	screen_width : 0,
	outside_height : 0,
	inside_height : 0,
	max_TMhelix_width : 0,
	realCanvas : null,
	init : function() {
		this.canvas = document.getElementById("canvas");
		this.screen_width = this.canvas.width;
		
		this.Tmhelix_width = this.screen_width;
		this.Tmhelix_height = 50;
		this.Tmhelix_x = 0;
		this.max_TMhelix_width = 20; 			//TMhelix의 최대 넓이
		this.Tmhelix_y = (this.canvas.height/2)-(this.Tmhelix_height/2);
		
		this.outside_height = this.Tmhelix_y - 80;
		this.inside_height = this.Tmhelix_y + this.Tmhelix_height + 100;
		
		this.realCanvas = document.getElementById("realCanvas");
	},
	paint : function( obj ) {
		var img = document.getElementById("cellbg1");
		var img2 = document.getElementById("cellbg2");
		var img3 = document.getElementById("cellbg3");
		var img4 = document.getElementById("cellbg4");
		
		var cell_inside_only = document.getElementById("cell_inside_only");
		var cell_outside_only = document.getElementById("cell_outside_only");

		var tmImg = new Image();
		tmImg.src = img.src;
		tmImg.myObj = this;
		tmImg.onload = function () {				
//			var base = $("#subcellular");
			
			var ctx = canvas.getContext("2d");
			
			var realctx = realCanvas.getContext("2d");
			
			var sub_TMhelix = document.getElementById("sub_TMhelix");
			var sub_inside = document.getElementById("sub_inside");
			var sub_outside = document.getElementById("sub_outside");

			if(obj == null) {
				//TMhelix option
			    var pattern = ctx.createPattern(tmImg, "repeat");
			    ctx.fillStyle = pattern;
			    ctx.fillRect(this.myObj.Tmhelix_x, this.myObj.Tmhelix_y, this.myObj.Tmhelix_width, this.myObj.Tmhelix_height);
				
				ctx.fillStyle = "#dcdcdc";
				ctx.fillRect(0, 0, this.myObj.Tmhelix_width, this.myObj.Tmhelix_y);
				
				ctx.fillStyle = "#cdcdcd";
				ctx.fillRect(0, this.myObj.Tmhelix_y+this.myObj.Tmhelix_height, this.myObj.Tmhelix_width, this.myObj.canvas.height);
				
				//inside font option
				ctx.fillStyle = "#fff";
				ctx.font = "50px Arial Unicode MS";
				ctx.fillText("No result", canvas.width/3, canvas.height/2);
			} else {
				//TMhelix option
			    var pattern = ctx.createPattern(tmImg, "repeat");
			    ctx.fillStyle = pattern;
			    ctx.fillRect(this.myObj.Tmhelix_x, this.myObj.Tmhelix_y, this.myObj.Tmhelix_width, this.myObj.Tmhelix_height);
				
				ctx.fillStyle = "#ebf9ba";
				ctx.fillRect(0, 0, this.myObj.Tmhelix_width, this.myObj.Tmhelix_y);
				
				ctx.fillStyle = "#c9eee8";
				ctx.fillRect(0, this.myObj.Tmhelix_y+this.myObj.Tmhelix_height, this.myObj.Tmhelix_width, this.myObj.canvas.height);
				
				//inside font option
				ctx.fillStyle = "#9fc80d";
				ctx.font = "20px Comic Sans MS";
				ctx.fillText("OutSide", this.myObj.Tmhelix_width/4, this.myObj.outside_height);
				
				//outside font option
				ctx.fillStyle = "#008570";
				ctx.font = "20px Comic Sans MS";
				ctx.fillText("InSide",this.myObj.Tmhelix_width/2, this.myObj.inside_height);
					
				var TMhelix_count=0;
				var link_count=0;
				
				//스케일링 팩터
				var unit = parseFloat(this.myObj.screen_width / obj.length);
				
				//1. TMHelix 및 link(inside+outside)의 개수 파악
				for (var i = 0; i < obj.elementList.length; i++) {
					if( obj.elementList[i].location == "TMhelix" )	TMhelix_count++;
					else											link_count++;
				}
				
				//TMhelix과 link의 길이를 같게 표현
				var unit_TMhelix = this.myObj.screen_width / (TMhelix_count + link_count);
				var unit_link = this.myObj.unit_TMhelix;
				
				//TMhelix가 최대 넓이보다 크면 max_TMhelix_width값으로 표현
				if(unit_TMhelix > this.myObj.max_TMhelix_width) {
					unit_TMhelix = this.myObj.max_TMhelix_width;
					unit_link = (this.myObj.screen_width - (unit_TMhelix*TMhelix_count))/link_count;
				}
	
				var xPos = 0;
				for (var i = 0; i < obj.elementList.length; i++) {
					var name = obj.elementList[i].location;
					var start = obj.elementList[i].start;
					var end = obj.elementList[i].end;
	
					var TMhelix_yPosition = this.myObj.Tmhelix_y - ((img2.height - this.myObj.Tmhelix_height)/2);
					
					var curve_height = 100;
	
					if( name == "inside") {
						
						ctx.beginPath();
						ctx.lineWidth = 3;
						ctx.strokeStyle = "#008570";
	
						//outside 또는 inside만 있는 경우
						if(obj.elementList.length == 1){
							drawing(ctx, canvas.width/2-cell_inside_only.width, TMhelix_yPosition+curve_height, cell_inside_only.src);
						} else{
							var x = 0;
							var y = 0;
	
							if(i == 0) {
								x = unit_link - (unit_TMhelix/2);
								y = TMhelix_yPosition+img2.height;
								
								if(img2.naturalWidth > unit_TMhelix)	x -= ((img3.width/2) - unit_TMhelix);
								
								drawing(ctx, x, y, img3.src);
	
							}else if(i == obj.elementList.length-1) {
								x = xPos - unit_TMhelix - (unit_TMhelix/2);
								y = TMhelix_yPosition+img2.height;
								
								if(img2.naturalWidth > unit_TMhelix)	x -= ((img3.width/2) - unit_TMhelix);
								
								drawing(ctx, x, y, img3.src);
							}else {
								// quadratic curve
								ctx.moveTo( xPos-(unit_TMhelix/2) , TMhelix_yPosition+img2.height );
								ctx.quadraticCurveTo(xPos+(unit_link/2), TMhelix_yPosition+img2.height+curve_height, xPos+unit_link+(unit_TMhelix/2), TMhelix_yPosition+img2.height);
							}
						}
						
						ctx.stroke();
						
					}else if( name == "outside") {
						ctx.beginPath();
						ctx.lineWidth = 3;
						ctx.strokeStyle = "#9fc80d";
						
						//outside 또는 inside만 있는 경우
						if(obj.elementList.length == 1){
							drawing(ctx, canvas.width/2-cell_inside_only.width, TMhelix_yPosition+img2.height-curve_height, cell_outside_only.src);
						} else{
							if(i == 0) {
								x = unit_link-(img4.width/2)+(unit_TMhelix/2);
								y = TMhelix_yPosition-img4.height;
								
	//							if(img2.naturalWidth > unit_TMhelix)	x += unit_link+unit_TMhelix-(img4.width/2);
								
								drawing(ctx, x, y, img4.src);
								
							}else if(i == obj.elementList.length-1) {//xPos-(unit_TMhelix/2)-(img4.width/2)
								x = xPos-unit_TMhelix-(unit_TMhelix/2);
								y = TMhelix_yPosition-img4.height;
								
								if(img2.naturalWidth > unit_TMhelix)	x += unit_TMhelix-(img4.width/2);
	
								drawing(ctx, x, y, img4.src);
	
							}else {
								// quadratic curve
								ctx.moveTo(xPos-(unit_TMhelix/2), TMhelix_yPosition);
								ctx.quadraticCurveTo(xPos+(unit_link/2), TMhelix_yPosition-curve_height, xPos+unit_link+(unit_TMhelix/2), TMhelix_yPosition);
							}
						}
						ctx.stroke();
						
					} else {
						drawingTM(ctx, xPos, TMhelix_yPosition, unit_TMhelix, img2.height, img2.src);
					}
	
					if( name=="TMhelix" ) {
						xPos += unit_TMhelix;
					} else {
						xPos += unit_link;
					}
	
					start=start-1;
					end=end-1;
					
					//화면 비율에 맞게 스케일링
					var newStart = parseFloat(unit) * parseInt(start);
					var newWidth = parseFloat(unit) * parseInt(parseInt(end) - parseInt(start) + 1);
					
					if(name == "inside")
						realctx.drawImage(sub_inside, newStart, sub_TMhelix.height/2, newWidth, sub_inside.height);
					else if (name == "outside")
						realctx.drawImage(sub_outside, newStart, sub_TMhelix.height/2, newWidth, sub_outside.height);
					else
						realctx.drawImage(sub_TMhelix, newStart, 0, newWidth, sub_TMhelix.height);
				}
			}
	  	};
	  	
		$("#tmhmmPanel").css("height", realCanvas.height + parseInt($("#realCanvas").css("margin-top")) + canvas.height + $(".tmm-title").height() );
	}
};

//$(document).ready(function(){
//	var ajaxRetObj = getTMHMMMessageFromServer();
//
//  	if( ajaxRetObj.status != 500 ) {
//  		var json = jQuery.parseJSON( ajaxRetObj.responseText );
//
//  		transMembraneObj.init();
//  		transMembraneObj.paint( eval(json) );
//  	}
//});