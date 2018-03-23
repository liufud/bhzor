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
<!-- <script src="//webjars/1.17.0/dist/jquery.validate.min.js" type="text/javascript"></script>
<script src="//webjars/1.17.0/dist/jquery.validate.js" type="text/javascript"></script> -->
<!-- <script src="webjars/1.17.0/src/localization/messages_es.js" type="text/javascript"></script> -->
<!-- <script src="js/form-validation.js"></script> -->
<title>Inventory</title>
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
							    <a class="nav-link" href="orders?selectOrderType=true">Orders</a>
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
							    <!-- <a class="nav-link" href="inventory">Inventory</a> -->
							    <div class="dropdown show">
								  <a class="nav-link active dropdown-toggle" href="inventory?orderStatus=openOrder" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    Inventory
								  </a>								
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="addProduct">Add Product</a>
								    <a class="dropdown-item" href="editProduct">View Inventory</a>
								    <a class="dropdown-item" href="orderReplenishment">Order Replenishment</a>
								    <a class="dropdown-item" href="#">Historical Inventory</a>
								  </div>
							     </div>	
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
			
			<div class="row">
				<div class="col-2">
				<br/>
					<div class="btn-group-vertical">
						<a class="btn btn-sm btn-secondary" href="addProduct" role="button">Add Product</a>
						<a class="btn btn-sm btn-secondary" href="editProduct" role="button">View Inventory</a>
						<a class="btn btn-sm btn-secondary" href="orderReplenishment" role="button">Order Replenishment</a>
						<a class="btn btn-sm btn-secondary" href="#" role="button">Historical Inventory</a>
					</div>
				</div>
				<div class="col-10">
				
				<!-- Received Rp Order Form -->				
				<c:if test="${not empty rpOrderReceivedForm}">
				<br/>
				<form:form name="receivedOrderForm" modelAttribute="receivedRpOrder" action="receivedRpOrder" method="post" class="form-control">
				<div class="row">
					<div class="col"></div>
					<div class="col-10">
						  <div class="form-group row">
							    <label for="lotID" class="col-sm-3 col-form-label">Lot ID</label>
							    <div class="col-sm-7">
							      <form:input name="lotID" id="lotID" type="number" path="lotID" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="lotID"></form:errors>
							    </div>
						  </div>
						  <div class="form-group row">
						  		<label for="productName" class="col-sm-3 col-form-label">Product</label>
						  		<div class="col-sm-7">
							      	<form:select name="productName" class="form-control" path="receivedRpProductName">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${rpProdNames}" />
					                </form:select>
							    </div>
						  </div>	 					  
						  <div class="form-group row">
							    <label for="qtyReceived" class="col-sm-3 col-form-label">Quantity Received</label>
							    <div class="col-sm-7">
							      <form:input name="qtyReceived" id="qtyReceived" type="number" path="quantityReceived" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="quantityReceived"></form:errors>
							    </div>
						  </div>
						  <div class="form-group row">
							    <label for="qtyRejected" class="col-sm-3 col-form-label">Quantity Rejected</label>
							    <div class="col-sm-7">
							      <form:input name="qtyRejected" id="qtyRejected" type="number" path="quantityRejected" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="quantityRejected"></form:errors>
							    </div>
						  </div>
						  <div class="form-group row">
							    <label for="expDate" class="col-sm-3 col-form-label">Expiration Date</label>
							    <div class="col-sm-7">
							      <form:input name="expDate" id="expDate" type="text" path="expDate" class="form-control" autofocus="true"></form:input>
			                      <form:errors path="expDate"></form:errors>
							    </div>				    
						  </div>
						      <form:input name="rpOrderID" id="rpOrderID" type="hidden" path="rpOrderID" class="form-control" autofocus="true" value="${rpOrderID}"></form:input>					  

					  		  <button class="btn btn-lg btn-primary btn-block text-center" type="submit">Submit</button>						  
					</div>
					<div class="col"></div>
				</div>
				</form:form>
				</c:if>
				<c:if test="${!empty receivedQtyError}">
					<p class="text-warning">Error: ${receivedQtyError}</p>
				</c:if>
				<c:if test="${!empty saveReceivedQtyToAnotherLot}">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title text-info">Note:</h5>
					    <p class="card-text">Do you want to save the rest of the quantity to another lot?</p>
					    <a href="${saveReceivedQtyToAnotherLot}/receivedRpOrder" class="card-link btn btn-primary" role="button">Yes</a>
					    <a href="inventory?orderStatus=openOrder" class="card-link btn btn-primary" role="button">No</a>
					  </div>
					</div>
				</c:if>
				<c:if test="${!empty rpOrderClosed}">
					<p class="text-success">Replenish order # ${rpOrderClosed} is now closed</p>
				</c:if>
				
				<!-- View Open Replenishment Order -->
				<c:if test="${not empty openOrders}">
				<h4><b>Open Replenishment Orders</b></h4>
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Order #</th>
							<th scope="col">Date</th>
							<c:forEach items="${productNames}" var="product">
								<th scope="col">${product.productName}</th>
								<th scope="col">Unreceived Quantity</th>
							</c:forEach>
							<th scope="col">Order Total</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${openOrders}" var="order">
						<tr>
							<th scope="row">${order.rpOrderID}</th>
							<td>${order.created_At}</td>
							<c:forEach items="${order.rpProducts}" var="product">
								<td>${product.orderedProductQty}</td>
								<td>${product.unreceivedProductqty}</td>
							</c:forEach>
							<td>${order.totalPrice}</td>
							<td>								
								<a href="${order.rpOrderID}/receivedRpOrder">Mark as Received</a>
							</td>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>	
				
				<!-- Add Product -->
				<c:if test="${!empty addProductForm}">
				<form:form modelAttribute="product" action="addProduct" method="post" class="form-control">
	
				<legend>Enter Product Details</legend>					
				<div class="row">
					<div class="col"></div>
					<div class="col-8">
					<c:if test="${!empty update}">
						<p class="text-info">${update}</p>
					</c:if>
						<spring:bind path="productName">
				            <div class="form-group">
				            	Product Name*:
				                <form:input type="text" path="productName" class="form-control"
				                            autofocus="true"></form:input>
				                <form:errors path="productName"></form:errors>
				            </div>
				        </spring:bind>
				        <spring:bind path="cost">
				            <div class="form-group">
				            	Cost from Factory*:
				                <form:input type="text" path="cost" class="form-control"
				                            autofocus="true"></form:input>
				                <form:errors path="cost"></form:errors>
				            </div>
				        </spring:bind>
				        <spring:bind path="price">
				            <div class="form-group">
				            	Price for Sale*:
				                <form:input type="text" path="price" class="form-control"
				                            autofocus="true"></form:input>
				                <form:errors path="price"></form:errors>
				            </div>
				        </spring:bind>
				       	<spring:bind path="productID">
				            <div class="form-group">
				                <form:hidden path="productID" class="form-control"
				                            autofocus="true"></form:hidden>
				                <form:errors path="productID"></form:errors>
				            </div>
				        </spring:bind>
				        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
					</div>
					<div class="col"></div>		
					</div>
					</form:form>
					</c:if>
				<c:if test="${not empty addProductSucceeded}">
					<div class="alert alert-success" role="alert">
					  <h4 class="alert-heading">Success!</h4>
					  <p class="text-success">${addProductSucceeded}</p>
					</div>
				</c:if>
				
				<!-- Edit Product -->
				
				<c:if test="${not empty editProduct}">
					<table class="table">								
					<thead>
						<tr>
							<th scope="col">Select A Product Type</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<form:form method="POST" modelAttribute="product" action="editProduct">
						<tr>
							<th scope="row">
								<spring:bind path="productName">
				                   	<form:select path = "productName">
					                   <%-- <form:option value = "NONE" label = "Select"/> --%>
					                   <form:options items = "${productList}" />
					                </form:select>   
				                </spring:bind> 
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
				<c:if test="${not empty viewAllProducts}">
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Product ID</th>
							<th scope="col">Product Name</th>
							<sec:authorize access="hasAuthority('Manager')">
							<th scope="col">Cost</th>
							</sec:authorize>
							<th scope="col">Price</th>
							<th scope="col">Quantity</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
					<c:forEach items="${listAllProducts}" var="product">
						<tr>
							<th scope="row">${product.productID}</th>
							<td>${product.productName}</td>
							<sec:authorize access="hasAuthority('Manager')">
							<td>${product.cost}</td>
							</sec:authorize>
							<td>${product.price}</td>
							<td>${product.quantity}</td>								
							<sec:authorize access="hasAuthority('Manager')">
							<%-- <td><a href="products/${product.productID}/updateProduct">Update</a></td> --%>
							<td>								
								<a href="${product.productID}/updateProduct">Update</a><br/>
								<a href="${product.productID}/deleteProduct">Delete</a>
							</td>
							</sec:authorize>
							<sec:authorize access="hasAuthority('Customer')">
							<td><a href="#">Order</a></td>
							</sec:authorize>							
						</tr>
					</c:forEach>				
					</tbody>
				</table>
				</c:if>			
				</c:if>
				
				<c:if test="${!empty editProductInfo}">
				<table class="table">								
					<thead>
						<tr>
							<th scope="col">Product ID</th>
							<th scope="col">Product Name</th>
							<sec:authorize access="hasAuthority('Manager')">
							<th scope="col">Cost</th>
							</sec:authorize>
							<th scope="col">Price</th>
							<th scope="col">Quantity</th>
							<th scope="col">Action</th>
						</tr>
					</thead>					
					<tbody>
						<tr>
							<th scope="row">${productToBeEdited.productID}</th>
							<td>${productToBeEdited.productName}</td>
							<sec:authorize access="hasAuthority('Manager')">
							<td>${productToBeEdited.cost}</td>
							</sec:authorize>
							<td>${productToBeEdited.price}</td>
							<td>${productToBeEdited.quantity}</td>								
							<sec:authorize access="hasAuthority('Manager')">
							<%-- <td><a href="products/${product.productID}/updateProduct">Update</a></td> --%>
							<td>								
								<a href="${productToBeEdited.productID}/updateProduct">Update</a><br/>
								<a href="${productToBeEdited.productID}/deleteProduct">Delete</a>
							</td>
							</sec:authorize>
							<sec:authorize access="hasAuthority('Customer')">
							<td><a href="#">Order</a></td>
							</sec:authorize>							
						</tr>				
					</tbody>
				</table>
			</c:if>
			<c:if test="${not empty deleteProductSucceeded}">
				<div class="alert alert-danger" role="alert">
				  <h4 class="alert-heading">Alert!</h4>
				  <p class="text-success">${deleteProductSucceeded}</p>
				</div>
			</c:if>
			
			<!-- Update Product -->
			<c:if test="${!empty productToBeUpdated.productID}">
				<form:form modelAttribute="productToBeUpdated" action="updateProduct" method="post" class="form-control">
	
				<legend>Update Product Details</legend>					
				<div class="row">
					<div class="col"></div>
					<div class="col-8">
					<c:if test="${!empty update}">
						<p class="text-info">${update}</p>
					</c:if>
						<spring:bind path="productName">
				            <div class="form-group">
				            	Product Name*:
				                <form:input type="text" path="productName" class="form-control"
				                            autofocus="true"></form:input>
				                <form:errors path="productName"></form:errors>
				            </div>
				        </spring:bind>
				        <spring:bind path="cost">
				            <div class="form-group">
				            	Cost from Factory*:
				                <form:input type="text" path="cost" class="form-control"
				                            autofocus="true"></form:input>
				                <form:errors path="cost"></form:errors>
				            </div>
				        </spring:bind>
				        <spring:bind path="price">
				            <div class="form-group">
				            	Price for Sale*:
				                <form:input type="text" path="price" class="form-control"
				                            autofocus="true"></form:input>
				                <form:errors path="price"></form:errors>
				            </div>
				        </spring:bind>
				       	<spring:bind path="productID">
				            <div class="form-group">
				                <form:hidden path="productID" class="form-control"
				                            autofocus="true"></form:hidden>
				                <form:errors path="productID"></form:errors>
				            </div>
				        </spring:bind>
				        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
					</div>
					<div class="col"></div>		
					</div>
					</form:form>
					</c:if>
				<c:if test="${not empty updateProductSucceeded}">
					<div class="alert alert-success" role="alert">
					  <h4 class="alert-heading">Success!</h4>
					  <p class="text-success">${updateProductSucceeded}</p>
					</div>
				</c:if>
			
			<!-- Order Replenishment -->
			<c:if test="${not empty orderReplenishment}">							
				<h4>Replenishment Order</h4>			        
		        <table class="table table-hover table-condensed">								
				<thead>
					<tr>
						<th scope="col" style="width:25%">Product Name</th>
						<th scope="col" style="width:25%">Quantity On Hand</th>
						<th scope="col" style="width:25%">Order Quantity</th>
						<th scope="col" style="width:25%">Action</th>
					</tr>
				</thead>					
				<tbody>
				<c:forEach items="${listProducts}" var="product">
				<form:form method="POST" action="${product.productID}/replenish">
					<tr>
						<th scope="row">${product.productName}</th>
						<td>${product.quantity}</td>
						<td data-th="Quantity">
							<input type="text" name="num" class="form-control text-center" value="0">
						</td>
						<td>
							<button class="btn btn-primary" type="submit">Add to Order</button>
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
							<a href="orderSummary" class="btn btn-warning">Order Summary</a>							
						</td>
					</tr>
				</tfoot>
				</table>										
			</c:if>
			
			<!-- Order Summary -->
			<c:if test="${!empty orderSummary}">
			<div class="row">
		        <div class="col-md-12">
		            <div class="panel panel-default">
		                <div class="panel-heading">
		                    <h3 class="text-center"><strong>Order summary</strong></h3>
		                </div>
		                <div class="panel-body">
		                    <div class="table-responsive">		                        		                        
		                        <table class="table table-condensed">
		                            <thead>
		                                <tr>
		                                    <td><strong>Product Name</strong></td>
		                                    <td class="text-center"><strong>Product Cost</strong></td>
		                                    <td class="text-center"><strong>Order Quantity</strong></td>
		                                    <td class="text-center"><strong>Action</strong></td>
		                                    <td class="text-right"><strong>Total</strong></td>
		                                </tr>
		                            </thead>
		                            <tbody>
		                            <form:form method="POST" action="updateRpCart">
		                            <c:forEach items="${products}" var="product">
										<tr>
		                                    <td>${product.productName}</td>
		                                    <td class="text-center">$${product.cost}</td>
		                                    <td class="text-center">
		                                    	<input type="text" name="quantity" class="form-control text-center" value="${product.orderedProductQty}">
		                                    </td>
		                                    <td class="text-center">
		                                    	<button type="submit" class="btn btn-info btn-sm"><i class="fa fa-refresh">Refresh</i></button>
												<a href="${product.productID}/deleteProductInRpCart" class="btn btn-danger btn-sm active" role="button" aria-pressed="true"><i class="fa fa-trash-o">&nbspDelete&nbsp</i></a>	
		                                    </td>		                                    	
		                                    <td class="text-right">${product.cost * product.orderedProductQty}</td>
		                                </tr>				
									</c:forEach>
									</form:form>		                            
		                                <tr>
		                                    <td class="highrow"></td>
		                                    <td class="highrow"></td>
		                                    <td class="highrow"></td>
		                                    <td class="highrow text-center"><strong>Subtotal</strong></td>
		                                    <td class="highrow text-right">$${cart.cartTotal}</td>
		                                </tr>
		                                <tr>
		                                    <td class="emptyrow"></td>
		                                    <td class="emptyrow"></td>
		                                    <td class="emptyrow"></td>
		                                    <td class="emptyrow text-center"><strong>Shipping</strong></td>
		                                    <td class="emptyrow text-right">$20</td>
		                                </tr>
		                                <tr>
		                                    <td class="emptyrow"><i class="fa fa-barcode iconbig"></i></td>
		                                    <td class="emptyrow"></td>
		                                    <td class="emptyrow"></td>
		                                    <td class="emptyrow text-center"><strong>Total</strong></td>
		                                    <td class="emptyrow text-right">$${cart.cartTotal + 20}</td>
		                                </tr>
		                                <form:form class="form-horizontal" role="form" action="placeReplenishmentOrder" method="post" id="payment-form">
		                                <tr>
		                                	<td class="emptyrow"><i class="fa fa-barcode iconbig"></i></td>
		                                    <td class="emptyrow">
		                                    	<c:if test="${!empty shoppingCartQtyError}">
													<p class="text-warning">Error: ${shoppingCartQtyError}</p>
												</c:if>
		                                    </td>
		                                    <td class="emptyrow"></td>
		                                    <td class="emptyrow text-center"></td>
		                                    <td class="emptyrow text-right">		                                    		                                    
		                                    <button type="submit" class="btn btn-success btn-lg" style="width:100%;">Place Order
                                        	</button></td>
		                                </tr>
		                                </form:form>
		                            </tbody>
		                        </table>		                        	                        
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		    </c:if>
		    <c:if test="${not empty replenishmentOrderConfirmed}">
				<div class="alert alert-success" role="alert">
				  <h4 class="alert-heading">Success!</h4>
				  <p class="text-success">${replenishmentOrderConfirmed}</p>
				</div>
			</c:if>	
			
				</div>
				<div class="col"></div>
			</div>
			
			<a href="<c:url value="/perform_logout" />">Logout</a>	
			</div>	
			<div class="col"></div>
		</div>
	</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</body>
</html>