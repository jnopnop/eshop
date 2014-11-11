<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                        <li><a href="/search">Advanced Search</a></li>
                        <li><a href="/top">Most Rated</a></li>
                        <li><a href="/about">About</a></li>
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