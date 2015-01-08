<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${movie.title}</title>
    <link rel="stylesheet" href="/css/movie.css">
    <link rel="stylesheet" href="/css/slick/slick.css"/>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <link rel="stylesheet" href="/css/chosen/chosen.min.css">
        <link rel="stylesheet" href="/css/fileinput/fileinput.css">
        <link rel="stylesheet" href="/css/admin.css">
        <script src="/js/fileinput/fileinput.min.js"></script>
    </sec:authorize>

    <script src="/js/slick/slick.min.js"></script>
    <script src="/js/movie.js"></script>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <script src="/js/chosen/chosen.jquery.min.js"></script>
        <script src="/js/admin_movies.js"></script>
    </sec:authorize>
</head>
<body>
    <%@ include file="common/navbar.jsp" %>
    <!-- Page content -->
    <div id="page-content-wrapper">
        <!-- Keep all page content within the page-content inset div! -->
        <div class="page-content inset">
            <div class="well movie">
                <div class="row movie-basic-info">
                    <div class="col-md-3 movie-image" style="position: relative;">
                        <sec:authorize ifNotGranted="ROLE_ADMIN">
                            <c:choose>
                                <c:when test="${empty movie.mainImage}">
                                    <img class="img-responsive img-thumbnail" src="/pic/movies/notfound">
                                </c:when>
                                <c:otherwise>
                                    <img class="img-responsive img-thumbnail" src="${movie.mainImage}">
                                </c:otherwise>
                            </c:choose>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_ADMIN">
                            <c:choose>
                                <c:when test="${empty movie.mainImage}">
                                    <form method="post" action="/pic/movie/carousel" enctype="multipart/form-data">
                                        <input id="upload-main-image" name="files[]" type="file" multiple=false class="file" data-upload-async="false" data-upload-url="/pic/primary/movies/${movie.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <img class="img-responsive img-thumbnail" src="${movie.mainImage}">
                                    <a href="#" class="delete-image" data-image-path="${movie.mainImage}"><span class="sub_icon glyphicon glyphicon-remove"></span></a>
                                </c:otherwise>
                            </c:choose>
                        </sec:authorize>
                    </div><!--
                    --><div class="col-md-9 movie-info vcenter">
                        <div class="movie-header">
                            <span class="movie-title">${movie.title}</span>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <span class="admin-delete"><a href="#" data-toggle="modal" data-target="#deleteMovieModal" data-movie-id="${movie.id}"><span class="glyphicon glyphicon-remove"></span></a></span>
                                <span class="admin-edit"><a href="#" data-toggle="modal" data-target="#editMovieModal" data-movie-id="${movie.id}"><span class="glyphicon glyphicon-edit"></span></a></span>
                            </sec:authorize>
                            <br/>
                            <h5 class="movie-genres">
                                <ul class="horizontal-list ">
                                    <c:forEach items="${movie.genres}" var="c">
                                        <li><a href="/movies/search?genres=${c.value}">${c.value}</a></li>
                                    </c:forEach>
                                </ul>
                            </h5>
                        </div>
                        <div class="movie-year">
                            Year: <fmt:formatDate value="${movie.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>
                        </div>
                        <div class="movie-countries">
                            <span class="pull-left">Countries:&nbsp;</span>
                            <ul class="horizontal-list">
                                <c:forEach items="${movie.countries}" var="c">
                                    <li><a href="/movies/search?country=${c.value}">${c.value}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="movie-rating">
                            Rating: ${movie.rating}/10
                        </div>
                        <div class="movie-duration">
                            Duration: ${movie.duration} m.
                        </div>
                        <div class="movie-directors">
                            <span class="pull-left">Directed by:&nbsp;</span>
                            <ul class="horizontal-list">
                                <c:forEach items="${movie.directors}" var="c">
                                    <li><a href="/person/${c.key}">${c.value}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="movie-writers">
                            <span class="pull-left">Scenario:&nbsp;</span>
                            <ul class="horizontal-list">
                                <c:forEach items="${movie.writers}" var="c">
                                    <li><a href="/person/${c.key}">${c.value}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="movie-actors">
                            <span class="pull-left">Stars:&nbsp;</span>
                            <ul class="horizontal-list">
                                <c:forEach items="${movie.actors}" var="c">
                                    <li><a href="/person/${c.key}">${c.value}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row movie-description">
                    ${movie.description}
                </div>
                <br>
                <div class="row" style="display: block;">
                    <div class="col-md-12">
                        <sec:authorize ifAnyGranted="ROLE_ADMIN">
                            <form method="post" action="/pic/movie/carousel" enctype="multipart/form-data">
                                <input id="upload-carousel-images" name="files[]" type="file" multiple=true class="file" data-upload-async="false" data-max-file-count="10" data-upload-url="/pic/carousel/movies/${movie.id}">
                            </form>
                        </sec:authorize>
                        <div class="images-carousel <sec:authorize ifAnyGranted="ROLE_ADMIN">admin-images</sec:authorize>">
                            <c:forEach var="img" items="${movie.carouselImages}">
                                <div class="item" style="max-height: 180px;margin: 2px;position: relative;">
                                    <a href="#" class="carousel-link"><img src="${img}" class="img-responsive" style="max-height: 180px;"></a>
                                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                        <a href="#" class="delete-image" data-image-path="${img}"><span class="sub_icon glyphicon glyphicon-remove"></span></a>
                                    </sec:authorize>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" style="padding: 0px;">
                        <form id="new-comment" role="form">
                            <legend>Have something to say about this movie?</legend>
                            <div class="form-group">
                                <sec:authorize access="isAnonymous()">
                                    Please <a href="/login">sign in</a> or <a href="/register">register</a> in order to leave a comment.
                                </sec:authorize>
                                <sec:authorize access="isAuthenticated()">
                                    <input id="movie-id" type="hidden" value="${movie.id}">
                                    <div class="input-group" style="width: 100%;">
                                        <!-- TODO: add comment title -->
                                        <label for="ctitle">Title</label>
                                        <input name="ctitle" id="ctitle" class="form-control">
                                    </div>
                                    <div class="input-group" style="width: 100%;">
                                        <!-- TODO: add comment title -->
                                        <label for="ccomment">Comment</label>
                                        <textarea name="ccomment" id="ccomment" class="form-control" rows="5" required></textarea>
                                    </div>
                                    <a type="submit" name="add-comment" id="add-comment" href="#" class="btn btn-info pull-right">Submit</a>
                                </sec:authorize>
                            </div>
                        </form>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <c:choose>
                        <c:when test="${not empty movie.comments}">
                            <h4>What people say about this movie</h4>
                        </c:when>
                        <c:otherwise>
                            <h4>There is no comments yet. Be the first!</h4>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:forEach items="${movie.comments}" var="c">
                    <div class="comment-section" id="${c.value.id}">
                        <hr/>
                        <div class="row comment">
                            <div class="col-md-12 comment-info">
                                <img class="comment-image" src="${c.value.user.image}"/>
                                <div class="comment-details">
                                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                        <span class="admin-delete"><a href="#" data-toggle="modal" data-target="#deleteCommentModal" data-comment-id="${c.value.id}">delete</a></span>
                                    </sec:authorize>
                                    <span class="comment-title">${c.value.title}</span>
                                    <br/>
                                    <span class="comment-author">by ${c.value.user.fullname}</span>
                                </div>
                            </div>
                        </div>
                        <br/>
                        <div class="row comment-text">
                            <p>${c.value.text}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        </div>
    </div>
    <div class="modal fade" id="modal-gallery" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                </div>
            </div>
        </div>
    </div>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="modal fade" id="deleteMovieModal" tabindex="-1" role="dialog" aria-labelledby="deleteMovieModal" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-body">
                        Delete movie?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button id="delete-movie-btn" type="button" class="btn btn-primary">OK</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="editMovieModal" tabindex="-1" role="dialog" aria-labelledby="editMovieModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="editMovieModalLabel">Edit Movie</h4>
                    </div>
                    <div class="modal-body">
                        <form Class="form-horizontal" id="form-edit-movie"method="put">
                            <fieldset>
                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mtitle">Title</label>
                                    <div class="col-md-10">
                                        <input id="mtitle" name="mtitle" placeholder="" class="form-control input-md" data-for="title">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mdescription">Description</label>
                                    <div class="col-md-10">
                                        <textarea id="mdescription" name="mdescription" class="form-control input-md" data-for="description"></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mduration">Duration</label>
                                    <div class="col-md-10">
                                        <input id="mduration" name="mduration" class="form-control input-md" data-for="duration">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mreleaseDate">Release Date</label>
                                    <div class="col-md-10">
                                        <input id="mreleaseDate" name="mreleaseDate" class="form-control input-md" data-for="releaseDate">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mrating">Rating</label>
                                    <div class="col-md-10">
                                        <input id="mrating" name="mrating" class="form-control input-md" data-for="rating">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mimdbId">IMDB ID</label>
                                    <div class="col-md-10">
                                        <input id="mimdbId" name="mimdbId" class="form-control input-md" data-for="imdbId">
                                        <span class="help-block">optional</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mageCategory">Age Category</label>
                                    <div class="col-md-10">
                                        <select id="mageCategory" name="mageCategory" class="form-control" data-for="ageCategory">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mgenres">Genres</label>
                                    <div class="col-md-10">
                                        <select id="mgenres" name="mgenres" class="form-control" multiple="multiple" data-for="genres">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mcountries">Countries</label>
                                    <div class="col-md-10">
                                        <select id="mcountries" name="mcountries" class="form-control" multiple="multiple" data-for="countries">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mdirectors">Directors</label>
                                    <div class="col-md-10">
                                        <select id="mdirectors" name="mdirectors" class="form-control" multiple="multiple" data-for="directors">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mwriters">Writers</label>
                                    <div class="col-md-10">
                                        <select id="mwriters" name="mwriters" class="form-control" multiple="multiple" data-for="writers">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="mactors">Actors</label>
                                    <div class="col-md-10">
                                        <select id="mactors" name="mactors" class="form-control" multiple="multiple" data-for="actors">
                                        </select>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button id="edit-movie-btn" type="button" class="btn btn-primary">Edit</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addMovieModal" tabindex="-1" role="dialog" aria-labelledby="addMovieModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="addMovieModalLabel">Add Movie</h4>
                    </div>
                    <div class="modal-body">
                        <form Class="form-horizontal" id="form-add-movie" method="put">
                            <fieldset>
                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="atitle">Title</label>
                                    <div class="col-md-10">
                                        <input id="atitle" name="atitle" placeholder="" class="form-control input-md" data-for="title">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="adescription">Description</label>
                                    <div class="col-md-10">
                                        <textarea id="adescription" name="adescription" class="form-control input-md" data-for="description"></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="aduration">Duration</label>
                                    <div class="col-md-10">
                                        <input id="aduration" name="aduration" class="form-control input-md" data-for="duration">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="areleaseDate">Release Date</label>
                                    <div class="col-md-10">
                                        <input id="areleaseDate" name="areleaseDate" class="form-control input-md" data-for="releaseDate">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="arating">Rating</label>
                                    <div class="col-md-10">
                                        <input id="arating" name="arating" class="form-control input-md" data-for="rating">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="aimdbId">IMDB ID</label>
                                    <div class="col-md-10">
                                        <input id="aimdbId" name="aimdbId" class="form-control input-md" data-for="imdbId">
                                        <span class="help-block">optional</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="aageCategory">Age Category</label>
                                    <div class="col-md-10">
                                        <select id="aageCategory" name="aageCategory" class="form-control" data-for="ageCategory">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="agenres">Genres</label>
                                    <div class="col-md-10">
                                        <select id="agenres" name="agenres" class="form-control" multiple="multiple" data-for="genres">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="acountries">Countries</label>
                                    <div class="col-md-10">
                                        <select id="acountries" name="acountries" class="form-control" multiple="multiple" data-for="countries">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="adirectors">Directors</label>
                                    <div class="col-md-10">
                                        <select id="adirectors" name="adirectors" class="form-control" multiple="multiple" data-for="directors">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="awriters">Writers</label>
                                    <div class="col-md-10">
                                        <select id="awriters" name="awriters" class="form-control" multiple="multiple" data-for="writers">
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="aactors">Actors</label>
                                    <div class="col-md-10">
                                        <select id="aactors" name="aactors" class="form-control" multiple="multiple" data-for="actors">
                                        </select>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button id="add-movie-btn" type="button" class="btn btn-primary">Add</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="deleteCommentModal" tabindex="-1" role="dialog" aria-labelledby="deleteCommentModal" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-body">
                        Delete this comment?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button id="delete-comment-btn" type="button" class="btn btn-primary">OK</button>
                    </div>
                </div>
            </div>
        </div>
    </sec:authorize>
</body>
</html>
