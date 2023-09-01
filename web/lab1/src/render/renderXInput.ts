import {selectedX, selectX} from "../main.ts";

export function renderXInput() {
    const wrapper = document.querySelector<HTMLDivElement>("#buttons-wrapper")!
    wrapper.innerHTML = ''
    for(let x = -5; x <= 3; x++) {

        const buttonId = `x-input-${x}`
        wrapper.innerHTML += `
        <button type="button" class="${selectedX == x ? "selected" : ""} btn x-btn" id="${buttonId}">
            ${x}
        </button>
        `


    }
    /**
     * По непонятным причинам добавление eventListener'а непосредственно после
     * добавления кнопки не работает.
     * Из-за этого приходится добавлять eventListener для всех кнопок после обновления.
    */
    let addedButton = document.querySelectorAll<HTMLButtonElement>(".x-btn")
    addedButton.forEach((btn) => {
        let x = parseInt(btn.innerText)
        btn.addEventListener("click", () => {
            selectX(x)
        }, false)
    })
}

export {selectX}