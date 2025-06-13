<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="_csrf" content="${_csrf.token}" />
    <title>Профиль пользователя</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body class="bg-light">
<#include "fragments/menu-for-auth.ftlh">
<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <#if RequestParameters["success"]?? && RequestParameters["success"] == "serviceAdded">
                <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                    Услуга успешно добавлена!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Закрыть"></button>
                </div>
            </#if>
            <#if RequestParameters["success"]?? && RequestParameters["success"] == "serviceUpdated">
                <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                    Услуга успешно обновлена!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Закрыть"></button>
                </div>
            </#if>


            <!-- Основная карточка профиля -->
            <div class="card border-primary mb-4 shadow-sm">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <h2 class="h4 mb-0">
                        <i class="bi bi-person-circle me-2"></i>Профиль пользователя
                    </h2>
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
                                                <#if user.role == "MASTER">
                                                    <span class="badge bg-success p-2">Мастер</span>
                                                <#elseif user.role == "CLIENT">
                                                    <span class="badge bg-info p-2">Клиент</span>
                                                <#elseif user.role == "ADMIN">
                                                    <span class="badge bg-danger p-2">Админ</span>
                                                </#if>
                                            </dd>

                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <#if user.role == "MASTER">
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
                                                        <!-- Кнопки действия -->
                                                        <div class="mt-3 d-flex gap-2">
                                                            <a href="/masters/${masterId}/services/${service.id}/edit" class="btn btn-outline-primary btn-sm">
                                                                <i class="bi bi-pencil-square me-1"></i> Изменить
                                                            </a>
                                                            <button type="button"
                                                                    class="btn btn-outline-danger btn-sm"
                                                                    onclick="initDeleteService(this)"
                                                                    data-master-id="${masterId}"
                                                                    data-service-id="${service.id}">
                                                                <i class="bi bi-trash me-1"></i> Удалить
                                                            </button>

                                                        </div>
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
                            <div class="mb-4 text-center">
                                <a href="/master/appointments" class="btn btn-primary btn-lg">
                                    <i class="bi bi-calendar-check me-2"></i> Просмотреть записи клиентов
                                </a>
                            </div>
                        </#if>
                    <#else>
                        Нет данных о пользователе
                    </#if>
                    <div class="d-flex justify-content-start mt-3">
                        <#if user.role == "CLIENT">
                            <a href="/appointments/my" class="btn btn-outline-primary">
                                <i class="bi bi-calendar-check me-1"></i> Мои записи
                            </a>
                        </#if>
                    </div>
                    <!-- Кнопки действий -->
                    <div class="d-flex justify-content-start mt-4">
                        <a href="/home" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left me-1"></i> На главную
                        </a>
                    </div>
                    <#if user.role == "ADMIN">
                        <div class="d-flex justify-content-start mt-3">
                            <a href="/admin" class="btn btn-outline-danger">
                                <i class="bi bi-shield-lock me-1"></i> Панель администратора
                            </a>
                        </div>
                    </#if>


                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Модальное окно подтверждения удаления -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="deleteConfirmLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title" id="deleteConfirmLabel">Подтвердите удаление</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <div class="modal-body">
                Вы уверены, что хотите удалить эту услугу?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                <button type="button" class="btn btn-danger" id="confirmDeleteBtn">Удалить</button>
            </div>
        </div>
    </div>
</div>
<script>
    let deleteModal = null;

    // Инициализация модального окна при загрузке страницы
    document.addEventListener('DOMContentLoaded', function() {
        deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));

        // Обработчик подтверждения удаления
        document.getElementById("confirmDeleteBtn").addEventListener("click", function () {
            const cardElement = document.querySelector('[data-deleting="true"]');
            const masterId = cardElement?.dataset.masterId;
            const serviceId = cardElement?.dataset.serviceId;

            if (!masterId || !serviceId) {
                alert("Ошибка: не указаны идентификаторы для удаления");
                return;
            }

            const url = '/api/masters/' + masterId + '/services/' + serviceId;

            fetch(url, {
                method: 'DELETE',
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        cardElement?.remove();
                        deleteModal.hide();
                    } else {
                        throw new Error('Ошибка при удалении');
                    }
                })
                .catch(err => {
                    console.error(err);
                    alert("Произошла ошибка при удалении: " + err.message);
                })
                .finally(() => {
                    if (cardElement) cardElement.removeAttribute('data-deleting');
                });
        });
    });

    // Функция инициализации удаления
    function initDeleteService(button) {
        const cardElement = button.closest(".col-12");

        // Добавляем данные в карточку
        cardElement.dataset.masterId = button.dataset.masterId;
        cardElement.dataset.serviceId = button.dataset.serviceId;
        cardElement.dataset.deleting = "true";

        // Показываем модальное окно
        deleteModal.show();
    }
</script>

</body>
</html>
