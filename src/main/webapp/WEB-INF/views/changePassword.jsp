<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">

    <h3 class="my-4">Zmień hasło</h3>

    <c:if test="${edited}">
        Zmieniono. <a href="${pageContext.request.contextPath}/user/${auth.getUserInfo().user.id}">Powrót</a>
    </c:if>

    <c:if test="${not edited}">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                    ${errorMsg}<br />
            </div>
        </c:if>

        <form  method="POST">
            <div class="form-group">
                <label for="oldPassword">Stare hasło</label>
                <input type="password" class="form-control" name="oldPassword" id="oldPassword" value="" placeholder=""/>
            </div>
            <div class="form-group">
                <label for="newPassword">Nowe hasło</label>
                <input type="password" class="form-control" name="newPassword" id="newPassword" value="" placeholder=""/>
            </div>
            <div class="form-group">
                <label for="newPassword2">Powtórz hasło</label>
                <input type="password" class="form-control" name="newPassword2" id="newPassword2" value="" placeholder=""/>
            </div>

            <button type="submit" class="btn btn-primary">Zmień</button>
        </form>
    </c:if>
</div>