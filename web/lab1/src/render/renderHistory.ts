import {HistoryEntry} from "../history/model.ts";

export function renderHistory(history: HistoryEntry[]) {
    const historyTable = document.querySelector<HTMLTableElement>("table#history")!
    historyTable.innerHTML = ''
    historyTable.innerHTML += `
        <tr>
            <th>
            X
            </th>
            <th>
            Y
            </th>
            <th>
            R
            </th>
            <th>
            Попал?
            </th>
            <th>
            Время на сервере
            </th>
            <th>
            Время выполнения
            </th>
        </tr>    
    `
    history.forEach((entry) => {
        historyTable.innerHTML += renderHistoryRow(entry)
    })

}

function renderHistoryRow(entry: HistoryEntry): string {
    return `
        <tr class="${entry.hit ? "success" : "fail"}">
            <td>
                ${entry.point.x}
            </td>
            <td>
                ${entry.point.y}
            </td>
            <td>
                ${entry.r}
            </td>
            <td>
                ${entry.hit ? "ДА!!!!" : "нет("}
            </td>
            <td>
                ${new Date(entry.currentTime).toLocaleString()}
            </td>
            <td>
                ${entry.executionTime} ns
            </td>
        </tr>
    `
}
