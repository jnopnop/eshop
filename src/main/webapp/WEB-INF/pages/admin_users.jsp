<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users</title>

    <link href="/css/chosen/chosen.min.css" rel="stylesheet">
    <link href="/css/jgrowl/jgrowl.min.css" rel="stylesheet">
    <link href="/css/datepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/css/admin.css" rel="stylesheet">
    <link href="/css/users.css">

    <script src="/js/chosen/chosen.jquery.min.js"></script>
    <script src="/js/datepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/js/users.js"></script>
</head>
<body>
<%@ include file="common/navbar.jsp" %>
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
                        <c:set var="searchBoxClass" value="${query.length() > 0 ? '' : 'collapse'}"></c:set>
                        <div id="search-form" class="panel-collapse ${searchBoxClass}">
                            <div class="panel-body">
                                <form role="search" method="get" action="/admin/persons">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Search" name="q" id="q" <c:if test="${not empty query}"> value="${query}"</c:if>>
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <div class="row">
                        <a class="btn btn-info" href="#" data-toggle="modal" data-target="#addPersonModal">Add Person</a>
                    </div>
                </sec:authorize>
                <div class="row">
                    <ul class="pager">
                        <c:set var="hereClass" value="${persons.currPage <= 1 ? 'disabled':''}"></c:set>
                        <c:set var="thereClass" value="${persons.currPage >= persons.lastPage ? 'disabled':''}"></c:set>
                        <li class="previous ${hereClass}">
                            <a href="/admin/persons?p=${persons.currPage - 1}&<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>">&larr; Here</a>
                        </li>
                        <li class="next ${thereClass}">
                            <a href="/admin/persons?p=${persons.currPage + 1}&<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>">There &rarr;</a>
                        </li>
                    </ul>
                </div>
                <div class="list-group">
                    <%--<!-- CKEditor -->--%>
                    <%--<div class="row">--%>
                    <%--<a href="#" data-toggle="modal" data-target="#ckeditorModal">ckeditor</a>--%>
                    <%--</div>--%>
                    <c:choose>
                        <c:when test="${empty persons.results}">
                            <h2>Nothing was found. You're trying to do something odd, aren't you?!</h2>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${persons.results}" var="si">
                                <div class="list-group-item row" id="item-person-${si.id}">
                                    <div class="media col-md-2">
                                        <figure class="pull-left">
                                            <c:choose>
                                                <c:when test="${not empty si.mainImage}">
                                                    <img style="width:150px;" class="img-responsive img-thumbnail" src="${si.mainImage}">
                                                </c:when>
                                                <c:otherwise>
                                                    <span>No photo</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </figure>
                                    </div>
                                    <div class="col-md-10">
                                        <a href="/person/${si.id}" target="_blank"><span class="list-group-item-heading admin-person-title">${si.fullname} (<fmt:formatDate value="${si.birthdate}" type="DATE" pattern="yyyy"></fmt:formatDate>)</span></a>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <span class="admin-delete"><a href="#" data-toggle="modal" data-target="#deletePersonModal" data-person-id="${si.id}"><span class="glyphicon glyphicon-remove"></span></a></span>
                                            <span class="admin-edit"><a href="#" data-toggle="modal" data-target="#editPersonModal" data-person-id="${si.id}"><span class="glyphicon glyphicon-edit"></span></a></span>
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

<div class="modal fade" id="ckeditorModal" tabindex="-1" role="dialog" aria-labelledby="ckeditorModal" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <form Class="form-horizontal" id="ckeditor-form" method="put">
                    <fieldset>
                        <!-- Fullname -->
                        <div class="form-group">
                            <div class="col-md-12">
                                <textarea name="editor1" id="editor1" rows="10" cols="80">
                                    This is my textarea to be replaced with CKEditor.
                                </textarea>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="ckeditor-submit-btn" type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deletePersonModal" tabindex="-1" role="dialog" aria-labelledby="deletePersonModal" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                Delete this person?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="delete-person-btn" type="button" class="btn btn-primary">OK</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editPersonModal" tabindex="-1" role="dialog" aria-labelledby="editPersonModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="editPersonModalLabel">Edit Person</h4>
            </div>
            <div class="modal-body">
                <form Class="form-horizontal" id="form-edit-person" method="put">
                    <fieldset>
                        <!-- Fullname -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="mfullname">Fullname</label>
                            <div class="col-md-10">
                                <input id="mfullname" name="mfullname" placeholder="" class="form-control input-md">
                            </div>
                        </div>

                        <!-- Birthdate -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="mbirthdate">Birthdate</label>
                            <div class="col-md-10">
                                <input id="mbirthdate" name="mbirthdate" class="form-control input-md">
                            </div>
                        </div>

                        <!-- Photo -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="mphotoURL">Photo</label>
                            <div class="col-md-10">
                                <input id="mphotoURL" name="mphotoURL" class="form-control input-md">
                            </div>
                        </div>

                        <!-- ImdbId -->
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="mimdbId">IMDB ID</label>
                            <div class="col-md-10">
                                <input id="mimdbId" name="mimdbId" class="form-control input-md">
                                <span class="help-block">optional</span>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="edit-person-btn" type="button" class="btn btn-primary">Edit</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>