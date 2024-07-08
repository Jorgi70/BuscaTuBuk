package com.challengerlura.Busca.tu.Buk.reposity;

import com.challengerlura.Busca.tu.Buk.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ILibroReposity extends JpaRepository <Libros, Long> {


    boolean existsByTitulo(String titulo);

    Libros findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT a.nombre, l.titulo FROM Autor a INNER JOIN Libros l ON a.id = l.id WHERE a.nombre LIKE %:autor%")
    Optional<Libros> findLibrosByAutorNombre(String autor);

    @Query("SELECT l FROM Libros l WHERE l.idioma = :idiomaLibro")
    List<Libros> BuscarPorIdioma(String idiomaLibro);

    @Query("SELECT l FROM Libros l ORDER BY l.descargas DESC LIMIT 10")
    List<Libros> top10MasDescargados();
}
