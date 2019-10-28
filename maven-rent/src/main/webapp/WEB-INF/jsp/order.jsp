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
    	<form action="controller" method="post">
					<input type="hidden" name="command" value="logout" />
					
					<input style="border-radius: 12px; border: 2px solid #f44336;float:right; margin-top:10px;"type="submit" value="Выйти" />
		</form></c:if>
<div class="topnav">
 <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.about" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.price" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.offer" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="controller?command=choosecar&idcar=${infocar.idCar}" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="controller?command=choosecar&idcar=${infocar.idCar}" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
</div>	
<c:if test="${empty requestScope.order}">
<p> Информация о авто:<br>
<c:out value="${infocar.brand} ${infocar.model}, ${infocar.color}, ${infocar.year} г.в., ${infocar.gearbox} "></c:out>
</p>
    <b style="color: red;"><c:if test="${not empty requestScope.message}">
		<fmt:message bundle="${loc}" key="${message}" /> <br>
 	</c:if></b>
  <c:remove var = "messageAlreadyRent"/>
<form name="orderForm" onsubmit="return myFunction()" action="controller" method="post">
<input type="hidden" name="command" value="ordercar" />
<input type="hidden" name="idcar" value="${infocar.idCar}" />
<input type="hidden" name="iduser" value="${sessionScope.user.idUser}" />
Дата начала аренды:<br>
<input type="date" name="daterent" min="2019-01-01" value="" placeholder="YYYY-MM-DD" requered><br>
Дата предполагаемого возвращения:<br>
<input type="date" name="datereturn" max="2025-12-31" requered><br><br>
			<c:if test="${empty sessionScope.user.passport}">
			Введите серию и номер паспорта<input type="text" name="passport" placeholder="AA1234567" value="" requered><br>
			Введите серию и номер водительских прав<input type="text" name="driverlicense" placeholder="0AA123456" value="" requered><br>
			</c:if>
<input type="reset">
<input type="submit" value="Оформить аренду"> 
</form>
<input type="button" onclick="history.back();" value="Назад"/>
</c:if>

<script>
function myFunction() {
 
  var dateRent = document.forms["orderForm"]["daterent"].value;
  var dateReturn = document.forms["orderForm"]["datereturn"].value;
  var now = new Date ();
  var datenow = new Date (now.getFullYear () + '-' + (now.getMonth () + 1) + '-' + now.getDate ());
  var rent = new Date (dateRent);
  var dreturn = new Date (dateReturn);
  if ((rent<now) && (dreturn<=now)){
	  alert ("Не верно выбраны даты")
	  return false;
  } 
   
}
</script>
</body>
</html>