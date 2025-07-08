package com.alura.ProyectoLiteratura.util;

import java.util.HashMap;
import java.util.Map;

public class TitulosPopulares {
    
    private static final Map<String, String> TITULOS_CORREGIDOS = new HashMap<>();
    
    static {
        // Libros populares con posibles errores ortográficos
        TITULOS_CORREGIDOS.put("frankestein", "frankenstein");
        TITULOS_CORREGIDOS.put("frankinstein", "frankenstein");
        TITULOS_CORREGIDOS.put("frankestain", "frankenstein");
        TITULOS_CORREGIDOS.put("don quijote", "don quixote");
        TITULOS_CORREGIDOS.put("don quixot", "don quixote");
        TITULOS_CORREGIDOS.put("mobi dick", "moby dick");
        TITULOS_CORREGIDOS.put("moby dic", "moby dick");
        TITULOS_CORREGIDOS.put("romeo y julieta", "romeo and juliet");
        TITULOS_CORREGIDOS.put("romeo and juliet", "romeo and juliet");
        TITULOS_CORREGIDOS.put("dracula", "dracula");
        TITULOS_CORREGIDOS.put("drakula", "dracula");
        TITULOS_CORREGIDOS.put("orgullo y prejuicio", "pride and prejudice");
        TITULOS_CORREGIDOS.put("pride and prejudice", "pride and prejudice");
        TITULOS_CORREGIDOS.put("la iliada", "the iliad");
        TITULOS_CORREGIDOS.put("iliada", "the iliad");
        TITULOS_CORREGIDOS.put("la odisea", "the odyssey");
        TITULOS_CORREGIDOS.put("odisea", "the odyssey");
    }
    
    public static String corregirTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return titulo;
        }
        
        String tituloLower = titulo.toLowerCase().trim();
        
        // Buscar coincidencia exacta
        if (TITULOS_CORREGIDOS.containsKey(tituloLower)) {
            return TITULOS_CORREGIDOS.get(tituloLower);
        }
        
        // Buscar coincidencia parcial
        for (Map.Entry<String, String> entry : TITULOS_CORREGIDOS.entrySet()) {
            if (tituloLower.contains(entry.getKey()) || entry.getKey().contains(tituloLower)) {
                return entry.getValue();
            }
        }
        
        return titulo;
    }
}