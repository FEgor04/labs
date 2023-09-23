import {showError} from "./errors.js";

export function validateR(value) {
    if (isNaN(value)) {
        showError("Выберите R!")
        renderCanvas(undefined, [])
        canvasWrapper.setAttribute("data-active", "false")
        return false
    }
    return true
}
