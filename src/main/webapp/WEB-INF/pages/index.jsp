<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>See what we have</title>

    <link rel="stylesheet" href="/css/movies.css" />
    <link rel="stylesheet" href="http://www.shieldui.com/shared/components/latest/css/shieldui-all.min.css" />
    <link rel="stylesheet" href="http://www.shieldui.com/shared/components/latest/css/light/all.min.css" />
    <link rel="stylesheet" href="/css/chosen/chosen.min.css">
    <script type="text/javascript" src="http://www.shieldui.com/shared/components/latest/js/shieldui-all.min.js"></script>
    <script src="/js/chosen/chosen.jquery.min.js"></script>
    <script src="/js/movies.js"></script>
</head>
<body>
    <%@ include file="common/navbar.jsp" %>
    <!-- Page content -->
    <div id="page-content-wrapper">
        <!-- Keep all page content within the page-content inset div! -->
        <div class="page-content inset">
            <div class="row">
                <div class="well">
                    <div class="row">
                            <c:if test="${advanced != null && advanced == true}">
                                <form:form method="get" action="/movies/search" commandName="sm" cssClass="form-horizontal" id="movie-search">
                                    <fieldset>
                                        <legend>Search Movie</legend>

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
                                                <form:select id="genres" path="genres" items="${genres}" cssClass="form-control chzn" multiple="multiple"></form:select>
                                            </div>
                                        </div>

                                        <input type="submit" name="submit_movie" id="submit_movie" value="Search" class="btn btn-info pull-right">
                                    </fieldset>
                                </form:form>
                            </c:if>
                            <c:if test="${advanced == null || advanced == false}">
                                <form role="search" method="get" action="/movies">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Search" name="q" id="q" <c:if test="${not empty query}"> value="${query}"</c:if>>
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                        </div>
                    <div class="row">
                        <ul class="pager">
                            <c:set var="hereClass" value="${movies.currPage <= 1 ? 'disabled':''}"></c:set>
                            <c:set var="thereClass" value="${movies.currPage >= movies.lastPage ? 'disabled':''}"></c:set>
                            <li class="previous ${hereClass}">
                                <c:choose>
                                    <c:when test="${advanced != null && advanced == true}">
                                        <a href="/movies/search?<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>&p=${movies.currPage - 1}">&larr; Here</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/movies?<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>&p=${movies.currPage - 1}">&larr; Here</a>
                                    </c:otherwise>
                                </c:choose>

                            </li>
                            <li class="next ${thereClass}">
                                <c:choose>
                                    <c:when test="${advanced != null && advanced == true}">
                                        <a href="/movies/search?<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>&p=${movies.currPage + 1}">There &rarr;</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/movies?<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>&p=${movies.currPage + 1}">There &rarr;</a>
                                    </c:otherwise>
                                </c:choose>

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
                                    <div class="list-group-item row">
                                        <div class="media col-md-2">
                                            <figure class="pull-left">
                                                <img style="width:150px;" class="img-responsive img-thumbnail" src="/pic/movies/${si.imageURL}">
                                            </figure>
                                        </div>
                                        <div class="col-md-9">
                                            <a href="/movie/${si.id}" target="_blank"><h4 class="list-group-item-heading">${si.title} (<fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>)</h4></a>
                                            <span class="rate" data-imdbrating="${si.rating}"></span>
                                            <span class="movie-rating">(${si.rating}/10)</span>
                                            <br/>
                                            <span class="movie-duration"><b><i>${si.duration} min</i></b></span>
                                            <div class="movie-genres">
                                                <c:forEach items="${si.genres}" var="g">
                                                    <a href="/movies/search?genres=${g.value}">${g.value}</a>
                                                </c:forEach>
                                            </div>
                                            <div class="movie-countries">
                                                <c:forEach items="${si.countries}" var="c">
                                                    <a href="/movies/search?country=${c.value}"><i>${c.value}</i>&nbsp;</a>
                                                </c:forEach>
                                            </div>
                                            <p class="list-group-item-text">
                                                    ${fn:substring(si.description, 0, 360)}...
                                            </p>
                                            <div class="movie-persons">
                                                Directed by:
                                                <c:forEach items="${si.directors}" var="d">
                                                    <a href="/person/${d.key}" target="_blank">${d.value}&NonBreakingSpace;</a>
                                                </c:forEach>
                                                <br/>
                                                Main roles:
                                                <c:forEach items="${si.actors}" var="a">
                                                    <a href="/person/${a.key}" target="_blank">${a.value}&NonBreakingSpace;</a>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="col-md-1 admin-controls">
                                            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                                <button data-id="${si.id}" type="button" class="edit-item">&hellip;</button>
                                                <button data-id="${si.id}" type="button" class="delete-item">&times;</button>
                                            </sec:authorize>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>