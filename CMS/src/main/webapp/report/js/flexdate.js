function ToLenStr(s,len)
{
	s = "" + s;
	while(s.length<len)
		s = "0" + s;
	return s;
}

function CurrentTime()
{ 
    var now = new Date(); 
    var hh = 0+now.getHours(); 
    var mm = 0+now.getMinutes(); 
    var ss = 0+now.getTime() % 60000; 
	ss = (ss - (ss % 1000)) / 1000; 

    var clock = "";
	if(hh <10) clock += '0';
    clock += ''+hh+':'; 
    if (mm < 10) clock += '0'; 
    clock += ''+mm+':'; 
    if (ss < 10) clock += '0'; 
    clock += ''+ss; 

    return(clock); 
} 
function CurrentDate()
{
    var now = new Date(); 
    var yy = now.getYear(); 
    var mm = now.getMonth()+1; 
	if(mm  <10) mm = "0" +mm;
	var dd = now.getDate();
	if(dd <10) dd = "0" +dd;

	var currdate = '' + yy + "-" + mm +"-"+dd;
	return currdate;
}
function CurrentDateProtean(num)
{
	var flag = Math.abs(num);
    var now = new Date(); 
    var yy = now.getYear(); 
    var mm = now.getMonth()+1;
	var dd = now.getDate();
	num = parseInt(num);
	//先添加
	if(flag >= 10000){
		yy = yy+Math.round(num/10000);
	}else if(flag >= 100){
		mm = mm+Math.round(num/100);
	}else{
		dd = dd+num;
	}
	//后判断
	if(mm>12){
		yy += 1;
		mm -= 12;
	}
	var lastDay = showMonthLastDay(yy,mm);
	if(dd > lastDay){
		mm += 1;
		dd -= parseInt(lastDay);
	}

	if(mm  <10) mm = "0" +mm;
	if(dd <10) dd = "0" +dd;

	var currdate = '' + yy + "-" + mm +"-"+dd;
	return currdate;
}
//=========================================================
//摘录自网上，修改人：兰江飞 2007-11-12
function showMonthLastDay(yy,mm)
{
var Nowdate=new Date();
var MonthNextFirstDay=new Date(yy,mm,1);
var MonthLastDay=new Date(MonthNextFirstDay-86400000);
return MonthLastDay.getDate();
}
/*function Date.prototype.toString(){
return this.getFullYear()+"年"+(this.getMonth()+1)+"月"+this.getDate()+"日";
}*/
//==========================================================
function CurrentDateTime()
{
	var currdatetime = CurrentDate()+" "+CurrentTime();
	return currdatetime;
}
function CurrentTimeStr()
{ 
    var now = new Date(); 
    var hh = 0+now.getHours(); 
    var mm = 0+now.getMinutes(); 
    var ss = 0+now.getTime() % 60000; 
    ss = (ss - (ss % 1000)) / 1000; 

    var clock = '';
	if (hh < 10) clock += '0';
	clock += hh;
    if (mm < 10) clock += '0'; 
    clock += mm; 
    if (ss < 10) clock += '0'; 
    clock += ss;

    return(clock); 
} 
function CurrentDateStr()
{
    var now = new Date(); 
    var yy = now.getYear(); 
    var mm = now.getMonth()+1; 
	if(mm  <10) mm = "0" +mm;
	var dd = now.getDate();
	if(dd <10) dd = "0" +dd;

	var currdate = ''+ yy + mm +dd;
	return currdate;
}
function CurrentDateStrProtean(num)
{
	var flag = Math.abs(num);
    var now = new Date(); 
    var yy = now.getYear(); 
    var mm = now.getMonth()+1;
	var dd = now.getDate();
	num = parseInt(num);
	//先添加
	if(flag >= 10000){
		yy = yy+Math.round(num/10000);
	}else if(flag >= 100){
		mm = mm+Math.round(num/100);
	}else{
		dd = dd+num;
	}
	//后判断
	if(mm>12){
		yy += 1;
		mm -= 12;
	}
	var lastDay = showMonthLastDay(yy,mm);
	if(dd > lastDay){
		mm += 1;
		dd -= parseInt(lastDay);
	}

	if(mm  <10) mm = "0" +mm;
	if(dd <10) dd = "0" +dd;

	var currdate = '' + yy + mm +dd;
	return currdate;
}

