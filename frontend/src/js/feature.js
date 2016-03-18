$(document).ready(function () {
    var featureId = $.urlParam('id');

    var $title = $('#title');
    var $description = $('#description');
    var $applications = $('#applications');

    $title.append("Wird abgerufen...");
    $description.append("Wird abgerufen...");
    $applications.append("Wird abgerufen...");

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/toolup/feature/' + featureId,
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
            $applications.empty();

            for (var item of data['applications']) {
                console.log(item);
                item = JSON.parse(item);
                var content = '<li>' + createLinkBoxForBO('application', item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);
                $applications.append(list);
            }
        },
        error: function() {
            alert("Laden des Features fehlgeschlagen.");
        }

    });

});