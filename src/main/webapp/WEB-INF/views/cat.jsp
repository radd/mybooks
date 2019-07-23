<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>


<div class="col-md-12">
    <h3 class="my-4">Lista książek w kategorii: ${cat.name}</h3>
    <c:forEach items="${books}" var="book" varStatus="tagStatus">
        <a href="/mybooks/book/${book.slug}">${book.title}</a>
        </br>
    </c:forEach>

</div>