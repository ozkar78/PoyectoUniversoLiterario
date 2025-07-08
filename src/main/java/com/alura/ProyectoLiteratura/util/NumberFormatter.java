package com.alura.ProyectoLiteratura.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utilidad para formatear números
 */
public class NumberFormatter {
    
    private static final NumberFormat formatter = NumberFormat.getInstance(Locale.US);
    
    /**
     * Formatea un número decimal a un formato legible con separadores de miles
     * @param number El número a formatear
     * @return El número formateado como String
     */
    public static String formatNumber(Double number) {
        if (number == null) {
            return "0";
        }
        
        // Redondear a entero
        long roundedNumber = Math.round(number);
        
        // Formatear con separadores de miles
        return formatter.format(roundedNumber);
    }
}