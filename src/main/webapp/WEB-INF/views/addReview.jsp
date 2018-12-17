<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">

    <c:choose>
        <c:when test="${edit}">
            <h3 class="my-4">Edytuj recenzję: ${reviewTitle}</h3>
        </c:when>
        <c:otherwise>
            <h3 class="my-4">Dodaj recenzję książki "${book.title}"</h3>
        </c:otherwise>
    </c:choose>
            <c:if test="${added}">
                Dodano. Zobacz <a href="/mybooks/review/${reviewPath}">${reviewTitle}</a> lub <a href="${pageContext.request.contextPath}/reviews/${bookID}/add">Dodaj nową recenzję</a>

            </c:if>

            <c:if test="${edited}">
                Zapisno. Zobacz <a href="/mybooks/review/${reviewPath}">${reviewTitle}</a>

            </c:if>

            <c:if test="${not added and not edited}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="review">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="title">Tytuł</label>
                    <form:input type="text" class="form-control" path="title" id="title" value="${review.title}" placeholder="Tytuł"/>
                    <form:errors path="title" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="content">Treść recenzji</label>
                    <form:textarea class="form-control" path="content" value="${review.content}" placeholder="Treść" rows="10"></form:textarea>
                    <form:errors path="content" cssClass="text-danger" />
                </div>
                <button type="submit" class="btn btn-primary">Zapisz</button>
            </form:form>
            </c:if>
</div>