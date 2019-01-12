<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div class="col-md-12">
    <h3 class="my-4">Szukaj książki</h3>

    <form method="get" action="<c:url value="/books/search" /> " class="form-inline" >
        <label class="sr-only" for="searchTerm">Search</label>
        <input type="text" style="flex: 1;" class="form-control mb-2 mr-sm-2"
               name="searchTerm" id="searchTerm" placeholder="Szukaj..."
               <c:if test="${not empty searchTerm}">value="${searchTerm}"</c:if>
        >

        <button type="submit" class="btn btn-primary mb-2">Szukaj</button>
    </form>

    <c:if test="${search}">
        <h5 class="my-4">Znaleziono: ${resultCount} wyników</h5>

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
        <c:if test="${resultCount > 0}">
            <tiles:insertAttribute name="pagination" />
        </c:if>
    </c:if>

</div>