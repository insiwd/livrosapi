package com.insiwd.literalura.entities;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.insiwd.literalura.model.DadosApi;
import com.insiwd.literalura.model.DadosLivro;
import com.insiwd.literalura.model.Livro;
import com.insiwd.literalura.service.AutorService;
import com.insiwd.literalura.service.ConsumoApi;
import com.insiwd.literalura.service.ConverteDados;
import com.insiwd.literalura.service.LivroService;

@Component
public class Menu {

    private final Scanner sc = new Scanner(System.in);

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverteDados conversor;

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    private static final String ENDERECO = "https://gutendex.com/books/?search=";

    public void chamaMenu() {
        int escolha = -1;
        while (escolha != 0) {
            var menu = """
                    ----------------------------
                    Escolha o número de sua opção:
                    1- buscar livro pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    0- sair
                    ----------------------------
                    """;
            System.out.println(menu);
            escolha = sc.nextInt();
            sc.nextLine();

            switch (escolha) {
                case 1 -> buscarLivro();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosNoAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    public void buscarLivro() {
        System.out.print("Digite o nome do livro: ");
        String nomeLivro = sc.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        DadosApi dadosApi = conversor.obterDados(json, DadosApi.class);

        if (dadosApi.results() != null && !dadosApi.results().isEmpty()) {
            for (DadosLivro livro : dadosApi.results()) {
                System.out.println(livro);

                // Verifica se o livro já existe no banco de dados
                if (livroService.buscarPorTitulo(livro.titulo()).isEmpty()) {
                    livroService.salvarLivro(livro);
                } else {
                    System.out.println("O livro '" + livro.titulo() + "' já está registrado no banco de dados.");
                }
            }
        } else {
            System.out.println("Nenhum resultado encontrado.");
        }
    }

    public void listarLivrosRegistrados() {
        List<String> livrosFormatados = livroService.listarLivros();
        if (!livrosFormatados.isEmpty()) {
            livrosFormatados.forEach(System.out::println);
        } else {
            System.out.println("Nenhum livro registrado.");
        }
    }

    public void listarAutoresRegistrados() {
        List<String> autoresFormatados = autorService.listarAutores();
        if (!autoresFormatados.isEmpty()) {
            autoresFormatados.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor registrado.");
        }
    }

    public void listarAutoresVivosNoAno() {
        System.out.println("Insira o ano que deseja pesquisar:");
        int ano = sc.nextInt();
        sc.nextLine();

        List<String> autoresVivos = autorService.listarAutoresVivosNoAno(ano);
        if (!autoresVivos.isEmpty()) {
            autoresVivos.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor estava vivo no ano especificado.");
        }
    }

    public void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca:");
        System.out.println("es- espanhol");
        System.out.println("en- inglês");
        System.out.println("fr- francês");
        System.out.println("pt- português");

        String idiomaSelecionado = sc.nextLine();

        List<Livro> livros = livroService.buscarLivrosPorIdioma(idiomaSelecionado);
        if (!livros.isEmpty()) {
            for (Livro livro : livros) {
                System.out.println("-------------- Livro ----------------");
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autores: " + livro.getAutores().stream()
                        .map(autor -> autor.getNome())
                        .collect(Collectors.joining(", ")));
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Número de Downloads: " + livro.getDownloads());
                System.out.println("--------------------------------------");
            }
        } else {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        }
    }
}
