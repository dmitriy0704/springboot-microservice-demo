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

# Регистрция пользователя из фронта.

Да, ты понял почти идеально 👍
Давай разложу **по шагам, без магии**, как это реально работает с фронтендом + Keycloak.

---

## Общая идея (очень коротко)

👉 **Фронтенд НИКОГДА не создает пользователя сам**
👉 **Регистрация и логин — всегда в Keycloak**
👉 **Фронтенд получает токен через OAuth2 / OpenID Connect flow**

---

## 1️⃣ Что делает фронтенд, когда нужен логин / регистрация

Фронтенд **не рисует форму регистрации** сам.

Он делает **redirect в Keycloak**:

```
https://keycloak:8080/realms/microservices/protocol/openid-connect/auth
```

с параметрами:

* `client_id`
* `redirect_uri`
* `response_type=code`
* `scope=openid profile email`
* `code_challenge` (PKCE)
* `state`

👉 Если пользователь **не зарегистрирован**, Keycloak сам покажет:

* кнопку **Register**
* форму регистрации

---

## 2️⃣ Пользователь регистрируется В Keycloak

На странице Keycloak:

* email
* username
* password
* (что ты разрешишь в realm settings)

📌 **Пользователь создаётся ТОЛЬКО в Keycloak**

После успешной регистрации / логина:

👉 Keycloak делает redirect **обратно на фронтенд**:

```
https://frontend.app/callback?code=AUTH_CODE
```

---

## 3️⃣ Как фронтенд получает токен

### 🔥 Ключевой момент: `code` ≠ `token`

Фронтенд получает **authorization code**, а не токен.

Дальше фронтенд делает **backend-запрос** (или JS-запрос, если SPA):

```
POST /protocol/openid-connect/token
```

с телом:

```http
grant_type=authorization_code
client_id=frontend-client
code=AUTH_CODE
redirect_uri=https://frontend.app/callback
code_verifier=...
```

👉 В ответ приходит:

```json
{
  "access_token": "...",
  "refresh_token": "...",
  "id_token": "...",
  "expires_in": 300
}
```

🎉 **Фронтенд получил токен**

---

## 4️⃣ Что делает фронтенд дальше

Теперь фронтенд:

* хранит `access_token` (в памяти / secure storage)
* при каждом запросе:

```
Authorization: Bearer <access_token>
```

---

## 5️⃣ Что видят твои микросервисы

### order-service / user-service:

* проверяют JWT
* читают `sub`
* читают `email`, `preferred_username`

```java
@AuthenticationPrincipal Jwt jwt

jwt.getSubject();           // keycloak user id
jwt.getClaim("email");      // email
jwt.getClaim("preferred_username");
```

📌 **Им вообще плевать, как пользователь регистрировался**

---

## 6️⃣ Где хранить кастомные поля пользователя

Очень важный момент 👇
**Keycloak ≠ бизнес-пользователь**

### Правильная модель:

#### Keycloak

* identity
* login / password
* email
* roles

#### user-service (ТВОЯ БД)

* keycloakId (sub)
* name
* phone
* address
* preferences
* anything else

---

## 7️⃣ Как user-service узнает о новом пользователе

Есть 3 рабочих варианта (ты можешь выбрать любой):

---

### ✅ Вариант 1 (самый популярный)

**Lazy creation**

1. Пользователь логинится
2. Первый запрос в user-service
3. user-service:

   * берет `sub`
   * ищет пользователя в БД
   * ❌ не нашел → создает автоматически

```java
User user = userRepository.findByKeycloakId(sub)
    .orElseGet(() -> createFromJwt(jwt));
```

🔥 **Просто и эффективно**

---

### ✅ Вариант 2

Фронтенд после логина дергает:

```
POST /users/me/init
```

user-service:

* создает пользователя из JWT

---

### ❌ Вариант 3 (сложный, enterprise)

Keycloak event listener → Kafka → user-service
(на твоем этапе **не нужно**)

---

## 8️⃣ Ответ на твой вопрос напрямую

> фронтенд перенаправляет на страницу регистрации Keycloak?

✅ **Да**

> пользователь заполняет форму Keycloak?

✅ **Да**

> дальше редирект обратно?

✅ **Да**

> как фронтенд получает токен?

🔥 **Через Authorization Code Flow (PKCE)**
👉 сначала `code`
👉 потом `token`

---

## 9️⃣ Как это выглядит в проде

```
[ Browser ]
    |
    | redirect
    v
[ Keycloak ]
    |
    | code
    v
[ Frontend ]
    |
    | Bearer token
    v
[ API Gateway / Order / User ]
```