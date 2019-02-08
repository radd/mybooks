<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <h3 class="my-4">Rejestracja</h3>

    <c:if test="${added}">
        Zarejestrowano. <a href="${pageContext.request.contextPath}/login">Zaloguj się</a>

    </c:if>
    <c:if test="${not added}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="user">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="email">Email</label>
                    <form:input type="email" class="form-control" path="email" id="email" value="${user.email}" placeholder="Email"/>
                    <form:errors path="email" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="password">Hasło</label>
                    <form:input type="password" class="form-control" path="password" id="password" value="${user.password}" placeholder="Password"/>
                    <form:errors path="password" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="matchingPassword">Powtórz hasło</label>
                    <form:input type="password" class="form-control" path="matchingPassword" id="matchingPassword" value="${user.matchingPassword}" placeholder="Password"/>
                    <form:errors path="matchingPassword" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="name">Imię</label>
                    <form:input type="text" class="form-control" path="name" id="name" value="${user.name}" placeholder="Imię"/>
                    <form:errors path="name" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="lastName">Nazwisko</label>
                    <form:input type="text" class="form-control" path="lastName" id="lastName" value="${user.lastName}" placeholder="Nazwisko"/>
                    <form:errors path="lastName" cssClass="text-danger" />
                </div>
                <button type="submit" class="btn btn-primary">Zarejestruj się</button>
            </form:form>
    </c:if>
</div>