<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${movie.title}</title>
    <link rel="stylesheet" href="/css/movie.css">
    <script src="/js/movie.js"></script>
</head>
<body>
    <%@ include file="common/navbar.jsp" %>
    <!-- Page content -->
    <div id="page-content-wrapper">
        <!-- Keep all page content within the page-content inset div! -->
        <div class="page-content inset">
            <div class="well movie">
                <div class="row movie-basic-info">
                    <div class="col-md-3 movie-image">
                        <img class="img-responsive img-thumbnail" src="${movie.mainImage}">
                    </div><!--
                    --><div class="col-md-9 movie-info vcenter">
                        <div class="movie-header">
                            <h1 class="movie-title">${movie.title}</h1>
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
                <c:if test="${not empty movie.carouselImages}">
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="carousel slide" id="movie-images-carousel">
                                <div class="carousel-inner">
                                    <c:forEach var="img" items="${movie.carouselImages}">
                                        <div class="item">
                                            <div class="col-md-4  col-xs-4"><a href="#" class="carousel-link"><img src="${img}" class="img-responsive"></a></div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <a class="left carousel-control" href="#movie-images-carousel" data-slide="prev"><i class="glyphicon glyphicon-chevron-left"></i></a>
                                <a class="right carousel-control" href="#movie-images-carousel" data-slide="next"><i class="glyphicon glyphicon-chevron-right"></i></a>
                            </div>
                        </div>
                    </div>
                </c:if>

                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <form id="add-comment" role="form" action="" method="post">
                            <div class="form-group">
                                <label for="comment">Have something to say?</label>
                                <div class="input-group" style="width: 100%;">
                                    <textarea name="comment" id="comment" class="form-control" rows="5" required></textarea>
                                </div>
                            </div>
                            <input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
                        </form>
                    </div>
                </div>
                <br/>
                <div class="row">
                    What people say about this movie
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
</body>
</html>
