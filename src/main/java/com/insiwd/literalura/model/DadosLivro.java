package com.insiwd.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DadosAutor> autor,
        @JsonAlias("download_count") Integer downloads,
        @JsonAlias("languages") List<String> idioma) {

    @Override
    public String toString() {
        String autorMap = autor.stream()
                .map(DadosAutor::nome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Desconhecido");

        return ">>>>>>>> LIVRO <<<<<<<\n" +
                "Título: " + titulo +"\n" +
                "Autor: " + autorMap + "\n" +
                "Downloads: " + downloads;

    }
}
