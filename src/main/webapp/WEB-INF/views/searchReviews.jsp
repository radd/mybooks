<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div class="col-md-12">
    <h3 class="my-4">Szukaj recenzji</h3>

    <form method="get" action="<c:url value="/reviews/search" /> " class="form-inline" >
        <label class="sr-only" for="searchTerm">Search</label>
        <input type="text" style="flex: 1;" class="form-control mb-2 mr-sm-2"
               name="searchTerm" id="searchTerm" placeholder="Szukaj..."
               <c:if test="${not empty searchTerm}">value="${searchTerm}"</c:if>
        >

        <button type="submit" class="btn btn-primary mb-2">Szukaj</button>
    </form>

    <c:if test="${search}">
        <h5 class="my-4">Znaleziono: ${resultCount} wyników</h5>

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
        <br />
        <c:if test="${resultCount > 0}">
            <tiles:insertAttribute name="pagination" />
        </c:if>
    </c:if>

</div>