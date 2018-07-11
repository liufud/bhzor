<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="es">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="author" content="Anupam Mondal">
      <title>Inventory</title>
      <!-- Bootstrap -->
      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
      <!-- Optional theme -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
      <style type="text/css">
         #paymentOptions {display:none;}
         #vendorSale {display:none;}
         #distributorSale {display:none;}
         #customerSale {display:none;}
         /* www.anupammondal.in */
         .container {
         margin-top: 25px;
         }
         header {
         height: 250px;
         background-color: #ffffff;
         padding: 30px 20px;
         background-size: cover;
         background-image: url(https://images.pexels.com/photos/415825/pexels-photo-415825.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260);
         background-position: center;
         }
         header h2 {
         border: #fff solid 3px;
         box-shadow: #000000 0px 0px 10px;
         border-radius: 3px;
         padding: 15px 15px;
         color: #fff;
         background-color: #007bffcc;
         width: fit-content;
         margin: 0 auto;
         }
         .nav-tabs {
         border-bottom: 1px solid #007bff;
         }
         .nav-tabs>li>a {
         border: none;
         }
         .nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover {
         border: 1px solid #007bff;
         border-bottom-color: transparent;
         }
         .thumbnail {
         border-top: none;
         border-radius: 0px 0px 4px 4px;
         border-color: #007bff;
         }
         .txt-bhzor {
         color: #4d5c93;
         }
         span#typetext {
         font-size: 15px;
         }
         .prdct {
         margin: 25px 15px;
         background: #007bff;
         padding: 25px 20px;
         border-radius: 5px;
         }
         .nav-tabs>li a {
         color: #007bff;
         }
         .tab-content {
         margin-bottom: 50px;
         }
         .prdct p a, .prdct p span {
         color: #fff;
         font-size: 15px;
         }
         p.userdtls {
         margin: 15px;
         }
         button.btn.btn-info {
         background-color: #007bff !important;
         border-color: #007bff !important;
         background-image: none;
         }
         footer {
         background-color: #007bff;
         color: #fff;
         padding: 25px 20px;
         }
         div.prdct p {
         cursor: pointer;
         }
         img.top-logo {
         margin: 0 auto;
         display: block;
         }
         /* previous stylesheet */
         .errorField{
         border:1px solid red;
         }
      </style>
      <!-- <script src="//webjars/1.17.0/dist/jquery.validate.min.js" type="text/javascript"></script>
         <script src="//webjars/1.17.0/dist/jquery.validate.js" type="text/javascript"></script> -->
      <!-- <script src="webjars/1.17.0/src/localization/messages_es.js" type="text/javascript"></script> -->
      <!-- <script src="js/form-validation.js"></script> -->
   </head>
   <body class="">
      <div class="container">
         <div class="row">
            <div class="col-sm-3 col-xs-12">
               <img src="img/BH-ZOR_logo.png" height="70" class="top-logo">
            </div>
            <div class="col-sm-9 col-xs-12">
               <%-- <sec:authorize access="hasAuthority('Manager')"> --%>
               <h1 class="text-center">BH-ZOR: Base de Datos</h1>
               <%-- 				</sec:authorize>
                  <sec:authorize access="hasAuthority('Customer')">
                  <div class="col5"><h1>Welcome</h1></div>
                  </sec:authorize> --%>
            </div>
         </div>
         <div class="row">
            <div class="col-xs-12">
               <hr>
               <h4>
                  <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                  &nbsp;Conectado como : 
                  <sec:authentication property="name"/>
                  <sec:authentication property="authorities"/>
                  &nbsp;<a class="btn btn-default" href="
                  <c:url value="/perform_logout" />
                  ">Cerrar Sesión</a>
               </h4>
               <hr>
            </div>
         </div>
         <div class="row">
            <div class="col-xs-12">
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
                  <li role="presentation">
                     <a href="orders?selectOrderType=true">Pedidos</a>
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
                  <li role="presentation" class="active">
                     <!-- <a class="nav-link" href="inventory">Inventory</a> -->
                     <!--                                    <div class="dropdown show">-->
                     <a href="inventory?orderStatus=openOrder">
                     Inventario
                     </a>								
                     <!-- <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="addProduct">Add Product</a>
                        <a class="dropdown-item" href="editProduct">View Inventory</a>
                        <a class="dropdown-item" href="orderReplenishment">Order Replenishment</a>
                        <a class="dropdown-item" href="#">Historical Inventory</a>
                        </div> -->
                     <!--                                     </div>	-->
                  </li>
                  <li role="presentation">
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
            </div>
         </div>
         <div class="row">
            <div class="col-sm-4 col-xs-12">
               <div class="prdct">
                  <p>
                     <span class="glyphicon glyphicon-inbox" aria-hidden="true"></span>
                     <a href="editProduct" role="button">Ver inventario</a>
                  </p>
                  <p>
                     <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                     <a href="addProduct" role="button">Agregar Productos</a>
                  </p>
                  <p>
                     <span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>
                     <a href="inventory?orderStatus=openOrder" role="button">Abrir Pedidos de Reposicion</a>
                  </p>
                  <p>
                     <span class="glyphicon glyphicon-record" aria-hidden="true"></span>
                     <a href="viewReceivedOrders" role="button">Ver Pedidos Recibidos</a>
                  </p>
                  <!-- <a class="btn btn-sm btn-secondary" href="inventory?orderStatus=closedOrder" role="button">Cerrado Pedidos de Reposicion</a> -->
                  <p>
                     <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>						
                     <a href="orderReplenishment" role="button">Pedidos de Reposición de Producto</a>
                  </p>
               </div>
            </div>
            <div class="col-sm-8 col-xs-12">
               <!-- Received Rp Order Form -->				
               <c:if test="${not empty rpOrderReceivedForm}">
                  <br/>
                  <form:form name="receivedOrderForm" modelAttribute="receivedRpOrder" action="receivedRpOrder" method="post" class="form-control">
                     <h2 class="form-signin-heading"># de Pedido ${rpOrderReceivedForm}</h2>
                     <div class="form-group row">
                        <label for="productName" class="col-sm-3 col-form-label">Producto</label>
                        <div class="col-sm-7">
                           <form:select name="productName" class="form-control" path="receivedRpProductName">
                              <%-- <form:option value = "NONE" label = "Select"/> --%>
                              <form:options items = "${rpProdNames}" />
                           </form:select>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="lotID" class="col-sm-3 col-form-label"># de Lote</label>
                        <div class="col-sm-7">
                           <form:input name="lotID" id="lotID" type="number" path="lotID" class="form-control" autofocus="true"></form:input>
                           <form:errors path="lotID"></form:errors>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="shelfID" class="col-sm-3 col-form-label"># de Estante</label>
                        <div class="col-sm-7">
                           <form:input name="shelfID" id="shelfID" type="number" path="shelfID" class="form-control" autofocus="true"></form:input>
                           <form:errors path="shelfID"></form:errors>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="qtyReceived" class="col-sm-3 col-form-label">Cantidad Recibida</label>
                        <div class="col-sm-7">
                           <form:input name="qtyReceived" id="qtyReceived" type="number" path="quantityReceived" class="form-control" autofocus="true"></form:input>
                           <form:errors path="quantityReceived"></form:errors>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="qtyRejected" class="col-sm-3 col-form-label">Cantidad Rechazada</label>
                        <div class="col-sm-7">
                           <form:input name="qtyRejected" id="qtyRejected" type="number" path="quantityRejected" class="form-control" autofocus="true"></form:input>
                           <form:errors path="quantityRejected" cssErrorClass="errorField"></form:errors>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="expDate" class="col-sm-3 col-form-label">Fecha de Expiracion<br>(dd/mm/yyyy)</label>
                        <div class="col-sm-7">
                           <form:input name="expDate" id="expDate" type="text" path="expDate" class="form-control" autofocus="true"></form:input>
                           <form:errors path="expDate"></form:errors>
                        </div>
                     </div>
                     <form:input name="rpOrderID" id="rpOrderID" type="hidden" path="rpOrderID" class="form-control" autofocus="true" value="${rpOrderID}"></form:input>
                     <button class="btn btn-success" type="submit">Confirmar</button>
                  </form:form>
               </c:if>
               <c:if test="${!empty receivedQtyError}">
                  <p class="text-warning">Error: ${receivedQtyError}</p>
               </c:if>
               <c:if test="${!empty saveReceivedQtyToAnotherLot}">
                  <div class="card">
                     <div class="card-body">
                        <h5 class="card-title text-info">Nota:</h5>
                        <p class="card-text">¿Desea guardar el resto de la cantidad en otro estante?</p>
                        <a href="${saveReceivedQtyToAnotherLot}/receivedRpOrder" class="card-link btn btn-primary" role="button">Sí</a>
                        <a href="inventory?orderStatus=openOrder" class="card-link btn btn-primary" role="button">No</a>
                     </div>
                  </div>
               </c:if>
               <c:if test="${!empty rpOrderClosed}">
                  <p class="text-success">#${rpOrderClosed} de Pedido de Reposicion está ahora cerrada</p>
               </c:if>
               <!-- View Received Replenishment Order -->
               <c:if test="${not empty receivedRpOrders}">
                  <h4><b>Pedidos Recibidos</b></h4>
                  <table class="table">
                     <thead>
                        <tr>
                           <th scope="col">No.</th>
                           <th scope="col">Fecha de Expiracion</th>
                           <th scope="col"># de Lote</th>
                           <th scope="col"># de Estante</th>
                           <th scope="col">ID de Pedido de Reposicion</th>
                           <th scope="col">Producto</th>
                           <th scope="col">Candidad Recibida</th>
                           <th scope="col">Candidad Rechazada</th>
                           <th scope="col">Recibido  Por</th>
                           <!-- <th scope="col">Total de Pedido</th> -->
                        </tr>
                     </thead>
                     <tbody>
                        <c:forEach items="${receivedRpOrders}" var="order">
                           <tr>
                              <th scope="row">${order.receivedRpOrderID}</th>
                              <td>${order.expDate}</td>
                              <td>${order.lotID}</td>
                              <td>${order.shelfID}</td>
                              <td>${order.rpOrderID}</td>
                              <td>${order.receivedRpProductName}</td>
                              <td>${order.quantityReceived}</td>
                              <td>${order.quantityRejected}</td>
                              <td>${order.createByUser.firstName} ${order.createByUser.lastName}</td>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </c:if>
               <!-- View Open Replenishment Order -->
               <c:if test="${not empty openOrders}">
                  <h4><b>Abrir Pedidos de Reposicion</b></h4>
                  <table class="table">
                     <thead>
                        <tr>
                           <th scope="col"># de Pedido</th>
                           <th scope="col">Fecha Creada</th>
                           <c:forEach items="${productNames}" var="product">
                              <th scope="col">${product.productName} No Procesado</th>
                           </c:forEach>
                           <th scope="col">Creado Por</th>
                           <!-- <th scope="col">Total de Pedido</th> -->
                           <th scope="col">Accion</th>
                        </tr>
                     </thead>
                     <tbody>
                        <c:forEach items="${openOrders}" var="order">
                           <tr>
                              <th scope="row">${order.rpOrderID}</th>
                              <td>${order.created_At}</td>
                              <c:forEach items="${order.rpProducts}" var="product">
                                 <td>${product.unprocessedProductqty}</td>
                              </c:forEach>
                              <td>${order.createByUser.firstName} ${order.createByUser.lastName}</td>
                              <%-- <td>${order.totalPrice}</td> --%>
                              <td>								
                                 <a href="${order.rpOrderID}/receivedRpOrder">Marcar como Recibido</a>
                              </td>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </c:if>
               <%-- 				<!-- View Closed Replenishment Order -->
                  <c:if test="${not empty closedOrders}">
                  <h4><b>Cerrado Pedidos de Reposicion</b></h4>
                  <table class="table">								
                          <thead>
                                  <tr>
                                          <th scope="col"># de Pedido</th>
                                          <th scope="col">Fecha</th>
                                          <c:forEach items="${productNames}" var="product">
                                                  <th scope="col">No Recibido ${product.productName}</th>
                                          </c:forEach>
                                          <th scope="col">Creado Por</th>
                                          <!-- <th scope="col">Total de Pedido</th> -->
                                          <th scope="col">Accion</th>
                                  </tr>
                          </thead>					
                          <tbody>
                          <c:forEach items="${openOrders}" var="order">
                                  <tr>
                                          <th scope="row">${order.rpOrderID}</th>
                                          <td>${order.created_At}</td>
                                          <c:forEach items="${order.rpProducts}" var="product">
                                                  <td>${product.unprocessedProductqty}</td>
                                          </c:forEach>
                                          <td>${order.createByUser.firstName} ${order.createByUser.lastName}</td>
                                          <td>${order.totalPrice}</td>
                                          <td>								
                                                  <a href="${order.rpOrderID}/receivedRpOrder">Marcar como Recibido</a>
                                          </td>							
                                  </tr>
                          </c:forEach>				
                          </tbody>
                  </table>
                  </c:if> --%>
               <!-- Add Product -->
               <c:if test="${!empty addProductForm}">
                  <form:form modelAttribute="product" action="addProduct" method="post" class="form-control">
                     <legend>Ingresar Detalles del Producto</legend>
                     <div class="row">
                        <div class="col"></div>
                        <div class="col-8">
                           <c:if test="${!empty update}">
                              <p class="text-info">${update}</p>
                           </c:if>
                           <spring:bind path="productName">
                              <div class="form-group">
                                 Nombre del Producto:
                                 <form:input type="text" path="productName" class="form-control"
                                    autofocus="true"></form:input>
                                 <form:errors path="productName"></form:errors>
                              </div>
                           </spring:bind>
                           <spring:bind path="cost">
                              <div class="form-group">
                                 Costo de Fàbrica:
                                 <form:input type="text" path="cost" class="form-control"
                                    autofocus="true"></form:input>
                                 <form:errors path="cost"></form:errors>
                              </div>
                           </spring:bind>
                           <%-- <spring:bind path="quantityOnHand">
                              <div class="form-group">
                                  Cantidad
                                  <form:input type="text" path="quantityOnHand" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="quantityOnHand"></form:errors>
                              </div>
                              </spring:bind> --%>
                           <%-- <spring:bind path="distributorPrice">
                              <div class="form-group">
                                  Precio de Distribuidor:
                                  <form:input type="text" path="distributorPrice" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="distributorPrice"></form:errors>
                              </div>
                              </spring:bind>
                              <spring:bind path="vendorPrice">
                              <div class="form-group">
                                  Precio de Venta:
                                  <form:input type="text" path="vendorPrice" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="vendorPrice"></form:errors>
                              </div>
                              </spring:bind>
                              <spring:bind path="clientPrice">
                              <div class="form-group">
                                  Precio de Cliente:
                                  <form:input type="text" path="clientPrice" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="clientPrice"></form:errors>
                              </div>
                              </spring:bind> --%>
                           <spring:bind path="productID">
                              <div class="form-group">
                                 <form:hidden path="productID" class="form-control" autofocus="true"></form:hidden>
                                 <form:errors path="productID"></form:errors>
                              </div>
                           </spring:bind>
                           <button class="btn btn-primary" type="submit">Confirmar</button>
                        </div>
                        <div class="col"></div>
                     </div>
                  </form:form>
               </c:if>
               <c:if test="${not empty addProductSucceeded}">
                  <div class="alert alert-success" role="alert">
                     <h4 class="alert-heading">Muy Bien!</h4>
                     <p class="text-success">${addProductSucceeded}</p>
                  </div>
               </c:if>
               <!-- Edit Product -->
               <c:if test="${not empty editProduct}">
                  <table class="table">
                     <thead>
                        <tr>
                           <th scope="col">Selecciona Tipo de Producto</th>
                           <th scope="col">Acciòn</th>
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
                  <c:if test="${not empty viewAllProducts}">
                     <table class="table">
                        <thead>
                           <tr>
                              <th scope="col">ID del Producto</th>
                              <th scope="col">Nombre del Producto</th>
                              <sec:authorize access="hasAuthority('Administrador')">
                                 <th scope="col">Costo de Fabrica</th>
                              </sec:authorize>
                              <!-- <th scope="col">Distribuidor Precio</th>
                                 <th scope="col">Vendedor Precio</th>
                                 <th scope="col">Cliente Precio</th> -->
                              <th scope="col">Cantidad Total en Mano</th>
                              <th scope="col">Estante</th>
                              <th scope="col">Acciòn</th>
                           </tr>
                        </thead>
                        <tbody>
                           <c:forEach items="${listAllProducts}" var="product">
                              <tr>
                                 <th scope="row">${product.productID}</th>
                                 <td>${product.productName}</td>
                                 <sec:authorize access="hasAuthority('Administrador')">
                                    <td>${product.cost}</td>
                                 </sec:authorize>
                                 <%-- <td>${product.distributorPrice}</td>
                                    <td>${product.vendorPrice}</td>
                                    <td>${product.clientPrice}</td> --%>
                                 <td>${product.quantity}</td>
                                 <td>${product.shelfLocations}</td>
                                 <sec:authorize access="hasAuthority('Administrador')">
                                    <%-- <td><a href="products/${product.productID}/updateProduct">Update</a></td> --%>
                                    <td>								
                                       <a href="${product.productID}/updateProduct">Actualizar</a><br/>
                                       <%-- <a href="${product.productID}/deleteProduct">Eliminar</a> --%>
                                    </td>
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
                           <th scope="col">ID del Producto</th>
                           <th scope="col">Nombre del Producto</th>
                           <sec:authorize access="hasAuthority('Administrador')">
                              <th scope="col">Costo</th>
                           </sec:authorize>
                           <!-- <th scope="col">Precio</th> -->
                           <th scope="col">Cantidad</th>
                           <th scope="col">Acciòn</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <th scope="row">${productToBeEdited.productID}</th>
                           <td>${productToBeEdited.productName}</td>
                           <sec:authorize access="hasAuthority('Administrador')">
                              <td>${productToBeEdited.cost}</td>
                           </sec:authorize>
                           <%-- <td>${productToBeEdited.price}</td> --%>
                           <td>${productToBeEdited.quantity}</td>
                           <sec:authorize access="hasAuthority('Administrador')">
                              <%-- <td><a href="products/${product.productID}/updateProduct">Update</a></td> --%>
                              <td>								
                                 <a href="${productToBeEdited.productID}/updateProduct">Actualizar</a><br/>
                                 <%-- <a href="${productToBeEdited.productID}/deleteProduct">Eliminar</a> --%>
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
                     <h4 class="alert-heading">¡Alerta!</h4>
                     <p class="text-success">${deleteProductSucceeded}</p>
                  </div>
               </c:if>
               <!-- Update Product -->
               <c:if test="${!empty productToBeUpdated.productID}">
                  <form:form modelAttribute="productToBeUpdated" action="updateProduct" method="post" class="form-control">
                     <legend>Actualizar Detalles del Producto</legend>
                     <div class="row">
                        <div class="col"></div>
                        <div class="col-8">
                           <c:if test="${!empty update}">
                              <p class="text-info">${update}</p>
                           </c:if>
                           <spring:bind path="productName">
                              <div class="form-group">
                                 Nombre del Producto:
                                 <form:input type="text" path="productName" class="form-control"
                                    autofocus="true"></form:input>
                                 <form:errors path="productName"></form:errors>
                              </div>
                           </spring:bind>
                           <spring:bind path="cost">
                              <div class="form-group">
                                 Costo de Fàbrica:
                                 <form:input type="text" path="cost" class="form-control"
                                    autofocus="true"></form:input>
                                 <form:errors path="cost"></form:errors>
                              </div>
                           </spring:bind>
                           <%-- <spring:bind path="distributorPrice">
                              <div class="form-group">
                                  Precio de Distribuidor:
                                  <form:input type="text" path="distributorPrice" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="distributorPrice"></form:errors>
                              </div>
                              </spring:bind>
                              <spring:bind path="vendorPrice">
                              <div class="form-group">
                                  Precio de Venta:
                                  <form:input type="text" path="vendorPrice" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="vendorPrice"></form:errors>
                              </div>
                              </spring:bind>
                              <spring:bind path="clientPrice">
                              <div class="form-group">
                                  Precio de Cliente:
                                  <form:input type="text" path="clientPrice" class="form-control"
                                              autofocus="true"></form:input>
                                  <form:errors path="clientPrice"></form:errors>
                              </div>
                              </spring:bind> --%>
                           <spring:bind path="productID">
                              <div class="form-group">
                                 <form:hidden path="productID" class="form-control"
                                    autofocus="true"></form:hidden>
                                 <form:errors path="productID"></form:errors>
                              </div>
                           </spring:bind>
                           <button class="btn btn-lg btn-primary btn-block" type="submit">Confirmar</button>
                        </div>
                        <div class="col"></div>
                     </div>
                  </form:form>
               </c:if>
               <c:if test="${not empty updateProductSucceeded}">
                  <div class="alert alert-success" role="alert">
                     <h4 class="alert-heading">Muy Bien!</h4>
                     <p class="text-success">${updateProductSucceeded}</p>
                  </div>
               </c:if>
               <!-- Order Replenishment -->
               <c:if test="${not empty orderReplenishment}">
                  <h4>Pedido de Reposiciòn</h4>
                  <table class="table table-hover table-condensed">
                     <thead>
                        <tr>
                           <th scope="col" style="width:25%">Nombre de Producto</th>
                           <th scope="col" style="width:25%">Cantidad Total en Mano</th>
                           <th scope="col" style="width:25%">Cantidad de Pedido</th>
                           <th scope="col" style="width:25%">Acción</th>
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
                                    <button class="btn btn-primary" type="submit">Agregar al Carrito</button>
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
                              <a href="orderSummary" class="btn btn-warning">Resumen del Pedido</a>							
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
                              <h3 class="text-center"><strong>Resumen del Pedido</strong></h3>
                           </div>
                           <div class="panel-body">
                              <div class="table-responsive">
                                 <table class="table table-condensed">
                                    <thead>
                                       <tr>
                                          <td><strong>Nombre del Producto</strong></td>
                                          <td class="text-center"><strong>Costo de Fabrica</strong></td>
                                          <td class="text-center"><strong>Cantidad</strong></td>
                                          <td class="text-center"><strong>Acción</strong></td>
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
                                                   <button type="submit" class="btn btn-info btn-sm"><i class="fa fa-refresh">Actualizar</i></button>
                                                   <a href="${product.productID}/deleteProductInRpCart" class="btn btn-danger btn-sm active" role="button" aria-pressed="true"><i class="fa fa-trash-o">&nbspEliminar&nbsp</i></a>	
                                                </td>
                                                <td class="text-right">${product.cost * product.orderedProductQty}</td>
                                             </tr>
                                          </c:forEach>
                                       </form:form>
                                       <tr>
                                          <td class="highrow"></td>
                                          <td class="highrow"></td>
                                          <td class="highrow"></td>
                                          <td class="highrow text-center"><strong>Total Parcial</strong></td>
                                          <td class="highrow text-right">
                                             $
                                             <fmt:formatNumber 
                                                value="${cart.cartTotal/1.16}" 
                                                maxFractionDigits="3"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td class="emptyrow"></td>
                                          <td class="emptyrow"></td>
                                          <td class="emptyrow"></td>
                                          <td class="emptyrow text-center"><strong>Impuesto</strong></td>
                                          <td class="emptyrow text-right">16%</td>
                                       </tr>
                                       <tr>
                                          <td class="emptyrow"><i class="fa fa-barcode iconbig"></i></td>
                                          <td class="emptyrow"></td>
                                          <td class="emptyrow"></td>
                                          <td class="emptyrow text-center"><strong>Total</strong></td>
                                          <td class="emptyrow text-right">
                                             $
                                             <fmt:formatNumber 
                                                value="${cart.cartTotal}" 
                                                maxFractionDigits="3"/>
                                          </td>
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
                                                <button type="submit" class="btn btn-success btn-lg" style="width:100%;">Hacer Pedido
                                                </button>
                                             </td>
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
                     <h4 class="alert-heading">Muy Bien!</h4>
                     <p class="text-success">${replenishmentOrderConfirmed}</p>
                  </div>
               </c:if>
            </div>
         </div>
      </div>
      <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
      <!-- Include all compiled plugins (below), or include individual files as needed -->
      <!-- Latest compiled and minified JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
      integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
   </body>
</html>