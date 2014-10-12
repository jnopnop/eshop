<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>See what we have</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="http://www.shieldui.com/shared/components/latest/css/shieldui-all.min.css" />
    <link rel="stylesheet" type="text/css" href="http://www.shieldui.com/shared/components/latest/css/light/all.min.css" />
    <script type="text/javascript" src="http://www.shieldui.com/shared/components/latest/js/shieldui-all.min.js"></script>
    <style>
        html {
            background: url(/img/main-background.jpg) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }

        body {
            margin-top: 40px;
            font-size: 16px;
            background: transparent;
        }

        .panel {
            background-color: rgba(255, 255, 255, 0.9);
        }

        a.list-group-item {
            height: auto;
            min-height: 250px;
        }

        a.list-group-item:hover, a.list-group-item:focus {
            border-left:10px solid #797778;;
            border-right:10px solid #797778;;
        }

        a.list-group-item {
            border-left:10px solid transparent;
            border-right:10px solid transparent;
        }

        div.well {
            background: rgba(240, 237, 239, 0.85);
        }
    </style>

    <script>
        jQuery(function ($) {
            $('.rate').each(function(){
                $(this).shieldRating({
                    max: 10,
                    step: 0.1,
                    value: $(this).data('imdbrating'),
                    markPreset: false
                });
            });
        });
    </script>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-lg-12 col-md-12 panel panel-default">
                    <nav class="navbar navbar-default navbar-fixed-top">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="#">GroovyMovie</a>
                        </div>

                        <div class="collapse navbar-collapse">
                            <ul class="nav navbar-nav pull-right">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">User <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Action</a></li>
                                        <li><a href="#">Another action</a></li>
                                        <li><a href="#">Something else here</a></li>
                                        <li class="nav-header">Nav header</li>
                                        <li><a href="#">Separated link</a></li>
                                        <li><a href="#">One more separated link</a></li>
                                    </ul>
                                </li>
                            </ul>
                            <form class="navbar-form">
                                <input type="search" class="form-control pull-left" name="q" />
                            </form>
                        </div><!--/.nav-collapse -->
                    </nav>
            </div>
        </div>

    <div class="row">
        <div class="well">
            <h1 class="text-center">Search results</h1>
            <div class="list-group">
                <c:if test="${not empty movies}">
                    <c:forEach items="${movies}" var="si">
                        <a href="#" class="list-group-item">
                            <div class="media col-md-3">
                                <figure class="pull-left">
                                    <c:set var="mainImg" value="${not empty si.imageURL ? si.imageURL:'http://placehold.it/317x214'}"></c:set>
                                    <img style="width:150px;" class="img-responsive img-thumbnail" src="${mainImg}">
                                </figure>
                            </div>
                            <div class="col-md-9">
                                <h4 class="list-group-item-heading">${si.title} (<fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>) ${si.rating}/10</h4>
                                <h5 id="rate-${si.id}" class="rate" data-imdbrating="${si.rating}"></h5>
                                <p class="list-group-item-text">
                                        ${fn:substring(si.description, 0, 360)}...
                                </p>
                            </div>
                        </a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>



            <%--<c:if test="${not empty movies}">--%>
                <%--<c:forEach items="${movies}" var="si">--%>
                    <%--<div class="row">--%>
                        <%--<div class="col-md-2">--%>
                            <%--<a href="#">--%>
                                <%--<c:set var="mainImg" value="${not empty si.imageURL ? si.imageURL:'http://placehold.it/317x214'}"></c:set>--%>
                                <%--<img class="img-responsive img-thumbnail" src="${mainImg}">--%>
                            <%--</a>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-10">--%>
                            <%--<h2>${si.title}</h2>--%>
                            <%--<h5><fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate></h5>--%>
                            <%--<h4>--%>
                                <%--<c:forEach items="${si.genres}" var="gg">--%>
                                    <%--${gg.title}&NonBreakingSpace;--%>
                                <%--</c:forEach>--%>
                            <%--</h4>--%>
                            <%--<h5>${si.duration} min</h5>--%>
                            <%--<span class="rating">${si.rating}/10</span>--%>
                            <%--<span class="age-cat">${si.ageCategory.category}</span>--%>
                            <%--<p>--%>
                                <%--${fn:substring(si.description, 0, 360)}...--%>
                            <%--</p>--%>
                            <%--<p>--%>
                                <%--<c:forEach items="${si.persons}" var="p">--%>
                                    <%--${p.person.fullname}&NonBreakingSpace;(${p.career.title})--%>
                                <%--</c:forEach>--%>
                            <%--</p>--%>
                            <%--<a class="btn btn-primary" href="#">More<span class="glyphicon glyphicon-chevron-right"></span></a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</c:forEach>--%>
            <%--</c:if>--%>
</div> <!-- /container -->



</body>
</html>