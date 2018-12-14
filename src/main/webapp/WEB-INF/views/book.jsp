<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">


            Book title: ${book.title} </br>
            Book slug: ${book.slug} </br>
            Book desc: ${book.description} </br>
            Book category: ${book.category.name} </br>
            <c:forEach items="${book.authors}" var="author" varStatus="tagStatus">
                Author: ${author.getDisplayName()} </br>
            </c:forEach>


        </div>
    </div>
</div>