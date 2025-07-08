package com.alura.ProyectoLiteratura.repository;

import com.alura.ProyectoLiteratura.model.Autor;
import com.alura.ProyectoLiteratura.model.Idioma;
import com.alura.ProyectoLiteratura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);
    
    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> findByIdioma(@Param("idioma") Idioma idioma);
    
    List<Libro> findByAutor(Autor autor);
}