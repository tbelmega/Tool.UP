$(document).ready(function () {
    $("#btn-feature-search").click(function(){
        var featureString = "";

        $('input[name="feature-checkboxes"]:checked').each(function() {
            if (featureString == "") {
                featureString = this.id;
            } else {
                featureString += "," + this.id;
            }
        });
        window.location.href = "lookup-result.html?features=" + featureString;
    });


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
                var content = '<li><input type="checkbox" name="feature-checkboxes" id="' + item['id'] + '">' + " " + item['title'] + '</input></li>';
                $features.append(content);
            }
        },
        error: function() {
            alert("Laden der Features fehlgeschlagen.");
        }

    });

    $features.text('Lade Features..');
});
