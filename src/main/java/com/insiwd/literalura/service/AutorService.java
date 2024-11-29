package com.insiwd.literalura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insiwd.literalura.model.Autor;
import com.insiwd.literalura.repository.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<String> listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        return autores.stream()
                .map(this::formatarAutor)
                .collect(Collectors.toList());
    }

    public List<String> listarAutoresVivosNoAno(int ano) {
        List<Autor> autores = autorRepository.findAll();

        return autores.stream()
                .filter(autor -> autor.getFalecimento() <= ano &&
                        (autor.getFalecimento() == null || autor.getFalecimento() > ano))
                .map(this::formatarAutor)
                .collect(Collectors.toList());
    }

    private String formatarAutor(Autor autor) {
        String livros = autor.getLivros().stream()
                .map(livro -> livro.getTitulo())
                .collect(Collectors.joining(", "));

        return "Autor: " + autor.getNome() + "\n" +
                "Ano de nascimento: " + autor.getFalecimento() + "\n" +
                "Ano de falecimento: " + (autor.getFalecimento() != null ? autor.getFalecimento() : "N/A") + "\n" +
                "Livros: [" + (livros.isEmpty() ? "Nenhum livro registrado" : livros) + "]\n";
    }
}