function CurrentDateTimeStr()
{
	var currdatetime = CurrentDateStr() +CurrentTimeStr();
	return currdatetime;
}
function DateTimeFmtToStr(dt)
{
	var str ="";
	for(var i=0;i<dt.length;i++)
    {
		if(dt.charAt(i) =="-" || dt.charAt(i) ==" " || dt.charAt(i) ==':') continue;
		str += dt.charAt(i);
	}
    return str;
}
function StrToDateTimeFmt(dt)
{
	var str ="";
	for(var i=0;i<dt.length;i++)
    {
		if(i==4 || i==6 ) str +="-";
		if(i==8) str +=" ";
		if(i==10 || i==12 ) str +=":";
		str += dt.charAt(i);
	}
    return str;
}

//----------------------------------------------------------------------------
// 日历 Javascript 页面脚本控件，适用于微软的 IE （5.0以上）浏览器
// 主调用函数是 setday(this,[object])和setday(this)，[object]是控件输出的控件名，举两个例子：
// 一、<input name=txt><input type=button value=setday onclick="setday(this,document.all.txt)">
// 二、<input onfocus="setday(this)">
// 本日历的年份限制是（1000 - 9999）
// 按ESC键关闭该控件
// 在年和月的显示地方点击时会分别出年与月的下拉框
// 控件外任意点击一点即可关闭该控件
//
//
//***************************************************************************
//* 选择时间和日期的JavaScript页面脚本控件，适用于微软的 IE （5.0以上）浏览器 
//* 调用函数是sel_time(timeName,timeSel,isDisabled,show),直接输出日期和时间控件,使
//* 用timeName.value即可获得选中的日期和时间。在日期选择的text控件中已经引用上面的setday 
//* 
//* 版本 1.01 
//* 参数: timeName: 整个控件包含隐藏表单的名字，也会做为3个分控件的前缀 
//* timeSel: 3个分控件预先显示的内容 
//* isDisabled：3个分控件是否disabled 
//* show： 一个附加属性，用途自定义
//* 阿笨 aben.117@163.com
//***************************************************************************
//说明：
//1.受到iframe的限制，如果拖动出日历窗口，则日历会停止移动。
//
//
//==================================================== 参数设定部分 =======================================================
var bMoveable=true; //设置日历是否可以拖动
var _VersionInfo="Version:2.0&#13;2.0作者:walkingpoison&#13;1.0作者: F.R.Huang(meizz)&#13;MAIL: meizz@hzcnc.com" //版本信息

