<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Advanced Search</title>
</head>
<body>
<%@ include file="common/navbar.jsp" %>
<!-- Page content -->
<div id="page-content-wrapper">
    <!-- Keep all page content within the page-content inset div! -->
    <div class="page-content inset">
        <div class="well movie">
            <form:form method="get" action="/movies/search" commandName="sm" cssClass="form-horizontal" id="movie-search">
                <fieldset>
                    <legend>Search Movie</legend>

                    <div class="form-group">
                        <form:label  cssClass="col-md-4 control-label" path="title">Title</form:label>
                        <div class="col-md-4">
                            <form:input path="title" cssClass="form-control input-md"></form:input>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label path="year_start" cssClass="col-md-4 control-label">Begin Year</form:label>
                        <div class="col-md-4">
                            <form:select path="year_start" items="${years}" cssClass="form-control"></form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label path="year_end" cssClass="col-md-4 control-label">End Year</form:label>
                        <div class="col-md-4">
                            <form:select path="year_end" items="${years}" cssClass="form-control"></form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label path="country" cssClass="col-md-4 control-label">Country</form:label>
                        <div class="col-md-4">
                            <form:select path="country" items="${countries}" cssClass="form-control"></form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label class="col-md-4 control-label" path="ageCategory">Age Category</form:label>
                        <div class="col-md-4">
                            <form:select path="ageCategory" cssClass="form-control" items="${ageCategories}"></form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label class="col-md-4 control-label" path="genres">Genres</form:label>
                        <div class="col-md-4">
                            <form:select path="genres" items="${genres}" cssClass="form-control" multiple="multiple"></form:select>
                        </div>
                    </div>

                    <input type="submit" name="submit_movie" id="submit_movie" value="Search" class="btn btn-info pull-right">
                </fieldset>
            </form:form>
            <%--<form class="form-horizontal" id="movie-search" method="get" action="/movies/search">--%>
                <%--<fieldset>--%>

                    <%--<!-- Form Name -->--%>
                    <%--<legend>Search Movies</legend>--%>

                    <%--<!-- Text input-->--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-md-4 control-label" for="title">Title</label>--%>
                        <%--<div class="col-md-4">--%>
                            <%--<input id="title" name="title" type="text" placeholder="" class="form-control input-md">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<!-- Select Basic -->--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-md-4 control-label" for="year_start">from</label>--%>
                        <%--<div class="col-md-4">--%>
                            <%--<select id="year_start" name="year_start" class="form-control">--%>
                                <%--<c:forEach var="g" begin="1920" end="2015">--%>
                                    <%--<option value="${g}">${g}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<!-- Select Basic -->--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-md-4 control-label" for="year_end">from</label>--%>
                        <%--<div class="col-md-4">--%>
                            <%--<select id="year_end" name="year_end" class="form-control">--%>
                                <%--<c:forEach var="g" begin="1920" end="2015">--%>
                                    <%--<option value="${g}">${g}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<!-- Select Basic -->--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-md-4 control-label" for="country">Country</label>--%>
                        <%--<div class="col-md-4">--%>
                            <%--<select id="country" name="country" class="form-control">--%>
                                <%--<c:forEach var="c" items="${countries}">--%>
                                    <%--<option value="${c.value}">${c.value}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<!-- Select Basic -->--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-md-4 control-label" for="ageCategory">Age Category</label>--%>
                        <%--<div class="col-md-4">--%>
                            <%--<select id="ageCategory" name="ageCategory" class="form-control">--%>
                                <%--<c:forEach var="a" items="${ageCategories}">--%>
                                    <%--<option value="${a}">${a}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<!-- Select Multiple -->--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-md-4 control-label" for="genres">Genres</label>--%>
                        <%--<div class="col-md-4">--%>
                            <%--<select id="genres" name="genres[]" class="form-control" multiple="multiple">--%>
                                <%--<c:forEach var="g" items="${genres}">--%>
                                    <%--<option value="${g.value}">${g.value}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<input type="submit" name="submit_movie" id="submit_movie" value="Search" class="btn btn-info pull-right">--%>
                <%--</fieldset>--%>
            <%--</form>--%>

            <form class="form-horizontal" id="person-search">
                <fieldset>

                    <!-- Form Name -->
                    <legend>Search Persons</legend>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="fullname">Name</label>
                        <div class="col-md-4">
                            <input id="fullname" name="fullname" type="text" placeholder="" class="form-control input-md" required="">
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="year_born">from</label>
                        <div class="col-md-4">
                            <select id="year_born" name="year_born" class="form-control">
                                <c:forEach var="g" begin="1920" end="2015">
                                    <option value="${g}">${g}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="movie_title">Movie</label>
                        <div class="col-md-4">
                            <input id="movie_title" name="movie_title" type="text" placeholder="" class="form-control input-md">
                        </div>
                    </div>
                    <input type="submit" name="submit_person" id="submit_person" value="Search" class="btn btn-info pull-right">
                </fieldset>
            </form>


        </div>
    </div>
</div>
</body>
</html>
