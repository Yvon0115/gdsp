<#include "/gdsp/include/main-sidebar-tabs.ftl">
<!-- Content Wrapper. Contains page content -->
<div id="__castlePageContent" class="content-wrapper">
    <#if __decorateContent?ends_with(".jsp")>
        <@include_page path="/gdsp/page/"+__decorateContent/>
    <#else>
        <#include "/gdsp/page/${__decorateContent}.ftl">
    </#if>
</div><!-- /.content-wrapper $(element).children(".box-footer").css("margin-top",tHeight-sum+"px");-->
<script>
    var node = document.getElementsByClassName("content-wrapper")[0];
    if(node){
        node.style.minHeight = window.innerHeight + "px";
    }
    var box_left_node = $(".box-left").height();
    var box_right_node = $(".box-right").height();
    if(box_left_node>box_right_node && box_left_node!=null && box_right_node!=null){
    	document.getElementsByClassName("box-right")[0].style.height = (box_left_node+2)+"px";
    }else if(box_left_node<box_right_node && box_left_node!=null && box_right_node!=null){
    	document.getElementsByClassName("box-left")[0].style.height = (box_right_node+2)+"px";
    }
    $(".col-md-3").css("padding-right","2px");
    
    $(".box").each(function(index,element){
    	if($(element).children(".box-footer").length>0){
    		//给所有的表格body增加结束横线
			$(element).children(".box-body").css("border-bottom","1px solid #ddd");
    	}
    });
    var sHeight = 0;
    var footMargin = 0;
	$(".box").each(function(index,element){
    	if($(element).children(".box-footer").length>0){
    		if(!$(element).children(".box-footer").hasClass("text-center")){
	    		if(sHeight==0)
	    			sHeight = $(element).height();
	    		var sum = 0;
	    		$(element).children().each(function(idx,ele){
	    			if($(ele).outerHeight()!=null){
	    				if(idx!=2)
	    					sum += $(ele).outerHeight();
	    				else
	    					sum += 45;
	    				if(idx==2){
	    					footMargin = $(ele).outerHeight(true)-$(ele).outerHeight();
	    				}
	    			}
	    		})
	    		if(sHeight!=(sum+footMargin)&&sum!=0)
	    			$(element).children(".box-footer").css("margin-top",sHeight-sum+"px");
    		}
    	}
    });
    
    $(document).ajaxStop(function(){
	    $(".box-right").each(function(index,element){
	    	if($(element).children(".box-footer").length>0){
	    		//给所有的表格body增加结束横线
				$(element).children(".box-body").css("border-bottom","1px solid #ddd");
	    	}
	    });
    	$(".box").each(function(index,element){
	    	if($(element).children(".box-footer").length>0){
	    		if(!$(element).children(".box-footer").hasClass("text-center")){
		    		if(sHeight==0)
		    			sHeight = $(element).height();
		    		var sum = 0;
		    		$(element).children().each(function(idx,ele){
		    			if($(ele).outerHeight()!=null){
		    				sum += $(ele).outerHeight();
		    				if(idx==2){
		    					footMargin = $(ele).outerHeight(true)-$(ele).outerHeight();
		    				}
		    				//console.log($(ele).outerHeight());
		    			}
		    		})
		    		//console.log(sum+footMargin);
		    		//console.log(sHeight);
		    		if(sHeight!=(sum+footMargin)&&sum!=0)
		    			$(element).children(".box-footer").css("margin-top",sHeight-sum+"px");
	    		}
	    	}
	    });/**/
	});
    function transExOrCollStatus(obj){
    	if($(obj).hasClass('accordion_expand')){
    		$(obj).removeClass('accordion_expand');
    		$(obj).addClass('accordion_collapse');
    	}else{
    		$(obj).removeClass('accordion_collapse');
    		$(obj).addClass('accordion_expand');
    	}
    }
</script>