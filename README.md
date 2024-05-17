# Сервис для банковских операций

## Обзор

Сервис для банковских операций - это RESTful API, который обеспечивает базовую банковскую функциональность, включая управление учетными записями пользователей, переводы денег и обновления баланса с начислением процентов. 
Сервис гарантирует безопасные  операции, а также предоставляет подробное логирование, аутентификацию и документацию Swagger.

## Функциональные требования

1. **Управление пользователями**
   - У каждого пользователя есть один банковский аккаунт.
   - У пользователя должен быть номер телефона и/или адрес электронной почты. Как минимум одно из этих полей должно быть заполнено.
   - Баланс пользователей не может быть отрицательным.
   - Личные данные пользователя, такие как дата рождения и ФИО, не могут быть изменены после создания(Сделано отдельное API для заполнения этих данных).
   - Предоставлен публичный API для создания новых пользователей с уникальными логином, паролем, начальным балансом, телефоном и email.
   - Пользователи могут обновлять свой номер телефона и/или email, при условии, что новые значения не используются другими пользователями.
   - Пользователи могут удалять свой номер телефона и/или email, но должны оставить как минимум одно из этих полей.

2. **Управление балансом**
   - Баланс должен увеличиваться на 5% каждую минуту, но не превышать 207% от начального депозита.

3. **Переводы денег**
   - Пользователи могут переводить деньги на счета других пользователей.
   - Баланс отправителя не должен уходить в минус.
   

4. **API поиска**
   - Позволяет искать пользователей с фильтрами:
     - Дата рождения (записи с датой рождения больше указанной).
     - Номер телефона (точное совпадение).
     - ФИО (поиск по шаблону).
     - Email (точное совпадение).
   - Поддержка пагинации и сортировки в результатах поиска.
   - Доступ к API поиска не требует наутентификации, в данном случае это сделано для простоты тестировки.

## Нефункциональные требования

- Реализация документации Swagger.
- Логирование.
- Аутентификация с использованием JWT.
- Реализация юнит-тестов для функционала перевода денег.

## Технологический стек

- **Java 17**: Основной язык программирования.
- **Spring Boot 3**: Фреймворк для создания приложения.
- **PostgreSQL**: База данных для хранения данных пользователей и аккаунтов.
- **Maven**: Инструмент для сборки и управления зависимостями.
- **REST API**: Архитектурный стиль для конечных точек API.

## Начало работы

### Необходимые условия

- Java 17
- Maven
- PostgreSQL

# Примеры запросов к API

## Создание пользователя

- **Endpoint**: `POST /v1/ancillary/addUser`
- **Пример тела запроса**:
  ```json
  {
    "login": "adm3",
    "password": "11235584",
    "balance": "100",
    "phoneNumber": "123",
    "email": "1h3"
  }
## Обновление данных пользователя

- **Endpoint**: `POST /v1/user/transfer`
- **Пример тела запроса**:
  ```json
  {
    "firstName": "sth",
    "secondName": "sth",
    "surName": "sth"
  }
## Перевод денег

- **Endpoint**: `POST /v1/user/transfer`
- **Пример тела запроса**:
  ```json
  {
    "loginOfUserToTransfer": "adm1",
    "quantity": "20"
  }
  
## Поиск пользователей

- **Endpoint**: `GET /v1/ancillary/search`
- **Пример тела запроса**:
  ```json
  {
    "fullName": ""
  }
