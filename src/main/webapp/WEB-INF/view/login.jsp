<%@ page pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <title>Avoté - Connexion</title>
        <link rel="shortcut icon" id="favicon-cntr" href="${pageContext.request.contextPath}/assets/images/favicon.ico">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/scss/fonts/icons/font.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css" type="text/css">
    </head>
    <body>
    <header>
        <a href="/" class="basic-link logo-cntr">
            <img src="${pageContext.request.contextPath}/assets/images/Logo-white.svg" alt="">
        </a>
    </header>

    <%@include file="infos.jsp" %>

    <main>
        <h1>connexion</h1>
        <form:form action="LoginForm" method="post" class="login-form" modelAttribute="user">
            <div class="form-row-cntr email-cntr">
                <form:label path="email" for="user-email" class="email-label">Email</form:label>
                <form:input path="email" type="email" class="email-input" id="user-email" name="user-email"/>
            </div>
            <div class="form-row-cntr password-cntr">
                <form:label path="password" for="user-password" class="password-label">Mot de passe</form:label>
                <form:input path="password" type="password" class="password-input" id="user-password" name="user-password-login"/>
            </div>
            <input type="submit" class="submit-input" id="form-submit" value="Valider" />
        </form:form>
        <a href="#" class="basic-link login-question">Mot de passe oublié ?</a>
        <a href="/Signin" class="basic-link login-question">Vous n'avez pas encore de compte ? S'inscrire</a>
    </main>

    <script src="${pageContext.request.contextPath}/assets/javascript/external/jQuery/jquery-2.2.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/javascript/external/jQuery/jquery-ui.js"></script>
    
    <script src="${pageContext.request.contextPath}/assets/javascript/login.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/javascript/notifications.js" type="text/javascript"></script>
    </body>
</html>