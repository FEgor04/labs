import {selectedX} from "./main.ts";
import {validateFormInput} from "./validator.ts";
import {renderCanvas} from "./render/renderCanvas.ts";
import {HitAPI} from "./lib/api";
import {HistoryManager} from "./history/interface/historyManager.ts";
import {Point} from "./model.ts";
import {renderHistory} from "./render/renderHistory.ts";

export function handleFormSubmit(api: HitAPI, historyManager: HistoryManager) {
    const form = document.querySelector<HTMLFormElement>("form#form")!
    let rawValues = {
        x: selectedX as any,
        y: form["y"].value,
        r: form["r"].value,
    }
    let values = validateFormInput(rawValues)
    if(!values.ok) {
        showError(values.error.toString())
        return
    }
    showError(undefined)
    const r = values.value.r
    const point: Point = {x: values.value.x, y: values.value.y}
    renderCanvas(r, point)

    api.hit(point, values.value.r)
        .then((res) => {
            if(res.ok) {
                const response = res.value
                historyManager.push({
                    r,
                    point,
                    ...response
                })
            }
            else {
                showError(res.error.toString())
            }
        })
        .finally(() => {
            renderHistory(historyManager.get())
        })
}

function showError(error?: string) {
    let err = document.querySelector<HTMLParagraphElement>("div#error")!
    err.innerText = error ?? ""
}