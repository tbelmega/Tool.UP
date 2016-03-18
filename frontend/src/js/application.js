$(document).ready(function () {
    var applicationId = $.urlParam('id');

    var $title = $('#title');
    var $description = $('#description');
    var $features = $('#features');
    var $categories = $('#categories');

    $title.append("Wird abgerufen...");
    $description.append("Wird abgerufen...");
    $features.append("Wird abgerufen...");
    $categories.append("Wird abgerufen...");

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/toolup/application/' + applicationId,
        dataType: 'json',
        crossDomain: true,
        contentType: 'text/plain',
        xhrFields: {
            withCredentials: false
        },
        headers: {

        },
        success: function(data) {
            $title.text(data['title']);
            $description.text(data['description']);
            $features.empty();
            $categories.empty();

            for (var item of data['features']) {
                console.log(item);
                item = JSON.parse(item);
                var content = '<li>' + createLinkBoxForBO('feature', item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);

                $features.append(list);
            }

            for (var item of data['categories']) {
                console.log(item);
                item = JSON.parse(item);
                var content = '<li>' + createLinkBoxForBO('category', item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);

                $categories.append(list);
            }

        },
        error: function() {
            alert("Laden der Kategorie fehlgeschlagen.");
        }

    });

});