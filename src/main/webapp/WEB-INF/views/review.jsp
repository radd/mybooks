<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Recenzja: ${review.title}</h3>

            <img class="book-cover" src="${book.cover}"/> </br>
            Tytuł: ${review.title} </br>
            Data dodania: ${review.getDate()} </br>
            Autor: ${review.user.getDisplayName()} </br> </br>
            Treść: ${review.content} </br></br>
    Książka:  <a href="/mybooks/book/${review.book.slug}">${review.book.title}</a> </br>
    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/reviews/edit/${review.id}">Edytuj recenzję</a>

    </c:if>
    </br>


</div>