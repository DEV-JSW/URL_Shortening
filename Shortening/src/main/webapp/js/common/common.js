/** 
* @File			: common.js
* @Description  : 공통함수
* @Modification Information
* @
* @     수정일                             수정자                                             수정내용
* @  ----------   	--------    ---------------------------
* @  2018. 3. 8.     신영석                                            최초 생성
*
* @author       : 신영석
* @since        : 2018. 3. 8.
* @version 1.0
* @see
* Copyright (C) by WISECAN All right reserved.
*/


/**
 * 함수명  : isNull
 * 내용   : 데이터 있는지 없는지 확인 (null, '', undefined check)
 * 반환값	: 없으면 true, 있으면 false
 */
function isNull(str) {
	var rVal = false;
	if (str == null) {
		rVal = true;
	} else if (str == '') {
		rVal = true;
	} else if (str == undefined) {
		rVal = true;
	}
	return rVal;
}

function moveFocus(fromform,toform){	
	var str = fromform.value.length;
	var maxLength = fromform.maxLength;
	if(str == maxLength) {
		$('#'+toform).focus();
	}    
}

/**
숫자만 입력가능하게.
48~57(0번 ~ 9번), 95~105(키패드0번~9번), 37~40(방향키), 8(백스페이스), 46(Del키), 9(TAB키), -=189, -(키패드)=109 , .=190
ex1) <input type="text" name="aaa" onKeyDown="onlynumber(event);"/>
ex2)	$("#cpsno").keydown(function() {
			onlynumber(event);
   });
**/
/*
function onlyNumber222(event) {
	var eventKey = event.keyCode;
	
	if ((eventKey > 47 && eventKey < 58) || (eventKey > 95 && eventKey < 106)
			|| (eventKey > 34 && eventKey < 41) || eventKey == 8
			|| eventKey == 46 ) {
	}else{
		return false;
	}
}
*/
function onlyNumber( Ev )
{
    if (window.event) // IE코드
        var code = window.event.keyCode;
    else // 타브라우저
        var code = Ev.which;
 
    if ((code > 34 && code < 41) || (code > 47 && code < 58) || (code > 95 && code < 106) || code == 8 || code == 9 || code == 13 || code == 46)
    {
        window.event.returnValue = true;
        return;
    }
 
    if (window.event)
        window.event.returnValue = false;
    else
        Ev.preventDefault();    
}

function onlyNumber2( Ev ){
	//debugger;
    if (window.event) // IE코드
        var code = window.event.keyCode;
    else // 타브라우저
        var code = Ev.which;
 
    
    if ((code > 34 && code < 41) || (code > 47 && code < 58) || (code > 95 && code < 106) || code == 8 || code == 9 || code == 13 || code == 46 || ((code==190 || code==110) && event.target.value.indexOf('.')=='-1'))
    {
        window.event.returnValue = true;
        return;
    }
 
    if (window.event)
        window.event.returnValue = false;
    else
        Ev.preventDefault();    
}

/*

function only_number(){
	if(event.keyCode >= 48 && event.keyCode <= 57){
		return;
	}else{
		if(event.preventDefault){
			event.preventDefault();
		}else{
			event.returnValue = false;
		}
	}
}

function onlyNumber(event){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 9) 
		return;
	else
		return false;
}
*/
function removeChar(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 9) 
		return;
	else
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
}

function onlyNaturalNumber(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 9) 
		return;
	else{
		event.target.value = event.target.value.replace(/[^0-9.]/g, '').replace(/^[.]*/, '').replace(/[.]{2,}/, '.');
		
		if(event.target.value.substring()>1){
			if(event.target.value.substring(0,2)=='0.') return;
			event.target.value = event.target.value.replace(/^0+/, '');
		}
	}
		
}

function chkNaturalNumber(elem){
	var val=elem.value;
	val=val.replace(/[^0-9.]/g, '').replace(/^[.]*/, '').replace(/[.]{2,}/, '.').replace(/[.]*$/, '');
	elem.value=val;
}

function email_chk(){
	var form = document.work_form;
	if(form.email1.value.replace(/\s/g, "") == "" || form.email2.value.replace(/\s/g, "") == ""){
		alert("이메일을 정확하게 입력해주세요.");
		form.email1.focus();
		return;
	}

	if(form.mailselect.value){ 
		alert("사용할 수 있는 메일입니다.  ");
		form.mailuse.value = "y";
	}else{
		if(korean_check(form.email2.value) == true){
			alert("한글은 입력할 수 없습니다.   ");
			form.email2.focus();
			return;
		}
		var mail = form.email1.value + "@" + form.email2.value;
		mail = escape(mail);
		var re=/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.]+\.[a-zA-Z]{2,4}$/i;
		if(re.test(mail)){
			alert("사용할 수 있는 메일입니다.  ");
			form.mailuse.value = "y";
		}else{
			alert("사용할 수 없는 메일입니다.  ");
			form.email2.select();
			form.mailuse.value = "n";
		}

	}
}

