import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kulakova.commands.*;
import ru.kulakova.commands.lists.*;
import ru.kulakova.controllers.LibraryController;
import ru.kulakova.handlers.*;
import ru.kulakova.handlers.lists.ListByAuthorHandler;
import ru.kulakova.handlers.lists.ListByTitleHandler;
import ru.kulakova.handlers.lists.ListByYearHandler;
import ru.kulakova.handlers.lists.ListHandler;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandlerChainTest {

    @Mock
    private LibraryController _controller;

    @Nested
    @DisplayName("AddHandler")
    class AddHandlerTest {

        @Test
        void Handle_ValidFormat_returnsCommand() {
            AddHandler handler = new AddHandler(_controller);

            Commanding cmd = handler.handle("ADD", "1984;Orwell;1949");

            assertNotNull(cmd);
            assertInstanceOf(AddCommand.class, cmd);
        }

        @Test
        void Handle_WrongPartCount_returnsNull() {
            AddHandler handler = new AddHandler(_controller);

            Commanding cmd = handler.handle("ADD", "1984;Orwell"); // только 2 части

            assertNull(cmd);
        }

        @Test
        void Handle_TailInYear_returnsNull() {
            AddHandler handler = new AddHandler(_controller);

            Commanding cmd = handler.handle("ADD", "1984;Orwell;1949 extra");

            assertNull(cmd);
        }

        @Test
        void Handle_WrongCommand_delegatesToNext() {
            AddHandler handler = new AddHandler(_controller);
            Handleable next = mock(Handleable.class);
            handler.addNext(next);

            handler.handle("REMOVE", "some-id");

            verify(next).handle("REMOVE", "some-id");
        }
    }

    @Nested
    @DisplayName("RemoveHandler")
    class RemoveHandlerTest {

        @Test
        void Handle_ValidUuid_ReturnsCommand() {
            RemoveHandler handler = new RemoveHandler(_controller);
            String validId = UUID.randomUUID().toString();

            Commanding cmd = handler.handle("REMOVE", validId);

            assertNotNull(cmd);
            assertInstanceOf(RemoveCommand.class, cmd);
        }

        @Test
        void Handle_UuidWithTail_ReturnsNull() {
            RemoveHandler handler = new RemoveHandler(_controller);
            String idWithTail = UUID.randomUUID().toString() + " extra";

            Commanding cmd = handler.handle("REMOVE", idWithTail);

            assertNull(cmd);
        }

        @Test
        void Handle_InvalidUuid_ReturnsCommandWithNull() {
            RemoveHandler handler = new RemoveHandler(_controller);

            Commanding cmd = handler.handle("REMOVE", "not-a-uuid");

            assertNotNull(cmd);
            assertInstanceOf(RemoveCommand.class, cmd);
        }
    }

    @Nested
    @DisplayName("ListHandler with children")
    class ListHandlerTest {

        private ListHandler createListHandlerWithChain() {
            ListHandler handler = new ListHandler(_controller);
            Handleable sortChain = new ListByTitleHandler(_controller)
                    .addNext(new ListByAuthorHandler(_controller))
                    .addNext(new ListByYearHandler(_controller));
            handler.addChild(sortChain);
            return handler;
        }

        @Test
        void Handle_EmptyArgs_ReturnsDefaultCommand() {
            ListHandler handler = createListHandlerWithChain();

            Commanding cmd = handler.handle("LIST", "");

            assertNotNull(cmd);
            assertInstanceOf(ListCommand.class, cmd);
        }

        @Test
        void Handle_TitleKeyword_ReturnsTitleCommand() {
            ListHandler handler = createListHandlerWithChain();

            Commanding cmd = handler.handle("LIST", "title");

            assertNotNull(cmd);
            assertInstanceOf(ListByTitleCommand.class, cmd);
        }

        @Test
        void Handle_KeyWithTail_ReturnsNull() {
            ListHandler handler = createListHandlerWithChain();

            Commanding cmd = handler.handle("LIST", "year extra text");

            assertNull(cmd);
        }

        @Test
        void Handle_UnknownKeyword_ReturnsNull() {
            ListHandler handler = createListHandlerWithChain();

            Commanding cmd = handler.handle("LIST", "date");

            assertNull(cmd);
        }
    }

    @Nested
    @DisplayName("FindHandler")
    class FindHandlerTest {

        @Test
        void Handle_AnyQuery_ReturnsCommand() {
            FindHandler handler = new FindHandler(_controller);

            Commanding cmd = handler.handle("FIND", "war peace orwell");

            assertNotNull(cmd);
            assertInstanceOf(FindCommand.class, cmd);
        }

        @Test
        void Handle_WrongCommand_Delegates() {
            FindHandler handler = new FindHandler(_controller);
            Handleable next = mock(Handleable.class);
            handler.addNext(next);

            handler.handle("ADD", "args");

            verify(next).handle("ADD", "args");
        }
    }

    @Nested
    @DisplayName("StatsHandler")
    class StatsHandlerTest {

        @Test
        void Handle_NoArgs_ReturnsCommand() {
            StatsHandler handler = new StatsHandler(_controller);

            Commanding cmd = handler.handle("STATS", "");

            assertNotNull(cmd);
            assertInstanceOf(StatsCommand.class, cmd);
        }

        @Test
        void Handle_WithArgs_ReturnsNull() {
            StatsHandler handler = new StatsHandler(_controller);

            Commanding cmd = handler.handle("STATS", "extra");

            assertNull(cmd);
        }
    }
}