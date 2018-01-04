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
	//������
	if(flag >= 10000){
		yy = yy+Math.round(num/10000);
	}else if(flag >= 100){
		mm = mm+Math.round(num/100);
	}else{
		dd = dd+num;
	}
	//���ж�
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
//ժ¼�����ϣ��޸��ˣ������� 2007-11-12
function showMonthLastDay(yy,mm)
{
var Nowdate=new Date();
var MonthNextFirstDay=new Date(yy,mm,1);
var MonthLastDay=new Date(MonthNextFirstDay-86400000);
return MonthLastDay.getDate();
}
/*function Date.prototype.toString(){
return this.getFullYear()+"��"+(this.getMonth()+1)+"��"+this.getDate()+"��";
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
	//������
	if(flag >= 10000){
		yy = yy+Math.round(num/10000);
	}else if(flag >= 100){
		mm = mm+Math.round(num/100);
	}else{
		dd = dd+num;
	}
	//���ж�
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
// ���� Javascript ҳ��ű��ؼ���������΢���� IE ��5.0���ϣ������
// �����ú����� setday(this,[object])��setday(this)��[object]�ǿؼ�����Ŀؼ��������������ӣ�
// һ��<input name=txt><input type=button value=setday onclick="setday(this,document.all.txt)">
// ����<input onfocus="setday(this)">
// ����������������ǣ�1000 - 9999��
// ��ESC���رոÿؼ�
// ������µ���ʾ�ط����ʱ��ֱ�������µ�������
// �ؼ���������һ�㼴�ɹرոÿؼ�
//
//
//***************************************************************************
//* ѡ��ʱ������ڵ�JavaScriptҳ��ű��ؼ���������΢���� IE ��5.0���ϣ������ 
//* ���ú�����sel_time(timeName,timeSel,isDisabled,show),ֱ��������ں�ʱ��ؼ�,ʹ
//* ��timeName.value���ɻ��ѡ�е����ں�ʱ�䡣������ѡ���text�ؼ����Ѿ����������setday 
//* 
//* �汾 1.01 
//* ����: timeName: �����ؼ��������ر��������֣�Ҳ����Ϊ3���ֿؼ���ǰ׺ 
//* timeSel: 3���ֿؼ�Ԥ����ʾ������ 
//* isDisabled��3���ֿؼ��Ƿ�disabled 
//* show�� һ���������ԣ���;�Զ���
//* ���� aben.117@163.com
//***************************************************************************
//˵����
//1.�ܵ�iframe�����ƣ�����϶����������ڣ���������ֹͣ�ƶ���
//
//
//==================================================== �����趨���� =======================================================
var bMoveable=true; //���������Ƿ�����϶�
var _VersionInfo="Version:2.0&#13;2.0����:walkingpoison&#13;1.0����: F.R.Huang(meizz)&#13;MAIL: meizz@hzcnc.com" //�汾��Ϣ

//==================================================== WEB ҳ����ʾ���� =====================================================
var strFrame; //����������HTML����
document.writeln('<iframe id=meizzDateLayer  frameborder=0 style="position: absolute; width: 148; height: 236; z-index: 9998; display: none"></iframe>');
strFrame='<style>';
strFrame+='INPUT.button{BORDER-RIGHT: #99CCFF 1px solid;BORDER-TOP: #99CCFF 1px solid;BORDER-LEFT: #99CCFF 1px solid;';
strFrame+='BORDER-BOTTOM: #99CCFF 1px solid;BACKGROUND-COLOR: #fff8ec;font-family:����;}';
strFrame+='TD{FONT-SIZE: 9pt;font-family:����;}';
strFrame+='</style>';
strFrame+='<scr' + 'ipt>';
strFrame+='var datelayerx,datelayery; /*��������ؼ������λ��*/';
strFrame+='var bDrag; /*����Ƿ�ʼ�϶�*/';
strFrame+='function document.onmousemove() /*������ƶ��¼��У������ʼ�϶����������ƶ�����*/';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+=' {var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+=' DateLayer.posLeft += window.event.clientX-datelayerx;/*����ÿ���ƶ��Ժ����λ�ö��ָ�Ϊ��ʼ��λ�ã����д����div�в�ͬ*/';
strFrame+=' DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart() /*��ʼ�����϶�*/';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+=' datelayerx=window.event.clientX;';
strFrame+=' datelayery=window.event.clientY;';
strFrame+=' bDrag=true;}';
strFrame+='function DragEnd(){ /*���������϶�*/';
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
strFrame+=' onclick="parent.meizzPrevM()" title="��ǰ�� 1 ��" ><b >&lt;</b>';
strFrame+=' </td><td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectYearInnerHTML(this.innerText.substring(0,4))" title="�������ѡ�����"><span  id=meizzYearHead></span></td>';
strFrame+='<td width=48 align=center style="font-size:12px;cursor:default"  onmouseover="style.backgroundColor=\'#99ccff\'" ';
strFrame+=' onmouseout="style.backgroundColor=\'white\'" onclick="parent.tmpSelectMonthInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))"';
strFrame+=' title="�������ѡ���·�"><span id=meizzMonthHead ></span></td>';
strFrame+=' <td width=16 bgcolor=#99CCFF align=center style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+=' onclick="parent.meizzNextM()" title="��� 1 ��" ><b >&gt;</b></td></tr>';
strFrame+=' </table></td></tr>';

