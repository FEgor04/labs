import {showError} from "./errors.js";

export function validateX(value) {
    if (value === undefined || value === null || value === "") {
        showError("Введите X!")
        return false
    }
    const parsedX = parseFloat(value)
    if (!isNumeric(parsedX)) {
        showError("X не является числом!")
        return false
    }
    if (-3 <= parsedX && parsedX <= 5) {
        return true
    }
    showError("X должен быть в промежутке [-3 .. 5]")
    return false
}

function isNumeric(value) {
    return !isNaN(parseFloat(value))
}