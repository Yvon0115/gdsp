<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<@c.Link title="修改数据信息" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/conf/datadic/editDimValue.d?id=${row.id}&pk_dicId=${pk_dicId?if_exists}")] >修改</@c.Link>
<@c.Link title="删除数据" icon="glyphicon glyphicon-remove"  action=[c.rpc("${ContextPath}/conf/datadic/deleteDimValue.d?id=${row.id}",{"dataloader":"#dicContents"},{"confirm":"确认删除记录‘${row.dimvl_name?if_exists}’？"})] >删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>

<@c.SimpleTable striped=true titles=["<th width=150px>名称</th>","<th width=150px>编码</th>","<th width=200px>描述</th>","<th width=150px>上级名称</th>""<th width=150px>上级编码</th>",""] keys=["dimvl_name","dimvl_code","dimvl_desc","parentName","parentCode",op] 
ellipsis={"dimvl_name":"150px","dimvl_code":"150px","dimvl_desc":"200px","parentName":"150px","parentCode":"150px"}

data=dataDicValueVO.content checkbox=true/>
<@c.PageData page=dataDicValueVO />