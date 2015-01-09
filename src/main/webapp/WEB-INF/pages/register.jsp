<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Register</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">
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
</body>
</html>