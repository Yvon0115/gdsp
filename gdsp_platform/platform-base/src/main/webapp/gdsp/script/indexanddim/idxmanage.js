$(function(){
    //判断浏览器是否支持placeholder属性
    supportPlaceholder='placeholder'in document.createElement('input'),
    placeholder=function(input){
        var text = "指标表",
        defaultValue = input.defaultValue;
        if(!defaultValue){
        	$(this).val(text).addClass("phcolor");
        }
        input.focus(function(){
            if(input.val() == text){
                $(this).val("");
            }
        });
        input.blur(function(){
            if(input.val() == ""){
                $(this).val(text).addClass("phcolor");
            }
        });
        //输入的字符不为灰色
        input.keydown(function(){
            $(this).removeClass("phcolor");
        });
    };
    //当浏览器不支持placeholder属性时，调用placeholder函数
    if(!supportPlaceholder){
        var placehold = $("#indexTableName_label").first();
        placehold.text("指标表");
        if(placehold.attr("type") == "text"){
            placeholder(placehold);
        }
    }
    setTimeout(function(){
    	$("#indexTableName_label").first().text("指标表");
    	$("#indexTableName_label").first().val("指标表");
    },50);
});


$(function(){
    //判断浏览器是否支持placeholder属性
    supportPlaceholder='placeholder'in document.createElement('input'),
    placeholder=function(input){
        var text = "归属部门",
        defaultValue = input.defaultValue;
        if(!defaultValue){
        	$(this).val(text).addClass("phcolor");
        }
        input.focus(function(){
            if(input.val() == text){
                $(this).val("");
            }
        });
        input.blur(function(){
            if(input.val() == ""){
                $(this).val(text).addClass("phcolor");
            }
        });
        //输入的字符不为灰色
        input.keydown(function(){
            $(this).removeClass("phcolor");
        });
    };
    //当浏览器不支持placeholder属性时，调用placeholder函数
    if(!supportPlaceholder){
        var placehold = $("#comedepart_label").first();
        placehold.text("归属部门");
        if(placehold.attr("type") == "text"){
            placeholder(placehold);
        }
    }
    setTimeout(function(){
    	$("#comedepart_label").first().text("归属部门");
    	$("#comedepart_label").first().val("归属部门");
    },50);
});

function saveform() {
	var peicision = $("#peicision").val();
	if(peicision == null || peicision == "") {
		peicision = 2;
	}
	$("#peicision").val(peicision);
	$("#indexInfoForm").submit();
}

function saveForm(){
	$fileName=$("#excelFile").val();
	$fx = $fileName.substring($fileName.lastIndexOf("."));
	$fx = $fx.toLocaleLowerCase();
	if($fx == '.xls'||$fx == '.xlsx'){
		$("#inportForm").submit();
	}else{
		$F.messager.warn("请选择正确的文件",{"label":"确定"});
		return;
	}
}

function clearData(){
	$("#indexCode").val('');
	$("#indexName").val('');
	$("#indexTableName_label").val('');
	$("#indexTableName_value").val('');
	$("#comedepart_label").val('');
	$("#comedepart").val('');
	}