<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>


<div class="col-md-8">
    <h3 class="my-4">Dodaj kategorię</h3>
            <c:if test="${added}">
                Dodano. Zobacz <a href="${pageContext.request.contextPath}/cat/${catPath}">${catName}</a> lub <a href="${pageContext.request.contextPath}/cat/add">Dodaj nową kategorię</a>

            </c:if>

            <c:if test="${not added}">
                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-danger">
                            ${errorMsg}<br />
                    </div>
                </c:if>

                <form:form modelAttribute="cat">
                    <form:errors path="*" cssClass="alert alert-danger" element="div" />
                    <div class="form-group">
                        <label for="name">Nazwa kategorii</label>
                        <form:input type="text" class="form-control" path="name" id="name" value="${cat.name}" placeholder="Nazwa kategorii"/>
                        <form:errors path="name" cssClass="text-danger" />
                    </div>
                     <div class="form-group">
                         <label for="cats">Rodzic</label>
                         <form:select path="parent" id="cats" class="form-control">
                             <form:option value="0" label="Brak" />
                             <form:options items="${cats}" itemValue="id" itemLabel="name" />
                         </form:select>
                    </div>



                    <button type="submit" class="btn btn-primary">Zapisz</button>
                </form:form>
            </c:if>
</div>