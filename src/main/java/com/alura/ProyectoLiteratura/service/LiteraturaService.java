package com.alura.ProyectoLiteratura.service;

import com.alura.ProyectoLiteratura.dto.DatosLibro;
import com.alura.ProyectoLiteratura.dto.RespuestaAPI;
import com.alura.ProyectoLiteratura.model.Autor;
import com.alura.ProyectoLiteratura.model.Idioma;
import com.alura.ProyectoLiteratura.model.Libro;
import com.alura.ProyectoLiteratura.repository.AutorRepository;
import com.alura.ProyectoLiteratura.repository.LibroRepository;
import com.alura.ProyectoLiteratura.util.ConsoleColors;
import com.alura.ProyectoLiteratura.util.TitulosPopulares;
import com.alura.ProyectoLiteratura.util.AutorPopulares;
import com.alura.ProyectoLiteratura.util.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class LiteraturaService {
    
    @Autowired
    private ConsumoAPI consumoAPI;
    
    @Autowired
    private ConvierteDatos conversor;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    private final String URL_BASE = "https://gutendex.com/books/";
    
    public void buscarLibroPorTitulo(String titulo) {
        try {
            // Validar que el input no esté vacío
            if (titulo == null || titulo.trim().isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD + "\nDebe ingresar un título válido" + ConsoleColors.RESET);
                return;
            }
            
            // Detectar si el usuario ingresó un nombre de autor en lugar de un título
            String input = titulo.trim();
            if (input.contains(",") && input.split(",").length == 2) {
                String[] partes = input.split(",");
                String apellido = partes[0].trim();
                String nombre = partes[1].trim();
                
                // Si parece formato "Apellido, Nombre", es probablemente un autor
                if (apellido.length() > 2 && nombre.length() > 2 && 
                    Character.isUpperCase(apellido.charAt(0)) && Character.isUpperCase(nombre.charAt(0))) {
                    System.out.println(ConsoleColors.RED_BOLD + "\n❌ ERROR: Ingresaste un nombre de autor, no un título" + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW_BOLD + "\n💡 AYUDA - Ejemplos de títulos válidos:" + ConsoleColors.RESET);
                    System.out.println("   • Frankenstein");
                    System.out.println("   • Pride and Prejudice");
                    System.out.println("   • The Great Gatsby");
                    System.out.println("   • Don Quixote");
                    System.out.println("   • Romeo and Juliet");
                    System.out.println(ConsoleColors.CYAN_BOLD + "\n📝 Para buscar por autor usa la opción [2] del menú" + ConsoleColors.RESET);
                    return;
                }
            }
            
            // Corregir posibles errores ortográficos en títulos populares
            String tituloCorregido = TitulosPopulares.corregirTitulo(input);
            
            // Si el título fue corregido, informar al usuario
            if (!tituloCorregido.equalsIgnoreCase(input)) {
                System.out.println(ConsoleColors.YELLOW_BOLD + "\nBuscando '" + tituloCorregido + "' en lugar de '" + input + "'" + ConsoleColors.RESET);
            }
            
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nBuscando libro: " + tituloCorregido + "..." + ConsoleColors.RESET);
            
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloCorregido.replace(" ", "%20"));
            var respuesta = conversor.obtenerDatos(json, RespuestaAPI.class);
            
            if (respuesta.resultados() == null || respuesta.resultados().isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD + "\nNo se encontraron libros para: " + tituloCorregido + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW_BOLD + "Sugerencias:" + ConsoleColors.RESET);
                System.out.println("- Verifica la ortografía del título");
                System.out.println("- Intenta con un título más corto o palabras clave");
                System.out.println("- Prueba con títulos en inglés (ej: 'Frankenstein', 'Dracula', 'Pride and Prejudice')");
                return;
            }
            
            // Buscar el libro que mejor coincida con el título buscado
            DatosLibro libroBuscado = null;
            for (DatosLibro libro : respuesta.resultados()) {
                if (libro.titulo().toLowerCase().contains(tituloCorregido.toLowerCase()) ||
                    tituloCorregido.toLowerCase().contains(libro.titulo().toLowerCase())) {
                    libroBuscado = libro;
                    break;
                }
            }
            
            // Si no se encontró coincidencia exacta, tomar el primer resultado
            if (libroBuscado == null) {
                libroBuscado = respuesta.resultados().get(0);
            }
            // Validar que el libro tenga datos mínimos requeridos
            if (libroBuscado.titulo() == null || libroBuscado.titulo().trim().isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD + "\nEl libro encontrado no tiene un título válido" + ConsoleColors.RESET);
                return;
            }
            
            // Mostrar información del libro encontrado
            System.out.println(ConsoleColors.GREEN_BOLD + "\nLibro encontrado:" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.PURPLE_BOLD + "Título: " + ConsoleColors.RESET + libroBuscado.titulo());
            
            String autorNombre = "Desconocido";
            if (!libroBuscado.autores().isEmpty()) {
                autorNombre = libroBuscado.autores().get(0).nombre();
            }
            System.out.println(ConsoleColors.PURPLE_BOLD + "Autor: " + ConsoleColors.RESET + autorNombre);
            System.out.println(ConsoleColors.PURPLE_BOLD + "Descargas: " + ConsoleColors.RESET + libroBuscado.numeroDescargas());
            
            // Verificar si el libro ya existe
            Optional<Libro> libroExistente = libroRepository.findByTitulo(libroBuscado.titulo());
            if (libroExistente.isPresent()) {
                System.out.println(ConsoleColors.YELLOW_BOLD + "\nEste libro ya está registrado en la base de datos" + ConsoleColors.RESET);
                return;
            }
            
            // Crear o buscar autor
            Autor autor = null;
            if (!libroBuscado.autores().isEmpty()) {
                var datosAutor = libroBuscado.autores().get(0);
                
                // Validar que el autor tenga nombre
                if (datosAutor.nombre() != null && !datosAutor.nombre().trim().isEmpty()) {
                    Optional<Autor> autorExistente = autorRepository.findByNombre(datosAutor.nombre());
                    
                    if (autorExistente.isPresent()) {
                        autor = autorExistente.get();
                        System.out.println(ConsoleColors.YELLOW_BOLD + "\nAutor ya existe en la base de datos" + ConsoleColors.RESET);
                    } else {
                        autor = new Autor(datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaMuerte());
                        autor = autorRepository.save(autor);
                        System.out.println(ConsoleColors.GREEN_BOLD + "\nNuevo autor guardado: " + autor.getNombre() + ConsoleColors.RESET);
                    }
                }
            }
            
            // Convertir idiomas
            List<Idioma> idiomas = new ArrayList<>();
            if (libroBuscado.idiomas() != null && !libroBuscado.idiomas().isEmpty()) {
                idiomas = libroBuscado.idiomas().stream()
                        .map(Idioma::fromCodigo)
                        .filter(idioma -> idioma != null)
                        .collect(Collectors.toList());
            }
            
            // Si no hay idiomas válidos, asignar inglés por defecto
            if (idiomas.isEmpty()) {
                idiomas.add(Idioma.INGLES);
            }
            
            // Crear y guardar libro
            Libro libro = new Libro(libroBuscado.titulo(), autor, idiomas, libroBuscado.numeroDescargas());
            libro = libroRepository.save(libro);
            
            System.out.println(ConsoleColors.GREEN_BOLD + "\n¡Libro guardado exitosamente en la base de datos!" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.PURPLE + "ID: " + libro.getId() + " - " + libro.getTitulo() + ConsoleColors.RESET);
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD + "\nError al buscar el libro: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
    
    public void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo hay libros registrados en la base de datos" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD + "Use la opción 1 para buscar y guardar libros" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN_BOLD + "\n========== LIBROS REGISTRADOS (" + libros.size() + ") ==========" + ConsoleColors.RESET);
            for (int i = 0; i < libros.size(); i++) {
                System.out.println(ConsoleColors.YELLOW_BOLD + "\n[" + (i+1) + "]" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.PURPLE + libros.get(i) + ConsoleColors.RESET);
            }
        }
    }
    
    public void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo hay autores registrados en la base de datos" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD + "Use la opción 1 o 2 para buscar y guardar autores" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN_BOLD + "\n========== AUTORES REGISTRADOS (" + autores.size() + ") ==========" + ConsoleColors.RESET);
            for (int i = 0; i < autores.size(); i++) {
                System.out.println(ConsoleColors.YELLOW_BOLD + "\n[" + (i+1) + "]" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.PURPLE + autores.get(i) + ConsoleColors.RESET);
            }
        }
    }
    
    public void listarAutoresVivosPorAno(int ano) {
        List<Autor> autores = autorRepository.findAutoresVivosEnAno(ano);
        if (autores.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo se encontraron autores vivos en el año " + ano + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN_BOLD + "\nAutores vivos en el año " + ano + ":" + ConsoleColors.RESET);
            autores.forEach(autor -> System.out.println(ConsoleColors.PURPLE + autor + ConsoleColors.RESET));
        }
    }
    
    public void listarLibrosPorIdioma(Idioma idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo se encontraron libros en " + idioma.getNombre() + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN_BOLD + "\nLibros en " + idioma.getNombre() + ":" + ConsoleColors.RESET);
            libros.forEach(libro -> System.out.println(ConsoleColors.PURPLE + libro + ConsoleColors.RESET));
        }
    }
    
    public void mostrarTop5Libros() {
        try {
            // Obtener libros de la base de datos
            List<Libro> librosLocales = libroRepository.findAll();
            
            if (librosLocales.isEmpty()) {
                System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo hay libros registrados en la base de datos." + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW_BOLD + "Buscando en la API Gutendex..." + ConsoleColors.RESET);
                
                // Si no hay libros locales, buscar en la API
                var json = consumoAPI.obtenerDatos(URL_BASE + "?sort=download_count");
                var respuesta = conversor.obtenerDatos(json, RespuestaAPI.class);
                
                if (respuesta.resultados() != null && !respuesta.resultados().isEmpty()) {
                    System.out.println(ConsoleColors.GREEN_BOLD + "\nTop 5 libros con mejor ranking (según la API):" + ConsoleColors.RESET);
                    System.out.println();
                    
                    for (int i = 0; i < Math.min(5, respuesta.resultados().size()); i++) {
                        var libro = respuesta.resultados().get(i);
                        String autorNombre = "Desconocido";
                        if (!libro.autores().isEmpty()) {
                            autorNombre = libro.autores().get(0).nombre();
                        }
                        
                        System.out.println(ConsoleColors.YELLOW_BOLD + "#" + (i+1) + ConsoleColors.RESET + 
                                         " - " + ConsoleColors.PURPLE_BOLD + libro.titulo() + ConsoleColors.RESET);
                        System.out.println("   Autor: " + autorNombre);
                        System.out.println("   Ranking: " + ConsoleColors.GREEN_BOLD + NumberFormatter.formatNumber(libro.numeroDescargas()) + " puntos" + ConsoleColors.RESET);
                        System.out.println();
                    }
                } else {
                    System.out.println(ConsoleColors.RED_BOLD + "No se pudieron obtener libros de la API." + ConsoleColors.RESET);
                }
            } else {
                // Si hay libros locales, ordenarlos por número de descargas
                List<Libro> top5 = librosLocales.stream()
                        .sorted(Comparator.comparing(Libro::getNumeroDescargas).reversed())
                        .limit(5)
                        .collect(Collectors.toList());
                
                System.out.println(ConsoleColors.GREEN_BOLD + "\nTop 5 libros con mejor ranking (base de datos local):" + ConsoleColors.RESET);
                System.out.println();
                
                for (int i = 0; i < top5.size(); i++) {
                    Libro libro = top5.get(i);
                    String autorNombre = "Desconocido";
                    if (libro.getAutor() != null) {
                        autorNombre = libro.getAutor().getNombre();
                    }
                    
                    System.out.println(ConsoleColors.YELLOW_BOLD + "#" + (i+1) + ConsoleColors.RESET + 
                                     " - " + ConsoleColors.PURPLE_BOLD + libro.getTitulo() + ConsoleColors.RESET);
                    System.out.println("   Autor: " + autorNombre);
                    System.out.println("   Ranking: " + ConsoleColors.GREEN_BOLD + NumberFormatter.formatNumber(libro.getNumeroDescargas()) + " puntos" + ConsoleColors.RESET);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD + "\nError al obtener el Top 5 de libros: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
    
    public void buscarLibrosPorAutor(String nombreAutor) {
        // Corregir posibles errores ortográficos en nombres de autores populares
        String nombreCorregido = AutorPopulares.corregirNombre(nombreAutor);
        
        // Si el nombre fue corregido, informar al usuario
        if (!nombreCorregido.equalsIgnoreCase(nombreAutor)) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nBuscando '" + nombreCorregido + "' en lugar de '" + nombreAutor + "'" + ConsoleColors.RESET);
        }
        
        Optional<Autor> autorOptional = autorRepository.findByNombre(nombreCorregido);
        
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            
            // Mostrar información detallada del autor
            System.out.println(ConsoleColors.GREEN_BOLD + "\nInformación del autor:" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.PURPLE_BOLD + "Nombre: " + ConsoleColors.RESET + autor.getNombre());
            
            String periodoVida = "";
            if (autor.getFechaNacimiento() != null) {
                periodoVida += autor.getFechaNacimiento();
            } else {
                periodoVida += "Desconocido";
            }
            
            periodoVida += " - ";
            
            if (autor.getFechaMuerte() != null) {
                periodoVida += autor.getFechaMuerte();
            } else {
                periodoVida += "Presente";
            }
            
            System.out.println(ConsoleColors.PURPLE_BOLD + "Periodo de vida: " + ConsoleColors.RESET + periodoVida);
            System.out.println(ConsoleColors.YELLOW_BOLD + "Estado: " + ConsoleColors.RESET + "Autor registrado en la base de datos");
            
            // Usar el método del repositorio para buscar libros por autor
            List<Libro> libros = libroRepository.findByAutor(autor);
            
            if (libros != null && !libros.isEmpty()) {
                System.out.println(ConsoleColors.GREEN_BOLD + "\nLibros del autor en la base de datos:" + ConsoleColors.RESET);
                libros.forEach(libro -> System.out.println(ConsoleColors.PURPLE + "- " + libro.getTitulo() + ConsoleColors.RESET));
            } else {
                System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo se encontraron libros para este autor en la base de datos" + ConsoleColors.RESET);
                
                // Buscar libros en la API
                try {
                    System.out.println(ConsoleColors.YELLOW_BOLD + "Buscando libros en la API Gutendex..." + ConsoleColors.RESET);
                    var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + autor.getNombre().replace(" ", "%20"));
                    var respuesta = conversor.obtenerDatos(json, RespuestaAPI.class);
                    
                    if (respuesta.resultados() != null && !respuesta.resultados().isEmpty()) {
                        System.out.println(ConsoleColors.GREEN_BOLD + "\nLibros encontrados en la API:" + ConsoleColors.RESET);
                        
                        // Filtrar libros por autor y eliminar duplicados
                        List<String> titulosUnicos = new ArrayList<>();
                        
                        for (var libro : respuesta.resultados()) {
                            // Verificar si el libro es realmente de este autor
                            boolean esDelAutor = false;
                            for (var autorLibro : libro.autores()) {
                                if (autorLibro.nombre().contains(autor.getNombre()) || 
                                    autor.getNombre().contains(autorLibro.nombre())) {
                                    esDelAutor = true;
                                    break;
                                }
                            }
                            
                            // Si es del autor y no está duplicado, añadirlo a la lista
                            if (esDelAutor && !titulosUnicos.contains(libro.titulo())) {
                                titulosUnicos.add(libro.titulo());
                            }
                        }
                        
                        // Mostrar los títulos únicos
                        if (!titulosUnicos.isEmpty()) {
                            for (String titulo : titulosUnicos) {
                                System.out.println(ConsoleColors.PURPLE + "- " + titulo + ConsoleColors.RESET);
                            }
                        } else {
                            System.out.println(ConsoleColors.YELLOW_BOLD + "No se encontraron libros específicos de este autor." + ConsoleColors.RESET);
                        }
                        
                        System.out.println(ConsoleColors.YELLOW_BOLD + "\nPuede guardar estos libros usando la opción 1 del menú (Buscar libro por título)." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD + "No se encontraron libros en la API para este autor." + ConsoleColors.RESET);
                    }
                } catch (Exception e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al buscar libros en la API: " + e.getMessage() + ConsoleColors.RESET);
                }
            }
        } else {
            // Intentar buscar en la API Gutendex
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nBuscando autor en la API Gutendex..." + ConsoleColors.RESET);
            
            try {
                var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreCorregido.replace(" ", "%20"));
                var respuesta = conversor.obtenerDatos(json, RespuestaAPI.class);
                
                if (respuesta.resultados() != null && !respuesta.resultados().isEmpty()) {
                    var primerLibro = respuesta.resultados().get(0);
                    
                    if (!primerLibro.autores().isEmpty()) {
                        var datosAutor = primerLibro.autores().get(0);
                        
                        // Mostrar información del autor encontrado en la API
                        System.out.println(ConsoleColors.GREEN_BOLD + "\nInformación del autor:" + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.PURPLE_BOLD + "Nombre: " + ConsoleColors.RESET + datosAutor.nombre());
                        
                        String periodoVida = "";
                        if (datosAutor.fechaNacimiento() != null) {
                            periodoVida += datosAutor.fechaNacimiento();
                        } else {
                            periodoVida += "Desconocido";
                        }
                        
                        periodoVida += " - ";
                        
                        if (datosAutor.fechaMuerte() != null) {
                            periodoVida += datosAutor.fechaMuerte();
                        } else {
                            periodoVida += "Presente";
                        }
                        
                        System.out.println(ConsoleColors.PURPLE_BOLD + "Periodo de vida: " + ConsoleColors.RESET + periodoVida);
                        System.out.println(ConsoleColors.YELLOW_BOLD + "Estado: " + ConsoleColors.RESET + "Autor encontrado en la API (no registrado en la base de datos)");
                        
                        // Guardar el autor automáticamente
                        Autor nuevoAutor = new Autor(datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaMuerte());
                        autorRepository.save(nuevoAutor);
                        
                        System.out.println(ConsoleColors.GREEN_BOLD + "\n¡Autor guardado exitosamente en la base de datos!" + ConsoleColors.RESET);
                        
                        // Buscar libros del autor en la API
                        System.out.println(ConsoleColors.YELLOW_BOLD + "\nLibros encontrados en la API:" + ConsoleColors.RESET);
                        
                        // Filtrar libros por autor y eliminar duplicados
                        List<String> titulosUnicos = new ArrayList<>();
                        
                        for (var libro : respuesta.resultados()) {
                            // Verificar si el libro es realmente de este autor
                            boolean esDelAutor = false;
                            for (var autorLibro : libro.autores()) {
                                if (autorLibro.nombre().contains(datosAutor.nombre()) || 
                                    datosAutor.nombre().contains(autorLibro.nombre())) {
                                    esDelAutor = true;
                                    break;
                                }
                            }
                            
                            // Si es del autor y no está duplicado, añadirlo a la lista
                            if (esDelAutor && !titulosUnicos.contains(libro.titulo())) {
                                titulosUnicos.add(libro.titulo());
                            }
                        }
                        
                        // Mostrar los títulos únicos
                        for (String titulo : titulosUnicos) {
                            System.out.println(ConsoleColors.PURPLE + "- " + titulo + ConsoleColors.RESET);
                        }
                        
                        // Sugerir buscar más libros
                        System.out.println(ConsoleColors.YELLOW_BOLD + "\nPuede buscar más libros de este autor usando la opción 1 del menú." + ConsoleColors.RESET);
                        return;
                    }
                }
                
                // Si no se encontró en la API
                System.out.println(ConsoleColors.RED_BOLD + "\nNo se encontró ningún autor con el nombre: " + nombreCorregido + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW_BOLD + "Sugerencias:" + ConsoleColors.RESET);
                System.out.println("- Verifica la ortografía del nombre del autor");
                System.out.println("- Intenta con el formato: 'Apellido, Nombre' (ej: 'Melville, Herman')");
                System.out.println("- Prueba con autores populares como: Shakespeare, Dickens, Austen, Poe");
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED_BOLD + "\nError al buscar el autor en la API: " + e.getMessage() + ConsoleColors.RESET);
            }
        }
    }
}