//==================================================== WEB 页面显示部分 =====================================================
var strFrame; //存放日历层的HTML代码
document.writeln('<iframe id=meizzDateLayer  frameborder=0 style="position: absolute; width: 148; height: 236; z-index: 9998; display: none"></iframe>');
strFrame='<style>';
strFrame+='INPUT.button{BORDER-RIGHT: #99CCFF 1px solid;BORDER-TOP: #99CCFF 1px solid;BORDER-LEFT: #99CCFF 1px solid;';
strFrame+='BORDER-BOTTOM: #99CCFF 1px solid;BACKGROUND-COLOR: #fff8ec;font-family:宋体;}';
strFrame+='TD{FONT-SIZE: 9pt;font-family:宋体;}';
strFrame+='</style>';
strFrame+='<scr' + 'ipt>';
strFrame+='var datelayerx,datelayery; /*存放日历控件的鼠标位置*/';
strFrame+='var bDrag; /*标记是否开始拖动*/';
strFrame+='function document.onmousemove() /*在鼠标移动事件中，如果开始拖动日历，则移动日历*/';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+=' {var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+=' DateLayer.posLeft += window.event.clientX-datelayerx;/*由于每次移动以后鼠标位置都恢复为初始的位置，因此写法与div中不同*/';
strFrame+=' DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart() /*开始日历拖动*/';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+=' datelayerx=window.event.clientX;';
strFrame+=' datelayery=window.event.clientY;';
strFrame+=' bDrag=true;}';
strFrame+='function DragEnd(){ /*结束日历拖动*/';
strFrame+=' bDrag=false;}';
strFrame+='</scr' + 'ipt>';
strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false"><span id=tmpSelectYearLayer  style="z-index: 9999;position: absolute;top: 3; left: 19;display: none"></span>';
strFrame+='<span id=tmpSelectMonthLayer  style="z-index: 9999;position: absolute;top: 3; left: 78;display: none"></span>';
strFrame+='<span id=tmpSelectHourLayer  style="z-index: 9999;position: absolute;top: 28; left: 2;display: none"></span>';
strFrame+='<span id=tmpSelectMinuteLayer  style="z-index: 9999;position: absolute;top: 28; left: 45;display: none"></span>';
strFrame+='<span id=tmpSelectSecondLayer  style="z-index: 9999;position: absolute;top: 28; left: 90;display: none"></span>';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 width=142 height=180 bordercolor=#99CCFF bgcolor=#99CCFF >';
strFrame+=' <tr ><td width=142 height=23  bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=140  height=23>';
strFrame+=' <tr align=center ><td width=16 align=center bgcolor=#99CCFF style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+=' onclick="parent.meizzPrevM()" title="向前翻 1 月" ><b >&lt;</b>';
strFrame+=' </td><td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectYearInnerHTML(this.innerText.substring(0,4))" title="点击这里选择年份"><span  id=meizzYearHead></span></td>';
strFrame+='<td width=48 align=center style="font-size:12px;cursor:default"  onmouseover="style.backgroundColor=\'#99ccff\'" ';
strFrame+=' onmouseout="style.backgroundColor=\'white\'" onclick="parent.tmpSelectMonthInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))"';
strFrame+=' title="点击这里选择月份"><span id=meizzMonthHead ></span></td>';
strFrame+=' <td width=16 bgcolor=#99CCFF align=center style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+=' onclick="parent.meizzNextM()" title="向后翻 1 月" ><b >&gt;</b></td></tr>';
strFrame+=' </table></td></tr>';

strFrame+=' <tr ><td width=142 height=23  bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=140  height=23>';
strFrame+=' <tr align=center >'
strFrame+=' <td width=4 align=center bgcolor=#99CCFF style="font-size:12px;color: #ffffff" ><b ></b></td>';
strFrame+=' <td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectHourInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="点击这里选择时"><span  id=meizzHourHead></span></td>';
strFrame+=' <td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectMinuteInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="点击这里选择分"><span  id=meizzMinuteHead></span></td>';
strFrame+=' <td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectSecondInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="点击这里选择秒"><span  id=meizzSecondHead></span></td>';
strFrame+=' <td width=4 align=center bgcolor=#99CCFF style="font-size:12px;color: #ffffff" ><b ></b></td>';
strFrame+=' </table></td></tr>';

