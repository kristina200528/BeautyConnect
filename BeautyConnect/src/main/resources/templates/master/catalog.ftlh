<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Каталог мастеров</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
<#if isAuthenticated?? && isAuthenticated>
    <#include "../fragments/menu-for-auth.ftlh">
<#else>
    <#include "../fragments/menu-for-guest.ftlh">
</#if>
<div class="container py-5">
    <div class="text-center mb-5">
        <h1 class="display-5 fw-bold">Наши мастера</h1>
        <p class="lead text-muted">Выберите специалиста для записи</p>
    </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <#if masters?size == 0>
            <div class="col">
                <div class="alert alert-info text-center py-4">
                    <i class="bi bi-emoji-frown fs-1"></i>
                    <h4 class="mt-3">Мастеров пока нет</h4>
                </div>
            </div>
        <#else>
            <#list masters as master>
                <div class="col">
                    <div class="card h-100 shadow-sm">
                        <div class="card-body text-center">
                            <div class="mb-3">
                                <i class="bi bi-person-circle fs-1 text-primary"></i>
                            </div>
                            <h3 class="h5 card-title">${master.user.firstName} ${master.user.lastName}</h3>

                            <#if master.specialization?? && (master.specialization?size > 0)>
                                <div class="mb-2">
                                    <#list master.specialization as category>
                                        <span class="badge bg-primary me-1">${category.name}</span>
                                    </#list>
                                </div>
                            <#else>
                                <div class="mb-2">
                                    <span class="badge bg-secondary">Нет специализации</span>
                                </div>
                            </#if>

                            <ul class="list-unstyled">
                                <li><i class="bi bi-briefcase me-2"></i> Опыт: ${master.experience} лет</li>
                                <li><i class="bi bi-geo-alt me-2"></i> ${master.location}</li>
                            </ul>
                        </div>
                        <div class="card-footer bg-white border-0">
                            <a href="/masters/${master.id}" class="btn btn-outline-primary w-100">
                                Подробнее
                            </a>
                        </div>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>