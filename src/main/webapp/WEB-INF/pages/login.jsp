<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Signin</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
        <form name="f" action="<c:url value="/login" />" method="POST">
            <fieldset>
                <legend>Sign in with existing account or <a href="/register">register</a></legend>
                <label for="username">Username</label>
                    <input type="text" id="username" name="username"/>
                <label for="password">Password</label>
                    <input type="password" id="password" name="password"/>
                <!--input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /-->
                <div class="form-actions">
                    <button type="submit" class="btn">Log in</button>
                </div>
            </fieldset>
        </form>
    </div>
</body>
</html>