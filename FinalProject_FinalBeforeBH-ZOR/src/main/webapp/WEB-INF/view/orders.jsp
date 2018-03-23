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

<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
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
				<div class="col"><h1>Inventory Tracker</h1></div>
<%-- 				</sec:authorize>
				<sec:authorize access="hasAuthority('Customer')">
				<div class="col5"><h1>Welcome</h1></div>
				</sec:authorize> --%>
				<div class="col"></div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col-12">
					<br/>	
						<ul class="nav nav-tabs">
							<sec:authorize access="hasAuthority('Manager')">
							  <li class="nav-item">
							    <a class="nav-link" href="dashboard">Dashboard</a>
							  </li>					  				
							  <li class="nav-item">
							    <a class="nav-link" href="sales">Sales</a>
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
							</sec:authorize>
							  <li class="nav-item">
							    <!-- <a class="nav-link active" href="orders">Orders</a> -->
							    <div class="dropdown show">
								  <a class="nav-link active dropdown-toggle" href="orders?selectOrderType=true" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    Orders
								  </a>								
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="#">Search Order</a>
								    <a class="dropdown-item" href="listProducts">Shopping</a>
								     <a class="dropdown-item" href="myCart">My Cart</a>
								    <a class="dropdown-item" href="#">Replace Damaged Product</a>
								  </div>
							    </div>
							  </li>
							  <li class="nav-item">
							    <a class="nav-link" href="inventory?orderStatus=openOrder">Inventory</a>
							  </li>
							  <li class="nav-item">
							    <a class="nav-link" href="siteManagement">Site Management</a>
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
						Logged in as: <sec:authentication property="name"/> <sec:authentication property="authorities"/>
					<br/>
				</div>
				<div class="col"></div>
			</div>			
			
			<!-- Display Orders Content Here -->
			
			<div class="row">
				<div class="col-3">
				<br/>
					<div class="btn-group-vertical">
						<a class="btn btn-sm btn-secondary" href="#" role="button">Search Order</a>
						<a class="btn btn-sm btn-secondary" href="listProducts" role="button">Shopping</a>
						<a class="btn btn-sm btn-secondary" href="myCart" role="button">My Cart</a>
						<a class="btn btn-sm btn-secondary" href="#" role="button">Replace Damaged Product</a>
					</div>
				</div>
				<div class="col-9">
				
				<!-- Unshipped Order and Unpaid Order information -->
				<c:if test="${not empty selectOrderType}">
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Select Orders Types</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<form:form method="POST" action="orderType">
						<tr>
							<th scope="row">
								  <select class="custom-select custom-select-sm" name="orderTypeName">
								    <option selected>All Orders</option>
								    <option value="Unshipped Orders">Unshipped Orders</option>
								    <option value="Unpaid Orders">Unpaid Orders</option>
								  </select> 
							</th>
							<td>
			                	<button class="btn btn-primary" type="submit">Submit</button>
			                	<!-- <a href="deleteProduct">Delete</a> -->
			                </td>				
							<%-- <sec:authorize access="hasAuthority('Manager')">							
							<td><a href="user/${order.orderID}/${orderplacedby}/deleteOrder">Delete</a></td>
							</sec:authorize> --%>
						</tr>
					</form:form>					
					</tbody>
				</table>		
				
				<c:if test="${not empty viewUnshippedOrders}">
				<h4><b>Unshipped Orders</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Order #</th>
							<th scope="col">Date</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Order Total</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${viewUnshippedOrders}" var="order">
						<tr>
							<th scope="row">${order.orderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.products}" var="product">
								<td>${product.orderedProductQty}</td>
							</c:forEach>
							<td>${order.totalPrice}</td>
							<td>								
								<a href="#">Mark as Received</a><br/>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>	
				
				<c:if test="${not empty viewUnpaidOrders}">
				<h4><b>Unpaid Orders</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Order #</th>
							<th scope="col">Date</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Order Total</th>
							<th scope="col">Action</th>
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
							<td>${order.totalPrice}</td>
							<td>								
								<a href="#">Mark as Received</a><br/>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>	
				
				<c:if test="${not empty viewAllOrders}">
				<h4><b>All Orders</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Order #</th>
							<th scope="col">Date</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
							</c:forEach>
							<th scope="col">Order Total</th>
							<th scope="col">Action</th>
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
							<td>${order.totalPrice}</td>
							<td>								
								<a href="#">Mark as Received</a><br/>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>	
				</c:if>	
				
				<!-- Product Catalog to Shopping Cart -->
				<c:if test="${not empty showList}">							
				<h4>Add Products To Shopping Cart</h4>			        
			        <table class="table table-hover table-condensed">								
					<thead>
						<tr>
							<th scope="col" style="width:25%">Product Name</th>
							<th scope="col" style="width:25%">Price</th>
							<th scope="col" style="width:25%">Quantity</th>
							<th scope="col" style="width:25%">Action</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${listProducts}" var="product">
					<form:form method="POST" action="${product.productID}/addToCart">
						<tr>
							<th scope="row">${product.productName}</th>
							<td>${product.price}</td>
							<td data-th="Quantity">
								<input type="text" name="quantity" class="form-control text-center" value="0">
							</td>
							<td>
								<button class="btn btn-primary" type="submit">Add to Cart</button>
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
						<td ></td>					
						<td>
							<c:if test="${!empty addToCartSucceeded}">
							<a href="myCart" class="btn btn-warning">My Cart</a>
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
							<th style="width:50%">Product</th>
							<th style="width:10%">Price</th>
							<th style="width:8%">Quantity</th>
							<th style="width:22%" class="text-center">Subtotal</th>
							<th style="width:10%"></th>
						</tr>
					</thead>
					<tbody>
					<form:form method="POST" action="updateCart">
					<c:forEach items="${productsInCart}" var="product">
						<tr>
							<td data-th="Product">
								<div class="row">
									<!-- <div class="col-sm-2 hidden-xs"><img src="http://placehold.it/100x100" alt="..." class="img-responsive"/></div> -->
									<div class="col-sm-10">
										<h4 class="nomargin">${product.productName}</h4>
										<p>Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet.</p>
									</div>
								</div>
							</td>
							<td data-th="Price">${product.price}</td>
							<td data-th="Quantity">
								<input type="text" name="quantity" class="form-control text-center" value="${product.orderedProductQty}">
							</td>
							<td data-th="Subtotal" class="text-center">${product.price*product.orderedProductQty}</td>
							<td class="actions" data-th="">
								<button type="submit" class="btn btn-info btn-sm"><i class="fa fa-refresh">Refresh</i></button><br/>
								<a href="${product.productID}/deleteProductInCart" class="btn btn-danger btn-sm active" role="button" aria-pressed="true"><i class="fa fa-trash-o">&nbspDelete&nbsp</i></a>								
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
							<td><a href="listProducts" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
							<td colspan="2">
								<c:if test="${!empty shoppingCartQtyError}">
									<p class="text-warning">Error: ${shoppingCartQtyError}</p>
								</c:if>
							</td>
							<td class="hidden-xs text-center"><strong>Total $${cartTotal}</strong></td>
							<c:if test="${not empty productsInCart}">
							<td><a href="checkout" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
							</c:if>
						</tr>
					</tfoot>
				</table>				
				</c:if>
				
				<!-- Checkout -->
				<c:if test="${not empty showCheckout}">
				<h2 style="text-align: center;">
                        Review Your Order & Complete Checkout
                    </h2>
                    <hr/>
                    <a href="listProducts" class="btn btn-info" style="width: 100%;">Add More Products</a>
                    <hr/>
                    <div class="shopping_cart">
                        <form:form modelAttribute="billingInfo" class="form-horizontal" role="form" action="placeOrder" method="post" id="payment-form">
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
                                          onclick="$(this).fadeOut(); $('#payInfo').fadeIn();">Review
                                                Your Order»</a>
                                        </div><br/>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <div class="items">
                                                <div class="col-md-9">
                                                <c:forEach items="${productsInCart}" var="product">
                                                    <table class="table table-striped">
                                                        <tr>
                                                            <td colspan="2">
                                                                <a class="btn btn-warning btn-sm pull-right"
                                                                   href="${product.productID}/deleteProductInCheckout"
                                                                   title="Remove Item">X</a>
                                                                <b>&nbsp ${product.productName}</b>                                                              
                                                            </td>
                                                        </tr>
                                                       <!-- product description -->
                                                        <tr>
                                                            <td>
                                                                <p>Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet.</p>
                                                            </td>
                                                            <td>
                                                                <b>$${product.price*product.quantity}</b>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    </c:forEach>
                                                </div>
                                                <div class="col-md-3">
                                                    <div style="text-align: center;">
                                                        <h3>Order Total</h3>
                                                        <h3><span style="color:green;">$${cartTotal}</span></h3>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <div style="text-align: center; width:100%;">
                                        <a style="width:100%;"
	                                      data-toggle="collapse"
	                                      data-parent="#accordion"
	                                      href="#collapseTwo"
	                                      class=" btn btn-success">
	                                      Continue to Billing Information»</a>
	                                    </div>
                                    </h4>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <!-- <div class="panel-heading">
                                     <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Existing Billing Information</a>
                                    </h4>
                                </div> -->
                                <div id="collapseTwo" class="panel-collapse collapse">
                                 <div class="panel-body">  
                                    <div class="card" style="width: 25rem;">
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
									</div>
								</div>                                  
                                </div>
                            </div>
                            <c:if test="${not empty showAddressForm}"> 
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
                                                
                                                <%-- <spring:bind path="email">
										            <div class="form-group">
										                <form:input type="text" path="email" class="form-control" placeholder="email@example.com"
										                            autofocus="true"></form:input>
										                <form:errors path="email"></form:errors>
										            </div>
										        </spring:bind> --%>
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
									                   <%-- <form:option value = "NONE" label = "Select"/> --%>
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
                                                
                                                <%-- <spring:bind path="email">
										            <div class="form-group">
										                <form:input type="text" path="email" class="form-control" placeholder="email@example.com"
										                            autofocus="true"></form:input>
										                <form:errors path="email"></form:errors>
										            </div>
										        </spring:bind> --%>
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
									                   <%-- <form:option value = "NONE" label = "Select"/> --%>
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
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                            <b>Payment Information</b>
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseThree" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <span class='payment-errors'></span>
                                        <fieldset>
                                            <div class="custom-control custom-checkbox">
											  <input type="checkbox" class="custom-control-input" id="customCheck1" name="paymentStatus">
											  <label class="custom-control-label font-weight-bold" for="customCheck1">This order has been paid</label>
											</div>
                                            <legend>If this order has been paid, which method was it paid for?</legend>
                                            <div class="input-group mb-3">
											  <div class="input-group-prepend">
											    <label class="input-group-text" for="inputGroupSelect01">Options</label>
											  </div>
											  <select class="custom-select" id="inputGroupSelect01" name="paymentMethod">
											    <option selected>Choose...</option>
											    <option value="Cash">Cash</option>
											    <option value="Direct Deposit">Direct Deposit</option>
											    <option value="Credit">Credit</option>
											  </select>
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
                                                <div class="form-group">
                                                    <div class="col-sm-offset-3 col-sm-9">
                                                    </div>
                                                </div>
                                        </fieldset>
                                        <button type="submit" class="btn btn-success btn-lg" style="width:100%;">Place Order
                                        </button>
                                        <br/>
                                        <div style="text-align: left;"><br/>
                                            By submiting this order you are agreeing to our <a href="/legal/billing/">universal
                                                billing agreement</a>, and <a href="/legal/terms/">terms of service</a>.
                                            If you have any questions about our products or services please contact us
                                            before placing this order.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                	</c:if>
					<c:if test="${not empty orderConfirmation}">
               			<div class="alert alert-success" role="alert">
							<center>  
								<h4 class="alert-heading">Success - your order is confirmed!</h4>
								<h5 class="text-success">Order number: #${confirmedOrder.orderID}</h5>
								<hr />
							</center>  
						</div>
						
						<div class="row">
					        <div class="col-xs-12">
					    		<div class="row">
					    			<div class="col-xs-6">
					        			<address>
					    				<strong>Shipping Address:</strong><br>
					                        ${confirmedOrder.billingInfo.firstName} ${confirmedOrder.billingInfo.lastName}<br>
					                        ${confirmedOrder.billingInfo.email}<br>
					                        ${confirmedOrder.billingInfo.phone}<br>
					    					${confirmedOrder.billingInfo.address}  ${confirmedOrder.billingInfo.unit_suite}<br>
					    					${confirmedOrder.billingInfo.city}, ${confirmedOrder.billingInfo.state} ${confirmedOrder.billingInfo.postalCode}
					    				</address>					
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
					    				<center><p><span class="glyphicon glyphicon glyphicon-question-sign" aria-hidden="true"></span> 
					                    All Monthly and Quarterly subscription plans renew automatically on the 15th of the month.</p> </center>
					    			</div>
					    			<div class="panel-body">
					    				<div class="table-responsive">
					    					<table class="table table-condensed">
					    						<thead>
					                                <tr>
					        							<td><strong>Product Name</strong></td>
					        							<td class="text-right"><strong>Product Unit Price</strong></td>
					            						<td class="text-right"><strong>Product Quantity</strong></td>
					            						<td class="text-right"><strong>Price</strong></td>
					                                    
					                                </tr>
					    						</thead>
					    						<tbody>
					    							<!-- foreach ($order->lineItems as $line) or some such thing here -->
					    							<c:forEach items="${confirmedOrder.products}" var="product">
					    							<tr>
					    								<td>${product.productName}</td>
					            						<td class="text-center">${product.cost}</td>
					            						<td class="text-center">${product.orderedProductQty}</td>
					                                    <td class="text-right">${product.cost * product.orderedProductQty}</td>
					    							</tr>
					    							</c:forEach>
					    							<tr>
					    								<td class="thick-line"></td>
					    								<td class="thick-line"></td>
					    								<td class="thick-line text-right"><strong>VAT - 12%</strong></td>
					    								<td class="thick-line text-right">incl.</td>
					    							</tr>
					    							<tr>
					    								<td class="no-line"></td>
					    								<td class="no-line"></td>
					    								<td class="no-line text-right"><strong>Shipping</strong></td>
					    								<td class="no-line text-right">incl.</td>
					    							</tr>
					    							<tr>
					    								<td class="no-line"></td>
					    								<td class="no-line"></td>
					    								<td class="no-line text-right"><strong>Total</strong></td>
					    								<td class="no-line text-right"><strong>$${confirmedOrder.totalPrice}</strong></td>
					    							</tr>					    							
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
			<a href="<c:url value="/perform_logout" />">Logout</a>	
			<div class="col"></div>
		</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>