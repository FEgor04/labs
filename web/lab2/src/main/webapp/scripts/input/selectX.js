import {setX} from "../state.js";

export let xInput = document.getElementById("xInput")

xInput.addEventListener("change", (e) => {
    setX(e.target.value)
})