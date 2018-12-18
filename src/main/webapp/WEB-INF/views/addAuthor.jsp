<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>


<h3 class="my-4">Dodaj autora</h3>

            <c:if test="${added}">
                Dodano. Zobacz <a href="${pageContext.request.contextPath}/author/${authorPath}">${authorName}</a> lub <a href="${pageContext.request.contextPath}/authors/add">Dodaj nowego autora</a>

            </c:if>

            <c:if test="${edited}">
                Zapisano. Zobacz <a href="${pageContext.request.contextPath}/author/${authorPath}">${authorName}</a>

            </c:if>

            <c:if test="${not added and not edited}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="author">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="firstname">Imię</label>
                    <form:input type="text" class="form-control" path="firstName" id="firstname" value="${author.firstName}" placeholder="Imię"/>
                    <form:errors path="firstName" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="lastname">Nazwisko</label>
                    <form:input type="text" class="form-control" path="lastName" id="lastname" value="${author.lastName}" placeholder="Nazwisko"/>
                    <form:errors path="lastName" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="dateOfBirth">Data urodzenia</label>
                    <form:input type="text" class="form-control" path="dateOfBirth" id="dateOfBirth" value="${author.dateOfBirth}" placeholder="np. 1990-08-03"/>
                    <form:errors path="dateOfBirth" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="description">Opis</label>
                    <form:textarea class="form-control" path="description" value="${author.description}" placeholder="Opis"></form:textarea>
                    <form:errors path="description" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="photo">Zdjęcie</label>
                    <form:input type="text" class="form-control" path="photo" id="photo" value="${author.photo}" placeholder="Zdjęcie"/>
                    <form:errors path="photo" cssClass="text-danger" />
                </div>
                <button type="submit" class="btn btn-primary">Zapisz</button>
            </form:form>
            </c:if>
