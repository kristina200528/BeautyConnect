<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список пользователей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
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

                    <td class="status-cell">
                        <#if user.status == 'ACTIVE'>
                            <span class="badge bg-success">Активный</span>
                        <#else>
                            <span class="badge bg-secondary">Неактивный</span>
                        </#if>
                    </td>
                    <td class="action-cell">
                        <#if user.status == 'ACTIVE'>
                            <button class="btn btn-danger block-btn" data-action="block" data-user-id="${user.id}">Заблокировать</button>
                        <#else>
                            <button class="btn btn-success block-btn" data-action="unblock" data-user-id="${user.id}">Разблокировать</button>
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

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const buttons = document.querySelectorAll('.block-btn');

        buttons.forEach(button => {
            button.addEventListener('click', function () {
                const userId = this.dataset.userId;
                const action = this.dataset.action;
                const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

                fetch('/admin/' + action + '/' + userId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        [csrfHeader]: csrfToken
                    }
                })
                    .then(async response => {
                        if (response.ok) {
                            location.reload();
                        } else {
                            const errorData = await response.json();
                            alert("Ошибка: " + (errorData.error || "Неизвестная ошибка"));
                        }
                    })
                    .catch(error => {
                        console.error("Ошибка:", error);
                        alert("Ошибка при выполнении запроса");
                    });
            });
        });
    });

</script>

</html>
