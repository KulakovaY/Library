package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.StatsCommand;
import ru.kulakova.controllers.LibraryController;

public class StatsHandler extends BaseHandler{

    private static final String COMMAND_NAME = "STATS";

    public StatsHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler != null ? _nextHandler.handle(command, args) : null;
        }

        if (args != null && !args.isBlank()) {
            return null;
        }

        return new StatsCommand(_controller);
    }
}
