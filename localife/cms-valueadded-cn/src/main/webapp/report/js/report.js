function setRptDate(obj){
	var val = $("#REPORTTYPE").val();
	var fullTimeFlag =$("#fullTimeFlag").val();
	var timeFmt="date";
	if(val=="month")timeFmt="yearmonth";
	if(val=="year")timeFmt="year";
	if(fullTimeFlag=="true"){
		timeFmt = "datetime";
	}
	setdayfmt(obj,obj,timeFmt);
}

	
	
function setMonthDate(obj){
	//var timeFmt="yearmonth";
	setmonth(obj,obj);
}

function setYearDate(obj){
	//var timeFmt="yearmonth";
	setyear(obj,obj);
}

function switchOrgan(obj){
	var trCode=$("#trCode").val();
	var level = obj.level;
	var queryId = obj.value;
	/**
	ReportDWRService.switchOrganInfo(trCode,level,queryId,function(data){
	for(attr in data){
		clearSelectObj($(attr));
		$(attr).options.add(new Option("所有","%"));
		for(i=0;i<data[attr].length;i++){
			var id = data[attr][i].id;
			var name = data[attr][i].name;
			$(attr).options.add(new Option(name+"("+id+")",id));
		}
		
	}
	});**/	
}

//生成组织机构序列
function generateOrganSerial(){
	var obj = document.getElementsByTagName("select");
	var organSerial = "";
	for(i=0;i<obj.length;i++){
		var key = obj[i];
		if(key.groupName=="organ"){
			organSerial = organSerial+key.id+",";
		}
	}
	$("#organSerial").val(organSerial);
	return organSerial;
}

function doSubmit(timecell){
	var reportType = $("#reportType").val();
	if(timecell){
		$("#TIMECELL").val(timecell);
	}
	if(reportType=="time"){
		if($("#STARTTIME").val()==""){
			alert("请输入开始日期！");
			return;
		}
		if($("#ENDTIME").val()==""){
			alert("请输入结束日期！");
			return;
		}
	}else{
		if($("#STARTTIME").val()==""){
			alert("请输入统计时间！");
			return;
		}
		$("#ENDTIME").value = $("STARTTIME").value;
	}
	if($("wdcCheck").checked){
		$("WDC").value="1";
	}else{
		$("WDC").value="0";
	}
	document.forms[0].submit();
}
	
function detailInfo(timeInfo,organInfo,unitInfo,type){
	//timeInfo = timeInfo.replace("年","-");
	//timeInfo = timeInfo.replace("月","");
	var timecell = $("#TIMECELL").val();
	var reportType = $("#REPORTTYPE").val();
	var startTime = timeInfo;
	var endTime = timeInfo;
	if(timeInfo==''){
		startTime = $("#STARTTIME").val();
		endTime = $("#ENDTIME").val();
	}
	var organSerial = generateOrganSerial();
	var organArr = organSerial.split(",");
	var organQueryType = $("#organQueryType").val();
	var organParmSerial="";
	for(i=0;i<organArr.length;i++){
		var key = organArr[i];
		if(key!=''){
			var value = $(key).value;
			if(value=='%')value='-9999';
			if(key==organQueryType)value=organInfo;
			organParmSerial=organParmSerial+"&"+key.toUpperCase()+"="+value;
		}
	}
	
	var wdc = $("#WDC").val();
	var trCode = $("#trCode").val();
	if(type=="time"){
		if(timecell=="04"){
			
		}
	}
		/**
		if(reportType=="month"){
			if(timecell=="04"){   //月到天,打开以天为纬度的月报
				reportType = "month";
				timecell="03";
			}else if(timecell=="03"){   //按天统计的月报，打开日报
				reportType = "day";
				timecell="02";		
			}else if(timecell =="06"){  //周报
				reportType = "day"
			}
			
		}else if(reportType=="year"){
			if(timecell=="05"){   //年到月,打开以月为纬度的年报
				timecell="04";
				timeInfo = timeInfo.substring(0,4);
			}else if(timecell=="04"){   //按天统计的月报，打开日报
				reportType = "month";
				timecell="03";		
				timeInfo = timeInfo.substring(0,4)+"-"+timeInfo.substring(5,7);
			}
			
			startTime = timeInfo;
			endTime = timeInfo;
		}else if(reportType=="time"){
			if(timecell=="03"){
				timecell ="02";
			}else if(timecell=="02"){
				timecell="01";
			}
		}else if(reportType=="day"){
			reportType="time";
			timecell="02";
		}
	}
	if(type=="organ"){
		
	}
	**/
	//if(unitInfo&&unitInfo!=null){
		window.open("stat/report.html?REPORTTYPE="+reportType+"&TIMECELL="+timecell+"&WDC="+wdc+
			"&trCode="+trCode+"&STARTTIME="+startTime+"&ENDTIME="+endTime+organParmSerial+"&DETAILTYPE="+type+"&UNITID="+unitInfo);
	//}
	
}
	
