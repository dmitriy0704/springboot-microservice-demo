# Заметки по проекту

> Для тестирования loadbalancer:
> Должны увидеть выводты ны разных портах:

```java
@RestController
@RequestMapping("/api/users/user-payments")
public class UserPaymentsController {

    private int port;

    @EventListener
    public String onWebServerReady(WebServerInitializedEvent event) {
         port = event.getWebServer().getPort();
        System.out.println("Реальный порт: " + port);
        return "hello from order-service on port " + port;
    }

    @GetMapping("/getlist")
    public String getUserPayments(){
        return "hello from order-service on port " + port;
    }
}
```

```shell
for i in {1..20}; do
  curl -s http://localhost:8085/api/v1/users/user-orders/hellouser
  echo
done
```

Или утилитой hey

```shell
 hey -n 1000 -c 50 http://localhost:8085/api/v1/users/user-payments/getlist
 -n 1000 — всего запросов

-c 50 — параллельность
```

И

```java

@Slf4j
@RestController
@RequestMapping("/api/users/user-payments")
public class UserPaymentsController {

    private int port;

    @EventListener
    public String onWebServerReady(WebServerInitializedEvent event) {
         port = event.getWebServer().getPort();
        System.out.println("Реальный порт: " + port);
        return "hello from order-service on port " + port;
    }
q   
    @GetMapping("/getlist")
    public String getUserPayments(){
        log.info("Handled by instance on port {}", port);
        return "hello from order-service on port " + port;
    }
}

```

# Для Docker:

## Запуск

```shell
docker compose build

# Запустить
docker compose up -d

# Посмотреть логи
docker compose logs -f
```


# Настройка Keycloak

Настройка Keycloak:

1. Созадть realm (Realm Setting: User registration +, Verify email +)
2. Создать Client(
    Standard flow +,
    Direct access grants +)
3. Создать User(Email verified +, заполнить General)