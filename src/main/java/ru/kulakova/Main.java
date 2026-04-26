package ru.kulakova;

import ru.kulakova.controllers.LibraryController;
import ru.kulakova.controllers.LibraryControllerImpl;
import ru.kulakova.handlers.*;
import ru.kulakova.handlers.lists.ListByAuthorHandler;
import ru.kulakova.handlers.lists.ListByTitleHandler;
import ru.kulakova.handlers.lists.ListByYearHandler;
import ru.kulakova.handlers.lists.ListHandler;
import ru.kulakova.runners.ConsoleRunner;
import ru.kulakova.services.LibraryService;
import ru.kulakova.services.LibraryServiceImpl;
import ru.kulakova.writers.ConsoleWriter;
import ru.kulakova.writers.Writable;

public class Main {
    public static void main(String[] args) {

        Writable writer = new ConsoleWriter();
        LibraryService service = new LibraryServiceImpl();
        LibraryController controller = new LibraryControllerImpl(service, writer);

        Handleable listSortChain = new ListByTitleHandler(controller)
                .addNext(new ListByAuthorHandler(controller))
                .addNext(new ListByYearHandler(controller));

        ListHandler listHandler = new ListHandler(controller);
        listHandler.addChild(listSortChain);

        Handleable mainChain = new AddHandler(controller)
                .addNext(new RemoveHandler(controller))
                .addNext(listHandler)
                .addNext(new FindHandler(controller))
                .addNext(new StatsHandler(controller));

        ConsoleRunner runner = new ConsoleRunner(mainChain);
        runner.run();
    }
}