var allData = {fresh: false};

var fillMovieData = function (id) {
    $.get('/admin/movies/'+id, function (data) {
        $('#mtitle').val(data.title);
        $('#mdescription').val(data.description);
        $('#mrating').val(data.rating);
        $('#mduration').val(data.duration);
        $('#mreleaseDate').val(data.releaseDate);
        $('#mimageURL').val(data.imageURL);
        $('#mimdbId').val(data.imdbId);
        $('select#mageCategory').val(data.ageCategory);
        $('select#mageCategory').trigger("chosen:updated");

        $('select#mgenres').val(_.keys(data.genres));
        $('select#mgenres').trigger("chosen:updated");

        $('select#mcountries').val(_.keys(data.countries));
        $('select#mcountries').trigger("chosen:updated");

        $('select#mdirectors').val(_.keys(data.directors));
        $('select#mdirectors').trigger("chosen:updated");

        $('select#mwriters').val(_.keys(data.writers));
        $('select#mwriters').trigger("chosen:updated");

        $('select#mactors').val(_.keys(data.actors));
        $('select#mactors').trigger("chosen:updated");
    });
};

var populateEditForm = function(data, handler, arg0) {
    $('form#form-edit-movie')[0].reset();

    //Genres
    var currItem = data['genres'];
    $('select#mgenres').html('');
    for (var key in currItem) {
        $('select#mgenres')
            .append('<option value="' + key + '">' + currItem[key] + '</option>');
    }
    $('select#mgenres').chosen();
    //$('select#genres').trigger("liszt:updated");

    //Age categories
    currItem = data['categories'];
    $('select#mageCategory').html('');
    for (var key in currItem) {
        $('select#mageCategory')
            .append('<option value="' + currItem[key] + '">' + currItem[key] + '</option>');
    }
    $('select#mageCategory').chosen();
    //$('select#ageCategory').trigger("liszt:updated");

    //Countries
    currItem = data['countries'];
    $('select#mcountries').html('');
    for (var key in data['countries']) {
        $('select#mcountries')
            .append('<option value="' + key + '">' + currItem[key] + '</option>');
    }
    $('select#mcountries').chosen();
    $('select#mcountries').trigger("liszt:updated");

    //Persons
    currItem = data['persons'];
    $('select#mdirectors').html('');
    $('select#mwriters').html('');
    $('select#mactors').html('');
    for (var key in currItem) {
        var option = '<option value="' + key + '">' + currItem[key] + '</option>';
        $('select#mdirectors').append(option);
        $('select#mwriters').append(option);
        $('select#mactors').append(option);
    };
    $('select#mdirectors').chosen();
    $('select#mwriters').chosen();
    $('select#mactors').chosen();

    handler(arg0);
};

var refreshAllData = function (handler, arg0, arg1) {
    $.get('/admin/movies/info', function (data) {
        allData['genres'] = data['genres'];
        allData['categories'] = data['categories'];
        allData['countries'] = data['countries'];
        allData['persons'] = data['persons'];
        allData['fresh'] = true;
        handler(allData, arg0, arg1);
    });
};


jQuery(function ($) {

    $('.chzn').chosen({allow_single_deselect: true});
    $('#search-form').removeClass('in');

    $('#editMovieModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('movie-id');

        $('#edit-movie-btn').data('movie-id', ID);

        if (!allData['fresh']) {
            refreshAllData(populateEditForm, fillMovieData, ID);
        } else {
            populateEditForm(allData, fillMovieData, ID);
        }
    });

    $('#deleteMovieModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var ID = button.data('movie-id') // Extract info from data-* attributes

        $('#delete-movie-btn').data('movie-id', ID);
    });

    $('#edit-movie-btn').click(function() {
        var movieID = '';//$(this).data('movie-id');
        var genres = {};
        $.each($('select#mgenres').chosen().val(), function() {
            genres[''+this] = '' + this;
        });

        var countries = {};
        $.each($('select#mcountries').val(), function() {
            countries[''+this] = '' + this;
        });

        var directors = {};
        $.each($('select#mdirectors').val(), function() {
            directors[''+this] = '' + this;
        });

        var writers = {};
        $.each($('select#mwriters').val(), function() {
            writers[''+this] = '' + this;
        });

        var actors = {};
        $.each($('select#mactors').val(), function() {
            actors[''+this] = '' + this;
        });

        var newMovie = {
            "id": movieID,
            "title": $('#mtitle').val(),
            "description": $('#mdescription').val(),
            "duration": $('#mduration').val(),
            "releaseDate": $('#mreleaseDate').val(),
            "rating": $('#mrating').val(),
            "imageURL": $('#mimageURL').val(),
            "imdbId": $('#mimdbId').val(),
            "ageCategory": $('select#mageCategory').val(),
            "genres": genres,
            "countries": countries,
            "directors": directors,
            "writers": writers,
            "actors": actors
        };
        $.ajax({
            type: "PUT",
            url: "/admin/edit/movie/",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newMovie),
            success: function(result) {
                debugger;
                if (result.success) {
                    location.reload();
                } else {
                    $.jGrowl('An error occurred while editing movie...');
                }
                $('#editMovieModal').modal('hide');
            },
            complete: function() {
             //
            }
        });
    });

    $('#delete-movie-btn').click(function() {
        var delID = $(this).data('movie-id');
        $.ajax({
            url: '/admin/deleteById/movie/'+delID+'.json',
            type: 'DELETE',
            success: function(result) {
                //debugger;
                //alert(result.success);
                if (result.success) {
                    $.jGrowl('Movie has been successfully deleted', {
                        theme: 'smoke',
                        open: function() {
                            console.log($(this).html());
                        }
                    });
                    $('#item-movie-'+delID).remove();
                } else {
                    $.jGrowl('An error occurred while deleting movie...');
                }

                $('#deleteMovieModal').modal('hide');
            },
            error: function() {
                $.jGrowl('An error occurred while deleting movie...');
                $('#deleteMovieModal').modal('hide');
            }
        });
    });

    var parseDate = function(str) {
        var dateParts = str.split('/');
        return new Date(dateParts[2], dateParts[1], dateParts[0]).getTime();
    };

    $('#addmovie').click(function () {
        var genres = {};
        $.each($('#genres').val(), function() {
            genres[''+this] = '' + this;
        });

        var countries = {};
        $.each($('#countries').val(), function() {
            countries[''+this] = '' + this;
        });

        var directors = {};
        $.each($('#directors').val(), function() {
            directors[''+this] = '' + this;
        });

        var writers = {};
        $.each($('#writers').val(), function() {
            writers[''+this] = '' + this;
        });

        var actors = {};
        $.each($('#actors').val(), function() {
            actors[''+this] = '' + this;
        });

        var newMovie = {
            "title": $('#title').val(),
            "description": $('#description').val(),
            "duration": $('#duration').val(),
            "releaseDate": parseDate($('#releaseDate').val()),
            "rating": $('#rating').val(),
            "imageURL": $('#imageURL').val(),
            "imdbId": $('#imdbId').val(),
            "ageCategory": {
                "id": $('#ageCategory').val(),
                "category": "category"
            },
            "genres": genres,
            "countries": countries,
            "directors": directors,
            "writers": writers,
            "actors": actors
        };
        $.ajax({
            type: "POST",
            url: "/movie/add",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newMovie)
        });
    });
});