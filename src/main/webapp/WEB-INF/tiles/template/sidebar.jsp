<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<c:if test="${auth.isLoggedIn()}">
    <div class="card my-4">
        <h5 class="card-header">User panel</h5>
        <div class="card-body">
            <ul class="list-unstyled mb-0">
                <li>
                        ${auth.getUserInfo().getUser().getEmail()}
                </li>
                <li>
                    <a href="<c:url value="/logout" />">Wyloguj się</a>
                </li>
            </ul>

        </div>
    </div>
</c:if>


<c:if test="${auth.getUserInfo().isAdmin()}">
	<div class="card my-4">
		<h5 class="card-header">Admin panel</h5>
		<div class="card-body">
			<ul class="sidebar-list list-unstyled mb-0">
				<li>
					<a href="<c:url value="/cat/add" />">Dodaj kategorię</a>
				</li>

			</ul>

		</div>
	</div>
</c:if>



<div class="card my-4">
	<h5 class="card-header">Menu</h5>
	<div class="card-body">

		<ul class="sidebar-list list-unstyled mb-0">
			<c:if test="${not auth.isLoggedIn()}">
				<li>
					<a href="<c:url value="/login" />">Logowanie</a>
				</li>
				<li>
					<a href="<c:url value="/signup" />">Rejestracja</a>
				</li>
			</c:if>
			<li>
				<a href="<c:url value="/books" />">Książki</a>
				<ul>
					<li>
						<a href="<c:url value="/books/add" />">Dodaj książkę</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="<c:url value="/reviews" />">Recenzje</a>
			</li>
			<li>
				<a href="<c:url value="/authors" />">Autorzy</a>
				<ul>
					<li>
						<a href="<c:url value="/authors/add" />">Dodaj autora</a>
					</li>
				</ul>
			</li>
		</ul>

	</div>
</div>

<!-- Categories Widget -->
<div class="card my-4">
    <h5 class="card-header">Kategorie</h5>
    <div class="card-body">

        <ul class="sidebar-list list-unstyled mb-0">
            ${categoryList}
        </ul>

    </div>
</div>




<%--

<!-- Search Widget -->
<div class="card my-4">
	<h5 class="card-header">Search</h5>
	<div class="card-body">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Search for...">
			<span class="input-group-btn">
	  <button class="btn btn-secondary" type="button">Go!</button>
	</span>
		</div>
	</div>
</div>



<!-- Side Widget -->
<div class="card my-4">
	<h5 class="card-header">Side Widget</h5>
	<div class="card-body">
		You can put anything you want inside of these side widgets. They are easy to use, and feature the new Bootstrap 4 card containers!
	</div>
</div>
--%>


