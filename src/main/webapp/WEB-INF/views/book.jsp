<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Książka: ${book.title}</h3>

            <img class="book-cover" src="${book.cover}"/> </br>
            Tytuł: ${book.title} </br>
    Kategoria: <a href="/mybooks/cat/${book.category.slug}">${book.category.name}</a> </br>
    Tagi:
    <c:forEach items="${book.bookTags}" var="tag" varStatus="tagStatus">
        <a href="/mybooks/tag/${tag.slug}">${tag.name}</a>,
    </c:forEach><br />
    Ocena: ${book.stars} <br />
    Twoja ocena:
    <select id="ratingBook" class="form-control">
        <option value="0">Oceń</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
    </select>
    <input type="text" id="bookID" value="${book.id}" autocomplete="false" hidden="hidden" />
    </br></br>
            Opis: ${book.description} </br></br>
            Autor:
            <c:forEach items="${book.authors}" var="author" varStatus="tagStatus">
    <a href="/mybooks/author/${author.slug}">${author.getDisplayName()}</a>,
            </c:forEach>
            </br>

    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/books/edit/${book.id}">Edytuj książkę</a>

    </c:if>
    </br>
    <c:if test="${auth.isLoggedIn()}">
        <a href="/mybooks/reviews/${book.id}/add">Dodaj recenzję</a>

    </c:if>
    </br>
    </br>
    Recenzje:  </br>
    <c:forEach items="${book.reviews}" var="review" varStatus="tagStatus">
        <a href="/mybooks/review/${review.slug}">${review.title}</a>
        </br>
    </c:forEach>

</div>

<script type="text/javascript">
    $(function() {

        $('#ratingBook').on("click", "option", function () {
            var ratingSelected = $(this);
            var stars = ratingSelected.val();
            var bookID = $("#bookID").val();

            if (stars != 0) {
                var rating = {
                    stars: stars,
                    bookID: bookID
                }

                $.ajax({
                    url: 'http://localhost:8080/mybooks/api/rating/add',
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(rating)
                }).done(addRatingRes)
                    .fail(function(e) {
                            console.log("ERROR: ", e);
                        }
                    );

            }

        });

        function addRatingRes (res) {
            console.log(res);
            if(res.status === "done"){
                console.log("done");


            }

        }


    })
</script>