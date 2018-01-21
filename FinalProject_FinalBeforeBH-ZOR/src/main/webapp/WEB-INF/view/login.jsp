<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<!-- <link href="https://getbootstrap.com/docs/4.0/examples/cover/cover.css" rel="stylesheet"> -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Login</title>
</head>
<body class="bg-light">
 <div class="container">
   	<div class="row">
   		<div class="col">
   		</div>
   		<div class="col-5">
			   <br/><br/><br/><br/><br/><br/><br/><br/>
			   <form action="perform_login" method='POST' class="form-signin">
			   <h1 class="form-signin-heading">Login</h1>
			      	<font color="red">
						<span>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
					</font>
		         <div class="form-group">
			            <input type='text' name='username' placeholder="username" autofocus="true" class="form-control">
			     </div>
			     <div class="form-group">			
			            <input type='password' name='password' placeholder="password" autofocus="true" class="form-control"/>
				 </div>
			            <input type="submit" value="Login" autofocus="true" class="btn btn-primary"/>
			  </form>
			  <br/><a href="register">register</a>
    	</div>
    	<div class="col">
    	</div>
    </div>
 </div>  
</body>
</html>