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
        <form id="serviceForm" class="needs-validation" novalidate>
            <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label for="beautyServiceId" class="form-label">Выберите услугу*</label>
                    <select class="form-select" id="beautyServiceId" name="beautyServiceId" required>
                        <option value="">-- Выберите услугу --</option>
                        <#list beautyServices as service>
                            <option value="${service.id}">${service.name}</option>
                        </#list>
                    </select>
                    <div id="error-beautyServiceId" class="invalid-feedback"></div>
                </div>
                <div class="col-md-6">
                    <label for="servicePrice" class="form-label">Цена*</label>
                    <input class="form-control" id="servicePrice" type="number" step="0.01" min="0.01" required>
                    <div id="error-servicePrice" class="invalid-feedback"></div>
                </div>
            </div>
            <div class="mb-3">
                <label for="serviceDescription" class="form-label">Описание</label>
                <textarea class="form-control" id="serviceDescription" rows="3" maxlength="1000"></textarea>
                <div id="error-serviceDescription" class="invalid-feedback"></div>
            </div>
            <div class="mb-3">
                <label for="serviceDuration" class="form-label">Длительность (минуты)</label>
                <input class="form-control" id="serviceDuration" type="number" min="1">
                <div id="error-serviceDuration" class="invalid-feedback"></div>
            </div>

            <button type="submit" class="btn btn-primary">Добавить услугу</button>
            <a href="/profile" class="btn btn-secondary">Отмена (в профиль)</a>
        </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById("serviceForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        // Очистка старых ошибок
        ["beautyServiceId", "servicePrice", "serviceDescription", "serviceDuration"].forEach(id => {
            const input = document.getElementById(id);
            input.classList.remove("is-invalid");
            document.getElementById("error-" + id).innerText = "";
        });

        const masterId = "${masterId}";
        const csrfToken = document.querySelector('input[name="${_csrf.getParameterName()}"]').value;

        const body = {
            beautyServiceId: document.getElementById("beautyServiceId").value,
            price: parseFloat(document.getElementById("servicePrice").value),
            description: document.getElementById("serviceDescription").value,
            durationMinutes: parseInt(document.getElementById("serviceDuration").value)
        };

        try {
            const response = await fetch(`/api/masters/${masterId}/services/new`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRF-TOKEN": csrfToken
                },
                body: JSON.stringify(body)
            });

            if (response.ok) {
                window.location.href = "/profile?success=serviceAdded";
            } else if (response.status === 400) {
                const errorData = await response.json();
                if (errorData.fieldErrors) {
                    for (const [field, message] of Object.entries(errorData.fieldErrors)) {
                        const inputId = "service" + field.charAt(0).toUpperCase() + field.slice(1);
                        const input = document.getElementById(inputId);
                        const errorDiv = document.getElementById("error-" + inputId);
                        if (input && errorDiv) {
                            input.classList.add("is-invalid");
                            errorDiv.innerText = message;
                        }
                    }
                } else {
                    alert("Ошибка: " + (errorData.message || "неизвестная ошибка"));
                }
            } else {
                alert("Ошибка сервера. Код: " + response.status);
            }

        } catch (err) {
            console.error(err);
            alert("Сетевая ошибка или проблема сервера.");
        }
    });
</script>

</body>
</html>
