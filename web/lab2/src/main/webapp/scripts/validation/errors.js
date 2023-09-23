
let errorsDescription = document.getElementById("errors")

export function showError(description) {
    errorsDescription.innerText = description
}

export function cleanError() {
    errorsDescription.innerText = ""
}