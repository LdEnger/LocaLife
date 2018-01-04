//loadDiv 
function loadDiv(msg) {  
    $("<div class=\"datagrid-mask\" style=\"position:fixed; z-index:9999; top:0;\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\" style=\"position:fixed; z-index:9999; top:0;\"></div>").html((msg=='' || msg ==null) ?'正在运行，请稍后...':msg).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}  
  
//hidden Load  
function displayLoad() {  
    $(".datagrid-mask").remove();  
    $(".datagrid-mask-msg").remove();  
} 