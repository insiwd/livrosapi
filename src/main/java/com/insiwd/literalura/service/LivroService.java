package com.insiwd.literalura.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insiwd.literalura.model.Autor;
import com.insiwd.literalura.model.DadosLivro;
import com.insiwd.literalura.model.Livro;
import com.insiwd.literalura.repository.AutorRepository;
import com.insiwd.literalura.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void salvarLivro(DadosLivro dadosLivro) {

        Optional<Livro> livroExistente = livroRepository.findByTitulo(dadosLivro.titulo());

        if (livroExistente.isPresent()) {
            System.out.println("O livro '" + dadosLivro.titulo() + "' já está registrado no banco de dados.");
            return;
        }

        Livro livro = new Livro();
        livro.setTitulo(dadosLivro.titulo());
        livro.setDownloads(dadosLivro.downloads());

        livro.setIdioma(dadosLivro.idioma().isEmpty() ? null : dadosLivro.idioma().get(0));

        List<Autor> autores = dadosLivro.autor().stream().map(dadosAutor -> {
            return autorRepository.findByNome(dadosAutor.nome())
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor();
                        novoAutor.setNome(dadosAutor.nome());
                        novoAutor.setNascimento(dadosAutor.nascimento());
                        novoAutor.setFalecimento(dadosAutor.falecimento());
                        return novoAutor;
                    });
        }).collect(Collectors.toList());

        autorRepository.saveAll(autores.stream().filter(autor -> autor.getId() == null).collect(Collectors.toList()));
        livro.setAutores(autores);

        livroRepository.save(livro);
    }
    
    @Transactional(readOnly = true)
    public List<String> listarLivros() {
        List<Livro> livros = livroRepository.findAll();

        for (Livro livro : livros) {
            livro.getAutores().size();
        }

        return livros.stream().map(livro -> {
            String autores = livro.getAutores().stream()
                    .map(Autor::getNome)
                    .collect(Collectors.joining(", "));

            return """
                    -------------- Livro ----------------
                    T\u00edtulo: """ + livro.getTitulo() + "\n" +
                    "Autores: " + autores + "\n" +
                    "Idioma: " + livro.getIdioma() + "\n" +
                    "Número de Downloads: " + livro.getDownloads() + "\n" +
                    "--------------------------------------";
        }).collect(Collectors.toList());
    }

    public Optional<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    public List<Livro> buscarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdiomaWithAutores(idioma);

        return livros;
    }

}
