$(document).ready(function(){

    var $menuCategories = $('#category-dropdown-content');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/toolup/category/withApplication',
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

            $menuCategories.empty();

            for (var item of data) {
                console.log(item);
                var content = '<a href="#">' + item['title'] + '</a>';
                $menuCategories.append(content);
            }
        },
        error: function() {
            alert("Laden der Kategorien mit Applications fehlgeschlagen.");
        }

    });
    $menuCategories.text('Loading the JSON file.');

});

//open category dropdown menu on click
function showCategoryDropDown() {
    document.getElementById("category-dropdown-content").classList.toggle("show");
}

//close dropdown menu if user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.category-dropdown')) {

        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}
