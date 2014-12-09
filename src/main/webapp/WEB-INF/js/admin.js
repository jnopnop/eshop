/**
 * Created by nop on 15/10/14.
 */
var allData = {fresh: false};

var fillMovieData = function (id) {
    $.get('/admin/movie/'+id, function (data) {
        $('#form-edit-movie #title').val(data.title);
        $('#description').val(data.description);
        $('#rating').val(data.rating);
        $('#duration').val(data.duration);
        $('#releaseDate').val(data.releaseDate);
        $('#imageURL').val(data.imageURL);
        $('#imdbId').val(data.imdbId);
        $('select#ageCategory').val(data.ageCategory);
        $('select#ageCategory').trigger("chosen:updated");

        $('select#genres').val(_.keys(data.genres));
        $('select#genres').trigger("chosen:updated");

        $('select#countries').val(_.keys(data.countries));
        $('select#countries').trigger("chosen:updated");

        $('select#directors').val(_.keys(data.directors));
        $('select#directors').trigger("chosen:updated");

        $('select#writers').val(_.keys(data.writers));
        $('select#writers').trigger("chosen:updated");

        $('select#actors').val(_.keys(data.actors));
        $('select#actors').trigger("chosen:updated");
    });
};

var populateEditForm = function(data, handler, arg0) {
    $('form#form-edit-movie')[0].reset();

    //Genres
    var currItem = data['genres'];
    $('select#genres').html('');
    for (var key in currItem) {
        $('select#genres')
            .append('<option value="' + key + '">' + currItem[key] + '</option>');
    }
    $('select#genres').chosen();
    //$('select#genres').trigger("liszt:updated");

    //Age categories
    currItem = data['categories'];
    $('select#ageCategory').html('');
    for (var key in currItem) {
        $('select#ageCategory')
            .append('<option value="' + currItem[key] + '">' + currItem[key] + '</option>');
    }
    $('select#ageCategory').chosen();
    //$('select#ageCategory').trigger("liszt:updated");

    //Countries
    currItem = data['countries'];
    $('select#countries').html('');
    for (var key in data['countries']) {
        $('select#countries')
            .append('<option value="' + key + '">' + currItem[key] + '</option>');
    }
    $('select#countries').chosen();
    $('select#countries').trigger("liszt:updated");

    //Persons
    currItem = data['persons'];
    $('select#directors').html('');
    $('select#writers').html('');
    $('select#actors').html('');
    for (var key in currItem) {
        var option = '<option value="' + key + '">' + currItem[key] + '</option>';
        $('select#directors').append(option);
        $('select#writers').append(option);
        $('select#actors').append(option);
    };
    $('select#directors').chosen();
    $('select#writers').chosen();
    $('select#actors').chosen();

    handler(arg0);
};

var refreshAllData = function (handler, arg0, arg1) {
    $.get('/admin/movies/alldata', function (data) {
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
        var button = $(event.relatedTarget) // Button that triggered the modal
        var ID = button.data('movie-id') // Extract info from data-* attributes

        $('#edit-movie-btn').data('movie-id', ID);
        $(this).find('form#form-edit-movie').attr('action', '/admin/edit/movie/'+ID);

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
        var movieID = $(this).data('movie-id');
        var genres = {};
        debugger;
        $.each($('select#genres').chosen().val(), function() {
            genres[''+this] = '' + this;
        });

        var countries = {};
        $.each($('select#countries').val(), function() {
            countries[''+this] = '' + this;
        });

        var directors = {};
        $.each($('select#directors').val(), function() {
            directors[''+this] = '' + this;
        });

        var writers = {};
        $.each($('select#writers').val(), function() {
            writers[''+this] = '' + this;
        });

        var actors = {};
        $.each($('select#actors').val(), function() {
            actors[''+this] = '' + this;
        });

        var newMovie = {
            "id": movieID,
            "title": $('#form-edit-movie #title').val(),
            "description": $('#description').val(),
            "duration": $('#duration').val(),
            "releaseDate": $('#releaseDate').val(),
            "rating": $('#rating').val(),
            "imageURL": $('#imageURL').val(),
            "imdbId": $('#imdbId').val(),
            "ageCategory": $('select#ageCategory').val(),
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
                if (result.success) {
                    $.jGrowl('Movie has been successfully edited', {theme: 'smoke'});
                    location.reload();
                } else {
                    $.jGrowl('An error occurred while editing movie...');
                }

                $('#editMovieModal').modal('hide');
            },
            error: function() {
                $.jGrowl('An error occurred while editing movie...');
                $('#editMovieModal').modal('hide');
            }
        });
        //debugger;
        //$.ajax({
        //    url: '/admin/edit/movie/',
        //    dataType: 'json',
        //    data: $('form#form-edit-movie').serialize(),
        //    type: 'PUT',
        //    success: function(result) {
        //        if (result.success) {
        //            $.jGrowl('Movie has been successfully edited', {
        //                theme: 'smoke'
        //            });
        //        } else {
        //            $.jGrowl('An error occurred while editing movie...');
        //        }
        //
        //        $('#editMovieModal').modal('hide');
        //    },
        //    error: function() {
        //        $.jGrowl('An error occurred while editing movie...');
        //        $('#editMovieModal').modal('hide');
        //    }
        //});
    });

    $('#delete-movie-btn').click(function() {
        var delID = $(this).data('movie-id');
        $.ajax({
            url: '/admin/delete/movie/'+delID+'.json',
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

    var myTemplate = $.templates("#personTmpl");
    var people = [
        {
            name: "Adriana"
        },
        {
            name: "Robert"
        }
    ];

    var html = myTemplate.render(people);
    $("#res").html(html);

    //$('#admin-movies').click(function(){
    //    $.get('/admin/movies.json', function(data) {
    //
    //    });
    //});

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

/*
 <style>
 html {
 background: url(/img/main-background.jpg) no-repeat center center fixed;
 -webkit-background-size: cover;
 -moz-background-size: cover;
 -o-background-size: cover;
 background-size: cover;
 }

 body {
 margin-top: 40px;
 font-size: 16px;
 background: transparent;
 }

 .panel {
 background-color: rgba(255, 255, 255, 0.9);
 }

 .well {
 background: rgba(240, 237, 239, 0.85);
 }
 </style>
 */