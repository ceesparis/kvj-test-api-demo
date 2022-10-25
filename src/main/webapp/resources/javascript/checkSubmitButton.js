function checkSubmitButton() {
    if (document.getElementById("search-input").value === "") {
        document.getElementById('search-button').disabled = true;
    } else {
        document.getElementById('search-button').disabled = false;
    }
}