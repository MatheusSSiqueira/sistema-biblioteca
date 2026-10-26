package com.biblioteca.ui;

import com.biblioteca.model.Book;
import com.biblioteca.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Interface de console que traduz entradas do usuário em operações do serviço. */
public class ConsoleUI {
    private final LibraryService service;
    private final Scanner scanner;

    public ConsoleUI(LibraryService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    private void clearScreen() {
        // Usa ANSI para limpar a tela antes de redesenhar o menu.
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /** Mantém o menu ativo até que o usuário escolha a opção de saída. */
    public void start() {
        int option = -1;
        while (option != 7) {
            clearScreen();
            printMenu();
            
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Opção inválida! Pressione ENTER para continuar...");
                scanner.nextLine();
                continue;
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleRegister();
                case 2 -> handleList();
                case 3 -> handleSearch();
                case 4 -> handleSortByYear();
                case 5 -> handleEdit();
                case 6 -> handleRemove();
                case 7 -> System.out.println("Saindo do programa...");
                default -> {
                    System.out.println("Opção inexistente! Pressione ENTER para continuar...");
                    scanner.nextLine();
                }
            }
        }
    }

    /** Exibe as operações disponíveis e a quantidade atual de livros. */
    private void printMenu() {
        int totalBooks = service.listAll().size();
        System.out.println("=================================================");
        System.out.println("               SISTEMA DE BIBLIOTECA             ");
        System.out.println("=================================================");
        System.out.println("1 - Cadastrar livros");
        System.out.println("2 - Imprimir todos os livros");
        System.out.println("3 - Pesquisar livro por código");
        System.out.println("4 - Ordenar livros por ano");
        System.out.println("5 - Editar livro por código");
        System.out.println("6 - Remover livro por código");
        System.out.println("7 - Sair do programa");
        System.out.println("-------------------------------------------------");
        System.out.println("Acervo: " + totalBooks + " livro(s)");
        System.out.print("Escolha uma opção: ");
    }

    /** Lê os dados de um livro e solicita seu cadastro ao serviço. */
    private void handleRegister() {
        System.out.print("Código: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("Ano de Publicação: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        if (service.registerBook(id, title, author, year)) {
            System.out.println(">> Livro '" + title + "' cadastrado com sucesso.");
        } else {
            System.out.println(">> Erro: Já existe um livro com esse código.");
        }
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** Imprime todos os livros em formato detalhado e numerado. */
    private void handleList() {
        List<Book> books = service.listAll();
        if (books.isEmpty()) {
            System.out.println("Acervo vazio.");
        } else {
            int index = 1;
            for (Book book : books) {
                System.out.println("\nCADASTRO DO LIVRO #" + index++);
                System.out.println(book);
                System.out.println("-----------------------------------");
            }
        }
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** Pesquisa e imprime um livro usando seu código. */
    private void handleSearch() {
        System.out.print("Digite o código do livro: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Book> bookOpt = service.findById(id);
        if (bookOpt.isPresent()) {
            System.out.println("\n>> Livro Encontrado:");
            System.out.println(bookOpt.get());
        } else {
            System.out.println(">> Livro não encontrado.");
        }
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** Ordena o acervo por ano e informa o resultado ao usuário. */
    private void handleSortByYear() {
        service.sortByYear();
        System.out.println(">> Acervo ordenado por ano de publicação com sucesso!");
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** Lê novos dados e atualiza o livro selecionado pelo código. */
    private void handleEdit() {
        System.out.print("Digite o código do livro a ser editado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Book> bookOpt = service.findById(id);
        if (bookOpt.isPresent()) {
            System.out.print("Novo Título: ");
            String title = scanner.nextLine();
            System.out.print("Novo Autor: ");
            String author = scanner.nextLine();
            System.out.print("Novo Ano de Publicação: ");
            int year = scanner.nextInt();
            scanner.nextLine();

            service.editBook(id, title, author, year);
            System.out.println(">> Livro atualizado com sucesso!");
        } else {
            System.out.println(">> Erro: Livro não encontrado.");
        }
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** Remove do acervo o livro identificado pelo usuário. */
    private void handleRemove() {
        System.out.print("Digite o código do livro a ser removido: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (service.removeBook(id)) {
            System.out.println(">> Livro removido com sucesso.");
        } else {
            System.out.println(">> Erro: Livro não encontrado.");
        }
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}