import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kulakova.controllers.LibraryController;
import ru.kulakova.controllers.LibraryControllerImpl;
import ru.kulakova.entities.Book;
import ru.kulakova.entities.LibraryStats;
import ru.kulakova.exceptions.DuplicatedDataException;
import ru.kulakova.exceptions.InvalidDataException;
import ru.kulakova.exceptions.NotFoundException;
import ru.kulakova.services.LibraryService;
import ru.kulakova.writers.Writable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryControllerImplTest {

    @Mock
    private LibraryService _service;
    @Mock private Writable _writer;

    private LibraryController _controller;

    @BeforeEach
    void setUp() {
        _controller = new LibraryControllerImpl(_service, _writer);
    }

    @Test
    void LibraryController_handleAdd_success() {
        UUID expectedId = UUID.randomUUID();
        when(_service.addBook("1984", "Orwell", 1949)).thenReturn(expectedId);

        _controller.handleAdd("1984", "Orwell", 1949);

        verify(_writer).writeUUID(expectedId);
        verify(_writer).writeSuccess("Книга успешно добавлена");
        verifyNoMoreInteractions(_writer);
    }

    @Test
    void LibraryController_handleAdd_invalidTitle() {
        when(_service.addBook("", "Orwell", 1949))
                .thenThrow(new InvalidDataException("Необходимо ввести название книги"));

        _controller.handleAdd("", "Orwell", 1949);

        verify(_writer).writeError("Необходимо ввести название книги");
        verifyNoMoreInteractions(_writer);
    }

    @Test
    void LibraryController_handleAdd_duplicate() {
        when(_service.addBook("1984", "Orwell", 1949))
                .thenThrow(new DuplicatedDataException("Такая книга уже существует"));

        _controller.handleAdd("1984", "Orwell", 1949);

        verify(_writer).writeError("Такая книга уже существует");
    }

    @Test
    void LibraryController_handleRemove_success() {
        UUID id = UUID.randomUUID();
        Book mockBook = mock(Book.class);
        when(_service.removeBook(id)).thenReturn(mockBook);

        _controller.handleRemove(id);

        verify(_writer).writeBook(mockBook);
        verify(_writer).writeSuccess("Книга удалена");
        verifyNoMoreInteractions(_writer);
    }

    @Test
    void LibraryController_handleRemove_notFound() {
        UUID id = UUID.randomUUID();
        when(_service.removeBook(id))
                .thenThrow(new NotFoundException("Книга с ID " + id + " не найдена"));

        _controller.handleRemove(id);

        verify(_writer).writeError("Книга с ID " + id + " не найдена");
    }

    @Test
    void LibraryController_handleList_empty() {
        when(_service.listBooksByTimeLine()).thenReturn(List.of());

        _controller.handleList();

        verify(_writer).writeNotification("Ничего не найдено");
        verifyNoMoreInteractions(_writer);
    }

    @Test
    void LibraryController_handleList_withBooks() {
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        when(_service.listBooksByTimeLine()).thenReturn(List.of(book1, book2));

        _controller.handleList();

        verify(_writer).writeBookList(List.of(book1, book2));
        verifyNoMoreInteractions(_writer);
    }

    @Test
    void LibraryController_handleFind_success() {
        Book found = mock(Book.class);
        when(_service.findBooks("orwell")).thenReturn(List.of(found));

        _controller.handleFind("orwell");

        verify(_writer).writeBookList(List.of(found));
    }

    @Test
    void LibraryController_handleFind_empty() {
        when(_service.findBooks("xyz")).thenReturn(List.of());

        _controller.handleFind("xyz");

        verify(_writer).writeNotification("Ничего не найдено");
    }

    @Test
    void LibraryController_handleStats_success() {
        LibraryStats stats = new LibraryStats(
                10,
                Optional.empty(),
                Optional.empty(),
                Map.of("Orwell", 5L)
        );
        when(_service.getStats()).thenReturn(stats);

        _controller.handleStats();

        verify(_writer).writeStats(stats);
    }
}
