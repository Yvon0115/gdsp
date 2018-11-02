/**
 * @author Lance Snider - lance@lancesnider.com
*/

//可修改的参数
var goalAmount = 4;//确定最大值
//var currentAmount = 0;//默认值
var animationTime = 1000;//动画时长，ms
var numberPrefix = "";//值前缀
var numberSuffix = "";//值后缀
var tickMarkSegementCount = 4;//切分的段数，每段40px
var widthOfNumbers = 130;//距左侧的距离
//添加:
var currentAmountPlus2 = "";

//standard resolution images
var glassTopImg = __jsPath+"/plugins/thermometer/images/glassTop.png";
var glassBodyImg = __jsPath+"/plugins/thermometer/images/glassBody.png";
var redVerticalImg = __jsPath+"/plugins/thermometer/images/redVertical.png";
var tooltipFGImg = __jsPath+"/plugins/thermometer/images/tickShine.png";
var glassBottomImg = __jsPath+"/plugins/thermometer/images/glassBottom.png";
var tootipPointImg = __jsPath+"/plugins/thermometer/images/tooltipPoint.png";
var tooltipMiddleImg = __jsPath+"/plugins/thermometer/images/tooltipMiddle.png";
var tooltipButtImg = __jsPath+"/plugins/thermometer/images/tooltipButt.png";
//五个值域色值
//正常
var norRangeImg = __jsPath+"/plugins/thermometer/images/norRangeImg.png";
var norRangeBottomImg = __jsPath+"/plugins/thermometer/images/norRangeBottomImg.png";
//冷
var coldRangeImg = __jsPath+"/plugins/thermometer/images/coldRangeImg.png";
var coldRangeBottomImg = __jsPath+"/plugins/thermometer/images/coldRangeBottomImg.png";
//过冷
var verColdRangeImg = __jsPath+"/plugins/thermometer/images/verColdRangeImg.png";
var verColdRangeBottomImg = __jsPath+"/plugins/thermometer/images/verColdRangeBottomImg.png";
//热
var hotRangeImg = __jsPath+"/plugins/thermometer/images/hotRangeImg.png";
var hotRangeBottomImg = __jsPath+"/plugins/thermometer/images/hotRangeBottomImg.png";
//过热
var verHotRangeImg = __jsPath+"/plugins/thermometer/images/verHotRangeImg.png";
var verHotRangeBottomImg = __jsPath+"/plugins/thermometer/images/verHotRangeBottomImg.png";
//图例
var legendImg = __jsPath+"/plugins/thermometer/images/termLegend.jpg";
/////////////////////////////////////////
// ------ don't edit below here ------ //
/////////////////////////////////////////

var arrayOfImages;
var imgsLoaded = 0;
var tickHeight = 40;
var mercuryHeightEmpty = 0;
var numberStartY = 6;
var thermTopHeight = 13;
var thermBottomHeight = 51;
var tooltipOffset = 15; 
var heightOfBody;
var mercuryId;
var tooltipId;
var resolution2x = false;

//start once the page is loaded
$( document ).ready(function() {
	if(currentAmount!=''){
		$('#goal-thermometer').show();
		$('#goal-thermometerisnull').hide();
		currentAmountPlus2= parseFloat(currentAmount)+2;
		determineImageSet();
	}else{
		$('#goal-thermometer').hide();
		$('#goal-thermometerisnull').show();
	}
});
function changeHeight(){
	currentAmount = $('#currentAmount').val();
	currentAmountPlus2= parseFloat(currentAmount)+2;
	determineImageSet();
}

//this checks if it's the high or normal resolution images(不用)
//判断值在哪值域范围内，切换不同的色值图像。
function determineImageSet(){
	if(currentAmountPlus2 < 0) {
		currentAmountPlus2 = 0;
	} else if(currentAmountPlus2 > 4) {
		currentAmountPlus2 = 4;
	}
	if(parseFloat(currentAmount) <= -1){
		redVerticalImg = verColdRangeImg;
		glassBottomImg = verColdRangeBottomImg;
	} else if(parseFloat(currentAmount) > -1 && parseFloat(currentAmount) <= -0.5){
		redVerticalImg = coldRangeImg;
		glassBottomImg = coldRangeBottomImg;
	} else if(parseFloat(currentAmount) > -0.5 && parseFloat(currentAmount) <= 0.5){
		redVerticalImg = norRangeImg;
		glassBottomImg = norRangeBottomImg;
	} else if(parseFloat(currentAmount) > 0.5 && parseFloat(currentAmount) < 1){
		redVerticalImg = hotRangeImg;
		glassBottomImg = hotRangeBottomImg;
	} else {
		redVerticalImg = verHotRangeImg;
		glassBottomImg = verHotRangeBottomImg;
	}
	
	createGraphics();
}

