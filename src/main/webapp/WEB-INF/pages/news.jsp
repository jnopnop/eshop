<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Movie News</title>

    <link href="/css/chosen/chosen.min.css" rel="stylesheet">
    <link href="/css/jgrowl/jgrowl.min.css" rel="stylesheet">
    <link href="/css/datepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/css/news.css" rel="stylesheet">
    <sec:authorize access="hasRole('ROLE_USER')">
        <link rel="stylesheet" href="/css/fileinput/fileinput.css">
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <link rel="stylesheet" href="/css/admin.css">
    </sec:authorize>

    <script src="/js/chosen/chosen.jquery.min.js"></script>
    <sec:authorize access="hasRole('ROLE_USER')">
        <script src="/js/fileinput/fileinput.min.js"></script>
    </sec:authorize>
    <script src="/js/datepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/ckeditor/ckeditor.js"></script>
    <script src="/js/admin_news.js"></script>
</head>
<body>
<%@ include file="common/navbar.jsp" %>
<div id="page-content-wrapper">
    <!-- Keep all page content within the page-content inset div! -->
    <div class="page-content inset">
        <div class="row">
            <div class="well">
                <!-- News fulltext search currently is not available -->
                <%--<div class="row accordion">--%>
                    <%--<div class="panel panel-default">--%>
                        <%--<div class="panel-heading">--%>
                            <%--<h4 class="panel-title">--%>
                                <%--<a data-toggle="collapse" data-parent="#accordion" href="#search-form">Search</a>--%>
                            <%--</h4>--%>
                        <%--</div>--%>
                        <%--<c:set var="searchBoxClass" value="${query.length() > 0 ? '' : 'collapse'}"></c:set>--%>
                        <%--<div id="search-form" class="panel-collapse ${searchBoxClass}">--%>
                            <%--<div class="panel-body">--%>
                                <%--<form role="search" method="get" action="/admin/news">--%>
                                    <%--<div class="input-group">--%>
                                        <%--<input type="text" class="form-control" placeholder="Search" name="q" id="q" <c:if test="${not empty query}"> value="${query}"</c:if>>--%>
                                        <%--<div class="input-group-btn">--%>
                                            <%--<button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</form>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <div class="row">
                        <a class="btn btn-info" href="#" data-toggle="modal" data-target="#addNewsModal">Add News</a>
                    </div>
                </sec:authorize>
                <div class="row">
                    <ul class="pager">
                        <c:set var="hereClass" value="${news.currPage <= 1 ? 'disabled':''}"></c:set>
                        <c:set var="thereClass" value="${news.currPage >= news.lastPage ? 'disabled':''}"></c:set>
                        <li class="previous ${hereClass}">
                            <a href="/admin/news?p=${news.currPage - 1}&<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>">&larr; Here</a>
                        </li>
                        <li class="next ${thereClass}">
                            <a href="/admin/news?p=${news.currPage + 1}&<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>">There &rarr;</a>
                        </li>
                    </ul>
                </div>
                <div class="list-group">
                    <c:choose>
                        <c:when test="${empty news.results}">
                            <h2>Nothing was found. You're trying to do something odd, aren't you?!</h2>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${news.results}" var="si">
                                <div class="list-group-item row" id="item-news-${si.id}">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h3 style="float:left;">${si.title}</h3>
                                            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                                <h3 style="float: right;">
                                                    <span class="admin-delete"><a href="#" data-toggle="modal" data-target="#deleteNewsModal" data-news-id="${si.id}"><span class="glyphicon glyphicon-remove"></span></a></span>
                                                    <span class="admin-edit"><a href="#" data-toggle="modal" data-target="#editNewsModal" data-news-id="${si.id}"><span class="glyphicon glyphicon-edit"></span></a></span>
                                                </h3>
                                            </sec:authorize>
                                        </div>
                                    </div>
                                    <div class="row" style="margin-bottom: 10px;">
                                        <div class="col-md-12" style="position: relative;">
                                            <sec:authorize ifNotGranted="ROLE_ADMIN">
                                                <c:if test="${not empty si.mainImage}">
                                                    <img class="news-image" src="${si.mainImage}">
                                                </c:if>
                                            </sec:authorize>
                                            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                                <c:choose>
                                                    <c:when test="${empty si.mainImage}">
                                                        <h5>Upload News Image</h5>
                                                        <form method="post" action="/pic/news/primary" enctype="multipart/form-data">
                                                            <input id="upload-main-image" name="files[]" type="file" multiple=false class="file" data-upload-async="false" data-upload-url="/pic/primary/news/${si.id}">
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img class="news-image" src="${si.mainImage}">
                                                        <a href="#" class="delete-image" data-image-path="${si.mainImage}" style="left:18px;"><span class="sub_icon glyphicon glyphicon-remove"></span></a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </sec:authorize>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            ${si.contents}
                                        </div>
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

