<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body class="bg-light">
<div class="container">
	<div class="row">
		<div class="col">
		</div>
		<div class="col-5">
			<br/><br/><br/><br/><br/>
			<form:form method="POST" modelAttribute="userForm" class="form-signin">
		        <h2 class="form-signin-heading">Create your account</h2>
		        <spring:bind path="username">
		            <div class="form-group">
		                <form:input type="text" path="username" class="form-control" placeholder="username*"
		                            autofocus="true"></form:input>
		                <form:errors path="username"></form:errors>
		            </div>
		        </spring:bind>
		        
		        <spring:bind path="password">
		            <div class="form-group">
		                <form:input type="password" path="password" class="form-control" placeholder="password*"></form:input>
		                <form:errors path="password"></form:errors>
		            </div>
		        </spring:bind>		        
		        
		        <spring:bind path="email">
		            <div class="form-group">
		                <form:input type="text" path="email" class="form-control" placeholder="email@example.com*"
		                            autofocus="true"></form:input>
		                <form:errors path="email"></form:errors>
		            </div>
		        </spring:bind>
		        
				<spring:bind path="phoneNumber">
		            <div class="form-group">
		                <form:input type="text" path="phoneNumber" class="form-control" placeholder="Phone Number"
		                            autofocus="true"></form:input>
		                <form:errors path="phoneNumber"></form:errors>
		            </div>
		        </spring:bind>
		        
		        <spring:bind path="shippingAddress">
		            <div class="form-group">
		                <form:input type="text" path="shippingAddress" class="form-control" placeholder="Shipping Address*"
		                            autofocus="true"></form:input>
		                <form:errors path="shippingAddress"></form:errors>
		            </div>
		        </spring:bind>
		
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
		    </form:form>
		    <br/><a href="login">Go Back</a>
		</div>
		<div class="col">
		</div>
	</div>
</div>
</body>
</html>