/** 
 * 이메일 유효성 검사
 * @param email
 * @returns
 */
function chkEmail(email){
	var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if (regExp.test(email)) return true;
    else return false;
}

/**
 * 영문/숫자만 입력하게
 * @param obj
 * @returns {Boolean}
 */
function korean_check(obj){
	var rtn; 
	for(var i=0;i<obj.length;i++){
		var vAsc = obj.charCodeAt(i);
		var vChr = obj.charAt(i);
		if((vAsc>96) && (vAsc<124) || (vAsc>64) && (vAsc<91) || (vAsc>47) && (vAsc<58) || (vAsc == 32) || (vAsc == 46)){
			rtn = false;
		}else{
			rtn =true; 
			break;
		}
	}
	return rtn;
}


/**
 * 랜덤 수 만들기
 * @param n : 자릿수
 */
function makeRandomNumber(n){	
	var randomNumber = '';
	for(i=0; i<n; i++){
		var tempRanNum = Math.floor(Math.random()*10);		
		randomNumber += tempRanNum;
	}
	return randomNumber;
}

/**
 * 함수명 : isEmptyObject
 * 함수내용 : 빈 객체 확인
 * @param obj
 */
function isEmptyObject(obj){
	if(Object.keys(obj).length>0)	return false;
	else							return true;	
}

/**
 * 콤마찍기
 * @param str
 * @returns
 */
function addComma(str) {
	if(isNull(str)){
    	return 0;
    }
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

/**
 * 콤마제거
 * @param str
 * @returns
 */
function removeComma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}
/**
 * 데이트변형1
 * YYYYMMDDHHMM
 * @param str
 */
function convertDate(str){
	if(isNull(str)) return '';
	
	var year = str.substring(0,4);
	var month = str.substring(4,6);
	var day = str.substring(6,8);
	var hour = str.substring(8,10);
	var min = str.substring(10,12);
	
	return year + '-' + month + '-' + day + ' ' + hour + ':' + min;	
}

/**
 * 데이트변형1_1
 * YYYYMMDD<br>HHMM
 * @param str
 */
function convertDate_br(str){
	if(isNull(str)) return '';
	
	var year = str.substring(0,4);
	var month = str.substring(4,6);
	var day = str.substring(6,8);
	var hour = str.substring(8,10);
	var min = str.substring(10,12);
	
	return year + '-' + month + '-' + day + '<br>' + hour + ':' + min;	
}

/**
 * 데이트변형2
 * YYYYMM
 * @param str
 */
function convertDate_YM(str){
	if(isNull(str)) return '';
	
	var year = str.substring(0,4);
	var month = str.substring(4,6);	
	
	return year + '-' + month;	
}

/**
 * 데이트변형2_k
 * YYYYMM
 * @param str
 */
function convertDate_YM_K(str){
	if(isNull(str)) return '';
	
	var year = str.substring(0,4);
	var month = str.substring(4,6);	
	
	return year + '년 ' + month + '월';	
}

/**
 * 데이트변형3
 * YYYYMMDD
 * @param str
 */
function convertDate_YMD(str){
	if(isNull(str)) return '';
	
	var year = str.substring(0,4);
	var month = str.substring(4,6);	
	var day = str.substring(6,8);
	
	return year + '-' + month + '-' + day;	
}

/**
 * 데이트변형4
 * YYYYMMDD
 * @param str * 
 */
function convertDate_YMD_K(str){
	if(isNull(str)) return '';
	
	var year = str.substring(0,4);
	var month = str.substring(4,6);	
	var day = str.substring(6,8);
	
	return year + '년 ' + month + '월 ' + day + '일';	
}

function nvl(str, rStr){
	var replaceStr = isNull(rStr) ? '' : rStr;
	if(isNull(str)) return replaceStr;
	else			return str;
}

