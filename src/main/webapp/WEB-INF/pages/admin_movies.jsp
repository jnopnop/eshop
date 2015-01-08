<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin [Movies]</title>

    <link href="/css/chosen/chosen.min.css" rel="stylesheet">
    <link href="/css/jgrowl/jgrowl.min.css" rel="stylesheet">
    <link href="/css/datepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/css/admin.css" rel="stylesheet">

    <script src="/js/chosen/chosen.jquery.min.js"></script>
    <script src="/js/datepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/js/admin_movies.js"></script>
</head>
<body>
    <%@ include file="common/admin_navbar.jsp" %>
    <div id="page-content-wrapper">
        <!-- Keep all page content within the page-content inset div! -->
        <div class="page-content inset">
            <div class="row">
                <div class="well">
                    <div class="row accordion">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#search-form">Search</a>
                                </h4>
                            </div>
                            <div id="search-form" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <form:form method="get" action="/admin/search/movies" commandName="sm" cssClass="form-horizontal" id="movie-search">
                                        <fieldset>

                                            <div class="form-group">
                                                <form:label  cssClass="col-md-4 control-label" path="title">Title</form:label>
                                                <div class="col-md-4">
                                                    <form:input path="title" cssClass="form-control input-md"></form:input>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="year_start" cssClass="col-md-4 control-label">Begin Year</form:label>
                                                <div class="col-md-4">
                                                    <form:select path="year_start" items="${years}" cssClass="form-control chzn"></form:select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="year_end" cssClass="col-md-4 control-label">End Year</form:label>
                                                <div class="col-md-4">
                                                    <form:select path="year_end" items="${years}" cssClass="form-control chzn"></form:select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="country" cssClass="col-md-4 control-label">Country</form:label>
                                                <div class="col-md-4">
                                                    <form:select path="country" items="${countries}" cssClass="form-control chzn"></form:select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label class="col-md-4 control-label" path="ageCategory">Age Category</form:label>
                                                <div class="col-md-4">
                                                    <form:select path="ageCategory" cssClass="form-control chzn" items="${ageCategories}"></form:select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label class="col-md-4 control-label" path="genres">Genres</form:label>
                                                <div class="col-md-4">
                                                    <form:select path="genres" items="${genres}" cssClass="form-control chzn" multiple="multiple"></form:select>
                                                </div>
                                            </div>

                                            <input type="submit" name="submit_movie" id="submit_movie" value="Search" class="btn btn-info pull-right">
                                        </fieldset>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <a href="#" id="add-movie" data-toggle="modal" data-target="#addMovieModal" class="btn btn-info">Add New Movie</a>
                    </div>
                    <div class="row">
                        <ul class="pager">
                            <c:set var="hereClass" value="${movies.currPage <= 1 ? 'disabled':''}"></c:set>
                            <c:set var="thereClass" value="${movies.currPage >= movies.lastPage ? 'disabled':''}"></c:set>
                            <li class="previous ${hereClass}">
                                <a href="/admin/search/movies?<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>&p=${movies.currPage - 1}">&larr; Here</a>
                            </li>
                            <li class="next ${thereClass}">
                                <a href="/admin/search/movies?<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>&p=${movies.currPage + 1}">There &rarr;</a>
                            </li>
                        </ul>
                    </div>
                    <div class="list-group">
                        <c:choose>
                            <c:when test="${empty movies.results}">
                                <h2>Nothing was found. You're trying to do something odd, aren't you?!</h2>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${movies.results}" var="si">
                                    <div class="list-group-item row" id="item-movie-${si.id}">
                                        <div class="media col-md-2">
                                            <figure class="pull-left">
                                                <img style="width:150px;" class="img-responsive img-thumbnail" src="${si.mainImage}">
                                            </figure>
                                        </div>
                                        <div class="col-md-10">
                                            <h4>ID: ${si.id}</h4>
                                            <a href="/movie/${si.id}" target="_blank"><h4 class="list-group-item-heading admin-movie-title">${si.title} (<fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>)</h4></a>
                                            <span class="admin-delete"><a href="#" data-toggle="modal" data-target="#deleteMovieModal" data-movie-id="${si.id}">delete</a></span>
                                            <span class="admin-edit"><a href="#" data-toggle="modal" data-target="#editMovieModal" data-movie-id="${si.id}">edit</a></span>
                                        </div> <%-- /admin/deleteById/movie/${si.id} --%>
                                        <%--<div class="col-md-1 admin-controls">--%>
                                            <%--<sec:authorize ifAnyGranted="ROLE_ADMIN">--%>
                                                <%--<button data-id="${si.id}" type="button" class="edit-item">&hellip;</button>--%>
                                                <%--<button data-id="${si.id}" type="button" class="deleteById-item">&times;</button>--%>
                                            <%--</sec:authorize>--%>
                                        <%--</div>--%>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

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
    <%@ include file="templates/admin.jsp" %>
</body>
</html>

<%--<c:url value="/logout" var="logoutUrl" />--%>
<%--<form action="${logoutUrl}" method="post" id="logoutForm">--%>
    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
<%--</form>--%>
<%--<script>--%>
    <%--function formSubmit() {--%>
        <%--document.getElementById("logoutForm").submit();--%>
    <%--}--%>
<%--</script>--%>

<%--<c:if test="${pageContext.request.userPrincipal.name != null}">--%>
    <%--<h2>--%>
        <%--Welcome : ${pageContext.request.userPrincipal.name} | <a--%>
            <%--href="javascript:formSubmit()"> Logout</a>--%>
    <%--</h2>--%>
<%--</c:if>--%>

<%--</body>--%>
<%--</html>--%>