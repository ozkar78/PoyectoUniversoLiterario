package com.alura.ProyectoLiteratura.model;

public enum Idioma {
    ESPANOL("es", "Español"),
    INGLES("en", "Inglés"),
    FRANCES("fr", "Francés"),
    PORTUGUES("pt", "Portugués");
    
    private String codigo;
    private String nombre;
    
    Idioma(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public static Idioma fromCodigo(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        return null;
    }
    
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    
    @Override
    public String toString() {
        return nombre;
    }
}