<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<div class="col-md-12">
    <h3 class="my-4">Lista autorów</h3>
    <c:forEach items="${authors}" var="author" varStatus="tagStatus">
        <a href="/mybooks/author/${author.slug}">${author.getDisplayName()}</a>
        </br>
    </c:forEach>

    <tiles:insertAttribute name="pagination" />


</div>