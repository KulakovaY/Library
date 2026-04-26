package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.RemoveCommand;
import ru.kulakova.controllers.LibraryController;

import java.util.UUID;

public class RemoveHandler extends BaseHandler{

    private static final String COMMAND_NAME = "REMOVE";

    public RemoveHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler != null ? _nextHandler.handle(command, args) : null;
        }
        if (args != null && args.contains(" ")) {
            return null;
        }

        UUID id = null;
        if (args != null && !args.isBlank()) {
            try {
                id = UUID.fromString(args.trim());
            } catch (IllegalArgumentException e) {
                id = null;
            }
        }

        return new RemoveCommand(_controller, id);
    }
}
