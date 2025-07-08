package com.alura.ProyectoLiteratura.util;

/**
 * Clase utilitaria para operaciones con cadenas de texto
 */
public class StringUtils {
    
    /**
     * Normaliza un texto eliminando acentos, diacríticos y convirtiendo a minúsculas
     * @param texto Texto a normalizar
     * @return Texto normalizado
     */
    public static String normalizar(String texto) {
        if (texto == null) {
            return null;
        }
        
        // Convertir a minúsculas
        String resultado = texto.toLowerCase().trim();
        
        // Reemplazar caracteres con acentos
        resultado = resultado.replaceAll("[áàäâã]", "a");
        resultado = resultado.replaceAll("[éèëê]", "e");
        resultado = resultado.replaceAll("[íìïî]", "i");
        resultado = resultado.replaceAll("[óòöôõ]", "o");
        resultado = resultado.replaceAll("[úùüû]", "u");
        resultado = resultado.replaceAll("[ñ]", "n");
        
        return resultado;
    }
    
    /**
     * Calcula la similitud entre dos cadenas (distancia de Levenshtein normalizada)
     * @param s1 Primera cadena
     * @param s2 Segunda cadena
     * @return Valor entre 0 (totalmente diferentes) y 1 (idénticas)
     */
    public static double similitud(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        
        // Normalizar las cadenas
        s1 = normalizar(s1);
        s2 = normalizar(s2);
        
        // Calcular la distancia de Levenshtein
        int[][] distancia = new int[s1.length() + 1][s2.length() + 1];
        
        for (int i = 0; i <= s1.length(); i++) {
            distancia[i][0] = i;
        }
        
        for (int j = 0; j <= s2.length(); j++) {
            distancia[0][j] = j;
        }
        
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int costo = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                distancia[i][j] = Math.min(
                    Math.min(distancia[i - 1][j] + 1, distancia[i][j - 1] + 1),
                    distancia[i - 1][j - 1] + costo
                );
            }
        }
        
        // Normalizar la distancia
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) return 1.0; // Ambas cadenas vacías son idénticas
        
        return 1.0 - ((double) distancia[s1.length()][s2.length()] / maxLen);
    }
}