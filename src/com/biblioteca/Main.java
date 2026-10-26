package com.biblioteca;

import com.biblioteca.repository.BinaryFileBookRepository;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.service.LibraryService;
import com.biblioteca.ui.ConsoleUI;

/** Ponto de entrada que monta as dependências e inicia a interface de console. */
public class Main {
    /** Cria as camadas da aplicação usando o arquivo binário como persistência. */
    public static void main(String[] args) {
        BookRepository repository = new BinaryFileBookRepository("acervo.dat");
        LibraryService service = new LibraryService(repository);
        ConsoleUI ui = new ConsoleUI(service);

        ui.start();
    }
}