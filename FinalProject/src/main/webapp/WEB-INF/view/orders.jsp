<%@page import="ics.dao.UserDAOImpl"%>
<%@page import="ics.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous"> -->
<!-- <link href="https://getbootstrap.com/docs/4.0/examples/cover/cover.css" rel="stylesheet"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel='stylesheet' href='webjars/bootstrap/4.0.0/css/bootstrap.css'>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<style type="text/css">
#paymentOptions {display:none;}
#vendorSale {display:none;}
#distributorSale {display:none;}
#customerSale {display:none;}
</style>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->

<!-- <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> -->

<title>Orders</title>
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
							    </div> -->						    
							  </li>							
							</sec:authorize> --%>
							  <li class="nav-item">
							    <!-- <a class="nav-link active" href="orders">Orders</a> -->
							    <div class="dropdown show">
								  <a class="nav-link active" href="orders?selectOrderType=true" role="button"  aria-haspopup="true" aria-expanded="false">
								    Pedidos
								  </a>								
								  <!-- <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="#">Search Order</a>
								    <a class="dropdown-item" href="listProducts">Shopping</a>
								     <a class="dropdown-item" href="myCart">My Cart</a>
								    <a class="dropdown-item" href="#">Replace Damaged Product</a>
								  </div> -->
							    </div>
							  </li>
							  <sec:authorize access="hasAuthority('Administrador')">
							  <li class="nav-item">
							    <a class="nav-link" href="inventory?orderStatus=openOrder">Inventario</a>
							  </li>
							  </sec:authorize>							  
							  <li class="nav-item">
							    <a class="nav-link" href="siteManagement">Administración de la Pagina</a>
							    <!-- <div class="dropdown show">
								  <a class="nav-link dropdown-toggle" href="inventory" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    Site Management
								  </a>								
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="#">Add User</a>
								    <a class="dropdown-item" href="#">Edit/Remove User</a>
								    <a class="dropdown-item" href="#">View User Info</a>
								  </div>
							     </div> -->
							  </li>
						</ul>
						Conectado como: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
					<br/>
				</div>
				<div class="col"></div>
			</div>			
			
			<!-- Display Orders Content Here -->
			
			<div class="row">
				<div class="col-3">
				<br/>
					<div class="btn-group-vertical">
						<a class="btn btn-sm btn-secondary" href="orders?selectOrderType=true" role="button">Historial de Pedidos</a>	
						<a class="btn btn-sm btn-secondary" href="listProducts" role="button">Hacer Pedido</a>
						<a class="btn btn-sm btn-secondary" href="myCart" role="button">Mi Carrito de Compra</a>
						<a class="btn btn-sm btn-secondary" href="mailto:isaacrozen8@gmail.com,scrowavila@gmail.com,arturoavilabaeza@yahoo.com?
																subject=Reportar Producto Dañado&
																body=Porfavor ponga la siguiente informacion:%0D%0A
																1. Numero de Pedido %0D%0A
																2. Descripcion sobre el producto dañado %0D%0A
																3. Adjunte una foto del producto dañado %0D%0A %0D%0A
																Le remplazaremos el producto dañado pronto! %0D%0A %0D%0A
																Muchas Gracias, %0D%0A
																El equipo de BHZOR" role="button">Remplazar Producto Dañado</a>
					</div>
				</div>
				<div class="col-9">
				
				<!-- Unshipped Order and Unpaid Order information -->
				<c:if test="${not empty selectOrderType}">
				<sec:authorize access="hasAuthority('Administrador')">
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Selecciona el tipo de Pedido</th>
							<th scope="col">Accion</th>
						</tr>
					</thead>					
					<tbody>
					<form:form method="POST" action="orderType">
						<tr>
							<th scope="row">
								  <select class="custom-select custom-select-sm" name="orderTypeName">
								    <option selected>Todos los Pedidos</option>
								    <!-- <option value="Shipped Orders">Pedidos Enviados</option> -->
								    <option value="Unshipped Orders">Pedidos no Enviados</option>
								    <option value="Unpaid Orders">Pedidos no Pagados</option>
								  </select> 
							</th>
							<td>
			                	<button class="btn btn-primary" type="submit">Confirmar</button>
			                	<!-- <a href="deleteProduct">Delete</a> -->
			                </td>				
							<%-- <sec:authorize access="hasAuthority('Manager')">							
							<td><a href="user/${order.orderID}/${orderplacedby}/deleteOrder">Delete</a></td>
							</sec:authorize> --%>
						</tr>
					</form:form>					
					</tbody>
				</table>
				</sec:authorize>		
				
				<c:if test="${not empty shippedOrderForm}">
				<br/>
				<form:form name="shippedOrderForm" modelAttribute="shippedOrder" action="shippedOrder" method="post" class="form-control">
				<div class="row">
					<div class="col"></div>
					<div class="col-10">
					<h2 class="form-signin-heading"># de Pedido ${shippedOrderForm}</h2>						  
						  <div class="form-group row">
						  		<label for="productName" class="col-sm-3 col-form-label">Producto</label>
						  		<div class="col-sm-7">
							      	<form:select name="productName" class="form-control" path="shippedProductName">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${orderedProdNames}" />
					                </form:select>
							    </div>
						  </div>
						  <div class="form-group row">
							    <label for="lotID" class="col-sm-3 col-form-label"># de Lote</label>
							    <div class="col-sm-7">
							      <form:select name="lotID" class="form-control" path="lotID">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${lotIDs}" />
					               </form:select>
							      <%-- <form:input name="lotID" id="lotID" type="number" path="lotID" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="lotID"></form:errors> --%>
							    </div>
						  </div>
						  <div class="form-group row">
							    <label for="shelfID" class="col-sm-3 col-form-label"># de Estante</label>
							    <div class="col-sm-7">
							      <form:select name="shelfID" class="form-control" path="shelfID">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${shelfIDs}" />
					               </form:select>
							      <%-- <form:input name="shelfID" id="shelfID" type="number" path="shelfID" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="shelfID"></form:errors> --%>
							    </div>
						  </div>	 					  
						  <div class="form-group row">
							    <label for="qtyShipped" class="col-sm-3 col-form-label">Cantidad Enviada</label>
							    <div class="col-sm-7">
							      <form:input name="qtyShipped" id="qtyReceived" type="number" path="qtyShipped" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="qtyShipped"></form:errors>
							    </div>
						  </div>
						  <div class="form-group row">
							    <label for="trackingNum" class="col-sm-3 col-form-label">Numero de Rastreo (Opcional si no tiene, dejar en blanco)</label>
							    <div class="col-sm-7">
							      <form:input name="trackingNum" id="expDate" type="text" path="trackingNumber" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="trackingNumber"></form:errors>
							    </div>				    
						  </div>
						      <form:input name="orderID" id="orderID" type="hidden" path="orderID" class="form-control" autofocus="true" value="${orderID}"></form:input>					  

					  		  <button class="btn btn-lg btn-primary btn-block text-center" type="submit">Confirmar</button>						  
					</div>
					<div class="col"></div>
				</div>
				</form:form>
				</c:if>
				<c:if test="${!empty shipFromAnotherLot}">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title text-info">Nota:</h5>
					    <p class="card-text">¿Desea enviar el resto de la cantidad desde otro estante?</p>
					    <a href="${shipFromAnotherLot}/shippedOrder" class="card-link btn btn-primary" role="button">Si</a>
					    <a href="orders?selectOrderType=true" class="card-link btn btn-primary" role="button">No</a>
					  </div>
					</div>
				</c:if>
				
				<c:if test="${not empty viewUnshippedOrders}">
								
				<c:if test="${!empty shippedQtyError}">
					<p class="text-warning">Error: ${shippedQtyError}</p>
				</c:if>
				<c:if test="${!empty qtyOnShelfExceeded}">
					<p class="text-warning">Error: ${qtyOnShelfExceeded}</p>
				</c:if>				
				
				
				<h4><b>Pedidos no Enviados</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col"># de Pedido</th>
							<th scope="col">Fecha de Creación</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Creado Por</th>
							<th scope="col">Creado Para</th>
							<!-- <th scope="col">Total de Pedido</th> -->
							<th scope="col">Acción</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${viewUnshippedOrders}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.products}" var="product">
								<td>${product.unshippedProductqty}</td>
							</c:forEach>
							<td>
								${order.createByUser.firstName} ${order.createByUser.lastName}<br/>
								(${order.createByUser.roleName})
							</td>
							<td>
								${order.createForUser.firstName} ${order.createForUser.lastName}
								(${order.createForUser.roleName})
							</td>
							<%-- <td>${order.totalPrice}</td> --%>
							<td>								
								<a href="${order.orderID}/shippedOrder">Marcar como Enviado</a><br/>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>
				<c:if test="${!empty orderPaid}">
					<p class="text-success">${orderPaid}</p>
				</c:if>	
				<c:if test="${!empty orderShipped}">
					<p class="text-success"># de Pedido ${orderShipped} cerrado</p>
				</c:if>
				
				<c:if test="${not empty viewUnpaidOrders}">
				<c:if test="${not empty markAsPaid}">
					<form name="markAsPaidForm" action="${markAsPaid}/orderPaid" method="post" class="form-control">
						<label class="input-group-text" for="inputGroupSelect01">Que metodo de pago fue utlizado para este pedido</label>
						  <select class="custom-select" id="inputGroupSelect01" name="paymentMethod">
						   	<option selected>Choose...</option>
						    <option value="Cash">Cash</option>
						    <option value="Direct Deposit">Deposito Directo</option>
						    <option value="Credit">Credito</option>
						  </select>
						  <div class="row">
						  		<div class="col"></div>
						  		<div class="col">
						  			<button class="btn btn-lg btn-primary text-center" type="submit">Confirmar</button>
						  		</div>
						  		<div class="col"></div>
						  </div>						  
					</form>
				</c:if>
				<h4><b>Pedidos no Pagado</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col"># de Pedido</th>
							<th scope="col">Fecha de Creación</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Creado Por</th>
							<th scope="col">Creado Para</th>
							<!-- <th scope="col">Total de Pedido</th> -->
							<th scope="col">Acción</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${viewUnpaidOrders}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.products}" var="product">
								<td>${product.orderedProductQty}</td>
							</c:forEach>
							<td>
								${order.createByUser.firstName} ${order.createByUser.lastName}
								(${order.createByUser.roleName})
							</td>
							<td>
								${order.createForUser.firstName} ${order.createForUser.lastName}
								(${order.createForUser.roleName})
							</td>
							<%-- <td>${order.totalPrice}</td> --%>
							<td>								
								<a href="${order.orderID}/orderPaid">Marcar como Pagado</a><br/>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>
				<%-- <c:if test="${not empty viewShippedOrders}">
				<h4><b>Pedidos no Pagado</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col"># de Pedido</th>
							<th scope="col">Fecha de Creación</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Creado Por</th>
							<th scope="col">Creado Para</th>
							<!-- <th scope="col">Total de Pedido</th> -->
							<!-- <th scope="col">Acción</th> -->
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${viewShippedOrders}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.products}" var="product">
								<td>${product.orderedProductQty}</td>
							</c:forEach>
							<td>
								${order.createByUser.firstName} ${order.createByUser.lastName}
								(${order.createByUser.roleName})
							</td>
							<td>
								${order.createForUser.firstName} ${order.createForUser.lastName}
								(${order.createForUser.roleName})
							</td>
							<td>${order.totalPrice}</td>
							<td>								
								<a href="${order.orderID}/orderPaid">Marcar como Pagado</a><br/>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>	 --%>
				
				<c:if test="${not empty viewAllOrders}">
				<h4><b>Todos los Pedidos</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col"># de Pedido</th>
							<th scope="col">Fecha de Creación</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Creado Por</th>
							<th scope="col">Creado Para</th>
							<th scope="col">Estado de Pedido</th>
							<!-- <th scope="col">Total de Pedido</th> -->
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${viewAllOrders}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.products}" var="product">
								<td>${product.orderedProductQty}</td>
							</c:forEach>
							<td>
								${order.createByUser.firstName} ${order.createByUser.lastName}
								(${order.createByUser.roleName})
							</td>
							<td>
								${order.createForUser.firstName} ${order.createForUser.lastName}
								(${order.createByUser.roleName})
							</td>
							<td>${order.orderStatus}</td>
							<%-- <td>${order.totalPrice}</td>	 --%>						
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>
				
				<!-- My Order History -->
				<h4><b>Mi Historial de Pedidos</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col"># de Pedido</th>
							<th scope="col">Fecha de Creación</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<sec:authorize access="hasAuthority('Administrador')">
							<th scope="col">Creado Por</th>
							<th scope="col">Creado Para</th>
							</sec:authorize>
							<!-- <th scope="col">Total de Pedido</th> -->
							<!-- <th scope="col">Acción</th> -->
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${myOrders}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.products}" var="product">
								<td>${product.orderedProductQty}</td>
							</c:forEach>
							<sec:authorize access="hasAuthority('Administrador')">
							<td>
								${order.createByUser.firstName} ${order.createByUser.lastName}<br/>
								(${order.createByUser.roleName})
							</td>
							<td>
								${order.createForUser.firstName} ${order.createForUser.lastName}
								(${order.createForUser.roleName})
							</td>
							</sec:authorize>
							<%-- <td>${order.totalPrice}</td> --%>
							<%-- <td>								
								<a href="${order.orderID}/shippedOrder">Marcar como Pagado</a><br/>
							</td>	 --%>						
						</tr>
					</c:forEach>				
					</tbody>
				</table>	
				</c:if>	
				
				<!-- Product Catalog to Shopping Cart -->
				<c:if test="${not empty showList}">							
				<h4>Agregar Productos al Pedido</h4>			        
			        <table class="table table-hover table-condensed">								
					<thead>
						<tr>
							<th scope="col" style="width:25%">Nombre de Producto</th>
							<!-- <th scope="col" style="width:25%">Precio</th> -->
							<th scope="col" style="width:25%">Cantidad</th>
							<th scope="col" style="width:25%">Acción</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${listProducts}" var="product">
					<form:form method="POST" action="${product.productID}/addToCart">
						<tr>
							<th scope="row">${product.productName}</th>
							<%-- <td>${product.price}</td> --%>
							<td data-th="Quantity">
								<input type="text" name="quantity" class="form-control text-center" value="0">
							</td>
							<td>
								<button class="btn btn-primary" type="submit">Agregar al Pedido</button>
								<%-- <a href="${product.productID}/addToCart">Add to Cart</a> --%>					
							</td>							
						</tr>
					</form:form>
					</c:forEach>
					</tbody>
					<tfoot>
					<tr>
						<td colspan="2">
							<c:if test="${!empty addToCartSucceeded}">
								<p class="text-success">${addToCartSucceeded}</p>
							</c:if>
							<c:if test="${!empty shoppingCartQtyError}">
								<p class="text-warning">Error: ${shoppingCartQtyError}</p>
							</c:if>
						</td>	
						<!-- <td ></td> -->					
						<td>
							<c:if test="${!empty addToCartSucceeded}">
							<a href="myCart" class="btn btn-warning">Mi Carrito de Compra</a>
							</c:if>	
						</td>
					</tr>
					</tfoot>
					<!-- <tfoot>
						<tr>
							<td colspan="2" class="hidden-xs"></td>						
							<td><button class="btn btn-primary" type="submit">Add to Cart</button></td>
						</tr>
					</tfoot> -->
					</table>										
				</c:if>
				
				<!-- My Cart -->
				<c:if test="${not empty showMyCart}">	
				<table id="cart" class="table table-hover table-condensed">
    				<thead>
						<tr>
							<th colspan="2" style="width:50%">Producto</th>
							<!-- <th style="width:10%">Precio</th> -->
							<th style="width:8%">Cantidad</th>
							<!-- <th style="width:22%" class="text-center">Total Parcial</th> -->
							<th style="width:10%"></th>
						</tr>
					</thead>
					<tbody>
					<form:form method="POST" action="updateCart">
					<c:forEach items="${productsInCart}" var="product">
						<tr>
							<td colspan="2" data-th="Product">
								<div class="row">
									<!-- <div class="col-sm-2 hidden-xs"><img src="http://placehold.it/100x100" alt="..." class="img-responsive"/></div> -->
									<div class="col-sm-10">
										<h4 class="nomargin">${product.productName}</h4>									
									</div>
								</div>
							</td>
							<%-- <td data-th="Price">${product.price}</td> --%>
							<td data-th="Quantity">
								<input type="text" name="quantity" class="form-control text-center" value="${product.orderedProductQty}">
							</td>
							<%-- <td data-th="Subtotal" class="text-center">${product.price*product.orderedProductQty}</td> --%>
							<td class="actions" data-th="">
								<button type="submit" class="btn btn-info btn-sm"><i class="fa fa-refresh">Actualizar</i></button><br/>
								<a href="${product.productID}/deleteProductInCart" class="btn btn-danger btn-sm active" role="button" aria-pressed="true"><i class="fa fa-trash-o">&nbspEliminar&nbsp</i></a>								
							</td>
						</tr>
					</c:forEach>
					</form:form>
					</tbody>
					<tfoot>
						<!-- tr class="visible-xs">
							<td class="text-center"><strong>Total 1.99</strong></td>
						</tr> -->
						<tr>
							<td><a href="listProducts" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continua haciendo pedidos</a></td>
							<td colspan="2">
								<c:if test="${!empty shoppingCartQtyError}">
									<p class="text-warning">Error: ${shoppingCartQtyError}</p>
								</c:if>
							</td>
							<!-- <td class="hidden-xs text-center"><strong>Total $${cartTotal}</strong></td> -->
							<c:if test="${not empty productsInCart}">
							<td><a href="checkout" class="btn btn-success btn-block">Confirmar Pedido <i class="fa fa-angle-right"></i></a></td>
							</c:if>
						</tr>
					</tfoot>
				</table>				
				</c:if>
				
				<!-- Checkout -->
				<c:if test="${not empty showCheckout}">
				<h2 style="text-align: center;">
                        Revision del Pedido y Complete la Compra
                    </h2>
                    <hr/>
                    <a href="listProducts" class="btn btn-info" style="width: 100%;">Agrega Mas Productos</a>
                    <hr/>
                    <div class="shopping_cart">
                        <form:form class="form-horizontal" role="form" action="placeOrder" method="post" id="payment-form">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <!-- <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Review
                                                Your Order</a>
                                        </h4> -->
                                        <div style="text-align: center; width:100%;">
                                        <a style="width:100%;"
                                          data-toggle="collapse"
                                          data-parent="#accordion"
                                          href="#collapseOne"
                                          class="btn btn-success"
                                          onclick="$(this).fadeOut(); $('#payInfo').fadeIn();">Revisa tu Pedido»</a>
                                        </div><br/>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <div class="items">
                                                <div class="col-md-9">
                                                    <table class="table table-striped">
                                                     <thead>
														<tr>
															<th>Nombre de Producto</th>
															<!-- <th style="width:10%">Precio</th> -->
															<th>Cantidad</th>
															<!-- <th style="width:22%" class="text-center">Total Parcial</th> -->
														</tr>
													</thead>
													<tbody>
													<c:forEach items="${productsInCart}" var="product">
														<tr>
                                                            <td>
                                                                <%-- <a class="btn btn-warning btn-sm pull-right"
                                                                   href="${product.productID}/deleteProductInCheckout"
                                                                   title="Remove Item">X</a> --%>
                                                                ${product.productName}                                                             
                                                            </td>
                                                            <td style="center;">
                                                                <span style="color:green;center;">${product.orderedProductQty}</span>                                                             
                                                            </td>
                                                        </tr>
													</c:forEach>
													</tbody>                       
                                                    </table>
                                                </div>
                                                
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                            <sec:authorize access="hasAuthority('Administrador') or hasAuthority('Vendedor')">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <div style="text-align: center; width:100%;">
                                        <a style="width:100%;"
	                                      data-toggle="collapse"
	                                      data-parent="#accordion"
	                                      href="#collapseTwo"
	                                      class=" btn btn-success">
	                                      Continua a la información de Envío y Pago »</a>
	                                    </div>
                                    </h4>
                                </div>
                            </div>
                            </sec:authorize>
                            <div class="panel panel-default">
                                <!-- <div class="panel-heading">
                                     <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Existing Billing Information</a>
                                    </h4>
                                </div> -->
                                <div id="collapseTwo" class="panel-collapse collapse">
                                 <div class="panel-body">                                          
                                      <div class="input-group mb-3"> 
                                      <div class="input-group-prepend">
											<c:if test="${!empty unselectedBoxError}">
												<p class="text-warning">Error: ${unselectedBoxError}</p>
											</c:if>
                                      </div>
                                      </div>
                                     
                                      <sec:authorize access="hasAuthority('Vendedor')">                                      
                                      <div class="input-group mb-3">
									  		<div class="input-group-prepend">							  	
											  	<label class="input-group-text" for="customers">Selecciona un Cliente para este Vendedor</label>
											  	<select class="custom-select" id="customers" name="customerName">
											  		<option selected>Choose...</option>
											  		<c:forEach var="customer" items="${customerNames}">
											  			<option value="${customer.firstName} ${customer.lastName}">${customer.firstName} ${customer.lastName}</option>
											  		</c:forEach>
											  	</select>												  	
									  		</div>
									  </div>                                       
									  </sec:authorize>
									  
                                      <sec:authorize access="hasAuthority('Administrador')">
                                      <div class="input-group mb-3">  					  
									  <div class="input-group-prepend">
									    <label class="input-group-text" for="saleType_select">Selecciona el tipo de Venta</label>
										  <select class="custom-select saleType_selectbox" id="saleType_select" name="saleType">
										    <option selected>Choose...</option>
										    <option class="vendorSale" id="vendorSaleActive" onclick="myFunction1()" value="vendorSale">Venta por Vendedor</option>
										    <option class="distributorSale" id="distributorSaleActive" onclick="myFunction2()" value="distributorSale">Venta por Distribuidor</option>
										    <option class="directSale" id="directSaleActive" onclick="myFunction3()" value="directSale">Venta directa a Cliente</option>
										  </select>									  
									  </div>
									  </div>
									  <div id="vendorSale" class="sale_type">
									       <div class="input-group mb-3">
									  		  <div class="input-group-prepend">
											  	 <label class="input-group-text" for="vendor_Sale">Selecciona el Vendedor de esta Venta</label>
											  	 <select class="custom-select" id="vendor_Sale" name="_vandorSale">
											  		<option selected>Choose...</option>
											  		<c:forEach var="vandor" items="${allVendors}">
											  			<option value="${vandor.firstName} ${vandor.lastName}">${vandor.firstName} ${vandor.lastName}</option>
											  		</c:forEach>
											  	 </select>
											 </div>
									  	  </div>
									  			
										  <div class="input-group mb-3">
										  		<div class="input-group-prepend">							  	
												  	<label class="input-group-text" for="customers">Selecciona un Cliente para este Vendedor</label>
												  	<select class="custom-select" id="customers" name="_customers">
												  		<option selected>Choose...</option>
												  		<c:forEach var="customer" items="${allCustomers}">
												  			<option value="${customer.firstName} ${customer.lastName}">${customer.firstName} ${customer.lastName}</option>
												  		</c:forEach>
												  	</select>												  	
										  		</div>
										  </div>
										  <div class="input-group mb-3">
										  		<div class="input-group-prepend">
										  		<p>Si el cliente no aparece en la lista, porfavor informe al equipo de BH-ZOR con su nuevo cliente para que lo agreguen al sistema. Si no tiene cliente, deje en blanco</p>
										  		</div>
										  </div>										  
									</div>
									<div id="distributorSale" class="sale_type">
									  <div class="input-group mb-3">
									  	<div class="input-group-prepend">
										  	<label class="input-group-text" for="distributor_Sale">Selecciona al Distribuidor de esta Venta</label>
										  	<select class="custom-select" id="distributor_Sale" name="_distributorSale">
										  		<option selected>Choose...</option>
										  		<c:forEach var="distribuitor" items="${allDistributors}">
										  			<option value="${distribuitor.firstName} ${distribuitor.lastName}">${distribuitor.firstName} ${distribuitor.lastName}</option>
										  		</c:forEach>
										  	</select>
									  	</div>
									  </div>
									</div>
									  <div id="customerSale" class="sale_type">
									  	 <div class="input-group mb-3">
									  		<div class="input-group-prepend">
											  	<label class="input-group-text" for="direct_Sale">Selecciona  al Cliente de esta Venta</label>
											  	<select class="custom-select" id="direct_Sale" name="_directSale">
											  		<option selected>Choose...</option>
											  		<c:forEach var="customer" items="${allCustomers}">
											  			<option value="${customer.firstName} ${customer.lastName}">${customer.firstName} ${customer.lastName}</option>
											  		</c:forEach>
											  	</select>
									  		</div>
									  	</div>
									  </div>
									</sec:authorize>								 
                                    <%-- <div class="card" style="width: 25rem;">
									  <div class="card-body">
									    <h5 class="card-title">${user.firstName} ${user.lastName}</h5>
									    <h6 class="card-subtitle mb-2 text-muted">username: ${user.username}</h6>
									    <dl class="row">
										  <dt class="col-sm-3">Email</dt>
										  <dd class="col-sm-9">${user.email}</dd>
										  <dt class="col-sm-3">Address</dt>
										  <dd class="col-sm-9">${user.address}, ${user.city}, ${user.state} ${user.zip}</dd>
										  <dt class="col-sm-3">Phone</dt>
										  <dd class="col-sm-9">${user.phoneNumber}</dd>
										</dl>
										<a href="thisAddress" class="card-link">Use This Address</a>
									    <a href="newAddress" class="card-link">Use Another Address</a>
									  </div>
									</div> --%>
								</div>                                  
                                </div>
                            </div>
                            <%-- <c:if test="${not empty showAddressForm}"> 
                            		<h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Existing Billing Information</a>
                                    </h4> 
                                    <div>
                                        <b>Please enter the new billing information below</b>
                                        <br/><br/>
                                        <table class="table table-striped" style="font-weight: bold;">
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_email">Best Email:</label></td>
                                                <td>
                                                <spring:bind path="email">
	                                                    <form:input type="text" path="email" class="form-control"
				                            					autofocus="true"></form:input>
				                            			<form:errors path="email"></form:errors>
                                                </spring:bind>
                                                </td>
                                                
                                                <spring:bind path="email">
										            <div class="form-group">
										                <form:input type="text" path="email" class="form-control" placeholder="email@example.com"
										                            autofocus="true"></form:input>
										                <form:errors path="email"></form:errors>
										            </div>
										        </spring:bind>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_first_name">First name:</label></td>
                                                <td>
                                                <spring:bind path="firstName">
                                                    <form:input type="text" path="firstName" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="firstName"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_last_name">Last name:</label></td>
                                                <td>
                                                <spring:bind path="lastName">
                                                    <form:input type="text" path="lastName" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="lastName"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_address_line_1">Address:</label></td>
                                                <td>
                                                <spring:bind path="address">
                                                    <form:input type="text" path="address" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="address"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_address_line_2">Unit / Suite #:</label></td>
                                                <td>
                                                <spring:bind path="unit_suite">
                                                    <form:input type="text" path="unit_suite" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="unit_suite"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_city">City:</label></td>
                                                <td>
                                                <spring:bind path="city">
                                                    <form:input type="text" path="city" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="city"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_state">State:</label></td>
                                                <td>
                                                <spring:bind path="state">
								                   	<form:select id="id_state" class="form-control" path = "state">
									                   <form:option value = "NONE" label = "Select"/>
									                   <form:options items = "${stateName}" />
									                </form:select>   
								                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_postalcode">Postalcode:</label></td>
                                                <td>
                                                 <spring:bind path="postalCode">
                                                    <form:input type="text" path="postalCode" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="postalCode"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_phone">Phone:</label></td>
                                                <td>
                                                <spring:bind path="phone">
                                                    <form:input type="text" path="phone" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="phone"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>

                                        </table>
                                    </div>
                                    </c:if>
                            <c:if test="${not empty thisAddress}"> 
                            		<h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Existing Billing Information</a>
                                    </h4> 
                                    <div>
                                        <b>Please enter the new billing information below</b>
                                        <br/><br/>
                                        <table class="table table-striped" style="font-weight: bold;">
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_email">Best Email:</label></td>
                                                <td>
                                                <spring:bind path="email">
	                                                    <form:input type="text" path="email" class="form-control"
				                            					autofocus="true"></form:input>
				                            			<form:errors path="email" cssClass="error"></form:errors>
                                                </spring:bind>
                                                </td>
                                                
                                                <spring:bind path="email">
										            <div class="form-group">
										                <form:input type="text" path="email" class="form-control" placeholder="email@example.com"
										                            autofocus="true"></form:input>
										                <form:errors path="email"></form:errors>
										            </div>
										        </spring:bind>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_first_name">First name:</label></td>
                                                <td>
                                                <spring:bind path="firstName">
                                                    <form:input type="text" path="firstName" class="form-control"
			                            					autofocus="true" cssErrorClass="error"></form:input>
			                            			<form:errors path="firstName"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_last_name">Last name:</label></td>
                                                <td>
                                                <spring:bind path="lastName">
                                                    <form:input type="text" path="lastName" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="lastName"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_address_line_1">Address:</label></td>
                                                <td>
                                                <spring:bind path="address">
                                                    <form:input type="text" path="address" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="address"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_address_line_2">Unit / Suite #:</label></td>
                                                <td>
                                                <spring:bind path="unit_suite">
                                                    <form:input type="text" path="unit_suite" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="unit_suite"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_city">City:</label></td>
                                                <td>
                                                <spring:bind path="city">
                                                    <form:input type="text" path="city" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="city"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_state">State:</label></td>
                                                <td>
                                                <spring:bind path="state">
								                   	<form:select id="id_state" class="form-control" path = "state">
									                   <form:option value = "NONE" label = "Select"/>
									                   <form:options items = "${stateName}" />
									                </form:select>   
								                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_postalcode">Postalcode:</label></td>
                                                <td>
                                                 <spring:bind path="postalCode">
                                                    <form:input type="text" path="postalCode" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="postalCode"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 175px;">
                                                    <label for="id_phone">Phone:</label></td>
                                                <td>
                                                <spring:bind path="phone">
                                                    <form:input type="text" path="phone" class="form-control"
			                            					autofocus="true"></form:input>
			                            			<form:errors path="phone"></form:errors>
                                                </spring:bind>
                                                </td>
                                            </tr>

                                        </table>
                                    </div>
                                    </c:if> --%>
                            		
                            		
                          	<sec:authorize access="hasAuthority('Administrador')">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <div style="text-align: center;"><a data-toggle="collapse"
                                                                            data-parent="#accordion"
                                                                            href="#collapseThree"
                                                                            class=" btn   btn-success" id="payInfo"
                                                                            style="width:100%;display: none;" onclick="$(this).fadeOut();  
                   document.getElementById('collapseThree').scrollIntoView()">Enter Payment Information »</a>
                                        </div>
                                    </h4>
                                </div>
                            </div>
                            </sec:authorize>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                	<sec:authorize access="hasAuthority('Administrador')">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                            <b>Informacion de Pago</b>
                                        </a>
                                    </h4>
                                    </sec:authorize>
                                </div>
                                <div id="collapseThree" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <span class='payment-errors'></span>
                                        <fieldset>
                                       <sec:authorize access="hasAuthority('Administrador')">
                                            <div class="custom-control custom-checkbox">
                                            	<div class="form-check">
												  <input class="form-check-input" type="checkbox" value="" id="customCheck1">
												  <label class="form-check-label font-weight-bold" for="customCheck1">
												    Este Pedido fue pagado
												  </label>
												</div>
                                           		<!-- <label class="custom-control-label font-weight-bold" for="customCheck1">Este Pedido fue pagado</label>
												<input type="checkbox" class="custom-control-input" id="customCheck1" name="paymentStatus">	 -->										  
											</div>
                                        </sec:authorize>      
                                            <div id="paymentOptions" class="input-group mb-3">
											  <div class="input-group-prepend">
											    <label class="input-group-text" for="inputGroupSelect01">Que metodo de pago fue utlizado para este pedido</label>
												  <select class="custom-select" id="inputGroupSelect01" name="paymentMethod">
												   <!-- <option selected>Choose...</option> -->
												    <option value="Cash">Cash</option>
												    <option value="Direct Deposit">Deposito Directo</option>
												    <option value="Credit">Credito</option>
												  </select>
											  </div>										  	
											</div> 
										 
                                            
                                            <!-- <div class="form-group">
                                                <label class="col-sm-3 control-label" for="card-holder-name">Name on
                                                    Card</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" stripe-data="name"
                                                           id="name-on-card" placeholder="Card Holder's Name">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label" for="card-number">Card
                                                    Number</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" stripe-data="number"
                                                           id="card-number" placeholder="Debit/Credit Card Number">
                                                    <br/>
                                                    <div><img class="pull-right"
                                                              src="https://s3.amazonaws.com/hiresnetwork/imgs/cc.png"
                                                              style="max-width: 250px; padding-bottom: 20px;">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label" for="expiry-month">Expiration
                                                        Date</label>
                                                    <div class="col-sm-9">
                                                        <div class="row">
                                                            <div class="col-xs-3">
                                                                <select class="form-control col-sm-2"
                                                                        data-stripe="exp-month" id="card-exp-month"
                                                                        style="margin-left:5px;">
                                                                    <option>Month</option>
                                                                    <option value="01">Jan (01)</option>
                                                                    <option value="02">Feb (02)</option>
                                                                    <option value="03">Mar (03)</option>
                                                                    <option value="04">Apr (04)</option>
                                                                    <option value="05">May (05)</option>
                                                                    <option value="06">June (06)</option>
                                                                    <option value="07">July (07)</option>
                                                                    <option value="08">Aug (08)</option>
                                                                    <option value="09">Sep (09)</option>
                                                                    <option value="10">Oct (10)</option>
                                                                    <option value="11">Nov (11)</option>
                                                                    <option value="12">Dec (12)</option>
                                                                </select>
                                                            </div>
                                                            <div class="col-xs-3">
                                                                <select class="form-control" data-stripe="exp-year"
                                                                        id="card-exp-year">
                                                                    <option value="2016">2016</option>
                                                                    <option value="2017">2017</option>
                                                                    <option value="2018">2018</option>
                                                                    <option value="2019">2019</option>
                                                                    <option value="2020">2020</option>
                                                                    <option value="2021">2021</option>
                                                                    <option value="2022">2022</option>
                                                                    <option value="2023">2023</option>
                                                                    <option value="2024">2024</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label" for="cvv">Card CVC</label>
                                                    <div class="col-sm-3">
                                                        <input type="text" class="form-control" stripe-data="cvc"
                                                               id="card-cvc" placeholder="Security Code">
                                                    </div>
                                                </div> -->                                          
                                        </fieldset><br>
                                        <!-- <div style="text-align: left;"><br/>
                                            By submiting this order you are agreeing to our <a href="/legal/billing/">universal
                                                billing agreement</a>, and <a href="/legal/terms/">terms of service</a>.
                                            If you have any questions about our products or services please contact us
                                            before placing this order.
                                        </div> -->
                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-success btn-lg" style="width:100%;">Realizar Pedido
                            </button>
                        </form:form>
                    </div>
                	</c:if>
					<c:if test="${not empty orderConfirmation}">
               			<div class="alert alert-success" role="alert">
							<center>  
								<h4 class="text-success">Numero de Pedido: #${confirmedOrder.orderID}</h4>
								<h5 class="alert-heading">Pedido Confirmado!</h5>
								<h5 class="alert-heading">Gracias por realizar su pedido! El equipo de BH-ZOR los contactara brevemente con la confirmacion de su pedido y las opciones de pago</h5>
								<hr />
							</center>  
						</div>
						
						<div class="row">
					        <div class="col-xs-12">
					    		<div class="row">
					    			<div class="col-xs-6">
					        			<%-- <address>
					    				<strong>Direccion de Envio:</strong><br>
					                        ${confirmedOrder.billingInfo.firstName} ${confirmedOrder.billingInfo.lastName}<br>
					                        ${confirmedOrder.billingInfo.email}<br>
					                        ${confirmedOrder.billingInfo.phone}<br>
					    					${confirmedOrder.billingInfo.address}<br>
					    					${confirmedOrder.billingInfo.city}, ${confirmedOrder.billingInfo.state} ${confirmedOrder.billingInfo.postalCode}
					    				</address> --%>					
					    			</div>
					    			<div class="col-xs-6 text-right">
					                <h1><span class="glyphicon glyphicon glyphicon-cloud-download" aria-hidden="true"></span></h1>
					    			</div>
					    		</div>
					    	</div>
					    </div>
					    <div class="row">
					    	<div class="col-md-12">
					    		<div class="panel panel-default">
					    			<div class="panel-heading">
					    				<%-- <center><p><span class="glyphicon glyphicon glyphicon-question-sign" aria-hidden="true"></span> 
					                    All Monthly and Quarterly subscription plans renew automatically on the 15th of the month.</p> </center> --%>
					    			</div>
					    			<div class="panel-body">
					    				<div class="table-responsive">
					    					<table class="table table-condensed">
					    						<thead>
					                                <tr>
					        							<td><strong>Nombre de Producto</strong></td>
					        							<!-- <td class="text-right"><strong>Unidad de Precio de Producto</strong></td> -->
					            						<td class="text-center"><strong>Cantidad de Producto</strong></td>
					            						<!-- <td class="text-right"><strong>Precio</strong></td> -->
					                                    
					                                </tr>
					    						</thead>
					    						<tbody>
					    							<!-- foreach ($order->lineItems as $line) or some such thing here -->
					    							<c:forEach items="${confirmedOrder.products}" var="product">
					    							<tr>
					    								<td>${product.productName}</td>
					            						<%-- <td class="text-center">${product.price}</td> --%>
					            						<td class="text-center">${product.unshippedProductqty}</td>
					                                    <%-- <td class="text-right">${product.price * product.unshippedProductqty}</td> --%>
					    							</tr>
					    							</c:forEach>
					    							<!-- <tr>
					    								<td class="thick-line"></td>
					    								<td class="thick-line"></td>
					    								<td class="thick-line text-right"><strong>VAT - 12%</strong></td>
					    								<td class="thick-line text-right">incl.</td>
					    							</tr>
					    							<tr>
					    								<td class="no-line"></td>
					    								<td class="no-line"></td>
					    								<td class="no-line text-right"><strong>Costo de Envio</strong></td>
					    								<td class="no-line text-right">incl.</td>
					    							</tr>
					    							<tr>
					    								<td class="no-line"></td>
					    								<td class="no-line"></td>
					    								<td class="no-line text-right"><strong>Total</strong></td>
					    								<td class="no-line text-right"><strong>$${confirmedOrder.totalPrice}</strong></td>
					    							</tr> -->					    							
					    						</tbody>
					    					</table>
					    				</div>
						    		</div>
						    	</div>
						    </div>
						</div>
      				</c:if>
				
				</div>
				<div class="col"></div>
			</div>			
			<a href="<c:url value="/perform_logout" />">Cerrar Sesión</a>	
			<div class="col"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
