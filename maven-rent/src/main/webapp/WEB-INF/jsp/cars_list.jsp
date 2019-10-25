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
<fmt:message bundle="${loc}" key="button.search" var="butsearch"/>
<fmt:message bundle="${loc}" key="button.order" var="butorder"/>
<fmt:message bundle="${loc}" key="button.find" var="butfind"/>
<fmt:message bundle="${loc}" key="button.logout" var="butlogout"/>
<fmt:message bundle="${loc}" key="button.back" var="butback"/>
<fmt:message bundle="${loc}" key="button.edit" var="butedit"/>
<fmt:message bundle="${loc}" key="button.delete" var="butdelete"/>
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
					
					<input style="border-radius: 12px; border: 2px solid #f44336;float:right; margin-top:10px;"type="submit" value="${butlogout}" />
		</form></c:if>
<div class="topnav">
  <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
  <a href="controller?command=showuser"><fmt:message bundle="${loc}" key="local.href.users" /></a>
   <a href="controller?command=showorder&position=0"><fmt:message bundle="${loc}" key="local.href.orders" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="controller?command=showcar" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="controller?command=showcar" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
</div>	
<br>
	<form action="controller" method="post">
					<input type="hidden" name="command" value="addcar" />
					<input type="hidden" name="idcar" value="${car.idCar}" />
					<input type="submit" value="Добавить новый автомобиль" />
	</form>

<br>
<div class="search">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="search" />
  		<input  type="text" name="search" placeholder="${butsearch}...">
  		<input  type="submit" value="${butsearch}"/></form>
	</form>
</div>	
<table>
	<tr>
		<th>Описание</th>
		<th>Статус</th>
		<th>Цена за сутки, руб</th>
		<c:if test="${not empty sessionScope.user}"><th>Операция</th></c:if>
	</tr>
	<c:forEach var="car" items="${cars}" >
        <tr>
        	<td><c:out value="${car.brand} ${car.model}, ${car.color}, ${car.year} г.в., ${car.gearbox}"></c:out></td>
        	<td><c:out value="${car.status}"></c:out></td> 
        	<td><c:out value="${car.price} "></c:out></td>
        	<td>
        		<form action="controller" method="post">
					<input type="hidden" name="command" value="choosecar" />
					<input type="hidden" name="idcar" value="${car.idCar}" />
					<input type="submit" value="${butorder}" />
				</form>
				<c:if test="${sessionScope.user.idRole==1}">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="removecar" />
					<input type="hidden" name="idcar" value="${car.idCar}" />
					<input type="submit" value="${butdelete}" />
				</form>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="updatecar" />
					<input type="hidden" name="idcar" value="${car.idCar}" />
					<input type="submit" value="${butedit}" />
				</form>
				
				</c:if>
			
			</td>
       </tr>
    </c:forEach>
    </table>
<input type="button" onclick="history.back();" value="${butback}"/>
</div>
</body>
</html>