function openPop(url, popNm, popWidth, popHeight){
	var winHeight = window.outerHeight;	// 현재창의 높이
	var winWidth = window.outerWidth;	// 현재창의 너비
	var winX = window.screenLeft;	// 현재창의 x좌표
	var winY = window.screenTop;	// 현재창의 y좌표
	var popX = winX + (winWidth - popWidth)/2;
	var popY = winY + (winHeight - popHeight)/2;
		
	window.open(url,popNm,"width="+popWidth+"px,height="+popHeight+"px,top="+popY+",left="+popX);	
}

function closePop(){
	self.close();
}

/**
 * 년월일시분초
 * @returns
 */
function getTimeStamp() {
	var d = new Date();
	var month = d.getMonth() + 1
	var date = d.getDate()
	var hour = d.getHours()
	var minute = d.getMinutes()
	var second = d.getSeconds()

	month = (month < 10 ? "0" : "") + month;
	date = (date < 10 ? "0" : "") + date;
	hour = (hour < 10 ? "0" : "") + hour;
	minute = (minute < 10 ? "0" : "") + minute;
	second = (second < 10 ? "0" : "") + second;

	var s =	d.getFullYear() + month + date + hour + minute + second

	return s;
}

//날짜포맷 지정하는 함수
function dateToYYYYMMDD(date)
{
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + pad(date.getMonth()+1) + pad(date.getDate());
}

function emailSet(id){
	//이메일주소 셀렉트 박스 세팅
	var mailDomains = ['naver.com','chol.com','empal.com','freechal.com','gmail.com','hanafos.com','hanmail.net','hanmir.com',
		               'hitel.net','hotmail.com','korea.com','lycos.co.kr','nate.com','netian.com','paran.com','yahoo.com','yahoo.co.kr'];
	
	for(i=0; i<mailDomains.length; i++){		
		var mailDomain = mailDomains[i];
		$('#' + id).append('<option value="'+ mailDomain +'">' + mailDomain + '</option>');	
	}
}

