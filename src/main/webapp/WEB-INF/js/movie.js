jQuery(function ($) {

    $('.images-carousel:not(.admin-images)').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        centerMode: true,
        variableWidth: true
    });


    $('.images-carousel:not(.admin-images)').slickNext();//.fadeIn(1200);
    setTimeout(function(){
        $('.images-carousel:not(.admin-images)').closest('.row').fadeIn(600);
    }, 100);

    $('a.carousel-link').click(function() {
        var src = $(this).find('img').attr("src");
        $("<img/>")
            .attr("src", src)
            .load(function() {
                var modalWidth = this.width + 30;
                var modalHeight = this.height + 30;
                $('#modal-gallery .modal-body').html('').append(this);
                $('#modal-gallery .modal-content').css({"width": modalWidth, "height": modalHeight});
                $('#modal-gallery').modal('show');
            });
    });

    $('.delete-image').click(function() {
        var _this = $(this);
        $.ajax({
            url: _this.data('image-path'),
            type: 'DELETE',
            success: function(data) {
                if (data.success) {
                    _this.hide();
                    _this.closest('div').find('img').hide();
                }
            }
        });
    });

    $('#add-comment').click(function() {
        var comment = {
            "title": $('#ctitle').val(),
            "text": $('#ccomment').val(),
            "movie": {
                "id": $('#movie-id').val()
            }
        };

        $.ajax({
            type: "POST",
            url: "/comments",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(comment),
            success: function(data) {
                if (data.success) {
                    location.reload();
                }
            }
        });
    });

    $('#deleteCommentModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var ID = button.data('comment-id'); // Extract info from data-* attributes

        $('#delete-comment-btn').data('comment-id', ID);
    });

    $('#delete-comment-btn').click(function() {
        var delID = $(this).data('comment-id');
        $.ajax({
            url: '/admin/comments/'+delID+'.json',
            type: 'DELETE',
            success: function(result) {
                if (result.success) {
                    $.jGrowl('Comment has been successfully deleted', {
                        theme: 'smoke',
                        open: function() {
                            console.log($(this).html());
                        }
                    });
                    //TODO: remove comment
                    $('.comment-section#'+delID).remove();
                    //$('#item-movie-'+delID).remove();
                } else {
                    $.jGrowl('An error occurred while deleting comment...');
                }

                $('#deleteCommentModal').modal('hide');
            },
            error: function() {
                $.jGrowl('An error occurred while deleting comment...');
                $('#deleteCommentModal').modal('hide');
            }
        });
    });
});
