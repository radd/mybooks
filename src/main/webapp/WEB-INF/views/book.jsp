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
    Ocena: ${book.stars}/6 <br />
    Liczba ocen: ${book.ratingCount}

<c:if test="${auth.isLoggedIn()}">
    <br />
    Twoja ocena:
    <c:if test="${not empty rating}">
    <span id="userStars">${rating.stars}</span>  <br />
    </c:if>

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
    <br />

    <div class="vote-box">
    <button type="button" id="read" class="btn btn-outline-success">Przeczytałem</button>
    <button type="button" id="reading" class="btn btn-outline-success">Czytam</button>
    <button type="button" id="wantRead" class="btn btn-outline-success">Chcę przeczytać</button>
    </div>



</c:if>

    </br></br>
            Opis: ${book.description} </br></br>
            Autor:
            <c:forEach items="${book.authors}" var="author" varStatus="tagStatus">
    <a href="/mybooks/author/${author.slug}">${author.getDisplayName()}</a>,
            </c:forEach>


    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        </br>
        <a href="/mybooks/books/edit/${book.id}">Edytuj książkę</a>

    </c:if>

    <c:if test="${auth.isLoggedIn()}">
        </br>
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

        $('#ratingBook').on("change", function () {
            var ratingSelected = $(this).find(":selected");
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
                $("#userStars").text(res.data.stars);

            }

        }


        $('#read').on("click", function () {
            preapareVoteBook("READ", $(this));
        });
        $('#reading').on("click", function () {
            preapareVoteBook("READING", $(this));
        });
        $('#wantRead').on("click", function () {
            preapareVoteBook("WANT_READ", $(this));
        });

        function preapareVoteBook(voteType, el) {
            if(
                (
                    voteType === "READ" ||
                    voteType === "READING" ||
                    voteType === "WANT_READ"
                )
                && el.hasClass("active")
            )
            {
                voteBook("REMOVE");
            }
            else {
                voteBook(voteType);
            }
        }

        function voteBook(voteType) {
            var bookID = $("#bookID").val();
            var vote = {
                voteType: voteType,
                bookID: bookID
            }

            $.ajax({
                url: 'http://localhost:8080/mybooks/api/vote_book/',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(vote)
            }).done(voteBookRes)
                .fail(function(e) {
                        console.log("ERROR: ", e);
                    }
                );
        }

        function voteBookRes (res) {
            console.log(res);
            if(res.status === "done"){
                console.log("done");
                showVoteBook(res.data);

            }

        }

        function showVoteBook (data) {
            $('#read').removeClass("active");
            $('#reading').removeClass("active");
            $('#wantRead').removeClass("active");

            if(data.voteType === "READ"){
                $('#read').addClass("active");
            }
            else if(data.voteType === "READING"){
                $('#reading').addClass("active");
            }
            else if(data.voteType === "WANT_READ"){
                $('#wantRead').addClass("active");
            }

            $(":focus").blur();
        }

        <c:if test="${not empty vote}">
        selectVoteBook("<c:out value="${vote.voteType}"/>");
        </c:if>

        function selectVoteBook(voteType) {
            showVoteBook ({voteType: voteType})
        }


    })
</script>