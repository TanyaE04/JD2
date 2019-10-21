<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="auth.css" type="text/css">
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.locbutton.ru" var="ru"/>
<fmt:message bundle="${loc}" key="local.locbutton.en" var="en"/>
</head>

<body>
	
 <div class="topnav">
 <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.about" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.price" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.offer" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="controller?command=orderdata" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="controller?command=orderdata" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
	
	
</div>
<div class="search">
<form>
  <input  type="text" name="search" placeholder="Search..">
	</form>
</div>	
<c:if test="${sessionScope.user.idRole!=1}">
<div class="leftcolumn">
<h2>Фильтр</h2>
Марка:<br>
<form action="">
<input type="checkbox" name="volkswagen" value="Volkswagen">Volkswagen
<br>
<input type="checkbox" name="audi" value="Audi">Audi
<br>
<input type="checkbox" name="bwm" value="BWM">BWM
<br>
<input type="checkbox" name="skoda" value="Skoda">Skoda
<br>
<input type="checkbox" name="toyota" value="Toyota">Toyota
<br><br>
КПП:<br>
  <input type="radio" name="akk" value="male" > АКПП<br>
  <input type="radio" name="mkk" value="female"> МКПП<br><br>
  <input type="submit" value="Найти">
</form> 
</div>
</c:if>

	<div class="maincolumn">
	
	<table style="background: rgba(135, 206, 235, 0.5)">
        <tr>
        	<th>Номер заказа</th>
        	<th>Марка машины</th>
        	<th>Дата аренды</th>
        	<th>Дата возвращения</th>
        	<th>Сумма</th>
        	<c:if test="${not empty order.damage}">
        	<th>Повреждения</th>
        	</c:if>
        	
       </tr>
       <c:forEach var="order" items="${orders}" >
       <tr>
        	<td><c:out value="${order.idOrder}"></c:out></td> 
        	<td><c:out value="${order.markCar}"></c:out></td> 
        	<td><c:out value="${order.dateRent}"></c:out></td> 
        	<td><c:out value="${order.dateReturn}"></c:out></td> 
        	<td><c:out value="${order.total}"></c:out></td> 
        	<c:if test="${not empty order.damage}">
        	<td><c:out value="${order.damage}"></c:out><br>
        	<c:out value="${order.damageSum} руб"></c:out></td> 
        	</c:if>
        	
       </tr>
       </c:forEach>
       
    </table>
    </div>
    
    <c:if test="${not empty sessionScope.user}">
    	<form action="controller" method="post">
					<input type="hidden" name="command" value="logout" />
					
					<input style="border-radius: 12px; border: 2px solid #f44336;float:right; margin-top:10px;"type="submit" value="Выйти" />
		</form>
    <c:if test="${sessionScope.user.idRole!=1}">
    
    <div class="rightcolumn">	
	<h2 style="padding-left: 7px;">Личный кабинет</h2>
	<form action="controller" method="post">
    <input type="hidden" name="command" value="userdata" />
    <input type="hidden" name="iduser" value="${user.idUser}" />
    <input style="padding-left: 7px; border: none; background-color: #ADD8E6; cursor: pointer;" type="submit" value="Личные данные" />
    </form>
    </div>
    </c:if>
    </c:if>
   

<div class="maincolumn">
	<input type="button" onclick="history.back();" value="Назад"/>
	</div>
</body>
</html>