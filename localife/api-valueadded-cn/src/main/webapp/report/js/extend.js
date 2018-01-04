var jq = top.jQuery;
/**
 * 翻页设计
 */
function submitPage(){
	var params = $("[name="+currentTabCode+"_PagebleFormdfads]").eq(0).serialize();
	var url = "stat/detail.html?"+params;
    $.post(url,null,function(data){
    	$("#"+currentTabCode).html(data);
    },"text");
}


function showCardDetail(cardNumber){
	var winId = 'carddetail';
	jq.createWin({
        title:"促销卡详情",
        url:'card/showCardDetail.html?cardNumber='+cardNumber,
        height:400,
        width:720,
        winId:winId,
        buttons:[],
		onClose:function(targetjq){
		},
		onComplete:function(dailog,targetjq){
			
		}
    });
}

//显示活动信息备注
function showRemark(id,remarkType){
	var winId="showRemark_";
	jq.createWin({
        title:"活动备注",
        url:'remark/show.html?contentId='+id+'&remarkType='+remarkType,
        height:380,
        width:650,
        winId:winId
    });
}

