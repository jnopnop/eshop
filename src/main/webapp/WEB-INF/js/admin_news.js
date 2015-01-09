jQuery(function ($) {

    $('.chzn').chosen({allow_single_deselect: true});

    $('#mbirthdate').datetimepicker({pickTime: false});

    $('#editNewsModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('news-id');

        $('#edit-news-btn').data('news-id', ID);
        $('form#form-edit-news')[0].reset();
        $.get('/admin/news/'+ID, function (data) {
            var news = data.data;
            $('#mtitle').val(news.title);
            $('#mimageURL').val(news.imageURL);
            $('#mcontents').val(news.contents);
            $('#edit-news-btn').data('news-id', news.id);
            CKEDITOR.replace('mcontents');
        });
    });

    $('#editUserModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('user-id');

        $('#edit-user-btn').data('user-id', ID);
        $('form#form-edit-user')[0].reset();
        $.get('/users/me', function (data) {
            var user = data.data;
            debugger;
            $('#ufullname').val(user.fullname);
            $('#uemail').val(user.email);
            $('#upassword').val(user.password);
            $('#uimage')
        });
    });

    $('#addNewsModal').on('show.bs.modal', function (event) {
        $('form#form-add-news')[0].reset();
        CKEDITOR.replace('acontents');
    });

    $('#deletePersonModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('person-id');
        $('#delete-person-btn').data('person-id', ID);
    });

    $('#ckeditorModal').on('show.bs.modal', function(event){
        CKEDITOR.replace('editor1');
    });

    $('.delete-image').click(function() {
        var _this = $(this);
        $.ajax({
            url: _this.data('image-path'),
            type: 'DELETE',
            success: function(data) {
                if (data.success) {
                    location.reload();
                }
            }
        });
    });

    $('#edit-news-btn').click(function(){
        var data = CKEDITOR.instances.mcontents.getData();
        var newsID = $(this).data('news-id');

        var news = {
            "id": newsID,
            "title": $('#mtitle').val(),
            "contents": data
        };
        $.ajax({
            type: "PUT",
            url: "/admin/news",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(news),
            success: function(result) {
                if (result.success) {
                    $.jGrowl('News have been successfully edited', {theme: 'smoke'});
                    location.reload();
                } else {
                    $.jGrowl('An error occurred while editing news...');
                }

                $('#editNewsModal').modal('hide');
            },
            error: function() {
                $.jGrowl('An error occurred while editing news...');
                $('#editNewsModal').modal('hide');
            }
        });
    });

    $('#edit-person-btn').click(function() {
        var personID = $(this).data('person-id');

        var newPerson = {
            "id": personID,
            "fullname": $('#mfullname').val(),
            "description": $('#mdescription').val(),
            "birthdate": moment($('#mbirthdate').val()),
            "photoURL": $('#mphotoURL').val(),
            "imdbId": $('#mimdbId').val()
        };
        $.ajax({
            type: "PUT",
            url: "/admin/persons",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newPerson),
            success: function(result) {
                if (result.success) {
                    $.jGrowl('Person has been successfully edited', {theme: 'smoke'});
                    location.reload();
                } else {
                    $.jGrowl('An error occurred while editing person...');
                }

                $('#editPersonModal').modal('hide');
            },
            error: function() {
                $.jGrowl('An error occurred while editing person...');
                $('#editPersonModal').modal('hide');
            }
        });
    });

    $('#deleteNewsModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('news-id');
        $('#delete-news-btn').data('news-id', ID);
    });

    $('#delete-news-btn').click(function() {
        var delID = $(this).data('news-id');
        $.ajax({
            url: '/admin/news/'+delID,
            type: 'DELETE',
            success: function(result) {
                if (result.success) {
                    location.reload();
                } else {
                    $.jGrowl('An error occurred while deleting news...');
                }

                $('#deleteNewsModal').modal('hide');
            }
        });
    });

    var parseDate = function(str) {
        var dateParts = str.split('/');
        return new Date(dateParts[2], dateParts[1], dateParts[0]).getTime();
    };

    $('#add-news-btn').click(function () {
        var formData = new FormData();
        formData.append('title', $('#atitle').val());
        formData.append('contents', CKEDITOR.instances.acontents.getData());
        formData.append('mainImage', $('#aimage')[0].files[0]);
        $.ajax({
            url: '/admin/news',
            type: 'POST',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function(data) {
                if (data.success) {
                    location.reload();
                }
            }
        });
    });
});