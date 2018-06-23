<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="es">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="author" content="Anupam Mondal">
      <title>Registration</title>
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
            <div class="col-sm-4 col-xs-12"></div>
            <div class="col-sm-4 col-xs-12">
               <img src="img/BH-ZOR_logo.png" alt="" class="img-responsive">
               <hr>
               <form:form method="POST" modelAttribute="userForm" class="form-signin">
                  <h2 class="form-signin-heading">Crea Tu Cuenta</h2>
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
                        <form:input type="text" path="username" class="form-control" placeholder="Usuarionombre"
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
                        <form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirmar Contraseña"></form:input>
                        <form:errors path="passwordConfirm"></form:errors>
                     </div>
                  </spring:bind>
                  <spring:bind path="email">
                     <div class="form-group">
                        <form:input type="text" path="email" class="form-control" placeholder="email@example.com"
                           autofocus="true"></form:input>
                        <form:errors path="email"></form:errors>
                     </div>
                  </spring:bind>
                  <spring:bind path="phoneNumber">
                     <div class="form-group">
                        <form:input type="text" path="phoneNumber" class="form-control" placeholder="Número de Teléfono"
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
                        <form:input type="text" path="state" class="form-control" placeholder="Estado"
                           autofocus="true"></form:input>
                        <form:errors path="state"></form:errors>
                     </div>
                  </spring:bind>
                  <spring:bind path="zip">
                     <div class="form-group">
                        <form:input type="text" path="zip" class="form-control" placeholder="Còdigo Postal"
                           autofocus="true"></form:input>
                        <form:errors path="zip"></form:errors>
                     </div>
                  </spring:bind>
                  <button class="btn btn-success" type="submit">Confirmar</button>
                    <c:if test="${empty addUser}">
                       <a class="btn btn-default" href="login">Regresa</a>&nbsp;
                    </c:if>
                    <c:if test="${!empty addUser}">
                       <a class="btn btn-default" href="addUser/backTOsiteManagement">Regresa</a>
                    </c:if>
               </form:form>
            </div>
            <div class="col-sm-4 col-xs-12"></div>
         </div>
      </div>
      <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
      <!-- Include all compiled plugins (below), or include individual files as needed -->
      <!-- Latest compiled and minified JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
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
</html>