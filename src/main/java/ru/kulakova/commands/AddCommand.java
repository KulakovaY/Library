package ru.kulakova.commands;

import ru.kulakova.controllers.LibraryController;

public class AddCommand implements Commanding{

    private final LibraryController _controller;
    private final String _rawArgs;

    public AddCommand(LibraryController controller, String rawArgs) {
        _controller = controller;
        _rawArgs = rawArgs;
    }

    @Override
    public void execute() {
        String[] parts = _rawArgs != null ? _rawArgs.split(";") : new String[0];

        String title = parts.length > 0 ? parts[0].trim() : null;
        String author = parts.length > 1 ? parts[1].trim() : null;

        Integer year = null;
        if (parts.length > 2 && parts[2] != null && !parts[2].isBlank()) {
            try {
                year = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                year = null;
            }
        }

        _controller.handleAdd(title, author, year);
    }
}
