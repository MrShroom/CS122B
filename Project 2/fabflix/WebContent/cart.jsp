<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search for a Movie</title>
<%@ include file="/header.jsp" %>
</head>
<body>
<center><h3>Cart</h3></center>
<table>
<tr>
<th>Movie Title</th>
<th>Price</th>
<th>Qty</th>
</tr>
<%
	Cart shopping_cart = (Cart)session.getAttribute("shopping_cart");
	HashMap<Integer, CartItem> basket = shopping_cart.getBasket();
%>
<% for(CartItem item: basket.values()){%>
<tr>
<td><%=item.getMovieTitle()%></td>
<td><%=item.getPrice()%></td>
<td>
<form action="/cartServlet" name="cartOperation">
<input type="hidden" name="movie_id" value="<%=item.getMovieID()%>"></input>
<input type="text" value="<%=item.getQty()%>" name="qty"></input>
<input type="submit" value="Update" name="update">
<input type="submit" value="Remove" name="Remove">
</form>
</td>
</tr>
<%} %>
</table>


</body>


<%@ include file="/footer.jsp" %>
</html>