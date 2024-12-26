<%@ taglib prefix="shop" tagdir="/WEB-INF/tags/shop"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="OnlineShopResourceBundle" var="rb"/>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <title>Management Orders</title>
      
      <shop:css-imports-main/>
   </head>
   <body>
      
      <shop:header/>
   
      <div class="product-page-main">
         <div class="container">
            <div class="row">
               <div class="col-md-12">
                  <div class="prod-page-title">
                     <h2>Active Orders</h2>
                  </div>
               </div>
            </div>
            <div class="row">
             
               <shop:order-table/>
             
            </div>
         </div>
      </div>
      
     <shop:footer/>
     <shop:js-imports-main/>
   </body>
</html>