strFrame+=' <tr ><td width=142 height=18 >';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 bgcolor=#99CCFF ' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'');
strFrame+=' BORDERCOLORLIGHT=#99CCFF BORDERCOLORDARK=#FFFFFF width=140 height=20  style="cursor:' + (bMoveable ? 'move':'default') + '">';
strFrame+='<tr  align=center valign=bottom><td style="font-size:12px;color:#FFFFFF" >日</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" >一</td><td style="font-size:12px;color:#FFFFFF" >二</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" >三</td><td style="font-size:12px;color:#FFFFFF" >四</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" >五</td><td style="font-size:12px;color:#FFFFFF" >六</td></tr>';
strFrame+='</table></td></tr><!-- Author:F.R.Huang(meizz) http://www.meizz.com/ mail: meizz@hzcnc.com 2002-10-8 -->';
strFrame+=' <tr ><td width=142 height=120 >';
strFrame+=' <table border=1 cellspacing=2 cellpadding=0 BORDERCOLORLIGHT=#99CCFF BORDERCOLORDARK=#FFFFFF bgcolor=#fff8ec width=140 height=120 >';
var n=0; for (var j=0;j<5;j++){ strFrame+= ' <tr align=center >'; for (i=0;i<7;i++){
strFrame+='<td width=20 height=20 id=meizzDay'+n+' style="font-size:12px"  onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
strFrame+='</tr>';}
strFrame+=' <tr align=center >';
for (var i=35;i<39;i++)strFrame+='<td width=20 height=20 id=meizzDay'+i+' style="font-size:12px"  onclick="parent.meizzDayClick(this.innerText,0)"></td>';
strFrame+=' <td colspan=3 align=right ><span onclick=parent.closeLayer() style="font-size:12px;cursor: hand"';
strFrame+='  title="' + _VersionInfo + '"><u>关闭</u></span>&nbsp;</td></tr>';
strFrame+=' </table></td></tr><tr ><td >';
strFrame+=' <table border=0 cellspacing=1 cellpadding=0 width=100%  bgcolor=#FFFFFF>';
strFrame+=' <tr ><td  align=left><input  type=button class=button value="<<" title="向前翻 1 年" onclick="parent.meizzPrevY()" ';
strFrame+=' onfocus="this.blur()" style="font-size: 12px; height: 20px"><input  class=button title="向前翻 1 月" type=button ';
strFrame+=' value="<" onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; height: 20px"></td><td ';
strFrame+='  align=center><input  type=button class=button value="今天" onclick="parent.meizzToday()" ';
strFrame+=' onfocus="this.blur()" title="当前日期" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='  align=center><input  type=button class=button value="现在" onclick="parent.meizzNow()" ';
strFrame+=' onfocus="this.blur()" title="当前日期" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='  align=right><input  type=button class=button value=">" onclick="parent.meizzNextM()" ';
strFrame+=' onfocus="this.blur()" title="向后翻 1 月" class=button style="font-size: 12px; height: 20px"><input ';
strFrame+='  type=button class=button value=">>" title="向后翻 1 年" onclick="parent.meizzNextY()"';
strFrame+=' onfocus="this.blur()" style="font-size: 12px; height: 20px"></td>';
strFrame+='</tr></table></td></tr></table></div>';

window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close(); //解决ie进度条不结束的问题

//==================================================== WEB 页面显示部分 ======================================================
var outObject;
var outButton; //点击的按钮
var outDate=""; //存放对象的日期
var odatelayer=window.frames.meizzDateLayer.document.all; //存放日历对象
var outDateFmt='date';//date = yyyy-mm-dd ;datestr=yyyymmdd ;datetime=yyyy-mm-dd 00:00:00;datetimestr=yyyymmdd000000

function setyear(tt,obj){
	if (arguments.length > 2){alert("对不起！传入本控件的参数太多！");return;}
	if (arguments.length == 0){alert("对不起！您没有传回本控件任何参数！");return;}
	outDateFmt='year';
	if(typeof(obj) == 'undefined' ) obj = tt;
	setdayfmt(tt,obj,outDateFmt);
}

function setmonth(tt,obj) //主调函数
{
if (arguments.length > 2){alert("对不起！传入本控件的参数太多！");return;}
if (arguments.length == 0){alert("对不起！您没有传回本控件任何参数！");return;}
outDateFmt='yearmonth';
if(typeof(obj) == 'undefined' ) obj = tt;
setdayfmt(tt,obj,outDateFmt);
}

function setday(tt,obj) //主调函数
{
if (arguments.length > 2){alert("对不起！传入本控件的参数太多！");return;}
if (arguments.length == 0){alert("对不起！您没有传回本控件任何参数！");return;}
outDateFmt='date';
if(typeof(obj) == 'undefined' ) obj = tt;
setdayfmt(tt,obj,outDateFmt);
}

function setdatetime(tt,obj) //主调函数
{
if (arguments.length > 2){alert("对不起！传入本控件的参数太多！");return;}
if (arguments.length == 0){alert("对不起！您没有传回本控件任何参数！");return;}
outDateFmt='datetime';
if(typeof(obj) == 'undefined' ) obj = tt;
setdayfmt(tt,obj,outDateFmt);
}



function setdayfmt(tt,obj,fmt) //主调函数
{
if (arguments.length !=3 ){alert("对不起！传入本控件的参数不正确！");return;}
outDateFmt=fmt;
var dads = document.all.meizzDateLayer.style;
var th = tt;
if(tt==null) tt = obj;
if(obj==null) obj = tt;
var ttop = tt.offsetTop; //TT控件的定位点高
var thei = tt.clientHeight; //TT控件本身的高
var tleft = tt.offsetLeft; //TT控件的定位点宽
var ttyp = tt.type; //TT控件的类型
while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
dads.top = (ttyp=="image")? ttop+thei : ttop+thei+6;
dads.left = tleft;
outObject =  obj;
outButton =  th; //设定外部点击的按钮

var r = DateTimeFmtToStr(outObject.value);
var meizzTheDay=1;
if(r.length>=4)
	meizzTheYear= r.substr(0,4);
if(r.length>=6)
	meizzTheMonth= r.substr(4,2);
if(r.length>=8)
	meizzTheDay= r.substr(6,2);
if(r.length>=10)
	meizzTheHour= r.substr(8,2);
if(r.length>=12)
	meizzTheMinute= r.substr(10,2);
if(r.length>=14)
	meizzTheSecond= r.substr(12,2);

outDate=new Date(meizzTheYear,meizzTheMonth-1,meizzTheDay,meizzTheHour,meizzTheMinute,meizzTheSecond); //保存外部传入的日期

meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);

/*
//根据当前输入框的日期显示日历的年月
var reg = /^(\d+)-(\d{1,2})-(\d{1,2})$/; 
var r = outObject.value.match(reg); 
if(r!=null)
{
	r[2]=r[2]-1; 
	var d= new Date(r[1], r[2],r[3]); 
	if(d.getFullYear()==r[1] && d.getMonth()==r[2] && d.getDate()==r[3]){
		outDate=d; //保存外部传入的日期
	}
	else outDate="";
	meizzSetDay(r[1],r[2]+1);
}
else
{
	outDate="";
	meizzSetDay(new Date().getFullYear(), new Date().getMonth() + 1);
}

*/
dads.display = '';

event.returnValue=false;
}

