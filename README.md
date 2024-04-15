# Liberty Bank - Java - AccountService
# Сервис для работы со счетами клиентов

## Стек:
Java 17, Spring Boot 3.2.4, PostgreSQL, Liquibase, MapStruct, Swagger, Gradle

## Установка и настройка:

Kлонируем проект

```shell
$ git clone https://git.astondevs.ru/aston/liberty-bank/liberty-bank-java-accountservice.git
$ cd wallet
$ ./gradlew build
```
Далее необходимо запустить docker compose

```shell
$ docker compose up
```

Поднимится postgres, и приложение.

## Подключение

### PostgreSQL

- **Host**: `postgres`
- **Port**: `5432`
- **User**: `user`
- **Password**: `pass`
- **Database**: `wallet_service_db`

### Wallet Service

- **Swagger UI**: http://localhost:8080/swagger-ui

