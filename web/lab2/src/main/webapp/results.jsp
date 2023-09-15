<%@ page import="efedorov.model.store.HistoryManager" %>
<%@ page import="efedorov.model.store.HistoryManagerProvider" %>
<%@ page import="efedorov.model.HistoryEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="efedorov.formatter.HistoryEntryFormatter" %>
<%@ page import="efedorov.formatter.HistoryEntryJsonFormatter" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String tdClasses = "border border-slate-200 border-collapse p-1";
    String headTdClasses = tdClasses + "font-bold";
    HistoryManager historyManager = HistoryManagerProvider.INSTANCE.getInstance();
    List<HistoryEntry> entries = historyManager.getEntries(session);
    HistoryEntryFormatter formatter = new HistoryEntryFormatter();

    HistoryEntryJsonFormatter jsonFormatter = new HistoryEntryJsonFormatter();

    String pointsJson = entries
            .stream()
            .map(jsonFormatter::format)
            .toList()
            .toString();
%>
<html>
<head>
    <title>ЛР #2</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-100 text-slate-950">
<jsp:include page="components/header.jsp"/>
<main class="max-w-screen-xl mx-auto mt-16">
    <div class="flex flex-col items-center space-y-8 w-full">
        <div>
            <jsp:include page="components/canvas.jsp"/>
        </div>
        <div id="historyWrapper" class="w-full">
            <table class="bg-slate-50 w-full rounded-md shadow-md border border-slate-200">
                <tr class="p-2">
                    <th class="<%= headTdClasses %>">
                        X
                    </th>
                    <th class="<%= headTdClasses %>">
                        Y
                    </th>
                    <th class="<%= headTdClasses %>">
                        R
                    </th>
                    <th class="<%= headTdClasses %>">
                        Попал?
                    </th>
                    <th class="<%= headTdClasses %>">
                        Время выполнения
                    </th>
                    <th class="<%= headTdClasses %>">
                        Время на сервере
                    </th>
                </tr>
                <%
                    for (HistoryEntry entry : entries) {
                        out.println(formatter.format(entry));
                    }
                %>
            </table>
        </div>
        <div class="flex flex-row justify-center mx-auto space-x-8">
            <a href="/lab2">
                <button type="button"
                        class="px-2 py-1.5 bg-lime-600 text-slate-50 font-bold hover:bg-lime-600/80 transition-all duration-150 border border-lime-700 rounded-md"
                        id="backButton"
                >
                    Обратно
                </button>
            </a>
<%--            <button type="button"--%>
<%--                    id="cleanButton"--%>
<%--                    disabled--%>
<%--                    class="px-2 py-1.5 text-slate-950 bg-white border border-slate-200 hover:text-lime-600 hover:border-lime-600 rounded-md transition-all duration-150 ">--%>
<%--                Очистить--%>
<%--            </button>--%>
        </div>

    </div>
</main>
<script>
    const historyEntriesJson = `<%= pointsJson %>`
    /** @type {Object[]} */
    const historyEntries = JSON.parse(historyEntriesJson)

    const lastR = historyEntries[historyEntries.length - 1].point.radius

    const entriesToRender = historyEntries.filter((entry) => {
        return entry.point.radius === lastR
    })

    const pointsToRender = entriesToRender.map((entry) => {
        return {
            x: entry.point.x,
            y: entry.point.y,
            r: entry.point.radius,
            success: entry.success,
        }
    })

    console.log(pointsToRender)
    console.log("Last R is ", lastR)

    renderCanvas(lastR, pointsToRender)
</script>
</body>
</html>
