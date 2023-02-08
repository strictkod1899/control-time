# control-time
Программа для контроля времени за ПК

Технические особенности:
- Java 17
- parent-зависимость от [jstrict](https://github.com/strictkod1899/jstrict)

Сборка проекта:
```
# Сборка версии 1.0.0-SNAPSHOT
mvn clean install

# Сборка скриптом через PowerShell:
deploy/deploy_local_skip_tests.ps1
```

Запускать лучше в отдельной папке, т.к. при запуске будут созданы логи.

Запуск приложения:
```
java -jar control-time-<xxx>.jar
```
