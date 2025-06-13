<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>Перенос записи</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container py-4">
    <h2>Перенос записи</h2>
    <form method="post">
        <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
        <div class="mb-3">
            <label class="form-label">Мастер</label>
            <input type="text" class="form-control" value="${appointment.master.user.firstName} ${appointment.master.user.lastName}" disabled />
        </div>

        <div class="mb-3">
            <label class="form-label">Услуга</label>
            <input type="text" class="form-control" value="${appointment.service.name}" disabled />
        </div>

        <div class="mb-3">
            <label for="dateTime" class="form-label">Дата и время</label>
            <input type="datetime-local" id="dateTime" name="dateTime" class="form-control"
                   value="${dateFormatter.format(appointment.dateTime)}" required />
        </div>

        <button type="submit" class="btn btn-primary">Сохранить изменения</button>
        <a href="/my-appointments" class="btn btn-secondary ms-2">Отмена</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