var MonHead = new Array(12); //定义阳历中每个月的最大天数
MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4] = 31; MonHead[5] = 30;
MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); //定义年的变量的初始值
var meizzTheMonth=new Date().getMonth()+1; //定义月的变量的初始值
var meizzWDay=new Array(39); //定义写日期的数组
var meizzTheHour = 0+(new Date().getHours());
var meizzTheMinute = 0+(new Date().getMinutes());
var meizzTheSecond = 0+(new Date().getTime()) % 60000; 
meizzTheSecond = (meizzTheSecond - (meizzTheSecond % 1000)) / 1000; 

 document.onclick=function() //任意点击时关闭该控件 //ie6的情况可以由下面的切换焦点处理代替
{ 
with(window.event)
{ if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
closeLayer();
}
}

 document.onkeyup=function() //按Esc键关闭，切换焦点关闭
{
if (window.event.keyCode==27){
if(outObject)outObject.blur();
closeLayer();
}
else if(document.activeElement)
if(document.activeElement.getAttribute("Author")==null && document.activeElement != outObject && document.activeElement != outButton)
{
closeLayer();
}
}

function meizzWriteHead(yy,mm,hh,nn,ss) //往 head 中写入当前的年与月
{
odatelayer.meizzYearHead.innerText = yy + " 年";
odatelayer.meizzMonthHead.innerText = mm + " 月";
odatelayer.meizzHourHead.innerText = hh + " 时";
odatelayer.meizzMinuteHead.innerText = nn + " 分";
odatelayer.meizzSecondHead.innerText = ss + " 秒";
}

function tmpSelectYearInnerHTML(strYear) //年份的下拉框
{
if (strYear.match(/\D/)!=null){alert("年份输入参数不是数字！");return;}
var m = (strYear) ? strYear : new Date().getFullYear();
if (m < 1000 || m > 9999) {alert("年份值不在 1000 到 9999 之间！");return;}
var n = m - 25;
if (n < 1000) n = 1000;
if (n + 26 > 9999) n = 9974;
var s = "<select  name=tmpSelectYear style='font-size: 12px' "
s += "onblur='document.all.tmpSelectYearLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectYearLayer.style.display=\"none\";"
s += "parent.meizzTheYear = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = n; i < n + 26; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='" + i + "' selected>" + i + "年" + "</option>\r\n";}
else {selectInnerHTML += "<option  value='" + i + "'>" + i + "年" + "</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectYearLayer.style.display="";
odatelayer.tmpSelectYearLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectYear.focus();
}

