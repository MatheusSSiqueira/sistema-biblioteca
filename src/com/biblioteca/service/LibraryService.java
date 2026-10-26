package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Centraliza as regras do catálogo e mantém sua versão carregada em memória. */
public class LibraryService {
    private final BookRepository repository;
    private final List<Book> catalog;

    public LibraryService(BookRepository repository) {
        this.repository = repository;
        this.catalog = new ArrayList<>(repository.loadAll());
    }

    /** Cadastra um livro quando o código ainda não existe no acervo. */
    public boolean registerBook(int id, String title, String author, int year) {
        boolean idExists = catalog.stream().anyMatch(b -> b.getId() == id);
        if (idExists) {
            return false;
        }

        catalog.add(new Book(id, title, author, year));
        repository.saveAll(catalog);
        return true;
    }

    /** Retorna uma cópia imutável do catálogo atual. */
    public List<Book> listAll() {
        return List.copyOf(catalog);
    }

    /** Procura um livro pelo código informado. */
    public Optional<Book> findById(int id) {
        return catalog.stream().filter(b -> b.getId() == id).findFirst();
    }

    /** Ordena o catálogo por ano de publicação e persiste a nova ordem. */
    public void sortByYear() {
        // A ordenação é feita diretamente na lista mantida pelo serviço.
        catalog.sort(Comparator.comparingInt(Book::getYear));
        repository.saveAll(catalog);
    }

    /** Atualiza os dados de um livro existente e persiste a alteração. */
    public boolean editBook(int id, String newTitle, String newAuthor, int newYear) {
        Optional<Book> bookOpt = findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setYear(newYear);
            repository.saveAll(catalog);
            return true;
        }
        return false;
    }

    /** Remove o livro pelo código e persiste o catálogo quando encontra o registro. */
    public boolean removeBook(int id) {
        // A persistência ocorre somente quando algum registro foi removido.
        boolean removed = catalog.removeIf(b -> b.getId() == id);
        if (removed) {
            repository.saveAll(catalog);
        }
        return removed;
    }
}