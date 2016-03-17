$(document).ready(function () {
    var categoryId = $.urlParam('id');

    var $title = $('#title');
    var $description = $('#description');
    var $subcategories = $('#subcategories');
    var $tools = $('#tools');

    $title.append("Wird abgerufen...");
    $description.append("Wird abgerufen...");
    $subcategories.append("Wird abgerufen...");
    $tools.append("Wird abgerufen...");

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/toolup/category/' + categoryId,
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

            $title.text(data['title']);
            $description.text(data['description']);
            $subcategories.empty();
            $tools.empty();

            for (var item of data['subCategories']) {
                console.log(item);
                var content = '<li>' + item + '</li>';
                var list = $('<ul />').html(content);

                $subcategories.append(list);
            }

            for (var item of data['applications']) {
                console.log(item);
                var content = '<li>' + item + '</li>';
                var list = $('<ul />').html(content);

                $tools.append(list);
            }

        },
        error: function() {
            alert("Laden der Kategorie fehlgeschlagen.");
        }

    });

});
