package ru.kulakova.commands.lists;

import ru.kulakova.commands.Commanding;
import ru.kulakova.controllers.LibraryController;

public class ListByTitleCommand implements Commanding {

    private final LibraryController _controller;

    public ListByTitleCommand(LibraryController controller) {
        _controller = controller;
    }

    @Override
    public void execute() {
        _controller.handleListByTitle();
    }
}
