import {updateSelectedYAttributes} from './input/selectY.js'
import {validateR} from "./validation/validateR.js";
import {validateX} from "./validation/validateX.js";
import {xInput} from "./input/selectX.js";
import {cleanError} from "./validation/errors.js";

let rInput = document.getElementById("rInput")

export let selectedX = undefined;
export let selectedY = 0;
updateSelectedYAttributes()
export let selectedR = undefined;

export const setX = (value) => {
    selectedX = parseFloat(value)
    xInput.value = value;
}
export const setY = (value) => {
    selectedY = parseFloat(value)
    updateSelectedYAttributes();
    const hiddenYInput = document.querySelector("input#yInputHidden")
    hiddenYInput.value = selectedY
}

export const setR = (value) => {
    if(!value) {
        rInput.value = "Выберите R"
        selectedR = value;
        renderCanvas(undefined, [])
        canvasWrapper.setAttribute("data-active", "false")
        return
    }
    if (validateR(value)) {
        selectedR = parseFloat(value);
        renderCanvas(value, [])
        canvasWrapper.setAttribute("data-active", "true")
        rInput.value = selectedR
    } else {
        selectedR = value;
        renderCanvas(undefined, [])
        canvasWrapper.setAttribute("data-active", "false")
        rInput.value = "Выберите R"
    }
}
setR(undefined)
