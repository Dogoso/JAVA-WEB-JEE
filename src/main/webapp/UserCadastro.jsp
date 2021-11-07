<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de usuário</title>
</head>
<body>
	<h1>Preencha as informações abaixo:</h1>
	<hr>
	<form method='POST' action='cadastro'>
		<br>
		Usuário: <input type='text' name='txtUser'> <br> <br>
		Senha: <input type='password' name='txtPassword'> <br> <br>
		<input type='submit' value='Cadastrar'>
	</form>
</body>
</html>