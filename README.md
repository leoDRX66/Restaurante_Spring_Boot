# Sistema de Gestión de Restaurante

Aplicación web para la gestión integral de pedidos, catálogo de alimentos y personal para restaurante. Como estudiante del Instituto Tecnológico Universitario (ITU), esta app fue desarrollada como proyecto práctico para la materia Programación Orientada a Objetos de la carrera de Desarrollo de Software, dictada por el docente Lic. Martín Vargas.

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

La base de datos se crea automáticamente al iniciar el backend (gracias a la propiedad createDatabaseIfNotExist=true). Solo asegúrate de que MySQL esté corriendo y de que las credenciales en src/main/resources/application.properties sean correctas:

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
El servidor queda disponible en `http://localhost:8080`.


### Frontend
Abre una nueva terminal con la dirección `usuario@usuario:~/Descargas/ITU - Cuarto semestre/POO/Restaurante_Spring_Boot$` y ejecuta:

```bash
cd frontend
npm install
npm run dev
```
La app queda disponible en `http://localhost:5173`.

---

## 5. Esquema de clases UML

A continuación se presenta el diagrama UML principal de la aplicación, mostrando cómo interactúan los actores, alimentos y pedidos dentro del restaurante.

![Diagrama de clases](Diagrama_restaurante.png)

---

## 6. Endpoints REST

La API del proyecto expone los siguientes controladores para la manipulación de recursos:

| Recurso | Método | Endpoint | Descripción |
| :--- | :---: | :--- | :--- |
| **Pedidos** | `GET` | `/api/pedidos` | Lista todos los pedidos |
| | `GET` | `/api/pedidos/{id}` | Obtiene un pedido por su ID |
| | `GET` | `/api/pedidos/mesa/{numero}` | Obtiene pedidos de una mesa en particular |
| | `POST` | `/api/pedidos` | Crea un nuevo pedido |
| | `DELETE` | `/api/pedidos/{id}` | Elimina un pedido |
| **Alimentos** | `GET` | `/api/alimentos` | Lista todos los alimentos |
| | `GET` | `/api/alimentos/{id}` | Detalle de un alimento |
| | `POST` | `/api/alimentos` | Registra un alimento |
| | `DELETE` | `/api/alimentos/{id}` | Elimina un alimento del menú |
| **Chefs** | `GET` | `/api/chefs` | Lista el plantel de chefs |
| | `GET` | `/api/chefs/{id}` | Detalle de un chef |
| | `POST` | `/api/chefs` | Contrata/Registra un nuevo chef |
| | `DELETE` | `/api/chefs/{id}` | Elimina un registro de chef |
| **Meseros** | `GET` | `/api/meseros` | Lista todos los meseros |
| | `GET` | `/api/meseros/{id}` | Detalle de un mesero |
| | `POST` | `/api/meseros` | Registra a un nuevo mesero |
| | `DELETE` | `/api/meseros/{id}` | Elimina un mesero |

---

## 7. Estructura de carpetas y archivos

```
├── frontend/                     # Aplicación React + Vite
│   ├── src/
│   │   ├── pages/                # Vistas principales (Dashboard, Pedidos, etc.)
│   │   ├── App.jsx               # Rutas e index de la app
│   │   └── main.jsx              # Punto de entrada de React
│   ├── package.json              # Dependencias de npm
│   └── vite.config.js            # Configuración Vite
├── pom.xml                       # Dependencias Maven (Backend)
└── src/
    └── main/
        ├── java/
        │   └── com/mycompany/restaurante/
        │       ├── config/       # Configuraciones generales 
        │       ├── controller/   # Controladores REST
        │       ├── dto/          # Data Transfer Objects (Request/Response)
        │       ├── model/        # Entidades JPA (Alimento, Mesero, Chef, Pedido, etc.)
        │       ├── repository/   # Interfaces de acceso a datos
        │       ├── service/      # Lógica de negocio (Interfaces e Implementaciones)
        │       ├── DataInitializer.java             # Inyección de datos
        │       └── RestauranteApplication.java      # Main donde se inicia el backend
        └── resources/
            └── application.properties # Propiedades y conexión a BD
```

---

## 8. Agradecimientos y colaboraciones

Este proyecto fue posible gracias a la colaboración, el esfuerzo en equipo y las correcciones de diseño. Queremos dar un agradecimiento especial a:

- Juan Pablo Simón y Francisco Villanueva, mi equipo durante todo el cursado, por colaborar en el modelado, la lógica de la base de datos y los consejos.
  
- Brian Tomadin, por su ayuda con la estructura del proyecto y los consejos en GitHub.

- Martín Vargas, por la labor docente de darnos el material de estudio y por las correcciones de proyectos anteriores en el patrón MVC y el modelado UML.
