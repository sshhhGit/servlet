<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aa.jsp</title>
</head>

<%
//java구문
String title = request.getParameter("title");
String imgfile = request.getParameter("imgfile");
String content = request.getParameter("content");
%>
<body bgcolor='orange' align='center'>
<br>
<a href="/02_servlet/Cart">상품목록</a>
<br>

	<font color=red size=20><%=title%></font><br><br><hr><br>
	<img src="/02_servlet/imgs/<%=imgfile%>" width="300" height="300"><br>
	<hr>
	<font color='blue' size=5>2021-08-13 빅세일 합니다 10분동안</font>
	<br>
	
	<font size=5><%=content%></font>
</body>
</html>