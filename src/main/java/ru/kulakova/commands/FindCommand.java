package ru.kulakova.commands;

import ru.kulakova.controllers.LibraryController;

public class FindCommand implements Commanding {

    private final LibraryController _controller;
    private final String _query;

    public FindCommand(LibraryController controller, String query) {
        _controller = controller;
        _query = query;
    }

    @Override
    public void execute() {
        _controller.handleFind(_query);
    }
}
