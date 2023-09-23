import { selectedY, setY } from '../state.js'

const yButtons = document.querySelectorAll("#yInput > button")

export function updateSelectedYAttributes() {
    const selectedValue = parseFloat(selectedY)
    yButtons.forEach(btn => {
        const btnValue = parseFloat(btn.innerHTML)
        if (selectedValue === btnValue) {
            btn.setAttribute("data-active", "true")
        } else {
            btn.setAttribute("data-active", "false")
        }
    })
}


yButtons.forEach(btn => {
    btn.addEventListener("click", event => {
        setY(event.target.innerHTML)
    })
})

