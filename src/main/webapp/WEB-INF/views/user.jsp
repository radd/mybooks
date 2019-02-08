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
    Napisane recenzje: </br></br>
    <section class="book_review">
        <div class="review_wrap">

            <c:forEach items="${reviews}" var="review" varStatus="tagStatus">
                <div class="review">
                    <div class="review-top">
                        <a href="/mybooks/review/${review.slug}" class="review_info">
                            <h4>${review.title}</h4>
                            <div class="description">
                                <p>${review.getShortContent()}</p>
                            </div>
                        </a>
                        <a href="/mybooks/book/${review.book.slug}" class="review_author">
                            <div class="avatar">
                                <img alt="avatar" src="${review.getBook().getCover()}" />
                            </div>
                        </a>
                    </div>
                    <div class="review-bottom">
                        <div class="review-author">Autor: <a href="/mybooks/user/${review.user.id}">${review.user.getDisplayName()}</a></div>
                        <div class="review-book-info">Książka: "<a href="/mybooks/book/${review.book.slug}">${review.book.title}</a>" Autor:

                            <c:forEach items="${review.book.authors}" var="author" varStatus="status">
                                <c:if test="${status.index == 0}">
                                    <a href="/mybooks/author/${author.slug}">${author.getDisplayName()}</a>
                                </c:if>
                                <c:if test="${status.index > 0}">
                                    , <a href="/mybooks/author/${author.slug}"> ${author.getDisplayName()}</a>
                                </c:if>


                            </c:forEach>

                        </div>
                    </div>
                </div>
            </c:forEach>



        </div>
    </section>
    </br>
    Ostatnio ocenione książki: </br></br>

    <section class="books">
        <div class="book_main_list">
            <c:forEach items="${ratings}" var="rating" varStatus="tagStatus">
                <c:set var="book" value="${rating.book}"></c:set>
                <div class="book">
                    <a href="/mybooks/book/${book.slug}">
                        <img class="cover" alt="okładka" src=" ${book.cover}" />
                        <div class="info">
                            <h5> ${book.title}</h5>
                            <div class="author"><c:forEach items="${book.authors}" var="author" varStatus="status"><c:if test="${status.index == 0}">${author.getDisplayName()}</c:if><c:if test="${status.index > 0}">, ${author.getDisplayName()}</c:if></c:forEach>
                            </div>
                                <div class="stars">${rating.stars}</div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    </br>
    Ostatnio przeczytane książki: </br></br>

    <section class="books">
        <div class="book_main_list">
            <c:forEach items="${voteRead}" var="vote" varStatus="tagStatus">
                <c:set var="book" value="${vote.book}"></c:set>
                <div class="book">
                    <a href="/mybooks/book/${book.slug}">
                        <img class="cover" alt="okładka" src=" ${book.cover}" />
                        <div class="info">
                            <h5> ${book.title}</h5>
                            <div class="author"><c:forEach items="${book.authors}" var="author" varStatus="status"><c:if test="${status.index == 0}">${author.getDisplayName()}</c:if><c:if test="${status.index > 0}">, ${author.getDisplayName()}</c:if></c:forEach>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    </br>
    Aktualnie czytane książki: </br></br>

    <section class="books">
        <div class="book_main_list">
            <c:forEach items="${voteReading}" var="vote" varStatus="tagStatus">
                <c:set var="book" value="${vote.book}"></c:set>
                <div class="book">
                    <a href="/mybooks/book/${book.slug}">
                        <img class="cover" alt="okładka" src=" ${book.cover}" />
                        <div class="info">
                            <h5> ${book.title}</h5>
                            <div class="author"><c:forEach items="${book.authors}" var="author" varStatus="status"><c:if test="${status.index == 0}">${author.getDisplayName()}</c:if><c:if test="${status.index > 0}">, ${author.getDisplayName()}</c:if></c:forEach>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    </br>
    Chce przeczytać: </br></br>

    <section class="books">
        <div class="book_main_list">
            <c:forEach items="${voteWantRead}" var="vote" varStatus="tagStatus">
                <c:set var="book" value="${vote.book}"></c:set>
                <div class="book">
                    <a href="/mybooks/book/${book.slug}">
                        <img class="cover" alt="okładka" src=" ${book.cover}" />
                        <div class="info">
                            <h5> ${book.title}</h5>
                            <div class="author"><c:forEach items="${book.authors}" var="author" varStatus="status"><c:if test="${status.index == 0}">${author.getDisplayName()}</c:if><c:if test="${status.index > 0}">, ${author.getDisplayName()}</c:if></c:forEach>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>
</div>