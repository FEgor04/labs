<%--
  Created by IntelliJ IDEA.
  User: fegor04
  Date: 16.09.2023
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String focusLimeClasses = "focus:ring-4 focus:outline-none focus:ring-lime-400 focus:border-lime-400";
    String outlineButtonClasses = "bg-white text-slate-950 border border-slate-200 transition-all duration-150 hover:text-lime-600 hover:border-lime-600";
    String yInputButtonClasses = outlineButtonClasses + " w-10 h-10 rounded-full text-center data-[active=false]:hover:text-lime-600 data-[active=false]:hover:border-lime-600 data-[active=true]:bg-lime-600 data-[active=true]:text-slate-50 data-[active=true]:border-lime-700" + focusLimeClasses;
    String inputClasses = "block bg-white rounded-md border border-slate-200 p-2 transition-all duration-150 " + focusLimeClasses;
%>

<html>
<head>
    <title>ЛР #2</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com/3.3.3"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.8.1/flowbite.min.css" rel="stylesheet"/>
</head>
<body class="bg-slate-100 text-slate-950">
<jsp:include page="components/header.jsp"/>
<main class="max-w-screen-xl mx-auto mt-16">
    <div class="flex flex-col space-y-8 md:flex-row md:justify-between md:space-y-0">
        <div id="canvasWrapper" class="data-[active=false]:cursor-not-allowed data-[active=true]:cursor-pointer"
             data-active="false">
            <jsp:include page="components/canvas.jsp"/>
        </div>
        <div id="formWrapper" class="p-4 bg-slate-50 rounded-md shadow-md border border-slate-200 w-[500px]">
            <form class="flex flex-col justify-between h-full" action="" method="POST">
                <div class="flex flex-col space-y-6">
                    <div class="flex flex-col space-y-2">
                        <label class="font-medium text-slate-900" for="xInput">Введите X</label>
                        <input class="<%= inputClasses %>"
                               type="text"
                               name="x"
                               id="xInput" required>
                    </div>
                    <div class="flex flex-col space-y-2">
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
                    <div class="flex flex-col space-y-2">
                        <label class="font-medium text-slate-900" for="rInput">Введите R</label>
                        <select class="<%= inputClasses %>"
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
                            class="px-2 py-1.5 bg-lime-600 text-slate-50 font-bold hover:bg-lime-600/80 transition-all duration-150 border border-lime-700 rounded-md <%= focusLimeClasses %>">
                        Отправить
                    </button>
                    <button type="reset"
                            class="px-2 py-1.5 text-slate-950 bg-white border border-slate-200 hover:text-lime-600 hover:border-lime-600 rounded-md transition-all duration-150 <%= focusLimeClasses %>">
                        Сбросить
                    </button>
                </div>
            </form>
        </div>
    </div>
</main>
<script type="module" src="/lab2/scripts/lib/shrinkToRange.js"></script>
<script type="module" src="/lab2/scripts/input/selectR.js"></script>
<script type="module" src="/lab2/scripts/input/selectY.js"></script>
<script type="module" src="/lab2/scripts/validation/errors.js"></script>
<script type="module" src="/lab2/scripts/validation/validateX.js"></script>
<script type="module" src="/lab2/scripts/validation/validateR.js"></script>
<script type="module" src="/lab2/scripts/validation/validateForm.js"></script>
<script type="module">
    import {selectedR, setX, setY} from "./scripts/state.js";
    import {cleanError, showError} from "./scripts/validation/errors.js";
    import shrinkToRange from "./scripts/lib/shrinkToRange.js";
    import {setR} from "./scripts/state.js";

    let canvasWrapper = document.querySelector("div#canvasWrapper")
    let canvasElement = document.querySelector("canvas")

    canvasElement.addEventListener("click", (e) => {
        if (isNaN(selectedR)) {
            showError("Сначала нужно выбрать R!")
            return
        }
        cleanError()
        const xValue = shrinkToRange(((e.offsetX - CANVAS_SIZE / 2) / POINT_IN_PIXELS).toFixed(2), -3, 5)
        const yValue = shrinkToRange(((CANVAS_SIZE / 2 - e.offsetY) / POINT_IN_PIXELS).toFixed(2), -3, 5)
        setX(xValue)
        setY(yValue)
        renderCanvas(selectedR, [{
            x: xValue,
            y: yValue,
            r: selectedR,
        }])
        document.querySelector("form").submit()
    })

    document.querySelector("form").addEventListener("reset", () => {
        setX(undefined);
        setY(0);
        setR(undefined);
        cleanError()
    })

    renderCanvas(undefined, [])
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.8.1/flowbite.min.js"></script>
</body>
</html>
