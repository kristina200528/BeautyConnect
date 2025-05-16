<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить новую услугу для мастера ID: ${masterId}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .invalid-feedback { display: block; }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2>Добавить новую услугу</h2>

        <#if errorMessage??>
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </#if>

    <#if successMessage??>
        <div role="alert">
            ${errorMessage}
        </div>
    </#if>

        <@spring.bind "newService" />
        <form action="/masters/${masterId}/services/new" method="post" class="needs-validation <#if spring.status.error>was-validated</#if>" novalidate>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label for="beautyServiceId" class="form-label">Выберите услугу*</label>
                    <select class="form-select" id="beautyServiceId" name="beautyServiceId" required>
                        <option value="">-- Выберите услугу --</option>
                        <#list beautyServices as service>
                            <option value="${service.id}" <#if newService.beautyServiceId?? && (newService.beautyServiceId == service.id)>selected</#if>>
                                ${service.name}
                            </option>
                        </#list>
                    </select>
                    <@spring.showErrors "beautyServiceId" "invalid-feedback" />
                </div>
                <div class="col-md-6">
                    <label for="servicePrice" class="form-label">Цена*</label>
                    <@spring.formInput path="newService.price" attributes='class="form-control" id="servicePrice" type="number" step="0.01" min="0.01" required' />
                    <@spring.showErrors "<br>" "invalid-feedback" />
                </div>
            </div>
            <div class="mb-3">
                <label for="serviceDescription" class="form-label">Описание</label>
                <@spring.formTextarea path="newService.description" attributes='class="form-control" id="serviceDescription" rows="3" maxlength="1000"' />
                <@spring.showErrors "<br>" "invalid-feedback" />
            </div>
            <div class="mb-3">
                <label for="serviceDuration" class="form-label">Длительность (минуты)</label>
                <@spring.formInput path="newService.durationMinutes" attributes='class="form-control" id="serviceDuration" type="number" min="1"' />
                <@spring.showErrors "<br>" "invalid-feedback" />
            </div>

            <button type="submit" class="btn btn-primary">Добавить услугу</button>
            <a href="/profile" class="btn btn-secondary">Отмена (в профиль)</a>
        </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
