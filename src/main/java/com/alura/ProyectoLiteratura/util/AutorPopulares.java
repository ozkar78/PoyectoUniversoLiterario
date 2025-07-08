package com.alura.ProyectoLiteratura.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AutorPopulares {
    
    private static final Map<String, String> AUTORES_CORREGIDOS = new HashMap<>();
    private static final Map<String, List<String>> PALABRAS_CLAVE = new HashMap<>();
    
    static {
        // Autores populares con posibles errores ortográficos
        AUTORES_CORREGIDOS.put("melville", "Melville, Herman");
        AUTORES_CORREGIDOS.put("herman melville", "Melville, Herman");
        AUTORES_CORREGIDOS.put("shelley", "Shelley, Mary Wollstonecraft");
        AUTORES_CORREGIDOS.put("mary shelley", "Shelley, Mary Wollstonecraft");
        AUTORES_CORREGIDOS.put("shakespeare", "Shakespeare, William");
        AUTORES_CORREGIDOS.put("william shakespeare", "Shakespeare, William");
        AUTORES_CORREGIDOS.put("dickens", "Dickens, Charles");
        AUTORES_CORREGIDOS.put("charles dickens", "Dickens, Charles");
        AUTORES_CORREGIDOS.put("austen", "Austen, Jane");
        AUTORES_CORREGIDOS.put("jane austen", "Austen, Jane");
        AUTORES_CORREGIDOS.put("poe", "Poe, Edgar Allan");
        AUTORES_CORREGIDOS.put("edgar allan poe", "Poe, Edgar Allan");
        AUTORES_CORREGIDOS.put("stoker", "Stoker, Bram");
        AUTORES_CORREGIDOS.put("bram stoker", "Stoker, Bram");
        AUTORES_CORREGIDOS.put("cervantes", "Cervantes Saavedra, Miguel de");
        AUTORES_CORREGIDOS.put("miguel de cervantes", "Cervantes Saavedra, Miguel de");
        
        // Palabras clave para identificar autores por nombres parciales
        PALABRAS_CLAVE.put("Melville, Herman", Arrays.asList("melville", "herman"));
        PALABRAS_CLAVE.put("Shelley, Mary Wollstonecraft", Arrays.asList("shelley", "mary", "wollstonecraft"));
        PALABRAS_CLAVE.put("Shakespeare, William", Arrays.asList("shakespeare", "william"));
        PALABRAS_CLAVE.put("Dickens, Charles", Arrays.asList("dickens", "charles"));
        PALABRAS_CLAVE.put("Austen, Jane", Arrays.asList("austen", "jane"));
        PALABRAS_CLAVE.put("Poe, Edgar Allan", Arrays.asList("poe", "edgar", "allan"));
        PALABRAS_CLAVE.put("Stoker, Bram", Arrays.asList("stoker", "bram"));
        PALABRAS_CLAVE.put("Cervantes Saavedra, Miguel de", Arrays.asList("cervantes", "miguel", "saavedra"));
    }
    
    public static String corregirNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        }
        
        String nombreLower = StringUtils.normalizar(nombre);
        
        // Buscar coincidencia exacta
        if (AUTORES_CORREGIDOS.containsKey(nombreLower)) {
            return AUTORES_CORREGIDOS.get(nombreLower);
        }
        
        // Buscar coincidencia parcial en el mapa de correcciones
        for (Map.Entry<String, String> entry : AUTORES_CORREGIDOS.entrySet()) {
            if (nombreLower.contains(entry.getKey()) || entry.getKey().contains(nombreLower)) {
                return entry.getValue();
            }
        }
        
        // Buscar coincidencia por palabras clave
        for (Map.Entry<String, List<String>> entry : PALABRAS_CLAVE.entrySet()) {
            String autorCompleto = entry.getKey();
            List<String> palabrasClave = entry.getValue();
            
            // Dividir el nombre ingresado en palabras
            String[] palabrasIngresadas = nombreLower.split("\\s+|,");
            
            // Contar cuántas palabras clave coinciden
            int coincidencias = 0;
            for (String palabra : palabrasIngresadas) {
                palabra = palabra.trim();
                if (!palabra.isEmpty()) {
                    for (String palabraClave : palabrasClave) {
                        if (StringUtils.similitud(palabra, palabraClave) > 0.7) {
                            coincidencias++;
                            break;
                        }
                    }
                }
            }
            
            // Si hay al menos una coincidencia significativa, devolver el nombre completo
            if (coincidencias > 0) {
                return autorCompleto;
            }
        }
        
        // Búsqueda por similitud global
        return encontrarAutorMasSimilar(nombre);
    }
    
    /**
     * Encuentra el autor más similar al nombre proporcionado
     * @param nombre Nombre a buscar
     * @return Nombre completo del autor más similar o el nombre original si no hay coincidencias
     */
    private static String encontrarAutorMasSimilar(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        }
        
        // Normalizar el nombre
        String nombreNormalizado = StringUtils.normalizar(nombre);
        
        // Lista para almacenar coincidencias con su puntuación
        List<Map.Entry<String, Double>> coincidencias = new ArrayList<>();
        
        // Comprobar similitud con cada autor conocido
        for (String autorCompleto : PALABRAS_CLAVE.keySet()) {
            String autorNormalizado = StringUtils.normalizar(autorCompleto);
            double similitud = StringUtils.similitud(nombreNormalizado, autorNormalizado);
            
            // Comprobar también con las palabras clave
            for (String palabraClave : PALABRAS_CLAVE.get(autorCompleto)) {
                double similitudPalabra = StringUtils.similitud(nombreNormalizado, palabraClave);
                similitud = Math.max(similitud, similitudPalabra);
            }
            
            // Si la similitud es suficientemente alta, añadir a la lista
            if (similitud > 0.6) {
                coincidencias.add(Map.entry(autorCompleto, similitud));
            }
        }
        
        // Si hay coincidencias, devolver la más alta
        if (!coincidencias.isEmpty()) {
            coincidencias.sort(Comparator.comparing(Map.Entry<String, Double>::getValue).reversed());
            return coincidencias.get(0).getKey();
        }
        
        return nombre;
    }
}