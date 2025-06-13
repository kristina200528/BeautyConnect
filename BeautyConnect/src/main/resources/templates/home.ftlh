<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>BeautyConnect — Платформа красоты</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm border-bottom">
    <div class="container">
        <a class="navbar-brand fw-bold" href="/home">BeautyConnect</a>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <#if isAuthenticated>
                    <#include "fragments/menu-for-auth.ftlh">
                <#else>
                    <#include "fragments/menu-for-guest.ftlh">
                </#if>
            </ul>
        </div>
    </div>
</nav>


<!-- Hero-секция -->
<section class="py-5 text-center bg-white">
    <div class="container">
        <h1 class="display-4 fw-semibold">Найди своего мастера красоты</h1>
        <p class="lead text-muted mt-3 mb-4">
            BeautyConnect — платформа, где клиенты встречаются с талантливыми мастерами.
            Бронирование услуг, профили специалистов и управление расписанием — всё в одном месте.
        </p>
        <div class="d-flex flex-column flex-sm-row justify-content-center gap-3">
            <#if isAuthenticated?? && !isAuthenticated>
                <a href="/register" class="btn btn-primary btn-lg px-4">Присоединиться</a>
            <#else>
                <#if user.role == "CLIENT">
                    <a href="/appointments/my" class="btn btn-primary btn-lg px-4">Мои записи</a>
                <#elseif user.role == "MASTER">
                    <a href="/master/appointments" class="btn btn-primary btn-lg px-4">Клиентские записи</a>
                <#elseif user.role == "ADMIN">
                    <a href="/admin" class="btn btn-primary btn-lg px-4">Панель администратора</a>
                </#if>
            </#if>
        </div>
    </div>
</section>

<!-- Описание возможностей -->
<section class="py-5 bg-light border-top border-bottom">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-4 mb-4">
                <h5 class="mb-3">Для клиентов</h5>
                <p class="text-muted">Ищите мастеров по рейтингу, специализации и местоположению. Легко записывайтесь онлайн.</p>
            </div>
            <div class="col-md-4 mb-4">
                <h5 class="mb-3">Для мастеров</h5>
                <p class="text-muted">Создайте профиль, добавьте услуги, управляйте расписанием и получайте новых клиентов.</p>
            </div>
            <div class="col-md-4 mb-4">
                <h5 class="mb-3">Всё под контролем</h5>
                <p class="text-muted">Платформа автоматизирует записи, уведомления и помогает строить доверие через отзывы.</p>
            </div>
        </div>
    </div>
</section>

<!-- Популярные услуги -->
<!-- Популярные услуги -->
<#if popularServices?? && popularServices?size gt 0>
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="text-center mb-5 display-5 fw-bold text-primary">🔥 Самые популярные услуги этого месяца</h2>
            <div class="row justify-content-center">
                <#list popularServices as service>
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card h-100 border-0 shadow-lg rounded-4">
                            <div class="card-body text-center">
                                <div class="mb-3">
                                    <i class="bi bi-stars text-warning" style="font-size: 2rem;"></i>
                                </div>
                                <h5 class="card-title fw-bold">${service.name}</h5>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </section>
</#if>


<!-- Footer -->
<footer class="py-4 bg-white">
    <div class="container text-center">
        <small class="text-muted">© 2025 BeautyConnect — Все права защищены</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
