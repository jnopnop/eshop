<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title>Sign in</title>
</head>
<body>
<!-- Page content -->
<div id="page-content-wrapper">
    <!-- Keep all page content within the page-content inset div! -->
    <div class="page-content inset">
        <c:if test="${not empty param.error}">
            <div class="error">${param.error}</div>
        </c:if>
        <form name="f" action="/register" method="POST">
            <fieldset>
                <legend>Register an account</legend>
                <label for="fullname">Full Name</label>
                    <input type="fullname" id="fullname" name="fullname" required="required"/>
                <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" required="required"/>
                <label for="password">Password</label>
                    <input type="password" id="password" name="password" required="required"/>
                <div class="form-actions">
                    <input type="submit" class="btn">Log in</button>
                </div>
            </fieldset>
        </form>
    </div>
</div>
</div>
</body>
</html>