function getParameterByName(name) {
	//보낼때 encodingURIComponent 해서 보내기
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function enterkey(func){
     if(event.keyCode == 13){ func(); }
}

function randomStr(careateNum){
	var aryStr1 = "2,3,4,5,6,7,8,9".split(",");
	var aryStr2 = "A,B,C,D,E,F,G,H,J,K,M,N,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
	var aryStr3 = "a,b,c,d,e,f,g,h,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z".split(",");
	var aryStr4 = "!,@,#,$,^,-,=,~".split(",");
		
	var tmpStr, fixStr, chkFlag;

	tmpStr = "";
	fixStr = "";
	chkFlag = false;
		
	fixStr += aryStr2[Math.floor(Math.random() * aryStr2.length)];	//첫글자는 대문자
		
	//숫자 2개
	for(var j=0; j<2; j++){
		tmpStr += aryStr1[Math.floor(Math.random() * aryStr1.length)];
	}
	
	//대문자 3개
	for(var j=0; j<3; j++){
		tmpStr += aryStr2[Math.floor(Math.random() * aryStr2.length)];
	}
	
	//소문자 4개
	for(var j=0; j<4; j++){
		tmpStr += aryStr3[Math.floor(Math.random() * aryStr3.length)];
	}
	
	//특수문자 2개
	for(var j=0; j<2; j++){
		tmpStr += aryStr4[Math.floor(Math.random() * aryStr4.length)];
	}

	//종합 12자리
	for(var j=1; j<12; j++){
		var tmpInt = Math.floor(Math.random() * tmpStr.length);
		fixStr += tmpStr[tmpInt];
		tmpStr = tmpStr.substring(0, tmpInt) + tmpStr.substring(tmpInt+1, tmpStr.length);
	}
	
	//최종값 8자리로 변경
	disStr = fixStr.substring(0, careateNum);
	
	return disStr;
}

/**
 * 해당시간 초로 환산
 */
function dateToSeconds(date){
	if(!date instanceof Date) return '';
	
	return date.getHours()*3600 + date.getMinutes()*60 + date.getSeconds();
}

/**
 * html Tag to Html entity
 * @param str
 * @returns
 */
function htmlTagToEntity(str) {
	return $("<textarea/>").html(str).html();
}

/**
 * HTMLTagFilter에서 html entity로 치환된 문자열을 다시 Html Tag로 변환
 * @param str
 * @returns
 */
function htmlEntityToTag(str) {
	return $("<textarea/>").html(str).val();
}

/**
 * 밀리세컨드 변환
 * OPTION
 * - s: 초 , m: 분, h: 시, 일: d 
 * @param option
 */
function convertMillisecond(ms, option){
	var stndTime = 1000;
	if(option == 's'){
		stndTime = stndTime*1;
	}else if(option == 'm'){
		stndTime = stndTime * 60;
	}else if(option == 'h'){
		stndTime = stndTime * 60 * 60
	}else if(option == 'd'){
		stndTime = stndTime * 60 * 60 * 24
	}
	return ms/stndTime;
}

function datetimeToDate(datetime){
	var a = datetime.split(" ");
	var d = a[0].split("-");
	var t = a[1].split(":");
	var date = new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
	return date;
}

/*
function isUserIdDuplicated(userId){
    var userId = userId.trim();

    cmmAjax({userId:userId}, '/member/selectUserIdDuplChk.do', callback=function(returnData){
        if(returnData.resultCd == '0'){
            if(returnData.userIdDuplChk){
                $('#idChkMsg').removeClass('txt_red txt_blue');
                $('#idChkMsg').addClass('txt_red');
                $('#idChkMsg').show();
                $('#idChkMsg').text('사용이 불가능한 아이디입니다.');
                idDuplChk = false;
            }else{
                $('#idChkMsg').removeClass('txt_red txt_blue');
                $('#idChkMsg').addClass('txt_blue');
                $('#idChkMsg').show();
                $('#idChkMsg').text('사용이 가능한 아이디입니다.');
                idDuplChk = true;
            }
        }else{
            alert('아이디 중복체크 과정에서 문제가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.');
        }
    });
}
*/

function jsonLog(id, json){
	if(isNull(id)){		
		console.log(JSON.stringify(json));
	}else{
		console.log(id + ': ' + JSON.stringify(json));
	}
}

// 전화번호 하이픈 넣기
function autoHypenPhone(getPhoneNum) { 
	var x = getPhoneNum;
    if (isValidate(x)) {
    	return x.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
	} else {
		return x;
    }
}

// 전화번호 유효성 검사
function isValidate(value) {
	var re=/^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
	return re.test(value);
}

function autoHypenBizNo(num, type) {
    var formatNum = '';
    try{
         if (num.length == 10) {
              if (type == 0) {
                   formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-*****');
              } else {
                    formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
              }
         } else {
        	 formatNum = num;
         }
    } catch(e) {
         formatNum = num;
    }
    return formatNum;
}

/**
 * @param {string} escape     ex) &lt;p&gt;escape&lt;/p&gt;
 * @return html로 변경된 문자 ex) <p>escape</p>
 */
function escapeToHtml(escape) {
	const d = document.createElement('div');
	d.innerHTML = escape;
	//console.log(d.textContent);
	return d.textContent;
}

/**
 * @param {string} html         ex) <p>html</p>
 * @return escape로 변경된 문자 ex) &lt;p&gt;escape&lt;/p&gt;
 */
function htmlToEscape(html) {
	const d = document.createElement('div');
	d.textContent = html;
	//console.log(d.innerHTML);
	return d.innerHTML;
}

/**
 * n번째 부모 element 를 찾아서 리턴
 * @param {Element} target         시작 el 위치
 * @param {number}  nth 		   n번째
 * @param {string}  parentElName   찾을 부모 element 이름
 */
function findNthParentElement(target, nth, parentElName) {
	if (target.nodeType !== 1) {
		console.error('only element allowed');
		return;
	}

	let result = nth +'번째 부모 ' + parentElName + '을 찾지 못하였습니다.';
	let count = 1; //몇번째인지 체크

	parentElName = parentElName.toUpperCase(); //tagName은 대문자

	for (let el = target; el !== null; el=el.parentElement) {
		if (el.tagName !== parentElName) continue;
		if (nth === count) {
			result = el;
			//console.log(result);
			return result;
		} else count++;
	}
	console.error(result);
}

/**
 * 문자열의 바이트 크기를 리턴
 * @param   {string} s 문자열
 * @returns {number} 바이트 크기
 */
function getByteLength(s) {
	let b,i,c;// byte, index, character
	for (b = i = 0; i<s.length; i++) {
		c = s.charCodeAt(i)
		b += c >> 11 ? 3 : c >> 7 ? 2 : 1; //2048로나눴을때 몫이 있으면 3바이트 다시 128이랑비교해서 몫이 있으면 2바이트 없으면1바이트
	}
	return b;
}

const deepFreeze = function (obj) {
	Object.keys(obj).forEach(function (prop) {
		if (typeof(obj[prop]) === 'object' && !Object.isFrozen(obj[prop])) deepFreeze(obj[prop]);
	});
	return Object.freeze(obj);
};