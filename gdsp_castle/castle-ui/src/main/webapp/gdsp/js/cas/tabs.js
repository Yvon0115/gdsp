/**
 * @class
 * @name tabs extend
 * @description <jquery methods class> 页签扩展
 */
define(function() {
	$.fn.tabs = function(){
		/**
		 * 添加按钮绑定点击事件处理
		 */
		function _clickEventHandle($el,$ul,$lis,ulsw){
			var $leftbtn = $("<button class='btn btn-default tabs-btn tabs-left-btn'><i class='fa fa-chevron-left'></i></button>");
			var $rightbtn = $("<button class='btn btn-default tabs-btn tabs-right-btn'><i class='fa fa-chevron-right'></i></button>");
			$el.parent().append($leftbtn).append($rightbtn);
			var index = 0
			,oneWidth = 0//移动宽度
			,scrollSum = 0//卷进去总宽度
			,liSum = 0;//li总长度
			$rightbtn.on("click",function(e){
				if (liSum - scrollSum < ulsw) {
		            return false;
		        }
				oneWidth = oneWidth - $lis.eq(index).width();
		        $ul.stop(true, false).animate({
		        	marginLeft: oneWidth
		        }, 300);
		        scrollSum = scrollSum + $lis.eq(index).width() + 2;
		        index = index + 1;
			});
			for (var i = 0; i < $lis.length; i++) {
		        liSum = $lis.eq(i).width() + 2 + liSum;
		    }
			$leftbtn.on("click",function(e){
				if (index <= 0) {
		            return false;
		        }
				index = index - 1;
		        oneWidth = oneWidth + $lis.eq(index).width();
		        $ul.stop(true, false).animate({
		            marginLeft: oneWidth
		        }, 300);
		        scrollSum = scrollSum - $lis.eq(index).width() - 2;
			});
		}
		return this.each(function(){
			var o=this,$el=$(this)
			,id = $el.attr("id")
			,$ul = $el.find("ul.nav.nav-tabs.oneline-tabs")
			//多页签导航栏宽度,加1，因为各浏览器计算宽度的时候有差异
			,ulsw = $ul[0].clientWidth + 1;
			if(ulsw <= 0){
				return;
			}
			var $lis = $($ul[0]).find("li"),liswidth=0;
			for(var i = 0; i < $lis.length; i++){
				liswidth += $lis[i].clientWidth;
			}
			if(ulsw < liswidth){
				_clickEventHandle($el,$ul,$lis,ulsw);
			}
			$el.on("draw",function(e,o){
				$el.initPageUI();
			});
			$el.attr("init",true);
		});
	}
});