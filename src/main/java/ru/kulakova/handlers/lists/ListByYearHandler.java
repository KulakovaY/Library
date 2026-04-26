package ru.kulakova.handlers.lists;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.lists.ListByYearCommand;
import ru.kulakova.controllers.LibraryController;
import ru.kulakova.handlers.BaseHandler;

public class ListByYearHandler extends BaseHandler {

    private static final String COMMAND_NAME = "year";


    public ListByYearHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler.handle(command, args);
        }

        if (args != null && !args.isBlank()) {
            return null;
        }

        return new ListByYearCommand(_controller);
    }
}
