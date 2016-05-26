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
            $title.text(data['title']);
            $description.text(data['description']!= null ? data['description'] : "");
            $subcategories.empty();
            $tools.empty();

            for (var item of data['subCategories']) {
                console.log(item);
                item = JSON.parse(item);
                var content = '<li>' + createLinkBoxForBO('category', item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);

                $subcategories.append(list);
            }

            for (var item of data['applications']) {
                console.log(item);
                item = JSON.parse(item);
                var content = '<li>' + createLinkBoxForBO('application', item['id'],item['title']) + '</li>';
                var list = $('<ul />').html(content);

                $tools.append(list);
            }

        },
        error: function() {
            alert("Laden der Kategorie fehlgeschlagen.");
        }

    });

});
