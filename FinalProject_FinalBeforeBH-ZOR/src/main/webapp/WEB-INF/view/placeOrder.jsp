<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
		<div class="col-8">
			<sec:authorize access="hasAuthority('Customer')">
			<h1>
				Place Order
			</h1>
			<form:form modelAttribute="product" action="placeorder" method="post" class="form-control">
			
			<h4>Enter the amount you would like to purchase</h4>
			<table class="table">
			<tr>
				<th scope="col">Product ID</th>
				<th scope="col">Product Name</th>
				<th scope="col">Inventory Level</th>
				<sec:authorize access="hasAuthority('Manager')">
				<th scope="col">Cost from Vendor</th>
				</sec:authorize>
				<th scope="col">Cost for Sale</th>
			</tr>
			<tbody>
				<tr>
					<th scope="row">${product.productID}</th>
					<td>${product.productName}</td>
					<td>${product.inventoryLevel}</td>
					<sec:authorize access="hasAuthority('Manager')">
					<td>${product.costFromManufacturer}</td>
					</sec:authorize>
					<td>${product.costForSale}</td>														
				</tr>
			</tbody>
		</table>				
			<div class="row">
				<div class="col"></div>
				<div class="col-8">
				<spring:bind path="productID">
		            <div class="form-group">
		            	<!-- Product ID: -->
		                <form:hidden path="productID" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="productID"></form:errors>
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
		        <spring:bind path="inventoryLevel">
		            <div class="form-group">
		            	Quantity:
		                <form:input type="text" path="inventoryLevel" class="form-control"
		                            autofocus="true"></form:input>
		                <form:errors path="inventoryLevel"></form:errors>
		            </div>
		        </spring:bind>
		        <spring:bind path="costFromManufacturer">
		            <div class="form-group">
		            	<!-- Cost from Vendor: -->
		                <form:hidden path="costFromManufacturer" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="costFromManufacturer"></form:errors>
		            </div>
		        </spring:bind>
		        <spring:bind path="costForSale">
		            <div class="form-group">
		            	<!-- Cost for Sale: -->
		                <form:hidden path="costForSale" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="costForSale"></form:errors>
		            </div>
		        </spring:bind>
		            <button type="submit" class="btn btn-primary">Place Order</button>		        
				</div>
				<div class="col"></div>		
			</div>
			</form:form>
			</sec:authorize>
			
			<a href="back">Back</a><br/>
		<a href="<c:url value="/perform_logout" />">Logout</a>	
		</div>
		<div class="col"></div>
	</div>
</div>
	
	
</body>
</html>