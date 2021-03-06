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
<fmt:message bundle="${loc}" key="button.search" var="butsearch"/>
<fmt:message bundle="${loc}" key="button.order" var="butorder"/>
<fmt:message bundle="${loc}" key="button.find" var="butfind"/>
<fmt:message bundle="${loc}" key="button.logout" var="butlogout"/>
<fmt:message bundle="${loc}" key="button.back" var="butback"/>
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
	<form action="controller" method="post">
		<input type="hidden" name="command" value="search" />
  		<input  type="text" name="search" placeholder="${butsearch}...">
  		<input  type="submit" value="${butsearch}"/></form>
	</form>
</div>	
<c:if test="${sessionScope.user.idRole!=1}">
<div class="leftcolumn">
<h2><fmt:message bundle="${loc}" key="page.filter"/></h2>
<fmt:message bundle="${loc}" key="page.brand"/>:<br>
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
<fmt:message bundle="${loc}" key="page.gearbox" />:<br>
  <input type="radio" name="gearbox" value="АКПП" ><fmt:message bundle="${loc}" key="page.ak" /><br>
  <input type="radio" name="gearbox" value="МКПП"><fmt:message bundle="${loc}" key="page.mk" /><br><br>
  <input type="submit" value="${butfind}">
</form> 
</div>
</c:if>

	<div class="maincolumn">
<div class="message">
 	<c:if test="${not empty sessionScope.message}">
		<fmt:message bundle="${loc}" key="${message}" /> <br>
 	</c:if>
 <c:remove var = "message"/>
</div>
	<table style="background: rgba(135, 206, 235, 0.5)">
        <tr>
        	<th>Номер заказа</th>
        	<th>Марка машины</th>
        	<th>Дата аренды</th>
        	<th>Дата возвращения</th>
        	<th>Сумма</th>
        	<th>Повреждения</th>
       	
       </tr>
       <c:forEach var="order" items="${orders}" >
       <c:if test="${order.status!='deleted'}">
       <tr>
        	<td><c:out value="${order.idOrder}"></c:out></td> 
        	<td><c:out value="${order.markCar}"></c:out></td> 
        	<td><c:out value="${order.dateRent}"></c:out></td> 
        	<td><c:out value="${order.dateReturn}"></c:out></td> 
        	<td><c:out value="${order.total}"></c:out>
        		<c:if test="${order.idRefusal==2}">
        		<p>Выбранное Вами авто в настоящее время находится в ремонте</p>
        		</c:if>
        	</td> 
        	<c:if test="${not empty order.damage}">
        	<td><c:out value="${order.damage}"></c:out><br>
        	<c:out value="${order.damageSum} руб"></c:out></td> 
        	</c:if>
			<c:if test="${order.status!='done'}">
        	<td>
        		<form action="controller" method="post">
					<input type="hidden" name="command" value="deleteorder" />
					<input type="hidden" name="page" value="controller?command=orderdata" />
					<input type="hidden" name="idorder" value="${order.idOrder}" />
					<input type="submit" value="Отменить заказ" />
				</form>
			</td>
			</c:if>
       </tr>
       </c:if>
       </c:forEach>
      
    </table>
    <table></table>
    <c:if test="${sessionScope.user.debt > 0}">
        <span style="color:red;">Итого сумма задолженности: <c:out value="${sessionScope.user.debt}"></c:out></span>
        		<form action="controller" method="post">
					<input type="hidden" name="command" value="pay" />
					<input type="hidden" name="iduser" value="${user.idUser}" />
					<input type="submit" value="Pay debts" />
				</form>
			
			</c:if>
    </div>
    
    <c:if test="${not empty sessionScope.user}">
    	<form action="controller" method="post">
					<input type="hidden" name="command" value="logout" />
					
					<input style="border-radius: 12px; border: 2px solid #f44336;float:right; margin-top:10px;"type="submit" value="${butlogout}" />
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
	<input type="button" onclick="history.back();" value="${butback}"/>
	</div>
</body>
</html>