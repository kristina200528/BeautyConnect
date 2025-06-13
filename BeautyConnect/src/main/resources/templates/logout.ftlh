<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Выход из системы</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center" style="height: 100vh;">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-body text-center">
                    <h3 class="card-title mb-3">Выход из системы</h3>
                    <p class="text-muted mb-4">Нажмите кнопку ниже, чтобы выйти из системы.</p>
                    <form action="/logout" method="post">
                        <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                        <button type="submit" class="btn btn-danger w-100">Выйти</button>
                    </form>
                    <hr>
                    <a href="/home" class="btn btn-outline-secondary mt-2">Отмена</a>
                </div>
            </div>
            <p class="text-center text-muted mt-3">&copy; 2025 BeautyConnect</p>
        </div>
    </div>
</div>
<!-- Bootstrap JS (optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
