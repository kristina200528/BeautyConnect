<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль мастера</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
<div class="container py-5">
    <a href="/masters" class="btn btn-outline-secondary mb-4">
        <i class="bi bi-arrow-left"></i> Назад
    </a>

    <div class="card shadow">
        <div class="card-body p-4">
            <div class="row align-items-center">
                <div class="col-md-3 text-center">
                    <i class="bi bi-person-circle display-3 text-primary"></i>
                </div>
                <div class="col-md-9">
                    <h2 class="mb-3">${master.firstName} ${master.lastName}</h2>
                    <div class="d-flex flex-wrap gap-2 mb-3">
                        <span class="badge bg-primary">${master.specialization}</span>
                        <span class="badge bg-success">Опыт: ${master.experience} лет</span>
                    </div>
                </div>
            </div>

            <hr>

            <div class="row mt-4">
                <div class="col-md-6">
                    <h5 class="fw-bold mb-3"><i class="bi bi-info-circle"></i> Информация</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <i class="bi bi-envelope me-2"></i> Email: ${master.email}
                        </li>
                        <li class="list-group-item">
                            <i class="bi bi-telephone me-2"></i> Контакты: ${master.contacts}
                        </li>
                        <li class="list-group-item">
                            <i class="bi bi-geo-alt me-2"></i> Город: ${master.location}
                        </li>
                    </ul>
                </div>
                <div class="col-md-6">
                    <h5 class="fw-bold mb-3"><i class="bi bi-star"></i> Рейтинг</h5>
                    <div class="alert alert-warning">
                        <div class="d-flex align-items-center">
                            <span class="fs-4 me-2">4.8</span>
                            <div class="text-warning">
                                <i class="bi bi-star-fill"></i>
                                <i class="bi bi-star-fill"></i>
                                <i class="bi bi-star-fill"></i>
                                <i class="bi bi-star-fill"></i>
                                <i class="bi bi-star-half"></i>
                            </div>
                        </div>
                        <small class="text-muted">На основе 24 отзывов</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>