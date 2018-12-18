<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Autor: ${author.getDisplayName()}</h3>

            Imię: ${author.firstName} </br>
            Nazwisko: ${author.lastName} </br>

    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/authors/edit/${author.id}">Edytuj autora</a>

    </c:if>

    </br>
    </br>
    Książki: </br>
    <c:forEach items="${author.books}" var="book" varStatus="tagStatus">
        <a href="/mybooks/book/${book.slug}">${book.title}</a>
        </br>
    </c:forEach>

</div>