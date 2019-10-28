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
<fmt:message bundle="${loc}" key="button.edit" var="butedit"/>
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
  <a href="controller?command=showorder&position=0&count=3"><fmt:message bundle="${loc}" key="local.href.orders" /></a>
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
</div class="maincolumn">		
<form  action="controller" method="post">
	<input type="hidden" name="command"  value="registration"/>
	<table style="background: rgba(135, 206, 235, 0.5)">
	<tr> 
     <td align="right">Имя</td>
     <td><input type="text" name="name" value="" required></td>
    </tr>
    <tr> 
     <td align="right">Фамилия</td>
     <td><input type="text" name="surname" value="" maxlength="50" size="20"></td>
    </tr>
    <tr> 
     <td align="right" >Логин </td>
     <td><input type="text" name="login" value=""
     <c:if test="${not empty requestScope.error}">
     	<p><fmt:message bundle="${loc}" key="${error}" /></p>
     	</c:if>></td>
     
    </tr>
    <tr> 
     <td align="right" >Пароль </td>
     <td><input type="password" name="password" value=""></td>
    </tr>
    <tr> 
     <td align="right" >Номер телефона </td>
     <td><input type="tel" name="phone" value="" pattern="[0-9]{4}-[0-9]{3}-[0-9]{2}-[0-9]{2}" required/>
	<span>Format: 8029-123-45-67</span></td>
    </tr>
    <tr> 
     <td align="right" >Адрес электронной почты </td>
     <td><input type="email" name="mail" value=""></td>
    </tr>
    <tr> 
    <tr> 
     <td align="right" >Адрес</td>
     <td><input type="text" name="address" value=""></td>
    </tr>
    <tr> 
     <td></td>
     <td><input type="reset"><input type="submit" value="Сохранить"></td>
    </tr>
   </table>
	
	</form>
	</div>
	
<div>
</div>

<input type="button" onclick="history.back();" value="${butback}"/>

</body>
</html>