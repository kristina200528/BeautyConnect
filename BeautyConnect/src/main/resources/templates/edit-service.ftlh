<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактировать услугу</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Редактировать услугу</h2>

    <form method="post" action="/api/masters/${masterId}/services/${service.id}" id="editServiceForm">
        <input type="hidden" name="_method" value="put"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="mb-3">
            <label for="beautyServiceId" class="form-label">Услуга</label>
            <select class="form-select" id="beautyServiceId" name="beautyServiceId" required>
                <#list beautyServices as bs>
                    <option value="${bs.id}" <#if bs.id == service.beautyServiceId>selected</#if>>${bs.name}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="price" class="form-label">Цена</label>
            <input type="number" step="0.01" class="form-control" id="price" name="price" value="${service.price}" required/>
        </div>

        <div class="mb-3">
            <label for="duration" class="form-label">Длительность (мин.)</label>
            <input type="number" class="form-control" id="duration" name="durationMinutes" value="${service.durationMinutes}" required/>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea class="form-control" id="description" name="description" rows="4">${service.description!""}</textarea>
        </div>

        <button type="submit" class="btn btn-success">Сохранить изменения</button>
        <a href="/profile" class="btn btn-secondary">Отмена</a>
    </form>
</div>
<script>
    // Переопределим метод на PUT (Spring поддерживает через hidden _method)
    document.getElementById('editServiceForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const form = e.target;
        const formData = {
            beautyServiceId: form.beautyServiceId.value,
            price: parseFloat(form.price.value),
            durationMinutes: parseInt(form.durationMinutes.value),
            description: form.description.value
        };

        fetch(form.action, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': form.querySelector('input[name="${_csrf.parameterName}"]').value
            },
            body: JSON.stringify(formData)
        }).then(response => {
            if (response.ok) {
                window.location.href = "/profile?success=serviceUpdated";
            } else {
                alert("Ошибка при обновлении.");
            }
        });
    });
</script>
</body>
</html>
