<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">

            <c:if test="${added}">
                Added. See <a href="${authorPath}">${authorName}</a> or <a href="${pageContext.request.contextPath}/author/add">Add new author</a>

            </c:if>

            <c:if test="${not added}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="author">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="firstname">First name</label>
                    <form:input type="text" class="form-control" path="firstName" id="firstname" value="${author.firstName}" placeholder="First name"/>
                    <form:errors path="firstName" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="lastname">Last name</label>
                    <form:input type="text" class="form-control" path="lastName" id="lastname" value="${author.lastName}" placeholder="Last name"/>
                    <form:errors path="lastName" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="dateOfBirth">Date of birth</label>
                    <form:input type="text" class="form-control" path="dateOfBirth" id="dateOfBirth" value="${author.dateOfBirth}" placeholder="yyyy-mm-dd"/>
                    <form:errors path="dateOfBirth" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <form:textarea class="form-control" path="description" value="${author.description}" placeholder="Description"></form:textarea>
                    <form:errors path="description" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="photo">Photo</label>
                    <form:input type="text" class="form-control" path="photo" id="photo" value="${author.photo}" placeholder="Photo"/>
                    <form:errors path="photo" cssClass="text-danger" />
                </div>
                <button type="submit" class="btn btn-primary">Add</button>
            </form:form>
            </c:if>
        </div>
    </div>
</div>