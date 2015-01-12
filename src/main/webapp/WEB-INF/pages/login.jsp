<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title>Sign in</title>
</head>
<body>
<!-- Page content -->
<div id="page-content-wrapper">
    <!-- Keep all page content within the page-content inset div! -->
    <div class="page-content inset" style="margin: 0px auto;width: 50%;margin-top: 100px;">
        <div class="well movie">
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
        <form class="form-horizontal" action="<c:url value="/login" />" method="POST">
            <fieldset>
                <legend>Sign in with existing account or <a href="/register">register</a></legend>

                <div class="form-group">
                    <label class="col-md-2 control-label" for="username">E-mail</label>
                    <img id="uimage" class="img img-responsive col-md-2" src="">
                    <div class="col-md-10">
                        <input type="email" name="username" id="username" class="form-control input-md" required="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label" for="password">Password</label>
                    <div class="col-md-10">
                        <input id="password" name="password" type="password" class="form-control input-md" required="">
                    </div>
                </div>

                <button type="submit" class="btn">Log in</button>
            </fieldset>
        </form>
    </div>
    </div>
</div>
</body>
</html>