strFrame+=' <tr ><td width=142 height=23  bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=140  height=23>';
strFrame+=' <tr align=center >'
strFrame+=' <td width=4 align=center bgcolor=#99CCFF style="font-size:12px;color: #ffffff" ><b ></b></td>';
strFrame+=' <td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectHourInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="�������ѡ��ʱ"><span  id=meizzHourHead></span></td>';
strFrame+=' <td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectMinuteInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="�������ѡ���"><span  id=meizzMinuteHead></span></td>';
strFrame+=' <td width=60 align=center style="font-size:12px;cursor:default"  ';
strFrame+='onmouseover="style.backgroundColor=\'#99ccff\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectSecondInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="�������ѡ����"><span  id=meizzSecondHead></span></td>';
strFrame+=' <td width=4 align=center bgcolor=#99CCFF style="font-size:12px;color: #ffffff" ><b ></b></td>';
strFrame+=' </table></td></tr>';

strFrame+=' <tr ><td width=142 height=18 >';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 bgcolor=#99CCFF ' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'');
strFrame+=' BORDERCOLORLIGHT=#99CCFF BORDERCOLORDARK=#FFFFFF width=140 height=20  style="cursor:' + (bMoveable ? 'move':'default') + '">';
strFrame+='<tr  align=center valign=bottom><td style="font-size:12px;color:#FFFFFF" >��</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" >һ</td><td style="font-size:12px;color:#FFFFFF" >��</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" >��</td><td style="font-size:12px;color:#FFFFFF" >��</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" >��</td><td style="font-size:12px;color:#FFFFFF" >��</td></tr>';
strFrame+='</table></td></tr><!-- Author:F.R.Huang(meizz) http://www.meizz.com/ mail: meizz@hzcnc.com 2002-10-8 -->';
strFrame+=' <tr ><td width=142 height=120 >';
strFrame+=' <table border=1 cellspacing=2 cellpadding=0 BORDERCOLORLIGHT=#99CCFF BORDERCOLORDARK=#FFFFFF bgcolor=#fff8ec width=140 height=120 >';
var n=0; for (var j=0;j<5;j++){ strFrame+= ' <tr align=center >'; for (i=0;i<7;i++){
strFrame+='<td width=20 height=20 id=meizzDay'+n+' style="font-size:12px"  onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
strFrame+='</tr>';}
strFrame+=' <tr align=center >';
for (var i=35;i<39;i++)strFrame+='<td width=20 height=20 id=meizzDay'+i+' style="font-size:12px"  onclick="parent.meizzDayClick(this.innerText,0)"></td>';
strFrame+=' <td colspan=3 align=right ><span onclick=parent.closeLayer() style="font-size:12px;cursor: hand"';
strFrame+='  title="' + _VersionInfo + '"><u>�ر�</u></span>&nbsp;</td></tr>';
strFrame+=' </table></td></tr><tr ><td >';
strFrame+=' <table border=0 cellspacing=1 cellpadding=0 width=100%  bgcolor=#FFFFFF>';
strFrame+=' <tr ><td  align=left><input  type=button class=button value="<<" title="��ǰ�� 1 ��" onclick="parent.meizzPrevY()" ';
strFrame+=' onfocus="this.blur()" style="font-size: 12px; height: 20px"><input  class=button title="��ǰ�� 1 ��" type=button ';
strFrame+=' value="<" onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; height: 20px"></td><td ';
strFrame+='  align=center><input  type=button class=button value="����" onclick="parent.meizzToday()" ';
strFrame+=' onfocus="this.blur()" title="��ǰ����" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='  align=center><input  type=button class=button value="����" onclick="parent.meizzNow()" ';
strFrame+=' onfocus="this.blur()" title="��ǰ����" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='  align=right><input  type=button class=button value=">" onclick="parent.meizzNextM()" ';
strFrame+=' onfocus="this.blur()" title="��� 1 ��" class=button style="font-size: 12px; height: 20px"><input ';
strFrame+='  type=button class=button value=">>" title="��� 1 ��" onclick="parent.meizzNextY()"';
strFrame+=' onfocus="this.blur()" style="font-size: 12px; height: 20px"></td>';
strFrame+='</tr></table></td></tr></table></div>';

window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close(); //���ie������������������

//==================================================== WEB ҳ����ʾ���� ======================================================
var outObject;
var outButton; //����İ�ť
var outDate=""; //��Ŷ��������
var odatelayer=window.frames.meizzDateLayer.document.all; //�����������
var outDateFmt='date';//date = yyyy-mm-dd ;datestr=yyyymmdd ;datetime=yyyy-mm-dd 00:00:00;datetimestr=yyyymmdd000000

function setyear(tt,obj){
	if (arguments.length > 2){alert("�Բ��𣡴��뱾�ؼ��Ĳ���̫�࣡");return;}
	if (arguments.length == 0){alert("�Բ�����û�д��ر��ؼ��κβ�����");return;}
	outDateFmt='year';
	if(typeof(obj) == 'undefined' ) obj = tt;
	setdayfmt(tt,obj,outDateFmt);
}

function setmonth(tt,obj) //��������
{
if (arguments.length > 2){alert("�Բ��𣡴��뱾�ؼ��Ĳ���̫�࣡");return;}
if (arguments.length == 0){alert("�Բ�����û�д��ر��ؼ��κβ�����");return;}
outDateFmt='yearmonth';
if(typeof(obj) == 'undefined' ) obj = tt;
setdayfmt(tt,obj,outDateFmt);
}

function setday(tt,obj) //��������
{
if (arguments.length > 2){alert("�Բ��𣡴��뱾�ؼ��Ĳ���̫�࣡");return;}
if (arguments.length == 0){alert("�Բ�����û�д��ر��ؼ��κβ�����");return;}
outDateFmt='date';
if(typeof(obj) == 'undefined' ) obj = tt;
setdayfmt(tt,obj,outDateFmt);
}

function setdatetime(tt,obj) //��������
{
if (arguments.length > 2){alert("�Բ��𣡴��뱾�ؼ��Ĳ���̫�࣡");return;}
if (arguments.length == 0){alert("�Բ�����û�д��ر��ؼ��κβ�����");return;}
outDateFmt='datetime';
if(typeof(obj) == 'undefined' ) obj = tt;
setdayfmt(tt,obj,outDateFmt);
}



function setdayfmt(tt,obj,fmt) //��������
{
if (arguments.length !=3 ){alert("�Բ��𣡴��뱾�ؼ��Ĳ�������ȷ��");return;}
outDateFmt=fmt;
var dads = document.all.meizzDateLayer.style;
var th = tt;
if(tt==null) tt = obj;
if(obj==null) obj = tt;
var ttop = tt.offsetTop; //TT�ؼ��Ķ�λ���
var thei = tt.clientHeight; //TT�ؼ������ĸ�
var tleft = tt.offsetLeft; //TT�ؼ��Ķ�λ���
var ttyp = tt.type; //TT�ؼ�������
while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
dads.top = (ttyp=="image")? ttop+thei : ttop+thei+6;
dads.left = tleft;
outObject =  obj;
outButton =  th; //�趨�ⲿ����İ�ť

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

outDate=new Date(meizzTheYear,meizzTheMonth-1,meizzTheDay,meizzTheHour,meizzTheMinute,meizzTheSecond); //�����ⲿ���������

meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);

/*
//���ݵ�ǰ������������ʾ����������
var reg = /^(\d+)-(\d{1,2})-(\d{1,2})$/; 
var r = outObject.value.match(reg); 
if(r!=null)
{
	r[2]=r[2]-1; 
	var d= new Date(r[1], r[2],r[3]); 
	if(d.getFullYear()==r[1] && d.getMonth()==r[2] && d.getDate()==r[3]){
		outDate=d; //�����ⲿ���������
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

var MonHead = new Array(12); //����������ÿ���µ��������
MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4] = 31; MonHead[5] = 30;
MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); //������ı����ĳ�ʼֵ
var meizzTheMonth=new Date().getMonth()+1; //�����µı����ĳ�ʼֵ
var meizzWDay=new Array(39); //����д���ڵ�����
var meizzTheHour = 0+(new Date().getHours());
var meizzTheMinute = 0+(new Date().getMinutes());
var meizzTheSecond = 0+(new Date().getTime()) % 60000; 
meizzTheSecond = (meizzTheSecond - (meizzTheSecond % 1000)) / 1000; 

 document.onclick=function() //������ʱ�رոÿؼ� //ie6�����������������л����㴦������
{ 
with(window.event)
{ if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
closeLayer();
}
}

 document.onkeyup=function() //��Esc���رգ��л�����ر�
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

function meizzWriteHead(yy,mm,hh,nn,ss) //�� head ��д�뵱ǰ��������
{
odatelayer.meizzYearHead.innerText = yy + " ��";
odatelayer.meizzMonthHead.innerText = mm + " ��";
odatelayer.meizzHourHead.innerText = hh + " ʱ";
odatelayer.meizzMinuteHead.innerText = nn + " ��";
odatelayer.meizzSecondHead.innerText = ss + " ��";
}

function tmpSelectYearInnerHTML(strYear) //��ݵ�������
{
if (strYear.match(/\D/)!=null){alert("�����������������֣�");return;}
var m = (strYear) ? strYear : new Date().getFullYear();
if (m < 1000 || m > 9999) {alert("���ֵ���� 1000 �� 9999 ֮�䣡");return;}
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
{selectInnerHTML += "<option  value='" + i + "' selected>" + i + "��" + "</option>\r\n";}
else {selectInnerHTML += "<option  value='" + i + "'>" + i + "��" + "</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectYearLayer.style.display="";
odatelayer.tmpSelectYearLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectYear.focus();
}

function tmpSelectMonthInnerHTML(strMonth) //�·ݵ�������
{
if (strMonth.match(/\D/)!=null){alert("�·���������������֣�");return;}
var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectMonth style='font-size: 12px' "
s += "onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
s += "parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 1; i < 13; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"��"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"��"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectMonthLayer.style.display="";
odatelayer.tmpSelectMonthLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectMonth.focus();
}



function tmpSelectHourInnerHTML(strHour) //Сʱ��������
{
if (strHour.match(/\D/)!=null){alert("ʱ����������������֣�");return;}
var m = (strHour) ? strHour : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectHour style='font-size: 12px' "
s += "onblur='document.all.tmpSelectHourLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectHourLayer.style.display=\"none\";"
s += "parent.meizzTheHour = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 0; i < 24; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"ʱ"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"ʱ"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectHourLayer.style.display="";
odatelayer.tmpSelectHourLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectHour.focus();
}

function tmpSelectMinuteInnerHTML(strHour) //Сʱ��������
{
if (strHour.match(/\D/)!=null){alert("ʱ����������������֣�");return;}
var m = (strHour) ? strHour : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectMinute style='font-size: 12px' "
s += "onblur='document.all.tmpSelectMinuteLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectMinuteLayer.style.display=\"none\";"
s += "parent.meizzTheMinute = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 0; i < 60; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"��"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"��"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectMinuteLayer.style.display="";
odatelayer.tmpSelectMinuteLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectMinute.focus();
}

function tmpSelectSecondInnerHTML(strHour) //Сʱ��������
{
if (strHour.match(/\D/)!=null){alert("ʱ����������������֣�");return;}
var m = (strHour) ? strHour : new Date().getMonth() + 1;
var s = "<select  name=tmpSelectSecond style='font-size: 12px' "
s += "onblur='document.all.tmpSelectSecondLayer.style.display=\"none\"' "
s += "onchange='document.all.tmpSelectSecondLayer.style.display=\"none\";"
s += "parent.meizzTheSecond = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth,parent.meizzTheHour,parent.meizzTheMinute,parent.meizzTheSecond)'>\r\n";
var selectInnerHTML = s;
for (var i = 0; i < 60; i++)
{
if (i == m)
{selectInnerHTML += "<option  value='"+i+"' selected>"+i+"��"+"</option>\r\n";}
else {selectInnerHTML += "<option  value='"+i+"'>"+i+"��"+"</option>\r\n";}
}
selectInnerHTML += "</select>";
odatelayer.tmpSelectSecondLayer.style.display="";
odatelayer.tmpSelectSecondLayer.innerHTML = selectInnerHTML;
odatelayer.tmpSelectSecond.focus();
}



function closeLayer() //�����Ĺر�
{
document.all.meizzDateLayer.style.display="none";
}

function IsPinYear(year) //�ж��Ƿ���ƽ��
{
if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
}

function GetMonthCount(year,month) //�������Ϊ29��
{
var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
}
function GetDOW(day,month,year) //��ĳ������ڼ�
{
var dt=new Date(year,month-1,day).getDay()/7; return dt;
}

function meizzPrevY() //��ǰ�� Year
{
if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear--;}
else{alert("��ݳ�����Χ��1000-9999����");}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}
function meizzNextY() //���� Year
{
if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear++;}
else{alert("��ݳ�����Χ��1000-9999����");}
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
meizzTheYear=new Date().getFullYear(); //������ı����ĳ�ʼֵ
meizzTheMonth=new Date().getMonth()+1; //�����µı����ĳ�ʼֵ
meizzTheHour = 0+(new Date().getHours());
meizzTheMinute = 0+(new Date().getMinutes());
meizzTheSecond = 0+(new Date().getTime()) % 60000; 
meizzTheSecond = (meizzTheSecond - (meizzTheSecond % 1000)) / 1000; 
var today;
today=new Date().getDate();
//meizzSetDay(meizzTheYear,meizzTheMonth);
meizzValue(today);

}
function meizzPrevM() //��ǰ���·�
{
if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}
function meizzNextM() //�����·�
{
if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
meizzSetDay(meizzTheYear,meizzTheMonth,meizzTheHour,meizzTheMinute,meizzTheSecond);
}

function meizzSetDay(yy,mm,hh,nn,ss) //��Ҫ��д����**********
{
meizzWriteHead(yy,mm,hh,nn,ss);
//���õ�ǰ���µĹ�������Ϊ����ֵ
meizzTheYear=yy;
meizzTheMonth=mm;
for (var i = 0; i < 39; i++){meizzWDay[i]=""}; //����ʾ�������ȫ�����
var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay(); //ĳ�µ�һ������ڼ�
for (i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1 //�ϸ��µ������
for (i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
for (i=firstday+GetMonthCount(yy,mm);i<39;i++){meizzWDay[i]=day2;day2++}
for (i = 0; i < 39; i++)
{ var da = eval("odatelayer.meizzDay"+i) //��д�µ�һ���µ�������������
if (meizzWDay[i]!="")
{ 
//��ʼ���߿�
da.borderColorLight="#99CCFF";
da.borderColorDark="#FFFFFF";
if(i<firstday) //�ϸ��µĲ���
{
da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
da.title=(mm==1?12:mm-1) +"��" + meizzWDay[i] + "��";
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
//��ѡ�е�������ʾΪ����ȥ
if((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
meizzWDay[i]==outDate.getDate())
{
da.borderColorLight="#FFFFFF";
da.borderColorDark="#99CCFF";
}
}
}
else if (i>=firstday+GetMonthCount(yy,mm)) //�¸��µĲ���
{
da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
da.title=(mm==12?1:mm+1) +"��" + meizzWDay[i] + "��";
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
//��ѡ�е�������ʾΪ����ȥ
if((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
meizzWDay[i]==outDate.getDate())
{
da.borderColorLight="#FFFFFF";
da.borderColorDark="#99CCFF";
}
}
}
else //���µĲ���
{
da.innerHTML="<b>" + meizzWDay[i] + "</b>";
da.title=mm +"��" + meizzWDay[i] + "��";
da.onclick=Function("meizzDayClick(this.innerText,0)"); //��td����onclick�¼��Ĵ���
//����ǵ�ǰѡ������ڣ�����ʾ����ɫ�ı���������ǵ�ǰ���ڣ�����ʾ����ɫ����
if(!outDate)
da.style.backgroundColor = (yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
"#99ccff":"#e0e0e0";
else
{
da.style.backgroundColor =(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())?
"#ff9900":((yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
"#99ccff":"#e0e0e0");
//��ѡ�е�������ʾΪ����ȥ
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

function meizzDayClick(n,ex) //�����ʾ��ѡȡ���ڣ������뺯��*************
{
var yy=meizzTheYear;
var mm = parseInt(meizzTheMonth)+ex; //ex��ʾƫ����������ѡ���ϸ��·ݺ��¸��·ݵ�����
//�ж��·ݣ������ж�Ӧ�Ĵ���
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
outObject.value= yy + "-" + mm + "-" + n ; //ע�����������������ĳ�����Ҫ�ĸ�ʽ
meizzTheYear=yy; //������ı����ĳ�ʼֵ
meizzTheMonth=mm; //�����µı����ĳ�ʼֵ
//meizzSetDay(meizzTheYear,meizzTheMonth);
meizzValue(n);

outObject.focus();
}
else {closeLayer(); alert("����Ҫ����Ŀؼ����󲢲����ڣ�");}
}

/**********************************************************************************
����sel_time

�汾 1.01
����: timeName: �����ؼ��������ر��������֣�Ҳ����Ϊ3���ֿؼ���ǰ׺
timeSel: 3���ֿؼ�Ԥ����ʾ������
isDisabled��3���ֿؼ��Ƿ�disabled
show: һ�������ֶΣ���������д���Լ��Ķ���
***********************************************************************************/
function sel_time(timeName,timeSel,isDisabled,show){
var hour=0;
var min=0;
//alert(hourSel+" "+minSel);
//alert(houName+" "+minName);
//alert(isDisabled);
//alert(hourSel+" "+minSel);
//alert(hourSel.length+" "+minSel.length)
///////////////////////ȡ��timeSel�е����ں�ʱ��///////////////////////////
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

//���3�����ڷֿؼ�
//������ڿؼ�
document.write("<input name="+timeName+"_date type='text' readonly onclick='setday(this);' show='"+show+"' size=10 value='"+dateSel+"' "+isDisabled+" onfocus='setHidden("+timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName+")'>&nbsp;");
//���Сʱ�ؼ�
document.write("<select name="+timeName+"_hou size=1 "+isDisabled+" onchange='setHidden("+timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName+")'>");
for(;hour<24;hour++){
if(hour<10) hour="0"+hour;
document.write("<option value="+hour)
if(hour==hourSel) document.write(" selected");
document.write(">"+hour+"</option>");

}

document.write("</select>ʱ&nbsp;");
//������ӿؼ�
document.write("<select name="+timeName+"_min size=1 "+isDisabled+" onchange='setHidden("+timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName+")'>");
for(;min<60;min++){
if(min<10) min="0"+min;
document.write("<option value="+min)
if(min==minSel) document.write(" selected");
document.write(" "+isDisabled+" >"+min+"</option>");
}
document.write("</select>��");
//�����������
document.write("<input name="+timeName+" type=hidden value='"+timeSel+"'>");
//setHidden(timeName+"_date,"+timeName+"_hou,"+timeName+"_min,"+timeName);
}

function setHidden(t,s1,s2,h){
//alert(t.value);
h.value=t.value+" "+s1.value+":"+s2.value+":00";
}

function sd(){
if (s.value!=""){
alert('��ѡ���ʱ����:'+s.value);
}
else{
alert('����û��ѡ��ʱ��')
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
<input type="button" value="�����ʾ��ѡ���ʱ��" onclick="sd();">
</form>
</body>
</html>
*/