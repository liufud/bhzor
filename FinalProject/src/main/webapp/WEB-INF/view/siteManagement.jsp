<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<html lang="es">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="author" content="Anupam Mondal">
      <title>Administración de la Pagina</title>
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
         #cust {
         display:none;
         }
      </style>
   </head>
   <body class="bg-light">
      <div class="container">
         <div class="row">
            <div class="col-sm-3 col-xs-12">
               <img src="img/BH-ZOR_logo.png" height="70" class="top-logo">
            </div>
            <%-- <sec:authorize access="hasAuthority('Manager')"> --%>
            <div class="col-sm-9 col-xs-12">
               <h1 class="text-center">BH-ZOR: Base de Datos</h1>
            </div>
         </div>
         <div class="row">
            <div class="col-xs-12">
               <hr>
               <h4>
                  <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                  &nbsp;Conectado como: 
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
            <%-- 	</sec:authorize>
               <sec:authorize access="hasAuthority('Customer')">
               <div class="col5"><h1>Welcome</h1></div>
               </sec:authorize> --%>
            <div class="col-xs-12">
               <ul class="nav nav-tabs" role="tablist">
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
                  <li role="tablist">
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
                  <sec:authorize access="hasAuthority('Administrador')">
                     <li role="tablist">
                        <a href="inventory?orderStatus=openOrder">Inventario</a>
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
                  </sec:authorize>
                  <li role="tablist">
                     <!-- <a class="nav-link" href="siteManagement">Site Management</a> -->
                     <!--							    <div class="dropdown show">-->
                     <a href="inventory">
                     Administración de la Pagina
                     </a>								
                     <!-- <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="allUsers">All Users</a>
                        <a class="dropdown-item" href="addUser">Add User</a>
                        <a class="dropdown-item" href="report/?type=xls">Export Data</a>
                        </div> -->
                     <!--							     </div>-->
                  </li>
               </ul>
            </div>
         </div>
         <!-- Display Orders Content Here -->
         <div class="row">
            <div class="col-sm-4 col-xs-12">
               <div class="prdct">
                  <sec:authorize access="hasAuthority('Administrador')">
                     <p>
                        <span class="glyphicon glyphicon-th" aria-hidden="true"></span>
                        <a class="" href="allUsers" role="button">Todos los Usuarios</a>
                     </p>
                  </sec:authorize>
                  <sec:authorize access="hasAuthority('Administrador') or hasAuthority('Vendedor') or hasAuthority('Distribuidor')">
                     <p>
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <a class="" href="addUser" role="button">Agregar Usuario</a>
                     </p>
                  </sec:authorize>
                  <sec:authorize access="hasAuthority('Administrador')">
                     <p>
                        <span class="glyphicon glyphicon-save-file" aria-hidden="true"></span>
                        <a class="" href="report/?type=xls" role="button">Exportar Datos</a>
                     </p>
                  </sec:authorize>
               </div>
            </div>
            <div class="col-sm-8 col-xs-12">
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
                     <label>Selecciona el tipo de usuario&nbsp</label>
                     <select class="roleList_select" id="role" name="roleList">
                        <option selected>Choose...</option>
                        <option onclick="check()" value="Cliente">Cliente</option>
                        <sec:authorize access="hasAuthority('Administrador')">
                           <option onclick="check2()" value="Vendedor">Vendedor</option>
                           <option onclick="check2()" value="Distribuidor">Distribuidor</option>
                           <option onclick="check2()" value="Administrador">Administrador</option>
                        </sec:authorize>
                     </select>
                     <div id="cust">
                        <label>
                           Selecciona a un Vendedor para este cliente&nbsp
                           <select id="vendorName" name="_vandorName">
                              <option selected>Choose...</option>
                              <c:forEach var="vendor" items="${allVendorsName}">
                                 <option value="${vendor}">${vendor}</option>
                              </c:forEach>
                           </select>
                           <br/>*Si el cliente no tiene un vendedor asociado a el deje en blanco
                        </label>
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
                     <%-- <spring:bind path="state">
                        <div class="form-group">
                         	<form:select id="id_state" class="form-control" path = "state">
                          <form:option value = "NONE" label = "Estado"/>
                          <form:options items = "${stateName}" />
                        </form:select>
                        </div>   
                        </spring:bind> --%>
                     <spring:bind path="state">
                        <div class="form-group">
                           <form:input type="text" path="state" class="form-control" placeholder="Estado"
                              autofocus="true"></form:input>
                           <form:errors path="state"></form:errors>
                        </div>
                     </spring:bind>
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
               <!-- Add User -->
               <c:if test="${!empty addNewClient}">
                  <form:form method="POST" modelAttribute="userForm" class="form-signin" action="addNewClient">
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
                     <label>Selecciona el tipo de usuario&nbsp</label>
                     <select class="roleList_select" id="role" name="roleList">
                        <option selected>Choose...</option>
                        <option onclick="check()" value="Cliente">Cliente</option>
                        <sec:authorize access="hasAuthority('Administrador')">
                           <option onclick="check2()" value="Vendedor">Vendedor</option>
                           <option onclick="check2()" value="Distribuidor">Distribuidor</option>
                           <option onclick="check2()" value="Administrador">Administrador</option>
                        </sec:authorize>
                     </select>
                     <div id="cust">
                        <label>
                           Selecciona a un Vendedor para este cliente&nbsp
                           <select id="vendorName" name="_vandorName">
                              <option selected>Choose...</option>
                              <c:forEach var="vendor" items="${allVendorsName}">
                                 <option value="${vendor}">${vendor}</option>
                              </c:forEach>
                           </select>
                           <br/>*Si el cliente no tiene un vendedor asociado a el deje en blanco
                        </label>
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
                     <%-- <spring:bind path="state">
                        <div class="form-group">
                         	<form:select id="id_state" class="form-control" path = "state">
                          <form:option value = "NONE" label = "Estado"/>
                          <form:options items = "${stateName}" />
                        </form:select>
                        </div>   
                        </spring:bind> --%>
                     <spring:bind path="state">
                        <div class="form-group">
                           <form:input type="text" path="state" class="form-control" placeholder="Estado"
                              autofocus="true"></form:input>
                           <form:errors path="state"></form:errors>
                        </div>
                     </spring:bind>
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
                     <sec:authorize access="hasAuthority('Administrador')">
                        <label>Selecciona el tipo de usuario&nbsp</label>
                        <select class="roleList_select" id="role" name="roleList">
                           <option selected>Choose...</option>
                           <option onclick="check()" value="Cliente">Cliente</option>
                           <option onclick="check2()" value="Vendedor">Vendedor</option>
                           <option onclick="check2()" value="Distribuidor">Distribuidor</option>
                           <option onclick="check2()" value="Administrador">Administrador</option>
                        </select>
                        <div id="cust">
                           <label>
                              Selecciona a un Vendedor para este cliente&nbsp
                              <select id="vendorName" name="_vendorName">
                                 <option selected>Choose...</option>
                                 <c:forEach var="vendor" items="${allVendorsName}">
                                    <option value="${vendor}">${vendor}</option>
                                 </c:forEach>
                              </select>
                              <br/>*Si el cliente no tiene un vendedor asociado a el deje en blanco
                           </label>
                        </div>
                     </sec:authorize>
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
                           <th scope="col">Usuario</th>
                           <th scope="col">Tipo de Usuario</th>
                           <th scope="col">Cambiar a</th>
                           <th scope="col">Accion</th>
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
                                 <button class="btn btn-primary" type="submit">Confirmar</button>
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
                           <th scope="col">ID Usuario</th>
                           <th scope="col">Nombre</th>
                           <th scope="col">Email</th>
                           <th scope="col">Telefono</th>
                           <th scope="col">Direccion</th>
                           <th scope="col">Fecha Creada</th>
                           <th scope="col">Tipo de Usuario</th>
                           <th scope="col">Vendedor Asociado</th>
                           <th scope="col">Accion</th>
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
                                 <a href="user/${user.userId}/updateRole">Cambiar Tipo de Usuario</a><br/>
                                 <sec:authorize access="hasAuthority('Administrador')">
                                    <a href="user/${user.userId}/updateInfo">Actualizar Informacion</a><br/>							
                                    <a href="user/${user.userId}/deleteUser">Eliminar</a>
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
                           <th scope="col">ID Usuario</th>
                           <th scope="col">Nombre</th>
                           <th scope="col">Email</th>
                           <th scope="col">Telefono</th>
                           <th scope="col">Direccion</th>
                           <!-- <th scope="col">Fecha Creada</th> -->
                           <th scope="col">Tipo de Usuario</th>
                           <th scope="col">Vendedor Asociado</th>
                           <th scope="col">Accion</th>
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
                                 <sec:authorize access="hasAuthority('Administrador')">
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
               <!-- Vendor Only -->
               <c:if test="${not empty user_Vendor}">
                  <table class="table">
                     <thead>
                        <tr>
                           <th scope="col">ID Usuario</th>
                           <th scope="col">Nombre</th>
                           <th scope="col">Email</th>
                           <th scope="col">Telefono</th>
                           <th scope="col">Direccion</th>
                           <!-- <th scope="col">Fecha Creada</th> -->
                           <th scope="col">Tipo de Usuario</th>
                           <th scope="col">Vendedor Asociado</th>
                           <th scope="col">Accion</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <th scope="row">${user_Vendor.userId}</th>
                           <td>${user_Vendor.firstName} ${user_Vendor.lastName}</td>
                           <td>${user_Vendor.email}</td>
                           <td>${user_Vendor.phoneNumber}</td>
                           <td>${user_Vendor.address}, ${user_Vendor.city}, ${user_Vendor.state} ${user_Vendor.zip}</td>
                           <%-- <td>${user.created_at}</td> --%>
                           <td>${user_Vendor.roleName}</td>
                           <td>${user_Vendor.vendor.firstName} ${user_Vendor.vendor.lastName}</td>
                           <td>
                              <sec:authorize access="hasAuthority('Vendedor')">
                                 <a href="user/${user_Vendor.userId}/updateInfo">Update Info</a><br/>							
                              </sec:authorize>
                           </td>
                        </tr>
                     </tbody>
                  </table>
                  <tag:paginate max="3" offset="${offset}" count="${count}"
                     uri="siteManagement" next="&raquo;" previous="&laquo;" />
               </c:if>
               <!-- Distributor Only -->
               <c:if test="${not empty user_Distributor}">
                  <table class="table">
                     <thead>
                        <tr>
                           <th scope="col">ID Usuario</th>
                           <th scope="col">Nombre</th>
                           <th scope="col">Email</th>
                           <th scope="col">Telefono</th>
                           <th scope="col">Direccion</th>
                           <!-- <th scope="col">Fecha Creada</th> -->
                           <th scope="col">Tipo de Usuario</th>
                           <th scope="col">Vendedor Asociado</th>
                           <th scope="col">Accion</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <th scope="row">${user_Distributor.userId}</th>
                           <td>${user_Distributor.firstName} ${user_Distributor.lastName}</td>
                           <td>${user_Distributor.email}</td>
                           <td>${user_Distributor.phoneNumber}</td>
                           <td>${user_Distributor.address}, ${user_Distributor.city}, ${user_Distributor.state} ${user_Distributor.zip}</td>
                           <%-- <td>${user.created_at}</td> --%>
                           <td>${user_Distributor.roleName}</td>
                           <td>${user_Distributor.vendor.firstName} ${user_Distributor.vendor.lastName}</td>
                           <td>
                              <sec:authorize access="hasAuthority('Distribuidor')">
                                 <a href="user/${user_Distributor.userId}/updateInfo">Update Info</a><br/>							
                              </sec:authorize>
                           </td>
                        </tr>
                     </tbody>
                  </table>
                  <tag:paginate max="3" offset="${offset}" count="${count}"
                     uri="siteManagement" next="&raquo;" previous="&laquo;" />
               </c:if>
               <!-- Client Only -->
               <c:if test="${not empty user_Client}">
                  <table class="table">
                     <thead>
                        <tr>
                           <th scope="col">ID Usuario</th>
                           <th scope="col">Nombre</th>
                           <th scope="col">Email</th>
                           <th scope="col">Telefono</th>
                           <th scope="col">Direccion</th>
                           <!-- <th scope="col">Fecha Creada</th> -->
                           <th scope="col">Tipo de Usuario</th>
                           <th scope="col">Vendedor Asociado</th>
                           <th scope="col">Accion</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <th scope="row">${user_Client.userId}</th>
                           <td>${user_Client.firstName} ${user_Client.lastName}</td>
                           <td>${user_Client.email}</td>
                           <td>${user_Client.phoneNumber}</td>
                           <td>${user_Client.address}, ${user_Client.city}, ${user_Client.state} ${user_Client.zip}</td>
                           <%-- <td>${user.created_at}</td> --%>
                           <td>${user_Client.roleName}</td>
                           <td>${user_Client.vendor.firstName} ${user_Client.vendor.lastName}</td>
                           <td>
                              <sec:authorize access="hasAuthority('Cliente')">
                                 <a href="user/${user_Client.userId}/updateInfo">Update Info</a><br/>							
                              </sec:authorize>
                           </td>
                        </tr>
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
                              <sec:authorize access="hasAuthority('Administrador')">							
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
         </div>
      </div>
      </div>
      </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
      <!-- Include all compiled plugins (below), or include individual files as needed -->
      <!-- Latest compiled and minified JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
      integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
      <script>
         $("SELECT.roleList_select").change(function(){
         	if($(this).val() == "Cliente"){
         		document.getElementById("cust").style.display="block";
         	}else{
         		document.getElementById("cust").style.display="none";
         	}
         })
         /* function check() {
         	document.getElementById("cust").style.display="block";
         }
         
         function check2() {
         		document.getElementById("cust").style.display="none";
         } */
         
      </script>
   </body>
</html>