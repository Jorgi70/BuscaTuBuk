package com.challengerlura.Busca.tu.Buk.reposity;

import com.challengerlura.Busca.tu.Buk.model.Autor;
import com.challengerlura.Busca.tu.Buk.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAutorReposity extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anoBuscado AND a.fechaFallecio >= :anoBuscado")
    List<Autor> buscarPorFechas(int anoBuscado);
}
