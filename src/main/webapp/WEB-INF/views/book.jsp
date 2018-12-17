<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Książka: ${book.title}</h3>

            <img class="book-cover" src="${book.cover}"/> </br>
            Tytuł: ${book.title} </br>
            Kategoria: ${book.category.name} </br>
            Opis: ${book.description} </br>
            Autor:
            <c:forEach items="${book.authors}" var="author" varStatus="tagStatus">
                ${author.getDisplayName()},
            </c:forEach>
            </br>
    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/books/edit/${book.id}">Edytuj książkę</a>

    </c:if>
    </br>
    <c:if test="${auth.isLoggedIn()}">
        <a href="/mybooks/reviews/${book.id}/add">Dodaj recenzję</a>

    </c:if>

</div>