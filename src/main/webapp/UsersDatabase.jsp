<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users Database</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<h1>Usuários no Banco:</h1>
	<hr>
	<table width='100%'>
		<tr id='top'>
			<td><strong>ID</strong></td>
			<td><strong>USUÁRIO</strong></td>
			<td><strong>SENHA</strong></td>
			<td><strong>EDITAR</strong></td>
			<td><strong>APAGAR</strong></td>
		</tr>
	<% 
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = 
				DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
		Statement stm = conn.createStatement();
		String SQL = "SELECT * FROM users";
		ResultSet result = stm.executeQuery(SQL);
		while(result.next()) {
	%>		
		<tr class='down'>
			<td><%=result.getInt("id") %></td>
			<td><%=result.getString("user") %></td>
			<td><%=result.getString("password") %></td>
			<td><a href='#'>[EDITAR]</a></td>
			<td><a href='http://localhost:8080/JEERLSYSTEM/database?id=<%=result.getInt("id") %>'>[APAGAR]</a></td>
		</tr>
	<%
		}
	%>
	</table>
	<br>
	<a href='http://localhost:8080/JEERLSYSTEM/index'>&lt;- Home</a>
	<!--  
	<script>
		window.alert('Fala Galera!')
	</script>
	-->
</body>
</html>