<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">

            <c:if test="${added}">
                Added. See <a href="${tagPath}">${tagName}</a> or <a href="${pageContext.request.contextPath}/tag/add">Add new tag</a>

            </c:if>

            <c:if test="${not added}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="tag">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="name">Tag name</label>
                    <form:input type="text" class="form-control" path="name" id="name" value="${tag.name}" placeholder="Tag name"/>
                    <form:errors path="name" cssClass="text-danger" />
                </div>

                <button type="submit" class="btn btn-primary">Add</button>
            </form:form>
            </c:if>
        </div>
    </div>
</div>