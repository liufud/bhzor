<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<!-- <link href="https://getbootstrap.com/docs/4.0/examples/cover/cover.css" rel="stylesheet"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customers</title>
</head>
<body class="bg-light">
<div class="container">
	<div class="row">
		<div class="col"></div>
		<div class="col-10">
		<div class="row">
				<div class="col"></div>
				<div class="col5"><h1>Inventory Tracker</h1></div>
				<div class="col"></div>
			</div>
			<ul class="nav nav-tabs">
				  <li class="nav-item">
				    <a class="nav-link active" href="customer">Customer</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="vendor">Vendor</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="products">Product</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="invoice">Order</a>
				  </li>
			</ul>
		</div>
		<div class="col"></div>
	</div>
</div>
<div class="container">
	<div class="row">
		<div class="col"></div>
		<div class="col-10">
		<h2>Customers</h2>Logged in as: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
			<br/>
			<c:if test="${!empty orderList}">
			<h4>Order placed by ${orderplacedby}</h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Order ID</th>
							<th scope="col">Product</th>
							<th scope="col">Order Quantity</th>
							<th scope="col">Unit Price</th>
							<th scope="col">Total Price</th>
							<th scope="col">Customer</th>
							<th scope="col">Date</th>
							<!-- <th scope="col">Action</th> -->
						</tr>
					</thead>					
					<tbody>
				
					<c:forEach items="${orderList}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.productName}</td>
							<td>${order.quantity}</td>
							<td>${order.unitPrice}</td>
							<td>${order.totalPrice}</td>
							<td>${order.createByUser}</td>
							<td>${order.created_At}</td>							
							<%-- <sec:authorize access="hasAuthority('Manager')">							
							<td><a href="customer/${order.orderID}/${orderplacedby}/deleteOrder">Delete</a></td>
							</sec:authorize> --%>
						</tr>
					</c:forEach>
					
					</tbody>
				</table>
			</c:if>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Customer ID</th>
							<th scope="col">Username</th>
							<th scope="col">Email</th>
							<th scope="col">Phone Number</th>
							<th scope="col">Shipping Address</th>
							<th scope="col">Created At</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
				<c:if test="${!empty customerList}">
					<c:forEach items="${customerList}" var="customer">
						<tr>
							<th scope="row">${customer.userId}</th>
							<td>${customer.username}</td>
							<td>${customer.email}</td>
							<td>${customer.phoneNumber}</td>
							<td>${customer.shippingAddress}</td>
							<td>${customer.created_at}</td>
							<td><a href="customer/${customer.username}/viewOrder">View Order</a></td>							
							<sec:authorize access="hasAuthority('Manager')">							
							<td><a href="customer/${customer.userId}/deleteCustomer">Delete</a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</c:if>
					</tbody>
				</table>
			<c:if test="${not empty error}">
				<p class="text-warning">Error: ${error}</p>
			</c:if>
			<c:if test="${not empty userHasOrder}">
				<p class="text-warning">Error: ${userHasOrder}</p>
			</c:if>	
		<a href="back">Home</a><br/>		
		<a href="<c:url value="/perform_logout" />">Logout</a>	
		</div>
		<div class="col"></div>
	</div>
</div>
</body>
</html>