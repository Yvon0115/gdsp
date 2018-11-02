/**
 * 表格扩展
 */
define(["cas/core","cas/utils","cas/options",localeFile,"bootstrap/bootstrap","plugins/datatables/jquery.dataTables","link!plugins/datatables/dataTables"],function($F,$utils,$options,$lang) {
	/**
	 * 扩展datatable的分页
	 */
	$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings ){
		return {
			"iStart":         oSettings._iDisplayStart,
			"iEnd":           oSettings.fnDisplayEnd(),
			"iLength":        oSettings._iDisplayLength,
			"iTotal":         oSettings.fnRecordsTotal(),
			"iFilteredTotal": oSettings.fnRecordsDisplay(),
			"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
			"iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
		};
	};

	$.extend( $.fn.dataTableExt.oPagination, {
		"bootstrap-fast": {
			"fnInit": function( oSettings, nPaging, fnDraw ) {
				var oLang = oSettings.oLanguage.oPaginate;
				var fnClickHandler = function ( e ) {
					e.preventDefault();
					if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
						fnDraw( oSettings );
					}
				};

				$(nPaging).addClass('pagination').append(
					'<ul>'+
						'<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
						'<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
					'</ul>'
				);
				var els = $('a', nPaging);
				$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
				$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
			},

			"fnUpdate": function ( oSettings, fnDraw ) {
				var iListLength = $options.dataTable.pageListLength||10;
				var oPaging = oSettings.oInstance.fnPagingInfo();
				var an = oSettings.aanFeatures.p;
				var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

				if ( oPaging.iTotalPages < iListLength) {
					iStart = 1;
					iEnd = oPaging.iTotalPages;
				}
				else if ( oPaging.iPage <= iHalf ) {
					iStart = 1;
					iEnd = iListLength;
				} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
					iStart = oPaging.iTotalPages - iListLength + 1;
					iEnd = oPaging.iTotalPages;
				} else {
					iStart = oPaging.iPage - iHalf + 1;
					iEnd = iStart + iListLength - 1;
				}

				for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
					// remove the middle elements
					$('li:gt(0)', an[i]).filter(':not(:last)').remove();

					// add the new list items and their event handlers
					for ( j=iStart ; j<=iEnd ; j++ ) {
						sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
						$('<li '+sClass+'><a href="#">'+j+'</a></li>')
							.insertBefore( $('li:last', an[i])[0] )
							.bind('click', function (e) {
								e.preventDefault();
								oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
								fnDraw( oSettings );
							} );
					}

					// add / remove disabled classes from the static elements
					if ( oPaging.iPage === 0 ) {
						$('li:first', an[i]).addClass('disabled');
					} else {
						$('li:first', an[i]).removeClass('disabled');
					}

					if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
						$('li:last', an[i]).addClass('disabled');
					} else {
						$('li:last', an[i]).removeClass('disabled');
					}
				}
			}
		}
	});
	
	$.fn.datatable=function(attrs){
		attrs = attrs||["sDom","sAjaxSource","iDeferLoading"];
		function addColumnOptions(el,index,aoColumns,aaSortingFixed,aaSorting,orderBys,orders){
			var $th = $(el)
			,columnObj = {}
			,name = $th.attr("column")
			,sortable = $th.attr("sortable")
			,sClass = $th.attr("cellClass")
			,sType = $th.attr("cellType")
			,columnWidth = $th.attr("columnWidth")
			,searchable = $th.attr("searchable")
			,visible = $th.attr("visible")
			,defaultContent = $th.attr("defaultContent");
			
			columnObj["sName"] = name;
			if(sClass)
				columnObj["sClass"]=sClass;
			if(sType)
				columnObj["sType"]=sType;
			if(columnWidth)
				columnObj["sWidth"]=columnWidth;
			if(searchable)
				columnObj["bSearchable"]=searchable;
			if(visible == "false")
				columnObj["bVisible"]=false;
			if(defaultContent)
				columnObj["sDefaultContent"]=defaultContent;
			if(sortable == "false"){
				columnObj["bSortable"] = false;
			}else{
				var sorting = $th.attr("sorting")
				,fixedSort = $th.attr("fixedSort")
				,defaultSort = $th.attr("defaultSort");
				
				if(sorting){
					columnObj["asSorting"] = sorting.split(",");
				}
				if(fixedSort){
					aaSortingFixed.push([index,fixedSort]);
				}
				
				if(defaultSort){
					aaSorting.push([index,defaultSort]);
				}else if(orderBys){
					for(var i=0; i< orderBys.length; i++){
						if(orderBys[i].toLowerCase() != name.toLowerCase())
							continue;
						aaSorting.push([count,orders[i]]);
						orderBys.splice(i,1);
						orders.splice(i,1);
						break;
					}
				}
				
			}
			aoColumns.push(columnObj);
			return columnObj;
		}
		function dtOptions(el){
			var $dt = $(el)
			,aoColumns = []
			,aaSorting = []
			,aaSortingFixed =[]
			,defaultSort = $dt.attr("defaultSort")
			,defaultOrderBy = $dt.attr("defaultOrderBys")
			,defaultOrder = $dt.attr("defaultOrders")
			,orderBys = defaultOrderBy?defaultOrderBy.split(','):null
			,orders = defaultOrder?defaultOrder.split(","):null
			,fnServerParams = $dt.attr("fnServerParams")
			,sorts=defaultSort?defaultSort.split(","):null;
			if(sorts&&sorts.length>0){
				orderBys = [];
				orders=[];
				$(sorts).each(function(i){
					var s = this.split(" ");
					orderBys.push(s[0]);
					if(s[1]=="")
						s[1]="asc";
					orders.push(s[1]);
				});
			}
			$(">thead>tr>th",$dt).each(function(idx){
				addColumnOptions(this,idx,aoColumns,aaSortingFixed,aaSorting,orderBys,orders)
			});
			
			var config = $utils.collectOptions($dt,attrs);
			config["aoColumns"]=aoColumns;
			config["aaSortingFixed"]=aaSortingFixed;
			config["aaSorting"]=aaSorting;
			config["bProcessing"]=true;
			if(fnServerParams){
				config["fnServerParams"]= new Function(fnServerParams+"("+data+")");
			}
			var options = $.extend({},$options.dataTable,$lang.dataTable,config);
			return options;
		}
		return this.each(function () {
			var $el = $(this),option=dtOptions(this);
			var dt = $el.dataTable(option);
			$el.on("draw",function(e,o){
				$el.initPageUI();
			});
			$el.attr("init",true);
		});
	}
		
});