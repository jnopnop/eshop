/**
 * Created by nop on 15/10/14.
 */
jQuery(function ($) {

    var parseDate = function(str) {
        var dateParts = str.split('/');
        return new Date(dateParts[2], dateParts[1], dateParts[0]).getTime();
    };

    $.get('/admin/genres', function( data ) {
        for (var key in data) {
            $('select#genres')
                .append('<option value="' + key + '">' + data[key] + '</option>');
        }
        $('select#genres').chosen();
        $('select#genres').trigger("liszt:updated");
    });
    $.get('/admin/categories', function( data ) {
        for (var key in data) {
            $('select#ageCategory')
                .append('<option value="' + key + '">' + data[key] + '</option>');
        }
        $('select#ageCategory').chosen();
        $('select#ageCategory').trigger("liszt:updated");
    });
    $.get('/admin/countries', function( data ) {
        for (var key in data) {
            $('select#countries')
                .append('<option value="' + key + '">' + data[key] + '</option>');
        }
        $('select#countries').chosen();
        $('select#countries').trigger("liszt:updated");
    });
    $.get('/admin/persons', function( data ) {
        for (var key in data) {
            var option = '<option value="' + key + '">' + data[key] + '</option>';
            $('select#directors').append(option);
            $('select#writers').append(option);
            $('select#actors').append(option);
        };
        $('select#directors').chosen();
        $('select#writers').chosen();
        $('select#actors').chosen();
    });

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

        //$.post('/movie/add', newMovie);
    });
});