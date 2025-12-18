RBAC (Role-Based Access Control) – Spring Boot & JWT
Bu doküman, Spring Boot ve JWT kullanılarak geliştirilen RBAC projesinin nasıl çalıştırılacağını, nasıl kullanılacağını ve proje yapısının nasıl işlediğini detaylı şekilde açıklamak amacıyla hazırlanmıştır.
1. Proje Hakkında Genel Bilgi
Bu proje, kullanıcıların sahip oldukları rollere göre sistem kaynaklarına erişimini kontrol eden Rol Tabanlı Erişim Kontrolü (RBAC) mimarisini temel alır. Kimlik doğrulama işlemleri JWT (JSON Web Token) ile stateless olarak yapılmaktadır.
2. Kullanılan Teknolojiler
Java 17
Spring Boot 3
Spring Security
JWT (JSON Web Token)
Spring Data JPA
H2 In-Memory Database
Maven
Lombok
3. Proje Mimarisi
Proje katmanlı mimari yapısına sahiptir. Controller, Service, Repository ve Security katmanları birbirinden ayrılmıştır.
4. Roller ve Yetkilendirme
Sistemde iki temel rol bulunmaktadır:
- ROLE_ADMIN: Yönetici yetkilerine sahiptir.
- ROLE_USER: Standart kullanıcı yetkilerine sahiptir.

Yetkilendirme işlemleri Spring Security ve @PreAuthorize anotasyonu ile yapılır.
5. JWT Kullanımı
Kullanıcı sisteme giriş yaptıktan sonra kendisine bir JWT token üretilir. Bu token, sonraki isteklerde Authorization header içinde gönderilir.

Örnek:
Authorization: Bearer <JWT_TOKEN>
6. Varsayılan Kullanıcılar
admin / Admin123!  → ROLE_ADMIN
user  / User123!   → ROLE_USER
7. API Endpointleri ve Kullanımı
GET  /api/public/ping  → Herkese açık endpoint
POST /auth/login       → Kullanıcı girişi ve JWT üretimi
GET  /api/user/me      → ROLE_USER veya ROLE_ADMIN
GET  /api/admin/panel  → Sadece ROLE_ADMIN
8. Projeyi Çalıştırma Adımları
Java 17 kurulu olduğundan emin olun.
Maven'in sistemde yüklü olduğundan emin olun.
Proje klasörüne girin.
mvn spring-boot:run komutunu çalıştırın.
Uygulama http://localhost:8080 adresinde ayağa kalkacaktır.
9. H2 Veritabanı
Proje, H2 in-memory veritabanı kullanmaktadır.
H2 Console adresi:
http://localhost:8080/h2

JDBC URL:
jdbc:h2:mem:rbacdb
10. Projenin Amacı ve Kazanımlar
Bu proje sayesinde aşağıdaki kazanımlar elde edilir:
- Spring Security ile güvenli backend geliştirme
- JWT tabanlı authentication ve authorization
- Rol tabanlı erişim kontrolü (RBAC) mantığını kavrama
- Katmanlı mimari kullanımı
11. Geliştirici
Atilla Talha Aytekin
12. Notlar
Bu proje eğitim ve demo amaçlıdır. Production ortamı için JWT secret key environment variable olarak tanımlanmalı, refresh token mekanizması eklenmelidir.
