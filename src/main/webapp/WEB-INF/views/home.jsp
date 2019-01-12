<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div class="col-md-12">
        <h3 class="my-4">Popularne książki</h3>

        <section class="books">
                <div class="book_main_list">

                        <c:forEach items="${books}" var="book" varStatus="tagStatus">
                                <div class="book">
                                        <a href="/mybooks/book/${book.slug}">
                                                <img class="cover" alt="okładka" src=" ${book.cover}" />
                                                <div class="info">
                                                        <h5> ${book.title}</h5>
                                                        <div class="author"><c:forEach items="${book.authors}" var="author" varStatus="status"><c:if test="${status.index == 0}">${author.getDisplayName()}</c:if><c:if test="${status.index > 0}">, ${author.getDisplayName()}</c:if></c:forEach>
                                                        </div>
                                                                <%-- <div class="stars">${book.stars}</div>--%>
                                                </div>
                                        </a>
                                </div>
                        </c:forEach>



                </div>
        </section>
        <br />

        <h3 class="my-4">Ostatnie recenzje</h3>

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

</div>