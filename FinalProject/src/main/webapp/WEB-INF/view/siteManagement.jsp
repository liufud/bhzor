<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous"> -->
<!-- <link href="https://getbootstrap.com/docs/4.0/examples/cover/cover.css" rel="stylesheet"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel='stylesheet' href='webjars/bootstrap/4.0.0/css/bootstrap.css'>

<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<title>Administración de la Pagina</title>
<style>
#cust {
display:none;
}
</style>
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
							<%-- <sec:authorize access="hasAuthority('Manager')">
							  <li class="nav-item">
							    <a class="nav-link" href="dashboard">Panel de Control</a>
							  </li>					  				
							  <li class="nav-item">
							   <a class="nav-link" href="sales">Ventas</a>
							    <!-- <div class="dropdown show">
								  <a class="nav-link dropdown-toggle" href="sales" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    Sales
								  </a>								
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="#">Forecasts</a>
								    <a class="dropdown-item" href="#">Vendor Sales</a>
								    <a class="dropdown-item" href="#">Distributor Sales</a>
								    <a class="dropdown-item" href="#">Direct Customer Sales</a>
								  </div>
							    </div>	 -->					    
							  </li>							
							</sec:authorize> --%>
							  <li class="nav-item">
							    <a class="nav-link" href="orders?selectOrderType=true">Pedidos</a>
							    <!-- <div class="dropdown show">
								  <a class="nav-link dropdown-toggle" href="orders" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    Orders
								  </a>								
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="#">Place Order</a>
								    <a class="dropdown-item" href="#">Cancel Order</a>
								    <a class="dropdown-item" href="#">Edit Order</a>
								    <a class="dropdown-item" href="#">Replace Damaged Product</a>
								  </div>
							    </div> -->
							  </li>
							  <li class="nav-item">
							    <a class="nav-link" href="inventory?orderStatus=openOrder">Inventario</a>
							    <!-- <div class="dropdown show">
								  <a class="nav-link dropdown-toggle" href="inventory" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    inventory
								  </a>								
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="#">Add Product</a>
								    <a class="dropdown-item" href="#">Edit/Return Product</a>
								    <a class="dropdown-item" href="#">Historical Inventory</a>
								  </div>
							     </div> -->	
							  </li>
							  <li class="nav-item">
							    <!-- <a class="nav-link" href="siteManagement">Site Management</a> -->
							    <div class="dropdown show">
								  <a class="nav-link active" href="inventory" role="button" id="dropdownMenuLink" aria-haspopup="true" aria-expanded="false">
								    Administración de la Pagina
								  </a>								
								  <!-- <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="allUsers">All Users</a>
								    <a class="dropdown-item" href="addUser">Add User</a>
								    <a class="dropdown-item" href="report/?type=xls">Export Data</a>
								  </div> -->
							     </div>
							  </li>
						</ul>
						Conectado como: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
					<br/>
				</div>
				<div class="col"></div>
			</div>						
			
			<!-- Display Orders Content Here -->
			
			<div class="row">
				<div class="col-2">
				<br/>
					<div class="btn-group-vertical">
						<a class="btn btn-sm btn-secondary" href="allUsers" role="button">Todos los Usuarios</a>
						<a class="btn btn-sm btn-secondary" href="addUser" role="button">Agregar Usuario</a>
						<a class="btn btn-sm btn-secondary" href="report/?type=xls" role="button">Exportar Datos</a>
					</div>
				</div>
				<div class="col-10 small">
				<!-- Add User -->
				<c:if test="${!empty addUser}">
				<form:form method="POST" modelAttribute="userForm" class="form-signin" action="siteManagement">
		        <h2 class="form-signin-heading">Crea tu cuenta</h2>
			        <spring:bind path="firstName">
			            <div class="form-group">
			                <form:input type="text" path="firstName" class="form-control" placeholder="Nombre"
			                            autofocus="true"></form:input>
			                <form:errors path="firstName"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="lastName">
			            <div class="form-group">
			                <form:input type="text" path="lastName" class="form-control" placeholder="Apellido"
			                            autofocus="true"></form:input>
			                <form:errors path="lastName"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="username">
			            <div class="form-group">
			                <form:input type="text" path="username" class="form-control" placeholder="Nombre de Usuario"
			                            autofocus="true"></form:input>
			                <form:errors path="username"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="password">
			            <div class="form-group">
			                <form:input type="password" path="password" class="form-control" placeholder="Contraseña"></form:input>
			                <form:errors path="password"></form:errors>
			            </div>
			        </spring:bind>	
			        
			        <c:if test="${not empty passwordError}">
						<p class="text-warning">Error: ${passwordError}</p>
					</c:if>
			        <spring:bind path="passwordConfirm">
			            <div class="form-group">
			                <form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirma Contraseña"></form:input>
			                <form:errors path="passwordConfirm"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <%-- <spring:bind path="roleName">
			            <div class="form-group" name="form" onclick="check()">
			            <label>Select a role for this user&nbsp</label>
			                <form:select id="role" path = "roleName">
					                   <form:option value = "NONE" label = "Select"/>
					                   <form:options  value="${roleList}" items = "${roleList}" />
					        </form:select>
			                <form:errors path="roleName"></form:errors>
			            </div>
			        </spring:bind> --%>
			        
			        
			        <label>Selecciona el rol para el usuario&nbsp</label>
		            <select id="role" name="roleList">
		            	<option selected>Choose...</option>
						<option onclick="check()" value="Cliente">Cliente</option>
						<option onclick="check2()" value="Vendedor">Vendedor</option>
						<option onclick="check2()" value="Distribuidor">Distribuidor</option>
						<option onclick="check2()" value="Administrador">Administrador</option>
				  	</select>	
				  	
			        
			        <div id="cust">
			        <label>Selecciona a un Vendedor para este cliente&nbsp</label>
		            <select id="vendorName" name="_vandorName">
				  		<option selected>Choose...</option>
				  		<c:forEach var="vendor" items="${allVendorsName}">
				  			<option value="${vendor}">${vendor}</option>
				  		</c:forEach>
				  	</select>	
				  	</div>        
			        
			        <spring:bind path="email">
			            <div class="form-group">
			                <form:input type="email" path="email" class="form-control" placeholder="email@example.com"
			                            autofocus="true"></form:input>
			                <form:errors path="email"></form:errors>
			            </div>
			        </spring:bind>
			        
					<spring:bind path="phoneNumber">
			            <div class="form-group">
			                <form:input type="text" path="phoneNumber" class="form-control" placeholder="Teléfono"
			                            autofocus="true"></form:input>
			                <form:errors path="phoneNumber"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="address">
			            <div class="form-group">
			                <form:input type="text" path="address" class="form-control" placeholder="Dirección"
			                            autofocus="true"></form:input>
			                <form:errors path="address"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="city">
			            <div class="form-group">
			                <form:input type="text" path="city" class="form-control" placeholder="Ciudad"
			                            autofocus="true"></form:input>
			                <form:errors path="city"></form:errors>
			            </div>
			        </spring:bind>
			        
                  	<spring:bind path="state">
	                  	<div class="form-group">
		                   	<form:select id="id_state" class="form-control" path = "state">
			                   <form:option value = "NONE" label = "Estado"/>
			                   <form:options items = "${stateName}" />
			                </form:select>
			            </div>   
	                </spring:bind>
			        
			     <%--    <spring:bind path="state">
			            <div class="form-group">
			                <form:input type="text" path="state" class="form-control" placeholder="State"
			                            autofocus="true"></form:input>
			                <form:errors path="state"></form:errors>
			            </div>
			        </spring:bind> --%>
			        
			        <spring:bind path="zip">
			            <div class="form-group">
			                <form:input type="text" path="zip" class="form-control" placeholder="Código Postal"
			                            autofocus="true"></form:input>
			                <form:errors path="zip"></form:errors>
			            </div>
			        </spring:bind>
			
			        <button class="btn btn-lg btn-primary btn-block" type="submit">Confirmar</button>
			    </form:form>
			    <br/>		    
			    	<a href="addUser/backTOsiteManagement">Regresa</a>
			    </c:if>
			    
			    <!-- Update User Info -->
				<c:if test="${!empty userInfoToBeUpdated.userId}">
				<form:form method="POST" modelAttribute="userInfoToBeUpdated" class="form-signin" action="updateUserInfo">
		        <h2 class="form-signin-heading">Actualizar Información del Usuario</h2>
			        <spring:bind path="firstName">
			            <div class="form-group">
			                <form:input type="text" path="firstName" class="form-control" placeholder="Nombre"
			                            autofocus="true"></form:input>
			                <form:errors path="firstName"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="lastName">
			            <div class="form-group">
			                <form:input type="text" path="lastName" class="form-control" placeholder="Apellido"
			                            autofocus="true"></form:input>
			                <form:errors path="lastName"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="username">
			            <div class="form-group">
			                <form:input type="text" path="username" class="form-control" placeholder="Nombre de Usuario"
			                            autofocus="true"></form:input>
			                <form:errors path="username"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="password">
			            <div class="form-group">
			                <form:input type="password" path="password" class="form-control" placeholder="Contraseña"></form:input>
			                <form:errors path="password"></form:errors>
			            </div>
			        </spring:bind>	
			        
			        <c:if test="${not empty passwordError}">
						<p class="text-warning">Error: ${passwordError}</p>
					</c:if>
			        <spring:bind path="passwordConfirm">
			            <div class="form-group">
			                <form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirma Contraseña"></form:input>
			                <form:errors path="passwordConfirm"></form:errors>
			            </div>
			        </spring:bind>	
			        
			        <%-- <spring:bind path="roleName">
			            <div class="form-group">
			            <label>Select a role for this user&nbsp</label>
			                <form:select path = "roleName">
					                   <form:option value = "NONE" label = "Select"/>
					                   <form:options items = "${roleList}" />
					        </form:select>
			                <form:errors path="roleName"></form:errors>
			            </div>
			        </spring:bind>  --%>       
			        
			        <spring:bind path="email">
			            <div class="form-group">
			                <form:input type="text" path="email" class="form-control" placeholder="email@example.com"
			                            autofocus="true"></form:input>
			                <form:errors path="email"></form:errors>
			            </div>
			        </spring:bind>
			        
					<spring:bind path="phoneNumber">
			            <div class="form-group">
			                <form:input type="text" path="phoneNumber" class="form-control" placeholder="Teléfono"
			                            autofocus="true"></form:input>
			                <form:errors path="phoneNumber"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="address">
			            <div class="form-group">
			                <form:input type="text" path="address" class="form-control" placeholder="Dirección"
			                            autofocus="true"></form:input>
			                <form:errors path="address"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="city">
			            <div class="form-group">
			                <form:input type="text" path="city" class="form-control" placeholder="Ciudad"
			                            autofocus="true"></form:input>
			                <form:errors path="city"></form:errors>
			            </div>
			        </spring:bind>
			        
			        
			        <spring:bind path="state">
	                  	<div class="form-group">
		                   	<form:select id="id_state" class="form-control" path = "state">
			                   <form:option value = "NONE" label = "Estado"/>
			                   <form:options items = "${stateName}" />
			                </form:select>
			            </div>   
	                </spring:bind>
			        <%-- <spring:bind path="state">
			            <div class="form-group">
			                <form:input type="text" path="state" class="form-control" placeholder="Estado"
			                            autofocus="true"></form:input>
			                <form:errors path="state"></form:errors>
			            </div>
			        </spring:bind> --%>
			        
			        <spring:bind path="zip">
			            <div class="form-group">
			                <form:input type="text" path="zip" class="form-control" placeholder="Código Postal"
			                            autofocus="true"></form:input>
			                <form:errors path="zip"></form:errors>
			            </div>
			        </spring:bind>
			        
			        <spring:bind path="userId">
		            <div class="form-group">
		                <form:hidden path="userId" class="form-control"
		                            autofocus="true"></form:hidden>
		                <form:errors path="userId"></form:errors>
		            </div>
		        	</spring:bind>
			
			        <button class="btn btn-lg btn-primary btn-block" type="submit">Confirmar</button>
			    </form:form>
			    <br/>		    
			    	<a href="addUser/backTOallUsers">Regresa</a>
			    </c:if>
			    
			    <c:if test="${not empty userUpdateSucceeded}">
					<div class="alert alert-success" role="alert">
					  <h4 class="alert-heading">Muy Bien!</h4>
					  <p class="text-success">${userUpdateSucceeded}</p>
					</div>
				</c:if>
				
				<!-- Show All Users Based on Role -->
				
				<c:if test="${not empty viewUserByRole}">
					<table class="table">								
					<thead>
						<tr>
							<th scope="col">Selecciona tipo de usuario</th>
							<th scope="col">Acción</th>
						</tr>
					</thead>					
					<tbody>
					<form:form method="POST" modelAttribute="role" action="showUsersByRole">
						<tr>
							<th scope="row">
								<spring:bind path="roleName">
				                   	<form:select path = "roleName">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${roleList}" />
					                </form:select>   
				                </spring:bind> 
							</th>
							<td>
			                	<button class="btn btn-primary" type="submit">Mostrar</button>
			                </td>				
							<%-- <sec:authorize access="hasAuthority('Manager')">							
							<td><a href="user/${order.orderID}/${orderplacedby}/deleteOrder">Delete</a></td>
							</sec:authorize> --%>
						</tr>
					</form:form>					
					</tbody>
				</table>			
				</c:if>
				
				<c:if test="${!empty checkUpdateRole}">
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">User</th>
							<th scope="col">Current Role</th>
							<th scope="col">Change To</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<form:form method="POST" modelAttribute="roleTobeUpdated" action="user/${userTobeUpdated.userId}/updateRole">
						<tr>
							<th scope="row">${userTobeUpdated.username}</th>
							<td>${userTobeUpdated.roleName}</td>	
							<td>
							<spring:bind path="roleName">
			                   <form:select path = "roleName">
			                      <%-- <form:option value = "NONE" label = "Select"/> --%>
			                      <form:options items = "${roleList}" />
			                   </form:select>   
			                </spring:bind>  	
			                </td>
			                <td>
			                	<button class="btn btn-primary" type="submit">Submit</button>
			                </td>				
							<%-- <sec:authorize access="hasAuthority('Manager')">							
							<td><a href="user/${order.orderID}/${orderplacedby}/deleteOrder">Delete</a></td>
							</sec:authorize> --%>
						</tr>
					</form:form>					
					</tbody>
				</table>
				</c:if>
				
				<c:if test="${not empty updateRoleSucceeded}">
					<div class="alert alert-success" role="alert">
					  <h4 class="alert-heading">Muy Bien!</h4>
					  <p class="text-success">${updateRoleSucceeded}</p>
					</div>
				</c:if>
				
				<c:if test="${not empty allUsersByRole}">
					<table class="table">								
					<thead>
						<tr>
							<th scope="col">User ID</th>
							<th scope="col">Name</th>
							<th scope="col">Email</th>
							<th scope="col">Phone Number</th>
							<th scope="col">Shipping Address</th>
							<th scope="col">Created At</th>
							<th scope="col">Role</th>
							<th scope="col">Vendor</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${allUsersByRole}" var="user">
						<tr>
							<th scope="row">${user.userId}</th>
							<td>${user.firstName} ${user.lastName}</td>
							<td>${user.email}</td>
							<td>${user.phoneNumber}</td>
							<td>${user.address}, ${user.city}, ${user.state} ${user.zip}</td>
							<td>${user.created_at}</td>
							<td>${user.roleName}</td>
							<td>${user.vendor.firstName} ${user.vendor.lastName}</td>
							<td>
								<a href="user/${user.userId}/updateRole">Update Role</a><br/>
								<sec:authorize access="hasAuthority('Manager')">
								<a href="user/${user.userId}/updateInfo">Update Info</a><br/>							
								<a href="user/${user.userId}/deleteUser">Delete</a>
								</sec:authorize>					
							</td>							
						</tr>
					</c:forEach>
				</table>
				</c:if>
				
				<!-- All Users -->
				<c:if test="${not empty allUsers}">	
					<table class="table">								
					<thead>
						<tr>
							<th scope="col">User ID</th>
							<th scope="col">Name</th>
							<th scope="col">Email</th>
							<th scope="col">Phone Number</th>
							<th scope="col">Shipping Address</th>
							<!-- <th scope="col">Created At</th> -->
							<th scope="col">Role</th>
							<th scope="col">Vendor</th>
							<th scope="col">Action</th>
						</tr>
					</thead>									
					<tbody>
					<c:forEach items="${allUsers}" var="user">
						<tr>
							<th scope="row">${user.userId}</th>
							<td>${user.firstName} ${user.lastName}</td>
							<td>${user.email}</td>
							<td>${user.phoneNumber}</td>
							<td>${user.address}, ${user.city}, ${user.state} ${user.zip}</td>
							<%-- <td>${user.created_at}</td> --%>
							<td>${user.roleName}</td>
							<td>${user.vendor.firstName} ${user.vendor.lastName}</td>
							<td>
								<a href="user/${user.userId}/updateRole">Update Role</a><br/>
								<sec:authorize access="hasAuthority('Manager')">
								<a href="user/${user.userId}/updateInfo">Update Info</a><br/>							
								<a href="user/${user.userId}/deleteUser">Delete</a>
								</sec:authorize>						
							</td>							
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<tag:paginate max="3" offset="${offset}" count="${count}"
   				uri="siteManagement" next="&raquo;" previous="&laquo;" />
				</c:if>

				
				
				
				<!-- Add User -->
				<c:if test="${not empty addSucceeded}">
					<div class="alert alert-success" role="alert">
					  <h4 class="alert-heading">Muy Bien!</h4>
					  <p class="text-success">${addSucceeded}</p>
					</div>
				</c:if>
				
				<!-- View User Info -->				
				<c:if test="${!empty userList}">				
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">User</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<form:form method="POST" modelAttribute="user" action="viewUserInfo">
						<tr>
							<th scope="row">
								<spring:bind path="username">
				                   	<form:select path = "username">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${userList}" />
					                </form:select>   
				                </spring:bind> 
							</th>
							<td>
			                	<button class="btn btn-primary" type="submit">View Info</button>
			                </td>				
							<%-- <sec:authorize access="hasAuthority('Manager')">							
							<td><a href="user/${order.orderID}/${orderplacedby}/deleteOrder">Delete</a></td>
							</sec:authorize> --%>
						</tr>
					</form:form>					
					</tbody>
				</table>				
				</c:if>
				<c:if test="${not empty check}">
					<table class="table">								
						<thead>
							<tr>
								<th scope="col">User ID</th>
								<th scope="col">Username</th>
								<th scope="col">Email</th>
								<th scope="col">Phone Number</th>
								<th scope="col">Shipping Address</th>
								<th scope="col">Created At</th>
								<th scope="col">Role</th>
								<th scope="col">Action</th>
							</tr>
						</thead>					
						<tbody>
							<tr>
								<th scope="row">${userSelected.userId}</th>
								<td>${userSelected.username}</td>
								<td>${userSelected.email}</td>
								<td>${userSelected.phoneNumber}</td>
								<td>${userSelected.address}</td>
								<td>${userSelected.created_at}</td>
								<td>${userSelected.roleName}</td>
								<td>
									<a href="customer/${userSelected.username}/viewOrder">View Order</a><br/>
									<sec:authorize access="hasAuthority('Manager')">							
									<a href="user/${userSelected.userId}/deleteCustomer">Delete</a>
									</sec:authorize>						
								</td>							
							</tr>
					 	</tbody>
					</table>
				</c:if>	
				
				<!-- List all Users -->
				
				<%-- <table class="table">								
					<thead>
						<tr>
							<th scope="col">User ID</th>
							<th scope="col">Username</th>
							<th scope="col">Email</th>
							<th scope="col">Phone Number</th>
							<th scope="col">Shipping Address</th>
							<th scope="col">Created At</th>
							<th scope="col">Role</th>
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
							<td>${customer.roleName}</td>
							<td>
								<a href="customer/${customer.username}/viewOrder">View Order</a><br/>
								<sec:authorize access="hasAuthority('Manager')">							
								<a href="user/${customer.userId}/deleteCustomer">Delete</a>
								</sec:authorize>						
							</td>							
						</tr>
					</c:forEach>
				</c:if>
					</tbody>
				</table> --%>
				
				</div>
				<div class="col"></div>
			</div>
			
			<a href="<c:url value="/perform_logout" />">Cerrar Sesión</a>	
			</div>	
			<div class="col"></div>
		</div>
	</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<script>
function check() {
	document.getElementById("cust").style.display="block";
}
function check2() {
		document.getElementById("cust").style.display="none";
}
</script>
</body>
</html>