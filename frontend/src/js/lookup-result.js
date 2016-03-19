$(document).ready(function () {
    var wantedFeatures = $.urlParam('features');
    var $applications = $('#applications');

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/toolup/lookup',
        data: { features: wantedFeatures},
        crossDomain: true,
        contentType: 'application/x-www-form-urlencoded',
        xhrFields: {
            withCredentials: false
        },
        headers: {

        },
        success: function(data) {
            console.log(data);

            $applications.empty();

            for (var item of data) {
                console.log(item);
                var content = '<li>' + createLinkBoxForBO('application', item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);
                $applications.append(list);
            }
        },
        error: function() {
            alert("Laden der Suchergebnisse fehlgeschlagen.");
        }

    });

    $applications.text('Lade Suchergebnisse..');

});
