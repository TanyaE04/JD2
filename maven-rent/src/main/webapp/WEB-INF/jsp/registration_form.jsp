<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.locbutton.ru" var="ru"/>
<fmt:message bundle="${loc}" key="local.locbutton.en" var="en"/>
</head>
<style>

   body {
    background-image: url(http://starmoz.com/images/bmw-m3-autumn-1.jpg);
    background-color: #c7b39b; 
    background-size: cover;
    }
    
    
.topnav {
  overflow: hidden;
  background-color: #6A5ACD;
}

.topnav a {
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

 .locale input[type=submit] {
  background-color: #c0c0c0;
  color: black;
  padding: 2px 2px;
  border: none;
  cursor: pointer;
  float:right;
  text-align: center;
  margin-top:10px;
  margin-right:10px
  }

  
  </style>
<body>
<div class="topnav">
 <a href="index.jsp"><fmt:message bundle="${loc}" key="local.href.tomain"/></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.about" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.price" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.offer" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="reg_form" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="reg_form" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
	
	
</div>	

	<h1 style="color:white">Форма регистрации</h1><br>
	<form action="controller" method="post">
	<input type="hidden" name="command"  value="registration"/>
	<table style="background-color: rgba(255, 99, 71, 0.7);">
	<tr> 
     <td align="right" width="100">Имя</td>
     <td><input type="text" name="name" value="" maxlength="50" size="20"></td>
    </tr>
    <tr> 
     <td align="right">Фамилия</td>
     <td><input type="text" name="surname" value="" maxlength="50" size="20"></td>
    </tr>
    <tr> 
     <td align="right" >Логин </td>
     <td><input type="text" name="login" value=""></td>
     <td>
     	<c:if test="${not empty requestScope.errorLoginExist}">
     	<p><c:out value="${requestScope.errorLoginExist}"/></p>
     	</c:if>
     </td>
    </tr>
    <tr> 
     <td align="right" >Пароль </td>
     <td><input type="password" name="password" value=""></td>
    </tr>
    <tr> 
     <td align="right" >Номер телефона </td>
     <td><input type="tel" name="phone" value="" pattern="[0-9]{4}-[0-9]{3}-[0-9]{2}-[0-9]{2}" required/>
	<span>Format: 8029-123-45-67</span>></td>
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
     <td><input type="reset"><input type="submit" value="Зарегистрироваться"></td>
    </tr>
   </table>
	
	</form>
	
	
</body>
</html>