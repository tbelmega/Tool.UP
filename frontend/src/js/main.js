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

$(document).ready(function () {
    var showData = $('#show-data');


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

            showData.empty();

            for (var item of data) {
                console.log(item);
                var content = '<li>' + item['title'] + '</li>';
                var list = $('<ul />').html(content);
                showData.append(list);
            }
        },
        error: function() {
            alert("Error");
        }

    });
    showData.text('Loading the JSON file.');

});
