<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>Запись к мастеру</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="card shadow-sm mx-auto" style="max-width: 600px;">
        <div class="card-body">
            <h2 class="card-title mb-4 text-center">
                Запись к мастеру <strong>${master.getUser().getFirstName()}</strong><br />
                <small class="text-muted">Услуга: ${service.beautyService.name}</small>
            </h2>

            <form action="/masters/${master.id}/appointments" method="post" novalidate>
                <input type="hidden" name="serviceId" value="${service.id}" />

                <div class="mb-3">
                    <label for="dateTime" class="form-label">Дата и время записи</label>
                    <input
                            type="datetime-local"
                            id="dateTime"
                            name="dateTime"
                            class="form-control <#if errors?? && errors.hasFieldErrors('dateTime')>is-invalid</#if>"
                            value="${appointmentForm.dateTimeFormatted!}"
                            required
                    />
                    <#if errors?? && errors.hasFieldErrors('dateTime')>
                        <div class="invalid-feedback">
                            <#list errors.getFieldErrors('dateTime') as error>
                                ${error.defaultMessage}
                            </#list>
                        </div>
                    </#if>
                </div>
                <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>

                <button type="submit" class="btn btn-primary w-100">Записаться</button>
            </form>

            <a href="/masters/${master.id}" class="d-block text-center mt-4 text-decoration-none">
                &larr; Назад к профилю мастера
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
