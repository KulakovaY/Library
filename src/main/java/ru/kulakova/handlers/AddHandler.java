package ru.kulakova.handlers;

import ru.kulakova.commands.AddCommand;
import ru.kulakova.commands.Commanding;
import ru.kulakova.controllers.LibraryController;

public class AddHandler extends BaseHandler {

    private static final String COMMAND_NAME = "ADD";

    public AddHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args){
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler != null ? _nextHandler.handle(command, args) : null;
        }

        String[] parts = args != null ? args.split(";", 3) : new String[0];
        if (parts.length != 3) {
            return null;
        }

        String title = parts[0].trim();
        String author = parts[1].trim();
        String yearStr = parts[2].trim();

        if (yearStr.contains(" ")) {
            return null;
        }

        Integer year = null;
        if (!yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                year = null;
            }
        }

        return new AddCommand(_controller, title, author, year);
    }
}
