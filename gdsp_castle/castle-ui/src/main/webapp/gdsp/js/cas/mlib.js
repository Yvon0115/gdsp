/**
 * @class
 * @name $F.mlib
 * @description <Object> 基于$F的方法库，主要用于ajax回调,通过方法关键字进行回调
 * @example 例1：$F.mlib.getFunction("closeDialog"),取得关闭对话框的方法
 */
define(["cas/core","cas/messager","cas/options"],function($F,$messager,$options) {


    /**
     * 表单提交后的方法处理
     */
    var that = {
        alibs:{
            dataloader:function(options,json){
                var target = json.dataloader||(options.toggle?$(options.toggle).attr("dataloader"):null);
                $(target).dataloader("load");
                return true;
            },
            switchtab:function(options,json){
                var target = json.switchtab||(options.toggle?$(options.toggle).attr("switchtab"):null)
                    ,url=json.switchtaburl||(options.toggle?$(options.toggle).attr("switchtaburl"):null)
                    ,reload=json.switchtabreload||(options.toggle?$(options.toggle).attr("switchtabreload"):null)
                    ,callback=json.switchtabcallback||(options.toggle?$(options.toggle).attr("switchtabcallback"):null)
                if(!target)
                    return true;
                var $t=$(target);
                if(url) {
                    $t.attr("href", url);
                    if(reload=="true")
                        $t.attr("reload", "true");
                    if(callback)
                        $t.attr("callback", callback);
                }
                $t.tab("show");
                return true;
            },
            pageload:function(options,json){
                var target = json.pageload||(options.toggle?$(options.toggle).attr("pageload"):null)
                    ,url=json.pageurl||(options.toggle?$(options.toggle).attr("pageurl"):null)
                    ,isGlobal=json.globalload===true||json.globalload==="true"||(options.toggle?$(options.toggle).attr("globalload")==="true":false)
                if(!isGlobal){
                    var $t=$F(target);
                    if(url){
                        $t.loadUrl(url);
                        return true;
                    }
                    url = $t.attr("pageurl");
                    if(url){
                        $t.loadUrl(url);
                        return true;
                    }
                }
                if(url){
                    window.location=url;
                }else{
                    window.location.reload()
                }
                return true;
            }
        },
        blibs:{
            confirm:function(options){
                var $toggle=options.toggle?$(options.toggle):null
                    ,prompt=options.confirm||($toggle?$toggle.attr("confirm"):null)
                if (prompt) {
                    prompt = prompt.evalTemplate($toggle);
                    if(!prompt)
                        return;
                    $messager.confirm(prompt, {
                        callback: function(ok){
                            return ok&&$F.ajax.ajaxCall(options);
                        }
                    });
                    return false;
                }
                return true;
            },
            checker:function(options){
                var $toggle=options.toggle?$(options.toggle):null
                    ,name = options.checker||($toggle?$toggle.attr("checker"):null)
                    ,box = options.checkerBox||($toggle?$toggle.attr("checker-box"):document)
                    ,$box = $(box);

                if(!name)
                    return true;
                var vs = [];
                $("input:checkbox[name='"+name+"']:checked",$box).each(function(){
                    vs.push($(this).val());
                });
                if(vs.length>0){
                    if(vs.length == 1)
                        vs = vs[0];
                    var data = options['data'];
                    if(!data){
                        data = options['data'] = {};
                    }
                    data[name]=vs;
                }else{
                    $messager.warn("未选中任何数据！");
                    return false;
                }
                return true;
            }
        },
        registerFunction:function(name,func){

        },
        /**
         * ajax 远程调用
         */
        getCallBefore: function (name,defaultFunction) {
            return this.blibs[name]||defaultFunction;
        },
        /**
         * ajax 远程调用
         */
        getCallBack: function (name,defaultFunction) {
            return this.alibs[name]||defaultFunction;
        }
    };
    $F.mlib = that;
    return that;
});
