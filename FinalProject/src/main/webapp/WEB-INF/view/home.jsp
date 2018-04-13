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
<!-- <link href="https://getbootstrap.com/docs/4.0/examples/cover/cover.css" rel="stylesheet"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body class="bg-light">

	<div class="container">
		<div class="row">
			<div class="col"></div>
			<div class="col-12">
			<div class="row">
				<div class="col"><img src="img/BH-ZOR_logo.png" height="70"></div>
				<%-- <sec:authorize access="hasAuthority('Manager')"> --%>
				<div class="col-7"><h1>BH-ZOR: Base de Datos</h1></div>
<%-- 				</sec:authorize>
				<sec:authorize access="hasAuthority('Customer')">
				<div class="col5"><h1>Welcome</h1></div>
				</sec:authorize> --%>
				<div class="col-1"></div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col-12">
					<br/>	
						<ul class="nav nav-tabs">
							<sec:authorize access="hasAuthority('Administrador')">
							 <!--  <li class="nav-item">
							    <a class="nav-link" href="dashboard">Panel de Control</a>
							  </li>					  				
							  <li class="nav-item">
							    <a class="nav-link" href="sales">Ventas</a>
							  </li> -->
							</sec:authorize>
							  <li class="nav-item">
							    <a class="nav-link" href="orders?selectOrderType=true">Pedidos</a>
							  </li>
							  <sec:authorize access="hasAuthority('Administrador')">
							  <li class="nav-item">
							    <a class="nav-link" href="inventory?orderStatus=openOrder">Inventario</a>
							  </li>
							  </sec:authorize>
							  <li class="nav-item">
							    <a class="nav-link" href="siteManagement">Administración de la Pagina</a>
							  </li>
						</ul>
						Conectado como: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
					<br/>
				</div>
				<div class="col"></div>
			</div>			
		  <main role="main">
	        <div class="jumbotron">
	          <div class="col-sm-8 mx-auto text-dark">
	            <h1 class="mx-auto">Bienvenido!</h1>
	            <sec:authorize access="hasAuthority('Administrador')">
	           		<p>Esta aplicación web es un control de inventario multi-usuario. No solo ayuda a administradores en manejar el inventario y hacer pedidos de producto, también permite a diferentes usuarios (clientes, vendedores o distribuidores) en hacer pedidos para diferentes productos. </p>	           
	            </sec:authorize>
	            <%-- <sec:authorize access="hasAnyRole('Cliente','Vendedor','Distribuidor')"> --%>
	            <sec:authorize access="hasAuthority('Distribuidor') or hasAuthority('Vendedor') or hasAuthority('Cliente')">
	            	<p>Use esta pagina Web para hacer todos sus pediods con BH-ZOR!</p>
	            </sec:authorize>
	            <p>
	              <a class="btn btn-primary" href="orders?selectOrderType=true" role="button">Entrar &raquo;</a>
	            </p>
	            
	            <%-- <sec:authorize access="hasAuthority('Customer')">
	            <p>
	              <a class="btn btn-primary" href="products" role="button">Get Started &raquo;</a>
	            </p>
	            </sec:authorize>
	            <sec:authorize access="hasAuthority('Manager')">
	            <p>
	              <a class="btn btn-primary" href="users" role="button">Get Started &raquo;</a>
	            </p>
	            </sec:authorize> --%>
	          </div>
	        </div>
	      </main>
			
			<a href="<c:url value="/perform_logout" />">Cerrar Sesión</a>	
			</div>	
			<div class="col"></div>
		</div>
	</div>
</body>
</html>