$(document).ready(function() {
    $('#like_topic').on('click',function(event){
       event.preventDefault();
        $.ajax({
            url: '/up?id=1',
            success: function(data) {
                console.log(data);
            },
            type: 'GET'
        });
    });
});