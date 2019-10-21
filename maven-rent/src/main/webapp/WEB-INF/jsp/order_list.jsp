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
  <a href="controller?command=showuser"><fmt:message bundle="${loc}" key="local.href.users" /></a>
   <a href="controller?command=showcar"><fmt:message bundle="${loc}" key="local.href.cars" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="controller?command=showcar&position=0&count=3" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="controller?command=showcar&position=0&count=3" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
</div>
<br>	
<form action="controller" method="post">
					<input type="hidden" name="command" value="addcar" />
					<input type="hidden" name="idcar" value="${car.idCar}" />
					<input type="submit" value="Создать новый заказ" />
	</form>
<br>
<table>
<tr>
<th>ID заказа</th>
<th>ID авто</th>
<th>Дата аренды</th>
<th>Количество дней</th>
<th>Дата возвращения</th>
<th>Количество реальных дней аренды</th>
<th>Общая сумма</th>
<th>ID отказа</th>
<th>ID пользователя</th>
<th>Операции</th>
</tr>
	<c:forEach var="order" items="${orders}" >
        <tr>
        	<td><c:out value="${order.idOrder}"></c:out><br>
        	<td><c:out value="${order.idCar}"></c:out><br>
        	<td><c:out value="${order.dateRent}"></c:out><br>
        	<td><c:out value="${order.days}"></c:out><br>
        	<td><c:out value="${order.dateReturn}"></c:out><br>
        	<td><c:out value="${order.realDays}"></c:out><br>
        	<td><c:out value="${order.total}"></c:out><br>
        	<td><c:out value="${order.idRefusal}"></c:out><br>
        	<td><c:out value="${order.idUsers}"></c:out><br>
        	<td><form action="controller" method="post">
					<input type="hidden" name="command" value="removeorder" />
					<input type="hidden" name="idorder" value="${order.idOrder}" />
					<input type="submit" value="Удалить" />
				</form>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="updateorder" />
					<input type="hidden" name="idorder" value="${order.idOrder}" />
					<input type="submit" value="Редактировать" />
				</form>
				<c:if test="${not empty order.status}">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="completeorder" />
					<input type="hidden" name="idorder" value="${order.idOrder}" />
					<input type="submit" value="Завершить заказ" />
				</form>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="pagination">
  <c:forEach var="count" items="${counts}">
  	<a href="controller?command=showorder&position=${count}">${count+1}</a>
  </c:forEach>
  <a href="controller?command=showorder">Все</a>
</div>

<input type="button" onclick="history.back();" value="Назад"/>
</body>
</html>