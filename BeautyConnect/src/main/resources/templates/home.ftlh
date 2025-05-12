<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добро пожаловать!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h1 class="mt-5">Добро пожаловать, ${userName}!</h1>
            <p>Вы успешно вошли в систему.</p>

            <#if isAdmin>
                <a class="btn btn-secondary mt-3" href="/admin">Панель администратора</a>
            </#if>
            <a class="btn btn-secondary mt-3" href="/masters">Все мастера</a>
            <a class="btn btn-secondary mt-3" href="/profile">Мой профиль</a>
            <a class="btn btn-danger mt-3" href="/logout">Выйти</a>
        </div>
    </div>
</div>
</body>
</html>
