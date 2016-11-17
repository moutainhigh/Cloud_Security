//----------------topJs----------------
$("#top .nav").find('li').hover(function(){
	$(this).find(".list-inner").slideDown(300);
	//$(this).siblings().find('.list-inner').slideUp(100);
},function(){
	$(this).find('.list-inner').stop(true,true).slideUp(100);
});
/*$("#top .nav").find('li').mouseleave(function(){
	$(this).find('.list-inner').slideUp(100);
});*/
