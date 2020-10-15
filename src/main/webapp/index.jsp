<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Directory</title>
    </head>
    <body>
        <p>${dateTimeNow}</p>
        <h2>${pathFile}</h2>
        <hr>
            <a href="/?path=${pathFile.getParent()}">
                <img src="https://icons.iconarchive.com/icons/icojam/blue-bits/32/document-arrow-up-icon.png">
                Вверх
            </a>
        <table>
            <tr>
                <th>Имя</th>
                <th>Размер файла</th>
                <th>Дата создания</th>
            </tr>
            <c:forEach items="${files}" var="file">
                <c:if test="${!file.isHidden()}">
                    <tr>
                        <td>
                            <c:if test="${file.isDirectory()}">
                                <img src="https://icon-icons.com/icons2/47/PNG/32/folder_blue_9791.png">
                            </c:if>
                            <c:if test="${file.isFile()}">
                                <img src="https://icon-icons.com/icons2/1129/PNG/32/fileinterfacesymboloftextpapersheet_79740.png">
                            </c:if>
                        <a href="/?path=${file.getAbsolutePath()}">
                            ${file.getName()}
                        </a>
                        </td>
                        <td>
                            <c:if test="${file.isFile()}">
                                ${file.getSize()}
                            </c:if>
                        </td>
                        <td>${file.getGenerationDateTime()}</td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </body>
</html>
