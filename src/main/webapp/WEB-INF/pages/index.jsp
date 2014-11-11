<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>See what we have</title>

    <link rel="stylesheet" href="/css/movies.css" />
    <link rel="stylesheet" href="http://www.shieldui.com/shared/components/latest/css/shieldui-all.min.css" />
    <link rel="stylesheet" href="http://www.shieldui.com/shared/components/latest/css/light/all.min.css" />
    <script type="text/javascript" src="http://www.shieldui.com/shared/components/latest/js/shieldui-all.min.js"></script>
    <script src="/js/movies.js"></script>
</head>
<body>
    <%@ include file="common/navbar.jsp" %>
    <div class="row">
        <div class="well">
            <div class="row">
                <ul class="pager">
                    <c:set var="hereClass" value="${movies.currPage <= 1 ? 'disabled':''}"></c:set>
                    <c:set var="thereClass" value="${movies.currPage >= movies.lastPage ? 'disabled':''}"></c:set>
                    <li class="previous ${hereClass}">
                        <a href="/movies?p=${movies.currPage - 1}">&larr; Here</a>
                    </li>
                    <li class="next ${thereClass}">
                        <a href="/movies?p=${movies.currPage + 1}">There &rarr;</a>
                    </li>
                </ul>
            </div>
            <div class="list-group">
                <c:if test="${not empty movies}">
                    <c:forEach items="${movies.results}" var="si">
                        <div class="list-group-item row">
                            <div class="media col-md-2">
                                <figure class="pull-left">
                                    <c:set var="mainImg" value="${not empty si.imageURL ? si.imageURL:'http://placehold.it/317x214'}"></c:set>
                                    <img style="width:150px;" class="img-responsive img-thumbnail" src="${mainImg}">
                                </figure>
                            </div>
                            <div class="col-md-10">
                                <a href="/movie/${si.id}"><h4 class="list-group-item-heading">${si.title} (<fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>)</h4></a>
                                <span class="rate" data-imdbrating="${si.rating}"></span>
                                <span class="movie-rating">(${si.rating}/10)</span>
                                <br/>
                                <span class="movie-duration"><b><i>${si.duration} min</i></b></span>
                                <div class="movie-genres">
                                    <c:forEach items="${si.genres}" var="g">
                                        <a href="/movies?genre=${g.key}">${g.value}</a>
                                    </c:forEach>
                                </div>
                                <p class="list-group-item-text">
                                        ${fn:substring(si.description, 0, 360)}...
                                </p>
                                <div class="movie-persons">
                                    Directed by:
                                    <c:forEach items="${si.directors}" var="d">
                                        <a href="/persons?id=${d.key}">${d.value}&NonBreakingSpace;</a>
                                    </c:forEach>
                                    <br/>
                                    Main roles:
                                    <c:forEach items="${si.actors}" var="a">
                                        <a href="/persons?id=${a.key}">${a.value}&NonBreakingSpace;</a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>