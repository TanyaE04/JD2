<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="admin.css" type="text/css">
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.locbutton.ru" var="ru"/>
<fmt:message bundle="${loc}" key="local.locbutton.en" var="en"/>
</head>
<body>
<style> 

table {
margin-top: 10px;
  width:100%;
}

table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
  padding: 15px;
  text-align: left;
}

table td {
	background: rgba(135, 206, 235, 0.5); 
}

table th {
  background-color: #8000ff;
  color: white;
}

</style>
 <c:if test="${not empty sessionScope.user}">
    	<form action="controller" method="post">
					<input type="hidden" name="command" value="logout" />
					
					<input style="border-radius: 12px; border: 2px solid #f44336;float:right; margin-top:10px;"type="submit" value="Выйти" />
		</form></c:if>
<div class="topnav">
  <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
  <a href="controller?command=showorder&position=0"><fmt:message bundle="${loc}" key="local.href.orders" /></a>
   <a href="controller?command=showcar"><fmt:message bundle="${loc}" key="local.href.cars" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="controller?command=showuser" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="controller?command=showuser" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
</div>
<br>
	<form action="add_user">
		<button type="submit">Добавить нового пользователя</button>
	</form>
<br>
 <div class="main">
	<div class="message">
	<c:if test="${not empty sessionScope.editMessage}">
<c:out value="${sessionScope.editMessage}"></c:out>  <br>
 </c:if>
 <c:remove var = "editMessage"/>
</div>
</div>	
<table>
<tr>
<th>ID пользователя</th>
<th>Роль</th>
<th>Фамилия, Имя</th>
<th>Номер телефона</th>
<th>Эл. почта</th>
<th>Адрес</th>
<th>Водителськое удостоверение</th>
<th>Паспорт</th>
<th>Задолженность</th>
<th>Операции</th>

</tr>
	<c:forEach var="user" items="${users}" >
        <tr>
        	<td><c:out value="${user.idUser}"></c:out><br>
        	<td><c:out value="${user.roleName}"></c:out><br>
        	<td><c:out value="${user.surname} ${user.name}"></c:out><br>
        	<td><c:out value="${user.phone}"></c:out><br>
        	<td><c:out value="${user.mail}"></c:out><br>
        	<td><c:out value="${user.address}"></c:out><br>
        	<td><c:out value="${user.driverLicense}"></c:out><br>
        	<td><c:out value="${user.passport}"></c:out><br>
        	<td><c:if test="${user.debt!=0}"><c:out value="${user.debt}"></c:out></c:if></td>
        	<td>
        	<c:if test="${empty user.status}">
        		<form onsubmit="return myFunction()" action="controller" method="post">
					<input type="hidden" name="command" value="blockuser" />
					<input type="hidden" name="iduser" value="${user.idUser}" />
					<input type="hidden" name="userstatus" value="${user.status}" />
					<input type="submit" value="Заблокировать" />
					
				</form>
			</c:if>
			<c:if test="${not empty user.status}">
				<form onsubmit="return myFunction()" action="controller" method="post">
					<input type="hidden" name="command" value="blockuser" />
					<input type="hidden" name="iduser" value="${user.idUser}" />
					<input type="hidden" name="userstatus" value="${user.status}" />
					<input type="submit" value="Разблокировать" />
				</form>
			</c:if>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="edituser" />
					<input type="hidden" name="iduser" value="${user.idUser}" />
					<input type="submit" value="Редактировать" />
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
<input type="button" onclick="history.back();" value="Назад"/>
<script>
function myFunction() {
 
  var r = confirm("Вы уверены?");
  if (!r) {
    return false;
  }
  
}
</script>
</body>
</html>