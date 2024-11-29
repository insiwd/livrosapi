package com.insiwd.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.insiwd.literalura.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTitulo(String titulo);

    @Query("SELECT l FROM Livro l JOIN FETCH l.autores WHERE l.idioma = :idioma")
    List<Livro> findByIdiomaWithAutores(@Param("idioma") String idioma);
}