function tmpSelectMonthInnerHTML(strMonth) //月份的下拉框
{
if (strMonth.match(/\D/)!=null){alert("月份输入参数不是数字！");return;}
var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectMonth style='font-size: 12px' "
s += "onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
s += "parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 1; i < 13; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"月"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"月"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectMonthLayer.style.display="";
odatelayer.tmpSelectMonthLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectMonth.focus();
}



function tmpSelectHourInnerHTML(strHour) //小时的下拉框
{
if (strHour.match(/\D/)!=null){alert("时间输入参数不是数字！");return;}
var m = (strHour) ? strHour : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectHour style='font-size: 12px' "
s += "onblur='document.all.tmpSelectHourLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectHourLayer.style.display=\"none\";"
s += "parent.meizzTheHour = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 0; i < 24; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"时"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"时"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectHourLayer.style.display="";
odatelayer.tmpSelectHourLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectHour.focus();
}

function tmpSelectMinuteInnerHTML(strHour) //小时的下拉框
{
if (strHour.match(/\D/)!=null){alert("时间输入参数不是数字！");return;}
var m = (strHour) ? strHour : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectMinute style='font-size: 12px' "
s += "onblur='document.all.tmpSelectMinuteLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectMinuteLayer.style.display=\"none\";"
s += "parent.meizzTheMinute = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 0; i < 60; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"分"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"分"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectMinuteLayer.style.display="";
odatelayer.tmpSelectMinuteLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectMinute.focus();
}

function tmpSelectSecondInnerHTML(strHour) //小时的下拉框
{
if (strHour.match(/\D/)!=null){alert("时间输入参数不是数字！");return;}
var m = (strHour) ? strHour : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectSecond style='font-size: 12px' "
s += "onblur='document.all.tmpSelectSecondLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectSecondLayer.style.display=\"none\";"
s += "parent.meizzTheSecond = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 0; i < 60; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"秒"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"秒"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectSecondLayer.style.display="";
odatelayer.tmpSelectSecondLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectSecond.focus();
}



function closeLayer() //这个层的关闭
{
document.all.meizzDateLayer.style.display="none";
}

function IsPinYear(year) //判断是否闰平年
{
if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
}

function GetMonthCount(year,month) //闰年二月为29天
{
var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
}
function GetDOW(day,month,year) //求某天的星期几
{
var dt=new Date(year,month-1,day).getDay()/7; return dt;
}

function meizzPrevY() //往前翻 Year
{
if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear--;}
else{alert("年份超出范围（1000-9999）！");}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}
function meizzNextY() //往后翻 Year
{
if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear++;}
else{alert("年份超出范围（1000-9999）！");}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}

function meizzValue(today)
{
var yy =""+meizzTheYear,mm = ""+meizzTheMonth,dd = ""+today,hh = ""+meizzTheHour,nn = ""+meizzTheMinute,ss=""+meizzTheSecond;

if(mm.length < 2) mm = "0" + mm;
if(hh.length <2)  hh = "0" + hh;
if(nn.length < 2) nn = "0" + nn;
if(ss.length < 2) ss = "0" + ss;

if(dd.length < 2) dd = "0" + dd;

if(outObject){
	outObject.value=yy + "-" + mm + "-" + dd;
}

if(outDateFmt=='year'){
	outObject.value="" + yy;
}

if(outDateFmt == 'yearmonth')
{
	outObject.value="" + yy + "-" + mm;
}

if(outDateFmt == 'yearmonthstr')
{
	outObject.value="" + yy + mm;
}

if(outDateFmt == 'date')
{
	outObject.value="" + yy + "-" + mm + "-" + dd;
}

if(outDateFmt=='datestr' ) 
{
	outObject.value="" + yy +  mm + dd;
}

if(outDateFmt=='datetimestr')
{
	outObject.value="" + yy +  mm + dd + hh + nn + ss;
}

if(outDateFmt=='datetime')
{
	outObject.value="" + yy +  "-" +mm + "-" + dd + " " + hh + ":" + nn + ":" + ss;
}

closeLayer();

}
function meizzToday() //Today Button
{
var today;
meizzTheYear = new Date().getFullYear();
meizzTheMonth = new Date().getMonth()+1;
today=new Date().getDate();
//meizzSetDay(meizzTheYear,meizzTheMonth);
meizzValue(today);

}
function meizzNow() //Now Button
{
meizzTheYear=new Date().getFullYear(); //定义年的变量的初始值
meizzTheMonth=new Date().getMonth()+1; //定义月的变量的初始值
meizzTheHour = 0+(new Date().getHours());
meizzTheMinute = 0+(new Date().getMinutes());
meizzTheSecond = 0+(new Date().getTime()) % 60000; 
meizzTheSecond = (meizzTheSecond - (meizzTheSecond % 1000)) / 1000; 
var today;
today=new Date().getDate();
//meizzSetDay(meizzTheYear,meizzTheMonth);
meizzValue(today);

}
function meizzPrevM() //往前翻月份
{
if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}
function meizzNextM() //往后翻月份
{
if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}

