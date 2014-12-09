<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${movie.title}</title>
    <link rel="stylesheet" href="/css/movie.css">
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
                        <img class="img-responsive img-thumbnail" src="/pic/movies/${movie.imageURL}">
                    </div><!--
                    --><div class="col-md-9 movie-info vcenter">
                        <div class="movie-header">
                            <h1 class="movie-title">${movie.title}</h1>
                            <h5 class="movie-genres">
                                <c:forEach items="${movie.genres}" var="c">
                                    <a href="#">${c.value}</a>
                                </c:forEach>
                            </h5>
                        </div>
                        <div class="movie-year">
                            Year: <fmt:formatDate value="${movie.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>
                        </div>
                        <div class="movie-countries">
                            Countries:
                            <c:forEach items="${movie.countries}" var="c">
                                ${c.value}&nbsp;
                            </c:forEach>
                        </div>
                        <div class="movie-rating">
                            Rating: ${movie.rating}/10
                        </div>
                        <div class="movie-duration">
                            Duration: ${movie.duration} m.
                        </div>
                        <div class="movie-directors">
                            Directed by:
                            <c:forEach items="${movie.directors}" var="c">
                                <a href="#">${c.value}</a>
                            </c:forEach>
                        </div>
                        <div class="movie-writers">
                            Scenario:
                            <c:forEach items="${movie.writers}" var="c">
                                <a href="#">${c.value}</a>
                            </c:forEach>
                        </div>
                        <div class="movie-actors">
                            Stars:
                            <c:forEach items="${movie.actors}" var="c">
                                <a href="#">${c.value}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="row movie-description">
                    ${movie.description}
                </div>
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
                    <hr/>
                    <div class="row comment">
                        <div class="col-md-12 comment-info">
                            <img class="comment-image" src="/pic/users/${c.value.user.image}"/>
                            <div class="comment-details">
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
                </c:forEach>
            </div>


            <%--<div class="row">--%>


                                <%--<div class="list-group-item row">--%>
                                    <%--<div class="media col-md-2">--%>
                                        <%--<figure class="pull-left">--%>
                                                <%--<%--<c:set var="mainImg" value="${not empty si.imageURL ? si.imageURL:'http://placehold.it/317x214'}"></c:set>--%>--%>
                                            <%--<img style="width:150px;" class="img-responsive img-thumbnail" src="/pic/movies/${si.imageURL}">--%>
                                        <%--</figure>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-9">--%>
                                        <%--<a href="/movie/${si.id}"><h4 class="list-group-item-heading">${si.title} (<fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>)</h4></a>--%>
                                        <%--<span class="rate" data-imdbrating="${si.rating}"></span>--%>
                                        <%--<span class="movie-rating">(${si.rating}/10)</span>--%>
                                        <%--<br/>--%>
                                        <%--<span class="movie-duration"><b><i>${si.duration} min</i></b></span>--%>
                                        <%--<div class="movie-genres">--%>
                                            <%--<c:forEach items="${si.genres}" var="g">--%>
                                                <%--<a href="/movies?genre=${g.key}">${g.value}</a>--%>
                                            <%--</c:forEach>--%>
                                        <%--</div>--%>
                                        <%--<p class="list-group-item-text">--%>
                                                <%--${fn:substring(si.description, 0, 360)}...--%>
                                        <%--</p>--%>
                                        <%--<div class="movie-persons">--%>
                                            <%--Directed by:--%>
                                            <%--<c:forEach items="${si.directors}" var="d">--%>
                                                <%--<a href="/persons?id=${d.key}">${d.value}&NonBreakingSpace;</a>--%>
                                            <%--</c:forEach>--%>
                                            <%--<br/>--%>
                                            <%--Main roles:--%>
                                            <%--<c:forEach items="${si.actors}" var="a">--%>
                                                <%--<a href="/persons?id=${a.key}">${a.value}&NonBreakingSpace;</a>--%>
                                            <%--</c:forEach>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-1 admin-controls">--%>
                                        <%--<sec:authorize ifAnyGranted="ROLE_ADMIN">--%>
                                            <%--<button data-id="${si.id}" type="button" class="edit-item">&hellip;</button>--%>
                                            <%--<button data-id="${si.id}" type="button" class="delete-item">&times;</button>--%>
                                        <%--</sec:authorize>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                <%--</div>--%>
        </div>
    </div>
</body>
</html>
