define(["cas/reference"],{
    okRefMultiTree:function(){
        var ids=[],names=[],types=[];
        $F("li[ckbox=true]>div>a").each(function(){
            var $o=$(this);
            ids.push($o.attr("value"));
            names.push($o.html());
            types.push($o.attr("wmtype"));
        })
        $.closeReference({value:ids.join(","),text:names.join(","),type:types.join(",")})
    }
})