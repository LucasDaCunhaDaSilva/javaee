<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "model.JavaBeans" %>
<% JavaBeans contato = (JavaBeans) request.getAttribute("contato"); %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Editar Contato</title>
		<link rel="icon" href="Images/phone.png">
		<link rel="stylesheet" href="css/Style.css">
	</head>
	<body>
		<h1>Editar Contato</h1>
		<form name = "frmContato" action="update">
			<table>
				<tr>
					<td> <input type="text" name="id"   class="Caixa3" value="<%=contato.getId() %>" readonly > </td>
				</tr>
				<tr>
					<td> <input type="text" name="name" class="Caixa" value="<%=contato.getName() %>" > </td>
				</tr>
				<tr>
					<td> <input type="text" name="fone"  class="Caixa2" value="<%=contato.getFone() %>" > </td>
				</tr>
				<tr>
					<td> <input type="text"name="email" class="Caixa" value="<%=contato.getEmail() %>" > </td>
				</tr>	
			</table>
			
			<input type="button" value="Salvar" class="Button" onclick="validar()">
		</form>
		
		<script src="scripts/Validador.js"></script>
	</body>
</html>