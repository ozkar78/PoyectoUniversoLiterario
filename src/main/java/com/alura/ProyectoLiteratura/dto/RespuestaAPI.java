package com.alura.ProyectoLiteratura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaAPI(
    @JsonAlias("results") List<DatosLibro> resultados
) {}