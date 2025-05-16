<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Профиль пользователя</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body class="bg-light">

<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-10">

            <!-- Основная карточка профиля -->
            <div class="card border-primary mb-4 shadow-sm">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <h2 class="h4 mb-0">
                        <i class="bi bi-person-circle me-2"></i>Профиль пользователя
                    </h2>
                    <a href="/logout" class="btn btn-outline-light btn-sm">
                        <i class="bi bi-box-arrow-right me-1"></i> Выйти
                    </a>
                </div>

                <div class="card-body">

                    <!-- Основная информация -->
                    <div class="mb-4 p-3 border rounded shadow-sm bg-white">
                        <h4 class="mb-3 pb-2 border-bottom border-primary">
                            <i class="bi bi-info-circle-fill text-primary me-2"></i>Основная информация
                        </h4>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <div class="p-3 border rounded bg-white h-100">
                                    <h5 class="text-center mb-3">
                                        <i class="bi bi-person-badge"></i> Личные данные
                                    </h5>
                                    <#if user??>
                                        <dl class="row mb-0">
                                            <dt class="col-sm-5">Имя:</dt>
                                            <dd class="col-sm-7 fw-bold">${user.firstName!"-"}</dd>

                                            <dt class="col-sm-5">Фамилия:</dt>
                                            <dd class="col-sm-7 fw-bold">${user.lastName!"-"}</dd>

                                            <dt class="col-sm-5">Имя пользователя:</dt>
                                            <dd class="col-sm-7 fw-bold">${user.username}</dd>

                                            <dt class="col-sm-5">Возраст:</dt>
                                            <dd class="col-sm-7 fw-bold">${user.age!"-"}</dd>
                                        </dl>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="p-3 border rounded bg-white h-100">
                                        <h5 class="text-center mb-3">
                                            <i class="bi bi-envelope-at"></i> Контакты
                                        </h5>
                                        <dl class="row mb-0">
                                            <dt class="col-sm-5">Email:</dt>
                                            <dd class="col-sm-7 fw-bold">${user.email}</dd>

                                            <dt class="col-sm-5">Роль:</dt>
                                            <dd class="col-sm-7">
                                                <span class="badge
                                                    <#if user.role == "MASTER">bg-success<#else>bg-info</#if> p-2">
                                                    <#if user.role == "CLIENT">Клиент<#elseif user.role == "MASTER">Мастер</#if>
                                                </span>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <#if user.role == "MASTER">
                            <!-- Информация мастера -->
                            <div class="mb-4 p-3 border rounded shadow-sm bg-white">
                                <h4 class="mb-3 pb-2 border-bottom border-primary">
                                    <i class="bi bi-award-fill text-primary me-2"></i>Профессиональная информация
                                </h4>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="p-3 border rounded bg-white h-100 text-center">
                                            <h5 class="mb-3"><i class="bi bi-briefcase"></i> Специализация</h5>
                                            <#if master.specialization?? && (master.specialization?size > 0)>
                                                <ul class="list-unstyled mb-0 fw-bold fs-5">
                                                    <#list master.specialization as category>
                                                        <li>${category.name!'-'}</li>
                                                    </#list>
                                                </ul>
                                            <#else>
                                                <p class="fw-bold fs-5">-</p>
                                            </#if>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="p-3 border rounded bg-white h-100 text-center">
                                            <h5 class="mb-3"><i class="bi bi-clock-history"></i> Опыт работы</h5>
                                            <p class="fw-bold fs-5">${master.experience!'-'} лет</p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="p-3 border rounded bg-white h-100 text-center">
                                            <h5 class="mb-3"><i class="bi bi-geo-alt"></i> Локация</h5>
                                            <p class="fw-bold fs-5">${master.location!'-'}</p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="p-3 border rounded bg-white h-100 text-center">
                                            <h5 class="mb-3"><i class="bi bi-telephone"></i> Контакты</h5>
                                            <p class="fw-bold fs-5">${master.contacts!'-'}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Услуги мастера -->
                            <div class="mb-4 p-3 border rounded shadow-sm bg-white">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <h4 class="mb-0 pb-2 border-bottom border-primary">
                                        <i class="bi bi-list-check text-primary me-2"></i>Мои услуги
                                    </h4>
                                    <#if masterId??>
                                        <a href="/masters/${masterId}/services/new" class="btn btn-primary btn-sm">
                                            <i class="bi bi-plus-circle me-1"></i> Добавить услугу
                                        </a>
                                    </#if>
                                </div>

                                <#if services?? && services?has_content>
                                    <div class="row g-3">
                                        <#list services as service>
                                            <div class="col-12">
                                                <div class="card border-start border-4 border-primary">
                                                    <div class="card-body">
                                                        <#assign bs = beautyServices[service.id?string]! />
                                                        <div class="d-flex justify-content-between align-items-start">
                                                            <div>
                                                                <h5 class="card-title mb-1">
                                                                    <#if bs??>
                                                                        ${bs.name}
                                                                    <#else>
                                                                        Неизвестная услуга
                                                                    </#if>
                                                                </h5>
                                                                <#if service.durationMinutes??>
                                                                    <p class="text-muted small mb-2">
                                                                        <i class="bi bi-clock"></i> Длительность: ${service.durationMinutes} мин.
                                                                    </p>
                                                                </#if>
                                                            </div>
                                                            <span class="badge bg-success fs-6 p-2">
                                                    ${service.price?string["0.00"]} руб.
                                                </span>
                                                        </div>
                                                        <#if service.description?? && service.description?has_content>
                                                            <div class="mt-2 p-2 bg-light rounded">
                                                                <p class="mb-0">${service.description}</p>
                                                            </div>
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>
                                        </#list>
                                    </div>
                                <#else>
                                    <div class="alert alert-warning d-flex align-items-center">
                                        <i class="bi bi-exclamation-triangle-fill me-2 fs-4"></i>
                                        <div class="fs-5">Вы еще не добавили ни одной услуги</div>
                                    </div>
                                </#if>
                            </div>
                        </#if>
                    <#else>
                        Нет данных о пользователе
                    </#if>
                    <!-- Кнопки действий -->
                    <div class="d-flex justify-content-start mt-4">
                        <a href="/home" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left me-1"></i> На главную
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
