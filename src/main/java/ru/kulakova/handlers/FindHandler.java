package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.FindCommand;
import ru.kulakova.controllers.LibraryController;

public class FindHandler extends BaseHandler{

    private static final String COMMAND_NAME = "FIND";

    public FindHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler != null ? _nextHandler.handle(command, args) : null;
        }

        return new FindCommand(_controller, args);
    }
}
