package ru.kulakova.repositories;

import ru.kulakova.entities.Book;

import java.util.*;
import java.util.stream.Collectors;


public class LibraryRepository {

    private static LibraryRepository _instance;

    private final List<Book> _catalogue;
    private final Map<UUID, Book> _indexById;
    private final Map<String, Book> _uniqueIndex;

    private LibraryRepository() {
        _catalogue = new ArrayList<>();
        _indexById = new HashMap<>();
        _uniqueIndex = new HashMap<>();
    }

    public static LibraryRepository getInstance() {
        if (_instance == null) {
            _instance = new LibraryRepository();
        }
        return _instance;
    }

    public boolean add(Book book) {
        if (book == null) return false;

        String key = _buildKey(book.getTitle(), book.getAuthor());
        if (_uniqueIndex.containsKey(key)) {
            return false;
        }

        _catalogue.add(book);
        _indexById.put(book.getId(), book);
        _uniqueIndex.put(key, book);
        return true;
    }

    public Optional<Book> removeById(UUID id) {
        Book book = _indexById.remove(id);
        if (book == null) return Optional.empty();

        _catalogue.remove(book);
        _uniqueIndex.remove(_buildKey(book.getTitle(), book.getAuthor()));
        return Optional.of(book);
    }

    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(_indexById.get(id));
    }

    public List<Book> findByQuery(String query) { // can be edited
        if (query == null || query.isBlank()) {
            return new ArrayList<>();
        }

        String[] keywords = query.toLowerCase().trim().split("\\s+");

        return _catalogue.stream()
                .filter(book -> {
                    String title = book.getTitle().toLowerCase();
                    String author = book.getAuthor().toLowerCase();

                    return Arrays.stream(keywords)
                            .anyMatch(word -> title.contains(word) || author.contains(word));
                })
                .collect(Collectors.toList());
    }

    public List<Book> getAll() {
        return new ArrayList<>(_catalogue);
    }

    public int count() {
        return _catalogue.size();
    }

    public Optional<Book> findOldest() {
        return _catalogue.stream().min(Comparator.comparingInt(Book::getYear));
    }

    public Optional<Book> findNewest() {
        return _catalogue.stream().max(Comparator.comparingInt(Book::getYear));
    }

    public Map<String, Long> getTopAuthors(int limit) {
        return _catalogue.stream()
                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                ));
    }

    private String _buildKey(String title, String author) {
        return title.toLowerCase().trim() + "|" + author.toLowerCase().trim();
    }

    public void clear() {
        _catalogue.clear();
        _indexById.clear();
        _uniqueIndex.clear();
    }
}
