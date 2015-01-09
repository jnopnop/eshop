jQuery(function ($) {

    $('input.toggle-admin').bootstrapSwitch();

    $('input.toggle-admin').on('switchChange.bootstrapSwitch', function(event, state) {
        var toggleValue = $(this).prop("checked");
        var _this = this;
        $.ajax({
            url: '/users/' + $(_this).data('id') + '?admin=' + toggleValue,
            type: 'PUT',
            success: function (response) {
                if (response.success) {
                    $('.list-group-item[data-user-id='+ $(_this).data('id') +']').toggleClass('user-admin');
                } else {
                    $(_this).bootstrapSwitch('toggleState', true);
                }
            },
            error: function() {
                $(_this).bootstrapSwitch('toggleState', true);
            }
        });
    });

    $('#deleteUserModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var ID = button.data('user-id');

        $('#delete-user-btn').data('user-id', ID);
    });

    $('#delete-user-btn').click(function() {
        var delID = $(this).data('user-id');
        $.ajax({
            url: '/users/'+delID,
            type: 'DELETE',
            success: function(result) {
                if (result.success) {
                    location.reload();
                }

                $('#deleteUserModal').modal('hide');
            }
        });
    });
});