function meizzSetDay(yy,mm,hh,nn,ss) //主要的写程序**********
{
meizzWriteHead(yy,mm,hh,nn,ss);
//设置当前年月的公共变量为传入值
meizzTheYear=yy;
meizzTheMonth=mm;
for (var i = 0; i < 39; i++){meizzWDay[i]=""}; //将显示框的内容全部清空
var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay(); //某月第一天的星期几
for (i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1 //上个月的最后几天
for (i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
for (i=firstday+GetMonthCount(yy,mm);i<39;i++){meizzWDay[i]=day2;day2++}
for (i = 0; i < 39; i++)
{ var da = eval("odatelayer.meizzDay"+i) //书写新的一个月的日期星期排列
if (meizzWDay[i]!="")
{ 
//初始化边框
da.borderColorLight="#99CCFF";
da.borderColorDark="#FFFFFF";
if(i<firstday) //上个月的部分
{
da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
da.title=(mm==1?12:mm-1) +"月" + meizzWDay[i] + "日";
da.onclick=Function("meizzDayClick(this.innerText,-1)");
if(!outDate)
da.style.backgroundColor = ((mm==1?yy-1:yy) == new Date().getFullYear() && 
(mm==1?12:mm-1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
"#99ccff":"#e0e0e0";
else
{
da.style.backgroundColor =((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
meizzWDay[i]==outDate.getDate())? "#ff9900" :
(((mm==1?yy-1:yy) == new Date().getFullYear() && (mm==1?12:mm-1) == new Date().getMonth()+1 && 
meizzWDay[i] == new Date().getDate()) ? "#99ccff":"#e0e0e0");
//将选中的日期显示为凹下去
if((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
meizzWDay[i]==outDate.getDate())
{
da.borderColorLight="#FFFFFF";
da.borderColorDark="#99CCFF";
}
}
}
else if (i>=firstday+GetMonthCount(yy,mm)) //下个月的部分
{
da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
da.title=(mm==12?1:mm+1) +"月" + meizzWDay[i] + "日";
da.onclick=Function("meizzDayClick(this.innerText,1)");
if(!outDate)
da.style.backgroundColor = ((mm==12?yy+1:yy) == new Date().getFullYear() && 
(mm==12?1:mm+1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
"#99ccff":"#e0e0e0";
else
{
da.style.backgroundColor =((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
meizzWDay[i]==outDate.getDate())? "#ff9900" :
(((mm==12?yy+1:yy) == new Date().getFullYear() && (mm==12?1:mm+1) == new Date().getMonth()+1 && 
meizzWDay[i] == new Date().getDate()) ? "#99ccff":"#e0e0e0");
//将选中的日期显示为凹下去
if((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
meizzWDay[i]==outDate.getDate())
{
da.borderColorLight="#FFFFFF";
da.borderColorDark="#99CCFF";
}
}
}
else //本月的部分
{
da.innerHTML="<b>" + meizzWDay[i] + "</b>";
da.title=mm +"月" + meizzWDay[i] + "日";
da.onclick=Function("meizzDayClick(this.innerText,0)"); //给td赋予onclick事件的处理
//如果是当前选择的日期，则显示亮蓝色的背景；如果是当前日期，则显示暗黄色背景
if(!outDate)
da.style.backgroundColor = (yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
"#99ccff":"#e0e0e0";
else
{
da.style.backgroundColor =(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())?
"#ff9900":((yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
"#99ccff":"#e0e0e0");
//将选中的日期显示为凹下去
if(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())
{
da.borderColorLight="#FFFFFF";
da.borderColorDark="#99CCFF";
}
}
}
da.style.cursor="hand"
}
else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}
}
}

function meizzDayClick(n,ex) //点击显示框选取日期，主输入函数*************
{
var yy=meizzTheYear;
var mm = parseInt(meizzTheMonth)+ex; //ex表示偏移量，用于选择上个月份和下个月份的日期
//判断月份，并进行对应的处理
if(mm<1){
yy--;
mm=12+mm;
}
else if(mm>12){
yy++;
mm=mm-12;
}

if (outObject)
{
if (!n) {//outObject.value=""; 
return;}
outObject.value= yy + "-" + mm + "-" + n ; //注：在这里你可以输出改成你想要的格式
meizzTheYear=yy; //定义年的变量的初始值
meizzTheMonth=mm; //定义月的变量的初始值
//meizzSetDay(meizzTheYear,meizzTheMonth);
meizzValue(n);

outObject.focus();
}
else {closeLayer(); alert("您所要输出的控件对象并不存在！");}
}

/**********************************************************************************
函数sel_time

版本 1.01
参数: timeName: 整个控件包含隐藏表单的名字，也会做为3个分控件的前缀
timeSel: 3个分控件预先显示的内容
isDisabled：3个分控件是否disabled
show: 一个多余字段，可以用来写你自己的东西
***********************************************************************************/
function sel_time(timeName,timeSel,isDisabled,show){
var hour=0;
var min=0;
//alert(hourSel+" "+minSel);
//alert(houName+" "+minName);
//alert(isDisabled);
//alert(hourSel+" "+minSel);
//alert(hourSel.length+" "+minSel.length)
///////////////////////取得timeSel中的日期和时间///////////////////////////
var dateSel="",hourSel="",minSel="";
var arry1,arry2;
if (timeSel!="")
{
arry1=timeSel.split(" ");
dateSel=arry1[0];
if (arry1[1]==null){arry1[1]='00:00:00';}
arry2=arry1[1].split(":");
hourSel=arry2[0];
minSel=arry2[1];
if (hourSel.length<2)
{
hourSel="0"+hourSel;
}
if (minSel.length<2)
{
minSel="0"+minSel;
}
}
/////////////////////////////////////////////////////////////////////////

//输出3个日期分控件
//输出日期控件
document.write("<input name="+timeName+"_date type='text' readonly onclick='setday(this);' show='"+show+"' size=10 value='"+dateSel+"' "+isDisabled+" onfocus='setHidden("+timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName+")'>&nbsp;");
//输出小时控件
document.write("<select name="+timeName+"_hou size=1 "+isDisabled+" onchange='setHidden("+timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName+")'>");
for(;hour<24;hour++){
if(hour<10) hour="0"+hour;
document.write("<option value="+hour)
if(hour==hourSel) document.write(" selected");
document.write(">"+hour+"</option>");

}

document.write("</select>时&nbsp;");
//输出分钟控件
document.write("<select name="+timeName+"_min size=1 "+isDisabled+" onchange='setHidden("+timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName+")'>");
for(;min<60;min++){
if(min<10) min="0"+min;
document.write("<option value="+min)
if(min==minSel) document.write(" selected");
document.write(" "+isDisabled+" >"+min+"</option>");
}
document.write("</select>分");
//输出隐藏区域
document.write("<input name="+timeName+" type=hidden value='"+timeSel+"'>");
//setHidden(timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName);
}

function setHidden(t,s1,s2,h){
//alert(t.value);
h.value=t.value+" "+s1.value+":"+s2.value+":00";
}

function sd(){
if (s.value!=""){
alert('您选择的时间是:'+s.value);
}
else{
alert('您还没有选择时间')
}
}

//-->
//</script>

/*
</head>

<body>
<script language="JavaScript">
<!--
sel_time("s","","","")
//-->
</script>
<form method=post action="">
<input type="button" value="点击显示您选择的时间" onclick="sd();">
</form>
</body>
</html>
*/
