$(document).ready(function () {
    var $features = $('#features');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/toolup/feature',
        dataType: 'json',
        crossDomain: true,
        contentType: 'text/plain',
        xhrFields: {
            withCredentials: false
        },
        headers: {

        },
        success: function(data) {
            console.log(data);

            $features.empty();

            for (var item of data) {
                console.log(item);
                var content = '<li><input type="checkbox">' + " " + item['title'] + '</input></li>';
                $features.append(content);
            }
        },
        error: function() {
            alert("Laden der Features fehlgeschlagen.");
        }

    });

    $features.text('Lade Features..');
});
