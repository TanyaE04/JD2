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
<fmt:message bundle="${loc}" key="button.logout" var="butlogout"/>
<fmt:message bundle="${loc}" key="button.back" var="butback"/>
</head>


<body>

<div class="header">
		<h1><fmt:message bundle="${loc}" key="welcome.message.welcome"/>,</h1>
		<h2> <c:out value="Админ ${sessionScope.user.name} ${sessionScope.user.surname}" ></c:out></h2>
	
</div>
 <c:if test="${not empty sessionScope.user}">
    	<form action="controller" method="post">
					<input type="hidden" name="command" value="logout" />
					
					<input style="border-radius: 12px; border: 2px solid #f44336;float:right; margin-top:10px;"type="submit" value="${butlogout}" />
		</form></c:if>
<div class="topnav">
 <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
 
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="admin" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="admin" />
		<input  type="submit" value="${en}"/>
	</form>
</div>
</div>

<div class="leftcolumn">
<div class="menu">
    	<form action="controller" method="get">
		<input type="hidden" name="command" value="showuser" />
		<input type="submit" value="Пользователи" />
	</form>
	</div>
</div>
   
 <div class="maincolumn">
 <div class="menu">
    <form action="controller" method="get">
		<input type="hidden" name="command" value="showcar" />
		<input type="submit" value="Автомобили" />
	</form>
	</div>
 </div>
   
<div class="rightcolumn" >
<div class="menu">
	<form action="controller" method="get">
		<input type="hidden" name="command" value="showorder" />
		<input type="hidden" name="position" value="0" />
		<input type="submit" value="Заказы" />
	</form>
	</div>
</div>

<input type="button" onclick="history.back();" value="${butback}"/>

</body>
</html>