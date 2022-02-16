<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/head.jsp" %>
<script>
$(document).ready(function(){
	$.ajax({
        url: "/testHistoryList.do",
        type: "post",
        dataType: "json",
        async: "false",
        success: function (returnData) {
        	if($("#histBody tr").length>0) $("#histBody tr").empty();
        	$("#histCount").empty();
        	
        	var histList = returnData.histList;
        	var histCount = returnData.histCount;
        	
        	if (histList.length>0) {
        		var content = "";
        		
				for (var i = 0; i < histList.length; i++) {
					content += "<tr>"
					  + "<td>" + histList[i].siteUrl + "</td>"
					  + "<td>" + histList[i].shortCnt + "</td>"
					  + "<td>" + histList[i].shortKey + "</td>"
					  + "<td>" + histList[i].redirectCnt + "</td>";
				}
				
				$("#histBody").append(content);
				$("#histCount").append(histCount);
        	} else {
        		var content = "<tr>";
        		
        		content += "<td colspan='4'>";
        		content += "요청하신 정보가 없습니다.";
        		content += "</td>";
        		content += "</tr>";

				$("#histBody").append(content);
        		$("#histCount").append("0");
        	}
        },
        error: function (jqXHR, textStatus, errorThrown, e, request) {
            if (jqXHR.status == 400) {
            	if(jqXHR.getResponseHeader("resut") != null){
            		alert("유효하지 않은 요청정보("+decodeURIComponent(jqXHR.getResponseHeader("resut"))+")가 포함되어 있습니다.");
            	}else{
            		alert("잘못된 요청정보 입니다.");
            	}
                location.href = "/";
            } else if (jqXHR.status == 999) {
                alert("접근 허용 아이피가 아닙니다.");
                location.href = "/";
            } else if (jqXHR.status == 888) {
                alert("해당 페이지에 접근 권한이 없습니다.");
            } else if (jqXHR.status == 777) {
                alert("유효하지 않는 패턴이 있습니다.");
            } else {
                alert("문제가 발생하였습니다.\n관리자에게 문의하시기바랍니다.");
            }
            return null;
        }
	});
});
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
				<div class="dase_area">
					<p class="left_desc">
						이력 관리 : 총 <span id="histCount"></span>건
					</p>
				</div>
				<table class="table_st1">
					<caption></caption>
					<colgroup>
						<col width="40%"/>
						<col width="" />
						<col width="" />
						<col width="" />
					</colgroup>
					<thead>
						<tr>
							<th>Target URL</th>
							<th>Shortening Count</th>
							<th>Shortening Key</th>
							<th>Redirect Count</th>
						</tr>
					</thead>
					<tbody id="histBody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>