function clearSelectObj(obj){
	while(obj.options.length>0){
		obj.options.remove(0);
	}
}
	
function switchReportType(init){
	clearSelectObj($("TIMECELL"));
	var reportType = $("REPORTTYPE").value;
	if(!init){
		$("STARTTIME").value="";
		$("ENDTIME").value="";
	}
	if(reportType=="year"){
		$("TIMECELL").options.add(new Option("按年","05"));
		$("TIMECELL").options.add(new Option("按月","04"));				
		return;
	}
	if(reportType=="month"){
		$("TIMECELL").options.add(new Option("按月","04"));
		$("TIMECELL").options.add(new Option("按天","03"));
		return;
	}
	if(reportType=="day"){
		$("TIMECELL").options.add(new Option("按天","03"));
		$("TIMECELL").options.add(new Option("按小时","02"));
		//$("TIMECELL").options.add(new Option("按十五分钟","01"));
		return;
	}
	if(reportType=="time"){
		$("TIMECELL").options.add(new Option("按小时","02"));
		return;
	}
}

function saveExcel(submitScript){
	$("#opttype").val("export");
	var myform = $("#reportForm");
	var params = {};
	var pm = "";
	var ci = 0;
	myform.find('input').each(function() {
		params[$(this).attr('name')] =  $(this).val();
		if($(this).attr('name') != undefined) {
			if(ci > 0) {
				pm += "&";
			}
			pm += $(this).attr('name') + "=" + $(this).val();
			ci ++;
		}
	});
	myform.find('select').each(function() {
		params[$(this).attr('name')] =  $(this).val();
		if($(this).attr('name') != undefined) {
			if(ci > 0) {
				pm += "&";
			}
			pm += $(this).attr('name') + "=" + $(this).val();
			ci ++;
		}
	});
	var ac = document.forms[0].action;
	ac = ac + "?" + pm + "&downloadFlag=1";
	document.getElementById("downloadifm").src = ac;
	$("#opttype").val("");	
	/*
	$("#opttype").val("export");
	if(submitScript&&submitScript!=""){
			try{
				eval(submitScript);
			}catch(e){
			}
		}
	document.forms[0].submit();
	$("#opttype").val("");	
	*/
}

function excelSelectFile(){
window.open('../excelSelectFile.jsp','','left=550,top=160, width=350, height=80, status=0,resizable=yes,menubar=0,toolbar=0,directories=0,location=0,status=0,scrollbars=0');
}
	
function dataDetail(timeInfo,organinfo,itemId){
	alert(1)
	var reportType = "detail";
	var timecell = $("TIMECELL").value;
	var trCode = $("trCode").value;
	if(timecell=="05"){   //年到月,打开以月为纬度的年报
		timeInfo = timeInfo.substring(0,4);
	}else if(timecell=="04"){   //按天统计的月报，打开日报
		timeInfo = timeInfo.substring(0,4)+"-"+timeInfo.substring(5,7);
	}
	
	var startTime = timeInfo;
	var endTime = timeInfo;
	var organQueryType = $("organQueryType").value;
	var organQueryValue = organinfo;
	window.open("stat/report.html?&REPORTTYPE="+reportType+"&trCode="+trCode+"&STARTTIME="+startTime+"&ENDTIME="+endTime+
			"&DETAILTYPE="+itemId+"&ORGANTYPE="+organQueryType+"&ORGANVALUE="+organQueryValue);
}