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
		<input type="hidden" name="page" value="controller?command=userdata" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="controller?command=userdata" />
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


	
	<form  action="controller" method="post">
	<input type="hidden" name="command" value="updateuser" />
	<input type="hidden" name="iduser" value="${user.idUser}" />
	<table style="background: rgba(135, 206, 235, 0.5)">
        <tr>
        	<td align="right">Имя</td>
        	<td align="left"><input type="text" name="name" value="${user.name}" required></td> 
       </tr>
       <tr>
        	<td align="right">Фамилия</td>
        	<td align="left"><input type="text" name="surname" value="${user.surname}" required></td> 
       </tr>
       <tr>
            <td align="right" >Номер телефона </td>
            <td align="left"><input type="tel" name="phone" value="${user.phone}" pattern="[0-9]{4}-[0-9]{3}-[0-9]{2}-[0-9]{2}" required/>
			<span>Format: 8029-123-45-67</span></td>
       </tr>
       <tr> 
            <td align="right" >Адрес электронной почты </td>
            <td align="left"><input type="email" name="mail" value="${user.mail}" required></td>
       </tr>
       <tr> 
            <td align="right" >Адрес</td>
            <td align="left"><input type="text" name="address" value="${user.address}" required></td>
       </tr>
       <tr>
            <td align="right" >Паспорт</td>
            <td align="left"><input type="text" name="passport" value="${user.passport}" pattern="[A-Z]{2}[0-9]{7}" required/>
	        <span>Format: AA1234567</span></td>
       </tr>
       <tr> 
            <td align="right" >Водительское удостоверение </td>
            <td align="left"><input type="text" name="driverlicense" value="${user.driverLicense}" pattern="[0-9]{1}[A-Z]{2}[0-9]{6}" required/>
	        <span>Format: 0AA123456</span></td>
       </tr>
       <tr><td></td><td align="left"><input type="submit" value="Редактировать" /></td></tr>
    </table>
    </form>
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
	<input type="hidden" name="command" value="orderdata" />
	<input type="hidden" name="iduser" value="${user.idUser}" />
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