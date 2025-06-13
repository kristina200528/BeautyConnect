<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль мастера ${master.firstName} ${master.lastName}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
<#if isAuthenticated?? && isAuthenticated>
    <#include "../fragments/menu-for-auth.ftlh">
<#else>
    <#include "../fragments/menu-for-guest.ftlh">
</#if>
<div class="container py-4">
    <#if successMessage??>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Закрыть"></button>
        </div>
    </#if>

    <!-- Кнопка назад -->
    <a href="/masters" class="btn btn-outline-secondary mb-4">
        <i class="bi bi-arrow-left"></i> Назад к списку мастеров
    </a>

    <!-- Основная карточка мастера -->
    <div class="card mb-4 border-primary">
        <div class="card-header bg-primary text-white">
            <h2 class="h4 mb-0">
                <i class="bi bi-person-badge"></i> Профиль мастера
            </h2>
        </div>
        <div class="card-body">
            <div class="row align-items-center">
                <div class="col-md-2 text-center mb-3 mb-md-0">
                    <i class="bi bi-person-circle display-3 text-primary"></i>
                </div>
                <div class="col-md-7">
                    <h3 class="card-title">${master.firstName} ${master.lastName}</h3>
                    <div class="d-flex flex-wrap gap-2 mb-2">
                        <#if master.specialization?? && (master.specialization?size > 0)>
                            <#list master.specialization as category>
                                <span class="badge bg-primary"><i class="bi bi-briefcase"></i> ${category.name}</span>
                            </#list>
                        <#else>
                            <span class="badge bg-secondary"> <i class="bi bi-briefcase"></i> Нет специализации</span>
                        </#if>
                        <span class="badge bg-success"><i class="bi bi-award"></i> Опыт: ${master.experience} лет</span>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="bg-light p-3 rounded text-center">
                        <div class="h3 mb-1">4.8</div>
                        <div class="text-warning mb-2">
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-half"></i>
                        </div>
                        <small class="text-muted">24 отзыва</small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- Контактная информация -->
        <div class="col-lg-4 mb-4">
            <div class="card h-100">
                <div class="card-header bg-white">
                    <h3 class="h5 mb-0">
                        <i class="bi bi-info-circle text-primary"></i> Контакты
                    </h3>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-envelope fs-5 text-primary me-3"></i>
                                <div>
                                    <small class="text-muted">Email</small>
                                    <div class="fw-bold">${master.email}</div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-telephone fs-5 text-primary me-3"></i>
                                <div>
                                    <small class="text-muted">Телефон</small>
                                    <div class="fw-bold">${master.contacts}</div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-geo-alt fs-5 text-primary me-3"></i>
                                <div>
                                    <small class="text-muted">Адрес</small>
                                    <div class="fw-bold">${master.location}</div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Услуги и отзывы -->
        <div class="col-lg-8">
            <!-- Услуги -->
            <div class="card mb-4">
                <div class="card-header bg-white d-flex justify-content-between align-items-center">
                    <h3 class="h5 mb-0">
                        <i class="bi bi-list-check text-primary"></i> Услуги
                    </h3>
                    <span class="badge bg-primary rounded-pill">${services?size}</span>
                </div>
                <div class="card-body">
                    <#if services?? && services?size gt 0>
                        <div class="row row-cols-1 row-cols-md-2 g-4">
                            <#list services as service>
                                <div class="col">
                                    <div class="card h-100 border-start border-primary border-3">
                                        <div class="card-body">
                                            <#assign bs = beautyServices[service.id?string]! />
                                            <div class="d-flex justify-content-between">
                                                <h5 class="card-title">
                                                    <#if bs??>
                                                        ${bs.name}
                                                    <#else>
                                                        Неизвестная услуга
                                                    </#if>
                                                </h5>
                                                <span class="badge bg-success fs-6">
                                                        ${service.price?string["0.00"]} ₽
                                                    </span>
                                            </div>

                                            <#if service.durationMinutes??>
                                                <p class="text-muted mb-2">
                                                    <i class="bi bi-clock"></i> ${service.durationMinutes} мин.
                                                </p>
                                            </#if>

                                            <#if service.description?? && service.description?has_content>
                                                <div class="bg-light p-3 rounded mt-2">
                                                    <p class="mb-0">${service.description}</p>
                                                </div>
                                            </#if>
                                        </div>
                                        <div class="card-footer bg-white border-0">
                                            <a href="/masters/${master.id}/appointments?serviceId=${service.id}" class="btn btn-outline-primary w-100">
                                                <i class="bi bi-calendar-plus me-2"></i> Записаться
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div class="alert alert-warning">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-exclamation-triangle-fill me-3 fs-4"></i>
                                <div>
                                    Мастер пока не добавил услуги в свой профиль
                                </div>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>

            <!-- Отзывы -->
            <div class="card">
                <div class="card-header bg-white">
                    <h3 class="h5 mb-0">
                        <i class="bi bi-chat-square-text text-primary"></i> Отзывы
                    </h3>
                </div>
                <div class="card-body">
                    <div class="alert alert-info">
                        <div class="d-flex align-items-center">
                            <i class="bi bi-info-circle-fill me-3"></i>
                            <div>Здесь будут отображаться отзывы о работе мастера</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>