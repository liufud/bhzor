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

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order</title>
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
			<sec:authorize access="hasAuthority('Manager')">
				  <li class="nav-item">
				    <a class="nav-link" href="customer">Customer</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="vendor">Vendor</a>
				  </li>
			</sec:authorize>
				  <li class="nav-item">
				    <a class="nav-link" href="products">Product</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link active" href="invoice">Order</a>
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
		<h2>Order</h2>Logged in as: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
			<br/>
			<sec:authorize access="hasAuthority('Customer')">	
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
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
				<c:if test="${!empty orderListBasedOnUser}">
					<c:forEach items="${orderListBasedOnUser}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.productName}</td>
							<td>${order.quantity}</td>
							<td>${order.unitPrice}</td>
							<td>${order.totalPrice}</td>
							<td>${order.createByUser}</td>
							<td>${order.created_At}</td>
							<td><a href="order/${order.orderID}/deleteOrder">Cancel</a></td>
							<td><a href="order/${order.orderID}/editOrder">Edit</a></td>						
						</tr>
					</c:forEach>
				</c:if>	
					</tbody>
				</table>
				
	<c:if test="${!empty update}">	
	<form:form modelAttribute="orderUpdate" action="order" method="post" class="form-control">
		
		<legend>${update}</legend>
		<div class="row">
			<div class="col"></div>
			<div class="col-8">
			<%-- <c:if test="${!empty update}">
				<p class="text-info">${update}</p>
			</c:if> --%>
				<spring:bind path="orderID">
		            <div class="form-group">
		            	<!-- orderID -->
		                <form:hidden  path="orderID" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="orderID"></form:errors>
		            </div>
		        </spring:bind>
		        <spring:bind path="productName">
		            <div class="form-group">
		            	<!-- Product Name: -->
		                <form:hidden path="productName" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="productName"></form:errors>
		            </div>
		        </spring:bind>
			    <c:if test="${not empty error}">
					<p class="text-warning">Error: ${error}</p>
				</c:if>	
		        <spring:bind path="quantity">
		            <div class="form-group">
		            	Quantity:
		                <form:input type="text" path="quantity" class="form-control"
		                            autofocus="true"></form:input>
		                <form:errors path="quantity"></form:errors>
		            </div>
		        </spring:bind>
		       <spring:bind path="unitPrice">
		            <div class="form-group">
		            	<!-- Unit Price: -->
		                <form:hidden path="unitPrice" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="unitPrice"></form:errors>
		            </div>
		        </spring:bind>
		       	<spring:bind path="totalPrice">
		            <div class="form-group">
		            	<!-- Total Price -->
		                <form:hidden path="totalPrice" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="totalPrice"></form:errors>
		            </div>
		        </spring:bind>
		        <spring:bind path="createByUser">
		            <div class="form-group">
		            	<!-- Created By -->
		                <form:hidden path="createByUser" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="createByUser"></form:errors>
		            </div>
		        </spring:bind>
		        <!-- <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button> -->
		         <button class="btn btn-primary" type="submit">Submit</button>
			</div>
			<div class="col"></div>		
		</div>
		</form:form>
		</c:if>
				
				
			</sec:authorize>
			<sec:authorize access="hasAuthority('Manager')">	
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
						</tr>
					</thead>					
					<tbody>
				<c:if test="${!empty orderList}">
					<c:forEach items="${orderList}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.productName}</td>
							<td>${order.quantity}</td>
							<td>${order.unitPrice}</td>
							<td>${order.totalPrice}</td>
							<td>${order.createByUser}</td>
							<td>${order.created_At}</td>													
													
						</tr>
					</c:forEach>
				</c:if>	
					</tbody>
				</table>
			</sec:authorize>
		<a href="back">Home</a><br/>		
		<a href="<c:url value="/perform_logout" />">Logout</a>	
		</div>
		<div class="col"></div>
	</div>
</div>
</body>
</html>