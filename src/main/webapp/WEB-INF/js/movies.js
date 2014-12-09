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
});
