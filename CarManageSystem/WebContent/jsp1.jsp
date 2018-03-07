<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
测试jsp页面：
<%
	if(request.getAttribute("list")!=null){
		java.util.List<String> list = (java.util.List<String>)request.getAttribute("list");
		int no = 0;
		for(String line: list){
			no++;
%>
	<p>No.<%= no %>，<%= line %></p>
<%
		}
	}
%>
</body>
</html>