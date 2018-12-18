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
        </div>
    </section>



</div>