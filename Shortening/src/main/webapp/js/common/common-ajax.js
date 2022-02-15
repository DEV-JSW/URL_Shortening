/*** url로 ajax 요청을 하고 callBack함수를 호출한다. ***/
function cmmAjax(data, url, callback, async, isShowProgress) {
    if (async == undefined) async = true;

    if( isShowProgress == undefined) isShowProgress= true;
    let result;

    //}
    $.ajax({
        url: url,
        type: 'post',
        data: data,
        dataType: 'json',
        async: async,
        //contentType: "application/x-www-form-urlencoded; charset=EUC-KR",

        beforeSend: function (xmlHttpRequest) {
            //xmlHttpRequest.setRequestHeader("AJAX", "true");

            if( isShowProgress) {
                $("#progressDiv").show();
                $("#progressNotice").show();
            }
        },
        complete: function () {
            if( isShowProgress ) {
                $("#progressDiv").hide();
                $("#progressNotice").hide();
            }
        },
        success: function (returnData) {
            var isDev = true;
            if( isDev) {
                // IE 도 동작하게 함
                if (window['console'] != 'undefined') {
                    console.log(url, "====>", data, '\n', returnData);
                }
            }
            result = returnData;
            callback(returnData);
        },
        error: function (jqXHR, textStatus, errorThrown, e, request) {
            //console.log("***cmmAjax error: " + JSON.stringify(jqXHR));
            //console.log("***cmmAjax error: " + JSON.stringify(textStatus));
            //console.log("***cmmAjax error: " + JSON.stringify(errorThrown));
            if(isShowProgress) {
                $("#progressDiv").hide();
                $("#progressNotice").hide();
            }

            if (jqXHR.status == 400) {
            	if(jqXHR.getResponseHeader('resut') != null){
            		alert('유효하지 않은 요청정보('+decodeURIComponent(jqXHR.getResponseHeader('resut'))+')가 포함되어 있습니다.');
            	}else{
            		alert('잘못된 요청정보 입니다.');
            	}
                location.href = '/';
            } else if (jqXHR.status == 999) {
                alert('접근 허용 아이피가 아닙니다.');
                location.href = '/';
            } else if (jqXHR.status == 888) {
                alert('해당 페이지에 접근 권한이 없습니다.');
            } else if (jqXHR.status == 777) {
                alert('유효하지 않는 패턴이 있습니다.');
            } else {
                alert("문제가 발생하였습니다.\n관리자에게 문의하시기바랍니다.");
            }
            return null;
        }
    });
    return result;
}


/*** url로 ajaxFormdata 요청을 하고 callBack함수를 호출한다. ***/
function cmmAjaxForm(formData, url, callback, async, isShowProgress) {
    if (async === undefined) async = true;

    if( isShowProgress === undefined) isShowProgress= true;

    $.ajax({
        url: url,
        data: formData,
        dataType: "json",
        type: "POST",
        cache: false,
        processData: false,
        contentType: false,
        beforeSend: function () {
            if( isShowProgress) {
                $("#progressDiv").show();
                $("#progressNotice").show();
            }
        },
        complete: function () {
            if( isShowProgress ) {
                $("#progressDiv").hide();
                $("#progressNotice").hide();
            }
        },
        success: function (returnData) {
            callback(returnData);
        },
        error: function (jqXHR, textStatus, errorThrown, e) {
            //console.log("***cmmAjax error: " + JSON.stringify(jqXHR));
            //console.log("***cmmAjax error: " + JSON.stringify(textStatus));
            //console.log("***cmmAjax error: " + JSON.stringify(errorThrown));
            if(isShowProgress) {
                $("#progressDiv").hide();
                $("#progressNotice").hide();
            }

        	 if (jqXHR.status == 400) {
        		 if(jqXHR.getResponseHeader('resut') != null){
        			 alert('유효하지 않은 요청정보('+decodeURIComponent(jqXHR.getResponseHeader('resut'))+')가 포함되어 있습니다.');
        		 }else{
        			 alert('잘못된 요청정보 입니다.');
        		 }
        		 location.href = '/';
             } else if (jqXHR.status == 999) {
                 alert('접근 허용 아이피가 아닙니다.');
                 location.href = '/';
             } else if (jqXHR.status == 888) {
                 alert('해당 페이지에 접근 권한이 없습니다.');
             } else if (jqXHR.status == 777) {
                 alert('유효하지 않는 패턴이 있습니다.');
             } else {
                 alert("문제가 발생하였습니다.\n관리자에게 문의하시기바랍니다.");
             }
        }
    });
}

