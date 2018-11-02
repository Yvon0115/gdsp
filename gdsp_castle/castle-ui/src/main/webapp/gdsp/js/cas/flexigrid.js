/**
 * 表格扩展
 */
define(["jquery","cas/options",localeFile,"plugins/flexigrid/flexigrid","link!plugins/flexigrid/flexigrid"],function($,$options,$lang) {

    $.fn.initFlexiGrid=function(){
        this.each(function () {
            var $this = $(this)
                ,url=$this.attr("url")
                ,dataType=$this.attr("dataType")||"json"
                ,gTitle=$this.attr("gTitle")
                ,striped=$this.attr("striped")||true
                ,height = $this.attr("height")||"300"
                ,width=$this.width()|| "auto"
                ,resizable=$this.attr("resizable")=="true"||false
                ,method=$this.attr("method")||"POST"
                ,errormsg=$this.attr("errormsg")||$lang.flexigrid.errormsg
                ,usepager=$this.attr("usepager")||false
                ,nowrap=!($this.attr("nowrap")=="false")
                ,page=($this.attr("page")||"1")*1
                ,useRp=!($this.attr("useRp")=="false")
                ,rp=($this.attr("rp"))*1
                ,idProperty=$this.attr("idField")||"id"
                ,pagestat=$this.attr("pagestat")||$lang.flexigrid.pagestat
                ,pagetext=$this.attr("pagetext")||$lang.flexigrid.pagetext
                ,outof=$this.attr("outof")||$lang.flexigrid.outof
                ,params=$this.attr("params")
                ,procmsg=$this.attr("procmsg")||$lang.flexigrid.procmsg
                ,nomsg=$this.attr("nomsg")||$lang.flexigrid.nomsg
                ,autoload=!($this.attr("autoload")=="false")
                ,preProcess=$this.attr("preProcess")||false
                ,addTitleToCell=$this.attr("addTitleToCell")||false
                ,onDragCol=$this.attr("onDragCol")||false
                ,onChangeSort=$this.attr("onChangeSort")||false
                ,onDoubleClick=$this.attr("onDoubleClick")||false
                ,onSuccess=$this.attr("onSuccess")||false
                ,onError=$this.attr("onError")||false
                ,onSubmit=$this.attr("onSubmit")||false
                ,sortname=$this.attr("sortname")
                ,sortorder=$this.attr("sortorder")||"asc"
                ,events=$this.attr("events")
                //16_1.7 Add
                ,showToggleBtn=$this.attr("showToggleBtn")||true
                ,showTableToggleBtn=$this.attr("showTableToggleBtn")||true
                ,minwidth=$this.attr("minwidth")||"30"
                ,minheight=$this.attr("minheight")||"80";

            if(preProcess){
                preProcess=new Function(preProcess);
            }
            if(addTitleToCell){
                addTitleToCell=new Function(addTitleToCell);
            }
            if(onDragCol){
                onDragCol=new Function(onDragCol);
            }
            if(onChangeSort){
                onChangeSort=new Function(onChangeSort);
            }
            if(onDoubleClick){
                onDoubleClick=new Function(onDoubleClick);
            }
            if(onSuccess){
                onSuccess=new Function(onSuccess);
            }

            var cols=[],$colTds=$("thead>tr>th",$this);
            $colTds.each(function(){
                var $o=$(this)
                    ,label=$o.html()
                    ,field=$o.attr("name")||label
                    ,w=$o.attr("width")||200
                    ,sortable=$o.attr("sortable") != "false"
                    ,align=$o.attr("align")||"left"
                cols.push({display:label,name:field,width:w,sortable:sortable,align:align})
            });
            $this.empty();
            var op={
                url:url,
                dataType:dataType,
                title:gTitle,
                striped:striped,
                height:height,
                width:width,
                resizable:resizable,
                method:method,
                errormsg:errormsg,
                usepager:usepager,
                nowrap:nowrap,
                page:page,
                useRp:useRp,
                rp:rp,
                idProperty:idProperty,
                pagestat:pagestat,
                pagetext:pagetext,
                outof:outof,
                params:params?App.jsonEval(params):[],
                procmsg:procmsg,
                nomsg:nomsg,
                autoload:autoload,
                preProcess:preProcess,
                addTitleToCell:addTitleToCell,
                onDragCol:onDragCol,
                onChangeSort:onChangeSort,
                onDoubleClick:onDoubleClick,
                onSuccess:onSuccess,
                onError:onError,
                onSubmit:onSubmit,
                colModel:cols,
                showToggleBtn:showToggleBtn,
                minwidth:minwidth,
                minheight:minheight
            };
            if(sortname){
                op["sortname"]=sortname;
                op["sortorder"]=sortorder;
            }
            $this.flexigrid(op);
            $this.attr("init","true");
        });
    }

});