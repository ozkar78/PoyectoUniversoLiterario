# 📚 Proyecto Literatura

Aplicación Spring Boot que consume la API Gutendex para buscar libros y gestionar una base de datos local de literatura. Este proyecto fue desarrollado como parte del curso de Java y Spring Boot de Alura.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue)

## 🌟 Características

- Búsqueda de libros por título usando la API Gutendex
- Almacenamiento local en PostgreSQL
- Gestión de autores y libros
- Filtrado por idiomas
- Consulta de autores vivos por año
- Interfaz de consola interactiva con colores
- Inicialización automática de datos de ejemplo

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas:

```
ProyectoLiteratura/
├── model/           # Entidades JPA (Autor, Libro, Idioma)
├── repository/      # Interfaces de acceso a datos
├── service/         # Lógica de negocio
├── ui/              # Interfaz de usuario en consola
├── util/            # Utilidades (colores, manejo de errores)
└── config/          # Configuraciones (inicialización de datos)
```

## 📋 Diagrama de Clases

El proyecto incluye un diagrama PlantUML (`diagrama.puml`) que muestra la estructura completa de clases y sus relaciones.

## 🛠️ Configuración

### Requisitos Previos
- Java 21
- Maven
- PostgreSQL

### Base de Datos
1. Instalar PostgreSQL
2. Crear base de datos: `alura_literatura`
3. Las credenciales se configuran en `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/alura_literatura
   spring.datasource.username=postgres
   spring.datasource.password=1786
   ```

### Ejecución
```bash
# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run
```

Alternativamente, puede usar el script `setup-project.bat` para configurar automáticamente el entorno.

## 🔍 Funcionalidades

1. **Buscar libro por título**: 
   - Busca en la API Gutendex y guarda en BD local
   - Muestra detalles del libro encontrado

2. **Buscar libros por autor**: 
   - Muestra todos los libros de un autor específico
   - Incluye información del autor

3. **Listar libros registrados**: 
   - Muestra todos los libros guardados en la base de datos
   - Incluye título, autor e idiomas disponibles

4. **Listar autores registrados**: 
   - Muestra todos los autores guardados
   - Incluye fechas de nacimiento y muerte

5. **Autores vivos por año**: 
   - Filtra autores que estaban vivos en un año específico
   - Utiliza una consulta JPQL personalizada

6. **Libros por idioma**: 
   - Filtra libros por idioma (Español, Inglés, Francés, Portugués)
   - Utiliza enumeraciones para representar los idiomas

7. **Top 5 libros con mejor ranking**: 
   - Muestra los 5 libros mejor valorados según número de descargas
   - Ordenados de mayor a menor popularidad

## 🧪 Datos de Ejemplo

La aplicación incluye un inicializador de datos (`DataInitializer.java`) que carga automáticamente información de autores y libros famosos para facilitar las pruebas:

- **Autores**: Jorge Luis Borges, Julio Cortázar, Gabriel García Márquez, Pablo Neruda
- **Libros**: Ficciones, El Aleph, Rayuela, Cien años de soledad, entre otros

## 🔧 Tecnologías

- **Java 21**: Lenguaje de programación principal
- **Spring Boot 3.5.3**: Framework para desarrollo de aplicaciones
- **Spring Data JPA**: Para acceso a datos y persistencia
- **PostgreSQL**: Sistema de gestión de base de datos
- **Jackson**: Procesamiento de JSON para la API
- **Maven**: Gestión de dependencias y construcción

## 👨‍💻 Autor

Desarrollado por [Tu Nombre] para el curso de Java y Spring Boot de Alura.

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo LICENSE para más detalles.