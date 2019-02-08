<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Recenzja: ${review.title}</h3>
            <img class="book-cover" src="${book.cover}"/> </br>
            Tytuł: ${review.title} </br>
            Data dodania: ${review.getDate()} </br>
            Liczba wyświetleń: ${review.viewCount} </br>
            Autor: <a href="/mybooks/user/${review.user.id}">${review.user.getDisplayName()}</a> </br></br>
            Treść: ${review.content} </br></br>
    Książka:  <a href="/mybooks/book/${review.book.slug}">${review.book.title}</a> </br>
    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/reviews/edit/${review.id}">Edytuj recenzję</a>

    </c:if>
    </br>
    </br>
    Komentarze (<span id="commentCount"></span>):
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addCommentModal">
       Dodaj komentarz
    </button>
<br />
    <div class="modal fade" id="addCommentModal" tabindex="-1" role="dialog" aria-labelledby="addCommentModal" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Dodaj komentarz</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="content" class="col-form-label">Treść:</label>
                            <textarea id="contentComment" class="form-control" id="content" rows="4"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Anuluj</button>
                    <button type="button" id="addCommentBtn" class="btn btn-primary">Dodaj</button>
                </div>
            </div>
        </div>
    </div>

<%--    <c:forEach items="${review.comments}" var="comment" varStatus="status">
       ${comment.content}, Autor: ${comment.user.getDisplayName()}, Data: ${comment.getDate()} <br />
    </c:forEach><br />--%>

    <div id="comments">

    </div>

        <button type="button" id="moreComments" class="btn btn-primary">Więcej</button>


<script type="text/javascript">
$(function() {

    $("#addCommentBtn").click(function () {
        var content = $('#contentComment').val();
        if (content != '') {

            var comment = {
                content: content,
                reviewID: ${review.id}
            }
            addCommentRes(comment);
            $.ajax({
                url: 'http://localhost:8080/mybooks/api/comment/add',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(comment)
            }).done(addCommentRes)
                .fail(function (e) {
                        console.log("ERROR: ", e);
                    }
                );
        }

    });

    function addCommentRes(res) {
        //console.log(res);
        if (res.status === "done") {

            $('#addCommentModal').modal('hide');
            $('#contentComment').val("");
            location.reload();
        }

    }

    var commentsPage = 1;
    var commentsDisabled = false;
    getComments();

    function getComments() {
        if(commentsDisabled)
            return;
        $.ajax({
            url: 'http://localhost:8080/mybooks/api/comment/get/${review.id}?page=' + commentsPage,
            type: 'get'
        }).done(getCommentsRes)
            .fail(function (e) {
                    console.log("ERROR: ", e);
                }
            );
    }



    function getCommentsRes(res) {
        //console.log(res);
        if (res.status === "done") {
            commentsPage++;
            var output = "";
            $.each(res.data.content, function(index, c) {
                output+= "<div class='comment'>" +
                    " <span class='author'> Autor: <a href='/mybooks/user/"+ c.userID +"'>" + c.userName +
                    "</a></span><span class='data'> Data: " + c.addDate + "</span>" +
                    "<p>" + c.content + "</p>" +
                    "</div>";
            });

            //$('#comments').append(output);
            $('#comments').prepend(output);
            $('#commentCount').text(res.data.totalCount);

            if(res.data.page === res.data.totalPage)
                disableMoreComments();
        }
        else {
            disableMoreComments();
            $('#commentCount').text("0");
        }

    }

    function disableMoreComments() {
        commentsDisabled = true;
        $("#moreComments").css("display", "none");
        $("#moreComments").addClass("disabled");
    }

    $("#moreComments").click(function () {
        getComments();
    });


});
</script>

</div>