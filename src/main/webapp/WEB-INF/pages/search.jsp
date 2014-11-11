<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Advanced Search</title>

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
            min-height: 350px;
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
//            $('.rate').each(function(){
//                $(this).shieldRating({
//                    max: 10,
//                    step: 0.1,
//                    value: $(this).data('imdbrating'),
//                    markPreset: false
//                });
//            });

//            $.get('/movies', function( data ) {
//               alert("success");
//            });
        });
    </script>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-lg-12 col-md-12 panel panel-default">
            <div class="navbar navbar-inverse navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">GroovyMovie</a>
                    </div>
                    <div class="navbar-collapse collapse" id="searchbar">

                        <ul class="nav navbar-nav navbar-right">
                            <sec:authorize ifAnyGranted="ROLE_USER">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.request.userPrincipal.name}<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Logout</a></li>
                                    </ul>
                                </li>
                            </sec:authorize>
                            <li><a href="about.html">Advanced Search</a></li>
                            <li><a href="about.html">Most Rated</a></li>
                            <li><a href="about.html">About</a></li>
                        </ul>
                        <form class="navbar-form">
                            <div class="form-group" style="display:inline;">
                                <div class="input-group">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
                                    <input class="form-control input-large" name="search" placeholder="Search Here" autocomplete="off" autofocus="autofocus" type="text">
                                </div>
                            </div>
                        </form>

                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="well">

            <div class="row">
                <form class="form-horizontal">
                    <fieldset>

                        <!-- Form Name -->
                        <legend>Advanced Movie Search</legend>

                        <!-- Search input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="searchinput">Keyword in Title</label>
                            <div class="col-md-4">
                                <input id="searchinput" name="searchinput" type="search" placeholder="title" class="form-control input-md" required="">

                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="textinput">Rating</label>
                            <div class="col-md-4">
                                <input id="textinput" name="textinput" type="text" placeholder="rating" class="form-control input-md">
                                <span class="help-block">1.0-10.0</span>
                            </div>
                        </div>

                        <!-- Select Basic -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="selectbasic">Begin Year</label>
                            <div class="col-md-4">
                                <select id="selectbasic" name="selectbasic" class="form-control">
                                    <option value="1">Option one</option>
                                    <option value="2">Option two</option>
                                </select>
                            </div>
                        </div>

                        <!-- Select Basic -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="selectbasic">End Year</label>
                            <div class="col-md-4">
                                <select id="selectbasic" name="selectbasic" class="form-control">
                                    <option value="1">Option one</option>
                                    <option value="2">Option two</option>
                                </select>
                            </div>
                        </div>

                        <!-- Button -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="submit"></label>
                            <div class="col-md-4">
                                <button id="submit" name="submit" class="btn btn-primary btn-submit">Search!</button>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>

            <div class="row">
                <form class="form-horizontal">
                    <fieldset>

                        <!-- Form Name -->
                        <legend>Advanced Movie Search</legend>

                        <!-- Search input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="searchinput">Keyword in Title</label>
                            <div class="col-md-4">
                                <input id="searchinput" name="searchinput" type="search" placeholder="title" class="form-control input-md" required="">

                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="textinput">Rating</label>
                            <div class="col-md-4">
                                <input id="textinput" name="textinput" type="text" placeholder="rating" class="form-control input-md">
                                <span class="help-block">1.0-10.0</span>
                            </div>
                        </div>

                        <!-- Select Basic -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="selectbasic">Begin Year</label>
                            <div class="col-md-4">
                                <select id="selectbasic" name="selectbasic" class="form-control">
                                    <option value="1">Option one</option>
                                    <option value="2">Option two</option>
                                </select>
                            </div>
                        </div>

                        <!-- Select Basic -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="selectbasic">End Year</label>
                            <div class="col-md-4">
                                <select id="selectbasic" name="selectbasic" class="form-control">
                                    <option value="1">Option one</option>
                                    <option value="2">Option two</option>
                                </select>
                            </div>
                        </div>

                        <!-- Button -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="submit"></label>
                            <div class="col-md-4">
                                <button id="submit" name="submit" class="btn btn-primary btn-submit">Search!</button>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div> <!-- /container -->



</body>
</html>
