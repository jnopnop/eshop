<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title>Sign in</title>

    <script src="/js/register.js"></script>
</head>
<body>
<!-- Page content -->
<div id="page-content-wrapper">
    <!-- Keep all page content within the page-content inset div! -->
    <div class="well page-content inset" style="margin: 0px auto;width: 60%;margin-top: 100px;">
        <c:if test="${not empty param.error}">
            <div class="error">${param.error}</div>
        </c:if>
        <form class="form-horizontal" id="register-form" action="/register" method="POST">
            <fieldset>
                <legend>Register an account</legend>

                <div class="form-group">
                    <label class="col-md-2 control-label" for="fullname">Full Name</label>
                    <div class="col-md-10">
                        <input type="text" name="fullname" id="fullname" class="form-control input-md" required="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label" for="email">E-mail</label>
                    <img id="uimage" class="img img-responsive col-md-2" src="">
                    <div class="col-md-10">
                        <input type="email" name="email" id="email" class="form-control input-md" required="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label" for="password">Password</label>
                    <div class="col-md-10">
                        <input id="password" name="password" type="password" class="form-control input-md" required="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label" for="password2">Re-type password</label>
                    <div class="col-md-10">
                        <input id="password2" name="password2" type="password" class="form-control input-md" required="">
                    </div>
                </div>

                <button type="submit" id="btn-register" class="btn">Log in</button>
            </fieldset>
        </form>
        <%--<form name="f" action="/register" method="POST">--%>
            <%--<fieldset>--%>
                <%--<legend>Register an account</legend>--%>
                <%--<label for="fullname">Full Name</label>--%>
                    <%--<input type="fullname" id="fullname" name="fullname" required="required"/>--%>
                <%--<label for="email">E-mail</label>--%>
                    <%--<input type="email" id="email" name="email" required="required"/>--%>
                <%--<label for="password">Password</label>--%>
                    <%--<input type="password" id="password" name="password" required="required"/>--%>
                <%--<div class="form-actions">--%>
                    <%--<input type="submit" class="btn">Log in</button>--%>
                <%--</div>--%>
            <%--</fieldset>--%>
        <%--</form>--%>
    </div>
</div>
</div>
</body>
</html>