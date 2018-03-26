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
<title>Vendors</title>
</head>
<body class="bg-light">
<div class="container">
	<div class="row">
		<div class="col"></div>
		<div class="col-12">
		<div class="row">
			<div class="col"></div>
			<div class="col5"><h1>Inventory Tracker</h1></div>
			<div class="col"></div>
		</div>
			<ul class="nav nav-tabs">
				  <li class="nav-item">
				    <a class="nav-link" href="users">Users</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link active" href="vendor">Vendor</a>
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
		<div class="col-12">
		<h2>Vendors</h2>Logged in as: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
			<br/>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Vendor ID</th>
							<th scope="col">Vendor Name</th>
							<th scope="col">Vendor Address</th>
							<th scope="col">Created At</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
				<c:if test="${!empty vendorList}">
					<c:forEach items="${vendorList}" var="vendor">
						<tr>
							<th scope="row">${vendor.vendorID}</th>
							<td>${vendor.vendorName}</td>
							<td>${vendor.vendorAddress}</td>
							<td>${vendor.created_at}</td>						
							<td>
								<a href="vendor/${vendor.vendorID}/products">Products</a>&nbsp&nbsp
								<sec:authorize access="hasAuthority('Manager')">
								<a href="vendor/${vendor.vendorID}/updateVendor">Update</a>&nbsp&nbsp
								<a href="vendor/${vendor.vendorID}/deleteVendor">Delete</a>
								</sec:authorize>
							</td>							
						</tr>
					</c:forEach>
				</c:if>
					</tbody>
				</table>
			<c:if test="${not empty error}">
				<p class="text-warning">Error: ${error}</p>
			</c:if>	
		<h1>
			Create or Update Vendor
		</h1>		
		<form:form modelAttribute="vendor" action="vendor" method="post" class="form-control">

			
		
		<div class="row">
			<div class="col"></div>
			<div class="col-8">
			<h4>Enter Vendor Details</h4>
			<c:if test="${!empty update}">
				<p class="text-info">${update}</p>
			</c:if>
				<spring:bind path="vendorName">
		            <div class="form-group">
		            	Vendor Name:
		                <form:input type="text" path="vendorName" class="form-control"
		                            autofocus="true" ></form:input>
		                <form:errors path="vendorName"></form:errors>
		            </div>
		        </spring:bind>
		        <spring:bind path="vendorAddress">
		            <div class="form-group">
		            	Vendor Address:
		                <form:input type="text" path="vendorAddress" class="form-control"
		                            autofocus="true"></form:input>
		                <form:errors path="vendorAddress"></form:errors>
		            </div>
		        </spring:bind>
		        <spring:bind path="vendorID">
		            <div class="form-group">
		                <form:hidden path="vendorID" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="vendorID"></form:errors>
		            </div>
		        </spring:bind>
		        <div class="row">
		        	<div class="col"></div>
		        	<div class="col-5">
		        		<button class="btn btn-primary" type="submit">Submit</button>
		        		<button class="btn btn-primary" type="reset">Reset</button>
		        	</div>
		        	<div class="col"></div>
		        </div>		        
			</div>
			<div class="col"></div>		
		</div>
		</form:form>
		<a href="back">Home</a><br/>
		<a href="<c:url value="/perform_logout" />">Logout</a>	
		</div>
		<div class="col"></div>
	</div>
</div>
</body>
</html>