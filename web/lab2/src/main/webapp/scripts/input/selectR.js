import {setR} from "../state.js";

export let rInput = document.getElementById("rInput")

rInput.addEventListener("change", (e) => {
    setR(e.target.value)
})