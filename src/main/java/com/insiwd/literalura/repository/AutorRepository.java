package com.insiwd.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insiwd.literalura.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Autor a JOIN FETCH a.livros")
    List<Autor> findAllWithLivros();
}
