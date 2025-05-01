<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список пользователей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Список пользователей</h2>

    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>Имя пользователя</th>
            <th>Email</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Возраст</th>
            <th>Роль</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <#if user.role != "ADMIN">
                <tr class="<#if user.status=="ACTIVE">table-success<#else>table-secondary</#if>">
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName!"-"}</td>
                    <td>${user.lastName!"-"}</td>
                    <td>${user.age!"-"}</td>
                    <td>${user.role}</td>
                    <td>
                        <#if user.status=="ACTIVE">
                            <span class="badge bg-success">Активный</span>
                        <#else>
                            <span class="badge bg-secondary">Неактивный</span>
                        </#if>
                    </td>
                    <td>
                        <#if user.status == 'ACTIVE'>
                            <form action="/admin/block/${user.id}" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <button type="submit" class="btn btn-danger">Заблокировать</button>
                            </form>
                        <#else>
                            <form action="/admin/unblock/${user.id}" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <button type="submit" class="btn btn-success">Разблокировать</button>
                            </form>
                        </#if>
                    </td>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>
    <div class="mt-4 text-center">
        <a class="btn btn-secondary" href="/home">Главная страница</a>
    </div>
</div>
</body>
</html>
