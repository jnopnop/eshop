<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>

    <link href="/css/chosen/chosen.min.css" rel="stylesheet">
    <link href="/css/jgrowl/jgrowl.min.css" rel="stylesheet">
    <link href="/css/datepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/css/bootstrap-switch/bootstrap-switch.min.css" rel="stylesheet">
    <link href="/css/admin.css" rel="stylesheet">
    <link href="/css/users.css" rel="stylesheet">

    <script src="/js/chosen/chosen.jquery.min.js"></script>
    <script src="/js/datepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/js/bootstrap-switch/bootstrap-switch.min.js"></script>
    <script src="/js/users.js"></script>
</head>
<body>
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
                                <form role="search" method="get" action="/users">
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
                <div class="row">
                    <ul class="pager">
                        <c:set var="hereClass" value="${users.currPage <= 1 ? 'disabled':''}"></c:set>
                        <c:set var="thereClass" value="${users.currPage >= users.lastPage ? 'disabled':''}"></c:set>
                        <li class="previous ${hereClass}">
                            <a href="/users?p=${users.currPage - 1}&<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>">&larr; Here</a>
                        </li>
                        <li class="next ${thereClass}">
                            <a href="/users?p=${users.currPage + 1}&<%=request.getQueryString() != null ? request.getQueryString().replaceAll("&p=[\\d]*", "") : ""%>">There &rarr;</a>
                        </li>
                    </ul>
                </div>
                <div class="list-group">
                    <c:choose>
                        <c:when test="${empty users.results}">
                            <h2>Nothing was found. You're trying to do something odd, aren't you?!</h2>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${users.results}" var="si">
                                <div class="list-group-item row <c:if test="${si.admin}">user-admin</c:if>" data-user-id="${si.id}">
                                    <div class="col-md-2">
                                        <figure class="pull-left">
                                            <c:choose>
                                                <c:when test="${not empty si.image}">
                                                    <img class="img-responsive img-thumbnail" src="${si.image}" style="max-width: 50px;">
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="img-responsive img-thumbnail" src="/pic/users/notfound">
                                                </c:otherwise>
                                            </c:choose>
                                        </figure>
                                    </div>
                                    <div class="col-md-2 col-admin">
                                        <span>Admin</span>
                                        <input type="checkbox" class="toggle-admin" data-id="${si.id}" <c:if test="${si.admin}">checked</c:if>>
                                    </div>
                                    <div class="col-md-7">
                                        <h4>${si.fullname}</h4>
                                        <h5>${si.email}</h5>
                                    </div>
                                    <div class="col-md-1">
                                        <span class="admin-delete"><a href="#" data-toggle="modal" data-target="#deleteUserModal" data-user-id="${si.id}"><span class="glyphicon glyphicon-remove"></span></a></span>
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

<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="deleteUserModal" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                Delete this user?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="delete-user-btn" type="button" class="btn btn-primary">OK</button>
            </div>
        </div>
    </div>
</div>

</body>
</body>
</html>
