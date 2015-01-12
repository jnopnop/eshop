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
        </div>
    </div>
</div>
</body>
</html>
