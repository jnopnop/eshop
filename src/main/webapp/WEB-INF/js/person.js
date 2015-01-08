jQuery(function ($) {

    $('.images-carousel:not(.admin-images)').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        centerMode: true,
        variableWidth: true
    });

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
});
