$(document).on({
    ajaxStart: function() { console.log('loading')},
    ajaxStop: function() { console.log('finished')},
    ajaxError: function( event, request, settings ) {
        $.jGrowl(request.responseJSON.data.exception, {theme: 'jerror'});
    }
});

jQuery(function ($) {
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
    });
});