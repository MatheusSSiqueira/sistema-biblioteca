package com.biblioteca.repository;

import com.biblioteca.model.Book;
import java.util.List;
import java.util.Optional;

/** Contrato de persistência usado pelo serviço de biblioteca. */
public interface BookRepository {
    /** Substitui o acervo persistido pela lista informada. */
    void saveAll(List<Book> books);

    /** Carrega todos os livros persistidos. */
    List<Book> loadAll();

    /** Procura um livro pelo identificador. */
    Optional<Book> findById(int id);
}