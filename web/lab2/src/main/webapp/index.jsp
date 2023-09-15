<%--
  Created by IntelliJ IDEA.
  User: fegor04
  Date: 16.09.2023
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String yInputButtonClasses = "w-8 h-8 rounded-full bg-white text-slate-950 border border-slate-200 text-center transition-all duration-150 data-[active=false]:hover:text-lime-600 data-[active=false]:hover:border-lime-600 data-[active=true]:bg-lime-600 data-[active=true]:text-slate-50 data-[active=true]:border-lime-700";
%>

<html>
<head>
    <title>ЛР #2</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-100 text-slate-950">
<jsp:include page="components/header.jsp"/>
<main class="max-w-screen-xl mx-auto mt-16">
    <div class="flex flex-col space-y-8 md:flex-row md:justify-between md:space-y-0">
        <div id="canvasWrapper" class="data-[active=false]:cursor-not-allowed data-[active=true]:cursor-pointer" data-active="false">
            <jsp:include page="components/canvas.jsp"/>
        </div>
        <div id="formWrapper" class="p-4 bg-slate-50 rounded-md shadow-md border border-slate-200 w-[500px]">
            <form class="flex flex-col justify-between h-full" action="" method="POST">
                <div class="flex flex-col space-y-6">
                    <div class="flex flex-col space-y-4">
                        <label class="font-medium text-slate-900" for="xInput">Введите X</label>
                        <input class="bg-white rounded-md border border-slate-200 p-2 transition-all duration-150 focus:ring-0 focus:ring-offset-0 focus:border-lime-600"
                               type="text"
                               name="x"
                               id="xInput" required>
                    </div>
                    <div class="flex flex-col space-y-4">
                        <label class="font-medium text-slate-900" for="yInput">Введите Y</label>
                        <input type="hidden" name="y" id="yInputHidden" value="0"/>
                        <div class="flex flex-row justify-between" id="yInput">
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                -3
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                -2
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                -1
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="true">
                                0
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                1
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                2
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                3
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                4
                            </button>
                            <button type="button" class="<%= yInputButtonClasses %>" data-active="false">
                                5
                            </button>
                        </div>
                    </div>
                    <div class="flex flex-col space-y-4">
                        <label class="font-medium text-slate-900" for="rInput">Введите R</label>
                        <select class="bg-white rounded-md border border-slate-200 p-2 focus:ring-0 focus:ring-offset-0 focus:border-lime-600"
                                id="rInput"
                                required
                                name="r"
                        >
                            <option selected>
                                Выберите R
                            </option>
                            <option value="1">
                                1
                            </option>
                            <option value="1.5">
                                1.5
                            </option>
                            <option value="2">
                                2
                            </option>
                            <option value="2.5">
                                2.5
                            </option>
                            <option value="3">
                                3
                            </option>
                        </select>
                    </div>
                    <p id="errors" class="text-red-600"></p>
                </div>
                <div class="flex flex-row space-x-6">
                    <button type="submit"
                            class="px-2 py-1.5 bg-lime-600 text-slate-50 font-bold hover:bg-lime-600/80 transition-all duration-150 border border-lime-700 rounded-md">
                        Отправить
                    </button>
                    <button type="reset"
                            class="px-2 py-1.5 text-slate-950 bg-white border border-slate-200 hover:text-lime-600 hover:border-lime-600 rounded-md transition-all duration-150 ">
                        Сбросить
                    </button>
                </div>
            </form>
        </div>
    </div>
</main>
<script>
    let selectedY = 0
    let yButtons = document.querySelectorAll("#yInput > button")
    let hiddenYInput = document.querySelector("input#yInputHidden")
    let canvasWrapper = document.querySelector("div#canvasWrapper")
    let canvasElement = document.querySelector("canvas")

    let xInput = document.getElementById("xInput")
    let rInput = document.getElementById("rInput")

    let errorsDescription = document.getElementById("errors")

    function selectY(value) {
        selectedY = value
        hiddenYInput.value = selectedY
        updateSelectedYAttributes()
    }

    function updateSelectedYAttributes() {
        yButtons.forEach(btn => {
            const value = parseInt(btn.innerHTML)
            if (value == selectedY) {
                btn.setAttribute("data-active", "true")
            } else {
                btn.setAttribute("data-active", "false")
            }
        })
    }

    rInput.addEventListener("change", (e) => {
        if (!isNaN(e.target.value)) {
            renderCanvas(e.target.value, [])
            canvasWrapper.setAttribute("data-active", "true")
        } else {
            canvasWrapper.setAttribute("data-active", "false")
        }
    })

    canvasElement.addEventListener("click", (e) => {
        let selectedR = rInput.value
        if(isNaN(selectedR)) {
            alert("Вы не выбрали R!")
            return
        }
        const xValue = Math.round((e.offsetX - CANVAS_SIZE / 2) / POINT_IN_PIXELS)
        const yValue = Math.round((CANVAS_SIZE / 2 - e.offsetY) / POINT_IN_PIXELS)
        xInput.value = xValue.toString()
        selectY(yValue)
        renderCanvas(selectedR, [{
            x: xValue,
            y: yValue,
            r: selectedR,
        }])
    })

    yButtons.forEach(btn => {
        btn.addEventListener("click", event => {
            selectY(parseInt(event.target.innerHTML))
        })
    })

    document.querySelector("form").addEventListener("submit", (e) => {
        errorsDescription.innerHTML = ''
        if (!validateX()) {
            e.preventDefault()
            return false;
        }
        if (!validateR()) {
            e.preventDefault()
            return false;
        }
        return true;
    })

    function validateX() {
        const value = parseFloat(xInput.value)
        if (isNaN(value)) {
            errorsDescription.innerHTML = "X не является числом!"
            console.log("X is not a number")
            return false
        }
        if (-3 <= value && value <= 5) {
            return true
        }
        errorsDescription.innerHTML = "X должен быть в промежутке [-3.. 5]"
        console.log("X is in bad range")
        return false
    }

    function validateR() {
        const value = parseFloat(rInput.value)
        if (isNaN(value)) {
            errorsDescription.innerHTML = "Выберите R!"
            return false
        }
        return true
    }
</script>
</body>
</html>
