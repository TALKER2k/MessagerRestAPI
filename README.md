# Пример использования API

Видео на 7 минут
https://drive.google.com/drive/folders/1tH2zByOjsR5_5Z-75QHT5owg18FkHCpZ?hl=ru

Но там я просто продемонстрировал все запросы

Дополнительно реализовано удаление переписки, возвращение аккаунта из статуса "No Active".

Пользователь после изменения статуса на "No Active" удаляется через 1 минуту.

Подключил базу данных PostgreSQL.

Хотел тесты написать, но понял, что не успею разобрться. Обычные тесты бы я написал, например, создать вручную пользователя и проверить его в бд, добаляется ли, но подумал, что это как-то непраильно и слишком просто.

## Запросы

### Регистрация пользователя
```http
POST http://localhost:8089/api/auth/signup
{
    "username": "userTest",
    "email": "userTest@mail.com",
    "password": "12345678"
}
```
### Регистрация пользователя
```http
POST http://localhost:8089/api/auth/signup
{
    "username": "userTest",
    "email": "userTest@mail.com",
    "password": "12345678"
}
```

### Вход пользователя
```http
POST http://localhost:8089/api/auth/signin
{
    "username": "userTest",
    "password": "12345678"
}

```

### Выход пользователя
```http
POST http://localhost:8089/api/auth/signout
```

### Получение информации о пользователе (модере, админе)
```http
GET http://localhost:8089/api/test/user
GET http://localhost:8089/api/test/mod
GET http://localhost:8089/api/test/admin
```

### Изменение пароля пользователя
```http
POST http://localhost:8089/api/profile/settings/password
{
    "newPassword": "123123"
}
```

### Изменение основной информации о пользователе
```http
POST http://localhost:8089/api/profile/settings/main
{
    "username": "",
    "firstname": "",
    "lastname": "",
    "email": "",
    "hobby": "FOOTBALL",
    "country": "RUSSIA",
    "city": "MOSCOW",
    "profession": "DOCTOR"
}
```

### Удаление аккаунта пользователя
```http
DELETE http://localhost:8089/api/profile/settings/delete
```

### Активация аккаунта
```http
POST http://localhost:8089/api/auth/activate
{
    "username": "userTest"
}
```

### Отправка сообщения
```http
POST http://localhost:8089/api/message/send
{
    "username": "user",
    "message": "Hello my friend!"
}
```

### Отображение сообщений
```http
POST http://localhost:8089/api/message/show
{
    "username": "user"
}
```

### Удаление сообщения
```http
DELETE http://localhost:8089/api/message/delete
```