<sec:authorize access="isAuthenticated()">
    <div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="editUserModalLabel">Edit My Profile</h4>
                </div>
                <div class="modal-body" id="edit-user-modal-body">
                    <div class="existing-user-image" style="display: none; float: left;position: relative;">
                        <img src="" class="img img-responsive" id="uimage">
                        <a href="#" id="uimage-del" class="delete-image" data-image-path=""><span class="glyphicon glyphicon-remove"></span></a>
                    </div>
                    <form style="display: none;" method="post" id="up-user-img" enctype="multipart/form-data">
                        <input name="files[]" class="file" type="file" multiple=false  data-upload-async="false" data-upload-url="/pic/primary/users/0">
                        <br/>
                    </form>
                    <form class="form-horizontal" id="form-edit-user" method="put">
                        <fieldset>
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="ufullname">Full Name</label>
                                <div class="col-md-10">
                                    <input id="ufullname" name="ufullname" class="form-control input-md">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label" for="uemail">E-mail</label>
                                <img id="uimage" class="img img-responsive col-md-2" src="">
                                <div class="col-md-10">
                                    <input type="email" name="uemail" id="uemail" class="form-control input-md" required="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label" for="upassword">Password</label>
                                <div class="col-md-10">
                                    <input id="upassword" name="upassword" type="password" class="form-control input-md" required="">
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button id="edit-user-btn" type="button" class="btn btn-primary">Save</button>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>

<div class="modal fade" id="deleteNewsModal" tabindex="-1" role="dialog" aria-labelledby="deleteNewsModal" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                Delete this News?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="delete-news-btn" type="button" class="btn btn-primary">OK</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editNewsModal" tabindex="-1" role="dialog" aria-labelledby="editNewsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="editNewsModalLabel">Edit News</h4>
            </div>
            <div class="modal-body">
                <form Class="form-horizontal" id="form-edit-news" method="put">
                    <fieldset>
                        <!-- Title -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="mtitle">Title</label>
                            <div class="col-md-10">
                                <input id="mtitle" name="mtitle" class="form-control input-md">
                            </div>
                        </div>

                        <!-- Contents -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="mcontents">Contents</label>
                            <div class="col-md-10">
                                <textarea name="mcontents" id="mcontents" rows="10" cols="80"></textarea>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="edit-news-btn" type="button" class="btn btn-primary">Edit</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addNewsModal" tabindex="-1" role="dialog" aria-labelledby="addNewsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addNewsModalLabel">Add News</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form-add-news" method="put" >
                    <fieldset>
                        <!-- Title -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="atitle">Title</label>
                            <div class="col-md-10">
                                <input id="atitle" name="atitle" class="form-control input-md">
                            </div>
                        </div>

                        <!-- Image -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="aimage">Image</label>
                            <div class="col-md-10">
                                <input id="aimage" name="aimage" class="form-control input-md" type="file">
                            </div>
                        </div>

                        <!-- Contents -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="acontents">Contents</label>
                            <div class="col-md-10">
                                <textarea name="acontents" id="acontents" rows="10" cols="80"></textarea>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="add-news-btn" type="button" class="btn btn-primary">Edit</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>