<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Записи ко мне</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body class="bg-light">
<#if isAuthenticated?? && isAuthenticated>
    <#include "../fragments/menu-for-auth.ftlh">
<#else>
    <#include "../fragments/menu-for-guest.ftlh">
</#if>
<div class="container py-5">
    <h1 class="mb-4">Записи клиентов ко мне</h1>

    <#if appointments?size gt 0>
    <table class="table table-bordered table-hover bg-white shadow-sm">
        <thead class="table-light">
        <tr>
            <th>Клиент</th>
            <th>Услуга</th>
            <th>Дата и время</th>
            <th>Статус</th>
        </tr>
        </thead>
        <tbody>
        <#list appointments as appt>
        <tr>
            <td>
                <#if appt.client.firstName?? || appt.client.lastName??>
                    ${appt.client.firstName!''} ${appt.client.lastName!''}
                <#else>
                    ${appt.client.username}
                </#if>
            </td>
            <td>${appt.service.name}</td>
            <td>${dateFormatter.format(appt.dateTime)}</td>
            <td>
                <#switch appt.status>
                    <#case "CREATED">
                        <span class="badge bg-warning text-dark">Создана</span>
                        <#break>
                    <#case "CONFIRMED">
                        <span class="badge bg-primary">Подтверждена</span>
                        <#break>
                    <#case "CANCELLED">
                        <span class="badge bg-danger">Отменена</span>
                        <#break>
                    <#case "COMPLETED">
                        <span class="badge bg-success">Выполнена</span>
                        <#break>
                    <#default>
                        <span class="badge bg-secondary">${appt.status?cap_first}</span>
                </#switch>
            </td>
            <td>
                <#if appt.status != "CANCELLED" && appt.status != "COMPLETED">
                    <form method="post" action="/master/appointments/${appt.id}/cancel" class="d-inline">
                        <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                        <button type="submit" class="btn btn-sm btn-danger">
                            <i class="bi bi-x-circle"></i> Отменить
                        </button>
                    </form>
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
    <#else>
    <div class="alert alert-info" role="alert">
        Пока нет записей.
    </div>
    </#if>

    <div class="mt-4">
        <a href="/profile" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-1"></i> Назад в профиль
        </a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
