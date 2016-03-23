$(document).ready(function () {
    var wantedString = $.urlParam('searchString');

    var $applications = $('#applications');

    var $heading = $('#main-content h2');

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/toolup/search',
        data: { 'search string': wantedString},
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

            if (data.length <= 0) {
                $heading.empty()
                var text = "Für Ihren Suchtext '" + wantedString + "' wurden leider keine Ergebnisse gefunden.";
                $heading.append(text);
            } else {
                for (var item of data) {
                    console.log(item);
                    var content = '<li>' + createLinkBoxForBO('application', item['id'],item['title']) + '</li>';
                    var list = $('<ul />').html(content);
                    $applications.append(list);
                }

                $heading.empty()
                var text = "Folgende Treffer wurden für Ihren Suchtext '" + wantedString + "' gefunden:";
                $heading.append(text);
            }


        },
        error: function() {
            alert("Laden der Suchergebnisse fehlgeschlagen.");
        }

    });

    $applications.text('Lade Suchergebnisse..');

});
