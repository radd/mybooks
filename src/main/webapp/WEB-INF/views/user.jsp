<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Użytkownik: ${user.getDisplayName()}</h3>

            Imię: ${user.firstName} </br>
            Nazwisko: ${user.lastName} </br>
            Wyświetlana nazwa: ${user.displayName} </br>
            Email: ${user.email} </br>

    <c:if test="${auth.getUserInfo().isAdminOrModerator() or auth.getUserInfo().user.id == user.id}">
        Data rejestracji: ${user.getDate()} </br>
    </c:if>
            Opis:  </br> ${user.description} </br>

    <c:choose>
        <c:when test="${auth.getUserInfo().isAdminOrModerator()}">
            <a href="/mybooks/user/${user.id}/edit">Edytuj</a>
        </c:when>
        <c:when test="${auth.getUserInfo().user.id == user.id}">
            <a href="/mybooks/user/${user.id}/edit">Edytuj</a>

        </c:when>
    </c:choose>
    </br>
    <c:if test="${auth.getUserInfo().user.id == user.id}">
        <a href="/mybooks/user/change-password">Zmień hasło</a>
    </c:if>
    </br>

    </br>
    Napisane recenzje: </br>
    <c:forEach items="${reviews}" var="review" varStatus="tagStatus">
        <a href="/mybooks/review/${review.slug}">${review.title}</a>
        </br>
    </c:forEach>

    </br>
    </br>
    Ostatnio ocenione książki: </br>
    <c:forEach items="${ratings}" var="rating" varStatus="tagStatus">
        <a href="/mybooks/book/${rating.book.slug}">${rating.book.title} || ${rating.stars}</a>
        </br>
    </c:forEach>

</div>