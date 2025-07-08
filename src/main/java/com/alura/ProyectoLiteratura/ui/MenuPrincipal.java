package com.alura.ProyectoLiteratura.ui;

import com.alura.ProyectoLiteratura.model.Idioma;
import com.alura.ProyectoLiteratura.service.LiteraturaService;
import com.alura.ProyectoLiteratura.util.ConsoleColors;
import com.alura.ProyectoLiteratura.util.ErrorHandler;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class MenuPrincipal {

    private final LiteraturaService literaturaService;
    private final Scanner scanner;

    public MenuPrincipal(LiteraturaService literaturaService) {
        this.literaturaService = literaturaService;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            mostrarCabecera();
            mostrarOpciones();

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                procesarOpcion(opcion);
            } catch (Exception e) {
                ErrorHandler.showError("Error al procesar la opción seleccionada", e);
                scanner.nextLine(); // Limpiar buffer en caso de error
            }
        }
        scanner.close();
    }

    private void mostrarCabecera() {
        System.out.println();
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "============= BIENVENIDOS A UNIVERSO LITERARIO =============" + ConsoleColors.RESET);
        System.out.println();
        System.out.println();
        System.out.println(ConsoleColors.YELLOW_BOLD + "============= MENÚ PRINCIPAL ===============" + ConsoleColors.RESET);
        System.out.println();
    }

    private void mostrarOpciones() {
        System.out.println(ConsoleColors.GREEN_BOLD + "[1]" + ConsoleColors.RESET + " - Buscar libro por título:");
        System.out.println(ConsoleColors.GREEN_BOLD + "[2]" + ConsoleColors.RESET + " - Buscar libros por autor:");
        System.out.println(ConsoleColors.GREEN_BOLD + "[3]" + ConsoleColors.RESET + " - Listar libros registrados:");
        System.out.println(ConsoleColors.GREEN_BOLD + "[4]" + ConsoleColors.RESET + " - Listar autores registrados:");
        System.out.println(ConsoleColors.GREEN_BOLD + "[5]" + ConsoleColors.RESET + " - Listar autores vivos en un año determinado:");
        System.out.println(ConsoleColors.GREEN_BOLD + "[6]" + ConsoleColors.RESET + " - Listar libros por idioma:");
        System.out.println(ConsoleColors.GREEN_BOLD + "[7]" + ConsoleColors.RESET + " - Ver Top 5 libros con mejor ranking:");
        System.out.println(ConsoleColors.RED_BOLD + "[0]" + ConsoleColors.RESET + " - Salir:");
        System.out.println();
        System.out.print(ConsoleColors.YELLOW_BOLD + "Seleccione una opción: " + ConsoleColors.RESET);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                buscarLibroPorTitulo();
                break;
            case 2:
                buscarLibrosPorAutor();
                break;
            case 3:
                listarLibrosRegistrados();
                break;
            case 4:
                listarAutoresRegistrados();
                break;
            case 5:
                listarAutoresVivosPorAno();
                break;
            case 6:
                mostrarMenuIdiomas();
                break;
            case 7:
                mostrarTop5Libros();
                break;
            case 0:
                mostrarMensajeDespedida();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "\nOpción no válida" + ConsoleColors.RESET);
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== BUSCAR LIBRO POR TÍTULO =====" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD + "Ingrese el título del libro: " + ConsoleColors.RESET);
        String titulo = scanner.nextLine();
        
        ErrorHandler.execute(
            () -> literaturaService.buscarLibroPorTitulo(titulo),
            "Error al buscar el libro por título"
        );
    }

    private void buscarLibrosPorAutor() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== BUSCAR LIBROS POR AUTOR =====" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD + "Ingrese el nombre del autor: " + ConsoleColors.RESET);
        String nombreAutor = scanner.nextLine();
        
        ErrorHandler.execute(
            () -> literaturaService.buscarLibrosPorAutor(nombreAutor),
            "Error al buscar libros por autor"
        );
    }

    private void listarLibrosRegistrados() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== LIBROS REGISTRADOS =====" + ConsoleColors.RESET);
        
        ErrorHandler.execute(
            () -> literaturaService.listarLibrosRegistrados(),
            "Error al listar los libros registrados"
        );
    }

    private void listarAutoresRegistrados() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== AUTORES REGISTRADOS =====" + ConsoleColors.RESET);
        
        ErrorHandler.execute(
            () -> literaturaService.listarAutoresRegistrados(),
            "Error al listar los autores registrados"
        );
    }

    private void listarAutoresVivosPorAno() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== AUTORES VIVOS POR AÑO =====" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD + "Ingrese el año: " + ConsoleColors.RESET);
        
        try {
            int ano = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            ErrorHandler.execute(
                () -> literaturaService.listarAutoresVivosPorAno(ano),
                "Error al listar autores vivos por año"
            );
        } catch (Exception e) {
            ErrorHandler.showError("El año debe ser un número válido", e);
            scanner.nextLine(); // Limpiar buffer en caso de error
        }
    }

    private void mostrarMenuIdiomas() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== LIBROS POR IDIOMA =====" + ConsoleColors.RESET);
        System.out.println();
        System.out.println(ConsoleColors.YELLOW_BOLD + "Seleccione el idioma:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD + "[1]" + ConsoleColors.RESET + " - Español");
        System.out.println(ConsoleColors.GREEN_BOLD + "[2]" + ConsoleColors.RESET + " - Inglés");
        System.out.println(ConsoleColors.GREEN_BOLD + "[3]" + ConsoleColors.RESET + " - Francés");
        System.out.println(ConsoleColors.GREEN_BOLD + "[4]" + ConsoleColors.RESET + " - Portugués");
        System.out.println();
        System.out.print(ConsoleColors.YELLOW_BOLD + "Opción: " + ConsoleColors.RESET);

        try {
            int opcionIdioma = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            Idioma idioma = switch (opcionIdioma) {
                case 1 -> Idioma.ESPANOL;
                case 2 -> Idioma.INGLES;
                case 3 -> Idioma.FRANCES;
                case 4 -> Idioma.PORTUGUES;
                default -> null;
            };

            if (idioma != null) {
                ErrorHandler.execute(
                    () -> literaturaService.listarLibrosPorIdioma(idioma),
                    "Error al listar libros por idioma"
                );
            } else {
                System.out.println(ConsoleColors.RED_BOLD + "\nOpción de idioma no válida" + ConsoleColors.RESET);
            }
        } catch (Exception e) {
            ErrorHandler.showError("Debe ingresar un número válido para el idioma", e);
            scanner.nextLine(); // Limpiar buffer en caso de error
        }
    }
    
    private void mostrarTop5Libros() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== TOP 5 LIBROS CON MEJOR RANKING =====" + ConsoleColors.RESET);
        
        ErrorHandler.execute(
            () -> literaturaService.mostrarTop5Libros(),
            "Error al obtener el Top 5 de libros"
        );
    }

    private void mostrarMensajeDespedida() {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE_BOLD + "===== ¡GRACIAS POR USAR UNIVERSO LITERARIO! =====" + ConsoleColors.RESET);
    }
}