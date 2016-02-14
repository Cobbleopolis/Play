function main() {
    $("#submitPrompt").submit(function() {
        submitPrompt();
        return false;
    })
}

function submitPrompt() {
    console.log($("#promptContent").val())
}
