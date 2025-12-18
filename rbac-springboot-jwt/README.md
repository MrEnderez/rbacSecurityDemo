# RBAC + JWT (Spring Boot 3, Java 17)

Bu proje: **Rol tabanlı erişim (RBAC)** + **JWT** ile kimlik doğrulama/authorization örneğidir.

## Çalıştırma
```bash
mvn spring-boot:run
```

## Demo Kullanıcılar (seed)
- admin / Admin123!  -> ROLE_ADMIN
- user  / User123!   -> ROLE_USER

## Endpointler
- GET `/api/public/ping` -> Public
- GET `/api/user/me` -> ROLE_USER veya ROLE_ADMIN
- GET `/api/admin/panel` -> ROLE_ADMIN
