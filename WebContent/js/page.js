$.fn.showPage=function(count){
	var str='<a href="javascript:void(0)" class="current">1</a>';
	for(var i=2;i<=count;i++){
		str+='<a href="javascript:void(0)">' +i +'</a>';
	}
	var currentObj=$(this);
	currentObj.append($(str));
	
	var objs=currentObj.children("a");
	if(count>10){
		objs.slice(5,count - 5).addClass("show");
		objs.eq(4).after($("<span>...</span>"));
	}
	objs.unbind("click");
	objs.click(function(){
		objs.removeClass("current"); 
		$(this).addClass("current");
		var index=$(this).index()+1;
		findByPage(index,this);
		
		if(index < 5 || index>count-5){
			
			return;
		}
		if(count>10){
			currentObj.children("span").remove();
			
			var nextObj=$(this).nextAll();
			var nextSize=nextObj.length;
			
			if(index>=5){
				nextObj.slice(2,nextSize-5).addClass("show");
				nextObj.slice(0,2).removeClass("show");
				
				var prvObj=$(this).prevAll();
				var prvSize=prvObj.length;
				
				prvObj.removeClass("show");
				
				if(prvSize>=8){
					prvObj.slice(2,index -6).addClass("show");
					
					if($(this).prevAll(".show").length>0){
						$(this).prev().prev().before($("<span>...</span>"));
						
					}
				}
				
				if($(this).nextAll(".show").length>0){
					$(this).next().next().after($("<span>...</span>"));
				}
			}
		}
	})
	return this;
}