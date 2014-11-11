<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Admin [Movies]</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/chosen.min.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/chosen.jquery.min.js"></script>
    <script src="/js/jquery.serializejson.min.js"></script>
    <script src="/js/admin.js"></script>
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

        .well {
            background: rgba(240, 237, 239, 0.85);
        }
    </style>
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
                    <a class="navbar-brand" href="#">GroovyMovieAdmin</a>
                </div>

                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav pull-right">
                        <sec:authorize ifAnyGranted="ROLE_USER">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.request.userPrincipal.name}<b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Logout</a></li>
                                </ul>
                            </li>
                        </sec:authorize>
                        <li class="active"><a href="#">Movies</a></li>
                        <li><a href="/padmin">Persons</a></li>
                    </ul>
                    <form class="navbar-form">
                        <input type="search" class="form-control pull-left" name="q" />
                    </form>
                </div><!--/.nav-collapse -->
            </nav>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal well" id="form-add-movie">
                <fieldset>

                    <!-- Form Name -->
                    <legend>Form Name</legend>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="title">Title</label>
                        <div class="col-md-4">
                            <input id="title" name="title" type="text" placeholder="" class="form-control input-md">

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="description">Description</label>
                        <div class="col-md-4">
                            <input id="description" name="description" type="text" placeholder="" class="form-control input-md">

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="duration">Duration</label>
                        <div class="col-md-4">
                            <input id="duration" name="duration" type="text" placeholder="" class="form-control input-md">

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="releaseDate">Release Date</label>
                        <div class="col-md-4">
                            <input id="releaseDate" name="releaseDate" type="text" placeholder="" class="form-control input-md">

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="rating">Rating</label>
                        <div class="col-md-4">
                            <input id="rating" name="rating" type="text" placeholder="" class="form-control input-md">

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="imageURL">Poster Picture</label>
                        <div class="col-md-4">
                            <input id="imageURL" name="imageURL" type="text" placeholder="http://" class="form-control input-md">

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="imdbId">IMDB ID</label>
                        <div class="col-md-4">
                            <input id="imdbId" name="imdbId" type="text" placeholder="" class="form-control input-md">
                            <span class="help-block">optional</span>
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="ageCategory">Age Category</label>
                        <div class="col-md-4">
                            <select id="ageCategory" name="ageCategory" class="form-control">
                            </select>
                        </div>
                    </div>

                    <!-- Select Multiple -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="genres">Genres</label>
                        <div class="col-md-4">
                            <select id="genres" name="genres" class="form-control" multiple="multiple">
                            </select>
                        </div>
                    </div>

                    <!-- Select Multiple -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="countries">Countries</label>
                        <div class="col-md-4">
                            <select id="countries" name="countries" class="form-control" multiple="multiple">
                            </select>
                        </div>
                    </div>

                    <!-- Select Multiple -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="directors">Directors</label>
                        <div class="col-md-4">
                            <select id="directors" name="directors" class="form-control" multiple="multiple">
                            </select>
                        </div>
                    </div>

                    <!-- Select Multiple -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="writers">Writers</label>
                        <div class="col-md-4">
                            <select id="writers" name="writers" class="form-control" multiple="multiple">
                            </select>
                        </div>
                    </div>

                    <!-- Select Multiple -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="actors">Actors</label>
                        <div class="col-md-4">
                            <select id="actors" name="actors" class="form-control" multiple="multiple">
                            </select>
                        </div>
                    </div>
                </fieldset>
            </form>
            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="addmovie"></label>
                <div class="col-md-4">
                    <button id="addmovie" name="addmovie" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </div>
    </div>
</div> <!-- /container -->
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
