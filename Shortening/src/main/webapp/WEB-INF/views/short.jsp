<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/head.jsp" %>
<script>
function shortening() {
	var siteUrl = $("#siteUrl").val();
	
	$.ajax({
        url: '/testShortProc.do',
        type: 'post',
        dataType: 'json',
        data: {siteUrl : siteUrl},
        async: 'false',
        success: function (returnData) {
        	var resultCode = returnData.resultCode;
        	var shortKey = returnData.shortKey;
        	var shortUrl = returnData.shortUrl;
        	
        	if (resultCode === 0) {
        		alert("URL Shortening Result\nKey : [" + shortKey + "]\nUrl : [" + shortUrl + shortKey + "]");
        	} else {
            	if (resultCode === -1) {
            		alert("Duplicate URL Shortening Count Update Fail.");
            	} else {
            		alert("URL Shortening Result Insert Fail.");
            	}
        	}
        },
        error: function (jqXHR, textStatus, errorThrown, e, request) {
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
}
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="wrap">
		<div class="header">
			<%--S:상단 링크,메뉴--%>
			<jsp:include page="/WEB-INF/views/top.jsp" flush="true" />
			<%--E:상단 링크,메뉴--%>
		</div>
		<div class="container">
			<div class="data_area">
				<div class="test_area">
					<p class="left_desc">
						<span>※ 단위(기능) 테스트를 위한 페이지입니다.<br>※ 서로 다른 URL 최대 10개까지 테스트 가능하며, 10개 초과시 메모리가 초기화됩니다.</span>
					</p>
					<table class="table_st1">
						<caption></caption>
						<colgroup>
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>대상 URL</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input style="width: 95%;" type="text" name="siteUrl" id="siteUrl"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="proc_area" style="padding-top:90px">
					<a href="javascript:shortening();" class="btn_st4">Shortening</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>