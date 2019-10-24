<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Index Page</title>
<link rel="stylesheet" href="ind.css" type="text/css">

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.locbutton.ru" var="ru"/>
<fmt:message bundle="${loc}" key="local.locbutton.en" var="en"/>
<fmt:message bundle="${loc}" key="local.button.signin" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.name.choosecar" var="choosecar" />

</head>
<style>
  </style>
<body>

<div class="header">
	<h1><fmt:message bundle="${loc}" key="local.message.welcome"/></h1>
</div>

<div class="topnav">
  <a href="#"><fmt:message bundle="${loc}" key="local.href.about" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.price" /></a>
  <a href="#"><fmt:message bundle="${loc}" key="local.href.offer" /></a>
  <div  class="locale">
  <form action="controller" method="post">
		<input type="hidden" name="local" value="ru_RU" />
		<input type="hidden" name="page" value="index.jsp" />
		<input  type="submit" value="${ru}"/></form>
  <form action="controller" method="post">
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="page" value="index.jsp" />
		<input  type="submit" value="${en}"/>
	</form>
	</div>
</div>	

<div class="row">
  <div class="leftcolumn">
    	<p><fmt:message bundle="${loc}" key="local.name.news" /></p> 
   </div>
   
   <div class="maincolumn">
    <div class="car"> 
    <form action="controller" method="get">
		<input type="hidden" name="command" value="showcar" />
		<input style="background-color:rgba(255, 255, 255, 0.5); border: none; color:black; width: 100%;" type="submit" value="${choosecar}" />
	</form>
    </div>
     <div class="we"><a href="#"><fmt:message bundle="${loc}" key="local.href.we"/>:) </a></div>
    </div>
    
	<div class="rightcolumn" >
		<c:if test="${not empty requestScope.error}">
            <p style="color:red;"> <fmt:message bundle="${loc}" key="${error}" /> <p>
        </c:if>
	<form action="controller" method="post">
		<input type="hidden" name="command" value="authorization" />
		<fmt:message bundle="${loc}" key="local.name.login"/><input type="text" name="login" placeholder="${login}" value="" requered /><br/>
		<fmt:message bundle="${loc}" key="local.name.password"/><input type="password" name="password" placeholder="${password}" value="" requered/><br/>
		<a style="color:black" href="reg_form"><fmt:message bundle="${loc}" key="local.button.registration"/></a>
		<input type="submit" value="${sign_in}" />
	</form>
	</div>
</div>

<div class="footer">
	<p><fmt:message bundle="${loc}" key="local.href.contacts"/></p>
</div>

</body>
</html>
