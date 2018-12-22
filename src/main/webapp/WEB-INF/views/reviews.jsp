<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>


<div class="col-md-12">
    <h3 class="my-4">Lista recenzji</h3>

    <section class="book_review">
        <div class="review_wrap">

            <c:forEach items="${reviews}" var="review" varStatus="tagStatus">
                    <div class="review">
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
            </c:forEach>


            <nav class="page-nav" aria-label="Page navigation">
                <ul class="pagination justify-content-center">

<%--Prev--%>
<c:url value="/reviews" var="prev">
    <c:param name="page" value="${page-1}"/>
    <c:if test="${not empty sort}">
        <c:param name="sort" value="${sort}"/>
    </c:if>
</c:url>
<c:choose>
    <c:when test="${page > 1}">
        <li class="page-item ">
            <a class="page-link" href="<c:out value="${prev}" />" href="<c:out value="${prev}" />">Poprzednia</a>
        </li>
    </c:when>
    <c:otherwise>
        <li class="page-item disabled">
            <a class="page-link" href="#" href="#">Poprzednia</a>
        </li>
    </c:otherwise>
</c:choose>


<c:choose>
    <c:when test="${page - 3 >= 3}">
        <c:url value="/reviews" var="first">
            <c:param name="page" value="1"/>
            <c:if test="${not empty sort}">
                <c:param name="sort" value="${sort}"/>
            </c:if>
        </c:url>
        <li class="page-item"><a class="page-link" href="<c:out value="${first}" />">1</a></li>
        <li class="page-item"><a class="page-link" href="#">. .</a></li>

        <c:forEach begin="${page - 3}" end="${page - 1}" step="1" varStatus="i">
            <c:url value="/reviews" var="url">
                <c:param name="page" value="${i.index}"/>
                <c:if test="${not empty sort}">
                    <c:param name="sort" value="${sort}"/>
                </c:if>
            </c:url>
            <li class="page-item"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach begin="1" end="${page - 1 >= 1 ? page -1 : 0}" step="1" varStatus="i">
            <c:url value="/reviews" var="url">
                <c:param name="page" value="${i.index}"/>
                <c:if test="${not empty sort}">
                    <c:param name="sort" value="${sort}"/>
                </c:if>
            </c:url>
            <li class="page-item"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

        </c:forEach>
    </c:otherwise>
</c:choose>
    <li class="page-item active"><a class="page-link" href="#">${page} <span class="sr-only">(current)</span></a></li>

<c:choose>
    <c:when test="${page + 3 <= totalPage - 2}">
        <c:forEach begin="${page + 1}" end="${page + 3}" step="1" varStatus="i">
            <c:url value="/reviews" var="url">
                <c:param name="page" value="${i.index}"/>
                <c:if test="${not empty sort}">
                    <c:param name="sort" value="${sort}"/>
                </c:if>
            </c:url>
            <li class="page-item"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

        </c:forEach>
        <li class="page-item"><a class="page-link" href="#">. .</a></li>
        <c:url value="/reviews" var="last">
            <c:param name="page" value="${totalPage}"/>
            <c:if test="${not empty sort}">
                <c:param name="sort" value="${sort}"/>
            </c:if>
        </c:url>
        <li class="page-item"><a class="page-link" href="<c:out value="${last}" />">${totalPage}</a></li>

    </c:when>
    <c:otherwise>
        <c:forEach begin="${page + 1}" end="${totalPage}" step="1" varStatus="i">
            <c:url value="/reviews" var="url">
                <c:param name="page" value="${i.index}"/>
                <c:if test="${not empty sort}">
                    <c:param name="sort" value="${sort}"/>
                </c:if>
            </c:url>
            <li class="page-item"><a class="page-link" href="<c:out value="${url}" />">${i.index}</a></li>

        </c:forEach>
    </c:otherwise>
</c:choose>

<%--Next--%>
<c:url value="/reviews" var="next">
    <c:param name="page" value="${page+1}"/>
    <c:if test="${not empty sort}">
        <c:param name="sort" value="${sort}"/>
    </c:if>
</c:url>
<c:choose>
    <c:when test="${page + 1 <= totalPage}">
        <li class="page-item ">
            <a class="page-link" href="<c:out value="${next}" />" href="<c:out value="${next}" />">Następna</a>
        </li>
    </c:when>
    <c:otherwise>
        <li class="page-item disabled">
            <a class="page-link" href="#" href="#">Następna</a>
        </li>
    </c:otherwise>
</c:choose>

                </ul>
            </nav>

        </div>
    </section>



</div>