<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="container">
    <div class="row">
        <div class="col-md-6">

            <c:if test="${added}">
                Added. See <a href="${bookPath}">${bookTitle}</a> or <a href="${pageContext.request.contextPath}/book/add">Add new book</a>

            </c:if>

            <c:if test="${not added}">
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                        ${errorMsg}<br />
                </div>
            </c:if>

            <form:form modelAttribute="book">
                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                <div class="form-group">
                    <label for="title">Title</label>
                    <form:input type="text" class="form-control" path="title" id="title" value="${book.title}" placeholder="Title"/>
                    <form:errors path="title" cssClass="text-danger" />
                </div>
                <form:input type="text" id="authors" path="authors" value="${book.authors}" autocomplete="false" hidden="hidden" />
                <div class="form-group">
                    <label for="authorSearch">Author</label>
                    <select id="authorSearch" class="form-control">
                        <option value="0">Select</option>
                        <c:forEach items="${authors}" var="author" varStatus="tagStatus">
                            <option value="${author.id}">${author.firstName} ${author.lastName}</option>
                        </c:forEach>
                    </select>
                    <div id="authorList" class="list_option"></div>



                    <button type="button" id="newAuthorBtn" class="btn btn-link">Nowy autor</button>
                    <div id="newAuthorForm" class="input-group">
                        <input type="text" id="add_new_author_name" class="form-control" placeholder="First name">
                        <input type="text" id="add_new_author_lastname" class="form-control" placeholder="Last name">
                        <div class="input-group-append">
                            <button id="add_new_author" class="btn btn-outline-secondary" type="button">Dodaj autora</button>
                        </div>
                    </div>

                </div>
                <div class="form-group">
                    <label for="cats">Category</label>
                    <form:select path="category" value="${book.category.id}" id="cats" class="form-control">
                        <form:option value="0" label="Select" />
                        <c:forEach items="${cats}" var="cat">
                            <c:choose>
                                <c:when test="${cat.id == book.category.id}">
                                    <option value="${cat.id}" selected>${cat.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${cat.id}">${cat.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                    <form:errors path="category" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <form:textarea class="form-control" path="description" value="${book.description}" placeholder="Description"></form:textarea>
                    <form:errors path="description" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="publishYear">Publish year</label>
                    <form:input type="text" class="form-control" path="publishYear" id="publishYear" value="${book.publishYear}" placeholder="Publish year"/>
                    <form:errors path="publishYear" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="pages">Page count</label>
                    <form:input type="text" class="form-control" path="pages" id="pages" value="${book.pages}" placeholder="Page count"/>
                    <form:errors path="pages" cssClass="text-danger" />
                </div>
                <div class="form-group">
                    <label for="originalTitle">Orginal title</label>
                    <form:input type="text" class="form-control" path="originalTitle" id="originalTitle" value="${book.originalTitle}" placeholder="Orginal title"/>
                    <form:errors path="originalTitle" cssClass="text-danger" />
                </div>

                <div class="form-group">
                    <label for="cover">Cover</label>
                    <form:input type="text" class="form-control" path="cover" id="cover" value="${book.cover}" placeholder="Cover"/>
                    <form:errors path="cover" cssClass="text-danger" />
                </div>


                <button type="submit" class="btn btn-primary">Add</button>
            </form:form>
            </c:if>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function() {

    $('#authorSearch').find('option').click(function () {
        var authorSelected = $(this);
        var id = authorSelected.val();
        var name = authorSelected.text();

        if(id != 0) {
            addAuthor(id, name);

            $(this).removeAttr("selected");
            $("#authorSearch")[0].selectedIndex = 0;

        }

        //console.log(id + " " + name);
    });

    function addAuthor(id, name) {
        var authors = $('#authors').val();

        var authorArray = authors.split(",");

        if(!authorArray.includes(id)) {
            authors += id + ",";
            $('#authors').val(authors);
            $('#authors').attr('value', authors);
            //console.log(authors);

            authorListAppend(id, name);

        }
    }

    function authorListAppend(id, name) {
        output = "" +
            "<div id=\"author"+id+"\" class=\"author\">\n" +
            "<div class=\"name\">"+name+"</div>\n" +
            "<div class=\"action\"><button type=\"button\" authorid=\""+id+"\" class=\"btn btn-link\">Remove</button></div>\n" +
            "</div>";
        $("#authorList").append(output);
    }

    $('#authorList').on("click", "button", function () {
        var id = $(this).attr("authorid");
        $( ".hello" ).remove();
        $("#author"+id).remove();

        var authors = $('#authors').val();
        authors = authors.replace(id+"," , "");
        $('#authors').val(authors);
        $('#authors').attr('value', authors);

        //console.log(id);
    });

    $("#newAuthorBtn").click(function () {
       var display =  $("#newAuthorForm").css("display");
       if(display == "none") {
           $("#newAuthorBtn").text("Anuluj");
           $("#newAuthorForm").css("display", "flex");
       }
       else {
           $("#newAuthorBtn").text("Nowy autor");
           $("#newAuthorForm").css("display", "none");
       }


    });


  $("#add_new_author").click(function () {
      var name = $('#add_new_author_name').val();
      var lastname = $('#add_new_author_lastname').val();
      if(name != '') {

          var author = {
              firstName: name,
              lastName: lastname
          }
           // console.log(JSON.stringify(author));


          $.ajax({
              url: 'http://localhost:8080/mybooks/api/author/add',
              type: 'post',
              dataType: 'json',
              contentType: 'application/json',
              data: JSON.stringify(author)
          }).done(addAuthorRes)
              .fail(function(e) {
                console.log("ERROR: ", e);
              }
          );
      }

    });

    function addAuthorRes (res) {
        //console.log(res);
        if(res.status === "done"){
           // console.log(res.data.id);

            addAuthor(res.data.id, res.data.firstName + " " + res.data.lastName);

            $("#newAuthorForm").css("display", "none");
            $("#newAuthorBtn").text("Nowy autor");

            $('#add_new_author_name').val("");
            $('#add_new_author_name').attr('value', "");
            $('#add_new_author_lastname').val("");
            $('#add_new_author_lastname').attr('value', "");
        }

    }


    function showAuthors() {
        var authors = $('#authors').val();
        var authorArray = authors.split(",");

        authorArray.forEach(function (id) {
            id = parseInt(id);
            if(!isNaN(id) && id !== 0) {
                var name = $('#authorSearch option[value="'+id+'"]').text();
                authorListAppend(id, name);
            }

        });
    }

    showAuthors();




   /* $("#authorSearch input").focusout(function () {
        $("#authorSearchResult").css("visibility", "collapse");
    });

    $("#authorSearch input").focusin(function () {
        $("#authorSearchResult").css("visibility", "visible");
    });

    $("#authorSearch input").keyup(_.debounce(function() {
        var filter = $(this).val(),
            count = 0;
        if(filter.length>=2){
            var search = $("#authorSearch input").val();
            console.log(search);

            var request = $.ajax({
                url: "/mybooks/api/author/search/" + search,
                type: "GET",
                dataType: 'json'
            });

            request.done(function(data) {
                console.log(data);
                var output = "";
                $.each(data, function(index, e) {
                    output+= "<div class='author_s'>" + e.firstName + " " + e.lastName + "</div>";
                });

                $("#authorSearchResult").html(output);
                $("#authorSearchResult").css("visibility", "visible");
            });

            request.fail(function(jqXHR, textStatus) {
                console.log(textStatus);
            });
        }
    }, 400));*/






/*var i = 13442;
$("#add_new_author").click( function() {

    var name = $('#add_new_author_name').val();
    var lastname = $('#add_new_author_lastname').val();
    if(name != '') {
        i++;
    $("#add_new_author_div").append("" +
        "<input id='authors"+i+".id' name='authors["+i+"]' value='"+i+"'/>" +
        "<input id='authors"+i+".firstName' name='authors["+i+"].firstName' value='"+name+"'/>" +
        "<input id='authors"+i+".lastName' name='authors["+i+"].lastName' value='"+lastname+"'/>");
    $('#add_new_author_name').val('');
    $('#add_new_author_lastname').val('');
    }

});*/

});

</script>