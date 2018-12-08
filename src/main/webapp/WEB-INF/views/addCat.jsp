<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">

            <c:if test="${added}">
                Added. See <a href="${catPath}">${catName}</a> or <a href="${pageContext.request.contextPath}/cat/add">Add new category</a>

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
                        <label for="name">Category name</label>
                        <form:input type="text" class="form-control" path="name" id="name" value="${cat.name}" placeholder="Category name"/>
                        <form:errors path="name" cssClass="text-danger" />
                    </div>
                     <div class="form-group">
                         <label for="cats">Parent</label>
                         <form:select path="parent" id="cats" class="form-control">
                             <form:option value="0" label="None" />
                             <form:options items="${cats}" itemValue="id" itemLabel="name" />
                         </form:select>
                    </div>



                    <button type="submit" class="btn btn-primary">Add</button>
                </form:form>
            </c:if>
        </div>
    </div>
</div>