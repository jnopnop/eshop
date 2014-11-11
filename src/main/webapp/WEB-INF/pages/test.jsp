<html>
<head>
    <title>Hello World (Dynamic)</title>
    <style type='text/css'>
        .date { font-weight: bold; padding: 10px; font-size: larger; }
    </style>
</head>
<body>

<p>This page demonstrates that dynamic content can be decorated in the same way as static content.</p>
<p>This is a simple JSP that shows the date and time on the server is now:</p>

<div class='date'><%= new java.util.Date() %></div>

<p>Of course, with SiteMesh you are not limited to JSP. Because it's a Servlet Filter, both content and decorators can be
    generated by any technology in your ServletEngine, including: static files, JSP, Velocity, FreeMarker, JSF, MVC frameworks, JRuby.... you get the point.</p>

</body>
</html>
