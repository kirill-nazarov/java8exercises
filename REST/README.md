Веб-приложение операции со счетами пользователей. 

3 API (RESTful) 

Transfer- перевод денег с одного счёта на другой

Deposit- положить деньги на счёт

Withdraw- снять деньги со счёта.

Хранилище - In memory H2 DB.

Cобирается с помощью maven в исполняемый Spring boot jar.

Обработка ошибок:
1. Не найден счет
2. Недостаточно средств для снятия со счета
3. Недостаточно средств для перевода со счета на счет
4. Передан один и тот же номер счета для перевода средств.
5. Денежное число передано отрицательным числом или 0.

Для хранения суммы счета используется BigDecimal.

Сборка приложения mvn clean package

Запуск приложения java -jar target\bank-rest-service-0.0.1-SNAPSHOT.jar

Примеры запросов

Get all accounts

curl -X GET localhost:8080/accounts/

Get single account

curl -X GET localhost:8080/accounts/1

Deposit Funds

curl -X PUT localhost:8080/deposit/1 -H 'Content-type:application/json' -d '{"balance": "500"}'

Withdraw Funds

curl -X PUT localhost:8080/withdraw/1 -H 'Content-type:application/json' -d '{"balance": "500"}'

Transfer Funds

curl -X PUT localhost:8080/transfer/ -H 'Content-type:application/json' -d '{"fromID": "1", "toID":"2", "amount": "100"}'
