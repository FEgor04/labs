import { selectedX } from "./main.ts";
import { validateFormInput } from "./validator.ts";
import { renderCanvas } from "./render/renderCanvas.ts";
import { HitAPI } from "./lib/api";
import { HistoryManager } from "./history/interface/historyManager.ts";
import { Point } from "./model.ts";
import { renderHistory } from "./render/renderHistory.ts";
import { minDelay } from "./lib/minDelay.ts";

export function handleFormSubmit(api: HitAPI, historyManager: HistoryManager) {
  const form = document.querySelector<HTMLFormElement>("form#form")!;
  const rawValues = {
    x: selectedX,
    y: form["y"].value,
    r: form["r"].value,
  };
  const values = validateFormInput(rawValues);
  if (!values.ok) {
    showError(values.error.toString());
    return;
  }
  showError(undefined);
  const r = values.value.r;
  const point: Point = { x: values.value.x, y: values.value.y };

  setLoading(true);
  minDelay(api.hit(point, values.value.r), 200)
    .then((res) => {
      if (res.ok) {
        const response = res.value;
        historyManager.push({
          r,
          point,
          ...response,
        });
        renderCanvas(r, point);
      } else {
        showError(res.error.toString());
      }
    })
    .finally(() => {
      renderHistory(historyManager.get());
      setLoading(false);
    });
}

function showError(error?: string) {
  const err = document.querySelector<HTMLParagraphElement>("div#error")!;
  err.innerText = error ?? "";
}

function setLoading(isLoading: boolean) {
  document
    .querySelectorAll<HTMLButtonElement>("button.x-btn")
    .forEach((xBtn) => {
      setElementDisabled(xBtn, isLoading);
    });

  const yInput = document.querySelector<HTMLInputElement>("input#y-input")!;
  setElementDisabled(yInput, isLoading);

  const rInput = document.querySelector<HTMLSelectElement>("select#r-input")!;
  setElementDisabled(rInput, isLoading);

  const submitButton = document.querySelector<HTMLButtonElement>("#submit")!;
  setElementDisabled(submitButton, isLoading);
}

function setElementDisabled(
  element: HTMLButtonElement | HTMLInputElement | HTMLSelectElement,
  isLoading: boolean,
) {
  element.classList.remove("disabled");
  if (isLoading) {
    element.classList.add("disabled");
  }
  element.disabled = isLoading;
}
