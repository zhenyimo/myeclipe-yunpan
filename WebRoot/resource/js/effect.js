//»¬¶¯
$(document).ready(function(){
	$(".nav li").hover(function(event){
		var active = $(".nav .active").attr('name');
		var thisname = $(this).attr('name');
		var nameNum = thisname*60;
		var nameNum1 = nameNum + (thisname - active)*5;
		//alert(thisname);
		$(".slide").stop().animate({top: nameNum1},400,function(){
			$(".slide").animate({top: nameNum}, 150);
		})	
	})
})