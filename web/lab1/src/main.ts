import { renderXInput } from "./render/renderXInput.ts";
import { renderCanvas } from "./render/renderCanvas.ts";

import { renderRInput } from "./render/renderRInput.ts";
import { handleFormSubmit } from "./submit.ts";
import { HistoryManager } from "./history/interface/historyManager.ts";
import { StorageHistoryManager } from "./history/storageHistoryManager.ts";
import { renderHistory } from "./render/renderHistory.ts";
import { HitAPI } from "./lib/api";
import { PhpAPI } from "./lib/api/phpAPI.ts";

renderRInput();

export let selectedX = 0;

export function selectX(x: number) {
  selectedX = x;
  renderXInput();
}

selectX(selectedX);

const historyManager: HistoryManager = new StorageHistoryManager(
  sessionStorage,
);
renderHistory(historyManager.get());

function getEntriesAndRender() {
    const lastEntry = historyManager.getLastEntry()
    const rToRender = lastEntry?.r ?? 5;
    const points = historyManager.get().filter(entry => {
        return entry.r == rToRender
    }).map(entry => {
        return entry.point
    })
    console.log(points)
    console.log(rToRender)
    renderCanvas(rToRender, points);
}
getEntriesAndRender()

const hitApi: HitAPI = new PhpAPI();

document
  .querySelector<HTMLFormElement>("form#form")
  ?.addEventListener("submit", (e) => {
    e.preventDefault();
    handleFormSubmit(hitApi, historyManager);
  });

document
  .querySelector<HTMLFormElement>("form#form")
  ?.addEventListener("reset", (_) => {
    selectX(0);
  });

document
  .querySelector<HTMLButtonElement>("button#clean-button")
  ?.addEventListener("click", (_) => {
    historyManager.cleanHistory();
    renderHistory(historyManager.get());
    getEntriesAndRender()
  });
