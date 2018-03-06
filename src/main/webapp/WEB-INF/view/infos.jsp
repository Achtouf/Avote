<%@ page import="fr.lyon1.avote.filters.Info" %>
<%@ page import="java.util.ArrayList" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="body-notification-cntr">
    <c:forEach var="info" items="${infos}">
        <div class="body-notification ${info.type}-notif">
            <a href="#" class="close" data-dismiss="alert" title="close">×</a>
            <c:if test="${not empty info.title}">
                <strong>${info.title} :</strong>
            </c:if>
            <p>${info.message}</p>
        </div>
        <%--<c:remove var='message'/>--%>
    </c:forEach>
    <%
        session.setAttribute("infos", new ArrayList<Info>());
    %>
</div>

<%--
    <c:forEach items="${map}" var="entry">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>

   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
--%>
<%--<div class="body-notification-cntr">
    <c:forEach var="message" items="${infos}">
        <c:if test="${not empty message}">
            <div class="body-notification ${message}-notif">
                <a href="#" class="close" data-dismiss="alert" title="close">×</a>
                <strong>${message} :</strong>
                <p>${message}</p>
            </div>
            <c:remove var='message'/>
        </c:if>
    </c:forEach>
</div>--%>