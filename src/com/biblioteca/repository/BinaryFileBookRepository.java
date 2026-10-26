package com.biblioteca.repository;

import com.biblioteca.model.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Implementação que serializa o acervo inteiro em um arquivo binário. */
public class BinaryFileBookRepository implements BookRepository {
    private final File storageFile;

    public BinaryFileBookRepository(String filePath) {
        this.storageFile = new File(filePath);
    }

    @Override
    public void saveAll(List<Book> books) {
        // A gravação integral mantém o arquivo simples e consistente com o catálogo em memória.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            out.writeObject(books);
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao persistir acervo", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> loadAll() {
        // Arquivo ausente, vazio ou inválido representa um acervo inicialmente vazio.
        if (!storageFile.exists() || storageFile.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(storageFile))) {
            return (List<Book>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Book> findById(int id) {
        // A busca reutiliza a leitura do repositório para manter uma única fonte de dados.
        return loadAll().stream()
                .filter(b -> b.getId() == id)
                .findFirst();
    }
}