$("SELECT.saleType_selectbox").change(function(){
	if($(this).val() == "vendorSale"){
		document.getElementById("vendorSale").style.display = "block";
		document.getElementById("distributorSale").style.display = "none";
		document.getElementById("customerSale").style.display = "none";			
	}
	if($(this).val() == "distributorSale"){
		document.getElementById("distributorSale").style.display = "block";
		document.getElementById("vendorSale").style.display = "none";
		document.getElementById("customerSale").style.display = "none";		
	}
	if($(this).val() == "directSale"){
		document.getElementById("customerSale").style.display = "block";
		document.getElementById("distributorSale").style.display = "none";
		document.getElementById("vendorSale").style.display = "none";
	}
		
})

/* $("SELECT.custom-select").change(
		if($(this).val() == "vendorSale")){
			function myFunction1() {
				document.getElementById("vendorSale").style.display = "block";
				document.getElementById("distributorSale").style.display = "none";
				document.getElementById("customerSale").style.display = "none";
			}			
		}
		if($(this).val() == "distributorSale")){
			function myFunction2() {
				document.getElementById("distributorSale").style.display = "block";
				document.getElementById("vendorSale").style.display = "none";
				document.getElementById("customerSale").style.display = "none";
			}			
		}
		if($(this).val() == "directSale")){
			function myFunction3() {
				document.getElementById("customerSale").style.display = "block";
				document.getElementById("distributorSale").style.display = "none";
				document.getElementById("vendorSale").style.display = "none";
			}
		}
	
) */
 
window.onload = function() {
	  var c = document.getElementById('customCheck1');
	  c.onclick = function() {
	    if (c.checked == true) {document.getElementById('paymentOptions').style.display = 'block';}
	    else {document.getElementById('paymentOptions').style.display = '';
	    }
	  }
	}
</script>
<!-- webjars/jquery/3.2.1/dist/jquery.min.js -->
<!-- <script type="text/javascript">
$(document).ready(function(){

	  //hides dropdown content
	  $(".sale_type").hide();
	  
	  //unhides first option content
	 // $("#option1").show();
	  
	  //listen to dropdown for change
	  $("#saleType_select").change(function(){
	    //rehide content on change
	    $('.sale_type').hide();
	    //unhides current item
	    $('#'+$(this).val()).show();
	  });
	  
	});
	
</script> -->


</body>
</html>