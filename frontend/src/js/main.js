$(document).ready(function () {
    var $toplevelCategories = $('#toplevel-categories');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/toolup/category/toplevel',
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

            $toplevelCategories.empty();

            for (var item of data) {
                console.log(item);
                var content = '<li>' + createCategoryLink(item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);
                $toplevelCategories.append(list);
            }
        },
        error: function() {
            alert("Laden der Toplevel-Kategorien fehlgeschlagen.");
        }

    });
    $toplevelCategories.text('Loading the JSON file.');
});
