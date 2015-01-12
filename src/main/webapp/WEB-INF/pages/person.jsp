<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>${person.fullname}</title>
    <link rel="stylesheet" href="/css/person.css">
    <link rel="stylesheet" href="/css/slick/slick.css"/>
    <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <link rel="stylesheet" href="/css/fileinput/fileinput.css">
        <link rel="stylesheet" href="/css/admin.css">
        <script src="/js/fileinput/fileinput.min.js"></script>
    </sec:authorize>

    <script src="/js/slick/slick.min.js"></script>
    <script src="/js/person.js"></script>
</head>
<body>
<%@ include file="common/navbar.jsp" %>
<!-- Page content -->
<div id="page-content-wrapper">
    <!-- Keep all page content within the page-content inset div! -->
    <div class="page-content inset">
        <div class="well person">
            <div class="row person-basic-info">
                <div class="col-md-3 person-image">
                    <sec:authorize ifNotGranted="ROLE_ADMIN">
                        <c:choose>
                            <c:when test="${empty person.mainImage}">
                                <img class="img-responsive img-thumbnail" src="/pic/persons/notfound">
                            </c:when>
                            <c:otherwise>
                                <img class="img-responsive img-thumbnail" src="${person.mainImage}">
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                        <c:choose>
                            <c:when test="${empty person.mainImage}">
                                <form method="post" action="/pic/persons/carousel" enctype="multipart/form-data">
                                    <input id="upload-main-image" name="files[]" type="file" multiple=false class="file" data-upload-async="false" data-upload-url="/pic/primary/persons/${person.id}">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <img class="img-responsive img-thumbnail" src="${person.mainImage}">
                                <a href="#" class="delete-image admin-delete" data-image-path="${person.mainImage}"><span class="sub_icon glyphicon glyphicon-remove"></span></a>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                </div><!--
                    --><div class="col-md-9 person-info vcenter">
                <div class="person-header">
                    <h1 class="person-title">${person.fullname}</h1>
                    <div class="person-birth">
                        Birth date:
                        <c:choose>
                            <c:when test="${not empty person.birthdate}">
                                <fmt:formatDate value="${person.birthdate}" type="date" dateStyle="long"></fmt:formatDate>
                            </c:when>
                            <c:otherwise>
                                Unknown
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            </div>
            <%--<c:if test="${not empty person.carouselImages}">--%>
                <%--<br>--%>
                <%--<div class="row">--%>
                    <%--<div class="col-md-12">--%>
                        <%--<div class="images-carousel">--%>
                            <%--<c:forEach var="img" items="${person.carouselImages}">--%>
                                <%--<div class="item">--%>
                                    <%--<img src="${img}" class="img-responsive" style="max-height: 180px;margin: 2px;">--%>
                                <%--</div>--%>
                            <%--</c:forEach>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</c:if>--%>
            <%--<c:if test="${not empty person.carouselImages}">--%>
                <br>
                <div class="row" style="display: block;">
                    <div class="col-md-12">
                        <sec:authorize ifAnyGranted="ROLE_ADMIN">
                            <form method="post" action="/pic/person/carousel" enctype="multipart/form-data">
                                <input id="upload-carousel-images" name="files[]" type="file" multiple=true class="file" data-upload-async="false" data-max-file-count="10" data-upload-url="/pic/carousel/persons/${person.id}">
                            </form>
                        </sec:authorize>
                        <div class="images-carousel <sec:authorize ifAnyGranted="ROLE_ADMIN">admin-images</sec:authorize>">
                            <c:forEach var="img" items="${person.carouselImages}">
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
            <%--</c:if>--%>
            <br>
            <span style="font-size: x-large;">Known for:</span>
            <hr style="margin-top: 0px;"/>
            <div class="person-careers">
                <c:if test="${not empty person.directorAt}">
                    <div class="row directed">
                        <div class="person-role">Director at:</div>
                        <c:forEach items="${person.directorAt}" var="c">
                            <div class="col-md-3 person-career-movie">
                                <c:choose>
                                    <c:when test="${empty c.mainImage}">
                                        <img class="img-responsive img-thumbnail" src="/pic/movies/notfound">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img-responsive img-thumbnail" src="${c.mainImage}">
                                    </c:otherwise>
                                </c:choose>
                                <a href="/movie/${c.id}">${c.title}</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${not empty person.writerAt}">
                    <div class="row wrote">
                        <div class="person-role">Writer at:</div>
                        <c:forEach items="${person.writerAt}" var="c">
                            <div class="col-md-3 person-career-movie">
                                <c:choose>
                                    <c:when test="${empty c.mainImage}">
                                        <img class="img-responsive img-thumbnail" src="/pic/movies/notfound">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img-responsive img-thumbnail" src="${c.mainImage}">
                                    </c:otherwise>
                                </c:choose>
                                <a href="/movie/${c.id}">${c.title}</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${not empty person.actorAt}">
                    <c:set var="N" value="${person.actorAt}"></c:set>
                    <div class="row acted">
                        <div class="person-role">Actor at:</div>
                        <c:forEach items="${person.actorAt}" var="c">
                            <div class="col-md-3 person-career-movie">
                                <c:choose>
                                    <c:when test="${empty c.mainImage}">
                                        <img class="img-responsive img-thumbnail" src="/pic/movies/notfound">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img-responsive img-thumbnail" src="${c.mainImage}">
                                    </c:otherwise>
                                </c:choose>
                                <a href="/movie/${c.id}">${c.title}</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
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
</body>
</html>
