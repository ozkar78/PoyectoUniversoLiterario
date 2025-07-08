# Guía de Evaluación - Proyecto Literatura

Este documento está diseñado para ayudar a los profesores en la evaluación del proyecto.

## Aspectos a Evaluar

### 1. Arquitectura y Diseño (30%)

- **Separación de capas**: El proyecto implementa correctamente el patrón de arquitectura en capas (modelo, repositorio, servicio, UI)
- **Modelado de datos**: Las entidades están bien diseñadas con relaciones apropiadas
- **Uso de interfaces**: Se utilizan interfaces para los repositorios siguiendo buenas prácticas
- **Patrones de diseño**: Implementación de patrones como Repository, Dependency Injection

### 2. Implementación Técnica (30%)

- **Spring Boot**: Uso correcto de anotaciones y configuración
- **JPA/Hibernate**: Mapeo adecuado de entidades y relaciones
- **Consultas**: Implementación de consultas personalizadas con JPQL
- **Manejo de errores**: Implementación de manejo de excepciones
- **Inicialización de datos**: Carga automática de datos de ejemplo

### 3. Funcionalidad (20%)

- **Búsqueda de libros**: Funcionalidad para buscar libros por título
- **Filtrado por autor**: Capacidad para mostrar libros de un autor específico
- **Filtrado por idioma**: Implementación del filtrado por idioma usando enumeraciones
- **Consulta por año**: Funcionalidad para encontrar autores vivos en un año específico
- **Ranking**: Implementación del top 5 de libros

### 4. Interfaz de Usuario (10%)

- **Usabilidad**: Menú claro y fácil de usar
- **Presentación**: Uso de colores y formato para mejorar la experiencia
- **Feedback**: Mensajes informativos y manejo de errores en la UI

### 5. Documentación y Código (10%)

- **README**: Documentación clara y completa
- **Diagrama**: Diagrama de clases que muestra la estructura del proyecto
- **Código limpio**: Nomenclatura adecuada, comentarios útiles
- **Convenciones**: Seguimiento de convenciones de Java y Spring

## Rúbrica de Calificación

| Criterio | Insuficiente (0-5) | Satisfactorio (6-7) | Bueno (8-9) | Excelente (10) |
|----------|-------------------|---------------------|-------------|---------------|
| Arquitectura | Estructura confusa, sin separación clara | Separación básica de capas | Buena arquitectura con patrones adecuados | Arquitectura excelente, uso avanzado de patrones |
| Implementación | Errores graves, funcionalidad limitada | Implementación básica funcional | Buena implementación con características avanzadas | Implementación excelente, optimizada y robusta |
| Funcionalidad | Menos del 60% de funciones implementadas | 70-80% de funciones implementadas | 90% de funciones implementadas correctamente | 100% de funciones implementadas con extras |
| UI | Difícil de usar, sin formato | Usable pero básica | Buena UI con formato adecuado | UI excelente, intuitiva y con detalles extras |
| Documentación | Escasa o inexistente | Documentación básica | Buena documentación con diagramas | Documentación excelente, completa y detallada |

## Comentarios Adicionales

- **Puntos fuertes**: Inicialización automática de datos, manejo de errores, interfaz colorida
- **Áreas de mejora**: [A completar por el profesor]
- **Sugerencias para versiones futuras**: Implementar una interfaz web, añadir tests unitarios, integrar más APIs externas