//visually create the thermometer
function createGraphics(){
	
	//add the html
	$("#goal-thermometer").html(
		"<div class='col-md-2 col-sm-2'><img src='"+legendImg+"'></img></div>" +
		"<div id='therm-numbers' class="+'\"col-md-2 col-sm-2\"'+ ">" + 
		"</div>" + 
		"<div id='therm-graphics' class="+'\"col-md-9 col-sm-9\"'+ ">" + 
			"<img id='therm-top' src='"+glassTopImg+"'></img>" + 
			"<img id='therm-body-bg' src='"+glassBodyImg+"' ></img>" + 
			"<img id='therm-body-mercury' src='"+redVerticalImg+"'></img>" + 
			"<div id='therm-body-fore'></div>" + 
			"<img id='therm-bottom' src='"+glassBottomImg+"'></img>" + 
			"<div id='therm-tooltip'>" + 
				"<img class='tip-left' src='"+tootipPointImg+"'></img>" + 
				"<div class='tip-middle'><p>0</p></div>" + 
				"<img class='tip-right' src='"+tooltipButtImg+"'></img>" + 
			"</div>" + 
		"</div>"
	);
	//preload and add the background images
	$('<img/>').attr('src', tooltipFGImg).load(function(){
		$(this).remove();
		$("#therm-body-fore").css("background-image", "url('"+tooltipFGImg+"')");
		checkIfAllImagesLoaded();
	});
	
	$('<img/>').attr('src', tooltipMiddleImg).load(function(){
		$(this).remove();
		$("#therm-tooltip .tip-middle").css("background-image", "url('" + tooltipMiddleImg + "')");
		checkIfAllImagesLoaded();
	});
	
	//adjust the css
	heightOfBody = tickMarkSegementCount * tickHeight;//段数 * 每段高度
	$("#therm-graphics").css("left", widthOfNumbers-30)//设置左侧的距离
	$("#therm-body-bg").css("height", heightOfBody);//设置芯的bg高度
	$("#goal-thermometer").css("height",  heightOfBody + thermTopHeight + thermBottomHeight);//温度计的高度
	$("#therm-body-fore").css("height", heightOfBody);//设置温度计的度量单位格高度
	$("#therm-bottom").css("top", heightOfBody + thermTopHeight);//设置温度计底部的位置
	mercuryId = $("#therm-body-mercury");
	mercuryId.css("top", heightOfBody + thermTopHeight);
	tooltipId = $("#therm-tooltip");
	tooltipId.css("top", heightOfBody + thermTopHeight - tooltipOffset);
	
	//add the numbers to the left
	var numbersDiv = $("#therm-numbers");
	var countPerTick = goalAmount/tickMarkSegementCount;
	
	var commaSepCountPerTick = commaSeparateNumber(countPerTick);
	
	//add the number
	//修改,tickMarkSegementCount+1，显示最下面的刻度。
	for ( var i = 0; i < tickMarkSegementCount+1; i++ ) {
		
		var yPos = tickHeight * i + numberStartY;
		var style = $("<style>.pos" + i + " { top: " + yPos + "px; width:"+widthOfNumbers+"px }</style>");
		$("html > head").append(style);
		var dollarText = commaSeparateNumber(goalAmount - countPerTick * i-2);
		$( numbersDiv ).append( "<div class='therm-number pos" + i + "'>" +dollarText+ "</div>" );
		
	}
	
	//check that the images are loaded before anything
	arrayOfImages = new Array( "#therm-top", "#therm-body-bg", "#therm-body-mercury", "#therm-bottom", ".tip-left", ".tip-right");
	preload(arrayOfImages);
	
};

//check if each image is preloaded
function preload(arrayOfImages) {
	
	for(i=0;i<arrayOfImages.length;i++){
		$(arrayOfImages[i]).load(function() {   checkIfAllImagesLoaded();  });
	}
    
}

//check that all the images are preloaded
function checkIfAllImagesLoaded(){
	$("#goal-thermometer").fadeTo(1000, 1, function(){
		animateThermometer();
	});
}


//animate the thermometer
function animateThermometer(){
	
	var percentageComplete = currentAmountPlus2/goalAmount;//值所占的百分比
	
	var mercuryHeight = Math.round(heightOfBody * percentageComplete); //100
	var newMercuryTop = heightOfBody + thermTopHeight - mercuryHeight; //113

	mercuryId.animate({height:mercuryHeight +1, top:newMercuryTop }, animationTime);
	tooltipId.animate({top:newMercuryTop - tooltipOffset}, {duration:animationTime});
	
	var tooltipTxt = $("#therm-tooltip .tip-middle p");
	
	//tooltip的值动态增加
	//change the tooltip number as it moves
	/*$({tipAmount: 0}).animate({tipAmount: currentAmount}, {
		duration:animationTime,
		step:function(){
			tooltipTxt.html(commaSeparateNumber(this.tipAmount));
		}
	});*/
	tooltipTxt.html(currentAmount);
	//alert(parseFloat(currentAmount));
	
}

//格式化当前值
//format the numbers with $ and commas
function commaSeparateNumber(val){
	val = Math.round(val);
    while (/(\d+)(\d{3})/.test(val.toString())){
      val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
    }
    return numberPrefix + val + numberSuffix;
}
