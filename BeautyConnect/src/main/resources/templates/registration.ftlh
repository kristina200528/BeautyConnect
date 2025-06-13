<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Регистрация</h3>
                </div>
                <div class="card-body">
                    <#if errorMessage??>
                        <div class="text-danger mb-3">
                            ${errorMessage}
                        </div>
                    </#if>


                    <@spring.bind "registrationForm"/>
                    <form action="/register" method="post" <#if spring.status.errors?exists>class="was-validated"</#if>>
                        <!-- Поле для имени -->
                        <div class="mb-3">
                            <label for="firstName" class="form-label">Имя</label>
                            <input type="text" class="form-control" id="firstName" name="firstName"
                                   value="${registrationForm.firstName!''}" required> <!-- Используем безопасный доступ с дефолтным значением -->
                            <#if bindingResult?? && bindingResult.hasFieldErrors('firstName')>
                                <div class="text-danger">${bindingResult.getFieldError('firstName').defaultMessage}</div>
                            </#if>
                        </div>


                        <!-- Поле для фамилии -->
                        <div class="mb-3">
                            <label for="lastName" class="form-label">Фамилия</label>
                            <input type="text" class="form-control" id="lastName" name="lastName"
                                   value="${registrationForm.lastName!''}" required> <!-- Используем безопасный доступ с дефолтным значением -->
                            <#if bindingResult?? && bindingResult.hasFieldErrors('lastName')>
                                <div class="text-danger">${bindingResult.getFieldError('lastName').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Поле для возраста -->
                        <div class="mb-3">
                            <label for="age" class="form-label">Возраст</label>
                            <input type="number" class="form-control" id="age" name="age"
                                   value="${registrationForm.age!''}" min="1" max="120" required>
                            <#if bindingResult?? && bindingResult.hasFieldErrors('age')>
                                <div class="text-danger">${bindingResult.getFieldError('age').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Поле для имени пользователя -->
                        <div class="mb-3">
                            <label for="username" class="form-label">Имя пользователя</label>
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${registrationForm.username!''}" required>
                            <#if bindingResult?? && bindingResult.hasFieldErrors('username')>
                                <div class="text-danger">${bindingResult.getFieldError('username').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Поле для электронной почты -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Электронная почта</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${registrationForm.email!''}" required>
                            <#if bindingResult?? && bindingResult.hasFieldErrors('email')>
                                <div class="text-danger">${bindingResult.getFieldError('email').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Поле для пароля -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                            <#if bindingResult?? && bindingResult.hasFieldErrors('password')>
                                <div class="text-danger">${bindingResult.getFieldError('password').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Поле для подтверждения пароля -->
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Подтвердите пароль</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            <#if bindingResult?? && bindingResult.hasFieldErrors('confirmPassword')>
                                <div class="text-danger">${bindingResult.getFieldError('confirmPassword').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Выбор роли -->
                        <div class="mb-3">
                            <label for="role" class="form-label">Роль</label>
                            <select class="form-select" id="role" name="role" required>
                                <option value="CLIENT" <#if (registrationForm.role?? && registrationForm.role == 'CLIENT')>selected</#if>>Клиент</option>
                                <option value="MASTER" <#if (registrationForm.role?? && registrationForm.role == 'MASTER')>selected</#if>>Мастер</option>
                            </select>
                            <#if bindingResult?? && bindingResult.hasFieldErrors('role')>
                                <div class="text-danger">${bindingResult.getFieldError('role').defaultMessage}</div>
                            </#if>
                        </div>

                        <!-- Поля для MASTER -->
                        <div id="masterFields" style="display: none;">
                            <!-- Специализация -->
                            <div class="mb-3">
                                <label class="form-label">Специализация</label>
                                <#list specializations as specialization>
                                    <div class="form-check">
                                        <input
                                                type="checkbox"
                                                class="form-check-input"
                                                id="specialization_${specialization.id}"
                                                name="specializationsId"
                                                value="${specialization.id}"
                                                <#if registrationForm.specializationsId?? && registrationForm.specializationsId?seq_contains(specialization.id)>checked</#if>>
                                        <label class="form-check-label" for="specialization_${specialization.id}">
                                            ${specialization.name}
                                        </label>
                                    </div>
                                </#list>
                            </div>


                            <!-- Опыт -->
                            <div class="mb-3">
                                <label for="experience" class="form-label">Опыт (в годах)</label>
                                <@spring.formInput "registrationForm.experience" "class=\"form-control\" id=\"experience\"" "number"/>
                            </div>

<#--                            <!-- Локация &ndash;&gt;-->
<#--                            <div class="mb-3">-->
<#--                                <label for="location" class="form-label">Локация</label>-->
<#--                                <@spring.formInput "registrationForm.location" "class=\"form-control\" id=\"location\"" "text"/>-->
<#--                            </div>-->

                            <!-- Вместо поля "Локация" -->
                            <div class="mb-3">
                                <label for="city" class="form-label">Город</label>
                                <input type="text" class="form-control" id="city" name="city" value="${registrationForm.city!''}" required autocomplete="off"/>
                                <#if bindingResult?? && bindingResult.hasFieldErrors('city')>
                                    <div class="text-danger">${bindingResult.getFieldError('city').defaultMessage}</div>
                                </#if>
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
                    <div class="mt-4 text-center">
                        <a href="/login">Уже есть аккаунт? Войти</a>
                    </div>
                </div>
            </div>
        </div>
        <p class="text-center text-muted mt-3">&copy; 2025 BeautyConnect</p>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css" />
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<script>
    $(function() {
        $("#city").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "/api/cities/search",
                    dataType: "json",
                    data: {
                        q: request.term
                    },
                    success: function(data) {
                        response(data);
                    },
                    error: function() {
                        response([]);
                    }
                });
            },
            minLength: 2,
            delay: 300
        });
    });
</script>

</body>
</html>