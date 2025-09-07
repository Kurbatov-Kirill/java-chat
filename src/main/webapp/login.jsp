<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="/TestTomcat/chat">
		<p>Username</p>
		<p><input type="text" name="login"></p>
		<p>Password</p>
		<p><input type="text" name="password"></p>
		<p><input type="submit" name="action" value="Login"><input type="submit" name="action" value="Signup"></p>
	</form>
</body>
</html>