package com.alura.ProyectoLiteratura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;
    
    @Column(name = "fecha_nacimiento")
    private Integer fechaNacimiento;
    
    @Column(name = "fecha_muerte")
    private Integer fechaMuerte;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;
    
    public Autor() {}
    
    public Autor(String nombre, Integer fechaNacimiento, Integer fechaMuerte) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaMuerte = fechaMuerte;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Integer fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public Integer getFechaMuerte() { return fechaMuerte; }
    public void setFechaMuerte(Integer fechaMuerte) { this.fechaMuerte = fechaMuerte; }
    
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- AUTOR ---\n");
        sb.append("Nombre: ").append(nombre != null ? nombre : "Desconocido").append("\n");
        sb.append("Nacimiento: ").append(fechaNacimiento != null ? fechaNacimiento : "Desconocido").append("\n");
        sb.append("Muerte: ").append(fechaMuerte != null ? fechaMuerte : "Aún vivo").append("\n");
        if (libros != null && !libros.isEmpty()) {
            sb.append("Libros registrados: ").append(libros.size());
        } else {
            sb.append("Sin libros registrados");
        }
        return sb.toString();
    }
}