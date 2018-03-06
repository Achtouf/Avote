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
        <title>Avoté - Inscription</title>
        <link rel="shortcut icon" id="favicon-cntr" href="${pageContext.request.contextPath}/assets/images/favicon.ico">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/scss/fonts/icons/font.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/signin.css" type="text/css">
    </head>
    <body>
    <header>
        <a href="/" class="basic-link logo-cntr">
            <img src="${pageContext.request.contextPath}/assets/images/Logo-white.svg" alt="">
        </a>
    </header>

    <%@include file="infos.jsp" %>

    <main>
        <h1>Inscription</h1>
        <div class="signin-form-cntr">
            <form:form action="SigninForm" method="post" class="signin-form" modelAttribute="user">
                <div class="form-row-cntr">
                    <div class="form-col-cntr">
                        <form:label path="lastName" for="user-lastname" class="lastname-label">Nom</form:label>
                        <form:input path="lastName" type="text" class="lastname-input" id="user-lastname" name="user-lastname"/>
                    </div>
                    <div class="form-col-cntr">
                        <form:label path="firstName" for="user-firstname" class="firstname-label">Prénom</form:label>
                        <form:input path="firstName" type="text" class="firstname-input" id="user-firstname" name="user-firstname"/>
                    </div>
                </div>
                <div class="form-row-cntr">
                    <div class="form-col-cntr email-cntr">
                        <form:label path="email" for="user-email" class="email-label">Email</form:label>
                        <form:input path="email" type="email" class="email-input" id="user-email" name="user-email"/>
                    </div>
                    <div class="form-col-cntr date-cntr">
                        <form:label path="birthday" for="user-date" class="date-label">Date de naissance</form:label>
                        <form:input path="birthday" type="text" class="date-input" id="user-date" name="user-date" data-type="date" pattern="[0-9]{2}/{1}[0-9]{2}/{1}[0-9]{4}"/>
                    </div>
                </div>
                <div class="form-row-cntr">
                    <div class="form-col-cntr password-cntr">
                        <form:label path="password" for="user-password" class="password-label">Mot de passe</form:label>
                        <form:input path="password" type="password" class="password-input" id="user-password" name="user-password"/>
                        <form:errors path="password" />
                        <span class="password-reveal" data-reveal="user-password"><span class="icon-visit-profile"><span class="path1"></span><span class="path2"></span></span></span>
                    </div>
                    <div class="form-col-cntr password-cntr">
                        <label for="user-password-confirm" class="password-label">Confirmer mot de passe</label>
                        <input type="password" class="password-input" id="user-password-confirm" name="user-password-confirm">
                        <span class="password-reveal" data-reveal="user-password-confirm">
                    <span class="icon-visit-profile"><span class="path1"></span><span class="path2"></span></span>
                  </span>
                    </div>
                </div>
                <input type="submit" class="submit-input" id="form-submit" value="Valider" />
            </form:form>
            <a href="Login" class="basic-link signin-question">Vous êtes déja membre ? Se connecter</a>
        </div>
    </main>

    <script src="${pageContext.request.contextPath}/assets/javascript/external/jQuery/jquery-2.2.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/javascript/external/jQuery/jquery-ui.js"></script>

    <script src="${pageContext.request.contextPath}/assets/javascript/signin.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/javascript/notifications.js" type="text/javascript"></script>
    </body>
</html>