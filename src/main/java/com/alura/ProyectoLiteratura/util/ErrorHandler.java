package com.alura.ProyectoLiteratura.util;

import java.util.function.Supplier;

/**
 * Clase utilitaria para manejar errores de forma elegante en toda la aplicación
 */
public class ErrorHandler {
    
    /**
     * Ejecuta una operación con manejo de errores
     * @param operation La operación a ejecutar
     * @param errorMessage Mensaje personalizado en caso de error
     */
    public static void execute(Runnable operation, String errorMessage) {
        try {
            operation.run();
        } catch (Exception e) {
            showError(errorMessage, e);
        }
    }
    
    /**
     * Ejecuta una operación que devuelve un resultado con manejo de errores
     * @param operation La operación a ejecutar
     * @param errorMessage Mensaje personalizado en caso de error
     * @param defaultValue Valor por defecto en caso de error
     * @return El resultado de la operación o el valor por defecto en caso de error
     */
    public static <T> T executeWithResult(Supplier<T> operation, String errorMessage, T defaultValue) {
        try {
            return operation.get();
        } catch (Exception e) {
            showError(errorMessage, e);
            return defaultValue;
        }
    }
    
    /**
     * Muestra un mensaje de error formateado
     * @param message Mensaje personalizado
     * @param e Excepción capturada
     */
    public static void showError(String message, Exception e) {
        System.out.println();
        System.out.println(ConsoleColors.RED_BOLD + "╔══════════════════ ERROR ══════════════════╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD + "║ " + message + ConsoleColors.RESET);
        
        // Mostrar detalles del error
        String errorDetail = e.getMessage();
        if (errorDetail != null && !errorDetail.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "║ Detalle: " + errorDetail + ConsoleColors.RESET);
        }
        
        // Mostrar sugerencias según el tipo de error
        System.out.println(ConsoleColors.GREEN_BOLD + "║ Sugerencias:" + ConsoleColors.RESET);
        
        if (e instanceof NumberFormatException) {
            System.out.println(ConsoleColors.GREEN_BOLD + "║ - Ingrese solo números en este campo" + ConsoleColors.RESET);
        } else if (e instanceof IllegalArgumentException) {
            System.out.println(ConsoleColors.GREEN_BOLD + "║ - Verifique los datos ingresados" + ConsoleColors.RESET);
        } else if (e instanceof NullPointerException) {
            System.out.println(ConsoleColors.GREEN_BOLD + "║ - Asegúrese de que todos los campos requeridos estén completos" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN_BOLD + "║ - Intente nuevamente con datos diferentes" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + "║ - Si el problema persiste, reinicie la aplicación" + ConsoleColors.RESET);
        }
        
        System.out.println(ConsoleColors.RED_BOLD + "╚═════════════════════════════════════════════╝" + ConsoleColors.RESET);
        System.out.println();
    }
}