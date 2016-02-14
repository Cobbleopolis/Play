function main() {
    $("#submitPrompt").submit(function() {
        submitPrompt();
        return false;
    })
}

function submitPrompt() {
    var data = {
        "user": user.email,
        "content": $("#promptContent").val()
    };
    //console.log(JSON.stringify(data));
    $.ajax({
        url: "/api/submitPrompt",
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: 'json',
        success: function(response) {
            alert(response.message)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        },
        processData: true
    });
}
