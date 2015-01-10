<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="sidebar-wrapper">
    <ul id="sidebar_menu" class="sidebar-nav">
        <li class="sidebar-brand"><a id="menu-toggle" href="/">
            <sec:authentication var="principal" property="principal" />
            <sec:authorize access="isAnonymous()">
                <i>eshop</i>
                <span id="main_icon" class="glyphicon glyphicon-align-justify"></span>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <i>${principal.username}</i>
                <img src="/pic/users/me" id="main_icon" >
            </sec:authorize>
        </a></li>
    </ul>
    <ul class="sidebar-nav" id="sidebar">
        <li><a href="/news">Home<span class="sub_icon glyphicon glyphicon-home"></span></a></li>
        <li><a href="/movies">Movies<span class="sub_icon glyphicon glyphicon-film"></span></a></li>
        <li><a href="/persons">Persons<span class="sub_icon glyphicon glyphicon-star-empty"></span></a></li>
        <li><a href="/search">Search<span class="sub_icon glyphicon glyphicon-filter"></span></a></li>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="/users">Users<span class="sub_icon glyphicon glyphicon-user"></span></a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li><a href="#" data-toggle="modal" data-target="#editUserModal" data-user-id="${si.id}">Profile<span class="sub_icon glyphicon glyphicon-edit"></span></a></li>
            <li><a href="/logout">Logout<span class="sub_icon glyphicon glyphicon-log-out"></span></a></li>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <li><a href="/login">Sign in<span class="sub_icon glyphicon glyphicon-log-in"></span></a></li>
        </sec:authorize>
    </ul>
</div>