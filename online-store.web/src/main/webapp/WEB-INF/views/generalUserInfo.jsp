<%@ taglib prefix="shop" tagdir="/WEB-INF/tags/shop"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="OnlineShopResourceBundle" var="rb"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>General User Info</title>
</head>
<body>

<p>First Name: ${user.firstName}</p>
<p>Last Name: ${user.lastName}</p>
<p>Email: ${user.email}</p>

</body>
</html>