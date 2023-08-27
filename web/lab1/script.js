let selectedDeltaX = -3;
updateSelectedXClasses()

function updateSelectedXClasses() {
    for (let deltaX = -3; deltaX <= 5; deltaX++) {
        document.getElementById(`x-input-${deltaX}`).className = "";
        if (deltaX == selectedDeltaX) {
            document.getElementById(`x-input-${deltaX}`).className = "selected";
        }
    }
}

for (let deltaX = -3; deltaX <= 5; deltaX++) {
    document
        .getElementById(`x-input-${deltaX}`)
        .addEventListener("click", (event) => {
            selectedDeltaX = deltaX;
            console.log(selectedDeltaX);
            updateSelectedXClasses();
        });
}

function validateForm(deltaX, deltaY, r) {
    if (isNaN(deltaX)) {
        alert("Ошибка! Delta X должен быть числом");
        return false;
    }
    if (isNaN(deltaY)) {
        alert("Ошибка! Delta Y должен быть числом");
        return false;
    }
    if (isNaN(r)) {
        alert("Ошибка! R должен быть числом");
        return false;
    }
    return true;
}

function submitForm(e) {
    e.preventDefault();
    const deltaX = selectedDeltaX;
    const deltaY = parseInt(document.getElementById("y-input").value);
    const r = parseInt(document.getElementById("r-input").value);
    console.log(deltaX, deltaY, r);
    if (validateForm(selectedDeltaX, "", "")) {
        sendDataToServer(
            parseInt(deltaX),
            parseInt(deltaY),
            parseInt(r)
        )
            .then((response) => {
                drawCanvas({ x: deltaX, y: deltaY }, r);
                showResponse(response["success"], undefined)
                addResponseToHistory(response)
            })
            .catch((error) => {
                showResponse(undefined, error.message)
            });
    }
}

function addResponseToHistory(response) {
    /** @type {object[] | undefined} */
        let history = JSON.parse(localStorage.getItem("history"))
    if(!history) {
        history = []
    }
    history.push(response)
    localStorage.setItem("history", JSON.stringify(history))
    renderHistory()
}

function renderHistory() {
    /** @type {object[] | undefined} */
        let history = JSON.parse(localStorage.getItem("history"))
    if(!history) {
        history = []
    }
    const historyElement = document.getElementById("history")
    historyElement.innerHTML = `
        <tr>
            <th> X </th>
            <th> Y </th>
            <th> R </th>
            <th> Попал?? </th>
            <th> Время выполнения </th>
            <th> Время на сервере </th>
        </tr>`
    history.forEach(response => {
        // jsx
        historyElement.innerHTML += `
            <tr>
                <td>${response["x"]}</td>
                <td>${response["y"]}</td>
                <td>${response["r"]}</td>
                <td>${response["success"] ? "ДА!!!!" : "Нет(("}</td>
                <td>${response["executionTime"]} ns</td>
                <td>${new Date(response["currentTime"] * 1000).toLocaleString()}</td>
            </tr>
            `
    })
}

async function sendDataToServer(x, y, r) {
    const response = await fetch(
        `http://localhost:8080/action.php?x=${x}&y=${y}&r=${r}`,
        {
            method: "GET",
        }
    );
    if (!response.ok) {
        throw new Error(`Ошибка. Код ответа: ${response.status}`);
    }
    const json = await response.json();
    return json;
}

/**
    * Отображает результат запроса клиенту
    * @param {boolean} success Успешное попадание или нет
    * @param {undefined | string} error Ошибка, возникшая во время работы
    */
    function showResponse(success, error) {
        const feedbackContainer = document.getElementById("form-feedback")
        if(error == undefined) {
            feedbackContainer.innerHTML = success ? "Успешное попадание!" : "Промахнулся..."
            feedbackContainer.className = success ? "success" : "miss"
        }
        else {
            feedbackContainer.className = "error"
            feedbackContainer.innerHTML = error
        }
    }

/**
    * Рисует канвас и все точки
    * @param {{x: number, y: number}} point точка
    * @param {number} r радиус
    */
    function drawCanvas(point, r) {
        const canvas = document.getElementById("diagram");
        const { width, height } = canvas.getBoundingClientRect();
        canvas.setAttribute("width", width);
        canvas.setAttribute("height", height);
        console.log(width, height);

        const midX = width / 2;
        const midY = height / 2;
        console.log(midX, midY);

        const ctx = canvas.getContext("2d");
        ctx.fillStyle = "blue";

        const ticksNumber = 12;
        const rTickX = (r / ticksNumber) * width;
        const rTickY = (r / ticksNumber) * height;
        ctx.beginPath();
        ctx.moveTo(midX + rTickX / 2, midY);
        ctx.lineTo(midX, midY + rTickY);
        ctx.lineTo(midX - rTickX / 2, midY + rTickY);
        ctx.lineTo(midX - rTickX / 2, midY);
        ctx.fill();

        ctx.beginPath();
        ctx.arc(midX, midY, rTickX, -Math.PI / 2, Math.PI, true);
        ctx.fill();
        ctx.beginPath();
        ctx.moveTo(midX - rTickX, midY);
        ctx.lineTo(midX, midY);
        ctx.lineTo(midX, midY - rTickY);
        ctx.fill();

        ctx.beginPath();
        ctx.moveTo(0, midY);
        ctx.lineTo(width, midY);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(midX, 0);
        ctx.lineTo(midX, height);
        ctx.stroke();

        ctx.fillStyle = "black";
        ctx.fillText("0", midX + 5, midY - 5);
        ctx.fillText("R/2", midX + rTickX / 2, midY - 5);
        ctx.fillText("R", midX + rTickX, midY - 5);
        ctx.fillText("-R/2", midX + 5, midY + rTickY / 2);
        ctx.fillText("-R", midX + 5, midY + rTickY);
        ctx.fillText("-R/2", midX - rTickX / 2, midY - 5);
        ctx.fillText("-R", midX - rTickX, midY - 5);
        ctx.fillText("R/2", midX + 5, midY - rTickY / 2);
        ctx.fillText("R", midX + 5, midY - rTickY);

        if (point) {
            const pointR = 5;
            ctx.fillStyle = "red";
            ctx.beginPath()
            ctx.arc(
                midX + (point.x / ticksNumber) * width,
                midY - (point.y / ticksNumber) * height,
                pointR,
                0,
                2 * Math.PI,
                true,
            );
            ctx.fill()
        }
    }

document
    .getElementById("form")
    .addEventListener("submit", (e) => submitForm(e));

drawCanvas(undefined, 5);
renderHistory()
