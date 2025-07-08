package com.alura.ProyectoLiteratura.config;

import com.alura.ProyectoLiteratura.model.Autor;
import com.alura.ProyectoLiteratura.model.Idioma;
import com.alura.ProyectoLiteratura.model.Libro;
import com.alura.ProyectoLiteratura.repository.AutorRepository;
import com.alura.ProyectoLiteratura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AutorRepository autorRepository, LibroRepository libroRepository) {
        return args -> {
            // Verificar si ya hay datos
            if (autorRepository.count() == 0) {
                // Crear autores
                Autor borges = new Autor("Jorge Luis Borges", 1899, 1986);
                Autor cortazar = new Autor("Julio Cortázar", 1914, 1984);
                Autor garcia = new Autor("Gabriel García Márquez", 1927, 2014);
                Autor neruda = new Autor("Pablo Neruda", 1904, 1973);
                
                // Guardar autores
                autorRepository.saveAll(Arrays.asList(borges, cortazar, garcia, neruda));
                
                // Crear libros
                Libro ficciones = new Libro("Ficciones", borges, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.INGLES), 15000.0);
                    
                Libro aleph = new Libro("El Aleph", borges, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.FRANCES, Idioma.INGLES), 12000.0);
                    
                Libro rayuela = new Libro("Rayuela", cortazar, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.FRANCES), 20000.0);
                    
                Libro cronopios = new Libro("Historias de Cronopios y de Famas", cortazar, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.PORTUGUES), 8000.0);
                    
                Libro soledad = new Libro("Cien años de soledad", garcia, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.INGLES, Idioma.FRANCES, Idioma.PORTUGUES), 30000.0);
                    
                Libro amor = new Libro("El amor en los tiempos del cólera", garcia, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.INGLES), 18000.0);
                    
                Libro veinte = new Libro("Veinte poemas de amor y una canción desesperada", neruda, 
                    Arrays.asList(Idioma.ESPANOL, Idioma.FRANCES), 25000.0);
                
                // Guardar libros
                libroRepository.saveAll(Arrays.asList(ficciones, aleph, rayuela, cronopios, soledad, amor, veinte));
                
                System.out.println("Base de datos inicializada con datos de ejemplo");
            } else {
                System.out.println("La base de datos ya contiene datos, omitiendo inicialización");
            }
        };
    }
}