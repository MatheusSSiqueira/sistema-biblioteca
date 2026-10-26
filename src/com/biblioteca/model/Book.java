package com.biblioteca.model;

import java.io.Serializable;

/** Entidade do acervo com dados editáveis e estado de empréstimo do livro. */
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String title;
    private String author;
    private int year;
    private boolean borrowed;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.borrowed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public boolean borrow() {
        // Impede o empréstimo de um livro que já está emprestado.
        if (this.borrowed) {
            return false;
        }
        this.borrowed = true;
        return true;
    }

    public boolean returnBook() {
        // Só libera o livro quando há um empréstimo ativo.
        if (!this.borrowed) {
            return false;
        }
        this.borrowed = false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Código: %d\nTítulo: %s\nAutor: %s\nAno de Publicação: %d\nStatus: %s",
                id, title, author, year, (borrowed ? "Emprestado" : "Disponível"));
    }
}