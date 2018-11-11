<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="user">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="email">Email address</label>
                    <form:input type="email" class="form-control" path="email" id="email" value="${user.email}" placeholder="Email"/>
                    <form:errors path="email" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <form:input type="password" class="form-control" path="password" id="password" value="${user.password}" placeholder="Password"/>
                    <form:errors path="password" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="matchingPassword">Password</label>
                    <form:input type="password" class="form-control" path="matchingPassword" id="matchingPassword" value="${user.matchingPassword}" placeholder="Password"/>
                    <form:errors path="matchingPassword" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <form:input type="text" class="form-control" path="name" id="name" value="${user.name}" placeholder="Name"/>
                    <form:errors path="name" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="lastName">Last name</label>
                    <form:input type="text" class="form-control" path="lastName" id="lastName" value="${user.lastName}" placeholder="Last name"/>
                    <form:errors path="lastName" cssClass="text-danger" />
                </div>
                <button type="submit" class="btn btn-primary">Sign up</button>
            </form:form>
        </div>
    </div>
</div>