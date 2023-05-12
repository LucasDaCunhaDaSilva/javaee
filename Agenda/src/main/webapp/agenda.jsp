<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import = "model.JavaBeans"%>
<%@page import = "java.util.List" %>

<% //CODIGO JAVA ------------------------------------------------------------------------- 
@SuppressWarnings("unchecked")	
List<JavaBeans> contatos = (List<JavaBeans>) request.getAttribute("contatos"); 
%>

<!DOCTYPE html>
<html lang=pt-br>
<head>
	<meta charset="UTF-8">
	
	<title>Agenda</title>
	<link rel="stylesheet" href="css/Style.css">
	<link rel="icon" href="Images/phone.png">
</head>
<body>
	<h1>Agenda de Contatos</h1>
	<a href= "novo.html" class="Button" >Adicionar Contato</a>
	<a href= "report" class="NegativeButton" >Gerar relatório</a>
	
	<table id="table">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Fone</th>
				<th>E-Mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		<tbody>
			<% for(JavaBeans contato: contatos) { %>
				<tr>
					<td><%= contato.getId()    %></td>
					<td><%= contato.getName()  %></td>
					<td><%= contato.getFone()  %></td>
					<td><%= contato.getEmail() %></td>
					<td>
						<a href="select?id=<%= contato.getId() %>" class="Button">Editar</a>
						<a href="javascript: confirmDelete('<%= contato.getName() %>',<%= contato.getId() %>)" class="NegativeButton">Excluir</a>
					</td>
				</tr>	
	
			<%} %>
		</tbody>
		
	</table>
	
	<script src="scripts/Confirm.js"></script>
</body>
</html>