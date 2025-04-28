<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация в ZEN</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Регистрация в ZEN</h3>
                </div>
                <div class="card-body">
                    <#if errorMessage??>
                        <div class="text-danger mb-3">
                            ${errorMessage}
                        </div>
                    </#if>
                    <@spring.bind "registrationForm"/>
                    <form action="registration" method="post" <#if spring.status.errors?exists>class="was-validated"</#if>>
                        <!-- Поле для имени -->
                        <div class="mb-3">
                            <label for="firstName" class="form-label">Имя</label>
                            <@spring.formInput "registrationForm.firstName" "class=\"form-control\" id=\"firstName\" name=\"firstName\" required" "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для фамилии -->
                        <div class="mb-3">
                            <label for="lastName" class="form-label">Фамилия</label>
                            <@spring.formInput "registrationForm.lastName" "class=\"form-control\" id=\"lastName\" name=\"lastName\" required" "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для возраста -->
                        <div class="mb-3">
                            <label for="age" class="form-label">Возраст</label>
                            <@spring.formInput "registrationForm.age" "class=\"form-control\" id=\"age\" name=\"age\" min=\"1\" max=\"120\" required" "number"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для имени пользователя -->
                        <div class="mb-3">
                            <label for="username" class="form-label">Имя пользователя</label>
                            <@spring.formInput "registrationForm.username" "class=\"form-control\" id=\"username\" name=\"username\" required" "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для электронной почты -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Электронная почта</label>
                            <@spring.formInput "registrationForm.email" "class=\"form-control\" id=\"email\" name=\"email\" required" "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для пароля -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <@spring.formInput "registrationForm.password" "class=\"form-control\" id=\"password\" name=\"password\" required" "password"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для подтверждения пароля -->
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Подтвердите пароль</label>
                            <@spring.formInput "registrationForm.confirmPassword" "class=\"form-control\" id=\"confirmPassword\" name=\"confirmPassword\" required" "password"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Выбор роли -->
                        <div class="mb-3">
                            <label for="role" class="form-label">Роль</label>
                            <@spring.formSingleSelect "registrationForm.role" {
                            "CLIENT": "Клиент",
                            "MASTER": "Мастер"
                            } "class=\"form-select\" id=\"role\" required"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поля для MASTER -->
                        <div id="masterFields" style="display: none;">
                            <!-- Специализация -->
                            <div class="mb-3">
                                <label for="specialization" class="form-label">Специализация</label>
                                <@spring.formInput "registrationForm.specialization" "class=\"form-control\" id=\"specialization\"" "text"/>
                            </div>

                            <!-- Опыт -->
                            <div class="mb-3">
                                <label for="experience" class="form-label">Опыт (в годах)</label>
                                <@spring.formInput "registrationForm.experience" "class=\"form-control\" id=\"experience\"" "number"/>
                            </div>

                            <!-- Локация -->
                            <div class="mb-3">
                                <label for="location" class="form-label">Локация</label>
                                <@spring.formInput "registrationForm.location" "class=\"form-control\" id=\"location\"" "text"/>
                            </div>

                            <!-- Контакты -->
                            <div class="mb-3">
                                <label for="contacts" class="form-label">Контакты</label>
                                <@spring.formInput "registrationForm.contacts" "class=\"form-control\" id=\"contacts\"" "text"/>
                            </div>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <!-- Кнопка отправки формы -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const roleSelect = document.getElementById("role");
        const masterFields = document.getElementById("masterFields");

        function toggleMasterFields() {
            if (roleSelect.value === "MASTER") {
                masterFields.style.display = "block";
            } else {
                masterFields.style.display = "none";
            }
        }

        roleSelect.addEventListener("change", toggleMasterFields);
        toggleMasterFields(); // вызов при загрузке
    });
</script>

<!-- Подключаем Bootstrap JS (необходим для некоторых компонентов) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>