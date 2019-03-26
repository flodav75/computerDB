(function ( $ ) {
	
	var regex = /^([0-2][0-9]|(3)[0-1])(\)(((0)[0-9])|((1)[0-2]))(\-)\d{4}$/i;
	var intro = $("#introduced");
	var discont = $("#discontinued");
	
	if(!intro.val()){
		discont.prop('disabled', true);
	}
	
	intro.change(function(){
		if(!intro.val()){
			discont.prop('disabled', true);
		}else if(!regex.test(intro.val())){
			intro.css("border","1px solid #ff2d00").parent().append("<p id='warning' style='color:#ff2d00'>Invalid date format (dd-mm-yyyy)</p>");
			discont.prop('disabled', true);
		}else{
			intro.css("border","1px solid #10d600");
			$("#warning").remove();
			discont.prop('disabled', false);
		}
	})
	
	discont.change(function(){
		
		var introDate = new Date(intro.val().slice(6, 10),intro.val().slice(3, 5),intro.val().slice(0, 2));
		var discontDate = new Date(discont.val().slice(6, 10),discont.val().slice(3, 5),discont.val().slice(0, 2));
		
		if($("#warning").length > 0) $("#warning").remove();
		
		if(!regex.test(discont.val())){
			discont.css("border","1px solid #ff2d00").parent().append("<p id='warning' style='color:#ff2d00'>Invalid date format (dd-mm-yyyy)</p>");
		}else if(introDate.getTime()>discontDate.getTime()){
			discont.css("border","1px solid #ff2d00").parent().append("<p id='warning' style='color:#ff2d00'>discontinued date can't be before introduced</p>");
		}else{
			discont.css("border","1px solid #10d600");
		}
	})
}( jQuery ));