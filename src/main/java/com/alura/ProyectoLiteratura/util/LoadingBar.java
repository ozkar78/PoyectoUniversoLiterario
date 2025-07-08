package com.alura.ProyectoLiteratura.util;

public class LoadingBar {
    
    public static void display(String message, int durationMs) {
        int steps = 20;
        int sleepTime = durationMs / steps;
        
        System.out.print(ConsoleColors.YELLOW_BOLD + message + " ");
        
        for (int i = 0; i < steps; i++) {
            System.out.print(ConsoleColors.GREEN_BOLD + "█");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println(ConsoleColors.GREEN_BOLD + " ¡Completado!" + ConsoleColors.RESET);
    }
    
    public static void displaySpinner(String message, int durationMs) {
        char[] spinner = {'|', '/', '-', '\\'};
        int steps = 20;
        int sleepTime = durationMs / steps;
        
        System.out.print(ConsoleColors.YELLOW_BOLD + message + " ");
        
        for (int i = 0; i < steps; i++) {
            System.out.print(ConsoleColors.CYAN_BOLD + spinner[i % 4] + "\r" + message + " ");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println(ConsoleColors.GREEN_BOLD + "¡Listo!" + ConsoleColors.RESET);
    }
}