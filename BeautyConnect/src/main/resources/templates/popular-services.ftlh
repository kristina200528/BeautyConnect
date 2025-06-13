<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Популярные услуги</title>
</head>
<body>
<h1>Популярные услуги за последние 30 дней</h1>

<#if popularServices?has_content>
    <ul>
        <#list popularServices as service>
            <li>
                <strong>${service.name}</strong>
                <#if service.category??>
                    — категория: ${service.category.name}
                </#if>
            </li>
        </#list>
    </ul>
<#else>
    <p>Пока нет популярных услуг.</p>
</#if>

</body>
</html>
