# Cloud files service
_____
### Описание
Пррограмма представляет собой серверную часть (backend) облачного хранилища файлов.
Предназначена для работы с клиентским приложением (frontend), к которому подключается без доработки оного.
Полное описание задачи здесь: https://github.com/netology-code/jd-homeworks/blob/master/diploma/cloudservice.md
_____
### Запуск:

Сборка образов из командной строки:

    mvn clean install -DskipTests

Запуск контейнера:

    docker compose up

Установленные настройки:
- Postgresql по адресу http://localhost:5432/
- Фронт по адресу http://localhost:5000/
- Бэкенд по адрес http://localhost:8080/
- В БД двое: user@mail.ru с логином user и
  user@two.user с логином user_two
_____
### Функционал: 
 - вывод списка файлов
 - загрузка файла
 - скачивание файла из хранилища
 - изменение имени файла
 - удаление файпа
_____
### Архитектура:
 - REST
 - 3 layers
