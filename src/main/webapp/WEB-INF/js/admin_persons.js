jQuery(function ($) {

    $('.chzn').chosen({allow_single_deselect: true});

    $('#mbirthdate,#abirthdate').datetimepicker({pickTime: false});

    $('#editPersonModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('person-id');

        $('#edit-person-btn').data('person-id', ID);
        $('form#form-edit-person')[0].reset();
        $.get('/persons/'+ID, function (data) {
            var person = data.data;
            $('#mfullname').val(person.fullname);
            $('#mbirthdate').data("DateTimePicker").setDate(moment(person.birthdate).format('MM/DD/YYYY'));
            $('#mphotoURL').val(person.photoURL);
            $('#mimdbId').val(person.imdbId);
        });
    });

    $('#deletePersonModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('person-id');
        $('#delete-person-btn').data('person-id', ID);
    });

    $('#addPersonModal').on('show.bs.modal', function (event) {
        $('#abirthdate').data("DateTimePicker").setDate(moment().format('MM/DD/YYYY'));
        $('form#form-add-person')[0].reset();
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
            url: "/persons",
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

    $('#delete-person-btn').click(function() {
        var delID = $(this).data('person-id');
        $.ajax({
            url: '/persons/'+delID,
            type: 'DELETE',
            success: function(result) {
                if (result.success) {
                    $.jGrowl('Person has been successfully deleted', {theme: 'smoke'});
                    $('#item-person-'+delID).remove();
                } else {
                    $.jGrowl('An error occurred while deleting person...');
                }

                $('#deletePersonModal').modal('hide');
            },
            error: function() {
                $.jGrowl('An error occurred while deleting person...');
                $('#deletePersonModal').modal('hide');
            }
        });
    });

    var parseDate = function(str) {
        var dateParts = str.split('/');
        return new Date(dateParts[2], dateParts[1], dateParts[0]).getTime();
    };

    $('#add-person-btn').click(function () {
        var newPerson = {
            "fullname": $('#afullname').val(),
            "birthdate": moment($('#abirthdate').val()),
            "imdbId": $('#aimdbId').val()
        };
        $.ajax({
            type: "POST",
            url: "/persons",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(newPerson),
            success: function(result) {
                if (result.success) {
                    location.reload();
                } else {
                    $.jGrowl('An error occurred while deleting person...');
                    $('#addPersonModal').modal('hide');
                }
            }
        });
    });
});