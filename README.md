# Sistema de Gestión de Restaurante

Aplicación web para la gestión integral de pedidos, catálogo de alimentos y personal para restaurante. Como estudiante del Instituto Tecnologico Universitario(ITU) esta app fue desarrollada como proyecto práctico para la materia programacion orientada a objetos de la carrera de desarrollo de software dictada por el docente Lic. Martín Vargas.

## 1. Tecnologías

**Backend**
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL 8

**Frontend**
- React 18 + Vite
- React Router v6
- Lucide React (Iconos)

---

## Requisitos previos
- JDK 17 o superior
- Maven 3.8+
- MySQL 8 corriendo en `localhost:3306`
- Node.js 18+ y npm

---

## 2. Configuración

### Base de datos

La base de datos se crea automáticamente al iniciar el backend (gracias a la propiedad createDatabaseIfNotExist=true). Solo asegurate de que MySQL esté corriendo y de que las credenciales en src/main/resources/application.properties sean correctas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_spring?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Datos iniciales

Al iniciar por primera vez, el sistema (a través de DataInitializer) carga automáticamente los siguientes datos de prueba para poder probar la aplicación:
- 3 artículos en el menú.
- 4 perfiles de Chefs.
- 5 perfiles de Meseros.

--- 

## 4. Cómo correr el proyecto


### Backend
En la raíz del proyecto, ejecuta:
```bash
mvn spring-boot:run
```
El servidor queda disponible en http://localhost:8080`.


### Frontend
Abre una nueva terminal con la direccion `usuario@usuario:~/Descargas/ITU - Cuarto semestre/POO/Restaurante_Spring_Boot$` y ejecuta:
```bash
cd frontend
npm install
npm run dev
```
La app queda disponible en `http://localhost:5173`.

---

![Diagrama de clases]


