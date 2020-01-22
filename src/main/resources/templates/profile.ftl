<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="form-style-2-heading">${user.login}</div>

<a class="button-add" href="/lists">New</a>

<#if user.wishLists??>
    <#list user.wishLists as list>
        <li id="${list.id}">
            <a href="/${user.login}/lists/${list.id}">${list.title}</a>&nbsp;&nbsp;
            <br>
            <br>
        </li>
        <br>
    </#list>
</#if>

<a href="/logout">Выход</a>
</body>
</html>