package com.alura.ProyectoLiteratura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", unique = true, nullable = false)
    private String titulo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    @Enumerated(EnumType.STRING)
    private List<Idioma> idiomas;
    
    @Column(name = "numero_descargas")
    private Double numeroDescargas;
    
    public Libro() {}
    
    public Libro(String titulo, Autor autor, List<Idioma> idiomas, Double numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    
    public List<Idioma> getIdiomas() { return idiomas; }
    public void setIdiomas(List<Idioma> idiomas) { this.idiomas = idiomas; }
    
    public Double getNumeroDescargas() { return numeroDescargas; }
    public void setNumeroDescargas(Double numeroDescargas) { this.numeroDescargas = numeroDescargas; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- LIBRO ---\n");
        sb.append("Título: ").append(titulo != null ? titulo : "Sin título").append("\n");
        sb.append("Autor: ").append(autor != null ? autor.getNombre() : "Desconocido").append("\n");
        sb.append("Idiomas: ");
        if (idiomas != null && !idiomas.isEmpty()) {
            sb.append(idiomas.stream().map(Idioma::getNombre).collect(Collectors.joining(", ")));
        } else {
            sb.append("No especificado");
        }
        sb.append("\n");
        sb.append("Descargas: ").append(numeroDescargas != null ? numeroDescargas.intValue() : 0);
        return sb.toString();
    }
}