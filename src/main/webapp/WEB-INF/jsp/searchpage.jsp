<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cees
  Date: 14/10/2022
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="../../resources/javascript/checkSubmitButton.js"></script>
    <script type="text/javascript" src="../../resources/javascript/formatJson.js"></script>
    <link rel="stylesheet" href="../../resources/css/index.css" type="text/css">
    <link rel="stylesheet" href="../../resources/css/searchpage.css" type="text/css">
    <title>Find Company</title>
</head>
<body>
<h1>Find Your Company</h1>
<%--@elvariable id="SearchInputForm" type="com.example.kvkpliegerdemo3.form.SearchInputForm"--%>
<form:form action="search" method="post" modelAttribute="SearchInputForm">
    <div>
        <label id="search-bar-label">Enter KVK number or Company Name</label>
        <input id="search-input" name="searchInput" value="${SearchInputForm.searchInput}"
               onkeyup="checkSubmitButton()"/>
        <button id="search-button" type="submit">search</button>
        <p class="error-message">${errorMessage}</p>
    </div>
    <script>
        checkSubmitButton();
    </script>
</form:form>
<c:set var="resultPresent" value="${json != null}"/>
<c:if test="${resultPresent}">
    <div class="card" id='json-card'>
        <h4 class="json-request">Request:</h4>
        <p class="json-request" id="json-request-url">${request}</p>
        <h4>Response:</h4>
        <div id="json-card-response">
        </div>
        <script>
            formatJson(${json})
        </script>
    </div>
</c:if>

<c:forEach items="${results}" var="result">
    <%--@elvariable id="RegistrationForm" type="com.example.kvkpliegerdemo3.form.RegistrationForm"--%>
    <form:form action="select-company" method="post" class="card" modelAttribute="RegistrationForm">
        <div>
            <label>Company</label>
            <textarea name="companyName" hidden>${result.getName()}</textarea>
            <p>${result.getName()}</p>
            <label>KVK Number</label>
            <textarea name="kvkNumber" hidden>${result.getKvkNumber()}</textarea>
            <p name="kvkNumber">${result.getKvkNumber()}</p>
            <label>Location</label>
            <textarea name="location" hidden>${result.getLocation()}</textarea>
            <p name="location">${result.getLocation()}</p>
            <div id="right-side-card">
                <button name="selectedKvk" type="submit" value="${ result.getKvkNumber() }" id="company-button">This is
                    My Company
                </button>
            </div>
        </div>
    </form:form>
</c:forEach>
</body>
</html>
