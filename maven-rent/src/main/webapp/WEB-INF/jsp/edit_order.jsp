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
<h1>Редактировать заказ</h1>
<c:out value="Номер заказа: ${order.idOrder}"></c:out>
<c:out value="ID авто: ${order.idCar}"></c:out>
<c:out value="ID пользователя: ${order.idUsers}"></c:out>
<form  action="controller" method="post">
	<input type="hidden" name="command" value="editorder" />
	<input type="hidden" name="idorder" value="${order.idOrder}" />
	<input type="hidden" name="idcar" value="${order.idCar}" />
	<table style="background: rgba(135, 206, 235, 0.5)">
       <tr>
            <td align="right" >Дата начала аренды:</td>
            <td align="left"><input type="date" name="daterent" max="2019-01-01" value="${order.dateRent}" requered><br></td> 
       </tr>
       <tr> 
            <td align="right" >Дата предполагаемого возвращения:</td>
            <td align="left"> <input type="date" name="datereturn" min="2025-12-31" value="${order.dateReturn}" requered><br><br></td>
       </tr>
       <tr><td></td><td align="left"><input type="reset"><input type="submit" value="Сохранить" /></td></tr>
    </table>
    </form>
<h1>Повреждения</h1>
<form  action="controller" method="post">
	<input type="hidden" name="command" value="damage" />
	<input type="hidden" name="idcar" value="${order.idCar}" />
	<input type="hidden" name="idorder" value="${order.idOrder}" />
	<table style="background: rgba(135, 206, 235, 0.5)">
       <tr>
            <td align="right" >Описание:</td>
            <td align="left"><textarea name="description" rows="10" cols="30" value="" ></textarea></td> 
       </tr>
       <tr> 
            <td align="right" >Сумма, руб:</td>
            <td align="left"> <input type="text" name="sum" value="" requered><br><br></td>
       </tr>
       <tr><td></td><td align="left"><input type="reset"><input type="submit" value="Сохранить и Отправить счет" /></td></tr>
    </table>
    </form>
<div>
</div>
<input type="button" onclick="history.back();" value="${butback}"/>

</body>
</html>