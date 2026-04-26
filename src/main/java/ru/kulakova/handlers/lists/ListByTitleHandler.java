package ru.kulakova.handlers.lists;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.lists.ListByTitleCommand;
import ru.kulakova.controllers.LibraryController;
import ru.kulakova.handlers.BaseHandler;

public class ListByTitleHandler extends BaseHandler {

    private static final String COMMAND_NAME = "title";


    public ListByTitleHandler(LibraryController controller) {
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

        return new ListByTitleCommand(_controller);
    }
}
