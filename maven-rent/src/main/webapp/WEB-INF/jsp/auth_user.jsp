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

	<c:if test="${not empty sessionScope.user}">
		<h1>Добро пожаловать, </h1>
		<h2> <c:out value="${sessionScope.user.name} ${sessionScope.user.surname}" ></c:out></h2>
	</c:if>
	
    <c:if test="${empty sessionScope.user}">
		<h1>Чтобы заказать авто - <a href="index.jsp">авторизуйтесь</a>, пожалуйста </h1>
	</c:if>

	
 <div class="topnav">
 <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.about" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.price" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.offer" /></a>
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
<div class="search">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="search" />
  		<input  type="text" name="search" placeholder="Search...">
  		<input  type="submit" value="Поиск"/></form>
	</form>
</div>	
<c:if test="${sessionScope.user.idRole!=1}">
<div class="leftcolumn">
<h2>Фильтр</h2>
Марка:<br>
<form action="controller" method="post">
<input type="hidden" name="command" value="filter" />
<input type="checkbox" name="volkswagen" value="Volkswagen">Volkswagen
<br>
<input type="checkbox" name="audi" value="Audi">Audi
<br>
<input type="checkbox" name="bmw" value="BMW">BMW
<br>
<input type="checkbox" name="skoda" value="Skoda">Skoda
<br>
<input type="checkbox" name="toyota" value="Toyota">Toyota
<br><br>
КПП:<br>
  <input type="radio" name="gearbox" value="АКПП" > АКПП<br>
  <input type="radio" name="gearbox" value="МКПП"> МКПП<br><br>
  <input type="submit" value="Найти">
</form> 
</div>
</c:if>

	<div class="maincolumn">
	<div class="message">
	<c:if test="${not empty sessionScope.order}">
		<c:out value="Номер Вашего заказа: ${order.idOrder}"></c:out>  <br>
		<c:out value="Количество дней аренды: ${order.days}"></c:out>  <br>
		<c:out value="Итого: ${order.total}"></c:out>  <br>
		<p>Cпасибо за Ваш заказ!</p>
 	</c:if>
 <c:remove var = "order"/>
 	<c:if test="${not empty sessionScope.messageNotFound}">
		<c:out value="${messageNotFound}"></c:out>  <br>
 	</c:if>
 <c:remove var = "messageNotFound"/>
</div>
	<table style="background: rgba(135, 206, 235, 0.5); text-align: left;">
	<c:forEach var="car" items="${cars}" >
        <tr>
        	<td colspan="2"><c:out value="${car.brand} ${car.model}, ${car.color}, ${car.year} г.в., ${car.gearbox}"></c:out><br></td>
        	<td></td>
        </tr>
        <tr>
        	<td><b>Статус: </b><c:out value="${car.status}"></c:out></td> 
        	<td><b>Цена за сутки: </b><c:out value=" ${car.price} "></c:out></td>
        	<td>
        	<c:if test="${not empty sessionScope.user}">
        		<form action="controller" method="post">
					<input type="hidden" name="command" value="choosecar" />
					<input type="hidden" name="idcar" value="${car.idCar}" />
					<input type="submit" value="Заказать" />
				</form>
			</c:if>
			</td>
       </tr>
       <tr><td><br></td><td><br></td><td><br></td></tr>
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
    
    <input style="padding-left: 7px; border: none; background-color: #ADD8E6; cursor: pointer;" type="submit" value="Личные данные" />
    </form>
     <form action="controller" method="post">
	<input type="hidden" name="command" value="orderdata" />
	
    <input style="padding-left: 7px; border: none; background-color: #ADD8E6; cursor: pointer;" type="submit" value="Информация о заказах" />
	</form>
    </div>
    </c:if>
    </c:if>
   

<div class="maincolumn">
	<input type="button" onclick="history.back();" value="Назад"/>
	</div>
</body>
</html>