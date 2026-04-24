package ru.kulakova.writers;

import ru.kulakova.entities.*;

import java.util.List;
import java.util.UUID;

public interface Writable {

    void writeSuccess(String message);
    void writeNotification(String message);
    void writeError(String message);

    void writeUUID(UUID id);

    void writeBook(Book book);
    void writeBookList(List<Book> books);

    void writeStats(LibraryStats stats);
}
