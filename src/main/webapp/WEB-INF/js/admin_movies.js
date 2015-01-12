var allData = {};

var fillMovieData = function (formSelector, id) {
    $.get('/admin/movies/'+id, function (response) {
        var data = response.data;
        var form = $(formSelector)[0];

        $('[data-for=title]', form).val(data.title);
        $('[data-for=description]').val(data.description);
        $('[data-for=rating]', form).val(data.rating);
        $('[data-for=duration]', form).val(data.duration);
        $('[data-for=releaseDate]', form).data("DateTimePicker").setDate(moment(data.releaseDate).format('MM/DD/YYYY'));
        $('[data-for=imdbId]', form).val(data.imdbId);
        $('[data-for=ageCategory]', form).val(data.ageCategory);
        $('[data-for=ageCategory]', form).trigger("chosen:updated");

        $('[data-for=genres]', form).val(_.keys(data.genres));
        $('[data-for=genres]', form).trigger("chosen:updated");

        $('[data-for=countries]', form).val(_.keys(data.countries));
        $('[data-for=countries]', form).trigger("chosen:updated");

        $('[data-for=directors]', form).val(_.keys(data.directors));
        $('[data-for=directors]', form).trigger("chosen:updated");

        $('[data-for=writers]', form).val(_.keys(data.writers));
        $('[data-for=writers]', form).trigger("chosen:updated");

        $('[data-for=actors]', form).val(_.keys(data.actors));
        $('[data-for=actors]', form).trigger("chosen:updated");
    });
};

var populateMovieForm = function(formSelector, data) {
    var form = $(formSelector)[0];
    form.reset();

    //Genres
    var currData = data['genres'];
    var currInput = $('select[data-for=genres]', form);
    currInput.html('');
    for (var key in currData) {
        currInput.append('<option value="' + key + '">' + currData[key] + '</option>');
    }
    currInput.chosen();

    //Age categories
    currData = data['categories'];
    var currInput = $('select[data-for=ageCategory]', form);
    currInput.html('');
    for (var key in currData) {
        currInput.append('<option value="' + currData[key] + '">' + currData[key] + '</option>');
    }
    currInput.chosen({allow_single_deselect: true});

    //Countries
    var currData = data['countries'];
    var currInput = $('select[data-for=countries]', form);
    currInput.html('');
    for (var key in currData) {
        currInput.append('<option value="' + key + '">' + currData[key] + '</option>');
    }
    currInput.chosen();

    //Persons
    currData = data['persons'];
    var directors = $('select[data-for=directors]', form);
    var writers = $('select[data-for=writers]', form);
    var actors = $('select[data-for=actors]', form);
    directors.html('');
    writers.html('');
    actors.html('');
    for (var key in currData) {
        var option = '<option value="' + key + '">' + currData[key] + '</option>';
        directors.append(option);
        writers.append(option);
        actors.append(option);
    };
    directors.chosen();
    writers.chosen();
    actors.chosen();
};

var refreshAllData = function (formSelector, ID) {
    $.get('/admin/movies/info', function (data) {
        allData['genres'] = data['genres'];
        allData['categories'] = data['categories'];
        allData['countries'] = data['countries'];
        allData['persons'] = data['persons'];
        populateMovieForm(formSelector, allData);
        if (ID) {
            fillMovieData(formSelector, ID);
        }
    });
};


jQuery(function ($) {
    //
    //$('.chzn').chosen({allow_single_deselect: true});
    $('#search-form').removeClass('in');

    $('[data-for=releaseDate]').datetimepicker({pickTime: false});

    $('#editMovieModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('movie-id');
        $('#edit-movie-btn').data('movie-id', ID);

        refreshAllData('#form-edit-movie', ID);
    });

    $('#addMovieModal').on('show.bs.modal', function (event) {
        refreshAllData('#form-add-movie');
    });

    $('#deleteMovieModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('movie-id');

        $('#delete-movie-btn').data('movie-id', ID);
    });

    $('#add-movie-btn').click(function() {

        var genres = {};
        $.each($('select#agenres').chosen().val(), function() {
            genres[''+this] = '' + this;
        });

        var countries = {};
        $.each($('select#acountries').val(), function() {
            countries[''+this] = '' + this;
        });

        var directors = {};
        $.each($('select#adirectors').val(), function() {
            directors[''+this] = '' + this;
        });

        var writers = {};
        $.each($('select#awriters').val(), function() {
            writers[''+this] = '' + this;
        });

        var actors = {};
        $.each($('select#aactors').val(), function() {
            actors[''+this] = '' + this;
        });

        var newMovie = {
            "title": $('#atitle').val(),
            "description": $('#adescription').val(),
            "duration": $('#aduration').val(),
            "releaseDate": moment($('#areleaseDate').val()),
            "rating": $('#arating').val(),
            "imdbId": $('#aimdbId').val(),
            "ageCategory": $('select#aageCategory').val(),
            "genres": genres,
            "countries": countries,
            "directors": directors,
            "writers": writers,
            "actors": actors
        };
        $.ajax({
            type: "POST",
            url: "/admin/movies",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newMovie),
            success: function(result) {
                if (result.success && result.data.redirect) {
                    //location.reload();
                    window.location.replace(result.data.redirect);
                }
                $('#addMovieModal').modal('hide');
            }
        });
    });

    $('#edit-movie-btn').click(function() {
        var movieID = $(this).data('movie-id');
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
            "releaseDate": moment($('#mreleaseDate').val()),
            "rating": $('#mrating').val(),
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
            url: "/admin/movies",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newMovie),
            success: function(result) {
                if (result.success) {
                    location.reload();
                }
                $('#editMovieModal').modal('hide');
            }
        });
    });

    $('#delete-movie-btn').click(function() {
        var delID = $(this).data('movie-id');
        $.ajax({
            url: '/admin/movies/'+delID,
            type: 'DELETE',
            success: function(result) {
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
});