<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в систему</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center" style="height: 100vh;">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="card-title text-center mb-4">Вход в систему</h3>

                    <!-- Ошибка неверных данных -->
                    <#if error??>
                        <div class="alert alert-danger" role="alert">
                            Неверное имя пользователя или пароль.
                        </div>
                    </#if>

                    <!-- Ошибка блокировки -->
                    <#if blocked??>
                        <div class="alert alert-warning" role="alert">
                            Ваш аккаунт заблокирован.
                        </div>
                    </#if>

                    <form action="/login" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <div class="mb-3">
                            <label for="username" class="form-label">Имя пользователя</label>
                            <input type="text" id="username" name="username" class="form-control" required autofocus>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Войти</button>
                    </form>

                    <div class="mt-4 text-center">
                        <a href="/register">Регистрация</a>
                    </div>
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
