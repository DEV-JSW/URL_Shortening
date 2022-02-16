<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/head.jsp" %>
<script>
$(document).ready(function(){
	var resultCode = "${resultCode}";
	var url = "${siteUrl}";
	
	if (resultCode === "0") {
		window.self.location = "${siteUrl}";
	} else {
		if (resultCode === "-1") {
			alert("Shortening URL Redirection Count Update Fail.");
		} else {
			alert("Not Found Shortening URL Redirection Info.");
		}
	}
});
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>