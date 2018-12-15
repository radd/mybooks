<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">

            <c:if test="${added}">
                Added. See <a href="${reviewPath}">${reviewTitle}</a> or <a href="${pageContext.request.contextPath}/review/${bookID}/add">Add new review</a>

            </c:if>

            <c:if test="${not added}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="review">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="title">Title</label>
                    <form:input type="text" class="form-control" path="title" id="title" value="${review.title}" placeholder="Title"/>
                    <form:errors path="title" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="content">Review</label>
                    <form:textarea class="form-control" path="content" value="${review.content}" placeholder="Review content"></form:textarea>
                    <form:errors path="content" cssClass="text-danger" />
                </div>
                <button type="submit" class="btn btn-primary">Add</button>
            </form:form>
            </c:if>
        </div>
    </div>
</div>