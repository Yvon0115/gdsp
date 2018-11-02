<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleGrid id="grid_array" target="#queryGrid" data=refreshData gtitle="demo" width="800"
	height="350" columnBorders=true localPaging=false autoFitable=false numCellShow=false showTitle=false 
	showBottom=false freezeCols="0" flexWidth=false flexHeight=false freezeRows="0" checkable=false/>
<@c.PageData page=simpleGridPage?default("")/>