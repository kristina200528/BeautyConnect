<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-body p-4">
                    <h2 class="card-title mb-4 text-center">Профиль</h2>

                    <dl class="row">
                        <dt class="col-sm-4">Имя:</dt>
                        <dd class="col-sm-8">${user.firstName}</dd>

                        <dt class="col-sm-4">Фамилия:</dt>
                        <dd class="col-sm-8">${user.lastName}</dd>

                        <dt class="col-sm-4">Имя пользователя:</dt>
                        <dd class="col-sm-8">${user.username}</dd>

                        <dt class="col-sm-4">Email:</dt>
                        <dd class="col-sm-8">${user.email}</dd>

                        <dt class="col-sm-4">Возраст:</dt>
                        <dd class="col-sm-8">${user.age}</dd>

                        <dt class="col-sm-4">Роль:</dt>
                        <dd class="col-sm-8">
                            <#if user.role == "CLIENT">
                                Клиент
                            <#elseif user.role == "MASTER">
                                Мастер
                            </#if>
                        </dd>
                    </dl>

                    <#if user.role == "MASTER">
                        <hr>
                        <h5 class="mt-4">Информация мастера</h5>
                        <dl class="row">
                            <dt class="col-sm-4">Специализация:</dt>
                            <dd class="col-sm-8">${master.specialization!''}</dd>

                            <dt class="col-sm-4">Опыт (лет):</dt>
                            <dd class="col-sm-8">${master.experience!''}</dd>

                            <dt class="col-sm-4">Локация:</dt>
                            <dd class="col-sm-8">${master.location!''}</dd>

                            <dt class="col-sm-4">Контакты:</dt>
                            <dd class="col-sm-8">${master.contacts!''}</dd>
                        </dl>
                    </#if>

                    <div class="d-grid gap-2 mt-4">
                        <a class="btn btn-primary" href="/edit-profile">Редактировать профиль</a>
                        <a class="btn btn-danger" href="/logout">Выйти</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
