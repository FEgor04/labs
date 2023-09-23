import {cleanError} from "./errors.js";
import {validateX} from "./validateX.js";
import {validateR} from "./validateR.js";
import {selectedR, selectedX} from "../state.js";
import {xInput} from "../input/selectX.js";

document.querySelector("form").addEventListener("submit", (e) => {
    cleanError()
    if (!validateX(selectedX)) {
        e.preventDefault()
        return false;
    }
    if (!validateR(selectedR)) {
        e.preventDefault()
        return false;
    }
    xInput.value = selectedX.toFixed(2)
    return true;
})
