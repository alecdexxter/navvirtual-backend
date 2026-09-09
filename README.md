Navegación Virtual — Backend

API REST desarrollada con Spring Boot 4 + Java 21, que da soporte a una plataforma de navegación virtual interactiva para convenciones y ferias de stands (recorrido 360°, tienda, sistema de roles, pagos con Mercado Pago, trivia, y más).

Este repositorio es el backend. El frontend (React) vive en un repositorio aparte: navvirtual-frontend.
Tecnologías
Tecnología	Uso
Java 21 + Spring Boot 4	Framework principal del servidor
Spring Security + JWT (jjwt)	Autenticación y control de permisos por rol
Spring Data JPA + Hibernate	Persistencia y mapeo objeto-relacional
PostgreSQL	Base de datos
Docker	Contenedor de la base de datos
Maven	Gestión de dependencias y build
SDK de Mercado Pago (sdk-java)	Procesamiento de pagos
Spring Mail	Notificaciones por email a vendedores
Roles del sistema
Cliente: navega el evento, compra entradas/productos, juega la trivia, vota stands.
Dueño de Stand / Dueño de Buffet: administra su espacio, productos, empleados y video/fotos.
Empleado de Stand / Empleado de Buffet: carga y edita productos de su espacio asignado.
Superadmin: crea eventos, stands, buffets, gestiona el recorrido 360° y los roles de usuario.
Requisitos previos
JDK 21 (Adoptium/Temurin)
Docker Desktop (docker.com)
IntelliJ IDEA (Community Edition alcanza) o el IDE de tu preferencia
Git
Cómo levantar el proyecto
Cloná el repositorio
bash
   git clone https://github.com/TU_USUARIO/navvirtual-backend.git
   cd navvirtual-backend
Configurá las variables de entorno El archivo application.properties real no está en el repo (contiene credenciales privadas). Copiá la plantilla y completala:
bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties

Editá el archivo nuevo y completá:

jwt.secret: cualquier cadena larga y aleatoria
mercadopago.access-token / mercadopago.public-key: credenciales de prueba de tu cuenta de Mercado Pago Developers
spring.mail.username / spring.mail.password: una cuenta de Gmail con contraseña de aplicación
Levantá la base de datos
bash
   docker compose up -d
Corré la aplicación Abrí el proyecto en IntelliJ y ejecutá NavvirtualBackendApplication, o desde la terminal:
bash
   ./mvnw spring-boot:run

Nota (Windows): si ves un error de TimeZone, agregá -Duser.timezone=UTC como VM option de la configuración de ejecución.

El backend queda disponible en http://localhost:8082.

Probá que funciona
bash
   curl http://localhost:8082/api/eventos/publicos/vigentes

Debería responder [] (lista vacía) si es la primera vez que lo corrés — es normal, los datos se cargan desde el panel de administración una vez que tengas un usuario Superadmin (el primer usuario registrado necesita que le asignes el rol manualmente por base de datos: UPDATE usuario_roles ... — ver la sección de Troubleshooting).

Estructura del proyecto
src/main/java/com/navvirtual/backend/
├── entity/       # Modelos JPA (Usuario, Evento, Stand, Producto, Compra, etc.)
├── repository/   # Interfaces JpaRepository
├── service/      # Lógica de negocio y validación de permisos
├── controller/   # Endpoints REST
├── security/     # JWT, filtros, configuración de Spring Security
├── dto/          # Objetos de transferencia entre capas
└── config/       # CORS, seeders de datos, configuración general
Licencia / Contexto académico

Proyecto desarrollado para la materia Programación 3, presentado en ExpoJuy 2026.
