<%@ page pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>Avoté - Dashboard</title>
        <link rel="shortcut icon" id="favicon-cntr" href="${pageContext.request.contextPath}/assets/images/favicon.ico">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/scss/fonts/icons/font.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css" type="text/css">
    </head>
    <body>
        <header class="header-cntr">
            <div class="banner header-banner" id="profile-nav" data-role="profile-nav-cntr">
                <div class="header-banner__profile">
                    <span class="header-banner__profile-img">
                        <c:set var="lastName" value="${fn:substring(user.lastName, 0, 1)}"/>
                        <c:set var="firstName" value="${fn:substring(user.firstName, 0, 1)}"/>${lastName}${firstName}
                    </span>
                    <span class="header-banner__profile_name">${user.lastName} ${user.firstName}</span>
                </div>
                <span class="icon-arrow"></span>
                <%--<ul class="header-banner__navigation" id="profile-nav-cntr">
                    <li class="profile-nav__item"><a class="basic-link" href="#"><span class="icon-visit-profile"><span class="path1"></span><span class="path2"></span></span></span>Visiter votre profil</a></li>
                    <li class="profile-nav__item"><a class="basic-link" href="#"><span class="icon-edit-profile"></span>Editer votre profil</a></li>
                    <li class="profile-nav__item"><a class="basic-link" href="#"><span class="icon-add-profile"></span>Inviter une personne</a></li>
                </ul>--%>
            </div>
            <div class="header">
                <ul class="header-navigation">
                    <li class="header-navigation__item is-active" data-section="home-section" title="Accueil">
                        <a href="#section=home" class="basic-link">
                            <span class="icon-home"></span>
                            <b>ACCUEIL</b>
                        </a>
                    </li>
                    <li class="header-navigation__item" data-section="results-section" title="Résultats">
                        <a href="#section=results" class="basic-link">
                            <span class="icon-results"></span>
                            <b>RÉSULTATS</b>
                        </a>
                    </li>
                    <li class="header-navigation__item" data-section="polls-section" title="Mes scrutins">
                        <a href="#section=polls" class="basic-link">
                            <span class="icon-vote"></span>
                            <b>MES SCRUTINS</b>
                        </a>
                    </li>
                    <li class="header-navigation__item" data-section="profile-section" title="Profil">
                        <a href="#section=profile" class="basic-link">
                            <span class="icon-user"></span>
                            <b>PROFIL</b>
                        </a>
                    </li>
                </ul>
            </div>
        </header>

        <main class="body-cntr">
            <div class="banner body-banner">
                <a href="." class="basic-link">
                    <span class="icon-Logo"></span>
                </a>
                <div class="body-banner__icons">
                    <div class="icon-notif floating-nav-cntr" data-role="notification-nav-cntr">
                        <div class="click-handler"></div>
                        <i class="notif-number">4</i>
                        <ul id="notification-nav-cntr" class="floating-nav">
                            <%--<li class="floating-nav__item"><a href="#" class="basic-link">Les résultats deu Scrutin <b
                                    class="bold-name">Couleur des Cartes étudiantes</b> sont disponibles.</a></li>
                            <li class="floating-nav__item"><a href="#" class="basic-link"><b>115</b> personnes o    épondu au
                                sondage <b
                                        class="bold-name">Couleur des Cartes étudiantes</b>.</a></li>
                            <li class="floating-nav__item"><a href="#" class="basic-link">Le sondage <b class="bold-name">Couleur
                                des Cartes étudiantes</b> a été mis en ligne.</a></li>
                            <li class="floating-nav__item"><a href="#" class="basic-link"><b>Laura</b> a modifié le sondage <b
                                    class="bold-name">Couleur des Cartes étudiantes</b>.</a></li>--%>
                        </ul>
                    </div>
                    <div class="icon-pref floating-nav-cntr" data-role="preferences-nav-cntr">
                        <div class="click-handler"></div>
                        <ul id="preferences-nav-cntr" class="floating-nav preferences-nav">
                            <li class="floating-nav__item" id="minify-header"><span class="toggle-btn"><i
                                    class="slider"></i></span> Raccourcir le menu
                            </li>
                            <li class="floating-nav__item" id="switch-mode"><span class="toggle-btn"><i
                                    class="slider"></i></span> Mode nuit
                            </li>
                            <li class="floating-nav__item" id="auto-refresh"><span class="toggle-btn"><i
                                    class="slider"></i></span> Actualiser automatiquement
                            </li>
                            <%--<li class="floating-nav__item"><a href="#" class="basic-link">Parametres</a></li>--%>
                            <li class="floating-nav__item padding-less">
                                <form:form action="logout" method="post">
                                    <input type="submit" class="fake-input" name="logout" value="Deconnexion">
                                </form:form></li>
                        </ul>
                    </div>
                </div>
            </div>

            <%@include file="infos.jsp" %>

            <div class="body">
                <%@include file="content-welcome.jsp" %>
                <%@include file="content-poll.jsp" %>
                <%@include file="content-results.jsp" %>
                <%@include file="content-profil.jsp" %>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/assets/javascript/external/jQuery/jquery-2.2.4.min.js"
                type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/external/jQuery/jquery-ui.js"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/external/Clipboard/clipboard.min.js"></script>

        <script src="${pageContext.request.contextPath}/assets/javascript/functions.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/notifications.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/Pie.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/Polls.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/Polls-results.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/Polls-welcome.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/PopUp.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/javascript/Profile.js" type="text/javascript"></script>
    </body>
</html>
