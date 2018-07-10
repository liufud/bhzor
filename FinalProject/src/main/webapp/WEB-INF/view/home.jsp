<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="es">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="author" content="Anupam Mondal">
      <title>Home</title>
      <!-- Bootstrap -->
      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
      <!-- Optional theme -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
      <style>
         #paymentOptions {display:none;}
         #vendorSale {display:none;}
         #distributorSale {display:none;}
         #customerSale {display:none;}
         /* www.anupammondal.in */
         .container {
         margin-top: 25px;
         }
         header {
         padding: 30px;
         background-size: cover;
         background-image: url(img/bh-zor-liposomas.png);
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
            /* border-top: none; */
            border-radius: 4px;
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
      </style>
   </head>
   <body onload="typeWriter();">
      <header>
         <div class="container">
            <h2 id="" class="text-center">
               BH-ZOR : Base de Datos<br>
               <span class="text-center" id="typetext"></span>
            </h2>
         </div>
      </header>
      <div class="container">
         <div class="row">
            <div class="col-xs-12">
               <!--                <img src="img/BH-ZOR_logo.png" height="70">-->
               <%-- <sec:authorize access="hasAuthority('Manager')"> --%>
               <!--				<div class="col-7"><h1>BH-ZOR: Base de Datos</h1></div>-->
               <%-- 				</sec:authorize>
                  <sec:authorize access="hasAuthority('Customer')">
                  <div class="col5"><h1>Welcome</h1></div>
                  </sec:authorize> --%>                            
            </div>
         </div>
         <div class="row">
            <div class="col-xs-12">
               <h4>
                  <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Conectado como : 
                  <sec:authentication property="name"/>
                  <sec:authentication property="authorities"/>
               </h4>
            </div>
         </div>
         <div class="row">
            <div class="col-xs-12">
<!--               <ul class="nav nav-tabs" role="tablist">
                  <sec:authorize access="hasAuthority('Administrador')">
                       <li class="nav-item">
                        <a class="nav-link" href="dashboard">Panel de Control</a>
                        </li>					  				
                        <li class="nav-item">
                        <a class="nav-link" href="sales">Ventas</a>
                        </li> 
                  </sec:authorize>
                  <li role="presentation" class="active">
                     <a href="orders?selectOrderType=true" aria-controls="home" role="tab" data-toggle="tab">Pedidos</a>
                  </li>
                  <sec:authorize access="hasAuthority('Administrador')">
                     <li role="presentation">
                        <a class="nav-link" href="inventory?orderStatus=openOrder">Inventario</a>
                     </li>
                  </sec:authorize>
                  <li role="presentation">
                     <a class="nav-link" href="siteManagement">Administración de la Pagina</a>
                  </li>
               </ul>-->
<!--            </div>
         </div>-->
            <main role="main">
    <!--            <div class="row">
                   <div class="col-xs-12">-->
                  <div class="thumbnail">
                     <img src="img/BH-ZOR_logo.png" alt="BH-ZOR" title="BH-ZOR">
                     <div class="caption">
                        <h3 class="text-center">Bienvenido!</h3>
                        <hr>
                        <sec:authorize access="hasAuthority('Administrador')">
                           <p class="text-justified">Esta aplicación web es un control de inventario multi-usuario. No solo ayuda a administradores en manejar el inventario y hacer pedidos 
                              de producto, también permite a diferentes usuarios (clientes, vendedores o distribuidores) en hacer pedidos para diferentes productos. 
                           </p>
                        </sec:authorize>
                        <%-- <sec:authorize access="hasAnyRole('Cliente','Vendedor','Distribuidor')"> --%>
                        <sec:authorize access="hasAuthority('Distribuidor') or hasAuthority('Vendedor') or hasAuthority('Cliente')">
                           <p>Use esta pagina Web para hacer todos sus pediods con BH-ZOR!</p>
                        </sec:authorize>
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
                        <hr>
                        <p class="text-center">
                           <a class="btn btn-success" href="orders?selectOrderType=true" role="button">Entrar &raquo;</a> 
                           <a class="btn btn-default" role="button" href="
                           <c:url value="/perform_logout" />
                           ">Cerrar Sesión</a>
                        </p>
                     </div>
                  </div>
            </main>
          </div>
        </div>
      </div>
      <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!--      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>-->
      <!-- Include all compiled plugins (below), or include individual files as needed -->
      <!-- Latest compiled and minified JavaScript -->
<!--      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
      <script>
         var i = 0;
         var txt = 'Nanotecnología liposomado de excelencia';
         var speed = 50;
         
         function typeWriter() {
           if (i < txt.length) {
             document.getElementById("typetext").innerHTML += txt.charAt(i);
             i++;
             setTimeout(typeWriter, speed);
           }
         }
      </script>
   </body>
</html>-->