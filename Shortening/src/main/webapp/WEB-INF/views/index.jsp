<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/head.jsp" %>
<script>
function shortening() {
	var siteName = $("#siteName").val();
	var siteUrl = $("#siteUrl").val();
	
	$.ajax({
        url: '/shortening.do',
        type: 'post',
        dataType: 'json',
        data: {siteName : siteName, siteUrl : siteUrl},
        async: 'false',
        success: function (returnData) {
        	var resultCode = returnData.resultCode;
        	var shortKey = returnData.shortKey;
        	var shortUrl = returnData.shortUrl;
        	
        	if (resultCode === 0) {
            	$("#shortKey").val(shortKey);
            	$("#shortUrl").val(shortUrl + shortKey);
        	} else {
            	$("#shortKey").val("");
            	$("#shortUrl").val("");
            	
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

function openHistPop(flag, popup) {
	var search = "";
	
	if (flag === 0) {
		search = $("#siteUrl").val();
	} else {
		search = $("#shortKey").val();
	}
	
	if (!search.trim()) {
		return;
	}
	
	$.ajax({
        url: '/history.do',
        type: 'post',
        dataType: 'json',
        data: {flag : flag, search : search},
        async: 'false',
        success: function (returnData) {
        	if ($("#histBody tr").length > 0) {
        		$("#histBody tr").empty();
        	}
        	
        	var content = "";
        	var histData = returnData.histData;
        	
        	if (histData !== null) {
        		content += "<tr>";
        		content += "<th>";
        		if (flag === 0) {
        			content += "대상 URL";
        		} else {
        			content += "결과 URL";
        		}
        		content += "</th>";
        		content += "<td>";
        		if (flag === 0) {
        			content += histData.siteUrl;
        		} else {
        			content += histData.shortUrl;
        		}
        		content += "</td>";
        		content += "</tr>";
        		content += "<tr>";
        		content += "<th>";
        		if (flag === 0) {
        			content += "Shortening<br>Count";
        		} else {
        			content += "Redirection<br>Count";
        		}
        		content += "</th>";
        		content += "<td>";
        		content += histData.cnt;
        		content += "</td>";
        		content += "</tr>";
        	} else {
        		content += "<tr>";
        		content += "<td colspan='2'>";
        		content += "요청 결과가 없습니다.";
        		content += "</td>";
        		content += "</tr>";
        	}
        	
        	$("#histBody").append(content);

        	$('#' + popup).show();
        	$('#' + popup).centerPop();
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

function closeHistPop(popup) {
	$('#' + popup).hide();
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
				<div class="reqt_area">
					<p class="right_desc">
						<a href="javascript:openHistPop(0, 'histPop');" class="btn_st1 sys_mode">요청이력</a>
					</p>
					<table class="table_st1">
						<caption></caption>
						<colgroup>
							<col width="25%" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>사이트명</th>
								<th>대상 URL</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input style="width: 90%;" type="text" name="siteName" id="siteName"></td>
								<td><input style="width: 90%;" type="text" name="siteUrl" id="siteUrl"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="resp_area">
					<p class="right_desc">
						<a href="javascript:openHistPop(1, 'histPop');" class="btn_st1 sys_mode">요청이력</a>
					</p>
					<table class="table_st1">
						<caption></caption>
						<colgroup>
							<col width="25%" />
							<col width="" />
						</colgroup>
						<thead>
							<tr>
								<th>Key</th>
								<th>결과 URL</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input style="width: 90%;" type="text" name="shortKey" id="shortKey" readOnly></td>
								<td><input style="width: 90%;" type="text" name="shortUrl" id="shortUrl" readOnly></td>
							</tr>
						</tbody>
					</table>
					<table class="title_table">
					</table>
				</div>
				<div class="proc_area">
					<a href="javascript:shortening();" class="btn_st4">Shortening</a>
				</div>
			</div>
		</div>
		<div class="layer_area">
			<div id="histPop" class="layer_wrap">
				<p class="layer_tit">
					<span>History</span>
					<a href="javascript:closeHistPop('histPop');">
					<img src="/images/layout/layer_x.png" alt="닫기" /></a>
				</p>
				<div class="layer_cont">
					<h4 class="sub_h4">요청 이력</h4>
					<table class="layer_req_input">
						<caption>기본정보</caption>
						<colgroup>
							<col width="140" />
							<col width="180" />
						</colgroup>
						<tbody id="histBody">
						</tbody>
					</table>
					<p class="layer_req_btn" style="margin-top: 20px !important">
						<a href="javascript:closeHistPop('histPop');" class="l_cancel_btn">닫기</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>