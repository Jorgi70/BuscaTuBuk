package com.challengerlura.Busca.tu.Buk.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroAPI(
        @JsonAlias("results")
        List<DatosLibro> resultadoLibros
) {
}
