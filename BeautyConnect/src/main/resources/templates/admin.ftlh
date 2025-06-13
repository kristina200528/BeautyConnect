<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список пользователей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<#include "fragments/menu-for-auth.ftlh">
<body class="bg-light">
<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-11">
            <div class="card shadow-sm border-primary">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <h2 class="h4 mb-0">
                        <i class="bi bi-people-fill me-2"></i>Список пользователей
                    </h2>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead class="table-primary text-center">
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
                                    <tr class="<#if user.status == 'ACTIVE'>table-light<#else>table-secondary</#if>">
                                        <td>${user.username}</td>
                                        <td>${user.email}</td>
                                        <td>${user.firstName!"-"}</td>
                                        <td>${user.lastName!"-"}</td>
                                        <td>${user.age!"-"}</td>
                                        <td>
                                            <#if user.role == "CLIENT">
                                                <span class="badge bg-secondary-subtle text-dark">Клиент</span>
                                            <#elseif user.role == "MASTER">
                                                <span class="badge bg-secondary-subtle text-dark">Мастер</span>
                                            </#if>
                                        </td>
                                        <td>
                                            <#if user.status == 'ACTIVE'>
                                                <span class="badge bg-success">Активный</span>
                                            <#else>
                                                <span class="badge bg-secondary">Неактивный</span>
                                            </#if>
                                        </td>
                                        <td class="text-center">
                                            <#if user.status == 'ACTIVE'>
                                                <button class="btn btn-sm btn-danger block-btn" data-action="block" data-user-id="${user.id}">
                                                    <i class="bi bi-person-dash"></i> Заблокировать
                                                </button>
                                            <#else>
                                                <button class="btn btn-sm btn-success block-btn" data-action="unblock" data-user-id="${user.id}">
                                                    <i class="bi bi-person-check"></i> Разблокировать
                                                </button>
                                            </#if>
                                        </td>
                                    </tr>
                                </#if>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="mt-4 text-start">
                        <a class="btn btn-outline-secondary" href="/home">
                            <i class="bi bi-arrow-left me-1"></i> На главную
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

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
</body>
</html>
