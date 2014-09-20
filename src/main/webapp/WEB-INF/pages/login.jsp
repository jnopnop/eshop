<html>
<head>
    <title>Login Page</title>
</head>
<body>
<div class="content">
    <form name="f" action="/login" method="post">
        <fieldset>
            <legend>Please Login</legend>
            <label for="username">Username</label>
            <input type="text" id="username" name="username"/>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="form-actions">
                <button type="submit" class="btn">Log in</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>