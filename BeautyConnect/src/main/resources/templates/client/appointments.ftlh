<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Мои записи</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body class="bg-light">
<#include "../fragments/menu-for-auth.ftlh">
<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">

            <div class="card border-primary shadow-sm mb-4">
                <div class="card-header bg-primary text-white d-flex align-items-center">
                    <i class="bi bi-calendar-check-fill fs-3 me-2"></i>
                    <h2 class="h4 mb-0">Мои записи</h2>
                </div>
                <div class="card-body bg-white">

                    <#if appointments?? && appointments?size gt 0>
                        <div class="list-group">
                            <#list appointments as appointment>
                                <div class="list-group-item list-group-item-action mb-3 rounded border shadow-sm">
                                    <h5 class="mb-2">
                                        <i class="bi bi-scissors me-2 text-primary"></i>
                                        ${appointment.serviceName} у мастера ${appointment.masterFirstName} ${appointment.masterLastName}
                                    </h5>
                                    <p class="mb-1">
                                        <i class="bi bi-calendar-event me-1"></i>
                                        <strong>Дата и время:</strong> ${dateFormatter.format(appointment.dateTime)}
                                    </p>
                                    <p class="mb-0">
                                        <i class="bi bi-info-circle me-1"></i>
                                        <strong>Статус:</strong>
                                        <#if appointment.status == "CREATED">Создана
                                        <#elseif appointment.status == "CONFIRMED">Подтверждена
                                        <#elseif appointment.status == "CANCELLED">Отменена
                                        <#elseif appointment.status == "COMPLETED">Выполнена
                                        <#else>Неизвестно</#if>
                                    </p>

                                    <#if appointment.status != "CANCELLED" && appointment.status != "COMPLETED">
                                        <div class="mt-2">
                                            <form method="post" action="/appointments/${appointment.id}/cancel" class="d-inline">
                                                <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                                                <button type="submit" class="btn btn-sm btn-danger">
                                                    <i class="bi bi-x-circle"></i> Отменить
                                                </button>
                                            </form>

                                            <a href="/appointments/${appointment.id}/edit" class="btn btn-sm btn-warning ms-2">
                                                <i class="bi bi-pencil-square"></i> Перенести
                                            </a>
                                        </div>
                                    </#if>

                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div class="alert alert-info d-flex align-items-center">
                            <i class="bi bi-info-circle-fill me-2 fs-4"></i>
                            У вас пока нет записей.
                        </div>
                    </#if>

                    <div class="mt-4">
                        <a href="/home" class="btn btn-outline-primary">
                            <i class="bi bi-house-door me-1"></i> На главную
                        </a>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>