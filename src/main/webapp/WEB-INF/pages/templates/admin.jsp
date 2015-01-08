<script id="movie-tpl" type="text/x-jsrender">
    <div class="list-group-item row">
        <div class="media col-md-2">
            <figure class="pull-left">
                <img style="width:150px;" class="img-responsive img-thumbnail" src="/pic/movies/${si.imageURL}">
            </figure>
        </div>
        <div class="col-md-9">
            <a href="/movie/${si.id}"><h4 class="list-group-item-heading">${si.title} (<fmt:formatDate value="${si.releaseDate}" type="DATE" pattern="yyyy"></fmt:formatDate>)</h4></a>
            <span class="rate" data-imdbrating="${si.rating}"></span>
            <span class="movie-rating">(${si.rating}/10)</span>
            <br/>
            <span class="movie-duration"><b><i>${si.duration} min</i></b></span>
            <div class="movie-genres">
                <c:forEach items="${si.genres}" var="g">
<a href="/movies?genre=${g.key}">${g.value}</a>
</c:forEach>
            </div>
            <p class="list-group-item-text">
                    ${fn:substring(si.description, 0, 360)}...
            </p>
            <div class="movie-persons">
                Directed by:
                <c:forEach items="${si.directors}" var="d">
<a href="/persons?id=${d.key}">${d.value}&NonBreakingSpace;</a>
</c:forEach>
                <br/>
                Main roles:
                <c:forEach items="${si.actors}" var="a">
<a href="/persons?id=${a.key}">${a.value}&NonBreakingSpace;</a>
</c:forEach>
            </div>
        </div>
        <div class="col-md-1 admin-controls">
<button data-id="${si.id}" type="button" class="edit-item">&hellip;</button>
<button data-id="${si.id}" type="button" class="delete-item">&times;</button>
        </div>
    </div>
</script>
