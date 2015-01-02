jQuery(function ($) {
    $('li.disabled>a').attr('href', '#');
    $('.rate').each(function(){
        $(this).shieldRating({
            max: 10,
            step: 0.1,
            value: $(this).data('imdbrating'),
            markPreset: false,
            enabled: false
        });
    });

    $('.chzn').chosen({allow_single_deselect: true});

    $('#up-image').click(function(){
        var formData = new FormData($(this).closest('form')[0]);
        $.ajax({
            url: '/form',
            type: 'POST',
            data: formData,
            //async: false,
            cache: false,
            contentType: false,
            processData: false,
            error: function(){
                alert("error in ajax form submission");
            }
        });

    });
});
