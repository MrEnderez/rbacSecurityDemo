# RBAC (Role-Based Access Control) – Spring Boot & JWT

Bu proje, Java Spring Boot kullanılarak geliştirilmiş bir Rol Tabanlı Erişim Kontrolü (RBAC) uygulamasıdır.
Kimlik doğrulama ve yetkilendirme işlemleri JWT (JSON Web Token) ile stateless (oturumsuz) olarak yapılmaktadır.

Bu doküman; projenin nasıl çalıştırılacağını, nasıl kullanılacağını ve iç mimarisinin nasıl işlediğini
adım adım açıklamak amacıyla hazırlanmıştır.

------------------------------------------------------------

KULLANILAN TEKNOLOJİLER

- Java 17
- Spring Boot 3
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- H2 In-Memory Database
- Maven
- Lombok

------------------------------------------------------------

PROJE MİMARİSİ

src/main/java/com/example/rbac

api
- AuthController.java        -> Login ve register işlemleri
- DemoApiController.java    -> Rol bazlı test endpointleri

api/dto
- AuthDtos.java              -> Request / Response nesneleri

bootstrap
- DataSeeder.java            -> Varsayılan kullanıcı ve roller

domain
- AppUser.java               -> Kullanıcı entity
- Role.java                  -> Rol entity

repo
- UserRepository.java
- RoleRepository.java

security
- SecurityConfig.java        -> Spring Security ayarları
- JwtService.java            -> JWT üretme ve doğrulama
- JwtAuthFilter.java         -> JWT kontrol filtresi
- DbUserDetailsService.java  -> UserDetails implementasyonu

service
- UserService.java           -> İş kuralları

RbacApplication.java         -> Uygulama başlangıç noktası

------------------------------------------------------------

RBAC (ROLE-BASED ACCESS CONTROL) MANTIĞI

- Kullanıcılar bir veya birden fazla role sahip olabilir.
- Roller veritabanında ayrı bir tablo olarak tutulur.
- Endpoint erişimleri Spring Security üzerinden kontrol edilir.
- Yetkilendirme işlemleri @PreAuthorize anotasyonu ile sağlanır.

KULLANILAN ROLLER
- ROLE_ADMIN
- ROLE_USER

------------------------------------------------------------

JWT (JSON WEB TOKEN) YAPISI

Kullanıcı giriş yaptıktan sonra sistem tarafından bir JWT token üretilir.

Token içinde:
- Kullanıcı adı
- Kullanıcının rolleri
- Oluşturulma zamanı
- Geçerlilik süresi

Token her istekte aşağıdaki header ile gönderilir:

Authorization: Bearer JWT_TOKEN

------------------------------------------------------------

VARSAYILAN KULLANICILAR

Uygulama ilk çalıştığında otomatik olarak oluşturulur:

admin / Admin123!  -> ROLE_ADMIN
user  / User123!   -> ROLE_USER

------------------------------------------------------------

API ENDPOINTLERİ

PUBLIC ENDPOINT
GET /api/public/ping
- Herkese açık
- Token gerekmez

LOGIN
POST /auth/login
- Kullanıcı adı ve şifre ile JWT üretir

USER ENDPOINT
GET /api/user/me
- ROLE_USER veya ROLE_ADMIN erişebilir

ADMIN ENDPOINT
GET /api/admin/panel
- Sadece ROLE_ADMIN erişebilir

------------------------------------------------------------

PROJEYİ ÇALIŞTIRMA

GEREKSİNİMLER
- Java 17
- Maven

ÇALIŞTIRMA ADIMLARI
1. Proje klasörüne gir
2. mvn spring-boot:run komutunu çalıştır
3. Uygulama ayağa kalkar

UYGULAMA ADRESİ
http://localhost:8080

------------------------------------------------------------

H2 VERİTABANI

H2 Console:
http://localhost:8080/h2

JDBC URL:
jdbc:h2:mem:rbacdb

------------------------------------------------------------

PROJENİN AMACI

Bu proje sayesinde:
- Spring Security kullanımı
- JWT tabanlı authentication ve authorization
- Rol tabanlı erişim kontrolü (RBAC)
- Katmanlı mimari yapısı

uygulamalı olarak öğrenilmiştir.

------------------------------------------------------------

GELİŞTİRİCİ

Atilla Talha Aytekin

------------------------------------------------------------

NOTLAR

- Proje eğitim ve demo amaçlıdır.
- Production ortamı için:
  - JWT secret key environment variable yapılmalıdır
  - Refresh token mekanizması eklenmelidir
  - Role -> Permission yapısı eklenebilir
